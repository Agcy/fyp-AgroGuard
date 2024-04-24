<template>
  <div class="w-full">
    <el-card
      class="text-center border-slate-100 border-0"
      :body-style="{ padding: '0px', position: 'relative' }"
    >
      <img alt="..." :src="backgroundImg"/>
      <div class="justify-center">
        <el-link :underline="false" class="card-avatar-profile">
          <el-avatar
            :size="!isHover ? 140 : 146"
            @mouseover="hoverCheck(true)"
            @mouseleave="hoverCheck(false)"
            :src="avatarImg"
            class="transition-all border-white border-4"
          />
        </el-link>
      </div>
      <div class="text-center border-0 pb-6 card-header">
        <div class="flex justify-between">
          <div class="ml-4 z-0">
            <el-button @click="handleClick" class="shadow-lg w-16" size="small" type="info">Edit</el-button>
          </div>
          <!-- Modal dialog for editing user preferences -->
          <el-dialog
            title="Edit Profile"
            v-model="showEditModal"
            :before-close="handleClose">
            <el-form :model="userForm">
              <el-form-item label="Username">
                <el-input v-model="userForm.username" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="Avatar">
                <div>
                  <el-upload
                    ref="upload"
                    class="avatar-uploader"
                    action="#"
                    list-type="picture-card"
                    :show-file-list="false"
                    :on-change="handleAvatarSuccess"
                    :auto-upload="false"
                    v-slot="{ file }"
                    multiple>
                    <i class="el-icon-plus"></i>
                    <el-dialog v-model="avatarUrl">
                      <img width="100%" :src="avatarUrl" alt="">
                    </el-dialog>
                  </el-upload>
                </div>
              </el-form-item>
              <el-form-item label="Occupation">
                <el-input v-model="userForm.occupation" autocomplete="off"></el-input>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click="handleClick">Cancel</el-button>
              <el-button type="primary" @click="saveChanges">Save Changes</el-button>
            </div>
          </el-dialog>

          <div class="mr-4 z-0">
            <el-button class="shadow-lg w-16" size="small" type="default" @click="toMessage">Message</el-button>
          </div>
        </div>
      </div>
      <div class="mt-8">
        <div class="flex justify-center gap-x-11">
          <div class="block">
            <div>
              <span class="text-1.1 text-normal font-bold">{{ following }}</span>
            </div>
            <div>
              <span class="text-sm text-gray-210 mb-0">Following</span>
            </div>
          </div>
          <div class="block">
            <div>
              <span class="text-1.1 text-normal font-bold">{{ posts }}</span>
            </div>
            <div>
              <span class="text-sm text-gray-210 mb-0">Posts</span>
            </div>
          </div>
          <div class="block">
            <div>
              <span class="text-1.1 text-normal font-bold">{{ followers }}</span>
            </div>
            <div>
              <span class="text-sm text-gray-210 mb-0">Followers</span>
            </div>
          </div>
        </div>
      </div>
      <div class="p-8">
        <div class="text-center text-primary-dark">
          <h3 class="font-semibold pb-2">
            {{ user?.username }}<span class="font-thin"></span>
          </h3>
          <h5 class="font-light">
            <el-icon :size="12">
              <Location/>
            </el-icon>
            {{ location }}
          </h5>
        </div>
        <div class="text-center text-primary-dark">
          <h3 class="text-0.813 mt-6 font-semibold pb-2">
            {{ job }}
          </h3>
          <h5 class="text-base text-dark-lighter font-normal"></h5>
        </div>
      </div>
    </el-card>
  </div>
</template>
<script lang="ts">
import {defineComponent, ref} from 'vue'
import {Location} from '@element-plus/icons-vue'
import {useState} from "modules/auth/store/state";
import router from "router";
import {ElForm, ElUpload, UploadFile, UploadUserFile} from "element-plus";

export default defineComponent({
  name: 'ProfileCard',

  components: {
    Location,
    ElUpload,
    ElForm,
  },
  props: {
    avatarImg: {
      type: String,
    },
    backgroundImg: {
      type: String,
    },
    following: {
      type: Number,
      default: 0,
    },
    posts: {
      type: Number,
      default: 0,
    },
    followers: {
      type: Number,
      default: 0,
    },
    name: {
      type: String,
      default: '',
    },
    location: {
      type: String,
      default: '',
    },
    job: {
      type: String,
      default: '',
    },
  },
  setup() {
    const newPostData = ref({
      username: '',
      occupation: '',
      avatarUrl: '', // 储存图片的Base64字符串
    });
    const user = useState().user
    // props.following = user?.following.length
    const isHover = ref(false)
    const hoverCheck = (b: boolean) => {
      isHover.value = b
    }

    const toMessage = () => {
      return router.push('/welcome-chat')
    }

    const handleClose = (done) => {
      // Optionally add a confirmation here or validations
      done();
    };
    const showEditModal = ref(false);
    const handleClick = () => {
      if (showEditModal.value) {
        showEditModal.value = false;
      } else {
        showEditModal.value = true;
      }
    };
    const userForm = ref({
      username: user?.username as string,
      avatarUrl: user?.avatarUrl as string,
      occupation: user?.occupation as string,
    });
    const saveChanges = () => {
      // Implement save logic here
      console.log('Saved', userForm.value);
      showEditModal.value = false;
    };

    const avatarUrl = ref(userForm.value.avatarUrl); // This will hold the actual image URL

    const handleAvatarSuccess = async (file: UploadFile, fileList: UploadUserFile[]) => new Promise<string>((resolve, reject) => {
      console.log("Handling file change...");
      // 假设只处理新增的文件
      if (file.status === 'ready' && file.raw && file.raw instanceof Blob) {
        // const base64 = await toBase64(file);
        const reader = new FileReader();
        // reader.readAsDataURL(file.raw as Blob);
        reader.onload = (e) => {
          file.url = e.target?.result as string; // 设置文件的Base64 URL用于预览
          avatarUrl.value = e.target?.result as string;
          newPostData.value.avatarUrl = e.target?.result as string;
          // previewVisible.value = true; // 显示预览对话框
        };
        reader.readAsDataURL(file.raw as Blob);
        reader.onerror = error => reject(error);

        console.log(newPostData)
        console.log("File converted to Base64.");
      } else {
        console.error("File is not ready or not a Blob.");
      }
    });

    // const beforeAvatarUpload = (file: UploadFile) => {
    //   // Optional: Restrict file types or sizes
    //   const isJPG = file.type === 'image/jpeg' || file.type === 'image/png';
    //   const isLt2M = file.size / 1024 / 1024 < 2;
    //
    //   if (!isJPG) {
    //     this.$message.error('Avatar picture must be JPG or PNG!');
    //     return false;
    //   }
    //   if (!isLt2M) {
    //     this.$message.error('Avatar size must be less than 2MB!');
    //     return false;
    //   }
    //   return true;
    // };
    return {
      handleAvatarSuccess,
      // beforeAvatarUpload,
      avatarUrl,
      handleClick,
      isHover,
      user,
      hoverCheck,
      toMessage,
      showEditModal,
      userForm,
      handleClose,
      saveChanges
    }
  },
})
</script>
<style scoped>
.dialog-footer {
  text-align: right;
}

.avatar-uploader {
  cursor: pointer;
  display: block;
  width: 128px;
  height: 128px;
  line-height: 128px;
  text-align: center;
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
}

.avatar {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c8c8c;
}
</style>
