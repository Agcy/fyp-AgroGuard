import os

import torch
from tqdm import tqdm

from utils.image_processing import get_lr


def calculate_accuracy(outputs, targets):
    correct = 0
    total = 0
    for output, target in zip(outputs, targets):
        # 假设output的shape为[N, num_classes]，target为[N, 1]，每个元素是类别的索引
        pred = output.max(1)[1]  # 获取最大概率的类别索引
        correct += (pred == target).sum().item()
        total += target.size(0)
    accuracy = 100 * correct / total
    return accuracy


def fit_one_epoch(model_train, model, yolo_loss, loss_history, optimizer, epoch, epoch_step, epoch_step_val, gen,
                  gen_val, Epoch, cuda, fp16, scaler, save_period, save_dir, local_rank=0):
    loss = 0  # fit_one_epoch只进行一轮的训练，loss和val_loss统计的是一轮的训练加验证损失
    val_loss = 0
    # 在fit_one_epoch函数的开始部分初始化累积变量
    train_accuracy = 0
    val_accuracy = 0
    num_batches = 0

    if local_rank == 0:
        print('Start Train')
    model_train.train()
    with tqdm(total=epoch_step, desc=f'Epoch {epoch + 1}/{Epoch}', postfix=dict, mininterval=0.3) as pbar:
        for iteration, batch in enumerate(gen):
            if iteration >= epoch_step:
                break

            images, targets = batch[0], batch[1]
            with torch.no_grad():
                if cuda:
                    images = images.cuda()
                    targets = [ann.cuda() for ann in targets]
            # ----------------------#
            #   清零梯度
            # ----------------------#
            optimizer.zero_grad()
            if not fp16:
                # ----------------------#
                #   前向传播
                # ----------------------#
                outputs = model_train(images)

                loss_value_all = 0
                # ----------------------#
                #   计算损失
                # ----------------------#
                for l in range(len(outputs)):
                    loss_item = yolo_loss(l, outputs[l], targets)
                    loss_value_all += loss_item
                loss_value = loss_value_all

                # ----------------------#
                #   反向传播
                # ----------------------#
                loss_value.backward()
                optimizer.step()
            else:
                from torch.cuda.amp import autocast
                with autocast():
                    # ----------------------#
                    #   前向传播
                    # ----------------------#
                    outputs = model_train(images)

                    loss_value_all = 0
                    # ----------------------#
                    #   计算损失
                    # ----------------------#
                    for l in range(len(outputs)):
                        loss_item = yolo_loss(l, outputs[l], targets)
                        loss_value_all += loss_item
                    loss_value = loss_value_all

                # ----------------------#
                #   反向传播
                # ----------------------#
                scaler.scale(loss_value).backward()
                scaler.step(optimizer)
                scaler.update()

                # 在反向传播后
            accuracy = calculate_accuracy(outputs, targets)
            pbar.set_postfix(**{'loss': loss / (iteration + 1), 'acc': accuracy, 'lr': get_lr(optimizer)})

            loss += loss_value.item()
            # 在每个训练和验证批次后更新累积精确度
            train_accuracy += calculate_accuracy(outputs, targets)
            num_batches += 1

            # 在训练和验证阶段结束时计算平均精确度
            avg_train_accuracy = train_accuracy / num_batches
            avg_val_accuracy = val_accuracy / num_batches

            if local_rank == 0:
                pbar.set_postfix(**{'loss': loss / (iteration + 1),
                                    'lr': get_lr(optimizer)})
                pbar.update(1)

    if local_rank == 0:
        print('Finish Train')
        print('Start Validation')

    model_train.eval()
    with tqdm(total=epoch_step_val, desc=f'Epoch {epoch + 1}/{Epoch}', postfix=dict, mininterval=0.3) as pbar:
        for iteration, batch in enumerate(gen_val):
            if iteration >= epoch_step_val:
                break
            images, targets = batch[0], batch[1]
            with torch.no_grad():
                if cuda:
                    images = images.cuda()
                    targets = [ann.cuda() for ann in targets]
                # ----------------------#
                #   清零梯度
                # ----------------------#
                optimizer.zero_grad()
                # ----------------------#
                #   前向传播
                # ----------------------#
                outputs = model_train(images)

                loss_value_all = 0
                # ----------------------#
                #   计算损失
                # ----------------------#
                for l in range(len(outputs)):
                    loss_item = yolo_loss(l, outputs[l], targets)
                    loss_value_all += loss_item
                loss_value = loss_value_all

            # 在计算完损失后
            accuracy = calculate_accuracy(outputs, targets)


            val_loss += loss_value.item()
            # 在每个训练和验证批次后更新累积精确度
            train_accuracy += calculate_accuracy(outputs, targets)
            num_batches += 1

            # 在训练和验证阶段结束时计算平均精确度
            avg_train_accuracy = train_accuracy / num_batches
            avg_val_accuracy = val_accuracy / num_batches
            if local_rank == 0:
                pbar.set_postfix(**{'val_loss': val_loss / (iteration + 1), 'val_acc': accuracy})
                # pbar.set_postfix(**{'val_loss': val_loss / (iteration + 1)})
                pbar.update(1)

    if local_rank == 0:
        print('Finish Validation')
        # loss_history.append_loss(epoch + 1, loss / epoch_step, val_loss / epoch_step_val)
        loss_history.append_loss(epoch + 1, loss / epoch_step, val_loss / epoch_step_val, avg_train_accuracy,
                                 avg_val_accuracy)
        print('Epoch:' + str(epoch + 1) + '/' + str(Epoch))
        # print('Total Loss: %.3f || Val Loss: %.3f ' % (loss / epoch_step, val_loss / epoch_step_val))
        print('Total Loss: %.3f || Val Loss: %.3f || Train Acc: %.2f %% || Val Acc: %.2f %%' % (
            loss / epoch_step, val_loss / epoch_step_val, avg_train_accuracy, avg_val_accuracy))

        # -----------------------------------------------#
        #   保存权值
        # -----------------------------------------------#
        if (epoch + 1) % save_period == 0 or epoch + 1 == Epoch:
            torch.save(model.state_dict(), os.path.join(save_dir, "ep%03d-loss%.3f-val_loss%.3f.pth" % (
                epoch + 1, loss / epoch_step, val_loss / epoch_step_val)))

        if len(loss_history.val_loss) <= 1 or (val_loss / epoch_step_val) <= min(loss_history.val_loss):
            print('Save best model to best_epoch_weights.pth')
            torch.save(model.state_dict(), os.path.join(save_dir, "best_epoch_weights.pth"))

        torch.save(model.state_dict(), os.path.join(save_dir, "last_epoch_weights.pth"))
        # 以字典的形式保存模型（推荐），加载的时候先定义模型结构，再加载模型参数。
        # 此处保存的是整个模型的权重，当然可以修改传入的model参数为backbone来保存主干网络的权重
