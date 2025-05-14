<script setup lang="ts">
import {computed, ref} from 'vue';

const props = defineProps({
  accent: {
    type: String,
    default: 'all', // 'all', 'none', 'top', 'bottom', 'left', 'right'
    validator: (value: string) => ['all', 'none', 'top', 'bottom', 'left', 'right'].includes(value)
  },
  // Content options
  title: {
    type: String,
    default: ''
  },
});

// Element references for animation effects
const cardElement = ref<HTMLElement | null>(null);

// Determine which corner accents to show
const showCorners = computed(() => {
  if (props.accent === 'none') return {tl: false, tr: false, bl: false, br: false};
  if (props.accent === 'top') return {tl: true, tr: true, bl: false, br: false};
  if (props.accent === 'bottom') return {tl: false, tr: false, bl: true, br: true};
  if (props.accent === 'left') return {tl: true, tr: false, bl: true, br: false};
  if (props.accent === 'right') return {tl: false, tr: true, bl: false, br: true};
  return {tl: true, tr: true, bl: true, br: true}; // default is 'all'
});

</script>

<template>
  <div class="holo-card-container" ref="cardElement">
    <!-- Holographic overlay effects -->
    <div class="hologram-overlay"></div>
    <div class="floating-glow"></div>

    <!-- Corner accents -->
    <div v-if="showCorners.tl" class="corner-accent top-left"></div>
    <div v-if="showCorners.tr" class="corner-accent top-right"></div>
    <div v-if="showCorners.bl" class="corner-accent bottom-left"></div>
    <div v-if="showCorners.br" class="corner-accent bottom-right"></div>

    <!-- Title section if provided -->
    <div v-if="title" class="card-title">
      <span class="bracket">《</span>
      {{ title }}
      <span class="bracket">》</span>
    </div>

    <!-- Card content -->
    <div class="card-content">
      <slot></slot>
    </div>
  </div>
</template>

<style scoped>
/* Main holographic container */
.holo-card-container {
  background: rgba(8, 20, 40, 0.75);
  backdrop-filter: blur(20px);
  border-radius: 12px;
  border: 1px solid rgba(64, 218, 255, 0.2);
  box-shadow: 0 0 30px rgba(0, 180, 255, 0.15),
  0 10px 40px rgba(0, 0, 0, 0.4);
  position: relative;
  overflow: hidden;
  color: #e6e6e6;

  /* Creates the floating effect */
  transform: translateZ(0);
  transition: all 0.3s cubic-bezier(0.165, 0.84, 0.44, 1);
}

.holo-card-container:hover {
  box-shadow: 0 0 40px rgba(0, 180, 255, 0.25),
  0 12px 50px rgba(0, 0, 0, 0.5);
}

/* Holographic overlay and texture */
.hologram-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: repeating-linear-gradient(
    to bottom,
    rgba(0, 210, 255, 0.03) 0px,
    rgba(0, 210, 255, 0.02) 1px,
    rgba(0, 210, 255, 0.03) 2px),
  linear-gradient(135deg, rgba(10, 30, 60, 0.7), rgba(20, 40, 70, 0.6));
  pointer-events: none;
  z-index: 1;
}

/* Floating glow effect */
.floating-glow {
  position: absolute;
  top: -50%;
  left: -50%;
  width: 200%;
  height: 200%;
  background: radial-gradient(
    ellipse at center,
    rgba(64, 218, 255, 0.15) 0%,
    rgba(64, 218, 255, 0) 70%
  );
  pointer-events: none;
  z-index: 0;
  transform-origin: center;
  animation: floatGlow 12s infinite ease-in-out;
}

.holo-card-container::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: linear-gradient(
    135deg,
    rgba(64, 218, 255, 0.08) 0%,
    rgba(64, 218, 255, 0) 50%,
    rgba(64, 218, 255, 0.08) 100%
  );
  z-index: 0;
  animation: shimmerEffect 8s infinite ease-in-out;
}

.holo-card-container::after {
  content: '';
  position: absolute;
  top: -10%;
  left: -10%;
  width: 120%;
  height: 120%;
  background: radial-gradient(
    circle at 30% 70%,
    rgba(64, 218, 255, 0.12) 0%,
    rgba(64, 218, 255, 0) 60%
  );
  pointer-events: none;
  z-index: 0;
}

@keyframes floatGlow {
  0% {
    transform: rotate(0deg) scale(1);
    opacity: 0.15;
  }
  25% {
    transform: rotate(90deg) scale(1.05);
    opacity: 0.2;
  }
  50% {
    transform: rotate(180deg) scale(1.1);
    opacity: 0.25;
  }
  75% {
    transform: rotate(270deg) scale(1.05);
    opacity: 0.2;
  }
  100% {
    transform: rotate(360deg) scale(1);
    opacity: 0.15;
  }
}

@keyframes shimmerEffect {
  0%, 100% {
    opacity: 0.3;
    transform: translateX(-5%) translateY(-5%);
  }
  50% {
    opacity: 0.7;
    transform: translateX(5%) translateY(5%);
  }
}

/* Corner accents */
.corner-accent {
  position: absolute;
  width: 30px;
  height: 30px;
  border: 2px solid rgba(64, 218, 255, 0.4);
  z-index: 2;
  pointer-events: none;
}

.top-left {
  top: 0;
  left: 0;
  border-right: none;
  border-bottom: none;
  border-top-left-radius: 10px;
}

.top-right {
  top: 0;
  right: 0;
  border-left: none;
  border-bottom: none;
  border-top-right-radius: 10px;
}

.bottom-left {
  bottom: 0;
  left: 0;
  border-right: none;
  border-top: none;
  border-bottom-left-radius: 10px;
}

.bottom-right {
  bottom: 0;
  right: 0;
  border-left: none;
  border-top: none;
  border-bottom-right-radius: 10px;
}

/* Card title */
.card-title {
  /* those two make sure it's not behind the hologram-overlay */
  position: relative;
  z-index: 10;

  font-size: 0.75rem;
  text-transform: uppercase;
  letter-spacing: 3px;
  color: rgba(64, 218, 255, 0.9);
  font-weight: 500;
  padding: 8px 10px 0;
  display: flex;
  align-items: center;
  justify-content: center;
  text-shadow: 0 0 10px rgba(64, 218, 255, 0.5);
}

.bracket {
  padding: 0 4px;
  font-weight: bold;
  text-shadow: 0 0 8px rgba(64, 218, 255, 0.5);
}

/* Content area */
.card-content {
  padding: 12px;

  /* those two make sure it's not behind the hologram-overlay */
  position: relative;
  z-index: 10;
}

</style>
