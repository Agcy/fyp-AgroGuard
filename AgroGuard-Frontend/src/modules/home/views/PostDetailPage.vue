<script lang="ts">
import PostReview from './components/ReviewDisplay.vue'
import PostDetail from './components/PostDetail.vue'
import CreatePost from './components/CreatePost.vue'
import {defineComponent} from "vue";

export default defineComponent({
  name: "PostDetailPage",
  components: {PostDetail, PostReview, CreatePost}
})
</script>

<template>

  <div>
    <div>
      <PostDetail/>
    </div>
  </div>

</template>

<style scoped>

</style>
