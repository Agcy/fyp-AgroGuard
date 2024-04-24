<template>
  <div class="min-h-screen bg-cover bg-center background-url">
    <div class=" bg-opacity-50 min-h-screen flex items-center justify-center">
      <div class="flex items-center justify-center min-h-screen">
        <div class="bg-yellow-100 bg-opacity-50 backdrop-filter backdrop-blur-lg p-8 rounded-lg shadow-lg items-center">
          <p class="text-lg text-gray-700 mb-4">Welcome to AgroGuard Chat Room!</p>
          <p class="text-sm text-gray-600 mb-6">Click 'Join' to Enter the Chat.</p>
          <el-button
            class="text-lg font-bold py-3 px-6 rounded bg-green-900
            hover:bg-green-800 focus:outline-none focus:ring-4 focus:ring-green-500
            focus:ring-opacity-50"
            @click="onJoinClicked">
            Join!
          </el-button>
        </div>
      </div>    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import router from "router";
import { notify } from "@kyvg/vue3-notification";
import { Store, useStore } from "vuex";
import type { StoreData } from "../store/types/StoreData";
import { registerUser } from "../store/User";
import {useState} from "modules/auth/store/state"
const username = ref("");
const userStore = useState()
const user = userStore.user
const store: Store<StoreData> = useStore();

const onJoinClicked = async () => {
  // if (username.value.replace(" ", "").length == 0) {
  //   notify({
  //     title: "Please enter your username",
  //     type: "error",
  //   });
  // } else {
    try {
      username.value = user?.username as string
      await registerUser(store, username.value);
      router.push("/chat");
    } catch (e: any) {
      console.log(e);
      notify({
        title: "A user with the same username is already in the chat :(",
        type: "error",
      });
    }
  // }
};

const handleKeyDown = ($event: any) => {
  if ($event.key == "Enter") {
    onJoinClicked();
  }
};

onMounted(() => {
  console.log(store.state);
});
</script>

<style lang="scss" scoped>
.background-url {
  @apply bg-[url('@/assets/images/farming-agriculture.jpg')] #{!important};
}
</style>
