<template>
  <div class="post-detail">
    <div class="header">
      <el-avatar :src="post?.userAvatar" class="review-avatar"></el-avatar>
      <span>{{ post?.userName }}</span>
      <el-button
        round
        class="bg-green-100"
        :class="isFollowing ? '.following' : '.follow'"
        @click="toggleFollow"
      >
        {{ isFollowing ? 'Following' : 'Follow' }}
      </el-button>
      <el-icon @click="createChatRoom"><ChatDotRound /></el-icon>
    </div>
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
import {ElAvatar, ElButton, ElInput, ElCarousel, ElCarouselItem, ElMessage} from "element-plus";
import {useState} from 'modules/auth/store/state'
import { apiGetFollowStatus } from 'myApi/user-api/getFollowStatus';
import { apiAddToFollow } from 'myApi/user-api/addToFollow';
import { apiUnFollow } from 'myApi/user-api/unFollow';
import {ChatDotRound} from "@element-plus/icons-vue";
import { apiCreateChatRoom } from 'myApi/chat-api/createChatRoom';
import {useChatStore} from 'modules/chat/store/store';
import router from 'router';

export default defineComponent({
  name: 'PostDetail',
  components: {ChatDotRound, ElButton, ElAvatar, ElInput, PostReview, ElCarousel, ElCarouselItem},
  // props: {
  //   post: {
  //     type: Object,
  //     required: true,
  //   },
  // },
  setup() {
    const isFollowing = ref<boolean>(false);
    const newComment = ref('');
    const postStore = usePostStore();
    const route = useRoute(); // 使用useRoute获取当前路由实例
    const store = useState();
    const chatStore = useChatStore();
    const user = computed(() => store.user)
    const currentChatRoom = {
      id: '',
      name: '',
      participantsId: []
    };


    onMounted(async () => {
      const postId = route.params.postId as string;
      await postStore.loadPostDetail(postId); // 加载帖子详情
      isFollowing.value = await apiGetFollowStatus(user.value?.id, post.value?.userId).then(res => res.data);
    });

// 使用计算属性来引用store中的currentPost，这样可以保证它是响应式的
    const post = computed(() => postStore.currentPost);
    const comments = computed(() => postStore.comments)

    function formatDate(date: string) {
      // 格式化日期的函数
      return new Date(date).toLocaleDateString();
    }

    const createChatRoom = async () => {
      if (user.value?.id === post.value?.userId) {
        ElMessage.error('You cannot chat with yourself');
        return;
      }
      const participantIds = [user.value?.id as string, post.value?.userId as string];
      const name = `${user.value?.username} and ${post.value?.userName}'s chat room`;

      try {
        await apiCreateChatRoom(name, participantIds).then(res => {
          if (res.status == 200) {
            currentChatRoom.id = res.data.id;
            currentChatRoom.name = res.data.name;
            currentChatRoom.participantsId = res.data.participants;
            chatStore.fetchChatRooms(currentChatRoom);
            ElMessage.success('Chat room created successfully');

          }else{
            ElMessage.error('Failed to create chat room');
          }
        });
        await router.push('/chat');
      } catch (error) {
        console.error("Failed to navigate:", error);
      }
    };

    const submitComment = async () => {
      if (newComment.value.trim()) {
        await postStore.submitComment(post.value?.id as string, newComment.value);
        newComment.value = ''; // 清空输入框
      }
    };
    const toggleFollow = async () => {
      if (user.value?.id === post.value?.userId) {
        ElMessage.error('You cannot follow yourself');
        return;
      }
      // 假设 apiSetFollowStatus 函数接受两个参数：被关注用户的 ID 和新的关注状态
      if (isFollowing.value == false) {
        // 取消关注
        await apiAddToFollow(user.value?.id, post.value?.userId);
        isFollowing.value = true;
      } else {
        // 关注
        await apiUnFollow(user.value?.id, post.value?.userId);
        isFollowing.value = false;
      }
    };

    return {post, formatDate, submitComment, newComment, comments, user, isFollowing, toggleFollow, createChatRoom};
  },
});
</script>

<style scoped>
.follow {
  color: palegreen;
  border-color: green;
}

.following {
  color: lightslategray;
  border-color: green;
  background-color: #f0f9f4;
}

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
