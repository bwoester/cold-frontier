<script setup lang="ts">
interface SvgPath {
  d: string;
  fill?: string;
}

interface AuthProvider {
  name: string;
  url: string;
  text: string;
  uniqueStyle: string;
  svgFill: string;
  svgPaths: SvgPath[];
}

defineProps<{
  provider: AuthProvider;
}>();
</script>

<template>
  <a
    :href="provider.url"
    :class="[
      'auth-btn',
      provider.name,
      'flex items-center justify-center gap-3 p-4 rounded-lg',
      provider.uniqueStyle,
      'font-semibold cursor-pointer transition-all duration-300',
      'border-1 border-primary/20',
      'hover:transform hover:-translate-y-1 hover:shadow-lg hover:border-primary/40'
    ]"
  >
    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"
         :fill="provider.svgFill">
      <path
        v-for="(path, index) in provider.svgPaths"
        :key="index"
        :d="path.d"
        :fill="path.fill || provider.svgFill"
      />
    </svg>
    {{ provider.text }}
  </a>
</template>
