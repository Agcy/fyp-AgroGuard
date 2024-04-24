// likesStore.ts
import { defineStore } from 'pinia';
import { apiAddToFavorite } from 'myApi/user-api/addToLike';
import { useState } from 'modules/auth/store/state'

export const useLikesStore = defineStore('likes', {
  state: () => ({
    likedPosts: new Set<string>(),
  }),
  actions: {
    async toggleLike(postId: string) {
      try {
        const response = await apiAddToFavorite(postId);
        if (response && response.status === 200) {
          if (this.likedPosts.has(postId)) {
            this.likedPosts.delete(postId);
          } else {
            this.likedPosts.add(postId);
          }
        }
      } catch (error) {
        console.error('Error toggling like:', error);
      }
    },
  },
});
