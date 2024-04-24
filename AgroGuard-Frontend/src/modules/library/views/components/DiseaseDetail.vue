<template>
  <el-container class="m-4">
    <el-header>
      <h2>{{ diseaseDetail?.disease_name }}</h2>
    </el-header>
    <el-main>
      <div class="post-images" v-if="diseaseDetail && diseaseDetail.image_url && diseaseDetail.image_url.length">
        <el-carousel trigger="click" arrow="always" :interval="5000">
          <el-carousel-item v-for="(image, index) in diseaseDetail.image_url" :key="index">
            <img :src="image" alt="Post Image" class="post-image"/>
          </el-carousel-item>
        </el-carousel>
      </div>
<!--      <img :src="diseaseDetail?.image_url[0]" alt="Disease Detail Image" class="w-full h-auto">-->
      <p class="mt-4">{{ diseaseDetail?.description }}</p>
      <p class="mt-4">{{ diseaseDetail?.prevent }}</p>
    </el-main>
  </el-container>
</template>

<script lang="ts">
import { ref, defineComponent, computed, onMounted } from 'vue';
import { ElContainer, ElHeader, ElMain } from 'element-plus';
import { DiseaseItem } from 'modules/library/store/type';
import {apiGetFollowStatus} from "myApi/user-api/getFollowStatus";
import {useRoute} from "vue-router";
import {useDiseaseStore} from "modules/library/store/store";
import {ElCarousel, ElCarouselItem} from "element-plus/es";
export default defineComponent({
name: 'DiseaseDetail',
  components: {
    ElCarouselItem, ElCarousel,
    ElContainer,
    ElHeader,
    ElMain
  },
  setup() {
    const store = useDiseaseStore();
    const route = useRoute(); // 使用useRoute获取当前路由实例
    // const diseaseDetail = ref<DiseaseItem>({
    //   id: '',
    //   disease_name: '',
    //   image_url: [],
    //   description: '',
    //   prevent: ''
    // });
    onMounted(async () => {
      const id = route.params.diseaseId as string;
      await store.loadDiseaseDetail(id); // 加载帖子详情
    });
    const diseaseDetail = computed(() => store.currentDisease)
    return {
      diseaseDetail
    };
  }
});
// 假设diseaseDetail是通过路由参数或某种方式获取的病情详情数据

</script>
<style scoped>
.post-images {
  max-width: 600px; /* Adjust based on your needs */
  margin: auto;
}
.post-image {
  width: 100%;
  height: auto;
  display: block;
}
</style>
