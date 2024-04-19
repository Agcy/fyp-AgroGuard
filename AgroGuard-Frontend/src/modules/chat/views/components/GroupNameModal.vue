<template>
  <div class="modal-wrapper bg-black bg-opacity-50 fixed inset-0 flex items-center justify-center">
    <div class="popup-modal bg-gray-800 text-white rounded-lg p-6 border border-green-700 relative">
      <div @click="closeModal" class="close-icon absolute top-2 right-2 cursor-pointer">
        <font-awesome-icon
          icon="fa-solid fa-times"
          class="text-gray-400 hover:text-white"
        />
      </div>

      <p class="text-center text-lg font-medium">New Group</p>
      <el-input
        v-model="groupName"
        class="input bg-gray-900 border border-gray-700 mt-4 w-full rounded p-2 text-white"
        placeholder="Enter group's name..."
      />
      <div class="flex justify-center space-x-4 mt-6">
        <el-button
          class="bg-gray-900 hover:bg-gray-700 text-white py-2 px-4 rounded"
          @click="closeModal"
        >
          Cancel
        </el-button>
        <el-button class="bg-green-600 hover:bg-green-700 py-2 px-4 rounded" @click="createGroup">
          Create
        </el-button>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import type { StoreData } from "../../store/types/StoreData";
import { addGroup } from "../../store/Group";
import { notify } from "@kyvg/vue3-notification";
import { ref, type Ref } from "vue";
import { useStore, type Store } from "vuex";

const store: Store<StoreData> = useStore();

const emits = defineEmits(["close", "selectChat"]);

const props = defineProps({
  members: Array<string>,
});

const groupName: Ref<string> = ref("");

const closeModal = () => {
  emits("close");
};

const createGroup = async () => {
  if (groupName.value.replace(" ", "").length != 0) {
    const id = await addGroup(props.members!, groupName.value);
    store.state.messages[id] = [];
    emits("selectChat", id);
    closeModal();
  } else {
    notify({
      title: `Invalid group's name!`,
      type: "error",
    });
  }
};
</script>

<style scoped>

</style>
