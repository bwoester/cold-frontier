<script setup lang="ts">
import Navigation from "@/views/public/components/Navigation.vue";
import PublicLayout from "@/layouts/PublicLayout.vue";
import Footer from "@/views/public/components/Footer.vue";
</script>

<template>
  <PublicLayout>
    <template #navigation>
      <Navigation></Navigation>
    </template>

    <template #footer>
      <Footer></Footer>
    </template>

    <!-- Hero Section -->
    <section class="hero">
      <h1>Conquer the Cold Frontier</h1>
      <p>Build your planetary empire, command mighty fleets, and raid rival colonies in this epic
        space strategy game. The galaxy awaits your command, commander!</p>
      <div class="hero-buttons">
        <a href="register" class="btn btn-primary">Start Your Empire</a>
        <a href="#gameplay" class="btn btn-secondary">Learn More</a>
      </div>
    </section>

    <!-- Features Section -->
    <section class="features" id="features">
      <div class="section-title">
        <h2>Game Features</h2>
        <p>Explore the unique gameplay elements that make Cold Frontier an immersive space strategy
          experience</p>
      </div>
      <div class="features-grid">
        <div class="feature-card">
          <div class="feature-icon">🪐</div>
          <h3>Planet Development</h3>
          <p>Develop and customize your home planet with various buildings, defenses, and resource
            generators. Create the perfect base of operations for your galactic empire.</p>
        </div>
        <div class="feature-card">
          <div class="feature-icon">🚀</div>
          <h3>Fleet Management</h3>
          <p>Design and build powerful fleets with a variety of ships, from nimble scouts to massive
            battleships. Each vessel has unique abilities and tactical advantages.</p>
        </div>
        <div class="feature-card">
          <div class="feature-icon">⚔️</div>
          <h3>Strategic Raids</h3>
          <p>Launch raids against other players' colonies to steal resources, sabotage their
            infrastructure, or establish dominance in contested star systems.</p>
        </div>
        <div class="feature-card">
          <div class="feature-icon">🤝</div>
          <h3>Alliances</h3>
          <p>Form powerful alliances with other players to protect your territories, coordinate
            attacks, and control valuable regions of space.</p>
        </div>
        <div class="feature-card">
          <div class="feature-icon">📈</div>
          <h3>Resource Economy</h3>
          <p>Manage a complex economy of rare materials, energy, and technology. Trade with other
            players or raid their stockpiles to fuel your expansion.</p>
        </div>
        <div class="feature-card">
          <div class="feature-icon">🔬</div>
          <h3>Research & Technology</h3>
          <p>Unlock powerful technologies through an extensive research tree. Specialize your empire
            for defense, offense, economy, or a balanced approach.</p>
        </div>
      </div>
    </section>

    <!-- Gameplay Section -->
    <section class="gameplay" id="gameplay">
      <div class="section-title">
        <h2>Gameplay Highlights</h2>
        <p>Dive deeper into the Cold Frontier experience</p>
      </div>
      <div class="gameplay-container">
        <div class="gameplay-item">
          <div class="gameplay-image">
            <span>[Planet development interface showcase]</span>
          </div>
          <div class="gameplay-content">
            <h3>Build Your Planet</h3>
            <p>Your home planet is the heart of your empire. Develop it wisely to generate
              resources, research technologies, and defend against enemy attacks.</p>
            <div class="gameplay-feature">
              <div class="icon">✓</div>
              <div>Construct specialized buildings for resource production</div>
            </div>
            <div class="gameplay-feature">
              <div class="icon">✓</div>
              <div>Establish defensive structures to repel raiders</div>
            </div>
            <div class="gameplay-feature">
              <div class="icon">✓</div>
              <div>Balance civil and military infrastructure</div>
            </div>
          </div>
        </div>

        <div class="gameplay-item">
          <div class="gameplay-image">
            <span>[Fleet management and combat visualization]</span>
          </div>
          <div class="gameplay-content">
            <h3>Command Your Fleets</h3>
            <p>Design, build, and command powerful fleets to explore the galaxy, protect your
              assets, and raid enemy planets.</p>
            <div class="gameplay-feature">
              <div class="icon">✓</div>
              <div>Choose from dozens of specialized ship designs</div>
            </div>
            <div class="gameplay-feature">
              <div class="icon">✓</div>
              <div>Deploy fleets with tactical precision</div>
            </div>
            <div class="gameplay-feature">
              <div class="icon">✓</div>
              <div>Customize your flagship with special abilities</div>
            </div>
          </div>
        </div>

        <div class="gameplay-item">
          <div class="gameplay-image">
            <span>[Alliance and diplomacy interface]</span>
          </div>
          <div class="gameplay-content">
            <h3>Forge Alliances</h3>
            <p>The cold frontier is dangerous to navigate alone. Form alliances with other
              commanders to protect your interests and dominate rival factions.</p>
            <div class="gameplay-feature">
              <div class="icon">✓</div>
              <div>Coordinate attacks with alliance members</div>
            </div>
            <div class="gameplay-feature">
              <div class="icon">✓</div>
              <div>Share resources and technology</div>
            </div>
            <div class="gameplay-feature">
              <div class="icon">✓</div>
              <div>Participate in alliance-exclusive events</div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Call to Action -->
    <section class="cta">
      <div class="cta-container">
        <h2>Ready to Claim Your Planet?</h2>
        <p>Join thousands of commanders already exploring the Cold Frontier. Your empire awaits!</p>
        <div class="cta-buttons">
          <a href="register" class="btn btn-primary">Create Account</a>
          <a href="login" class="btn btn-secondary">Login</a>
        </div>
      </div>
    </section>

  </PublicLayout>


