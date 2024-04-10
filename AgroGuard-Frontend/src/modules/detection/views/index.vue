<script lang="ts">
import ImageUpload from './components/Detection.vue'
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'Detection',
  components: {
    ImageUpload
  },
});

</script>

<template>
  <div>
    <ImageUpload />
  </div>

</template>

<style scoped>

</style>
