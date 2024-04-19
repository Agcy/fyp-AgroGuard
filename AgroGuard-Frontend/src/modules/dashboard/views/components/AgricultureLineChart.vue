<template>
  <div class="w-full">
    <el-card class="bg-gradient-to-r from-default to-dark-100">
      <template #header>
        <div class="flex flex-wrap items-center bg-transparent">
          <div class="max-w-full basis-0 grow">
            <h6 class="uppercase text-light tracking-0.625 mb-1">{{ title }}</h6>
            <h2 class="text-white mb-0">{{ subcription }}</h2>
          </div>
          <div class="max-w-full basis-0 grow">
            <div class="flex flex-nowrap mb-0 pl-0 justify-end gap-x-3">
              <div>
                <el-select v-model="selectedRegion" placeholder="Select region">
                  <el-option
                    v-for="region in uniqueRegions"
                    :key="region"
                    :label="region"
                    :value="region">
                  </el-option>
                </el-select>
                <el-select v-model="selectedPeriod" placeholder="Select period">
                  <el-option
                    v-for="period in uniquePeriods"
                    :key="period"
                    :label="period"
                    :value="period">
                  </el-option>
                </el-select>
                <el-select v-model="selectedItem" placeholder="Select item">
                  <el-option
                    v-for="item in uniqueItems"
                    :key="item"
                    :label="item"
                    :value="item">
                  </el-option>
                </el-select>
                <el-button
                  type="primary"
                  size="small"
                  class="py-2 px-3 lh:w-20 lg:h-9"
                  @click="filterAndLoadData"
                >
                  <span class="hidden md:block font-medium text-sm px-2">Refresh Data</span>
                </el-button>
              </div>
            </div>
          </div>
        </div>
      </template>

      <div class="card-body bg-transparent">
        <LineChart :chart-data="filteredChartData" :options="chartOptions" :height="350" />
      </div>
    </el-card>
  </div>
</template>

<script lang="ts">
import {computed, defineComponent, onMounted, ref, Ref } from 'vue'
import { LineChart } from 'vue-chart-3'
import { Chart, registerables } from 'chart.js'
import { apiGetAgricultureData } from 'myApi/dashboard-api/agricultureData'

Chart.register(...registerables)

export default defineComponent({
  name: 'AgricultureLineChart',
  components: {
    LineChart,
  },
  props: {
    title: {
      type: String,
      default: 'Grain Situation',
    },
    subcription: {
      type: String,
      default: 'Overview',
    },
  },
  setup() {
    const chartOptions = ref({
      responsive: true,  // 确保图表响应式地调整大小
      maintainAspectRatio: false,  // 不保持宽高比
      scales: {
        x: {
          title: {
            display: true,
            text: 'Month'  // X轴的标题
          }
        },
        y: {
          beginAtZero: true,  // Y轴从0开始
          title: {
            display: true,
            text: 'Value'  // Y轴的标题
          }
        }
      },
      plugins: {
        legend: {
          display: true  // 是否显示图例
        },
        tooltip: {
          enabled: true,  // 启用工具提示
          mode: 'index',
          intersect: false
        }
      }
    });

    const data: Ref<number[]> = ref([])
    const labels: Ref<string[]> = ref([])
    const salesData = ref({
      labels: labels.value,
      datasets: [{
        label: 'Performance',
        tension: 0.4,
        pointRadius: 0,
        borderColor: 'rgb(94 114 228)',
        backgroundColor: 'rgba(23, 43, 77, 0.01)',
        borderWidth: 4,
        fill: true,
        data: data.value,
      }]
    })

    // const chartOptions = ref({
    //   responsive: true,
    //   maintainAspectRatio: false,
    //   // other options...
    // })

    const rawData = ref([]);
    const selectedRegion = ref('');
    const selectedPeriod = ref('');
    const selectedItem = ref('');

    const uniqueRegions = computed(() => Array.from(new Set(rawData.value.map(item => item.region))));
    const uniquePeriods = computed(() => Array.from(new Set(rawData.value.map(item => item.period))));
    const uniqueItems = computed(() => Array.from(new Set(rawData.value.map(item => item.item))));

    const filteredChartData = computed(() => {
      const datasets = [{
        label: 'Performance',
        tension: 0.4,
        pointRadius: 0,
        borderColor: 'rgb(94 114 228)',
        backgroundColor: 'rgba(23, 43, 77, 0.01)',
        borderWidth: 4,
        fill: true,
        data: rawData.value.filter(item =>
          (selectedRegion.value === '' || item.region === selectedRegion.value) &&
          (selectedPeriod.value === '' || item.period === selectedPeriod.value) &&
          (selectedItem.value === '' || item.item === selectedItem.value)
        ).map(item => item.value)
      }];
      return {
        labels: rawData.value.filter(item =>
          (selectedRegion.value === '' || item.region === selectedRegion.value) &&
          (selectedPeriod.value === '' || item.period === selectedPeriod.value) &&
          (selectedItem.value === '' || item.item === selectedItem.value)
        ).map(item => item.item),
        datasets
      };
    });

    async function loadAgricultureData() {
      try {
        const response = await apiGetAgricultureData("WHEAT_WORLD_19");
        rawData.value = response.data;
      } catch (error) {
        console.error('Failed to fetch agriculture data', error);
      }
    }

    function filterAndLoadData() {
      loadAgricultureData();
    }

    onMounted(() => {
      loadAgricultureData();
    });

    onMounted(() => {
      loadAgricultureData()
    })

    return {
      selectedRegion,
      selectedPeriod,
      selectedItem,
      uniqueRegions,
      uniquePeriods,
      uniqueItems,
      filteredChartData,
      filterAndLoadData,
      chartOptions
    }
  },
})
</script>
