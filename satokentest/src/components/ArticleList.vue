<template>
  <div class="article-list">
    <div v-if="loading" class="loading">加载中...</div>
    <div v-else-if="articles.length === 0" class="empty">暂无文章</div>
    <div v-else class="articles">
      <div
        v-for="article in articles"
        :key="article.id"
        class="article-item"
      >
        <h3 class="article-title">
          <router-link :to="`/article/${article.id}`" class="title-link">
            {{ article.title }}
          </router-link>
        </h3>
        <p class="article-summary">{{ article.summary }}</p>
        <div class="article-meta">
          <span class="meta-item">作者: {{ article.authorName }}</span>
          <span class="meta-item">浏览: {{ article.viewCount }}</span>
          <span class="meta-item">点赞: {{ article.likeCount }}</span>
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
import { getArticleList } from '@/api/article'

const props = defineProps({
  page: {
    type: Number,
    default: 1
  },
  size: {
    type: Number,
    default: 10
  }
})

const emit = defineEmits(['page-change'])

const articles = ref([])
const total = ref(0)
const loading = ref(false)
const currentPage = ref(props.page)
const pageSize = ref(props.size)

// 获取文章列表
const fetchArticles = async (page = currentPage.value) => {
  loading.value = true
  try {
    const response = await getArticleList({
      page,
      size: pageSize.value
    })

    if (response.code === 200) {
      articles.value = response.data.records
      total.value = response.data.total
      currentPage.value = page
      console.log('获取文章列表成功:', response.data)
    } else {
      console.error('获取文章列表失败:', response.msg)
    }
  } catch (error) {
    console.error('获取文章列表出错:', error)
  } finally {
    loading.value = false
  }
}

// 切换页面
const changePage = (page) => {
  fetchArticles(page)
  emit('page-change', page)
}

onMounted(() => {
  fetchArticles()
})
</script>

<style scoped>
.article-list {
  background: #fff;
  border-radius: 8px;
  padding: 1.5rem;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.loading, .empty {
  text-align: center;
  padding: 2rem;
  color: #666;
}

.articles {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.article-item {
  padding: 1rem 0;
  border-bottom: 1px solid #eee;
}

.article-item:last-child {
  border-bottom: none;
}

.article-title {
  margin: 0 0 0.5rem 0;
}

.title-link {
  text-decoration: none;
  color: #333;
  font-size: 1.2rem;
  transition: color 0.3s;
}

.title-link:hover {
  color: #007bff;
}

.article-summary {
  color: #666;
  margin: 0 0 1rem 0;
  line-height: 1.5;
}

.article-meta {
  display: flex;
  gap: 1rem;
  font-size: 0.9rem;
  color: #999;
}

.meta-item {
  display: flex;
  align-items: center;
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
</style>
