import axios from 'axios'


const service = axios.create({
  baseURL: 'http://localhost:8080/api',
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json',
    'X-Requested-With': 'XMLHttpRequest'
  },
})

// 定义白名单路径
// const authPathRegex = /^\/api\/auth\//;

// 添加请求拦截器
service.interceptors.request.use(
  function (config) {
    // 定义一个白名单路径的正则表达式
    const authPathRegex = /^\/api\/auth\//;
    // 确保 config.url 是一个字符串
    const url = config.url || '';

    // 检查当前请求的 URL 是否在白名单中
    if (!authPathRegex.test(url)) {
      // 如果不在白名单中，则尝试添加 token 到请求头
      const token = localStorage.getItem('app/token');
      if (token) {
        config.headers['Authorization'] = `Bearer ${token}`;
        // config.headers.append(`Bearer ${token}`)
        console.log(config.headers)
      }
    }

    return config;
  },
  function (error) {
    console.log(error);
    return Promise.reject(error);
  }
);

// 添加响应拦截器
service.interceptors.response.use(
  function (response) {
    console.log(response)
    // 2xx 范围内的状态码都会触发该函数。
    // 对响应数据做点什么
    // dataAxios 是 axios 返回数据中的 data
    const dataAxios = response
    // 这个状态码是和后端约定的
    const code = dataAxios.data.reset
    return dataAxios
  },
  function (error) {
    // 超出 2xx 范围的状态码都会触发该函数。
    // 对响应错误做点什么
    console.log(error)
    return Promise.reject(error)
  }
)

export default service
