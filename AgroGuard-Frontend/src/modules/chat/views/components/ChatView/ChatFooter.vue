<template>
  <div class="footer-wrapper bg-gray-800 border-t border-green-700 py-2">
    <div class="icon-text flex w-full px-4">
      <span class="input-wrapper flex-grow">
        <input
          v-model="message"
          @keydown="handleKeyDown"
          class="input bg-gray-700 text-white rounded-full px-4 py-2 w-full"
          placeholder="Enter your chat..."
        />
      </span>
      <span class="icon-wrapper flex items-center justify-center ml-2">
        <font-awesome-icon
          @click="sendMessage"
          class="icon text-green-500 hover:text-green-300 cursor-pointer"
          icon="fa-solid fa-paper-plane"
        />
      </span>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { type Ref, ref } from "vue";

const emit = defineEmits(["new-message"]);

const message: Ref<String> = ref("");

const sendMessage = () => {
  if (message.value != "") {
    emit("new-message", message.value);
    message.value = "";
  }
};

const handleKeyDown = ($event: any) => {
  if ($event.key == "Enter") {
    sendMessage();
  }
};
</script>

<style scoped>

</style>
