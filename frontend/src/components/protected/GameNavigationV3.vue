<script setup lang="ts">
import { ref, onMounted, computed } from 'vue';

// Mock data for planets
const planets = ref([
  { id: 1, name: 'Borealis VI', type: 'Ice Planet', selected: true },
  { id: 2, name: 'Ignis Prime', type: 'Volcanic Moon', selected: false },
  { id: 3, name: 'Verdant Echo', type: 'Forest World', selected: false }
]);

// Current selected planet
const selectedPlanet = computed(() => planets.value.find(planet => planet.selected));

// Toggle planet dropdown visibility
const showPlanetDropdown = ref(false);

// Navigation links grouped by category with alternating alignment
const navigationGroups = [
  {
    title: "OPERATIONS",
    icon: "🔧",
    alignment: "left",
    links: [
      { icon: '🪐', text: 'Overview', route: '/game', description: 'Planet status dashboard' },
      { icon: '🏗️', text: 'Buildings', route: '/game/buildings', description: 'Construct and upgrade planetary facilities' },
      { icon: '🔬', text: 'Research', route: '/game/research', description: 'Develop new technologies' },
      { icon: '💱', text: 'Trade', route: '/game/trade', description: 'Trade resources with other players' },
      { icon: '📊', text: 'Economy', route: '/game/economy', description: 'Economic overview and forecasts' }
    ]
  },
  {
    title: "MILITARY",
    icon: "⚔️",
    alignment: "right",
    links: [
      { icon: '🚀', text: 'Shipyard', route: '/game/shipyard', description: 'Build and manage your fleet' },
      { icon: '🛡️', text: 'Defense', route: '/game/defense', description: 'Construct planetary defenses' },
      { icon: '⚔️', text: 'Fleet', route: '/game/fleet', description: 'Fleet management and deployment' },
      { icon: '🎲', text: 'Simulator', route: '/game/simulator', description: 'Battle simulator' }
    ]
  },
  {
    title: "INTELLIGENCE",
    icon: "🌌",
    alignment: "left",
    links: [
      { icon: '🌌', text: 'Universe', route: '/game/universe', description: 'Explore the galactic map' },
      { icon: '✉️', text: 'Messages', route: '/game/messages', description: 'Communication center' },
      { icon: '👤', text: 'Profile', route: '/game/profile', description: 'Commander profile and settings' },
      { icon: '🤝', text: 'Alliance', route: '/game/alliance', description: 'Alliance management' }
    ]
  }
];

// Toggle planet selection
const selectPlanet = (planetId: number) => {
  planets.value = planets.value.map(planet =>
    ({...planet, selected: planet.id === planetId})
  );
  showPlanetDropdown.value = false;
};

// Simulate blinking light effect for status indicators
const isBlinking = ref(false);

onMounted(() => {
  // Set random blink intervals for status indicators
  setInterval(() => {
    isBlinking.value = !isBlinking.value;
  }, 3000);
});
</script>

