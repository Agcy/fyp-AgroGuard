<template>
  <div class="post-detail">
    <div class="post-images" v-if="post && post.base64Image && post.base64Image.length">
      <el-carousel trigger="click" arrow="always" :interval="5000">
        <el-carousel-item v-for="(image, index) in post.base64Image" :key="index">
          <img :src="image" alt="Post Image" class="post-image"/>
        </el-carousel-item>
      </el-carousel>
    </div>
    <h1>{{ post?.title }}</h1>

    <div v-html="post?.content" class="post-content"></div>
    <p class="post-date">By {{ post?.userName }} on {{ formatDate(post?.updatedAt) }}</p>
  </div>
  <el-divider/>
  <div>
    <div class="review">
      <div class="review-row">
        <div>
          <el-avatar :src="user?.avatarUrl" class="review-avatar"></el-avatar>
        </div>
        <div class="review-content">
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
        </div>
      </div>
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
            <div class="review-time">{{ formatDate(comment?.createdAt) }}</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import {defineComponent, onMounted, computed, ref} from 'vue';
// 假设有一个API函数来获取帖子详情
import {apiGetSinglePost} from 'myApi/post-api/getPost-api';
import {useRoute} from 'vue-router';
import {Post} from "modules/home/store/type";
import {usePostStore} from '../../store/store';
import PostReview from "modules/home/views/components/ReviewDisplay.vue";
import {ElAvatar, ElButton, ElInput, ElCarousel, ElCarouselItem} from "element-plus";
import {useState} from '../../../auth/store/state'

export default defineComponent({
  name: 'PostDetail',
  components: {ElButton, ElAvatar, ElInput, PostReview, ElCarousel, ElCarouselItem},
  // props: {
  //   post: {
  //     type: Object,
  //     required: true,
  //   },
  // },
  setup() {
    const newComment = ref('');
    const postStore = usePostStore();
    const route = useRoute(); // 使用useRoute获取当前路由实例
    const store = useState()
    const user = computed(() => store.user)


    onMounted(async () => {
      const postId = route.params.postId as string;
      await postStore.loadPostDetail(postId); // 加载帖子详情
    });

// 使用计算属性来引用store中的currentPost，这样可以保证它是响应式的
    const post = computed(() => postStore.currentPost);
    const comments = computed(() => postStore.comments)

    function formatDate(date: string) {
      // 格式化日期的函数
      return new Date(date).toLocaleDateString();
    }

    const submitComment = async () => {
      if (newComment.value.trim()) {
        await postStore.submitComment(post.value?.id as string, newComment.value);
        newComment.value = ''; // 清空输入框
      }
    };

    return {post, formatDate, submitComment, newComment, comments, user};
  },
});
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

.post-detail {
  /* 样式 */
}

.post-image {
  max-width: 100%;
  height: auto;
}

.post-content {
  /* 样式 */
  padding-top: 30px;
}

.post-date {
  padding-top: 50px;
}

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

.review-row {
  display: flex;
  align-items: center; /* 垂直居中 */
  gap: 10px; /* 为头像和评论输入框之间添加一些间隔 */
}
</style>
