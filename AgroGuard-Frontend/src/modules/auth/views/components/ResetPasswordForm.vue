<template>
  <div class="w-full">
    <el-card class="bg-secondary text-center">
      <div class="content-center items-center w-full md:p-6 mb-6">
        <div class="text-muted text-center -mt-0.5 mb-6">
          <small> Please Enter Your New Password </small>
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
            <el-input type="password" placeholder="New Password" v-model="formData.password" />
          </el-form-item>

          <el-form-item class="mb-6 rounded-md" prop="password">
            <div
              class="authentication-form-icon z-10 absolute inset-y-0 left-0 pl-2.5 flex items-center pointer-events-none"
            >
              <div class="w-5 h-5">
                <LockOpenIcon class="w-5 h-5 text-gray-210" />
              </div>
            </div>
            <el-input type="password" placeholder="Confirm Password" v-model="formData.confirmPassword" />
          </el-form-item>
        </el-form>
        <el-button type="primary" @click="sendResetPwd"> Reset Password </el-button>
      </div>
    </el-card>
  </div>
</template>
<script lang="ts">
import { defineComponent, ref, onMounted } from 'vue'
import { MailIcon, LockOpenIcon } from '@heroicons/vue/solid'
import { apiReset } from 'myApi/auth-api/updatePwd-api'
import { ElMessage } from "element-plus";
import { useRoute, useRouter } from 'vue-router';

export default defineComponent({
  name: 'ResetPasswordForm',
  components: {
    LockOpenIcon,
    MailIcon,
  },
  setup() {
    const form = ref<ElementForm>()
    const formData = ref({ password: '', confirmPassword: '' })

    const route = useRoute();
    const router = useRouter();
    let token = ref('');

    onMounted(() => {
      token.value = route.query.token as string;
    });

    const sendResetPwd = async () => {
      if (formData.value.password !== formData.value.confirmPassword) {
        ElMessage.error('Passwords do not match.');
        return; // 停止执行更多的代码，因为密码不匹配
      }

      try {
        const response = await apiReset(token.value, formData.value.password);
        if (response.status == 200){
          ElMessage.success(response.data.message)
          // window.location.href = '/login'
          await router.push('/login')
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
      sendResetPwd,
    }
  },
})
</script>
