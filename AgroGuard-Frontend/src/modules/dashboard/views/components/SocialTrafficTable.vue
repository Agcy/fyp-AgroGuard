<template>
  <div class="w-full">
    <div class="flex flex-wrap flex-col bg-white shadow mb-7 mx-auto rounded-md">
      <div class="flex flex-wrap items-center py-2 px-6 mb-0 border-b-dark-4">
        <div class="max-w-full basis-0 grow">
          <h3 class="mb-0 cursor-auto text-primary-dark">{{ selectedCommodity }}</h3>
        </div>
        <div class="max-w-full basis-0 grow">
          <el-date-picker
            v-model="reportMonth"
            type="month"
            placeholder="Select month"
            format="YYYY-MM"
            value-format="YYYY-MM">
          </el-date-picker>
          <el-select v-model="selectedCommodity" placeholder="Select commodity">
            <el-option v-for="commodity in commodities" :key="commodity" :label="commodity" :value="commodity"></el-option>
          </el-select>
          <el-select v-model="selectedItem" placeholder="Select item">
            <el-option v-for="item in items" :key="item" :label="item" :value="item"></el-option>
          </el-select>
          <el-button type="primary" size="small" @click="fetchData">Refresh Data</el-button>
        </div>
      </div>
      <div class="block overflow-x-auto w-full">
        <el-table :data="tableData" style="width: 100%">
          <el-table-column prop="region" label="Region" min-width="120"></el-table-column>
          <el-table-column prop="item" label="Item" min-width="120"></el-table-column>
          <el-table-column prop="value" label="Value" width="110"></el-table-column>
          <el-table-column prop="commodity" label="Commodity" min-width="200"></el-table-column>
        </el-table>
      </div>
    </div>
  </div>
</template>


<script lang="ts">
import { defineComponent, ref } from 'vue';
import { ElSelect, ElOption, ElDatePicker, ElButton, ElTable, ElTableColumn } from 'element-plus';
import {apiGetAgricultureData} from "myApi/dashboard-api/agricultureData";
import {apiGeProjData} from "myApi/dashboard-api/projData";

export default defineComponent({
  name: 'CommodityDataDisplay',
  components: {
    ElSelect, ElOption, ElDatePicker, ElButton, ElTable, ElTableColumn
  },
  setup() {
    const reportMonth = ref('');
    const selectedCommodity = ref('');
    const selectedItem = ref('');
    const commodities = ref(['Wheat', 'Corn', 'Soybeans', 'Cotton', 'Sorghum', 'Barley', 'Oats', 'Soybean Oil']);
    const items = ref(['Production', 'Exports', 'Domestic Use', 'Ending Stocks']);
    const tableData = ref([]);

    const fetchData = async () => {
      const response = await apiGeProjData({
        report_month: reportMonth.value,
        commodity: selectedCommodity.value,
        item: selectedItem.value,
      });
      const data = response.data;
      console.log("Fetching data for", reportMonth.value, selectedCommodity.value, selectedItem.value);
      // Simulate fetched data
      tableData.value = data
    };

    return {
      reportMonth, selectedCommodity, selectedItem, commodities, items, tableData, fetchData
    };
  },
});
</script>

