<template>
  <div class="detections-container">
    <el-card v-for="detection in detections" :key="detection.id" class="detection-card bg-yellow-50 my-4">
      <DetectionCard :detection="detection" />
    </el-card>
  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref } from 'vue';
import DetectionCard from './DetectionCard.vue'; // Ensure this path is correct
import { apiGetUserDetectionRecord } from 'myApi/detect-api/getDetectionRecordById'; // Adapt this to your actual API call
import {useState} from "modules/auth/store/state";
import {Detection} from "../../store/types";
export default defineComponent({
  name: 'DetectionPage',
  components: {
    DetectionCard
  },
  setup() {
    const user = useState().user;
    const detections = ref([]);

    onMounted(async () => {
      const response = await apiGetUserDetectionRecord(user?.id as string);
      const data = response.data
      data.reverse();
      detections.value = data;
    });

    return {
      detections
    };
  },
});
</script>

<style scoped>
.detections-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
}
</style>
