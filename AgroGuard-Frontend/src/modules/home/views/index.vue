<template>
  <div class="posts-container mt-7 p-6">
    <PostCard
      v-for="post in posts"
      :key="post.id"
      :post="post"
    />
  </div>
  <div>
    <CreatePost/>
  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref } from 'vue';
import PostCard from './components/PostCard.vue'; // 确保路径正确
import { apiGetAllPost } from 'myApi/post-api/getall-api'; // 更新为正确的导入路径
import { Post } from '../store/type';
import CreatePost from "modules/home/views/components/CreatePost.vue"; // 更新为正确的导入路径

export default defineComponent({
  name: 'PostsList',
  components: {
    CreatePost,
    PostCard,
  },
  setup() {
    const posts = ref<Post[]>([]);

    onMounted(async () => {
      try {
        const response = await apiGetAllPost();
        posts.value = response.data; // 假设响应的数据直接是帖子数组
      } catch (error) {
        console.error("Failed to fetch posts:", error);
        // 这里可以添加错误处理逻辑
      }
    });

    return {
      posts,
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
