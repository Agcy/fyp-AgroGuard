<template>
  <div class="detection-card bg-white shadow-md rounded-lg overflow-hidden">
    <div class="image-container">
      <el-image :src="`data:image/jpeg;base64,${detection.sourceImageUrl}`" alt="Detection" class="detection-image"/>
    </div>
    <div class="p-4">
      <h3 class="text-lg font-semibold">{{ detection.detectionResult.title }}</h3>
      <p>{{ detection.gmtDetected }}</p>
<!--      <el-button size="small" @click="reviewDetection(detection.id)">Review</el-button>-->
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, PropType } from 'vue';
import { Detection } from '../../store/types'; // Update path as necessary

export default defineComponent({
  name: 'DetectionCard',
  props: {
    detection: {
      type: Object as PropType<Detection>,
      required: true,
    },
  },
  setup(props) {
    // Use a function within `setup` to handle the review action
    const reviewDetection = (detectionId: string) => {
      // Add your logic to handle the review here
      console.log(`Reviewing detection: ${detectionId}`);
    };

    // Return all reactive properties and functions that need to be accessible in the template
    return {
      reviewDetection
    };
  }
});
</script>

<style scoped>
.detection-card {
  width: 100%;
  max-width: 360px;
}
.detection-image {
  width: 100%;
  height: 200px;
  object-fit: cover;
}
</style>
