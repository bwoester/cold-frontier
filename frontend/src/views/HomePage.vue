<script setup lang="ts">
import AppLayout from "@/components/AppLayout.vue";
import AppNavigation from "@/components/AppNavigation.vue";
import TestimonialCarousel from "@/components/TestimonialCarousel.vue";

const navLinks = [
  {text: 'Features', href: '#features'},
  {text: 'Gameplay', href: '#gameplay'},
  {text: 'Story', href: '#story'},
  {text: 'Community', href: '#community'},
  {text: 'FAQ', href: '#faq'},
  {text: 'Quarkus', href: '/q/dev-ui/welcome'}
];

const features = [
  {
    icon: '🪐',
    title: 'Planet Development',
    description: 'Develop and customize your home planet with various buildings, defenses, and resource generators. Create the perfect base of operations for your galactic empire.'
  },
  {
    icon: '🚀',
    title: 'Fleet Management',
    description: 'Design and build powerful fleets with a variety of ships, from nimble scouts to massive battleships. Each vessel has unique abilities and tactical advantages.'
  },
  {
    icon: '⚔️',
    title: 'Strategic Raids',
    description: 'Launch raids against other players\' colonies to steal resources, sabotage their infrastructure, or establish dominance in contested star systems.'
  },
  {
    icon: '🤝',
    title: 'Alliances',
    description: 'Form powerful alliances with other players to protect your territories, coordinate attacks, and control valuable regions of space.'
  },
  {
    icon: '📈',
    title: 'Resource Economy',
    description: 'Manage a complex economy of rare materials, energy, and technology. Trade with other players or raid their stockpiles to fuel your expansion.'
  },
  {
    icon: '🔬',
    title: 'Research & Technology',
    description: 'Unlock powerful technologies through an extensive research tree. Specialize your empire for defense, offense, economy, or a balanced approach.'
  }
];

const gameplayItems = [
  {
    title: 'Build Your Planet',
    description: 'Your home planet is the heart of your empire. Develop it wisely to generate resources, research technologies, and defend against enemy attacks.',
    imagePlaceholder: '[Planet development interface showcase]',
    features: [
      'Construct specialized buildings for resource production',
      'Establish defensive structures to repel raiders',
      'Balance civil and military infrastructure'
    ]
  },
  {
    title: 'Command Your Fleets',
    description: 'Design, build, and command powerful fleets to explore the galaxy, protect your assets, and raid enemy planets.',
    imagePlaceholder: '[Fleet management and combat visualization]',
    features: [
      'Choose from dozens of specialized ship designs',
      'Deploy fleets with tactical precision',
      'Customize your flagship with special abilities'
    ]
  },
  {
    title: 'Forge Alliances',
    description: 'The cold frontier is dangerous to navigate alone. Form alliances with other commanders to protect your interests and dominate rival factions.',
    imagePlaceholder: '[Alliance and diplomacy interface]',
    features: [
      'Coordinate attacks with alliance members',
      'Share resources and technology',
      'Participate in alliance-exclusive events'
    ]
  }
];

