<template>
  <header class="header">
    <div class="container">
      <div class="header-left">
        <router-link to="/" class="logo">个人博客</router-link>
      </div>
      <div class="header-right">
        <nav class="nav">
          <router-link to="/" class="nav-item">首页</router-link>
          <template v-if="authStore.isAuthenticated">
<!--            &lt;!&ndash; 添加调试信息 &ndash;&gt;-->
<!--            <span style="color: red; font-size: 12px;">-->
<!--              isAdmin: {{ authStore.isAdmin }}-->
<!--            </span>-->
            <router-link
              v-if="authStore.isAdmin"
              to="/article/edit"
              class="nav-item"
            >
              写文章
            </router-link>
            <button @click="handleLogout" class="btn btn-outline">退出</button>
            <span class="user-info">欢迎, {{ authStore.isAdmin ? '管理员' : '用户' }}:{{authStore.currentUser.username}}</span>
          </template>
          <template v-else>
            <router-link to="/login" class="nav-item">登录</router-link>
            <router-link to="/register" class="nav-item">注册</router-link>
          </template>
        </nav>
      </div>
    </div>
  </header>
</template>

<script setup>
import { useAuthStore } from '@/store/auth'
import { onMounted } from 'vue'

const authStore = useAuthStore()

// 组件挂载时检查认证状态
onMounted(() => {
  console.log('Header mounted - isAuthenticated:', authStore.isAuthenticated)
  console.log('Header mounted - currentUser:', authStore.currentUser)
  console.log('Header mounted - isAdmin:', authStore.isAdmin)
})



const handleLogout = async () => {
  await authStore.doLogout()
}
</script>


<style scoped>
.header {
  background: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  padding: 0.8rem 0;
  position: sticky;
  top: 0;
  z-index: 100;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 1rem;
}

.logo {
  font-size: 1.5rem;
  font-weight: bold;
  color: #333;
  text-decoration: none;
}

.nav {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.nav-item {
  text-decoration: none;
  color: #333;
  padding: 0.5rem 1rem;
  border-radius: 4px;
  transition: background-color 0.3s;
}

.nav-item:hover {
  background-color: #f5f5f5;
}

.btn {
  padding: 0.5rem 1rem;
  border-radius: 4px;
  cursor: pointer;
  border: none;
  font-size: 1rem;
}

.btn-outline {
  background: transparent;
  border: 1px solid #333;
  color: #333;
}

.btn-outline:hover {
  background-color: #f5f5f5;
}

.user-info {
  margin-left: 1rem;
  color: #666;
}
</style>
