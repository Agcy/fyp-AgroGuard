<template>
  <div id="app">
    <transition name="el-zoom-in-center">
      <div v-show="show3">
        <div class="sidebar">
          <list></list>
        </div>
        <div class="main">
          <message></message>
          <uesrtext></uesrtext>
        </div>
      </div>
    </transition>

  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useChatStore } from '../store/store' // 假设你已经切换到使用Pinia
import List from './components/List.vue'
import Message from './components/Message.vue'
import uesrtext from './components/UserText.vue'

// 定义响应式数据
const show3 = ref(false)
const uploadShow = ref(false)
const uploadflag = ref(true)

const store = useChatStore()

// 移除created生命周期，使用onMounted代替
onMounted(() => {
  uploadanimated()
  store.connect()
  initview()
})

function initview() {
  setTimeout(() => {
    show3.value = true
  }, 1000)
}

function uploadenter() {
  uploadflag.value = true
}

function uploadleave() {
  uploadflag.value = false
}

function uploadanimated() {
  setTimeout(() => {
    uploadShow.value = true
    setTimeout(() => {
      uploadleave()
    }, 1000)
  }, 1000)
}
</script>


<style lang="scss" scoped>
.off{
  -webkit-animation:1s seconddiv;
  background: transparent;
}

@keyframes seconddiv{
  0% {transform: scale(1.4,1.4);}
  10% {transform: scale(1,1);}
  25% {transform: scale(1.2,1.2);}
  50% {transform: scale(1,1);}
  70% {transform: scale(1.2,1.2);}
  100% {transform: scale(1,1);}
}
.meun-switch {
  position: fixed;
  top: 90px;
  left: 0px;
  z-index: 2001;
  cursor: pointer;
  width: 150px;
  height: 150px;
  padding: 5px;
  transition: all 0.25s;

  &.leave {
    left: -65px;
  }

  &.active {
    left: 0;
  }

  &:hover {
    transform: scale(1.02);
  }

  img {
    width: 120px;
    height: 120px;
  }
}

#particles-js {
  width: 100%;
  height: calc(100% - 100px);
  position: absolute;
  overflow: hidden;
}

#app {
  /*背景裁剪在背景边框内部*/
  background-clip: padding-box;
  /*// 边框样式*/
  border: 1px solid #eaeaea;
  /*// 边框阴影*/
  box-shadow: 0 0 25px #cac6c6;
  margin: 20px auto;
  width: 1100px;
  height: 100%;
  overflow: hidden;
  border-radius: 10px;
  overflow-x: hidden;

  .sidebar,
  .main {
    height: 100%;
  }

  .sidebar {
    float: left;
    color: #f4f4f4;
    background-color: transparent;
    width: 300px;
    height: 100%
  }

  .main {
    position: relative;
    overflow: hidden;
    background-color: transparent;
  }
}
</style>
