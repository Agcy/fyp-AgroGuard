<template>
  <div class="posts-container">
    <el-card v-for="post in posts" :key="post.id" class="post-card bg-yellow-50 my-4">
      <PostCard :post="post" />
    </el-card>
  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref } from 'vue';
import PostCard from './PostCard.vue'; // Ensure this path is correct
import {useState} from "modules/auth/store/state";
import { apiGetUserPosts } from 'myApi/post-api/getUserPost';
import {apiLikePost} from "myApi/user-api/getLikePosts"; // Adapt this to your actual API call

export default defineComponent({
  name: 'FavoritePostsPage',
  components: {
    PostCard
  },
  setup() {
    const user = useState().user;
    const posts = ref([]);

    onMounted(async () => {
      const response = await apiLikePost();
      posts.value = response.data;
    });

    return {
      posts
    };
  },
});
</script>

<style scoped>
.posts-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
}
</style>