const gameStories = [
  {
    text: "The day we established our first off-world agricultural biodome on the frozen plains of Borealis VI was truly heartening. To see those first seedlings sprout in the nutrient-rich soil, a vibrant splash of green against the stark white landscape, filled us all with a profound sense of hope and self-sufficiency. It was a tangible symbol of our commitment to nurturing life, even in the most challenging environments. The subsequent 'First Harvest Festival' was a joyous occasion, a testament to the resilience and ingenuity of our pioneering spirit.",
    author: "Ambassador Elara Vex"
  },
  {
    text: "The integration into the Intergalactic Stock Exchange marked a significant expansion of our economic influence. Access to a broader market allowed for the diversification of our investment portfolio and the potential for exponential growth. While the inherent volatility of intergalactic trade introduced a degree of risk, the projected returns outweighed the potential liabilities by a factor of 2.73. The initial trading algorithms required extensive calibration to account for the unpredictable fluctuations in xeno-currency exchange rates, but the long-term benefits to our resource accumulation are undeniable.",
    author: "Director Algernon Fiduciary"
  },
  {
    text: "The breakthrough in Gravitic Anomaly Containment! For cycles, we toiled, wrestling with the unpredictable fluctuations of localized spacetime distortions. Early attempts to harness their energy resulted in little more than flickering lights and the occasional misplaced teacup. But then! A moment of pure scientific serendipity! By cross-referencing ancient Lemurian star charts with the migratory patterns of the Nebula Nymph, we stumbled upon the key harmonic frequency! The subsequent surge of stable, harnessed energy powered our first interstellar warp drive prototype! A glorious testament to the power of interdisciplinary (and slightly unhinged) research!",
    author: "Professor Quentin Quibble"
  },
  {
    text: "Our first successful planetary siege was… efficient. The target, a heavily fortified pirate stronghold on the volcanic moon of Ignis Prime, offered significant resistance. Their orbital defenses were… adequate. Their ground troops… less so. Our coordinated assault, utilizing synchronized orbital bombardment and rapid deployment of armored divisions, neutralized the threat with minimal losses to our forces. The subsequent acquisition of their illicit resource caches proved… strategically advantageous. A clear demonstration of the superiority of disciplined military action over chaotic lawlessness. And, I might add, not a single tree was harmed in the process.",
    author: "General Arminius \"Ironclad\" Von Bolt"
  },
  {
    text: "The initial foray into Xeno-Linguistics was… humbling. We encountered a species of sentient gas clouds whose primary mode of communication involved complex shifts in atmospheric pressure and the occasional discharge of static electricity. Our universal translator, designed for humanoid vocalizations, proved about as useful as a chocolate teapot in a supernova. It required weeks of painstaking observation, the development of an entirely new sensory input array, and several near-electrocutions for my research assistants, before we could even begin to decipher their basic greetings. (Footnote: Their concept of 'greeting' appears to involve a mild form of atmospheric buffeting, often followed by what we tentatively translated as 'inquire about fungal specimens.') A fascinating, if somewhat breezy, endeavor.",
    author: "Professor Quentin Quibble"
  },
  {
    text: "The formation of the 'Galactic Cooperative of United Explorers' was a landmark moment for interspecies relations. To see representatives from so many diverse civilizations – the silicon-based Kryll, the aquatic Floobian Collective, the arboreal Sylvans – come together with a shared vision of peaceful exploration and mutual support... it was deeply moving. The initial negotiations were, admittedly, a little… animated, particularly concerning the proper etiquette for inter-species tea ceremonies. But ultimately, a spirit of understanding and compromise prevailed. It showed us that even across vast cultural divides, common ground can always be cultivated.",
    author: "Ambassador Elara Vex"
  },
  {
    text: "The early resource shortages on Aethelred IV tested our logistical capabilities. The initial survey data had underestimated the planet's geological instability, leading to significant disruptions in ore extraction. Certain… less disciplined commanders panicked. But through strict rationing, prioritized resource allocation, and the… persuasive reallocation of certain non-essential personnel to mining duties, we weathered the crisis. It was a vital lesson in the importance of meticulous planning and the swift, decisive application of… resource management protocols. And the elimination of any non-productive… foliage around the mining facilities.",
    author: "General Arminius \"Ironclad\" Von Bolt"
  },
  {
    text: "The period following the unexpected depletion of the primary neutronium reserves on Cygnus Minor presented a critical resource management challenge. Neutronium, as you know, is essential for advanced ship construction. The immediate cessation of supply necessitated a rapid reassessment of our production pipelines and the implementation of stringent conservation measures. While the short-term impact on fleet expansion was significant, the crisis spurred innovation in alternative propulsion technologies, ultimately leading to a more diversified and resilient industrial base. A painful, but ultimately instructive, lesson in the perils of over-reliance on a single resource stream.",
    author: "Director Algernon Fiduciary"
  },
];

</script>

