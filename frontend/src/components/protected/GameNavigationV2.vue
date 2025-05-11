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

// Navigation links grouped by category
const navigationGroups = [
  {
    title: "OPERATIONS",
    icon: "🔧",
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

// Holographic pulse effect
const pulseElements = ref<HTMLElement[]>([]);

onMounted(() => {
  const elements = document.querySelectorAll('.hologram-pulse') as NodeListOf<HTMLElement>;
  pulseElements.value = Array.from(elements);

  // Stagger pulse animation start times
  pulseElements.value.forEach((el, index) => {
    el.style.animationDelay = `${index * 0.5}s`;
  });
});
</script>

<template>
  <aside class="nav-hologram w-64 h-full overflow-y-auto flex-shrink-0 relative">
    <!-- Holographic overlay effects -->
    <div class="hologram-overlay"></div>
    <div class="corner-accent top-left"></div>
    <div class="corner-accent top-right"></div>
    <div class="corner-accent bottom-left"></div>
    <div class="corner-accent bottom-right"></div>

    <div class="p-4 z-10 relative">
      <!-- Game Logo/Title -->
      <div class="mb-6 text-center">
        <div class="holo-card glow-intense pulse-subtle">
          <h1 class="text-2xl font-bold text-cyan-100 hologram-text">
            Cold Frontier
          </h1>
          <div class="system-info">
            <div class="text-xs text-cyan-200 mt-1 tracking-widest">NEXUS INTERFACE v8.13</div>
            <div class="flex justify-between items-center mt-2">
              <span class="text-2xs text-cyan-300 pulse-text">SECURE</span>
              <div class="hex-pattern">
                <span v-for="i in 6" :key="i" class="hex-digit">{{ '0123456789ABCDEF'[Math.floor(Math.random() * 16)] }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Planet Selector - Compact Version -->
      <div class="mb-6">
        <h2 class="section-title">
          <span class="bracket">《</span>
          PLANETARY NEXUS
          <span class="bracket">》</span>
        </h2>
        <div class="relative">
          <!-- Selected Planet Display -->
          <button
            @click="showPlanetDropdown = !showPlanetDropdown"
            class="holo-card planet-card selected-planet mt-3 w-full"
          >
            <div class="planet-hexagon">
              <div class="planet-core"></div>
              <div class="planet-ring"></div>
            </div>
            <div class="planet-info">
              <div class="planet-name">{{ selectedPlanet?.name }}</div>
              <div class="planet-type">{{ selectedPlanet?.type }}</div>
            </div>
            <div class="dropdown-indicator">
              <div :class="['arrow', showPlanetDropdown ? 'up' : 'down']"></div>
            </div>
          </button>

          <!-- Planet Dropdown -->
          <div v-if="showPlanetDropdown" class="planet-dropdown">
            <button
              v-for="planet in planets.filter(p => !p.selected)"
              :key="planet.id"
              @click="selectPlanet(planet.id)"
              class="holo-card planet-card w-full"
            >
              <div class="planet-hexagon small">
                <div class="planet-core"></div>
              </div>
              <div class="planet-info">
                <div class="planet-name">{{ planet.name }}</div>
                <div class="planet-type">{{ planet.type }}</div>
              </div>
            </button>
          </div>
        </div>
      </div>

      <!-- Navigation Links - Grouped -->
      <nav class="space-y-4">
        <div v-for="(group, groupIndex) in navigationGroups" :key="groupIndex" class="nav-group">
          <h2 class="section-title">
            <span class="bracket">《</span>
            {{ group.title }}
            <span class="bracket">》</span>
          </h2>

          <div class="holo-card nav-group-container mt-2 hologram-pulse">
            <div class="group-icon">{{ group.icon }}</div>

            <div class="nav-links">
              <RouterLink
                v-for="(link, linkIndex) in group.links"
                :key="linkIndex"
                :to="link.route"
                class="nav-link"
                v-tooltip="link.description"
              >
                <div class="link-icon">{{ link.icon }}</div>
                <span class="link-text">{{ link.text }}</span>
              </RouterLink>
            </div>
          </div>
        </div>
      </nav>
    </div>

    <!-- Logout Button -->
    <div class="mt-6 p-4 border-t border-cyan-800/30">
      <RouterLink to="/logout" class="logout-button">
        <div class="link-icon pulse-danger">🚪</div>
        <span class="link-text">DISCONNECT</span>
        <div class="hex-pattern small">
          <span v-for="i in 3" :key="i" class="hex-digit danger">{{ 'AFC'[Math.floor(Math.random() * 3)] }}</span>
        </div>
      </RouterLink>
    </div>
  </aside>
</template>

<style scoped>
/* Main holographic panel */
.nav-hologram {
  background: rgba(8, 20, 40, 0.85);
  backdrop-filter: blur(15px);
  border-right: 1px solid rgba(64, 218, 255, 0.15);
  box-shadow: 0 0 30px rgba(0, 180, 255, 0.07);
  position: relative;
  overflow: hidden;
}

/* Holographic overlay and texture */
.hologram-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background:
    repeating-linear-gradient(
      to bottom,
      rgba(0, 210, 255, 0.03) 0px,
      rgba(0, 210, 255, 0.02) 1px,
      rgba(0, 210, 255, 0.03) 2px
    ),
    linear-gradient(
      135deg,
      rgba(10, 30, 60, 0.9),
      rgba(20, 40, 70, 0.8)
    );
  pointer-events: none;
  z-index: 1;
}

/* Corner accents */
.corner-accent {
  position: absolute;
  width: 30px;
  height: 30px;
  border: 2px solid rgba(64, 218, 255, 0.3);
  z-index: 2;
  pointer-events: none;
}

.top-left {
  top: 0;
  left: 0;
  border-right: none;
  border-bottom: none;
  border-top-left-radius: 3px;
}

.top-right {
  top: 0;
  right: 0;
  border-left: none;
  border-bottom: none;
  border-top-right-radius: 3px;
}

.bottom-left {
  bottom: 0;
  left: 0;
  border-right: none;
  border-top: none;
  border-bottom-left-radius: 3px;
}

.bottom-right {
  bottom: 0;
  right: 0;
  border-left: none;
  border-top: none;
  border-bottom-right-radius: 3px;
}

/* Holographic text with glow effect */
.hologram-text {
  text-shadow:
    0 0 10px rgba(64, 218, 255, 0.7),
    0 0 20px rgba(64, 218, 255, 0.5);
  letter-spacing: 2px;
  font-family: 'Arial', sans-serif;
}

/* Glowing card effect for sections */
.holo-card {
  background: rgba(20, 50, 80, 0.4);
  border: 1px solid rgba(64, 218, 255, 0.25);
  border-radius: 8px;
  padding: 12px;
  position: relative;
  overflow: hidden;
  box-shadow:
    inset 0 0 20px rgba(64, 218, 255, 0.05),
    0 0 10px rgba(64, 218, 255, 0.1);
  transition: all 0.3s ease;
}

.holo-card:hover {
  border-color: rgba(64, 218, 255, 0.4);
  box-shadow:
    inset 0 0 30px rgba(64, 218, 255, 0.1),
    0 0 15px rgba(64, 218, 255, 0.2);
}

.holo-card::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 1px;
  background: linear-gradient(
    90deg,
    rgba(64, 218, 255, 0),
    rgba(64, 218, 255, 0.5),
    rgba(64, 218, 255, 0)
  );
}

.holo-card::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  height: 1px;
  background: linear-gradient(
    90deg,
    rgba(64, 218, 255, 0),
    rgba(64, 218, 255, 0.2),
    rgba(64, 218, 255, 0)
  );
}

