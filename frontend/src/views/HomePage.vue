<script setup lang="ts">
import AppLayout from "@/components/AppLayout.vue";
import AppNavigation from "@/components/AppNavigation.vue";

const navLinks = [
  {text: 'Features', href: '#features'},
  {text: 'Gameplay', href: '#gameplay'},
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

const footerLinks = [
  {
    title: 'Game',
    links: [
      {text: 'Features', href: '#features'},
      {text: 'Gameplay', href: '#gameplay'},
      {text: 'Updates', href: '/updates'},
      {text: 'Roadmap', href: '/roadmap'}
    ]
  },
  {
    title: 'Community',
    links: [
      {text: 'Forums', href: '/forums'},
      {text: 'Discord', href: '/discord'},
      {text: 'Wiki', href: '/wiki'},
      {text: 'Support', href: '/support'}
    ]
  },
  {
    title: 'Legal',
    links: [
      {text: 'Terms of Service', href: '/terms'},
      {text: 'Privacy Policy', href: '/privacy'},
      {text: 'Cookie Policy', href: '/cookies'}
    ]
  }
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

      <!-- Footer -->
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
    </div>
  </AppLayout>
</template>

<style scoped>
@reference "@/assets/home.css";

.btn-primary {
  @apply inline-block px-5 py-3 bg-primary text-slate-100 font-medium rounded-lg text-center transition-all duration-300
  hover:bg-primary/90 hover:transform-[translateY(-2px)] hover:shadow-lg hover:shadow-primary/30 ;
}

.btn-secondary {
  @apply inline-block px-5 py-3 bg-transparent border border-primary text-slate-100 font-medium rounded-lg text-center transition-all duration-300
  hover:bg-primary/10 hover:transform-[translateY(-2px)];
}
</style>
