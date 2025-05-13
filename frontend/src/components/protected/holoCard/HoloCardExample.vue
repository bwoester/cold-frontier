<script setup lang="ts">
import HoloCard from './HoloCard.vue';
</script>

<template>
  <div class="examples-container">
    <h1 class="examples-title">HoloCard Examples</h1>

    <!-- Basic Example -->
    <div class="example-wrapper">
      <h2 class="example-title">Basic Card</h2>
      <HoloCard title="BASIC CARD">
        <div class="example-content">
          <p>This is a basic card with default styling.</p>
        </div>
      </HoloCard>
    </div>

    <!-- Intense Glow Example -->
    <div class="example-wrapper">
      <h2 class="example-title">Intense Glow</h2>
      <HoloCard
        title="INTENSE GLOW"
        glowIntensity="intense"
      >
        <div class="example-content">
          <p>This card has an intense glow effect.</p>
        </div>
      </HoloCard>
    </div>

    <!-- Different Border Style Example -->
    <div class="example-wrapper">
      <h2 class="example-title">Left Border Accent</h2>
      <HoloCard
        title="LEFT ACCENT"
        borderStyle="left"
      >
        <div class="example-content">
          <p>This card has a left border accent.</p>
        </div>
      </HoloCard>
    </div>

    <!-- Custom Color Example -->
    <div class="example-wrapper">
      <h2 class="example-title">Custom Color</h2>
      <HoloCard
        title="CUSTOM COLOR"
        primaryColor="rgba(255, 100, 100, 0.9)"
      >
        <div class="example-content">
          <p>This card uses a custom reddish color theme.</p>
        </div>
      </HoloCard>
    </div>

    <!-- Pulse Animation Example -->
    <div class="example-wrapper">
      <h2 class="example-title">Pulse Animation</h2>
      <HoloCard
        title="PULSE EFFECT"
        :pulse="true"
      >
        <div class="example-content">
          <p>This card has a subtle pulse animation effect.</p>
        </div>
      </HoloCard>
    </div>

    <!-- Data Card Example -->
    <div class="example-wrapper">
      <h2 class="example-title">Data Card</h2>
      <HoloCard
        title="RESOURCE STATS"
        glowIntensity="medium"
        borderStyle="left"
      >
        <div class="resource-stats">
          <div class="resource">
            <div class="resource-icon">💎</div>
            <div class="resource-data">
              <div class="resource-name">Crystals</div>
              <div class="resource-value">5,842</div>
            </div>
          </div>
          <div class="resource">
            <div class="resource-icon">⚡</div>
            <div class="resource-data">
              <div class="resource-name">Energy</div>
              <div class="resource-value">3,279</div>
            </div>
          </div>
          <div class="resource">
            <div class="resource-icon">🧪</div>
            <div class="resource-data">
              <div class="resource-name">Isotopes</div>
              <div class="resource-value">1,124</div>
            </div>
          </div>
        </div>
      </HoloCard>
    </div>
  </div>
</template>

<style scoped>
.examples-container {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 20px;
  background-color: #0a1525;
  min-height: 100vh;
}

.examples-title {
  grid-column: 1 / -1;
  color: #4cc2ff;
  font-size: 2rem;
  margin-bottom: 20px;
  letter-spacing: 2px;
  text-align: center;
  text-shadow: 0 0 10px rgba(76, 194, 255, 0.5);
  font-family: 'Courier New', monospace;
}

.example-wrapper {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.example-title {
  color: #4cc2ff;
  font-size: 1rem;
  margin-bottom: 5px;
  font-family: 'Courier New', monospace;
  letter-spacing: 1px;
}

.example-content {
  padding: 10px;
  color: #e6e6e6;
}

.example-content p {
  margin: 0;
  line-height: 1.5;
}

/* Resource stats styling */
.resource-stats {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.resource {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 8px;
  background: rgba(255, 255, 255, 0.05);
  border-radius: 6px;
}

.resource-icon {
  width: 24px;
  height: 24px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.25rem;
}

.resource-data {
  flex: 1;
}

.resource-name {
  font-size: 0.8rem;
  color: rgba(220, 240, 255, 0.7);
}

.resource-value {
  font-size: 1rem;
  font-weight: bold;
  color: rgba(220, 240, 255, 0.9);
}
</style>
