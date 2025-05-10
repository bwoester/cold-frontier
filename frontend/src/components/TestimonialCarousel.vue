<script setup lang="ts">
import {computed, onBeforeUnmount, onMounted, ref} from 'vue';

const props = defineProps<{
  quotes: Array<{
    text: string;
    author?: string;
  }>;
  autoplay?: boolean;
  interval?: number;
}>();

const currentIndex = ref(0);
const timer = ref<number | null>(null);
const isHovering = ref(false);

const totalQuotes = computed(() => props.quotes.length);

function nextQuote() {
  currentIndex.value = (currentIndex.value + 1) % totalQuotes.value;
}

function prevQuote() {
  currentIndex.value = (currentIndex.value - 1 + totalQuotes.value) % totalQuotes.value;
}

function setQuote(index: number) {
  currentIndex.value = index;
}

function startAutoplay() {
  if (props.autoplay && !timer.value && !isHovering.value) {
    timer.value = window.setInterval(() => {
      nextQuote();
    }, props.interval || 5000);
  }
}

function stopAutoplay() {
  if (timer.value) {
    clearInterval(timer.value);
    timer.value = null;
  }
}

function handleMouseEnter() {
  isHovering.value = true;
  stopAutoplay();
}

function handleMouseLeave() {
  isHovering.value = false;
  startAutoplay();
}

onMounted(() => {
  startAutoplay();
});

onBeforeUnmount(() => {
  stopAutoplay();
});
</script>

<template>
  <div
    class="testimonial-carousel relative overflow-hidden rounded-xl border border-primary/30 bg-dark/40 backdrop-blur-sm"
    @mouseenter="handleMouseEnter"
    @mouseleave="handleMouseLeave"
  >
    <div class="quotes-container relative min-h-96 p-8">
      <transition-group name="quote-fade">
        <div
          v-for="(quote, index) in quotes"
          :key="index"
          v-show="index === currentIndex"
          class="quote-item absolute inset-0 flex flex-col justify-center p-8"
        >
          <div class="text-primary text-5xl opacity-30 mb-2">"</div>
          <p class="text-blue-100 text-lg md:text-l italic leading-relaxed mb-4">{{
              quote.text
            }}</p>
          <div v-if="quote.author" class="text-primary text-right">
            — {{ quote.author }}
          </div>
        </div>
      </transition-group>
    </div>

    <div
      class="controls-container flex justify-between items-center p-4 border-t border-primary/20">
      <button
        @click="prevQuote"
        class="carousel-control flex items-center justify-center w-10 h-10 rounded-full bg-primary/10 hover:bg-primary/20 transition-colors"
        aria-label="Previous quote"
      >
        <span class="sr-only">Previous</span>
        <span class="text-xl">←</span>
      </button>

      <div class="indicators flex space-x-2">
        <button
          v-for="(_, index) in quotes"
          :key="index"
          @click="setQuote(index)"
          class="w-3 h-3 rounded-full transition-colors"
          :class="index === currentIndex ? 'bg-primary' : 'bg-blue-300/30 hover:bg-blue-300/50'"
          :aria-label="`Go to quote ${index + 1}`"
          :aria-current="index === currentIndex ? 'true' : 'false'"
        ></button>
      </div>

      <button
        @click="nextQuote"
        class="carousel-control flex items-center justify-center w-10 h-10 rounded-full bg-primary/10 hover:bg-primary/20 transition-colors"
        aria-label="Next quote"
      >
        <span class="sr-only">Next</span>
        <span class="text-xl">→</span>
      </button>
    </div>
  </div>
</template>

<style scoped>
.quote-fade-enter-active,
.quote-fade-leave-active {
  transition: opacity 0.5s ease, transform 0.5s ease;
}

.quote-fade-enter-from {
  opacity: 0;
  transform: translateY(20px);
}

.quote-fade-leave-to {
  opacity: 0;
  transform: translateY(-20px);
}
</style>
