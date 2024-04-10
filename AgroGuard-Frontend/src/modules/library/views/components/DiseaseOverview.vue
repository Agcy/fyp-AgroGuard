<template>
  <div class="posts-masonry">
    <el-card class="post-card bg-yellow-50" :body-style="{ padding: '20px' }">
      <router-link :to="`/library/${disease.id}`" class="post-card-link">
        <img :src="disease.image_url[0]" class="post-image" alt="Type Image">
        <div style="margin-top: 20px;">
          <h3>{{ disease.disease_name }}</h3>
        </div>
      </router-link>
    </el-card>
  </div>
</template>

<script lang="ts">
import {defineComponent, PropType} from 'vue';
import {DiseaseItem} from '../../store/type';

export default defineComponent({
  name: 'DiseaseOverview',
  props: {
    disease: {
      type: Object as PropType<DiseaseItem>,
      required: true,
    },
  },
});
</script>
<style scoped>
.posts-masonry {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr)); /* 创建灵活的列，列宽至少300px */
  gap: 20px; /* 设置网格间距 */
}
.post-card {
  //width: 300px; /* 根据需要调整 */
  margin-bottom: 20px;
}

.post-image {
  width: 100%;
  height: auto;
  border-radius: 4px;
}

.post-card-link {
  text-decoration: none;
  color: inherit;
}

@keyframes scale-up {
  from {
    transform: scale(1.0);
  }
  to {
    transform: scale(1.1);
  }
}

</style>
