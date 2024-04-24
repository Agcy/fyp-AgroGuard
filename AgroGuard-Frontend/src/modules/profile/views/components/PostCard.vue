<template>
  <div class="post-card bg-white shadow-md rounded-lg overflow-hidden">
    <router-link :to="`/posts/${post.id}`" class="post-card-link">
      <div v-if="post.base64Image">
        <img :src="post.base64Image[0]" class="post-image" alt="Post Image">
      </div>
      <div class="p-4">
        <h3 class="text-lg font-semibold">{{ post.title }}</h3>
        <p class="text-gray-600">{{ contentPreview }}</p>
      </div>
    </router-link>
    <div class="p-4 flex justify-between items-center">
      <el-avatar :src="post.userAvatar" class="avatar-small" />
<!--      <div>-->
<!--        <el-button type="text" @click="toggleLike(post.id)">-->
<!--          <el-icon>-->
<!--            <HeartIcon :class="{'text-red-500': isLiked}" />-->
<!--          </el-icon>-->
<!--        </el-button>-->
<!--        <span>{{ post.likesCount }}</span>-->
<!--      </div>-->
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, computed, PropType } from 'vue';
import { Post } from '../../store/types'; // Update path as necessary
import HeartIcon from '@heroicons/vue/outline/HeartIcon';

export default defineComponent({
  name: 'PostCard',
  components: {
    HeartIcon,
  },
  props: {
    post: {
      type: Object as PropType<Post>,
      required: true,
    },
  },
  setup(props) {
    const isLiked = computed(() => {
      // Logic to determine if the post is liked
      return false; // Placeholder
    });

    const contentPreview = computed(() => {
      return props.post.content.substring(0, 100) + '...';
    });

    const toggleLike = (postId) => {
      // Logic to toggle like
    };

    return { isLiked, toggleLike, contentPreview };
  },
});
</script>

<style scoped>
.post-card {
  width: 100%;
  max-width: 360px;
}
.post-image {
  width: 100%;
  height: auto;
  object-fit: cover;
}
</style>
