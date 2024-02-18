import torch
from torch.utils.data import Dataset, DataLoader
from torchvision import transforms, models
import torch.nn as nn
import torch.optim as optim
import os
from PIL import Image
import yaml


# 定义数据集类
class PlantDiseaseDataset(Dataset):
    def __init__(self, image_dir, label_dir, class_names, transform=None):
        self.image_dir = image_dir
        self.label_dir = label_dir
        self.class_names = class_names
        self.transform = transform
        self.image_labels = []

        # 过滤不存在的图像或标签
        for img_file in os.listdir(image_dir):
            image_path = os.path.join(image_dir, img_file)
            label_path = os.path.join(label_dir, img_file.replace('.jpg', '.txt'))
            if os.path.exists(image_path) and os.path.exists(label_path):
                self.image_labels.append((image_path, label_path))

    def __len__(self):
        return len(self.image_labels)

    def __getitem__(self, idx):
        # 正确地读取图像和对应的标签文件路径
        image_path, label_path = self.image_labels[idx]

        # 读取图像
        image = Image.open(image_path).convert('RGB')

        # 尝试读取标签文件的第一行第一个数字作为类别索引
        try:
            with open(label_path, 'r') as f:
                line = f.readline().strip()
                parts = line.split()
                if len(parts) > 0:
                    class_index = int(parts[0])
                else:
                    raise ValueError("标签文件格式不正确或为空")
        except ValueError as e:
            print(f"读取标签文件时出错: {label_path}, 错误: {e}")
            class_index = 0  # 或根据实际情况处理

        # 应用转换（如果有的话）
        if self.transform:
            image = self.transform(image)

        return image, class_index


class ModelTrainer:
    def __init__(self, data_yaml_path, train_image_dir, train_label_dir, test_image_dir, test_label_dir):
        self.data_yaml_path = data_yaml_path

        self.train_image_dir = train_image_dir
        self.train_label_dir = train_label_dir
        self.test_image_dir = test_image_dir
        self.test_label_dir = test_label_dir
        with open(data_yaml_path, 'r') as file:
            self.data_yaml = yaml.safe_load(file)
        self.device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
        self.model = self.configure_model()
        self.train_loader = self.configure_dataloader(train_image_dir, train_label_dir, self.data_yaml['names'])
        self.test_loader = self.configure_dataloader(test_image_dir, test_label_dir, self.data_yaml['names'])
        self.criterion = nn.CrossEntropyLoss()
        self.optimizer = optim.Adam(self.model.parameters(), lr=0.001)

    def configure_model(self):
        model = models.densenet121(pretrained=True)
        num_ftrs = model.classifier.in_features
        model.classifier = nn.Linear(num_ftrs, len(self.data_yaml['names']))
        model.to(self.device)
        return model

    def configure_dataloader(self, image_dir, label_dir, class_names):
        transform = transforms.Compose([
            transforms.Resize((224, 224)),
            transforms.ToTensor(),
            transforms.Normalize(mean=[0.485, 0.456, 0.406], std=[0.229, 0.224, 0.225]),
        ])
        dataset = PlantDiseaseDataset(image_dir, label_dir, class_names, transform)
        return DataLoader(dataset, batch_size=32, shuffle=True)

    def train(self, num_epochs=10):
        for epoch in range(num_epochs):
            self.model.train()
            running_loss = 0.0
            for inputs, labels in self.train_loader:
                inputs, labels = inputs.to(self.device), labels.to(self.device)
                self.optimizer.zero_grad()
                outputs = self.model(inputs)
                loss = self.criterion(outputs, labels)
                loss.backward()
                self.optimizer.step()
                running_loss += loss.item()
            print(f'Epoch {epoch + 1}/{num_epochs}, Loss: {running_loss / len(self.train_loader)}')

    def test(self):
        self.model.eval()
        correct = 0
        total = 0
        with torch.no_grad():
            for inputs, labels in self.test_loader:
                inputs, labels = inputs.to(self.device), labels.to(self.device)
                outputs = self.model(inputs)
                _, predicted = torch.max(outputs.data, 1)
                total += labels.size(0)
                correct += (predicted == labels).sum().item()
        print(f'Test Accuracy: {100 * correct / total:.2f}%')


# 示例用法
if __name__ == "__main__":
    data_yaml_path = 'D:/BIRD/year4/FinalYearProject/AgroGuard/AgroGuard_Pytorch/dataset/data.yaml'
    train_image_dir = 'D:/BIRD/year4/FinalYearProject/AgroGuard/AgroGuard_Pytorch/dataset/train/images'
    train_label_dir = 'D:/BIRD/year4/FinalYearProject/AgroGuard/AgroGuard_Pytorch/dataset/train/labels'
    test_image_dir = 'D:/BIRD/year4/FinalYearProject/AgroGuard/AgroGuard_Pytorch/dataset/test/images'
    test_label_dir = 'D:/BIRD/year4/FinalYearProject/AgroGuard/AgroGuard_Pytorch/dataset/test/labels'

    trainer = ModelTrainer(data_yaml_path, train_image_dir, train_label_dir, test_image_dir, test_label_dir)
    trainer.train()
    trainer.test()
