<template>
  <div class="article-edit-page">
    <div class="container">
      <div class="edit-card">
        <h2 class="edit-title">{{ isEdit ? '编辑文章' : '创建文章' }}</h2>
        <form @submit.prevent="submitArticle" class="edit-form">
          <div class="form-group">
            <label for="title" class="form-label">文章标题</label>
            <input
              id="title"
              v-model="articleForm.title"
              type="text"
              class="form-input"
              placeholder="请输入文章标题"
              required
            />
          </div>
          <div class="form-group">
            <label for="content" class="form-label">文章内容</label>
            <textarea
              id="content"
              v-model="articleForm.content"
              class="form-textarea"
              placeholder="请输入文章内容"
              rows="15"
              required
            ></textarea>
          </div>
          <div class="form-actions">
            <button
              type="submit"
              :disabled="submitting"
              class="btn btn-primary"
            >
              {{ submitting ? '提交中...' : (isEdit ? '更新文章' : '发布文章') }}
            </button>
            <router-link to="/" class="btn btn-outline">取消</router-link>
          </div>
          <div v-if="errorMessage" class="error-message">
            {{ errorMessage }}
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getArticleDetail, createArticle, updateArticle } from '@/api/article'

const route = useRoute()
const router = useRouter()

const articleId = route.params.id
const isEdit = !!articleId

const articleForm = ref({
  title: '',
  content: ''
})

const submitting = ref(false)
const errorMessage = ref('')

// 获取文章详情（编辑模式）
const fetchArticle = async () => {
  try {
    const response = await getArticleDetail(articleId)
    if (response.code === 200) {
      articleForm.value.title = response.data.title
      articleForm.value.content = response.data.content
      console.log('获取文章详情成功:', response.data)
    } else {
      console.error('获取文章详情失败:', response.msg)
      router.push('/')
    }
  } catch (error) {
    console.error('获取文章详情出错:', error)
    router.push('/')
  }
}

// 提交文章
const submitArticle = async () => {
  if (!articleForm.value.title.trim() || !articleForm.value.content.trim()) {
    errorMessage.value = '请填写完整的文章信息'
    return
  }

  submitting.value = true
  errorMessage.value = ''

  try {
    let response
    if (isEdit) {
      response = await updateArticle(articleId, articleForm.value)
    } else {
      response = await createArticle(articleForm.value)
    }

    if (response.code === 200) {
      console.log(isEdit ? '更新文章成功' : '创建文章成功')
      // 跳转到文章详情页或首页
      const targetId = isEdit ? articleId : response.data.id
      router.push(`/article/${targetId}`)
    } else {
      errorMessage.value = response.msg
    }
  } catch (error) {
    errorMessage.value = isEdit ? '更新文章失败' : '创建文章失败'
    console.error(isEdit ? '更新文章出错:' : '创建文章出错:', error)
  } finally {
    submitting.value = false
  }
}

onMounted(() => {
  if (isEdit) {
    fetchArticle()
  }
})
</script>

<style scoped>
.article-edit-page {
  padding: 2rem 0;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 1rem;
}

.edit-card {
  background: #fff;
  border-radius: 8px;
  padding: 2rem;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  max-width: 800px;
  margin: 0 auto;
}

.edit-title {
  text-align: center;
  margin: 0 0 1.5rem 0;
  color: #333;
}

.edit-form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.form-label {
  font-weight: 500;
  color: #333;
}

.form-input {
  padding: 0.75rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 1rem;
  transition: border-color 0.3s;
}

.form-input:focus {
  outline: none;
  border-color: #007bff;
}

.form-textarea {
  padding: 0.75rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-family: inherit;
  font-size: 1rem;
  resize: vertical;
  transition: border-color 0.3s;
}

.form-textarea:focus {
  outline: none;
  border-color: #007bff;
}

.form-actions {
  display: flex;
  gap: 1rem;
  margin-top: 1rem;
  padding-top: 1rem;
  border-top: 1px solid #eee;
}

.btn {
  padding: 0.75rem 1.5rem;
  border: none;
  border-radius: 4px;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.3s;
  text-decoration: none;
  display: inline-block;
  text-align: center;
}

.btn-primary {
  background: #007bff;
  color: #fff;
}

.btn-primary:hover:not(:disabled) {
  background: #0056b3;
}

.btn-outline {
  background: transparent;
  border: 1px solid #333;
  color: #333;
}

.btn-outline:hover {
  background: #f5f5f5;
}

.btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.error-message {
  color: #dc3545;
  text-align: center;
  padding: 0.5rem;
  background: #f8d7da;
  border-radius: 4px;
  border: 1px solid #f5c6cb;
  margin-top: 1rem;
}
</style>
