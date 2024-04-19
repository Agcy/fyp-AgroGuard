<template>
  <div class="w-full">
    <el-card class="bg-secondary text-center pb-6">
      <template #header>
        <div class="text-muted text-center mt-2 mb-4">
          <small class="text-90">Sign in with</small>
        </div>
        <div class="pb-6 flex flex-nowrap text-center justify-center">
          <el-button class="bg-white border-white" href="#">
            <img src="@/assets/images/facebook.png" alt="" class="h-4 w-4" />
            <span class="pl-4 text-indigo-410">Facebook</span>
          </el-button>
          <el-button class="bg-white border-white" href="#">
            <img src="@/assets/images/google.png" alt="" class="h-4 w-4" />
            <span class="pl-4 text-indigo-410">Google</span>
          </el-button>
        </div>
      </template>
      <div class="content-center items-center w-full lg:p-6">
        <div class="mb-4 mt-2 text-center">
          <small class="block w-full text-12.8 mb-6 text-muted">{{ description }}</small>
        </div>
        <el-form ref="form" :model="formData" class="authentication-form">
          <el-form-item class="warning-input mb-4 rounded-md" prop="account">
            <div class="z-10 absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
              <div class="w-5 h-5">
                <MailIcon class="w-5 h-5 text-gray-210" />
              </div>
            </div>
            <el-input placeholder="Account" v-model="formData.account" />
          </el-form-item>
          <el-form-item class="mb-6 rounded-md" prop="password">
            <div class="z-10 absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
              <div class="w-5 h-5">
                <LockOpenIcon class="w-5 h-5 text-gray-210" />
              </div>
            </div>
            <el-input type="password" placeholder="Password" v-model="formData.password" />
          </el-form-item>
          <el-form-item class="mb-4">
            <el-checkbox class="text-muted font-normal">Remember me</el-checkbox>
          </el-form-item>
        </el-form>
        <el-button type="primary" @click="handleLoginClick"> Sign in </el-button>
      </div>
    </el-card>
  </div>
</template>
<script lang="ts">
import { defineComponent, ref } from 'vue'
import useStore from 'store'
import {apiSignIn} from "myApi/auth-api/signin-api";
import { MailIcon, LockOpenIcon } from '@heroicons/vue/solid'
import {AUTH_TOKEN} from "core/constants";

export default defineComponent({
  name: 'LoginForm',
  components: {
    MailIcon,
    LockOpenIcon,
  },
  props: {
    height: {
      type: Number,
    },
    description: {
      type: String,
      default: '',
    },
    account: {
      type: String,
      default: '',
    },
    password: {
      type: String,
      default: '',
    },
  },
  setup(props) {
    const store = useStore()
    const form = ref<ElementForm>()
    const formData = ref({ account: props.account, password: props.password })
    console.log(props)
    const handleKeyDown = async () => {
      login()
    }

    const handleLoginClick = async () => {
      login()
    }

    const login = async () => {
      apiSignIn(formData.value.account, formData.value.password).then((response)=>{
        if (response.status == 200) {
          console.log(response.data.msg)
          const data = response.data; // 服务器返回的数据

          // 构建用户信息对象，确保它匹配你的用户信息结构
          const user = {
            id: data.id,
            username: data.username,
            email: data.email,
            avatarUrl: data.avatarUrl,
            roles: data.roles,
          };
          const token = data.token; // 从响应中获取 token
          console.log(user, token)

          if (!store.auth.isAuthenticated) {
            store.auth.actLogin(token, user)
          }
          // router.push('/mypage')
        }else{
          alert("failed")
        }
      }).catch (e => {
        console.log('err::: ', e)
      })
    }
    return {
      form,
      formData,
      handleLoginClick,
      handleKeyDown,
    }
  },
})
</script>