<template>
  <aside class="mission-control w-64 h-full overflow-y-auto flex-shrink-0 relative">
    <div class="p-4 relative">
      <!-- Game Logo/Title -->
      <div class="mb-5 text-center">
        <div class="console-panel">
          <div class="panel-label">MISSION COMMAND SYSTEM</div>
          <h1 class="text-xl font-bold text-blue-100 console-text">
            COLD FRONTIER
          </h1>
          <div class="system-info">
            <div class="text-xs text-cyan-300 mt-1 tracking-widest">COMMAND INTERFACE v1.72</div>
            <div class="flex justify-between items-center mt-2">
              <div class="indicator-light" :class="{ 'active': isBlinking }"></div>
              <div class="status-code">SYS NOMINAL</div>
            </div>
          </div>
        </div>
      </div>

      <!-- Planet Selector -->
      <div class="mb-5">
        <div class="section-header">
          <div class="header-line"></div>
          <div class="header-title">ACTIVE COLONY</div>
          <div class="header-line"></div>
        </div>

        <div class="relative">
          <!-- Selected Planet Display -->
          <button
            @click="showPlanetDropdown = !showPlanetDropdown"
            class="console-panel planet-status mt-2 w-full"
          >
            <div class="status-indicator">
              <div class="indicator-light active"></div>
            </div>
            <div class="planet-info">
              <div class="planet-name">{{ selectedPlanet?.name }}</div>
              <div class="planet-type">{{ selectedPlanet?.type }}</div>
            </div>
            <div class="toggle-switch">
              <div class="toggle-button">SELECT</div>
            </div>
          </button>

          <!-- Planet Dropdown -->
          <div v-if="showPlanetDropdown" class="planet-dropdown">
            <button
              v-for="planet in planets.filter(p => !p.selected)"
              :key="planet.id"
              @click="selectPlanet(planet.id)"
              class="console-panel planet-option w-full"
            >
              <div class="status-indicator">
                <div class="indicator-light inactive"></div>
              </div>
              <div class="planet-info">
                <div class="planet-name">{{ planet.name }}</div>
                <div class="planet-type">{{ planet.type }}</div>
              </div>
            </button>
          </div>
        </div>
      </div>

      <!-- Navigation Links - Grouped with alternating alignment -->
      <nav class="space-y-5">
        <div
          v-for="(group, groupIndex) in navigationGroups"
          :key="groupIndex"
          class="nav-group"
          :class="[group.alignment === 'right' ? 'align-right' : 'align-left']"
        >
          <div class="section-header">
            <div class="header-line"></div>
            <div class="header-title">{{ group.title }}</div>
            <div class="header-line"></div>
          </div>

          <div class="console-panel nav-group-container mt-2">
            <div class="nav-links">
              <RouterLink
                v-for="(link, linkIndex) in group.links"
                :key="linkIndex"
                :to="link.route"
                class="nav-link"
                v-tooltip="link.description"
              >
                <div class="link-icon">{{ link.icon }}</div>
                <div class="link-text">{{ link.text }}</div>
              </RouterLink>
            </div>
          </div>
        </div>
      </nav>
    </div>

    <!-- Logout Button -->
    <div class="mt-5 p-4 border-t border-blue-900/30">
      <RouterLink to="/logout" class="logout-button">
        <div class="link-icon warning">🚪</div>
        <span class="link-text">DISCONNECT</span>
      </RouterLink>
    </div>
  </aside>
</template>

<style scoped>
/* Main console panel */
.mission-control {
  background-color: #121820;
  border-right: 5px solid #1e2830;
  position: relative;
  overflow: hidden;
  font-family: 'Courier New', monospace;
  color: #e6e6e6;
}

/* Console panel styling */
.console-panel {
  background-color: #1a2530;
  border: 2px solid #2c3844;
  border-radius: 4px;
  padding: 10px;
  position: relative;
  box-shadow: inset 0 0 10px rgba(0, 0, 0, 0.5);
}

.console-panel::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 3px;
  background: linear-gradient(to right, #2c3844, #3a4c5c, #2c3844);
}

.console-text {
  font-family: 'Courier New', monospace;
  letter-spacing: 1px;
}

.panel-label {
  position: absolute;
  top: -8px;
  left: 10px;
  background-color: #1e2830;
  padding: 0 8px;
  font-size: 0.6rem;
  color: #4cc2ff;
  letter-spacing: 1px;
}

/* System info styling */
.system-info {
  margin-top: 8px;
  padding-top: 8px;
  border-top: 1px solid #2c3844;
}

.indicator-light {
  width: 10px;
  height: 10px;
  border-radius: 50%;
  background-color: #2c3844;
  border: 1px solid #3a4c5c;
  box-shadow: inset 0 0 3px rgba(0, 0, 0, 0.5);
}

.indicator-light.active {
  background-color: #4cc2ff; /* Cyan blue */
  box-shadow: 0 0 5px #4cc2ff, inset 0 0 3px rgba(255, 255, 255, 0.5);
}

