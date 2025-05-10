<script setup lang="ts">
// Define props for footer links
defineProps({
  footerLinks: {
    type: Array,
    default: () => []
  }
});
</script>

<template>
  <footer class="relative z-10 bg-darker px-6 py-12 border-t-1 border-primary/20">
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-12 max-w-7xl mx-auto">
      <div>
        <a href="#" class="text-2xl font-bold text-light inline-block mb-4">COLD<span
          class="text-primary">FRONTIER</span></a>
        <p class="text-blue-200 leading-relaxed mb-6">
          An immersive space strategy game where you build planets and command fleets in an
          ever-expanding universe.
        </p>
      </div>
      <div v-for="(linkGroup, groupIndex) in footerLinks" :key="groupIndex">
        <h4 class="text-light mb-6 text-lg">{{ linkGroup.title }}</h4>
        <ul class="list-none">
          <li v-for="(link, linkIndex) in linkGroup.links" :key="linkIndex" class="mb-3">
            <!--
            TODO  Those trigger re-loads.
                  Use RouterLinks for internal links, <a href="..."> for external links
            -->
            <a
              :href="link.href"
              class="text-blue-200 no-underline transition-colors duration-300 hover:text-primary"
            >
              {{ link.text }}
            </a>
          </li>
        </ul>
      </div>
    </div>
    <div class="text-center pt-12 text-blue-200 text-sm">
      <p>&copy; 2023 Cold Frontier. All rights reserved.</p>
    </div>
  </footer>
</template>

<style scoped>
</style>
