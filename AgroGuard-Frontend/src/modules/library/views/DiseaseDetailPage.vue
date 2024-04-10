<script lang="ts">
import { ref, defineComponent, computed, onMounted } from 'vue';
import DiseaseDetail from "modules/library/views/components/DiseaseDetail.vue"; // 更新为正确的导入路径
export default defineComponent({
  name: 'PostsList',
  components: {
    DiseaseDetail,
  },
});
</script>

<template>
  <div>
    <DiseaseDetail/>
  </div>

</template>

<style scoped>

</style>
