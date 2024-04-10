<template>
  <el-container class="m-4">
    <el-header>
      <h2>{{ diseaseDetail?.disease_name }}</h2>
    </el-header>
    <el-main>
      <img :src="diseaseDetail?.image_url" alt="Disease Detail Image" class="w-full h-auto">
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
export default defineComponent({
name: 'DiseaseDetail',
  components: {
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
