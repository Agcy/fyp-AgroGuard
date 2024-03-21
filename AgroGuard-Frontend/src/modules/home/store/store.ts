// postStore.ts
import { defineStore } from 'pinia';
import {Post, Comment, CreatePostPayload} from './type';
import { apiGetSinglePost } from 'myApi/post-api/getPost-api'; // 确保路径正确
import {apiGetPostComment} from 'myApi/post-api/getPostComment-api'
import {apiAddNewComment} from "myApi/post-api/postComment-api";
import {ElMessage} from "element-plus";
import {apiAddNewPost} from "myApi/post-api/addPost";

export const usePostStore = defineStore('post', {
  state: () => ({
    posts: [] as Post[],
    comments: [] as Comment[],
    currentPost: null as Post | null,
  }),
  actions: {
    // 假设已经有了apiGetSinglePost的定义
    async loadPostDetail(postId: string) {
      try {
        const [postDetailResponse, commentsResponse] = await Promise.all([
          apiGetSinglePost(postId),
          apiGetPostComment(postId),
        ]);
        this.currentPost = postDetailResponse.data;
        this.comments = commentsResponse.data;
      } catch (error) {
        console.error("Error loading post and comments:", error);
        // 处理错误，比如设置一个错误状态
      }
    },
    // 其他操作...
    async submitComment(postId: string, comment: string) {
      try {
        await apiAddNewComment(postId, comment); // 提交评论的API调用
        await this.loadPostDetail(postId); // 重新加载评论以更新列表
      } catch (error) {
        console.error('Error submitting comment:', error);
      }
    }
  },
});

export const useReviewsStore = defineStore('comments', {
  state: () => ({
    comments: [] as Comment[],
  }),
  actions: {
    async loadReviews(postId: string) {
      try {
        const loadedReviews = await apiGetPostComment(postId); // 加载评论列表的API调用
        this.comments = loadedReviews.data;
      } catch (error) {
        console.error('Error loading reviews:', error);
      }
    },
    async submitComment(postId: string, comment: string) {
      try {
        await apiAddNewComment(postId, comment); // 提交评论的API调用
        await this.loadReviews(postId); // 重新加载评论以更新列表
      } catch (error) {
        console.error('Error submitting comment:', error);
      }
    }
  },
});

export const usePostsStore = defineStore('posts', {
  state: () => ({
    posts: [] as Post[],
    // 可能还需要其他与帖子相关的状态
  }),
  actions: {
    async addPost(payload: CreatePostPayload) {
      try {
        await apiAddNewPost(payload.title, payload.content, payload.base64Imgs);
        console.log('Adding post:', payload);
        // 假设上传成功后，你可能想更新posts列表或执行其他操作
        ElMessage.success('帖子已添加');
      } catch (error) {
        console.error('Error adding post:', error);
        ElMessage.error('添加帖子失败');
      }
    },
    // 这里可以包含转换图片为Base64的逻辑或任何与帖子相关的操作
  },
});
