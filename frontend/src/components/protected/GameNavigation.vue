<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue';

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

// Holographic scan effect
const scanPosition = ref(0);
const scanInterval = ref<number | null>(null);

onMounted(() => {
  scanInterval.value = window.setInterval(() => {
    scanPosition.value = (scanPosition.value + 1) % 100;
  }, 50);
});

onUnmounted(() => {
  if (scanInterval.value !== null) {
    clearInterval(scanInterval.value);
  }
});
</script>

<template>
  <aside class="holo-panel w-64 h-full overflow-y-auto flex-shrink-0 relative">
    <!-- Holographic scan line -->
    <div class="scan-line" :style="{ top: `${scanPosition}%` }"></div>

    <!-- Edge glow effects -->
    <div class="edge-glow-top"></div>
    <div class="edge-glow-right"></div>
    <div class="edge-glow-bottom"></div>
    <div class="edge-glow-left"></div>

    <div class="p-4 z-10 relative">
      <!-- Game Logo/Title -->
      <div class="mb-6 text-center">
        <div class="holo-interface-element">
          <h1 class="text-2xl font-bold text-cyan-300 glowing-text">
            Cold Frontier
          </h1>
          <div class="system-info">
            <div class="text-xs text-cyan-300 mt-1 tracking-widest">NEXUS INTERFACE v7.24</div>
            <div class="text-2xs mt-1 text-cyan-400/70 blink-slow">SECURE CONNECTION</div>
          </div>
        </div>
      </div>

      <!-- Planet Selector -->
      <div class="mb-6">
        <h2 class="section-header">
          <span class="bracket">[</span>
          PLANETARY CONTROL
          <span class="bracket">]</span>
        </h2>
        <div class="space-y-2 mt-3">
          <button
            v-for="planet in planets"
            :key="planet.id"
            @click="selectPlanet(planet.id)"
            class="holo-planet-button"
            :class="{ 'selected': planet.selected }"
          >
            <div class="status-indicator">
              <div class="status-dot" :class="{ 'active': planet.selected }"></div>
              <div class="status-ring"></div>
            </div>
            <div class="planet-info">
              <div class="planet-name">{{ planet.name }}</div>
              <div class="planet-type">{{ planet.type }}</div>
            </div>
          </button>
        </div>
      </div>

      <!-- Navigation Links -->
      <nav>
        <h2 class="section-header">
          <span class="bracket">[</span>
          COMMAND PROTOCOLS
          <span class="bracket">]</span>
        </h2>
        <div class="holo-nav-links">
          <RouterLink
            v-for="(link, index) in navigationLinks"
            :key="index"
            :to="link.route"
            class="holo-nav-link"
            v-tooltip="link.description"
          >
            <div class="nav-icon">{{ link.icon }}</div>
            <span class="nav-text">{{ link.text }}</span>
          </RouterLink>
        </div>
      </nav>
    </div>

    <!-- Logout Button -->
    <div class="mt-6 p-4 border-t border-cyan-900/30">
      <RouterLink to="/logout" class="holo-logout">
        <div class="nav-icon">🚪</div>
        <span class="nav-text">DISCONNECT</span>
      </RouterLink>
    </div>
  </aside>
</template>

<style scoped>
.holo-panel {
  background: rgba(5, 15, 25, 0.7);
  backdrop-filter: blur(12px);
  border-right: 1px solid rgba(0, 195, 255, 0.2);
  box-shadow: 0 0 25px rgba(0, 180, 255, 0.1);
  position: relative;
  overflow: hidden;
}

.glowing-text {
  text-shadow: 0 0 10px rgba(0, 195, 255, 0.7);
  letter-spacing: 1px;
}

.section-header {
  font-size: 0.7rem;
  text-transform: uppercase;
  letter-spacing: 2px;
  color: rgba(0, 210, 255, 0.8);
  font-weight: 500;
  margin-bottom: 0.75rem;
  padding: 0 0.5rem;
  display: flex;
  align-items: center;
  justify-content: center;
}

.bracket {
  color: rgba(0, 195, 255, 0.5);
  padding: 0 4px;
  font-weight: bold;
}

.scan-line {
  position: absolute;
  left: 0;
  width: 100%;
  height: 2px;
  background: linear-gradient(
    90deg,
    rgba(0, 210, 255, 0),
    rgba(0, 210, 255, 0.5),
    rgba(0, 210, 255, 0)
  );
  opacity: 0.6;
  z-index: 2;
  pointer-events: none;
}

.edge-glow-top, .edge-glow-right, .edge-glow-bottom, .edge-glow-left {
  position: absolute;
  pointer-events: none;
  z-index: 2;
}

.edge-glow-top, .edge-glow-bottom {
  left: 0;
  width: 100%;
  height: 1px;
  background: linear-gradient(
    90deg,
    rgba(0, 210, 255, 0),
    rgba(0, 210, 255, 0.5),
    rgba(0, 210, 255, 0)
  );
}

