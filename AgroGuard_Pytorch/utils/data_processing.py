import os

image_dir = 'D:\BIRD\year4\FinalYearProject\AgroGuard\AgroGuard_Pytorch\dataset\\test\images'  # 图像文件夹路径
annotation_dir = 'D:\BIRD\year4\FinalYearProject\AgroGuard\AgroGuard_Pytorch\dataset\\test\labels'  # 标注文件夹路径
train_annotation_path = 'D:\BIRD\year4\FinalYearProject\AgroGuard\AgroGuard_Pytorch\dataset\ImageSets\Main\\test.txt'  # 总标注文件路径

# 打开总标注文件准备写入
with open(train_annotation_path, 'w') as f:
    # 遍历图像目录下的所有图像文件
    for image_name in os.listdir(image_dir):
        if not image_name.endswith('.jpg'):  # 根据你的图像格式调整
            continue

        # 构建图像路径和相应的标注文件路径
        image_path = os.path.join(image_dir, image_name)
        annotation_path = os.path.join(annotation_dir, os.path.splitext(image_name)[0] + '.txt')

        # 检查标注文件是否存在
        if not os.path.exists(annotation_path):
            print(f"Annotation file for image {image_name} not found.")
            continue

        # 读取标注文件内容
        with open(annotation_path, 'r') as ann_file:
            annotations = ann_file.read().strip()

        # 将图像路径和标注写入总标注文件
        f.write(image_path + ' ' + annotations + '\n')
