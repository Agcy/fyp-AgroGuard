<template>
  <nav class="bg-green-100 p-4 shadow-md" aria-label="main navigation">
    <div class="flex justify-between items-center">
      <div class="flex items-center">
        <!-- Using Element Plus ElLink for navigation links -->
        <el-link :underline="false" type="primary" class="text-xl text-green-800" href="/public">Chat Room</el-link>

        <!-- Burger Icon for mobile menu -->
        <button @click="toggleNav" :class="{'is-active': showNav}" class="ml-4 navbar-burger focus:outline-none lg:hidden">
          <span class="block w-8 h-0.5 bg-gray-600 mb-1"></span>
          <span class="block w-8 h-0.5 bg-gray-600 mb-1"></span>
          <span class="block w-8 h-0.5 bg-gray-600"></span>
        </button>
      </div>

      <!-- Navbar Menu -->
      <div :class="{'flex': showNav, 'hidden': !showNav}" class="navbar-menu lg:flex lg:items-center">
        <p class="text-green-700 mx-4 my-2">
          Hi <span class="text-green-900 font-bold">{{ user.username }}</span>!
        </p>
<!--        <el-button type="danger" size="small" href="/public">Log out</el-button>-->
      </div>
    </div>
  </nav>
</template>

<script lang="ts">
import type { StoreData } from "../../store/types/StoreData";
import {computed, defineComponent, ref } from "vue";
import { Store, useStore } from "vuex";
export default defineComponent({
  setup() {
    const store: Store<StoreData> = useStore();
    const user = computed(() => store.state.user);
    const showNav = ref(false);

    const toggleNav = () => {
      showNav.value = !showNav.value;
    };

    return { showNav, user, toggleNav };
  }

});

</script>

<style scoped>
.navbar-burger.is-active span {
  transform: rotate(45deg);
  position: relative;
}
.navbar-burger.is-active span:nth-child(2) {
  opacity: 0;
}
.navbar-burger.is-active span:nth-child(3) {
  transform: rotate(-45deg);
  position: relative;
  top: -10px;
}

</style>
