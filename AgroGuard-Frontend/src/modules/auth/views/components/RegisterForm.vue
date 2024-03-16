<template>
  <div class="w-full">
    <el-card class="bg-secondary text-center">
      <template #header>
        <div class="text-muted text-center mt-1.5 mb-6"><small>Sign up with</small></div>
        <div class="flex flex-nowrap text-center justify-center pb-7.5">
          <el-button class="bg-white border-white" href="#">
            <img src="@/assets/images/github.png" alt="" class="h-4 w-4" />
            <span class="pl-4 text-indigo-410">Github</span>
          </el-button>
          <el-button class="bg-white border-white ml-7" href="#">
            <img src="@/assets/images/google.png" alt="" class="h-4 w-4" />
            <span class="pl-4 text-indigo-410">Google</span>
          </el-button>
        </div>
      </template>
      <div class="content-center items-center w-full lg:p-6">
        <div class="text-muted text-center mb-5">
          <small>Or sign up with credentials </small>
        </div>
        <el-form ref="form" :model="formData" class="authentication-form pb-6">

          <!--新建用户用户名输入框-->
          <el-form-item class="mb-6 rounded-md" prop="username">
            <div
              class="authentication-form-icon z-10 absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none"
            >
              <div class="w-5 h-5">
                <AcademicCapIcon class="w-5 h-5 text-gray-210" />
              </div>
            </div>
            <el-input placeholder="Name" v-model="formData.username" />
          </el-form-item>

          <!--新建用户邮箱输入框-->
          <el-form-item class="mb-6 rounded-md" prop="email">
            <div
              class="authentication-form-icon z-10 absolute inset-y-0 left-0 pl-2.5 flex items-center pointer-events-none"
            >
              <div class="w-5 h-5">
                <MailIcon class="w-5 h-5 text-gray-210" />
              </div>
            </div>
            <el-input type="email" placeholder="Email" v-model="formData.email" />
          </el-form-item>

          <!--新建用户性别复选框-->
          <el-form-item class="mb-6 rounded-md" prop="type">
            <div
              class="authentication-form-icon z-10 absolute inset-y-0 left-0 pl-2.5 flex items-center pointer-events-none"
            >
              <div class="w-5 h-5">
                <IdentificationIcon class="w-5 h-5 text-gray-210" />
              </div>
            </div>
            <el-select
              v-model="formData.gender"
              placeholder="Gender"
              class="w-full"
              popper-class="item-input-popper"
            >
              <el-option
                v-for="item in gender"
                :key="item.id"
                :label="item.genderType"
                :value="item.id"
              >
              </el-option>
            </el-select>
          </el-form-item>

          <!--新建用户职业复选框-->
          <el-form-item class="mb-6 rounded-md" prop="type">
            <div
              class="authentication-form-icon z-10 absolute inset-y-0 left-0 pl-2.5 flex items-center pointer-events-none"
            >
              <div class="w-5 h-5">
                <IdentificationIcon class="w-5 h-5 text-gray-210" />
              </div>
            </div>
            <el-select
              v-model="formData.occupation"
              placeholder="Occupation"
              class="w-full"
              popper-class="item-input-popper"
            >
              <el-option
                v-for="item in occupation"
                :key="item.id"
                :label="item.occupationType"
                :value="item.id"
              >
              </el-option>
            </el-select>
          </el-form-item>

          <el-form-item class="mb-6 rounded-md" prop="password">
            <div
              class="authentication-form-icon z-10 absolute inset-y-0 left-0 pl-2.5 flex items-center pointer-events-none"
            >
              <div class="w-5 h-5">
                <LockOpenIcon class="w-5 h-5 text-gray-210" />
              </div>
            </div>
            <el-input type="password" placeholder="Password" v-model="formData.password" />
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
          <el-form-item class="mb-6 rounded-md">
            <div class="italic">
              <span class="text-0.8125 text-muted font-normal">
                password strength: <strong class="text-success">strong</strong>
              </span>
            </div>
          </el-form-item>
          <el-form-item class="mb-6">
            <el-checkbox class="w-4 h-4 text-muted font-normal"
              >I agree with the
              <a href="#!" class="text-indigo-410 hover:text-indigo-410-active">Privacy Policy</a>
            </el-checkbox>
          </el-form-item>
        </el-form>
        <el-button type="primary" @click="handleSignupClick"> Create account </el-button>
      </div>
    </el-card>
  </div>
</template>
<script lang="ts">
import {defineComponent, ref} from 'vue'
import { MailIcon, AcademicCapIcon, IdentificationIcon, LockOpenIcon } from '@heroicons/vue/solid'
import {apiSignUp} from "myApi/user-api/signup-api";
import {apiSignIn} from "myApi/user-api/signin-api";
import useStore from 'store'
import {ElMessage} from "element-plus";

export default defineComponent({
  name: 'RegisterForm',
  components: {
    MailIcon,
    AcademicCapIcon,
    IdentificationIcon,
    LockOpenIcon,
  },
  setup() {
    const store = useStore();
    const form = ref<ElementForm>()
    const formData = ref({
      username: '',
      email: '',
      gender: '',
      occupation: '',
      password: '',
      confirmPassword: ''
    })

    const gender = [
      {
        id: 1,
        genderType: 'Male',
      },
      {
        id: 2,
        genderType: 'Female',
      },
      {
        id: 3,
        genderType: 'Would Rather Not Say',
      },
    ]

    const occupation = [
      {
        id: 1,
        occupationType: 'Farmer',
      },
      {
        id: 2,
        occupationType: 'Company Representative',
      },
      {
        id: 3,
        occupationType: 'Enthusiast',
      },
      {
        id: 4,
        occupationType: 'Expert',
      },
      {
        id: 5,
        occupationType: 'Professor',
      },
      {
        id: 6,
        occupationType: 'Teacher',
      },
      {
        id: 7,
        occupationType: 'Student',
      },
    ]

    const handleSignupClick = async() => {
      signup()
    }

    const signup = async() => {
      if (formData.value.password !== formData.value.confirmPassword) {
        ElMessage.error('Passwords do not match.');
        return; // 停止执行更多的代码，因为密码不匹配
      }
      apiSignUp(formData.value.username,
        formData.value.gender,
        formData.value.occupation,
        formData.value.email,
        formData.value.password)
        .then((signUpResponse) => {
        if (signUpResponse.status == 200){
          console.log(signUpResponse.data.message)

          //调用一次signin，用户不需要再登录一遍
          apiSignIn(formData.value.username, formData.value.password).then((loginResponse)=>{
            if (loginResponse.status == 200) {
              console.log(loginResponse.data.message)
              const data = loginResponse.data; // 服务器返回的数据

              // 构建用户信息对象，确保它匹配你的用户信息结构
              const user = {
                id: data.id,
                username: data.username,
                email: data.email,
                roles: data.roles,
              };
              const token = data.token; // 从响应中获取 token
              console.log(user, token)

              if (!store.auth.isAuthenticated) {
                store.auth.actLogin(token, user)
              }
              // router.push('/mypage')
            }else{
              alert(loginResponse.status+" "+loginResponse.data.message)
            }
          }).catch (e => {
            console.log('err::: ', e)
          })
        } else {
          ElMessage.error(signUpResponse.status+" "+signUpResponse.data.message)
        }
      })
    }
    return {
      gender,
      occupation,
      form,
      formData,
      handleSignupClick,
    }
  },

})
</script>

<style lang="scss" scoped>
.item-input-popper {
  .el-select-dropdown__item.selected {
    @apply text-indigo-410;
  }
}
</style>
