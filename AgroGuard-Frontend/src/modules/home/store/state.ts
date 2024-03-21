import { defineStore } from 'pinia';

export const useLikesStore = defineStore('likes', {
  state: () => ({
    likedPosts: new Set<string>(), // 存储已喜欢的帖子 ID
  }),
  actions: {
    toggleLike(postId: string) {
      if (this.likedPosts.has(postId)) {
        this.likedPosts.delete(postId);
      } else {
        this.likedPosts.add(postId);
      }
      // 这里不直接处理动画效果，动画效果将在组件中使用 CSS 实现
    },
  },
});
