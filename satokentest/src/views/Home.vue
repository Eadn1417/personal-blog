<template>
  <div class="home">
    <div class="container">
      <ArticleList />
    </div>
  </div>
</template>

<script setup>
import ArticleList from '@/components/ArticleList.vue'
</script>

<style scoped>
.home {
  padding: 2rem 0;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 1rem;
}
</style>
