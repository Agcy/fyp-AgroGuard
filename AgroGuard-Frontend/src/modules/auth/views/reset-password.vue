<template>
  <div v-if="!isAuthenticated">
    <div
      class="relative w-full bg-gradient-to-r from-indigo-410 to-indigo-450 py-24 lg:py-32 lg:pt-40"
    >
      <div
        class="container xl:max-w-5.75xl lg:max-w-4.5xl md:max-w-2.625xl sm:max-w-0.25xl w-full mx-auto px-3.75"
      >
        <div class="text-center mb-12">
          <div class="flex flex-wrap -mx-3.75 justify-center px-3.75">
            <div class="md:flex-9 md:max-w-9/12 lg:flex-8 lg:max-w-2/3">
              <WelcomeLabel />
            </div>
          </div>
        </div>
      </div>

      <SplitBackground />
    </div>
    <div
      class="container relative xl:max-w-5.75xl lg:max-w-4.5xl md:max-w-2.625xl sm:max-w-0.25xl w-full mx-auto px-4"
    >
      <div class="relative lg:max-w-5/12 md:max-w-7/12 mx-auto px-4 -mt-32 mb-20">
        <ResetPasswordForm :reset-token="resetToken"/>
      </div>
    </div>
  </div>
</template>
<script lang="ts">
import { defineComponent, computed } from 'vue'
import useStore from 'store'
import SplitBackground from './components/SplitBackground.vue'
import ResetPasswordForm from './components/ResetPasswordForm.vue'
import WelcomeLabel from './components/WelcomeLabel.vue'
import { useRoute } from 'vue-router'

export default defineComponent({
  components: {
    ResetPasswordForm,
    SplitBackground,
    WelcomeLabel,
  },
  setup() {
    const store = useStore()
    const isAuthenticated = computed<boolean>(() => store.auth.getAuthenticationState)

    const route = useRoute();
    const resetToken = computed(() => route.query.token?.toString() ?? '');
    return {
      isAuthenticated,
      resetToken,
    }
  },
})
</script>
