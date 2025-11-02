<template>
  <div class="comment-list">
    <div v-if="authStore.isAuthenticated" class="comment-form">
      <h4>发表评论</h4>
      <form @submit.prevent="submitComment" class="comment-form-content">
        <textarea
          v-model="commentForm.content"
          placeholder="请输入评论内容"
          class="comment-textarea"
          rows="3"
          required
        ></textarea>
        <div class="form-actions">
          <button
            type="submit"
            :disabled="submitting"
            class="btn btn-primary"
          >
            {{ submitting ? '提交中...' : '发表评论' }}
          </button>
        </div>
      </form>
    </div>

    <div v-if="loading" class="loading">加载评论中...</div>
    <div v-else-if="comments.length === 0" class="empty">暂无评论</div>
    <div v-else class="comments">
      <div
        v-for="comment in comments"
        :key="comment.id"
        class="comment-item"
      >
        <div class="comment-header">
          <span class="comment-user">{{ comment.username }}</span>
          <span class="comment-time">{{ formatDate(comment.createTime) }}</span>
        </div>
        <div class="comment-content">
          <div v-if="editingCommentId === comment.id">
            <textarea
              v-model="editForm.content"
              class="comment-textarea"
              rows="3"
              required
            ></textarea>
            <div class="comment-actions">
              <button
                @click="updateComment(comment.id)"
                :disabled="submitting"
                class="btn btn-primary btn-sm"
              >
                保存
              </button>
              <button
                @click="cancelEdit"
                class="btn btn-outline btn-sm"
              >
                取消
              </button>
            </div>
          </div>
          <div v-else>
            <p class="comment-text">{{ comment.content }}</p>
            <div v-if="comment.isOwn" class="comment-actions">
              <button
                @click="startEdit(comment)"
                class="btn btn-outline btn-sm"
              >
                编辑
              </button>
              <button
                @click="deleteComment(comment.id)"
                class="btn btn-danger btn-sm"
              >
                删除
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 分页 -->
    <div v-if="total > 0" class="pagination">
      <button
        :disabled="currentPage <= 1"
        @click="changePage(currentPage - 1)"
        class="pagination-btn"
      >
        上一页
      </button>
      <span class="pagination-info">
        第 {{ currentPage }} 页，共 {{ Math.ceil(total / pageSize) }} 页
      </span>
      <button
        :disabled="currentPage >= Math.ceil(total / pageSize)"
        @click="changePage(currentPage + 1)"
        class="pagination-btn"
      >
        下一页
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useAuthStore } from '@/store/auth'
import {
  getCommentList,
  createComment,
  updateComment as apiUpdateComment,
  deleteComment as apiDeleteComment
} from '@/api/comment'

const props = defineProps({
  articleId: {
    type: String,
    required: true
  },
  page: {
    type: Number,
    default: 1
  },
  size: {
    type: Number,
    default: 10
  }
})

const authStore = useAuthStore()

const comments = ref([])
const total = ref(0)
const loading = ref(false)
const submitting = ref(false)
const currentPage = ref(props.page)
const pageSize = ref(props.size)

const commentForm = ref({
  content: ''
})

const editingCommentId = ref(null)
const editForm = ref({
  content: ''
})

// 获取评论列表
const fetchComments = async (page = currentPage.value) => {
  loading.value = true
  try {
    const response = await getCommentList(props.articleId, {
      page,
      size: pageSize.value
    })

    if (response.code === 200) {
      comments.value = response.data.records
      total.value = response.data.total
      currentPage.value = page
      console.log('获取评论列表成功:', response.data)
    } else {
      console.error('获取评论列表失败:', response.msg)
    }
  } catch (error) {
    console.error('获取评论列表出错:', error)
  } finally {
    loading.value = false
  }
}

