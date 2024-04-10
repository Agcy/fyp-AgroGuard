import {defineStore} from "pinia";
import {DiseaseItem} from "modules/library/store/type";
import {apiGetSinglePost} from "myApi/post-api/getPost-api";
import {apiGetPostComment} from "myApi/post-api/getPostComment-api";
import {apiAddNewComment} from "myApi/post-api/postComment-api";
import {apiGetDiseaseById} from "myApi/disease-api/getDiseaseById";

export const useDiseaseStore = defineStore('disease', {
  state: () => ({
    diseases: [] as DiseaseItem[],
    currentDisease: null as DiseaseItem | null,
  }),
  actions: {
    // 假设已经有了apiGetSinglePost的定义
    async loadDiseaseDetail(id: string) {
      try {
        await apiGetDiseaseById(id).then((response) => {
          this.currentDisease = response.data;
        });
      } catch (error) {
        console.error("Error loading post and comments:", error);
        // 处理错误，比如设置一个错误状态
      }
    },
  },
});