<template>
  <AppLayout>
    <div class="relative min-h-screen">

      <!-- Navigation -->
      <AppNavigation :showFullNav="true" :navLinks="navLinks">
        <template #actions>
          <RouterLink to="/login" class="btn-secondary">Login</RouterLink>
          <RouterLink to="/register" class="btn-primary">Register</RouterLink>
        </template>
      </AppNavigation>

      <!-- Hero Section -->
      <section
        class="relative z-10 flex flex-col items-center justify-center text-center px-6 py-24">
        <h1
          class="text-5xl md:text-6xl font-extrabold mb-6 leading-tight bg-gradient-to-r from-light to-primary bg-clip-text text-transparent">
          Conquer the Cold Frontier
        </h1>
        <p class="text-lg max-w-2xl mb-10 text-blue-200 leading-relaxed">
          Build your planetary empire, command mighty fleets, and raid rival colonies in this epic
          space strategy game.
          The galaxy awaits your command, commander!
        </p>
        <div class="flex flex-col sm:flex-row gap-4 mb-12">
          <RouterLink to="/register" class="btn-primary">Start Your Empire</RouterLink>
          <a href="#gameplay" class="btn-secondary">Learn More</a>
        </div>
      </section>

      <!-- Features Section -->
      <section id="features" class="relative z-10 px-6 py-24 bg-dark/50">
        <div class="text-center mb-12">
          <h2 class="text-4xl text-light mb-4">Game Features</h2>
          <p class="text-blue-200 max-w-2xl mx-auto">
            Explore the unique gameplay elements that make Cold Frontier an immersive space strategy
            experience
          </p>
        </div>
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8 max-w-7xl mx-auto">
          <div
            v-for="(feature, index) in features"
            :key="index"
            class="bg-gradient-to-br from-dark/80 to-darker/90 rounded-xl p-8 flex flex-col
                border border-primary/10 transition-all duration-300
                hover:transform hover:-translate-y-2.5 hover:shadow-xl hover:shadow-primary/20 hover:border-primary/30"
          >
            <div
              class="text-4xl mb-6 text-primary h-15 w-15 flex items-center justify-center rounded-xl bg-primary/10">
              {{ feature.icon }}
            </div>
            <h3 class="text-2xl text-light mb-4">{{ feature.title }}</h3>
            <p class="text-blue-200 leading-relaxed">{{ feature.description }}</p>
          </div>
        </div>
      </section>

      <!-- Gameplay Section -->
      <section id="gameplay" class="relative z-10 px-6 py-24">
        <div class="text-center mb-12">
          <h2 class="text-4xl text-light mb-4">Gameplay Highlights</h2>
          <p class="text-blue-200 max-w-2xl mx-auto">Dive deeper into the Cold Frontier
            experience</p>
        </div>
        <div class="flex flex-col gap-20 max-w-7xl mx-auto">
          <div
            v-for="(item, index) in gameplayItems"
            :key="index"
            :class="[
            'grid gap-12 items-center',
            index % 2 === 0 ? 'md:grid-cols-2' : 'md:grid-cols-2'
          ]"
          >
            <div
              :class="[
              'relative rounded-xl overflow-hidden shadow-xl border border-primary/30 h-[350px] bg-dark flex items-center justify-center',
              index % 2 === 0 ? '' : 'md:row-start-1 md:row-end-2 md:col-start-2 md:col-end-3'
            ]">
              <span class="text-lg text-blue-200 text-center p-4">{{ item.imagePlaceholder }}</span>
            </div>
            <div
              :class="[ index % 2 === 0 ? '' : 'md:row-start-1 md:row-end-2 md:col-start-1 md:col-end-2' ]">
              <h3 class="text-3xl text-light mb-6">{{ item.title }}</h3>
              <p class="text-blue-200 leading-relaxed mb-6">{{ item.description }}</p>
              <div v-for="(feature, featureIndex) in item.features" :key="featureIndex"
                   class="flex items-center mb-4">
                <div class="text-secondary mr-3">✓</div>
                <div>{{ feature }}</div>
              </div>
            </div>
          </div>
        </div>
      </section>

      <!-- Story Section -->
      <section id="story" class="relative z-10 px-6 py-24 bg-dark/50">
        <div class="text-center mb-12">
          <h2 class="text-4xl text-light mb-4">Tales from the Frontier</h2>
          <p class="text-blue-200 max-w-2xl mx-auto">
            Explore the rich narrative universe of Cold Frontier through the eyes of those who've ventured into the deep unknown
          </p>
        </div>
        <div class="max-w-4xl mx-auto">
          <TestimonialCarousel
            :quotes="gameStories"
            :autoplay="true"
            :interval="8000"
            class="story-carousel"
          />

          <div class="mt-16 text-center">
            <p class="text-blue-200 italic mb-6">
              Your story awaits among the stars. What legacy will you forge in the Cold Frontier?
            </p>
            <RouterLink to="/register" class="btn-primary">Begin Your Journey</RouterLink>
          </div>
        </div>
      </section>

      <!-- Call to Action -->
      <section
        class="relative z-10 px-6 py-24 text-center bg-gradient-to-br from-dark/80 to-darker/90">
        <div class="max-w-3xl mx-auto">
          <h2 class="text-5xl text-light mb-6">Ready to Claim Your Planet?</h2>
          <p class="text-blue-200 mb-10 text-lg leading-relaxed">
            Join thousands of commanders already exploring the Cold Frontier. Your empire awaits!
          </p>
          <div class="flex flex-col sm:flex-row justify-center gap-4">
            <RouterLink to="/register" class="btn-primary">Create Account</RouterLink>
            <RouterLink to="/login" class="btn-secondary">Login</RouterLink>
          </div>
        </div>
      </section>

    </div>
  </AppLayout>
</template>

<style scoped>
.story-carousel {
  box-shadow: 0 0 30px rgba(59, 130, 246, 0.15);
  position: relative;
}

.story-carousel::before {
  content: '';
  position: absolute;
  top: -10px;
  right: -10px;
  bottom: -10px;
  left: -10px;
  background: linear-gradient(45deg, rgba(59, 130, 246, 0.05), rgba(16, 185, 129, 0.05));
  border-radius: 1rem;
  z-index: -1;
  filter: blur(15px);
  opacity: 0.6;
}
</style>