// 提交评论
const submitComment = async () => {
  if (!commentForm.value.content.trim()) {
    return
  }

  submitting.value = true
  try {
    const response = await createComment(props.articleId, {
      content: commentForm.value.content
    })

    if (response.code === 200) {
      commentForm.value.content = ''
      console.log('发表评论成功')
      // 重新加载评论列表
      await fetchComments()
    } else {
      console.error('发表评论失败:', response.msg)
    }
  } catch (error) {
    console.error('发表评论出错:', error)
  } finally {
    submitting.value = false
  }
}

// 开始编辑评论
const startEdit = (comment) => {
  editingCommentId.value = comment.id
  editForm.value.content = comment.content
}

// 取消编辑
const cancelEdit = () => {
  editingCommentId.value = null
  editForm.value.content = ''
}

// 更新评论
const updateComment = async (id) => {
  if (!editForm.value.content.trim()) {
    return
  }

  submitting.value = true
  try {
    const response = await apiUpdateComment(id, {
      content: editForm.value.content
    })

    if (response.code === 200) {
      editingCommentId.value = null
      editForm.value.content = ''
      console.log('更新评论成功')
      // 重新加载评论列表
      await fetchComments()
    } else {
      console.error('更新评论失败:', response.msg)
    }
  } catch (error) {
    console.error('更新评论出错:', error)
  } finally {
    submitting.value = false
  }
}

// 删除评论
const deleteComment = async (id) => {
  if (!confirm('确定要删除这条评论吗？')) {
    return
  }

  try {
    const response = await apiDeleteComment(id)
    if (response.code === 200) {
      console.log('删除评论成功')
      // 重新加载评论列表
      await fetchComments()
    } else {
      console.error('删除评论失败:', response.msg)
    }
  } catch (error) {
    console.error('删除评论出错:', error)
  }
}

// 格式化时间
const formatDate = (dateString) => {
  if (!dateString) return ''
  const date = new Date(dateString)
  return date.toLocaleDateString() + ' ' + date.toLocaleTimeString()
}

// 切换页面
const changePage = (page) => {
  fetchComments(page)
}

onMounted(() => {
  fetchComments()
})
</script>

<style scoped>
.comment-list {
  margin-top: 2rem;
}

.comment-form {
  margin-bottom: 2rem;
  padding-bottom: 1rem;
  border-bottom: 1px solid #eee;
}

.comment-form h4 {
  margin: 0 0 1rem 0;
  color: #333;
}

.comment-form-content {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.comment-textarea {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-family: inherit;
  font-size: 1rem;
  resize: vertical;
}

.comment-textarea:focus {
  outline: none;
  border-color: #007bff;
}

.comments {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.comment-item {
  padding: 1rem;
  border: 1px solid #eee;
  border-radius: 4px;
  background: #fafafa;
}

.comment-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 0.5rem;
  font-size: 0.9rem;
}

.comment-user {
  font-weight: 500;
  color: #333;
}

.comment-time {
  color: #999;
}

.comment-content {
  margin-top: 0.5rem;
}

.comment-text {
  margin: 0 0 1rem 0;
  line-height: 1.5;
  white-space: pre-wrap;
}

.comment-actions {
  display: flex;
  gap: 0.5rem;
}

.btn {
  padding: 0.25rem 0.5rem;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
  font-size: 0.875rem;
}

.btn-sm {
  padding: 0.25rem 0.5rem;
  font-size: 0.875rem;
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

.btn-danger {
  background: #dc3545;
  color: #fff;
}

.btn-danger:hover {
  background: #c82333;
}

.btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 1rem;
  margin-top: 2rem;
  padding-top: 1rem;
  border-top: 1px solid #eee;
}

.pagination-btn {
  padding: 0.5rem 1rem;
  border: 1px solid #ddd;
  background: #fff;
  border-radius: 4px;
  cursor: pointer;
  transition: all 0.3s;
}

.pagination-btn:hover:not(:disabled) {
  background: #f5f5f5;
}

.pagination-btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.pagination-info {
  color: #666;
}

.loading, .empty {
  text-align: center;
  padding: 2rem;
  color: #666;
}
</style>
