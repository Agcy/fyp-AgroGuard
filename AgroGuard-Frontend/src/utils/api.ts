// src/api/index.ts
import axios, { AxiosInstance, AxiosRequestConfig, AxiosResponse } from 'axios';
import { ElNotification } from 'element-plus';
import router from 'router';

// Define interfaces for better type checking
interface ApiResponse {
  data: any;
  msg?: string;
  status?: number;
}

// Create an axios instance
const api: AxiosInstance = axios.create({
  baseURL: 'http://localhost:8081/api', // Adjusted to your needs
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json',
    'X-Requested-With': 'XMLHttpRequest',
  },
});

// Request interceptor to add auth token if available
api.interceptors.request.use((config: AxiosRequestConfig) => {
  const token = localStorage.getItem('app/token');
  if (token && config.headers) {
    config.headers['Authorization'] = `Bearer ${token}`;
  }
  return config;
});

// Response interceptor to handle global API response and errors
api.interceptors.response.use((response: AxiosResponse<ApiResponse>) => {
  if (response.status === 200 && response.data.status === 500) {
    ElNotification.error({ title: '系统讯息', message: response.data.msg || 'Error' });
    return Promise.reject(new Error(response.data.msg));
  }
  if (response.data.msg) {
    ElNotification.success({ title: '系统讯息', message: response.data.msg });
  }
  return response.data;
}, (error: any) => {
  // const status = error.response?.status;
  // switch (status) {
  //   case 401:
  //     ElNotification.error({ title: '系统讯息', message: '尚未登录，请登录' });
  //     router.replace('/');
  //     break;
  //   case 403:
  //     ElNotification.error({ title: '系统讯息', message: '权限不足，请联系管理员' });
  //     break;
  //   case 404:
  //   case 504:
  //     ElNotification.error({ title: '系统讯息', message: '服务器被吃了( ╯□╰ )' });
  //     break;
  //   default:
  //     ElNotification.error({ title: '系统讯息', message: error.response?.data?.msg || '未知错误!' });
  // }
  return Promise.reject(error);
});

// Helper methods for API requests
export const getRequest = (url: string, params?: any) => api.get(url, { params });
export const postRequest = (url: string, params: any) => api.post(url, params);
export const putRequest = (url: string, params: any) => api.put(url, params);
export const deleteRequest = (url: string, params?: any) => api.delete(url, { data: params });

export default api;
