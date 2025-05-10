<script setup lang="ts">
import { defineProps } from 'vue';

const props = defineProps({
  resources: {
    type: Object,
    required: true
  }
});
</script>

<template>
  <div class="resource-panel">
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
      <div v-for="(resource, key) in resources" :key="key" class="resource-card">
        <div class="resource-icon">{{ resource.icon }}</div>
        <div class="resource-info">
          <h3>{{ resource.name }}</h3>
          <div class="resource-stats">
            <div class="text-lg font-mono">{{ resource.stock.toLocaleString() }}</div>
            <div class="text-xs mt-1 flex justify-between">
              <span class="text-green-400">{{ resource.production }}</span>
              <span>/ {{ resource.capacity.toLocaleString() }}</span>
            </div>
          </div>
          <div class="resource-bar">
            <div
              class="resource-progress"
              :style="{width: `${(resource.stock / resource.capacity) * 100}%`}"
            ></div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.resource-card {
  @apply bg-darker/50 rounded-lg p-3 flex items-center border border-primary/10;
  backdrop-filter: blur(2px);
  transition: all 0.3s ease;
}

.resource-card:hover {
  @apply border-primary/30 shadow-md;
  box-shadow: 0 0 15px rgba(59, 130, 246, 0.15);
}

.resource-icon {
  @apply text-2xl mr-3 bg-primary/10 h-10 w-10 flex items-center justify-center rounded-md;
}

.resource-info {
  @apply flex-1;
}

.resource-info h3 {
  @apply text-xs uppercase tracking-wider text-blue-300 mb-1;
}

.resource-bar {
  @apply h-1.5 bg-darker rounded-full mt-1 overflow-hidden;
}

.resource-progress {
  @apply h-full rounded-full;
  background: linear-gradient(to right, #3b82f6, #10b981);
}
</style>
