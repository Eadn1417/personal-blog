import axios from 'axios'

// 修改后
const request = axios.create({
    baseURL: '/api', // ✅ 改成相对路径
    timeout: 10000
})


// 请求拦截器
request.interceptors.request.use(
  config => {
    // 添加认证token
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    console.log('发送请求:', config.method?.toUpperCase(), config.url, config.data || config.params)
    return config
  },
  error => {
    console.error('请求错误:', error)
    return Promise.reject(error)
  }
)

// 响应拦截器
request.interceptors.response.use(
  response => {
    console.log('收到响应:', response.config.url, response.data)
    return response.data
  },
  error => {
    console.error('响应错误:', error.response?.data || error.message)

    // 处理认证失败
    if (error.response?.status === 401 ||
        (error.response?.status === 500 && error.response?.data?.message?.includes('token 无效'))) {
      localStorage.removeItem('token')
      // 只在当前不在登录页时跳转
      if (window.location.pathname !== '/login') {
        window.location.href = '/login'
      }
    }

    return Promise.reject(error)
  }
)


export default request
