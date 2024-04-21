<template>
  <div class="w-full h-auto">
    <el-card>
      <template #header>
        <div class="flex flex-wrap items-center">
          <div class="grow px-4">
            <h2 class="text-dark-100">{{ selectedRegion }}</h2>
            <h4 class="uppercase text-muted">{{ selectedCommodity }}</h4>

          </div>
          <div class="grow">
            <el-select v-model="selectedCommodity" placeholder="Select commodity">
              <el-option v-for="commodity in commodities" :key="commodity" :label="commodity" :value="commodity"></el-option>
            </el-select>
            <el-select v-model="selectedRegion" placeholder="Select region">
              <el-option v-for="region in regions" :key="region" :label="region" :value="region"></el-option>
            </el-select>
          </div>
        </div>
      </template>
      <div class="card-body">
        <BarChart
          :chart-data="chartData"
          :options="chartOptions"
          :height="350"
          class="h-83"
        />
      </div>
    </el-card>
  </div>
</template>


<script lang="ts">
import { defineComponent, ref, watch, onMounted } from 'vue';
import { BarChart } from 'vue-chart-3';
import { Chart, registerables } from 'chart.js';
import { apiGetAgricultureData } from 'myApi/dashboard-api/agricultureData';
import {AgricultureData} from "modules/dashboard/store/types"; // Adjust the path as necessary

Chart.register(...registerables);

export default defineComponent({
  name: 'TotalBarChart',
  components: {
    BarChart,
  },
  setup() {
    const selectedCommodity = ref('wheat'); // Default selection
    const commodities = ref(['wheat', 'corn', 'soybean', 'rice', 'soybean oil', 'oilseeds', 'cotton', 'grains', 'course grain']); // Example commodities
    const regions = ref(['China', 'United Kingdom', 'Kazakhstan', 'India', 'Southeast Asia',
      'Nigeria', 'Japan', 'Brazil', 'Bangladesh', 'Ukraine', 'Russia', 'European Union  5/', 'Canada', 'Australia', 'United States']); // Default region
    const selectedRegion = ref('China'); // Default selection
    const chartData = ref({});
    const chartOptions = ref({
      responsive: true,
      maintainAspectRatio: false,
      scales: {
        y: {
          beginAtZero: true
        }
      },
      plugins: {
        legend: {
          display: false,
        },
      },
    });
    // items: ['Output', 'Total Supply', 'Trade 2/', 'Total Use 3/', 'Ending Stocks'] // Assuming these are the fields you're interested in

    const fetchData = async () => {
      const response = await apiGetAgricultureData({
        region: selectedRegion.value,
        commodity: selectedCommodity.value,
      });

      const data = response.data
      updateChartData(data);
    };

    const updateChartData = (data: AgricultureData[]) => {
      chartData.value = {
        labels: data.map(d => d.item),
        datasets: [{
          label: `${selectedCommodity.value} in 2023/24`,
          data: data.map(d => d.value),
          backgroundColor: 'rgba(54, 162, 235, 0.5)',
          borderColor: 'rgba(54, 162, 235, 1)',
          borderWidth: 1
        }]
      };
    };

    watch(selectedCommodity, fetchData, { immediate: true });
    watch(selectedRegion, fetchData, { immediate: true });

    return {
      selectedCommodity,
      commodities,
      chartData,
      chartOptions,
      selectedRegion,
      regions,
    };
  }
});
</script>

