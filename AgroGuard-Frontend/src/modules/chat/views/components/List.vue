<template>

  <div style="display: flex;justify-content:space-between;height: 100%;width: 100%">
    <div class="leftlist">

      <el-menu background-color="#2e3238" router
               class="el-menu-vertical-demo"
               active-text-color="#67C23A"
               text-color="#fff"
               :collapse="isCollapse">
        <el-menu-item index="/chat" style="padding-left: 10px;margin:10px 0px 20px 2px">
          <el-tooltip effect="light" placement="right-start" popper-class="el-scrollbar">
            <div slot="content">

              <div style="margin-top: 5px;font-size: 13px;lineHeight:1.5;">
                <div>用户名：{{user.username}}</div>
                <div>EMail：{{user.email}}</div>
                <div>地 址：{{user.address}}</div>
              </div>
            </div>
            <img class="avatar"
                 :src="user.avatarUrl"
                 :alt="user.name"></el-tooltip>
        </el-menu-item>
        <el-menu-item index="/chat" style="padding-left: 15px">
          <i class="fa fa-weixin fa-2x"></i>

        </el-menu-item>
        <el-menu-item index="/chat" style="padding-left: 15px">
          <i class="fa fa-windows fa-2x"></i>

        </el-menu-item>
        <el-menu-item index="/chat" style="padding-left: 15px">
          <i class="fa fa-modx fa-2x"></i>

        </el-menu-item>
        <el-menu-item index="/chat" style="padding-left: 15px">
          <i class="fa fa-share-alt fa-2x"></i>

        </el-menu-item>
      </el-menu>

    </div>
    <div id="list">

      <div style="height:100%;width:100%;overflow-x: hidden">

        <ul style="padding-left: 0px; overflow-x: hidden;">
          <el-autocomplete
            v-model="SearchHr" class="input-with-select" popper-append-to-body="false"
            style="width: 90%;padding-left: 5%;padding-top: 10px;margin-bottom: 10px"
            size="small"
            :fetch-suggestions="querySearch"
            placeholder="请输入内容"
            @select="handleSelect"
          >
            <el-button slot="append" icon="el-icon-search"
                       @click="SearchCurrentSession()"></el-button>
          </el-autocomplete>

          <PerfectScrollbar
            class="my-scroll-bars">
            <li v-for="(item,index) in chatRooms" :id="index"
                :key="index"
                :class="{ active: currentSession?item.username === currentSession.username:false}"
                @click="changeCurrentSession(item.chatRoomId)">
              <img class="avatar"
                   :src="item.avatarUrl" alt="">
              <el-badge :is-dot="isDot[user.username+'#'+item.username]">
                <p class="name">{{item.username}}</p>
              </el-badge>
            </li>
          </PerfectScrollbar>
        </ul>
      </div>

    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, computed, onMounted } from 'vue';
import { useChatStore } from '../../store/store';
import {useStore} from "vuex";
import {apiGetMessageByChatRoomId} from "myApi/chat-api/getMessageByChatRoomId";

// 使用Composition API重构data
const isCollapse = ref(true);
const SearchHr = ref('');
const hr = ref({
  id: '',
  name: '',
  avatarUrl: ''
});
const restaurants = ref([]);
const user = ref(JSON.parse(window.sessionStorage.getItem("user") || 'null'));
console.log(123123,user.value.username)
const store = useChatStore();

// 使用Composition API重构computed
const chatRooms = computed(() => store.chatrooms);
const friends = computed(() => store.friends);
const isDot = computed(() => store.isDot);
const currentSession = computed(() => store.currentSession);

// 方法
function SearchCurrentSession() {
  // 注意: 你需要定义getRequest或从外部导入
  apiGetMessageByChatRoomId( SearchHr.value ).then(resp => {
    if (resp) {
      hr.value = resp.data;
      changeCurrentSession(hr.value.id);
      let it = 0;
      chatRooms.value.forEach((item: any, index: number) => {
        if (item.username === hr.value.name) {
          it = index;
        }
      });
      document.getElementById(it.toString()).scrollIntoView();
      SearchHr.value = '';
    }
  });
}

function querySearch(queryString: any, cb: (arg0: any[]) => void) {
  restaurants.value = loadAll();
  let results: any[] = [];
  restaurants.value.forEach(value => {
    let { name, username } = value;
    let restaurant = { value: name, username: username };
    results.push(restaurant);
  });
  results = queryString ? results.filter(createFilter(queryString)) : results;
  cb(results);
}

function createFilter(queryString: string) {
  return (searchHr: { value: string; }) => {
    return (searchHr.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
  };
}

function loadAll() {
  return chatRooms.value;
}

function changeCurrentSession(currentSession: string) {
  store.changeCurrentSession(currentSession);
}

function handleSelect(item: any) {
  console.log(item);
}

// 挂载时
onMounted(() => {
  store.initData(user.value.id);
});
</script>


<style lang="scss" scoped>


.my-scroll-bars {
  height: 610px;
  overflow-x: hidden;
}

/* override gemini-scrollbar default styles */

/* vertical scrollbar track */
.gm-scrollbar.-vertical {
  background-color: #f0f0f0;
  overflow-x: hidden;
}

/* horizontal scrollbar track */
.gm-scrollbar.-horizontal {
  background-color: transparent;
}

/* scrollbar thumb */
.gm-scrollbar .thumb {
  background-color: rebeccapurple;
}

.gm-scroll-view {
  overflow-x: hidden;
}

.gm-scrollbar .thumb:hover {
  background-color: fuchsia;
}


.input-with-select {
  margin-top: 50px;
  padding-top: 20px;

}

.el-scrollbar__wrap {
  width: 100%;
  height: 100%;
  overflow-x: hidden;
}

.el-menu-item is-active {
  padding-left: 10px;

}

.el-menu-vertical-demo {
  background-color: #2e3238;
  width: 80px;
  height: 100%;
  /*opacity:0.8;*/

}

.leftlist {
  background-color: transparent;
  width: 90px;
  height: 700px;
  overflow-x: hidden;
}

.avatar {
  width: 45px;
  height: 45px;
  /*这个是图片和文字居中对齐*/
  border-radius: 5px;
  margin-top: 5px;
}

.el-scrollbar__wrap {
  background-color: #E4E7ED;
}

#el-scrollbar {
  background-color: #E4E7ED;
}

#list {
  background-color: #E4E7ED;
  width: 100%;
  overflow-x: hidden;

  li {
    padding: 7px 15px;
    border-bottom: 1px solid #E4E7ED;
    cursor: pointer;
    list-style: none;
    color: #505458;

    &:hover {
      background-color: rgba(0, 0, 0, 0.07);
    }
  }

  li.active {
    /*注意这个是.不是冒号:*/
    background-color: rgba(0, 0, 0, 0.1);
  }

  .avatar {
    border-radius: 2px;
    width: 30px;
    height: 30px;
    vertical-align: middle;
  }

  .name {
    display: inline-block;
    margin-left: 15px;
    margin-top: 0px;
    margin-bottom: 0px;
  }
}
</style>
