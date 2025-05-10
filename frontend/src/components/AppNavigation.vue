<script setup lang="ts">
interface NavLink {
  text: string;
  href: string;
}

const props = defineProps<{
  showFullNav?: boolean;
  navLinks?: NavLink[];
}>();

// Default props for the navigation component
const navLinks = props.navLinks || [
  {text: 'Features', href: '#features'},
  {text: 'Gameplay', href: '#gameplay'},
  {text: 'Community', href: '#community'},
  {text: 'FAQ', href: '#faq'},
  {text: 'Quarkus', href: '/q/dev-ui/welcome'}
];
</script>

<template>
  <nav class="sticky top-0 z-100
      flex justify-between items-center p-6 max-w-full mx-auto
      bg-dark/80 backdrop-blur-sm border-b-1 border-primary/20">
    <RouterLink to="/" class="text-3xl font-bold text-light">COLD<span class="text-primary">FRONTIER</span></RouterLink>

    <!-- Navigation links (shown on home page) -->
    <ul v-if="showFullNav" class="hidden md:flex gap-8 list-none">
      <li v-for="(link, index) in navLinks" :key="index">
        <a
          :href="link.href"
          class="text-light no-underline font-medium py-2 px-0 transition-colors duration-300 relative
                hover:text-primary after:content-[''] after:absolute after:w-0 after:h-0.5
                after:bg-primary after:bottom-0 after:left-0 after:transition-width after:duration-300
                hover:after:w-full"
        >
          {{ link.text }}
        </a>
      </li>
    </ul>

    <!-- Action buttons -->
    <div class="flex gap-4">
      <slot name="actions"></slot>
    </div>
  </nav>
</template>

<style scoped>

</style>