/* Section titles */
.section-title {
  font-size: 0.75rem;
  text-transform: uppercase;
  letter-spacing: 3px;
  color: rgba(64, 218, 255, 0.9);
  font-weight: 500;
  margin-bottom: 0.5rem;
  padding: 0 0.5rem;
  display: flex;
  align-items: center;
  justify-content: center;
  text-shadow: 0 0 10px rgba(64, 218, 255, 0.5);
}

.bracket {
  color: rgba(64, 218, 255, 0.7);
  padding: 0 4px;
  font-weight: bold;
}

/* System info styling */
.system-info {
  position: relative;
  margin-top: 8px;
  padding-top: 8px;
  border-top: 1px solid rgba(64, 218, 255, 0.2);
}

.pulse-text {
  animation: pulse 2s infinite ease-in-out;
}

@keyframes pulse {
  0%, 100% { opacity: 0.7; }
  50% { opacity: 1; }
}

.pulse-subtle {
  animation: pulseShadow 3s infinite ease-in-out;
}

@keyframes pulseShadow {
  0%, 100% { box-shadow: 0 0 15px rgba(64, 218, 255, 0.15); }
  50% { box-shadow: 0 0 25px rgba(64, 218, 255, 0.3); }
}

.glow-intense {
  box-shadow:
    0 0 15px rgba(64, 218, 255, 0.2),
    0 0 30px rgba(64, 218, 255, 0.1);
}