</template>

<style>
body {
  overflow-x: hidden;
}

.nav-links {
  display: flex;
  gap: 2rem;
  list-style: none;
}

.nav-links a {
  color: var(--light);
  text-decoration: none;
  font-weight: 500;
  padding: 0.5rem 0;
  transition: color 0.3s;
  position: relative;
}

.nav-links a:hover {
  color: var(--primary);
}

.nav-links a:after {
  content: '';
  position: absolute;
  width: 0;
  height: 2px;
  background-color: var(--primary);
  bottom: 0;
  left: 0;
  transition: width 0.3s;
}

.nav-links a:hover:after {
  width: 100%;
}

.auth-buttons {
  display: flex;
  gap: 1rem;
}

.hero {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  text-align: center;
  padding: 6rem 2rem;
  position: relative;
  overflow: hidden;
}

.hero h1 {
  font-size: 4rem;
  font-weight: 800;
  margin-bottom: 1.5rem;
  line-height: 1.2;
  background: linear-gradient(90deg, var(--light) 0%, var(--primary) 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}

.hero p {
  font-size: 1.2rem;
  max-width: 700px;
  margin-bottom: 2.5rem;
  color: #b4bbde;
  line-height: 1.6;
}

.hero-buttons {
  display: flex;
  gap: 1rem;
  margin-bottom: 3rem;
}

.features {
  padding: 5rem 2rem;
  background-color: rgba(15, 22, 49, 0.5);
}

.section-title {
  text-align: center;
  margin-bottom: 3rem;
}

.section-title h2 {
  font-size: 2.5rem;
  color: var(--light);
  margin-bottom: 1rem;
}

.section-title p {
  color: #b4bbde;
  max-width: 700px;
  margin: 0 auto;
}

.features-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 2rem;
  max-width: 1200px;
  margin: 0 auto;
}

.feature-card {
  background: linear-gradient(135deg, rgba(15, 22, 49, 0.8) 0%, rgba(7, 12, 31, 0.9) 100%);
  border-radius: 12px;
  padding: 2rem;
  display: flex;
  flex-direction: column;
  transition: transform 0.3s, box-shadow 0.3s;
  border: 1px solid rgba(74, 107, 255, 0.1);
}

.feature-card:hover {
  transform: translateY(-10px);
  box-shadow: 0 10px 25px rgba(74, 107, 255, 0.2);
  border-color: rgba(74, 107, 255, 0.3);
}

