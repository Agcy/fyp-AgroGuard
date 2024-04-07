<template>
  <div id="message">
    <el-divider content-position="left">
      <font style="font-weight:bold; font-size:20px; margin-bottom: 20px">
<!--        {{ currentSession.username }}-->
      </font>
    </el-divider>
    <PerfectScrollbar class="my-scroll-bar" style="overflow-x: hidden;">
      <div style="overflow-x: hidden;" v-scroll-bottom="sessions">
        <ul v-if="currentSession && sessions[user.username + '#' + currentSession.username]" style="overflow-x: hidden;">
          <li v-for="(entry, index) in sessions[user.username + '#' + currentSession.username]" :id="`message-${index}`" :key="index">
            <p class="time">
              <!-- 这里我们用方法替换了过滤器 -->
              <span>{{ formatTimes(entry.date) }}</span>
            </p>
            <div class="main" :class="{ self: entry.self }">
              <img class="avatar" :src="entry.self ? user.avatarUrl : currentSession.avatarUrl" alt="">
              <!-- 移除了v-scroll-bottom，因为它需要通过自定义指令或其他方法来实现滚动逻辑 -->
              <p class="text">{{ entry.content }}</p>
            </div>
          </li>
        </ul>
      </div>
    </PerfectScrollbar>
  </div>
</template>

<script lang="ts" setup>
import { ref, computed, onMounted } from 'vue';
import { useChatStore } from '../../store/store';

const user = ref(JSON.parse(window.sessionStorage.getItem("user") || '{}'));

const store = useChatStore();

const sessions = computed(() => store.sessions);
const currentSession = computed(() => store.currentSession);

// 替代过滤器的方法
const formatTimes = (date: string) => {
  if (date) {
    const dateObj = new Date(date);
    return ` ${dateObj.getMonth() + 1} 月 ${dateObj.getDate()} 日 ${dateObj.getHours()}:${dateObj.getMinutes()}`;
  }
  return '';
};

// 挂载生命周期钩子
onMounted(() => {
  console.log(789789789, currentSession.value)
  if (currentSession.value) {
    const currentMessages = sessions.value[user.value.username + '#' + currentSession.value.username];
    if (currentMessages && currentMessages.length > 0) {
      document.getElementById((currentMessages.length - 1).toString()).scrollIntoView();
    }
  }
});

// 自定义指令
const scrollBottomDirective = {
  mounted(el: HTMLElement) {
    el.scrollIntoView(false);
    console.log(el);
  }
};
</script>

<style lang="scss" scoped>
.my-scroll-bar {
  margin-top: 10px;
  height: 450px;
  overflow-x: hidden;
}

/* override gemini-scrollbar default styles */

/* vertical scrollbar track */
.gm-scrollbar.-vertical {
  background-color: #f0f0f0
}

/* horizontal scrollbar track */
.gm-scrollbar.-horizontal {
  background-color: transparent;
}

/* scrollbar thumb */
.gm-scrollbar .thumb {
  background-color: rebeccapurple;
}

.gm-scrollbar .thumb:hover {
  background-color: fuchsia;
}


#message {
  padding: 10px 0px;
  max-height: 500px;
  background: transparent;
  list-style: none;
  overflow-x: hidden;

  ul {
    list-style-type: none;
    padding-left: 0px;
    overflow-x: hidden;

    li {
      margin-bottom: 5px;
    }
  }

  .time {
    text-align: center;
    margin: 7px 0;

    > span {
      display: inline-block;
      padding: 0 18px;
      font-size: 12px;
      color: #fff;
      background-color: #dcdcdc;
      border-radius: 2px;
    }
  }

  .main {
    .avatar {
      float: left;
      margin: 0 10px 0 10px;
      border-radius: 3px;
      width: 30px;
      height: 30px;
    }

    .text {
      display: inline-block;
      padding: 0 10px;
      max-width: 80%;
      background-color: #fafafa;
      border-radius: 4px;
      line-height: 30px;
    }
  }

  .self {
    text-align: right;

    .avatar {
      float: right;
      margin: 0 20px 0 10px;
      border-radius: 3px;
      width: 30px;
      height: 30px;
    }

    .text {
      display: inline-block;
      padding: 0 10px;
      max-width: 80%;
      background-color: #b2e281;
      border-radius: 4px;
      line-height: 30px;
    }
  }
}
</style>
