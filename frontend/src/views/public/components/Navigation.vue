<script setup lang="ts">
defineProps({
  showNavLinks: {
    type: Boolean,
    default: true
  },
  showAuthButtons: {
    type: Boolean,
    default: true
  }
});
</script>

<template>
  <nav class="sticky top-0 w-full z-50 flex items-center justify-between px-6 py-4 bg-gray-900/75 backdrop-blur-sm" >
    <RouterLink to="/" class="logo">COLD<span>FRONTIER</span></RouterLink>

    <ul v-if="showNavLinks" class="nav-links">
      <li><a href="#features">Features</a></li>
      <li><a href="#gameplay">Gameplay</a></li>
      <li><a href="#community">Community</a></li>
      <li><a href="#faq">FAQ</a></li>
      <li><a href="/q/dev-ui/welcome">Quarkus</a></li>
    </ul>

    <div v-if="showAuthButtons" class="auth-buttons">
      <RouterLink to="/login" class="btn btn-secondary">Login</RouterLink>
      <RouterLink to="/register" class="btn btn-primary">Register</RouterLink>
    </div>
  </nav>
</template>

<style scoped>
/* No custom CSS needed as we're using Tailwind classes */
</style>
