<template>

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
      <div><el-avatar :src="user?.avatarUrl" class="review-avatar"/>{{ post.userName }}
        <el-icon
          @click.stop.prevent="handleLikeClick"
          class="like-button w-6 h-6">
          <component
          :is="HeartIcon"
          :class="['icon', isLiked ? 'text-red-500' : 'text-gray-400', scaleClass]"
          />
        </el-icon>
<!--          <HeartIcon />-->

      </div>
    </div>
  </el-card>

</template>

<script lang="ts">
import {defineComponent, PropType, computed, ref} from 'vue';
import {Post} from '../../store/type'; // 假设这是你的Post接口路径
import {useLikesStore} from "../../store/action";
import {HeartIcon} from '@heroicons/vue/solid'

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
    const likesStore = useLikesStore();

// 计算属性，检查当前帖子是否被喜欢
    const isLiked = computed(() => likesStore.likedPosts.has(props.post.id));

    const scaleClass = ref('');

// 处理点击事件并触发动画效果
    function handleLikeClick() {
      likesStore.toggleLike(props.post.id);
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
.post-card {
  width: 300px; /* 根据需要调整 */
  margin-bottom: 20px;
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
