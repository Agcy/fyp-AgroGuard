<template>
  <div class="wrapper bg-green-100 min-h-screen p-2">
    <ChatHeader
      class="h-12 bg-green-800 text-white flex items-center px-4"
      :chat-name="selectedChat?.name"
      :members="selectedChat?.members.length || 0"
    ></ChatHeader>
    <ChatContent
      class="chat-content flex-grow overflow-auto p-2"
      :messages="store.state.messages[props.selectedChat!.id]"
    ></ChatContent>
    <ChatFooter
      class="h-16 bg-green-950 text-white items-center px-4"
      @new-message="onSendMessage"
    ></ChatFooter>
  </div>
</template>

<script lang="ts" setup>
import ChatHeader from "./ChatHeader.vue";
import ChatFooter from "./ChatFooter.vue";
import ChatContent from "./ChatContent/ChatContent.vue";
import type { Message } from "../../../store/types/Message";
import { Store, useStore } from "vuex";
import type { StoreData } from "../../../store/types/StoreData";
import { sendMessage } from "../../../store/Message";
import type { Group } from "../../../store/types/Group";
import { Status } from "../../../store/types/Status";

const store: Store<StoreData> = useStore();

const props = defineProps({
  selectedChat: Object as () => Group,
});

console.log("Size", props.selectedChat?.members.length);

const onSendMessage = ($event: any) => {
  const senderName = store.state.user!.username;
  const receiverId = props.selectedChat!.id;
  const status = Status.MESSAGE;
  const date = new Date().toString();
  const message: Message = {
    senderName: senderName,
    message: $event,
    receiverId: receiverId,
    status: status,
    date: date,
  };

  sendMessage(store, message);
};
</script>

<style scoped>
.wrapper {
  //background-color: #282828;
  width: 100%;
}

.chat-content {
  height: calc(76.5%);
  overflow-y: scroll;
  width: 100%;
}
</style>
