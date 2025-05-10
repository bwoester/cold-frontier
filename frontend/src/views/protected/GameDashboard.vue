<script setup lang="ts">
import { ref, onMounted } from 'vue';
import ProtectedLayout from "@/components/protected/ProtectedLayout.vue";
import GameNavigation from "@/components/protected/GameNavigation.vue";

// Mock data for resources
const resources = ref({
  minerals: {
    name: 'Minerals',
    stock: 15420,
    production: '+247/h',
    capacity: 25000,
    icon: '💎'
  },
  energy: {
    name: 'Energy',
    stock: 8932,
    production: '+128/h',
    capacity: 12000,
    icon: '⚡'
  },
  biomass: {
    name: 'Biomass',
    stock: 7453,
    production: '+92/h',
    capacity: 10000,
    icon: '🌱'
  },
  technology: {
    name: 'Research',
    stock: 576,
    production: '+8/h',
    capacity: 1000,
    icon: '🔬'
  }
});

// Mock data for planet info
const currentPlanet = ref({
  name: 'Borealis VI',
  coordinates: 'Sector 3-2-7',
  type: 'Ice Planet',
  size: 'Medium (8.5k km²)',
  temperature: '-85°C',
  atmosphere: 'Thin, mostly nitrogen',
  habitability: '62%',
  defense: '37%',
  scannerRange: '45 parsecs',
  image: '/assets/planets/ice-planet-1.jpg'
});

// Mock data for building production
const buildingProductions = ref([
  {
    planet: 'Borealis VI',
    building: 'Mineral Extractor',
    progress: 68,
    timeRemaining: '2h 15m'
  },
  {
    planet: 'Ignis Prime',
    building: 'Energy Core',
    progress: 42,
    timeRemaining: '4h 30m'
  }
]);

// Mock data for research progress
const researchProgress = ref({
  technology: 'Advanced Propulsion Systems',
  description: 'Improves fleet travel speed by 15%',
  progress: 34,
  timeRemaining: '6h 20m'
});

// Current time
const currentTime = ref('');

const updateTime = () => {
  const now = new Date();
  currentTime.value = now.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', second: '2-digit' });
};

onMounted(() => {
  updateTime();
  setInterval(updateTime, 1000);
});
</script>

<template>
  <ProtectedLayout>

    <div class="flex h-screen">

      <!-- Left Navigation Panel -->
      <GameNavigation />

      <!-- Main Content Area -->
      <main class="flex-1 overflow-y-auto p-6">
        <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">

          <!-- Planet Resources Section -->
          <!--
          <div class="command-panel lg:col-span-2">
            <div class="panel-header">
              <h2>Planet Resources</h2>
              <div class="text-sm text-blue-300">{{ currentPlanet.name }}</div>
            </div>
            <div class="panel-content">
              <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
                <div v-for="(resource, key) in resources" :key="key" class="resource-card">
                  <div class="resource-icon">{{ resource.icon }}</div>
                  <div class="resource-info">
                    <h3>{{ resource.name }}</h3>
                    <div class="resource-stats">
                      <div class="text-lg font-mono">{{ resource.stock.toLocaleString() }}</div>
                      <div class="text-xs mt-1 flex justify-between">
                        <span class="text-green-400">{{ resource.production }}</span>
                        <span>/ {{ resource.capacity.toLocaleString() }}</span>
                      </div>
                    </div>
                    <div class="resource-bar">
                      <div class="resource-progress" :style="{width: `${(resource.stock / resource.capacity) * 100}%`}"></div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          -->

          <!-- Planet Info Card -->
          <!--
          <div class="command-panel">
            <div class="panel-header">
              <h2>Planet Information</h2>
              <div class="text-sm text-blue-300">{{ currentPlanet.coordinates }}</div>
            </div>
            <div class="panel-content">
              <div class="planet-image mb-4">
                <div class="planet-placeholder">
                  [{{ currentPlanet.type }} Visualization]
                </div>
              </div>
              <div class="grid grid-cols-2 gap-y-2 gap-x-4 text-sm">
                <div class="stat-label">Type:</div>
                <div>{{ currentPlanet.type }}</div>
                <div class="stat-label">Size:</div>
                <div>{{ currentPlanet.size }}</div>
                <div class="stat-label">Temperature:</div>
                <div>{{ currentPlanet.temperature }}</div>
                <div class="stat-label">Atmosphere:</div>
                <div>{{ currentPlanet.atmosphere }}</div>
                <div class="stat-label">Habitability:</div>
                <div>
                  <div class="stat-bar">
                    <div class="stat-progress bg-green-500" :style="{width: currentPlanet.habitability}"></div>
                  </div>
                </div>
                <div class="stat-label">Defense:</div>
                <div>
                  <div class="stat-bar">
                    <div class="stat-progress bg-red-500" :style="{width: currentPlanet.defense}"></div>
                  </div>
                </div>
                <div class="stat-label">Scanner Range:</div>
                <div>{{ currentPlanet.scannerRange }}</div>
              </div>
            </div>
          </div>
          -->

          <!-- Building Production Status -->
          <!--
          <div class="command-panel">
            <div class="panel-header">
              <h2>Building Production</h2>
              <div class="text-sm text-blue-300">All Planets</div>
            </div>
            <div class="panel-content">
              <div v-if="buildingProductions.length > 0">
                <div v-for="(build, index) in buildingProductions" :key="index" class="production-item">
                  <div class="flex justify-between mb-1">
                    <div>
                      <span class="font-medium">{{ build.building }}</span>
                      <span class="text-xs text-blue-300 ml-2">{{ build.planet }}</span>
                    </div>
                    <div class="text-xs">{{ build.timeRemaining }}</div>
                  </div>
                  <div class="production-bar">
                    <div class="production-progress" :style="{width: `${build.progress}%`}"></div>
                  </div>
                </div>
              </div>
              <div v-else class="text-center py-4 text-blue-300">
                No active construction
              </div>
            </div>
          </div>
          -->

          <!-- Research Progress -->
          <!--
          <div class="command-panel">
            <div class="panel-header">
              <h2>Research Progress</h2>
              <div class="text-sm text-blue-300">Empire Research</div>
            </div>
            <div class="panel-content">
              <div class="flex flex-col h-full">
                <h3 class="text-lg font-medium mb-1">{{ researchProgress.technology }}</h3>
                <p class="text-sm text-blue-300 mb-3">{{ researchProgress.description }}</p>
                <div class="flex justify-between mb-1 text-xs">
                  <span>Progress</span>
                  <span>{{ researchProgress.timeRemaining }} remaining</span>
                </div>
                <div class="research-bar">
                  <div class="research-progress" :style="{width: `${researchProgress.progress}%`}"></div>
                </div>
              </div>
            </div>
          </div>
          -->

          <!-- Current Time -->
          <!--
          <div class="command-panel lg:col-span-1">
            <div class="panel-header">
              <h2>Galactic Standard Time</h2>
            </div>
            <div class="panel-content">
              <div class="text-center">
                <div class="text-3xl font-mono tracking-wide">{{ currentTime }}</div>
                <div class="text-xs text-blue-300 mt-1">Universal Coordinated Time</div>
              </div>
            </div>
          </div>
          -->
        </div>
      </main>
    </div>
  </ProtectedLayout>
</template>

<style scoped>

</style>
