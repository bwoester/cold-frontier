<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue';

const currentTime = ref('00:00:00');
let timerInterval: number | null = null;

const updateTime = () => {
  const now = new Date();
  currentTime.value = now.toLocaleTimeString([], {
    hour: '2-digit',
    minute: '2-digit',
    second: '2-digit'
  });
};

onMounted(() => {
  updateTime();
  timerInterval = window.setInterval(updateTime, 1000);
});

onUnmounted(() => {
  if (timerInterval) {
    clearInterval(timerInterval);
  }
});
</script>

<template>
  <div class="game-timer">
    <div class="time-display">{{ currentTime }}</div>
    <div class="time-label">Galactic Standard Time</div>
  </div>
</template>

<style scoped>
</style>
