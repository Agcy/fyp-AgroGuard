<template>
  <div class="map">
    <GoogleMap :api-key="googleApi" class="w-full h-[600px]" :center="center" :zoom="5" :language=en>
      <Marker
        v-for="marker in markerOptions"
        :key="marker.id"
        :options="{
          position: marker.position,
          label: marker.label,
          title: marker.title
        }"
        @click="openInfoWindow(marker.id)"
      />
      <InfoWindow
        v-if="activeMarker"
        :options="{ position: activeMarker.position }"
        :opened="true"
        @closeclick="activeMarker = null"
      >
        <div>
          <h1>{{ activeMarker.title }}</h1>
          <p>Location: {{ activeMarker.label }}</p>
        </div>
      </InfoWindow>
    </GoogleMap>
  </div>
</template>

<script>
import env from 'core/env'
import { defineComponent, ref } from 'vue'
import { GoogleMap, Marker, InfoWindow } from 'vue3-google-map'

export default defineComponent({
  components: { GoogleMap, Marker, InfoWindow },
  setup() {
    const googleApi = ref(env('VITE_GOOGLE_API_KEY'))
    const center = { lat: 34.047863, lng: 100.619655 }
    const markerOptions = ref([
      { id: 1, position: { lat: 53.1424, lng: -7.6921 }, label: 'IR', title: 'Potato Blight in Ireland' },
      { id: 2, position: { lat: 48.8566, lng: 2.3522 }, label: 'FR', title: 'Grapevine Disease in France' },
      { id: 3, position: { lat: 51.1657, lng: 10.4515 }, label: 'DE', title: 'Wheat Rust in Germany' },
      { id: 4, position: { lat: 35.8617, lng: 104.1954 }, label: 'CN', title: 'Rice Blast in China' },
      { id: 5, position: { lat: 39.9042, lng: 116.4074 }, label: 'CN', title: 'Apple Scab Disease in Beijing' },
      { id: 6, position: { lat: 31.2304, lng: 121.4737 }, label: 'CN', title: 'Citrus Huanglongbing (HLB) in Shanghai' },
      { id: 7, position: { lat: 23.1291, lng: 113.2644 }, label: 'CN', title: 'Litchi Downy Blight in Guangzhou' },
      { id: 8, position: { lat: 30.5728, lng: 104.0668 }, label: 'CN', title: 'Bamboo Rust in Chengdu' },
      { id: 9, position: { lat: 34.3416, lng: 108.9398 }, label: 'CN', title: 'Grape Powdery Mildew in Xi\'an' },
      { id: 10, position: { lat: 22.3964, lng: 114.1095 }, label: 'HK', title: 'Orchid Bacterial Brown Spot in Hong Kong' },
      { id: 11, position: { lat: 25.0320, lng: 121.5654 }, label: 'TW', title: 'Pineapple Disease in Taiwan' },
      { id: 12, position: { lat: 35.6895, lng: 139.6917 }, label: 'JP', title: 'Tomato Leaf Curl Virus in Tokyo' },
      { id: 13, position: { lat: 37.7749, lng: -122.4194 }, label: 'US', title: 'Grape Black Rot in San Francisco' },
      { id: 14, position: { lat: 40.7128, lng: -74.0060 }, label: 'US', title: 'Corn Smut in New York' },
      { id: 15, position: { lat: 51.5074, lng: -0.1278 }, label: 'UK', title: 'Wheat Stripe Rust in London' },
      { id: 16, position: { lat: 55.7558, lng: 37.6176 }, label: 'RU', title: 'Potato Late Blight in Moscow' },
      { id: 17, position: { lat: 59.3293, lng: 18.0686 }, label: 'SE', title: 'Barley Scald in Stockholm' },
      { id: 18, position: { lat: 55.6761, lng: 12.5683 }, label: 'DK', title: 'Sugar Beet Rhizomania in Copenhagen' },
      { id: 19, position: { lat: 52.5200, lng: 13.4050 }, label: 'DE', title: 'Tomato Yellow Leaf Curl Virus in Berlin' },
      { id: 20, position: { lat: 41.9028, lng: 12.4964 }, label: 'IT', title: 'Olive Anthracnose in Rome' },
      { id: 21, position: { lat: 36.2048, lng: 138.2529 }, label: 'JP', title: 'Soybean Rust in Japan' },
      { id: 22, position: { lat: 23.4241, lng: 53.8478 }, label: 'UAE', title: 'Date Palm Disease in UAE' },
      { id: 23, position: { lat: 60.4720, lng: 8.4689 }, label: 'NO', title: 'Barley Blight in Norway' },
      { id: 24, position: { lat: 28.7041, lng: 77.1025 }, label: 'IN', title: 'Wheat Rust in Delhi' },
      { id: 25, position: { lat: 19.0760, lng: 72.8777 }, label: 'IN', title: 'Mango Malformation Disease in Mumbai' },
      { id: 26, position: { lat: 12.9716, lng: 77.5946 }, label: 'IN', title: 'Coffee Rust in Bangalore' },
      { id: 27, position: { lat: 22.5726, lng: 88.3639 }, label: 'IN', title: 'Rice Blast in Kolkata' },
      { id: 28, position: { lat: 13.0827, lng: 80.2707 }, label: 'IN', title: 'Banana Bunchy Top Virus in Chennai' },
      { id: 29, position: { lat: -33.8688, lng: 151.2093 }, label: 'AU', title: 'Wheat Stem Rust in Sydney' },
      { id: 30, position: { lat: -37.8136, lng: 144.9631 }, label: 'AU', title: 'Apple Black Spot in Melbourne' },
      { id: 31, position: { lat: -36.8485, lng: 174.7633 }, label: 'NZ', title: 'Kiwifruit Canker in Auckland' },
      { id: 32, position: { lat: -41.2865, lng: 174.7762 }, label: 'NZ', title: 'Potato Late Blight in Wellington' },
      { id: 33, position: { lat: -9.4438, lng: 147.1803 }, label: 'PG', title: 'Coconut Lethal Yellowing in Port Moresby' }
    ])
    const activeMarker = ref(null)
    const openInfoWindow = (markerId) => {
      const marker = markerOptions.value.find(m => m.id === markerId)
      activeMarker.value = marker
    }

    return { center, markerOptions, googleApi, activeMarker, openInfoWindow }
  },
})
</script>

<style lang="scss" scoped>
.map {
  @apply sm:w-[92%] md:w-[94%] lg:w-[96%] w-[92%] rounded-lg overflow-hidden p-0 mx-auto #{!important};
}
</style>
