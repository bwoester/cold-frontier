<script setup lang="ts">
// Simple component for stars background effect
</script>

<template>
  <div class="stars absolute inset-0"></div>
</template>

<style scoped>
.stars {
  background-image: radial-gradient(2px 2px at 20px 30px, #eee, rgba(0, 0, 0, 0)),
  radial-gradient(2px 2px at 40px 70px, #fff, rgba(0, 0, 0, 0)),
  radial-gradient(1px 1px at 90px 40px, #ddd, rgba(0, 0, 0, 0));
  background-repeat: repeat;
  background-size: 200px 200px;
  background-attachment: fixed;
  opacity: 0.2;
  animation: twinkle 5s ease-in-out infinite;
}

@keyframes twinkle {
  0% {
    opacity: 0.2;
  }
  50% {
    opacity: 0.4;
  }
  100% {
    opacity: 0.2;
  }
}
</style>
