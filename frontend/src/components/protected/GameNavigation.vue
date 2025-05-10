<script setup lang="ts">
import { ref } from 'vue';

// Mock data for planets
const planets = ref([
  { id: 1, name: 'Borealis VI', type: 'Ice Planet', selected: true },
  { id: 2, name: 'Ignis Prime', type: 'Volcanic Moon', selected: false },
  { id: 3, name: 'Verdant Echo', type: 'Forest World', selected: false }
]);

// Navigation links
const navigationLinks = [
  { icon: '🪐', text: 'Overview', route: '/game', description: 'Planet status dashboard' },
  { icon: '🏗️', text: 'Buildings', route: '/game/buildings', description: 'Construct and upgrade planetary facilities' },
  { icon: '🔬', text: 'Research', route: '/game/research', description: 'Develop new technologies' },
  { icon: '🚀', text: 'Shipyard', route: '/game/shipyard', description: 'Build and manage your fleet' },
  { icon: '🛡️', text: 'Defense', route: '/game/defense', description: 'Construct planetary defenses' },
  { icon: '💱', text: 'Trade', route: '/game/trade', description: 'Trade resources with other players' },
  { icon: '📊', text: 'Economy', route: '/game/economy', description: 'Economic overview and forecasts' },
  { icon: '⚔️', text: 'Fleet', route: '/game/fleet', description: 'Fleet management and deployment' },
  { icon: '🌌', text: 'Universe', route: '/game/universe', description: 'Explore the galactic map' },
  { icon: '🎲', text: 'Simulator', route: '/game/simulator', description: 'Battle simulator' },
  { icon: '✉️', text: 'Messages', route: '/game/messages', description: 'Communication center' },
  { icon: '👤', text: 'Profile', route: '/game/profile', description: 'Commander profile and settings' },
  { icon: '🤝', text: 'Alliance', route: '/game/alliance', description: 'Alliance management' }
];

// Toggle planet selection
const selectPlanet = (planetId: number) => {
  planets.value = planets.value.map(planet =>
    ({...planet, selected: planet.id === planetId})
  );
};
</script>

<template>
  <aside class="frozen-panel w-64 h-full overflow-y-auto flex-shrink-0">
    <div class="p-4">
      <!-- Game Logo/Title -->
      <div class="mb-6 text-center">
        <h1 class="text-2xl font-bold bg-gradient-to-r from-blue-300 to-primary bg-clip-text text-transparent">
          Cold Frontier
        </h1>
        <div class="text-xs text-blue-300 mt-1">Command Console</div>
      </div>

      <!-- Planet Selector -->
      <div class="mb-6">
        <h2 class="text-xs uppercase tracking-wider text-blue-300 mb-2 px-2">Your Planets</h2>
        <div class="space-y-1.5">
          <button
            v-for="planet in planets"
            :key="planet.id"
            @click="selectPlanet(planet.id)"
            :class="[
              'w-full text-left px-3 py-2 rounded-lg transition-all duration-200 text-sm flex items-center',
              planet.selected
                ? 'bg-primary/20 text-light border border-primary/30'
                : 'hover:bg-darker/70 text-blue-200 border border-transparent'
            ]"
          >
            <span class="mr-2">{{ planet.selected ? '🔹' : '🔸' }}</span>
            <div>
              <div>{{ planet.name }}</div>
              <div class="text-xs opacity-70">{{ planet.type }}</div>
            </div>
          </button>
        </div>
      </div>

      <!-- Navigation Links -->
      <nav>
        <h2 class="text-xs uppercase tracking-wider text-blue-300 mb-2 px-2">Command Functions</h2>
        <div class="space-y-1">
          <RouterLink
            v-for="(link, index) in navigationLinks"
            :key="index"
            :to="link.route"
            class="nav-link"
            v-tooltip="link.description"
          >
            <span class="nav-icon">{{ link.icon }}</span>
            <span>{{ link.text }}</span>
          </RouterLink>
        </div>
      </nav>
    </div>

    <!-- Logout Button -->
    <div class="mt-6 p-4 border-t border-primary/20">
      <RouterLink to="/logout" class="nav-link text-red-400 hover:text-red-300">
        <span class="nav-icon">🚪</span>
        <span>Log Out</span>
      </RouterLink>
    </div>
  </aside>
</template>

<style scoped>
/* not needed */
</style>