.edge-glow-left, .edge-glow-right {
  top: 0;
  height: 100%;
  width: 1px;
  background: linear-gradient(
    0deg,
    rgba(0, 210, 255, 0),
    rgba(0, 210, 255, 0.5),
    rgba(0, 210, 255, 0)
  );
}

.edge-glow-top { top: 0; }
.edge-glow-right { right: 0; }
.edge-glow-bottom { bottom: 0; }
.edge-glow-left { left: 0; }

.holo-interface-element {
  background: rgba(0, 50, 100, 0.15);
  border: 1px solid rgba(0, 210, 255, 0.2);
  border-radius: 5px;
  padding: 12px;
  position: relative;
  overflow: hidden;
}

.holo-interface-element::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 1px;
  background: linear-gradient(
    90deg,
    rgba(0, 210, 255, 0),
    rgba(0, 210, 255, 0.7),
    rgba(0, 210, 255, 0)
  );
}

.system-info {
  position: relative;
  margin-top: 6px;
  padding-top: 6px;
  border-top: 1px solid rgba(0, 210, 255, 0.2);
}

.blink-slow {
  animation: blink 5s infinite;
}

@keyframes blink {
  0%, 98%, 100% { opacity: 1; }
  99% { opacity: 0.5; }
}

.text-2xs {
  font-size: 0.65rem;
}

.holo-planet-button {
  width: 100%;
  background: rgba(0, 70, 100, 0.15);
  border: 1px solid rgba(0, 210, 255, 0.2);
  border-radius: 6px;
  padding: 8px 10px;
  display: flex;
  align-items: center;
  transition: all 0.3s ease;
  position: relative;
  overflow: hidden;
}

.holo-planet-button::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(
    135deg,
    rgba(0, 210, 255, 0.02),
    rgba(0, 210, 255, 0.1),
    rgba(0, 210, 255, 0.02)
  );
  opacity: 0;
  transition: opacity 0.3s ease;
}

.holo-planet-button:hover::before,
.holo-planet-button.selected::before {
  opacity: 1;
}

.holo-planet-button:hover {
  border-color: rgba(0, 210, 255, 0.5);
  box-shadow: 0 0 8px rgba(0, 210, 255, 0.5);
}

.holo-planet-button.selected {
  background: rgba(0, 100, 150, 0.25);
  border-color: rgba(0, 210, 255, 0.6);
  box-shadow: 0 0 12px rgba(0, 210, 255, 0.4);
}

.status-indicator {
  position: relative;
  width: 16px;
  height: 16px;
  margin-right: 10px;
}

.status-dot {
  position: absolute;
  width: 8px;
  height: 8px;
  background: rgba(0, 160, 255, 0.5);
  border-radius: 50%;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  transition: all 0.3s ease;
}

.status-dot.active {
  background: rgba(0, 210, 255, 1);
  box-shadow: 0 0 8px rgba(0, 210, 255, 0.7);
}

.status-ring {
  position: absolute;
  width: 16px;
  height: 16px;
  border: 1px solid rgba(0, 210, 255, 0.4);
  border-radius: 50%;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
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

.holo-nav-links {
  display: flex;
  flex-direction: column;
  gap: 4px;
  margin-top: 10px;
}

.holo-nav-link {
  display: flex;
  align-items: center;
  padding: 6px 10px;
  border-radius: 4px;
  color: rgba(160, 210, 255, 0.9);
  transition: all 0.25s ease;
  text-decoration: none;
  position: relative;
  overflow: hidden;
  border-left: 2px solid rgba(0, 210, 255, 0);
}

.holo-nav-link::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 0%;
  height: 100%;
  background: linear-gradient(
    90deg,
    rgba(0, 210, 255, 0.15),
    rgba(0, 210, 255, 0)
  );
  transition: width 0.3s ease;
}

.holo-nav-link:hover {
  color: rgba(220, 240, 255, 1);
  border-left: 2px solid rgba(0, 210, 255, 0.7);
}

.holo-nav-link:hover::before {
  width: 100%;
}

.holo-nav-link.router-link-active {
  background: rgba(0, 80, 120, 0.2);
  border-left: 2px solid rgba(0, 210, 255, 0.7);
  color: rgba(220, 240, 255, 1);
}

.nav-icon {
  width: 24px;
  height: 24px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 10px;
  font-size: 1rem;
}

.nav-text {
  font-size: 0.875rem;
  letter-spacing: 0.5px;
}

.holo-logout {
  display: flex;
  align-items: center;
  padding: 6px 10px;
  border-radius: 4px;
  color: rgba(255, 100, 100, 0.8);
  transition: all 0.25s ease;
  text-decoration: none;
  letter-spacing: 1px;
  font-size: 0.75rem;
  font-weight: 500;
}

.holo-logout:hover {
  background: rgba(255, 50, 50, 0.15);
  color: rgba(255, 160, 160, 1);
}
</style>
