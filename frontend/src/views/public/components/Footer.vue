<script setup lang="ts">
defineProps({
  showContainer: {
    type: Boolean,
    default: true
  }
});
</script>

<template>
  <footer class="mt-auto">
    <div v-if="showContainer" class="footer-container">
      <div class="footer-info">
        <a href="#" class="footer-logo underline">COLD<span>FRONTIER</span></a>
        <p class="footer-text">An immersive space strategy game where you build planets and command
          fleets in an ever-expanding universe.</p>
      </div>
      <div class="footer-links">
        <h4>Game</h4>
        <ul>
          <li><a href="#features">Features</a></li>
          <li><a href="#gameplay">Gameplay</a></li>
          <li><a href="/updates">Updates</a></li>
          <li><a href="/roadmap">Roadmap</a></li>
        </ul>
      </div>
      <div class="footer-links">
        <h4>Community</h4>
        <ul>
          <li><a href="/forums">Forums</a></li>
          <li><a href="/discord">Discord</a></li>
          <li><a href="/wiki">Wiki</a></li>
          <li><a href="/support">Support</a></li>
        </ul>
      </div>
      <div class="footer-links">
        <h4>Legal</h4>
        <ul>
          <li><a href="/terms">Terms of Service</a></li>
          <li><a href="/privacy">Privacy Policy</a></li>
          <li><a href="/cookies">Cookie Policy</a></li>
        </ul>
      </div>
    </div>
    <div class="copyright">
      <p>&copy; 2023 Cold Frontier. All rights reserved.</p>
    </div>
  </footer>
</template>

<style scoped>
/* No custom CSS needed as we're using Tailwind classes */
</style>
