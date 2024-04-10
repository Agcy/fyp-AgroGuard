<template>
  <div class="posts-container mt-7 p-6">
    <DiseaseOverview
      v-for="disease in diseaseItems"
      :key="disease.id"
      :disease="disease"/>
  </div>
  <div>
  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref } from 'vue';
import { DiseaseItem } from '../store/type';
import {apiGetAllDisease} from "myApi/disease-api/getAllDiseases";
import DiseaseOverview from "modules/library/views/components/DiseaseOverview.vue"; // 更新为正确的导入路径

export default defineComponent({
  name: 'PostsList',
  components: {
    DiseaseOverview,
  },
  setup() {
    const diseaseItems = ref<DiseaseItem[]>([]);

    onMounted(async () => {
      try {
        const response = await apiGetAllDisease();
        diseaseItems.value = response.data; // 假设响应的数据直接是帖子数组
        console.log(diseaseItems.value[0].image_url);
      } catch (error) {
        console.error("Failed to fetch diseases:", error);
        // 这里可以添加错误处理逻辑
      }
    });

    return {
      diseaseItems,
    };
  },
});
</script>

<style scoped>
::v-deep(.card-avatar-profile.el-link) .el-link--inner {
  @apply absolute;
}
.posts-container {
  display: flex;
  flex-wrap: wrap;
  gap: 20px; /* 根据需要调整 */
  justify-content: left;
}
</style>