.text-2xs {
  font-size: 0.65rem;
}

.hex-pattern {
  display: flex;
  gap: 1px;
}

.hex-digit {
  font-family: monospace;
  font-size: 0.7rem;
  color: rgba(64, 218, 255, 0.8);
  letter-spacing: 0px;
}

/* Planet selector styling */
.planet-card {
  display: flex;
  align-items: center;
  gap: 12px;
  transition: all 0.3s ease;
  cursor: pointer;
}

.selected-planet {
  background: rgba(30, 70, 110, 0.5);
  border-color: rgba(64, 218, 255, 0.4);
}

.planet-hexagon {
  position: relative;
  width: 32px;
  height: 32px;
}

.planet-hexagon.small {
  width: 24px;
  height: 24px;
}

.planet-core {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 16px;
  height: 16px;
  background: radial-gradient(
    circle,
    rgba(100, 200, 255, 0.8),
    rgba(30, 100, 180, 0.5)
  );
  border-radius: 50%;
  box-shadow: 0 0 10px rgba(64, 218, 255, 0.4);
}

.planet-hexagon.small .planet-core {
  width: 12px;
  height: 12px;
}

.planet-ring {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 30px;
  height: 30px;
  border: 1px solid rgba(64, 218, 255, 0.6);
  border-radius: 50%;
  clip-path: polygon(
    50% 0%,
    100% 25%,
    100% 75%,
    50% 100%,
    0% 75%,
    0% 25%
  );
  background: radial-gradient(
    circle,
    rgba(64, 218, 255, 0.1),
    rgba(30, 100, 180, 0.05)
  );
}

.planet-info {
  flex: 1;
}

.planet-name {
  color: rgba(220, 240, 255, 0.9);
  font-size: 0.875rem;
  font-weight: 500;
  letter-spacing: 0.5px;
}

.planet-type {
  font-size: 0.7rem;
  color: rgba(160, 210, 255, 0.7);
  margin-top: 2px;
  letter-spacing: 0.3px;
}

.dropdown-indicator {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 24px;
  height: 24px;
}

.arrow {
  width: 0;
  height: 0;
  border-left: 5px solid transparent;
  border-right: 5px solid transparent;
}

.arrow.down {
  border-top: 5px solid rgba(64, 218, 255, 0.8);
}

.arrow.up {
  border-bottom: 5px solid rgba(64, 218, 255, 0.8);
}

.planet-dropdown {
  position: absolute;
  width: 100%;
  margin-top: 4px;
  background: rgba(15, 30, 50, 0.95);
  border: 1px solid rgba(64, 218, 255, 0.25);
  border-radius: 8px;
  overflow: hidden;
  z-index: 20;
  backdrop-filter: blur(10px);
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
}