.indicator-light.inactive {
  background-color: #385060;
}

.status-code {
  font-family: 'Courier New', monospace;
  font-size: 0.7rem;
  color: #4cc2ff;
  letter-spacing: 1px;
}

/* Section headers with lines */
.section-header {
  display: flex;
  align-items: center;
  margin: 10px 0;
}

.header-line {
  flex: 1;
  height: 1px;
  background-color: #3a4c5c;
}

.header-title {
  padding: 0 10px;
  font-size: 0.7rem;
  letter-spacing: 2px;
  color: #4cc2ff;
  white-space: nowrap;
}

/* Planet status styling */
.planet-status {
  display: flex;
  align-items: center;
  gap: 10px;
  cursor: pointer;
  transition: background-color 0.2s ease;
}

.planet-status:hover {
  background-color: #223040;
}

.status-indicator {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 20px;
}

.planet-info {
  flex: 1;
}

.planet-name {
  color: #e6e6e6;
  font-size: 0.85rem;
  font-weight: bold;
}

.planet-type {
  font-size: 0.7rem;
  color: #a3c5e0;
  margin-top: 2px;
}

.toggle-switch {
  background-color: #1e2830;
  border: 1px solid #3a4c5c;
  border-radius: 2px;
  padding: 3px 6px;
}

.toggle-button {
  font-size: 0.6rem;
  color: #4cc2ff;
  letter-spacing: 1px;
}

/* Planet dropdown */
.planet-dropdown {
  position: absolute;
  width: 100%;
  margin-top: 4px;
  background-color: #1a2530;
  border: 2px solid #2c3844;
  border-radius: 4px;
  overflow: hidden;
  z-index: 20;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
}

.planet-option {
  display: flex;
  align-items: center;
  gap: 10px;
  border-radius: 0;
  border: none;
  border-bottom: 1px solid #1e2830;
  margin: 0;
  cursor: pointer;
  transition: background-color 0.2s ease;
}

.planet-option:hover {
  background-color: #223040;
}

.planet-option:last-child {
  border-bottom: none;
}

/* Navigation group styling */
.nav-group {
  margin-bottom: 15px;
}

.align-left .nav-group-container {
  border-left: 4px solid #4cc2ff;
  border-right: 1px solid #2c3844;
}

.align-right .nav-group-container {
  border-right: 4px solid #4cc2ff;
  border-left: 1px solid #2c3844;
}

.align-right .section-header {
  flex-direction: row-reverse;
}

.nav-group-container {
  padding: 8px;
}

.nav-links {
  display: flex;
  flex-direction: column;
  gap: 3px;
}

.nav-link {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 6px 8px;
  border-radius: 2px;
  text-decoration: none;
  color: #e6e6e6;
  transition: all 0.2s ease;
  background-color: #223040;
  border: 1px solid #2c3844;
}

.align-left .nav-link {
  border-left: 3px solid transparent;
}

.align-right .nav-link {
  border-right: 3px solid transparent;
  flex-direction: row-reverse;
  text-align: right;
}

.nav-link:hover {
  background-color: #2c3844;
}

.nav-link.router-link-active {
  background-color: #2c3844;
}

.align-left .nav-link.router-link-active {
  border-left-color: #4cc2ff;
}

.align-right .nav-link.router-link-active {
  border-right-color: #4cc2ff;
}

.link-icon {
  width: 20px;
  height: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1rem;
}

.link-text {
  font-size: 0.8rem;
  letter-spacing: 0.5px;
  font-weight: 600;
}

/* Logout button styling */
.logout-button {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 8px 12px;
  background-color: #301a1e;
  border: 2px solid #50262e;
  border-radius: 2px;
  color: #ff9999;
  text-decoration: none;
  font-size: 0.8rem;
  font-weight: 600;
  letter-spacing: 1px;
  transition: all 0.2s ease;
}

.logout-button:hover {
  background-color: #401a24;
  border-color: #602a36;
}

.warning {
  color: #ff6666;
}
</style>
