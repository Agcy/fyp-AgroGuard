<template>
  <div class="w-full">
    <div class="flex flex-wrap flex-col bg-white shadow mb-7 mx-auto rounded-md">
      <div class="flex flex-wrap items-center py-2 px-6 mb-0 border-b-dark-4">
        <div class="max-w-full basis-0 grow">
          <h3 class="mb-0 cursor-auto text-primary-dark">{{ title }}</h3>
        </div>
      </div>
      <div class="block overflow-x-auto w-full p-0">
        <el-table :data="paginatedData" style="width: 100%">
          <el-table-column label="Source" prop="source" min-width="200">
            <template #default="scope">
              {{ scope.row.source.name }}
            </template>
          </el-table-column>
          <el-table-column label="Title" prop="title" min-width="300">
            <template #default="scope">
              <a :href="scope.row.url" target="_blank">{{ scope.row.title }}</a>
            </template>
          </el-table-column>
          <el-table-column label="Description" prop="description" min-width="450">
            <template #default="scope">
              {{ scope.row.description }}
            </template>
          </el-table-column>
          <el-table-column label="Published At" prop="publishedAt" min-width="150">
            <template #default="scope">
              {{ new Date(scope.row.publishedAt).toLocaleDateString() }}
            </template>
          </el-table-column>
        </el-table>
      </div>

      <div class="pagination-container">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[10, 20, 30, 50]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="tableData.length">
        </el-pagination>
      </div>
    </div>
  </div>
</template>


<script lang="ts">
import { defineComponent, ref, onMounted, computed } from 'vue'
import { ElTable, ElTableColumn } from 'element-plus'
import { apiGetNewsData } from 'myApi/dashboard-api/newsData'

export default defineComponent({
  name: 'NewsTable',
  components: {
    ElTable, ElTableColumn
  },
  props: {
    title: {
      type: String,
      default: 'Latest Agricultural News',
    },
  },
  setup() {
    const currentPage = ref(1);
    const pageSize = ref(20);
    const tableData = ref([]);

    const fetchNews = async () => {
      try {
        const newsData = await apiGetNewsData();
        tableData.value = newsData.data.map(article => ({
          source: article.source.name,
          title: article.title,
          url: article.url,
          description: article.description,
          publishedAt: article.publishedAt
        }));
      } catch (error) {
        console.error('Error fetching news:', error);
      }
    };

    onMounted(fetchNews);

    const paginatedData = computed(() => {
      const start = (currentPage.value - 1) * pageSize.value;
      const end = start + pageSize.value;
      return tableData.value.slice(start, end);
    });

    const handleCurrentChange = (newPage) => {
      currentPage.value = newPage;
    };

    const handleSizeChange = (newSize) => {
      pageSize.value = newSize;
    };

    return {
      tableData,
      currentPage,
      pageSize,
      paginatedData,
      handleCurrentChange,
      handleSizeChange
    };
  },
});
</script>

