<template>
  <div
    class="bg-emerald-900 text-1.7 "
    :class="`listing-wrapper ${
      selectedChat?.id == chatInfo?.id ? 'selected' : ''
    }`"
    @click="selectListing"
  >
    <p class="text-yellow-100 font-bold">{{ chatInfo?.name }}</p>
    <div v-if="lastMessage">
      <p class="text-gray-200">
        <span class="text-white font-bold"
          >{{
            lastMessage?.senderName == store.state.user?.username
              ? "You"
              : lastMessage.senderName
          }}:</span
        >
        {{ lastMessage?.message }}
      </p>
    </div>
    <div v-else>
      <p class="is-italic">There is no message to show</p>
    </div>
  </div>
</template>

<script lang="ts" setup>
import type { Group } from "../../../store/types/Group";
import type { Message } from "../../../store/types/Message";
import type { StoreData } from "../../../store/types/StoreData";
import { ref, watch, type Ref, onMounted, defineComponent } from "vue";
import { Store, useStore } from "vuex";
// export default defineComponent({
//
// })
const emit = defineEmits(["select-chat"]);

const props = defineProps({
  chatInfo: Object as () => Group,
  selectedChat: Object as () => Group,
});

const store: Store<StoreData> = useStore();

const lastMessage: Ref<Message | null> = ref(null);

watch(
  () => store.state.messages[props.chatInfo!.id].length,
  () => {
    updateMessage();
  }
);

onMounted(() => {
  updateMessage();
});

const updateMessage = () => {
  const messages = store.state.messages[props.chatInfo!.id];
  if (!messages) {
    lastMessage.value = null;
  } else {
    let i = messages.length - 1;
    while (i >= 0 && messages[i].message == "") i -= 1;
    if (i < 0) {
      lastMessage.value = null;
    } else {
      lastMessage.value = messages[i];
    }
  }
};

const selectListing = () => {
  emit("select-chat", props.chatInfo?.id);
};
</script>

<style scoped>
.listing-wrapper {
  width: 100%;
  height: 70px;
  cursor: pointer;
  border-radius: 5px;
  padding: 10px;
}


</style>