.feature-icon {
  font-size: 2.5rem;
  margin-bottom: 1.5rem;
  color: var(--primary);
  height: 60px;
  width: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 12px;
  background: rgba(74, 107, 255, 0.1);
}

.feature-card h3 {
  font-size: 1.5rem;
  margin-bottom: 1rem;
  color: var(--light);
}

.feature-card p {
  color: #b4bbde;
  line-height: 1.6;
}

.gameplay {
  padding: 5rem 2rem;
  position: relative;
}

.gameplay-container {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  gap: 5rem;
}

.gameplay-item {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 3rem;
  align-items: center;
}

.gameplay-item:nth-child(even) {
  direction: rtl;
}

.gameplay-item:nth-child(even) .gameplay-content {
  direction: ltr;
}

.gameplay-image {
  position: relative;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.3);
  border: 1px solid rgba(74, 107, 255, 0.3);
  height: 350px;
  background-color: var(--dark);
  display: flex;
  align-items: center;
  justify-content: center;
}

.gameplay-image span {
  font-size: 1.2rem;
  color: #b4bbde;
  text-align: center;
  padding: 1rem;
}

.gameplay-content h3 {
  font-size: 2rem;
  margin-bottom: 1.5rem;
  color: var(--light);
}

.gameplay-content p {
  color: #b4bbde;
  line-height: 1.8;
  margin-bottom: 1.5rem;
}

.gameplay-feature {
  display: flex;
  align-items: center;
  margin-bottom: 1rem;
}

.gameplay-feature .icon {
  color: var(--secondary);
  margin-right: 0.75rem;
  font-size: 1.2rem;
}

.cta {
  padding: 6rem 2rem;
  text-align: center;
  background: linear-gradient(135deg, rgba(15, 22, 49, 0.9) 0%, rgba(7, 12, 31, 0.95) 100%);
  position: relative;
}

.cta-container {
  max-width: 800px;
  margin: 0 auto;
}

.cta h2 {
  font-size: 3rem;
  margin-bottom: 1.5rem;
  color: var(--light);
}

.cta p {
  color: #b4bbde;
  margin-bottom: 2.5rem;
  font-size: 1.2rem;
  line-height: 1.6;
}

.cta-buttons {
  display: flex;
  justify-content: center;
  gap: 1rem;
}

footer {
  background-color: var(--darker);
  padding: 3rem 2rem;
}

.footer-container {
  max-width: 1200px;
  margin: 0 auto;
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 3rem;
}

.footer-logo {
  font-size: 1.8rem;
  font-weight: 700;
  color: var(--light);
  margin-bottom: 1rem;
  display: inline-block;
}

.footer-logo span {
  color: var(--primary);
}

.footer-text {
  color: #b4bbde;
  line-height: 1.6;
  margin-bottom: 1.5rem;
}

.footer-links h4 {
  color: var(--light);
  margin-bottom: 1.5rem;
  font-size: 1.2rem;
}

.footer-links ul {
  list-style: none;
}

.footer-links li {
  margin-bottom: 0.75rem;
}

.footer-links a {
  color: #b4bbde;
  text-decoration: none;
  transition: color 0.3s;
}

.footer-links a:hover {
  color: var(--primary);
}

.copyright {
  text-align: center;
  padding-top: 3rem;
  color: #b4bbde;
  font-size: 0.9rem;
}

@media (max-width: 768px) {
  .navbar {
    flex-direction: column;
    gap: 1rem;
  }

  .nav-links {
    flex-direction: column;
    gap: 1rem;
    text-align: center;
  }

  .hero h1 {
    font-size: 2.5rem;
  }

  .gameplay-item {
    grid-template-columns: 1fr;
    gap: 2rem;
  }

  .gameplay-item:nth-child(even) {
    direction: ltr;
  }

  .hero-buttons, .cta-buttons {
    flex-direction: column;
    width: 100%;
    max-width: 300px;
  }
}
</style>
