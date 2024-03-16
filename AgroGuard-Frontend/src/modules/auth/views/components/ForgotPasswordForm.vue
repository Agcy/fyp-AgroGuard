<template>
  <div class="w-full">
    <el-card class="bg-secondary text-center">
      <div class="content-center items-center w-full md:p-6 mb-6">
        <div class="text-muted text-center -mt-0.5 mb-6">
          <small> Reset password </small>
        </div>
        <el-form ref="form" :model="formData" class="authentication-form pb-6">
          <el-form-item class="mb-4 rounded-md" prop="email">
            <div
              class="authentication-form-icon z-10 absolute inset-y-0 left-0 pl-2.5 flex items-center pointer-events-none"
            >
              <div class="w-5 h-5">
                <MailIcon class="w-5 h-5 text-gray-210" />
              </div>
            </div>
            <el-input type="email" placeholder="Email" v-model="formData.email" />
          </el-form-item>
        </el-form>
        <el-button type="primary" @click="sendResetLink"> Send Password Reset Link </el-button>
      </div>
    </el-card>
  </div>
</template>
<script lang="ts">
import { defineComponent, ref } from 'vue'
import { MailIcon } from '@heroicons/vue/solid'
import { apiReqReset } from 'myApi/user-api/updatePwd-api'
import {ElMessage} from "element-plus";

export default defineComponent({
  name: 'ForgotPasswordForm',
  components: {
    MailIcon,
  },
  setup() {
    const form = ref<ElementForm>()
    const formData = ref({ email: '' })

    const sendResetLink = async () => {
      try {
        const response = await apiReqReset(formData.value.email);
        if (response.status == 200){
          ElMessage.success(response.data.message)
        }else{
          ElMessage.warning(response.data.message)
        }
        // 可以在这里添加一些用户友好的反馈，如弹窗提示"重置链接已发送，请检查您的邮箱。"
      } catch (error) {
        console.error('Error:', error);
        // 处理错误情况，如显示错误消息
      }
    };

    return {
      form,
      formData,
      sendResetLink,
    }
  },
})
</script>
