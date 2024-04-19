import vueEmitter from 'core/emitter'
import Vue, { createApp, h } from 'vue'
import { createPinia } from 'pinia'
import ElementPlus from 'element-plus'
import App from './App.vue'
import AppComponents from './components'
import VueSweetAlert2 from 'vue-sweetalert2'
import { PerfectScrollbarPlugin } from 'vue3-perfect-scrollbar'
import * as process from 'process';
import { library } from '@fortawesome/fontawesome-svg-core'
import { fas } from '@fortawesome/free-solid-svg-icons'
import { fab } from '@fortawesome/free-brands-svg-icons'
import {
  faEllipsis,
  faPaperPlane,
  faPlusCircle,
  faSearch,
  faTimes,
} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import type { StoreData } from "modules/chat/store/types/StoreData";
import 'sweetalert2/dist/sweetalert2.min.css'
import Notifications from "@kyvg/vue3-notification";
import './index.css'
import router from './router'
import Vuex, {createStore, Store} from "vuex";
// create new app instance
const icons = [faSearch, faEllipsis, faPaperPlane, faPlusCircle, faTimes];
icons.forEach((icon) => library.add(icon));

const store: Store<StoreData> = createStore({
  state() {
    return {
      user: {
        username: '', // 根据实际情况提供用户名的初始值
      },
      // stompClient: undefined,
      isConnected: false,
      messages: {},
      groupById: {},
    };
  },
});
const createNewApp = () => {
  const app = createApp({
    render: () => h(App),
  })
  library.add(fas, fab)

  app.component('font-awesome-icon', FontAwesomeIcon)
  app.provide('eventHub', vueEmitter)
  app.use(router)
  app.use(ElementPlus)
  app.use(PerfectScrollbarPlugin)
  app.use(AppComponents)
  app.use(createPinia())
  app.use(VueSweetAlert2)
  app.use(store)
  app.use(Notifications);

  app.mount('#app')
  app.config.performance = true
}

const initApp = async () => {
  createNewApp()
}

initApp().then(() => {
  // initialized
})

declare const window: any;

if (typeof window.global === 'undefined') {
  window.global = window;
}


(window as any).process = process;