.planet-dropdown .planet-card {
  border: none;
  border-radius: 0;
  border-bottom: 1px solid rgba(64, 218, 255, 0.1);
  margin: 0;
}

.planet-dropdown .planet-card:last-child {
  border-bottom: none;
}

/* Navigation group styling */
.nav-group {
  margin-bottom: 16px;
}

.nav-group-container {
  position: relative;
  padding: 12px 8px;
}

.hologram-pulse {
  position: relative;
  overflow: hidden;
}

.hologram-pulse::after {
  content: '';
  position: absolute;
  top: -100%;
  left: -100%;
  height: 300%;
  width: 300%;
  background: radial-gradient(
    circle,
    rgba(64, 218, 255, 0.2) 0%,
    rgba(64, 218, 255, 0) 70%
  );
  animation: pulseMove 8s infinite linear;
  pointer-events: none;
  opacity: 0;
}

@keyframes pulseMove {
  0% {
    opacity: 0;
    transform: translate(0, 0);
  }
  25% {
    opacity: 0.05;
  }
  50% {
    opacity: 0;
  }
  75% {
    opacity: 0.05;
  }
  100% {
    opacity: 0;
    transform: translate(33%, 33%);
  }
}

.group-icon {
  position: absolute;
  top: -10px;
  left: 10px;
  width: 24px;
  height: 24px;
  background: rgba(20, 50, 80, 0.9);
  border: 1px solid rgba(64, 218, 255, 0.3);
  border-radius: 4px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 0.9rem;
  z-index: 5;
}

.nav-links {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 6px;
}

.nav-link {
  display: flex;
  align-items: center;
  padding: 6px 8px;
  border-radius: 4px;
  color: rgba(180, 220, 255, 0.8);
  transition: all 0.25s ease;
  text-decoration: none;
  font-size: 0.8rem;
  background: rgba(30, 60, 100, 0.2);
  border: 1px solid rgba(64, 218, 255, 0.1);
}

.nav-link:hover {
  background: rgba(40, 80, 120, 0.3);
  color: rgba(220, 240, 255, 1);
  border-color: rgba(64, 218, 255, 0.4);
  box-shadow: 0 0 10px rgba(64, 218, 255, 0.2);
}

.nav-link.router-link-active {
  background: rgba(40, 80, 120, 0.4);
  border-color: rgba(64, 218, 255, 0.5);
  color: rgba(220, 240, 255, 1);
  box-shadow: 0 0 15px rgba(64, 218, 255, 0.3);
}

.link-icon {
  width: 20px;
  height: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 8px;
  font-size: 0.9rem;
}

.link-text {
  font-size: 0.8rem;
  letter-spacing: 0.5px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

/* Logout button styling */
.logout-button {
  display: flex;
  align-items: center;
  padding: 8px 12px;
  border-radius: 6px;
  color: rgba(255, 130, 130, 0.9);
  transition: all 0.25s ease;
  text-decoration: none;
  letter-spacing: 1px;
  font-size: 0.8rem;
  font-weight: 500;
  justify-content: space-between;
  background: rgba(80, 20, 20, 0.15);
  border: 1px solid rgba(255, 100, 100, 0.2);
}

.logout-button:hover {
  background: rgba(100, 20, 20, 0.25);
  color: rgba(255, 180, 180, 1);
  border-color: rgba(255, 100, 100, 0.4);
  box-shadow: 0 0 15px rgba(255, 100, 100, 0.2);
}

.pulse-danger {
  animation: pulseDanger 2s infinite ease-in-out;
}

@keyframes pulseDanger {
  0%, 100% { opacity: 0.7; }
  50% { opacity: 1; color: rgba(255, 150, 150, 1); }
}

.hex-digit.danger {
  color: rgba(255, 130, 130, 0.8);
}

.hex-pattern.small {
  gap: 3px;
}
</style>
