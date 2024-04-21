<template>
  <div class="w-full">
    <el-card class="bg-gradient-to-r from-default to-dark-100">
      <template #header>
        <div class="flex flex-wrap items-center bg-transparent">
          <div class="max-w-full basis-0 grow">
            <h6 class="uppercase text-light tracking-0.625 mb-1">{{ selectedCategory }}</h6>
            <h2 class="text-white mb-0">{{ selectedType }}</h2>
          </div>
          <div class="max-w-full basis-0 grow">
            <div class="flex flex-nowrap mb-0 pl-0 justify-end gap-x-3">
              <!--              <el-select v-model="selectedType">-->
              <!--                <el-option value="output">output</el-option>-->
              <!--                <el-option value="input">input</el-option>-->
              <!--              </el-select>-->
              <el-select v-model="selectedCategory">
                <el-option
                  v-for="category in categories"
                  :key="category.value"
                  :label="category.label"
                  :value="category.value">
                </el-option>
              </el-select>
              <div>
                <el-button
                  class="el-button--secondary py-2 px-3 lh:w-20 lg:h-9"
                  size="small"
                  @click="fetchData()"
                >
                  <span class="hidden md:block text-indigo-410 font-medium text-sm px-2">Refresh Data</span>
                  <span class="md:hidden text-indigo-410 font-medium text-sm">W</span>
                </el-button>
              </div>
            </div>
          </div>
        </div>
      </template>

      <div class="card-body bg-transparent">
        <LineChart :chartData="chartData" :options="chartOptions" :height="350"/>
      </div>
    </el-card>
  </div>
</template>

<script lang="ts">
import {ref, onMounted, defineComponent, watch} from 'vue';
import {LineChart} from 'vue-chart-3';
import {Chart, registerables} from 'chart.js';
import {apiGePriceData} from 'myApi/dashboard-api/priceData';
import {ChartData, AgricultureData, PriceData} from '../../store/types';

Chart.register(...registerables);

export default defineComponent({
  name: 'AgricultureLineChart',
  components: {
    LineChart
  },
  setup() {
    const chartData = ref<ChartData | null>(null);
    const categories = ref([
      {value: 'all_agricultural_outputs', label: 'All Agricultural Outputs'},
      {value: 'wheat', label: 'Wheat'},
      {value: 'calves', label: 'Calves'},
      {value: 'milk', label: 'Milk'},
      {value: 'soya_beans', label: 'Soybeans'},
      {value: 'oilcakes', label: 'Oil Cakes'},
      {value: 'pigs', label: 'Pigs'},
      {value: 'eggs', label: 'Eggs'},
      {value: 'field_peas', label: 'Peas'},
      {value: 'tractors', label: 'Tractors'},
      {value: 'sunflower_seed_meal', label: 'Sunflower Seed'},
      {value: 'feed_oats', label: 'Oats'},
      {value: 'other_fertilisers_and_soil_improvers', label: 'Fertilisers'},
      {value: 'chickens', label: 'Chickens'},
    ]);
    const tradeType = ref('output');
    const selectedType = ref('output');
    const selectedCategory = ref('wheat');
    const chartOptions = ref({
      responsive: true,
      maintainAspectRatio: false,
      scales: {
        y: {
          beginAtZero: true
        }
      }
    });

    const fetchData = async () => {
      try {
        const response = await apiGePriceData({
          type: 'output',
          category: selectedCategory.value
        });
        const response1 = await apiGePriceData({
          type: 'input',
          category: selectedCategory.value
        });
        console.log('output', response.data);
        console.log('input', response1.data);
        const outputData: PriceData[] = response.data;
        const inputData: PriceData[] = response1.data;
        inputData.reverse()
        outputData.reverse()
        transformToChartData(inputData, outputData);
      } catch (error) {
        console.error("Failed to load data:", error);
      }
    };

    function transformToChartData(inputData: PriceData[], outputData: PriceData[]) {
      if (inputData.length === 0) {
        selectedType.value = 'Output';
        chartData.value = {
          labels: outputData.map(item => item.date),
          datasets: [
            {
              label: `Output ${selectedCategory.value}`,
              data: outputData.map(item => item.index),
              fill: false,
              borderColor: 'rgb(75, 192, 192)',
              tension: 0.1
            },
          ],
        };
      }else{
        selectedType.value = 'Input';
        chartData.value = {
          labels: inputData.map(item => item.date),
          datasets: [
            {
              label: `Input ${selectedCategory.value}`,
              data: inputData.map(item => item.index),
              fill: false,
              borderColor: 'rgb(192, 75, 75)',
              tension: 0.1
            },
          ]
        };

      }
    }


    watch(selectedCategory, () => {
      fetchData();
    }, {deep: true});

    onMounted(() => {
      fetchData();
    });

    return {
      chartData,
      chartOptions,
      fetchData,
      selectedType,
      selectedCategory,
      categories
    };
  }
});
</script>
