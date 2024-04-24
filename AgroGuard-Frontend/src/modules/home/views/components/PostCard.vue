<template>
  <div class="posts-container">
    <el-card class="post-card bg-yellow-50" :body-style="{ padding: '20px' }">
      <router-link :to="`/posts/${post.id}`" class="post-card-link">
        <div v-if="post.base64Image.length > 0">
          <img :src="post.base64Image[0]" class="post-image" alt="Type Image">
        </div>
        <div style="margin-top: 20px;">
          <h3>{{ post.title }}</h3>
          <p>{{ contentPreview }}</p>

        </div>
      </router-link>
      <div class="post-footer">
        <div>
          <el-avatar :src="user?.avatarUrl" class="review-avatar"/>
          {{ post.userName }}
          <el-icon
            @click.stop.prevent="handleLikeClick"
            class="like-button w-6 h-6">
            <component
              :is="HeartIcon"
              :class="['icon', isLiked ? 'bg-red-410 text-red-700' : 'bg-gray-400', scaleClass]"
            />
          </el-icon>
          <!--          <HeartIcon />-->

        </div>
      </div>
    </el-card>
  </div>
</template>

<script lang="ts">
import {defineComponent, PropType, computed, ref, reactive} from 'vue';
import {Post} from '../../store/type'; // 假设这是你的Post接口路径
import {useLikesStore} from "../../store/action";
import {HeartIcon} from '@heroicons/vue/solid'
import {apiLikePost} from "myApi/user-api/likePost";
import { useState } from 'modules/auth/store/state'
import {ElMessage} from "element-plus";
import {apiUnLikePost} from "myApi/user-api/unLikePosts";

export default defineComponent({
  name: 'PostCard',
  props: {
    post: {
      type: Object as PropType<Post>,
      required: true,
    },
  },
  computed: {
    contentPreview(): string {
      // 截取前100个字符作为预览
      return this.post.content.substring(0, 25) + '...';
    },
  },
  setup(props) {
    const user = useState().user;
    // const likesStore = useLikesStore();
    // const likedPosts: string[] = user?.likedPosts as string[] || [];
    const likedPosts = reactive<string[]>(user?.likedPosts || []);

// 计算属性，检查当前帖子是否被喜欢
    let isLiked = computed(() => likedPosts.includes(props.post.id as string));
    // console.log('islike',isLiked.value)
    const scaleClass = ref('');

// 处理点击事件并触发动画效果
    function handleLikeClick() {
      if (!isLiked.value) {
        apiLikePost(props.post.id).then((res) => {
          if (res.status == 200){
            // isLiked.value = true;
            likedPosts.push(props.post.id);
            user.likedPosts = likedPosts;
          } else{
            ElMessage.error('Failed to like post');
          }
        });
      } else {
        apiUnLikePost(props.post.id).then((res) => {
          if (res.status == 200){
            // isLiked.value = false;
            likedPosts.splice(likedPosts.indexOf(props.post.id), 1);
            user?.likedPosts.splice(user?.likedPosts.indexOf(props.post.id), 1);
          } else{
            ElMessage.error('Failed to unlike post');
          }
        });
      }

      scaleClass.value = 'scale-up';
      setTimeout(() => {
        scaleClass.value = '';
      }, 150); // 根据 CSS 动画持续时间设置
    }

    return {handleLikeClick, isLiked, HeartIcon, scaleClass}
  },
});
</script>

<style scoped>
.posts-container {
  display: flex;
  flex-wrap: wrap;
  gap: 20px; /* 根据需要调整，用于设置卡片之间的间距 */
  justify-content: flex-start; /* 根据需要调整，控制如何分布卡片 */
}
.post-card {
  width: 300px; /* 根据需要调整 */
  margin-bottom: 20px;
  height: auto;
}

.post-image {
  width: 100%;
  height: auto;
  border-radius: 4px;
}

.post-footer {
  margin-top: 20px;
  font-size: 0.85rem;
  color: #666;
}

.post-card-link {
  text-decoration: none;
  color: inherit;
}

.like-button {
  float: right;
  background-color: #eff1fc;
}

@keyframes scale-up {
  from {
    transform: scale(1.0);
  }
  to {
    transform: scale(1.1);
  }
}

.icon {
  animation: scale-up 0.15s ease-in-out forwards;
}

</style>
