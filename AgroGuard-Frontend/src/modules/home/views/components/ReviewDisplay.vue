<template>
  <div class="reviews">
    <el-input
      v-model="newComment"
      autosize
      clearable
      placeholder="Leave your comment..."
      class="comment-input"
    >
      <template #append>
        <el-button @click="submitComment">Submit</el-button>
      </template>
    </el-input>

    <div class="review-list">
      <div
        v-for="comment in comments"
        :key="comment.id"
        class="review-item"
      >
        <div>
          <el-avatar :src="comment.avatarUrl" class="review-avatar"></el-avatar>
        </div>

        <div class="review-content">
          <div class="review-user">{{ comment.userName }}</div>
          <div class="review-message">{{ comment.content }}</div>
          <div class="review-time">{{ comment.createdAt }}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, onMounted } from 'vue';
import { ElAvatar, ElButton, ElInput } from 'element-plus';
import { Comment } from '../../store/type'; // 假设这是你定义的Review类型
import { useReviewsStore } from 'modules/home/store/store';
import {useRoute} from "vue-router";
import {Search} from "@element-plus/icons-vue";

export default defineComponent({
  name: 'PostReview',
  // computed: {
  //   Search() {
  //     return Search
  //   }
  // },
  components: { ElAvatar, ElButton, ElInput },
  setup() {
    const newComment = ref('');
    const reviewsStore = useReviewsStore();
    const route = useRoute()
    const postId = route.params.postId as string

    onMounted(async () => {
      await reviewsStore.loadReviews(postId);
    });

    const submitComment = async () => {
      if (newComment.value.trim()) {
        await reviewsStore.submitComment(postId,newComment.value);
        newComment.value = ''; // 清空输入框
      }
    };

    return { newComment, comments: reviewsStore.comments, submitComment, Search };
  },
});
</script>

<style scoped>
.reviews {
  margin-top: 20px;
}

.comment-input {
  margin-bottom: 20px;
}

.review-list {
  margin-top: 20px;
}

.review-item {
  display: flex;
  align-items: center;
  margin-bottom: 15px;
}

.review-avatar {
  float: initial;
  margin-right: 10px;
}

.review-content {
  flex: 1;
}

.review-user {
  font-weight: bold;
}

.review-message {
  margin: 5px 0;
}

.review-time {
  font-size: 0.8rem;
  color: #999;
}
</style>
