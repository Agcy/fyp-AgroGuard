<template>
  <div class="flex gap-6">
    <div class="flex-1">
      <div class="upload-area border-dashed border-2 border-gray-300 flex justify-center items-center cursor-pointer h-72" @click="triggerFileInput">
        <input type="file" ref="fileInput" @change="previewImage" hidden accept="image/*" />
        <img v-if="imageUrl" :src="imageUrl" alt="Image preview" class="max-w-full max-h-full" />
        <el-icon v-else><Upload /></el-icon>
      </div>
      <el-button class="mt-4" type="primary" @click="detectImage">检测</el-button>
    </div>
    <div class="flex-1">
      <div v-if="detectionResult.imageUrl || detectionResult.text" class="result-area">
        <img v-if="detectionResult.imageUrl" :src="detectionResult.imageUrl" alt="Detection result" class="max-w-full h-auto mb-4" />
        <p v-if="detectionResult.text">{{ detectionResult.text }}</p>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import { Upload } from '@element-plus/icons-vue';
import { ElButton, ElIcon } from 'element-plus';

export default defineComponent({
  name: 'ImageUpload',
  components: {
    ElButton,
    ElIcon,
    Upload,
  },
  setup() {
    const imageUrl = ref<string | null>(null);
    const detectionResult = ref({ imageUrl: null, text: '暂无结果' });
    const fileInput = ref<HTMLInputElement | null>(null);

    const triggerFileInput = () => {
      fileInput.value?.click();
    };

    const previewImage = (event: Event) => {
      const files = (event.target as HTMLInputElement).files;
      if (files && files[0]) {
        const reader = new FileReader();
        reader.onload = (e) => {
          imageUrl.value = e.target?.result as string;
        };
        reader.readAsDataURL(files[0]);
      }
    };

    const detectImage = async () => {
      if (!fileInput.value || !fileInput.value.files || fileInput.value.files.length === 0) {
        console.log("没有选择文件。");
        return;
      }

      const file = fileInput.value.files[0];
      const formData = new FormData();
      formData.append("image", file);

      try {
        console.log("发送图片到后端进行检测...");
        const response = await fetch("/apiDetection", {
          method: "POST",
          body: formData,
        });

        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }

        const data = await response.json();
        detectionResult.value = {
          imageUrl: data.resultImg, // 从后端返回的实际检测结果图片URL
          text: data.diagnosis, // 从后端返回的诊断文本
        };
      } catch (error) {
        console.error("检测失败:", error);
        detectionResult.value = {
          imageUrl: null,
          text: "检测失败，请稍后再试。",
        };
      }
    };


    return { imageUrl, detectionResult, triggerFileInput, previewImage, detectImage, fileInput };
  },
});
</script>

<style scoped>
.upload-area {
  height: 300px;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
}
</style>
