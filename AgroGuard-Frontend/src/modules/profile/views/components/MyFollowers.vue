<template>
  <div class="w-full">
    <el-card class="border-none">
      <template #header>
        <h3 class="cursor-auto mb-0 text-primary-dark">My Followers</h3>
      </template>
      <div class="flex flex-col -my-4">
        <div v-for="user in followers" :key="user.id" class="flex gap-7 items-center py-4">
          <el-avatar :src="user.avatarUrl" class="h-12 w-12 overflow-hidden rounded-full" />
          <div class="flex-auto">
            <h5 class="mb-2">{{ user.name }}</h5>
            <p>{{ user.occupation }}</p>
          </div>
        </div>
      </div>
    </el-card>
  </div>
</template>


<script lang="ts">
import { defineComponent, ref, onMounted } from 'vue';
import { apiGetAllFollowers } from 'myApi/user-api/getAllFollowers'; // Make sure this path is correct
import { useState } from "modules/auth/store/state";

export default defineComponent({
  name: 'FollowersList',
  setup() {
    const user = useState().user;
    const followers = ref([]);

    onMounted(async () => {
      try {
        // Assume current user ID is known or dynamically provided
        const response = await apiGetAllFollowers(user?.id as string);
        followers.value = response.data;
      } catch (error) {
        console.error('Failed to fetch followers:', error);
      }
    });

    return {
      followers
    };
  },
});
</script>

<style scoped>
.el-card {
  margin: 20px;
}

.flex {
  display: flex;
  align-items: center;
}

.gap-7 {
  gap: 28px;
}

.py-4 {
  padding-top: 1rem;
  padding-bottom: 1rem;
}

.h-12, .w-12 {
  height: 3rem;
  width: 3rem;
}

.overflow-hidden {
  overflow: hidden;
}

.rounded-full {
  border-radius: 9999px;
}
</style>

