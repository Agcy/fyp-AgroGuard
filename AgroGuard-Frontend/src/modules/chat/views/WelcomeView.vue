<template>
  <div class="wrapper">
<!--    <video autoplay muted loop id="myVideo">-->
<!--      <source src="@/assets/face-678.mp4" type="video/mp4">-->
<!--    </video>-->
<!--    <input-->
<!--      v-model="username"-->
<!--      v-on:keydown="handleKeyDown"-->
<!--      class="input has-background-dark is-primary has-text-white is-large"-->
<!--      placeholder="Enter your name..."-->
<!--    />-->
    <el-button class="button is-primary is-large ml-2" @click="onJoinClicked">Join!</el-button>
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

<style scoped>
.wrapper {
  height: 100vh;
  width: 100vw;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: black;
}

#myVideo {
  position: fixed;
  right: 0;
  bottom: 0;
  min-width: 100%;
  min-height: 100%;
}
.input {
  width: 50vw;
}

.button {
  width: 200px;
}
</style>
