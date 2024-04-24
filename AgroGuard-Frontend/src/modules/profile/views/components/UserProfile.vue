<template>
  <div class="w-full">
    <el-card class="items-center border-white">
      <template #header>
        <div class="flex justify-between py-2">
          <h3>Edit Profile</h3>
          <el-button class="text-sky-700" size="small" type="primary">Settings</el-button>
        </div>
      </template>

      <!-- Tabbed Interface -->
      <el-tabs v-model="activeTab" @tab-click="handleTabClick">
        <el-tab-pane label="My Posts" name="my-posts"></el-tab-pane>
        <el-tab-pane label="My Detection" name="my-detection"></el-tab-pane>
        <el-tab-pane label="My Favorite Posts" name="my-favorite-posts"></el-tab-pane>
        <el-tab-pane label="My Follow User" name="my-follow-user"></el-tab-pane>
        <el-tab-pane label="My Friends" name="my-friends"></el-tab-pane>
      </el-tabs>

      <!-- Content based on active tab -->
      <component :is="getComponent(activeTab)" />

    </el-card>
  </div>
</template>

<script>
import { defineComponent, ref } from 'vue'
import PostsPage from 'modules/profile/views/components/MyPosts.vue'
import DetectionPage from './MyDetection.vue'
import MyFavoritePosts from './MyFavoritePosts.vue'
import MyFollowUser from './MyFollowUser.vue'
import MyFriends from './Friends.vue'

export default defineComponent({
  name: 'EditProfileForm',
  components: {
    PostsPage,
    DetectionPage,
    MyFavoritePosts,
    MyFollowUser,
    MyFriends
  },
  setup() {
    const activeTab = ref('my-posts');

    function handleTabClick(tab) {
      // You can also add router.push or replace here if each tab corresponds to a route
      console.log('Switching to tab:', tab.name);
    }

    function getComponent(tabName) {
      switch(tabName) {
        case 'my-posts': return PostsPage;
        case 'my-detection': return DetectionPage;
        case 'my-favorite-posts': return MyFavoritePosts;
        case 'my-follow-user': return MyFollowUser;
        case 'my-friends': return MyFriends;
        default: return null;
      }
    }

    return {
      activeTab,
      handleTabClick,
      getComponent
    }
  },
})
</script>
