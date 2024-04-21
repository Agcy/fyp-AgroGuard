export interface DashboardState {
  welcomeText: string
  isSBPin: boolean
  isSBOpen: boolean
}

export interface AgricultureData {
  region: string;
  commodity: string;
  item: string;
  period: string;
  value: number;
}

export interface ChartData {
  labels: string[];
  datasets: Array<{
    label: string;
    backgroundColor: string;
    borderColor: string;
    data: number[];
  }>;
}

export interface PriceData {
  date: string;
  type: string;
  category: string;
  index: number;
}
