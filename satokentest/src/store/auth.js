import { defineStore } from 'pinia'
import { login, logout } from '@/api/auth'
import { getCurrentUser } from '@/api/auth' // 需要添加这个API
export const useAuthStore = defineStore('auth', {
  state: () => ({
    isAuthenticated: false,
    userRole: '',
    token: localStorage.getItem('token') || ''
  }),

 getters: {
  isAdmin: (state) => {
    console.log('计算 isAdmin - currentUser:', state.currentUser)
    console.log('计算 isAdmin - role:', state.currentUser?.role)
    const result = state.currentUser?.role === 'admin'
    console.log('计算 isAdmin - result:', result)
    return result
  }
},

  actions: {
    // 登录
    async login(username, password) {
      try {
        const response = await login({username, password})
        if (response.code === 200) {
          const {tokenName, tokenValue} = response.data
          this.token = tokenValue
          this.isAuthenticated = true
          localStorage.setItem('token', tokenValue)

          // 获取用户详细信息（包括角色）
          try {
            const userResponse = await getCurrentUser()
            if (userResponse.code === 200) {
              this.currentUser = userResponse.data
            }
          } catch (error) {
            console.error('获取用户信息失败:', error)
            this.currentUser = null
          }
          console.log('登录成功')
          return {success: true}
        } else {
          return {success: false, message: response.msg}
        }
      } catch (error) {
        console.error('登录失败:', error)
        return {success: false, message: error.message}
      }
    },

    // 登出
    async doLogout() {
      try {
        await logout()
        this.clearAuth()
        console.log('登出成功')
      } catch (error) {
        console.error('登出失败:', error)
        // 即使后端登出失败，前端也要清除认证信息
        this.clearAuth()
      }
    },

    // 清除认证信息
    clearAuth() {
      this.isAuthenticated = false
      this.userRole = ''
      this.token = ''
      localStorage.removeItem('token')
    },

// 检查认证状态
    async checkAuth() {
      const token = localStorage.getItem('token')
      this.isAuthenticated = !!token
      this.token = token || ''

      // 如果已认证，获取用户信息
      if (this.isAuthenticated) {
        try {
          const response = await getCurrentUser()
          if (response.code === 200) {
            this.currentUser = response.data
          } else {
            // 如果获取用户信息失败，清除认证状态
            this.clearAuth()
          }
        } catch (error) {
          console.error('获取用户信息失败:', error)
          // 认证失败时清除本地存储的无效token
          this.clearAuth()
          this.currentUser = null
        }
      }
    }
  }
})
