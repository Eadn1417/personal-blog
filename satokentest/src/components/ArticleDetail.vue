<template>
  <div class="article-page">
    <div class="container">
      <div v-if="loading" class="loading">加载中...</div>
      <div v-else-if="article" class="article-content">
        <div class="article-header">
          <h1 class="article-title">{{ article.title }}</h1>
          <div class="article-meta">
            <span class="meta-item">作者: {{ article.authorName }}</span>
            <span class="meta-item">浏览: {{ article.viewCount }}</span>
            <span class="meta-item">点赞: {{ article.likeCount }}</span>
          </div>
        </div>

        <div class="article-body">
          <div class="article-text" v-html="article.content"></div>
        </div>

        <div class="article-actions">
          <button
            @click="toggleLike"
            :class="['like-btn', { liked: article.isLiked }]"
            :disabled="likeLoading"
          >
            {{ likeLoading ? '处理中...' : (article.isLiked ? '取消点赞' : '点赞') }}
          </button>

          <div v-if="article.currentUserRole === 'admin'" class="admin-actions">
            <router-link
              :to="`/article/edit/${article.id}`"
              class="btn btn-outline"
            >
              编辑
            </router-link>
            <button
              @click="deleteArticleHandler"
              class="btn btn-danger"
            >
              删除
            </button>
          </div>
        </div>
      </div>

      <div v-else class="error">文章不存在</div>

      <!-- 评论区域 -->
      <div v-if="article" class="comments-section">
        <h3>评论</h3>
        <CommentList :article-id="articleId" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useAuthStore } from '@/store/auth'
import { getArticleDetail, likeArticle, unlikeArticle, deleteArticle } from '@/api/article'
import CommentList from '@/components/CommentList.vue'

const route = useRoute()
const router = useRouter()
const authStore = useAuthStore()

const articleId = route.params.id
const article = ref(null)
const loading = ref(false)
const likeLoading = ref(false)

// 获取文章详情
const fetchArticle = async () => {
  loading.value = true
  try {
    const response = await getArticleDetail(articleId)
    if (response.code === 200) {
      article.value = response.data
      console.log('获取文章详情成功:', response.data)
    } else {
      console.error('获取文章详情失败:', response.msg)
    }
  } catch (error) {
    console.error('获取文章详情出错:', error)
  } finally {
    loading.value = false
  }
}

// 点赞/取消点赞
const toggleLike = async () => {
  if (!authStore.isAuthenticated) {
    router.push('/login')
    return
  }

  if (!article.value) return

  likeLoading.value = true
  try {
    let response
    if (article.value.isLiked) {
      response = await unlikeArticle(articleId)
    } else {
      response = await likeArticle(articleId)
    }

    if (response.code === 200) {
      article.value.isLiked = !article.value.isLiked
      article.value.likeCount = response.data.likeCount
      console.log(article.value.isLiked ? '点赞成功' : '取消点赞成功')
    } else {
      console.error('点赞操作失败:', response.msg)
    }
  } catch (error) {
    console.error('点赞操作出错:', error)
  } finally {
    likeLoading.value = false
  }
}

// 删除文章
const deleteArticleHandler = async () => {
  if (!confirm('确定要删除这篇文章吗？')) {
    return
  }

  try {
    const response = await deleteArticle(articleId)
    if (response.code === 200) {
      console.log('删除文章成功')
      router.push('/')
    } else {
      console.error('删除文章失败:', response.msg)
    }
  } catch (error) {
    console.error('删除文章出错:', error)
  }
}

onMounted(() => {
  fetchArticle()
})
</script>

<style scoped>
.article-page {
  padding: 2rem 0;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 1rem;
}

.loading, .error {
  text-align: center;
  padding: 2rem;
  color: #666;
}

.article-content {
  background: #fff;
  border-radius: 8px;
  padding: 2rem;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  margin-bottom: 2rem;
}

.article-header {
  margin-bottom: 2rem;
}

.article-title {
  margin: 0 0 1rem 0;
  color: #333;
  line-height: 1.3;
}

.article-meta {
  display: flex;
  gap: 1rem;
  font-size: 0.9rem;
  color: #999;
  border-top: 1px solid #eee;
  padding-top: 1rem;
}

.meta-item {
  display: flex;
  align-items: center;
}

.article-body {
  margin-bottom: 2rem;
  line-height: 1.8;
}

.article-text {
  white-space: pre-wrap;
}

.article-actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-top: 1rem;
  border-top: 1px solid #eee;
}

.like-btn {
  padding: 0.5rem 1rem;
  border: 1px solid #007bff;
  background: #fff;
  color: #007bff;
  border-radius: 4px;
  cursor: pointer;
  transition: all 0.3s;
}

.like-btn:hover:not(:disabled) {
  background: #007bff;
  color: #fff;
}

.like-btn.liked {
  background: #007bff;
  color: #fff;
}

.like-btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.admin-actions {
  display: flex;
  gap: 0.5rem;
}

.btn {
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.btn-outline {
  background: transparent;
  border: 1px solid #333;
  color: #333;
}

.btn-outline:hover {
  background: #f5f5f5;
}

.btn-danger {
  background: #dc3545;
  color: #fff;
}

.btn-danger:hover {
  background: #c82333;
}

.comments-section {
  background: #fff;
  border-radius: 8px;
  padding: 2rem;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.comments-section h3 {
  margin: 0 0 1rem 0;
  color: #333;
}
</style>
