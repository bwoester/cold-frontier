<script setup lang="ts">
import "@/assets/protected.css"
import StarsBackground from '@/components/StarsBackground.vue';
import GameNavigationV5 from "@/components/protected/GameNavigationV5.vue";
</script>

<template>
  <div class="relative min-h-screen">

    <!-- Stars background -->
    <StarsBackground />

    <div class="flex z-10 flex-col md:flex-row max-w-7xl mx-auto">

      <!-- Left Navigation Panel -->
      <GameNavigationV5/>

      <!-- Page content -->
      <slot></slot>

    </div>
  </div>
</template>

<style scoped>
#game-bg {
  background-image:
    radial-gradient(circle at 20% 35%, rgba(74, 107, 255, 0.15) 0%, transparent 50%),
    radial-gradient(circle at 75% 65%, rgba(255, 91, 122, 0.1) 0%, transparent 50%);
  background-attachment: fixed;
}
</style>
