import {
  ViewGridIcon,
  CursorClickIcon,
  DocumentTextIcon,
  StarIcon,
  HomeIcon,
  ViewBoardsIcon,
  BellIcon,
  LocationMarkerIcon,
  UserIcon,
  ColorSwatchIcon
} from '@heroicons/vue/outline'

import {
  CreditCardIcon,
} from '@heroicons/vue/solid'

const Login = () => import('modules/auth/views/login.vue')
const Register = () => import('modules/auth/views/register.vue')
const ForgotPassword = () => import('modules/auth/views/forgot-password.vue')
const ResetPassword = () => import('modules/auth/views/reset-password.vue');
const NotFound = () => import('modules/pages/views/404.vue')
const Dashboard = () => import('modules/dashboard/views/index.vue')
const Table = () => import('modules/table/views/index.vue')
const Grid = () => import('modules/grid/views/index.vue')
const Notification = () => import('modules/notification/views/index.vue')
const Button = () => import('modules/buttons/views/index.vue')
const Tags = () => import('modules/tags/views/index.vue')
const Typography = () => import('modules/typography/views/index.vue')
const Card = () => import('modules/cards/views/index.vue')
const Icons = () => import('modules/icons/views/index.vue')
const Profile = () => import('modules/profile/views/index.vue')
const Map = () => import('modules/map/views/index.vue')
const Posts = () => import('modules/home/views/index.vue')
const PostDetail = () => import('modules/home/views/PostDetailPage.vue')
const WelcomeView = () => import('modules/chat/views/WelcomeView.vue')
const Detection = () => import('modules/detection/views/index.vue')
const Library = () => import('modules/library/views/index.vue')
const DiseaseDetailPage = () => import('modules/library/views/DiseaseDetailPage.vue')
const HomeView = () => import('modules/chat/views/HomeView.vue')
const Chat = () => import('modules/chat/views/WelcomeView.vue')

const ComponentLayout = () => import('components/ComponentLayout/index.vue')


const routes = [
  {
    path: '/',
    component: Dashboard,
    name: 'Dashboard',
    meta: {
      title: 'Dashboard',
      icon: HomeIcon,
      color: 'text-indigo-410',
      requiresAuth: true,
      parentPath: 'Home'
    },
  },
  {
    path: '/components/:componentItem?',
    component: ComponentLayout,
    name: 'Components',
    meta: {
      title: 'Components',
      icon: ColorSwatchIcon,
      color: 'text-info',
      requiresAuth: true,
      parentPath: 'Components'
    },
    children: [
      {
        path: 'buttons',
        name: 'Buttons',
        component: Button,
        meta: {
          title: 'Buttons',
          icon: CursorClickIcon,
          color: 'text-danger-50',
          requiresAuth: true,
        },
      },
      {
        path: 'tags',
        name: 'Tags',
        component: Tags,
        meta: {
          title: 'Tags',
          icon: CursorClickIcon,
          color: 'text-primary',
          requiresAuth: true,
        },
      },
      {
        path: 'notifications',
        component: Notification,
        name: 'Notifications',
        meta: {
          title: 'Notifications',
          icon: BellIcon,
          color: 'text-success-50',
          requiresAuth: true,
        },
      },
      {
        path: 'tables',
        component: Table,
        name: 'Tables',
        meta: {
          title: 'Tables',
          icon: ViewBoardsIcon,
          color: 'text-indigo-410',
          requiresAuth: true,
        },
      },
      {
        path: 'grid',
        component: Grid,
        name: 'Grid',
        meta: {
          title: 'Grid',
          icon: ViewGridIcon,
          color: 'text-info',
          requiresAuth: true,
        },
      },
      {
        path: 'typography',
        component: Typography,
        name: 'Typography',
        meta: {
          title: 'Typography',
          icon: DocumentTextIcon,
          color: 'text-yellow-310',
          requiresAuth: true,
        },
      },
      {
        path: 'cards',
        component: Card,
        name: 'Cards',
        meta: {
          title: 'Cards',
          icon: CreditCardIcon,
          color: 'text-warning-50',
          requiresAuth: true,
        },
      },
      {
        path: 'icons',
        component: Icons,
        name: 'Icons',
        meta: {
          title: 'Icons',
          icon: StarIcon,
          color: 'text-red-410',
          requiresAuth: true,
        },
      },
    ]
  },

  {
    path: '/profile',
    component: Profile,
    name: 'Profile',
    meta: {
      title: 'Profile',
      icon: UserIcon,
      color: 'text-success-50',
      isDarkBackground: true,
      isFullWidthLayout: true,
      requiresAuth: true,
      parentPath: 'Home'
    },
  },
  {
    path: '/map',
    component: Map,
    name: 'Map',
    meta: {
      title: 'Map',
      icon: LocationMarkerIcon,
      color: 'text-red-410',
      requiresAuth: true,
      parentPath: 'Home'
    },
  },
  {
    path: '/register',
    component: Register,
    name: 'register',
    meta: {
      requiresAuth: false,
    },
  },
  {
    path: '/forgot-password',
    component: ForgotPassword,
    name: 'forgot-password',
    meta: {
      requiresAuth: false,
    },
  },
  {
    path: '/reset-password/:token', // 使用动态路由匹配来接收token
    component: ResetPassword,
    name: 'reset-password',
    meta: {
      requiresAuth: false,
    },
  },
  {

    path: '/login',
    component: Login,
    name: 'login',
    meta: {
      requiresAuth: false,
    },
  },
  {
    path: '/:pathMatch(.*)*',
    component: NotFound,
    name: 'NotFound',
    meta: {
      requiresAuth: false,
    },
  },
  {
    path: '/posts',
    component: Posts,
    name: 'Posts',
    meta: {
      title: 'Posts',
      icon: DocumentTextIcon,
      requiresAuth: true,
      parentPath: 'Home',
      color: 'text-red-410',
    },
  },
  {
    path: '/posts/:postId',
    component: PostDetail,
    name: 'Post',
    meta: {
      parentPath: 'Posts',
      requiresAuth: true,
    }
  },
  {
    path: '/welcome-chat',
    component: Chat,
    name: 'Chat',
    meta: {
      title: 'Chat',
      parentPath: 'Home',
      requiresAuth: true,
      icon: DocumentTextIcon,
      color: 'text-red-410',
    }
  },
  {
    path: '/detection',
    component: Detection,
    name: 'Detection',
    meta: {
      title: 'Detection',
      parentPath: 'Home',
      requiresAuth: true,
      icon: DocumentTextIcon,
      color: 'text-red-410',
    }
  },
  {
    path: '/library',
    component: Library,
    name: 'Library',
    meta: {
      title: 'Library',
      parentPath: 'Home',
      requiresAuth: true,
      icon: DocumentTextIcon,
      color: 'text-red-410',
    }
  },
  {
    path: '/library/:diseaseId',
    component: DiseaseDetailPage,
    name: 'DiseaseDetail',
    meta: {
      title: 'DiseaseDetail',
      parentPath: 'Library',
      requiresAuth: true,
    }
  },
  {
    path: '/chat',
    component: HomeView,
    name: 'TestWebsocket',
    meta: {
      title: 'TestWebsocket',
      parentPath: 'Home',
      requiresAuth: true,
      // icon: DocumentTextIcon,
      // color: 'text-red-410',
    }
  }

]

export default routes
