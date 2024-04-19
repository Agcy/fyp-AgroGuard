<template>
  <div class="chat-header">
    <p>
      <span class="text-white font-bold">{{ chatName }}</span>
      <span class="text-white"> | {{ members }} members</span>
    </p>
    <font-awesome-icon
      class="icon has-text-primary"
      icon="fa-solid fa-ellipsis"
      inverse
    />
  </div>
</template>

<script lang="ts" setup>
defineProps({
  chatName: String,
  members: Number,
});
</script>

<style scoped>

</style>
