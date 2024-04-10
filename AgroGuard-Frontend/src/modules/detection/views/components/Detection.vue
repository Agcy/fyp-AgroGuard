<template>
  <div class="flex gap-6 p-5">
    <div class="flex-1">
      <div class="upload-area border-dashed border-2 border-gray-300 flex justify-center items-center cursor-pointer h-72" @click="triggerFileInput">
        <input type="file" ref="fileInput" @change="previewImage" hidden accept="image/*" />
        <img v-if="imageUrl" :src="imageUrl" alt="Image preview" class="max-w-full max-h-full" />
        <el-icon v-else><Upload /></el-icon>
      </div>
      <el-button class="mt-4" :class="{'bg-green-500': true, 'text-white': true}" @click="detectImage">Start Detection</el-button>
    </div>
    <div class="flex-1">
      <div v-if="detectionResult.title || detectionResult.text" class="result-area p-5">
        <p class="mt-2">{{ detectionResult.text }}</p>
        <h3
          :class="{'text-green-600': detectionResult.title.includes('Healthy'), 'text-red-600': !detectionResult.title.includes('Healthy')}"
          class="text-xl font-semibold"
        >
          {{ detectionResult.title }}
        </h3>
        <p class="mt-2 text-gray-700"><p class="mt-3 text-black text-1.7">Description: </p> {{ detectionResult.description }}</p>
        <p class="mt-2 text-gray-600"><p class="mt-3 text-black text-1.7">Preventive Measures: </p> {{ detectionResult.prevent }}</p>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import { Upload } from '@element-plus/icons-vue';
import {ElButton, ElIcon, ElMessage} from 'element-plus';
import {apiDetection} from "myApi/detect-api/detectionImage";

export default defineComponent({
  name: 'ImageUpload',
  components: {
    ElButton,
    ElIcon,
    Upload,
  },
  setup() {
    const imageUrl = ref<string | null>(null);
    const detectionResult = ref({ title: "", description: '', prevent: '', text: 'please upload your image'});
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
        ElMessage.info("Your Image now Detecting...");
        await apiDetection(formData).then((res) => {
          if (res.status !== 200) {
            ElMessage.warning("HTTP error! status: " + res.status);
            throw new Error(`HTTP error! status: ${res.status}`);
          }

          const data = res.data;
          console.log("检测结果:", data);
          detectionResult.value = {
            title: data.title, // 从后端返回的实际检测结果图片URL
            description: data.description, // 从后端返回的诊断文本
            prevent: data.prevent, // 从后端返回的诊断文本
            text: 'Detection completed.'
          };
        });


      } catch (error) {
        ElMessage.error("Detection failed, please try again later.");
        console.error("检测失败:", error);
        detectionResult.value = {
          title: "Detection failed, please try again later.",
          description: "Detection failed, please try again later.",
          prevent: "Detection failed, please try again later.",
          text: "Detection failed, please try again later.",
        };
      }
    };


    return { imageUrl, detectionResult, triggerFileInput, previewImage, detectImage, fileInput };
  },
});
</script>

<style scoped>
.upload-area {
  //height: 600px;
  //display: flex;
  //justify-content: center;
  //align-items: center;
  cursor: pointer;
}
</style>
