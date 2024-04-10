import os
from flask import Flask, request, jsonify  # 导入jsonify
from PIL import Image
import torchvision.transforms.functional as TF
import utils.CNN as CNN
import numpy as np
import torch
import pandas as pd

# 加载疾病信息和补充信息
disease_info = pd.read_csv('cfg/csv/disease_info.csv', encoding='cp1252')
supplement_info = pd.read_csv('cfg/csv/supplement_info.csv', encoding='cp1252')

# 初始化模型并加载预训练权重
model = CNN.CNN(39)
model.load_state_dict(torch.load("models/hybrid/logs/plant_disease_model_1_latest.pt"))
model.eval()


# 定义预测函数
def prediction(image_path):
    image = Image.open(image_path)
    image = image.resize((224, 224))
    input_data = TF.to_tensor(image)
    input_data = input_data.view((-1, 3, 224, 224))
    output = model(input_data)
    output = output.detach().numpy()
    print(output)
    index = np.argmax(output)
    return int(index)  # 确保将numpy的类型转换为Python原生类型


app = Flask(__name__)


@app.route('/submit', methods=['POST'])  # 只需要POST方法
def submit():
    if request.method == 'POST':
        image = request.files['image']
        filename = image.filename
        file_path = os.path.join('static/uploads', filename)
        image.save(file_path)

        pred = prediction(file_path)
        response_data = {
            "title": disease_info['disease_name'][pred],  # 使用.item()转换为Python原生类型
            "description": disease_info['description'][pred],
            "prevent": disease_info['prevent'][pred],
            "image_url": disease_info['image_url'][pred],
            "pred": pred,
            "supplement_name": supplement_info['supplement name'][pred],
            "supplement_image_url": supplement_info['supplement image'][pred],
            "supplement_buy_link": supplement_info['buy link'][pred]
        }

        # 使用jsonify确保返回的是JSON响应
        return jsonify(response_data)


if __name__ == '__main__':
    app.run(debug=True)
