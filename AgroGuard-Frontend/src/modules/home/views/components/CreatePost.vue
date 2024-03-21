<template>
  <div>
    <el-button type="primary" @click="dialogVisible = true">添加帖子</el-button>
    <el-dialog v-model="dialogVisible" title="添加新帖子">
      <div>
        <el-upload
          ref="upload"
          action="#"
          list-type="picture-card"
          :on-change="handleFileChange"
          :file-list="fileList"
          :auto-upload="false"
          v-slot="{ file }"
          multiple>
          <i class="el-icon-plus"></i>
        </el-upload>
        <el-dialog v-model="previewVisible">
          <img width="100%" :src="previewImage" alt="">
        </el-dialog>
      </div>
      <el-input placeholder="请输入标题" v-model="newPostData.title" class="mb-2"></el-input>
      <el-input type="textarea" placeholder="请输入内容" v-model="newPostData.content"></el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitPost">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script lang="ts">
import {defineComponent, Ref, ref} from 'vue';
import {usePostsStore} from '../../store/store'; // 请确保路径正确
import {ElAvatar, ElButton, ElInput, ElUpload, UploadFile, UploadUserFile, UploadProps} from 'element-plus';
import {forEach} from "lodash";

export default defineComponent({
  name: 'CreatePost',
  methods: {forEach},
  components: {ElAvatar, ElButton, ElInput, ElUpload},
  setup() {
    const dialogVisible = ref(false);
    const postsStore = usePostsStore();
    const previewVisible = ref(false);
    const previewImage = ref('');
    const newPostData = ref({
      title: '',
      content: '',
      base64Imgs: [] as string[], // 储存图片的Base64字符串
    });
    const fileList: Ref<UploadUserFile[]> = ref([]);

    // 将文件转换为Base64
    const toBase64 = (file: UploadFile) => new Promise<string>((resolve, reject) => {

    });

    // 处理图片选择
    // 处理图片选择和转换为Base64
    // 处理图片选择和转换为Base64
    const handleFileChange = async (file: UploadFile, fileList: UploadUserFile[]) => new Promise<string>((resolve, reject) => {
      console.log("Handling file change...");
      // 假设只处理新增的文件
      if (file.status === 'ready' && file.raw && file.raw instanceof Blob) {
        // const base64 = await toBase64(file);
        const reader = new FileReader();
        // reader.readAsDataURL(file.raw as Blob);
        reader.onload = (e) => {
          file.url = e.target?.result as string; // 设置文件的Base64 URL用于预览
          previewImage.value = e.target?.result as string;
          newPostData.value.base64Imgs.push(e.target?.result as string);
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


    const submitPost = () => {
      // 使用store中的addPost动作提交新帖子
      postsStore.addPost(newPostData.value);
      // 重置表单数据
      newPostData.value = {title: '', content: '', base64Imgs: []};
      fileList.value = []; // 重置文件列表
    };

    const handlePictureCardPreview = (file: UploadUserFile) => {
      if (file.url) {
        previewImage.value = file.url;
        previewVisible.value = true;
      }
    }

    return {
      dialogVisible,
      previewVisible,
      previewImage,
      newPostData,
      fileList,
      handleFileChange,
      submitPost,
      handlePictureCardPreview
    };
  },
});
</script>


<style scoped>
.mb-2 {
  margin-bottom: 20px;
}
</style>
