<template>
  <div class="wrapper bg-green-800">
    <ListingHeader
      class="w-full"
      @create-group="onCreateGroup"
    ></ListingHeader>
    <div class="modals-wrapper overflow-y-auto h-full">
      <ListingModal
        v-for="group in Object.values(store.state.groupById)"
        :key="group.id"
        :chat-info="group as Group"
        :selected-chat="selectedChat"
        @select-chat="selectListing"
        class="mb-2"
      ></ListingModal>
    </div>
  </div>
</template>

<script lang="ts" setup>
import ListingModal from "./ListingModal.vue";
import ListingHeader from "./ListingHeader.vue";
import { useStore, type Store } from "vuex";
import type { StoreData } from "../../../store/types/StoreData";
import type { Group } from "../../../store/types/Group";

const store: Store<StoreData> = useStore();

defineProps({
  selectedChat: Object as () => Group,
});

const emit = defineEmits(["select-chat", "create-group"]);

const selectListing = (username: string) => {
  emit("select-chat", username);
};

const onCreateGroup = (members: string[]) => {
  emit("create-group", members);
};
</script>

<style scoped>

</style>
