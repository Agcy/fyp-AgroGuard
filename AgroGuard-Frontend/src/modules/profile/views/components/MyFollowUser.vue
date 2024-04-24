<template>
  <div class="user-list">
    <el-card v-for="user in users" :key="user.id" class="user-card">
      <el-row align="middle">
        <el-col :span="4">
          <el-avatar :src="user.avatarUrl" />
        </el-col>
        <el-col :span="16">
          <h3>{{ user.name }}</h3>
          <p>{{ user.occupation }}</p>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref } from 'vue';
import { apiGetAllFollowing } from 'myApi/user-api/getAllFollowing'; // Adjust the path as needed
import { useState } from "modules/auth/store/state";

export default defineComponent({
  name: 'MyFollowUser',
  setup() {
    const users = ref([]);
    const user = useState().user;

    onMounted(async () => {
      try {
        const response = await apiGetAllFollowing(user?.id as string); // Assume current user ID is known
        users.value = response.data;
      } catch (error) {
        console.error('Failed to fetch following:', error);
      }
    });

    return {
      users
    };
  }
});
</script>

<style scoped>
.user-list {
  display: flex;
  flex-direction: column;
}
.user-card {
  margin-bottom: 1rem;
  padding: 1rem;
}
</style>
