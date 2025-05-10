<script setup lang="ts">
// Main layout component that wraps page content with common elements
import StarsBackground from './StarsBackground.vue';
</script>

<template>
  <div class="relative min-h-screen bg-darker">
    <!-- Background gradients applied via CSS -->

    <!-- Stars background -->
    <StarsBackground />

    <!-- Page content -->
    <slot></slot>

  </div>
</template>

<style scoped>
#app > div:first-child {
  background-image:
    radial-gradient(circle at 20% 35%, rgba(74, 107, 255, 0.15) 0%, transparent 50%),
    radial-gradient(circle at 75% 65%, rgba(255, 91, 122, 0.1) 0%, transparent 50%);
  background-attachment: fixed;
}
</style>
