import os


def clean_dataset(image_dir, label_dir):
    # 遍历图像文件夹
    for image_file in os.listdir(image_dir):
        # 构建图像的完整路径
        image_path = os.path.join(image_dir, image_file)
        # 构建相应的标签文件路径
        label_file = image_file.replace('.jpg', '.txt')
        label_path = os.path.join(label_dir, label_file)

        # 规范化路径
        image_path = os.path.normpath(image_path)
        label_path = os.path.normpath(label_path)

        # 检查标签文件是否存在
        if not os.path.exists(label_path):
            print(f"标签文件不存在，正在删除图像：{image_path}")
            if os.path.exists(image_path):
                try:
                    os.remove(image_path)
                except Exception as e:
                    print(f"删除文件时出错: {e}")
            else:
                print(f"文件不存在，无法删除: {image_path}")
            continue

        # 检查标签文件是否为空
        if os.stat(label_path).st_size == 0:
            print(f"标签文件为空，正在删除图像和标签：{image_path}")
            os.remove(image_path)
            os.remove(label_path)


if __name__ == "__main__":
    # 定义图像和标签的文件夹路径
    image_dir = 'D:/BIRD/year4/FinalYearProject/AgroGuard/AgroGuard_Pytorch/dataset/test/images'
    label_dir = 'D:/BIRD/year4/FinalYearProject/AgroGuard/AgroGuard_Pytorch/dataset/test/labels'
    clean_dataset(image_dir, label_dir)
