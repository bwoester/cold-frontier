<script setup lang="ts">
</script>

<template>
  <div class="stars"></div>

  <div class="min-h-full flex flex-col">
    <slot name="navigation"></slot>

    <!-- Main content -->
    <main>
      <slot></slot>
    </main>

    <!-- Footer -->
    <slot name="footer"></slot>
  </div>
</template>

<style>
:root {
  --primary: #4a6bff;
  --secondary: #21d07c;
  --dark: #0f1631;
  --darker: #070c1f;
  --light: #e6e9ff;
  --accent: #ff5b7a;
}

body {
  background-color: var(--darker);
  color: var(--light);
  background-image: radial-gradient(circle at 20% 35%, rgba(74, 107, 255, 0.15) 0%, transparent 50%),
  radial-gradient(circle at 75% 65%, rgba(255, 91, 122, 0.1) 0%, transparent 50%);
  background-attachment: fixed;
  display: flex;
  flex-direction: column;
}

.stars {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: -1;
  pointer-events: none;
  overflow: hidden;
}

.stars:before {
  content: '';
  position: absolute;
  width: 100%;
  height: 100%;
  background-image: radial-gradient(2px 2px at 20px 30px, white, rgba(0, 0, 0, 0)),
  radial-gradient(2px 2px at 40px 70px, white, rgba(0, 0, 0, 0)),
  radial-gradient(1px 1px at 90px 40px, white, rgba(0, 0, 0, 0)),
  radial-gradient(2px 2px at 160px 120px, white, rgba(0, 0, 0, 0)),
  radial-gradient(1px 1px at 230px 190px, white, rgba(0, 0, 0, 0));
  background-repeat: repeat;
  background-size: 250px 250px;
  opacity: 0.6;
  animation: twinkle 5s linear infinite;
}

@keyframes twinkle {
  0% {
    opacity: 0.4;
  }
  50% {
    opacity: 0.8;
  }
  100% {
    opacity: 0.4;
  }
}

.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1.5rem 3rem;
  background-color: rgba(15, 22, 49, 0.8);
  backdrop-filter: blur(10px);
  position: sticky;
  top: 0;
  z-index: 100;
  border-bottom: 1px solid rgba(74, 107, 255, 0.2);
}

.logo {
  font-size: 1.8rem;
  font-weight: 700;
  color: var(--light);
  text-decoration: none;
  display: flex;
  align-items: center;
}

.logo span {
  color: var(--primary);
}

.btn {
  padding: 0.75rem 1.5rem;
  border-radius: 4px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s;
  border: none;
  text-decoration: none;
  text-align: center;
}

.btn-primary {
  background-color: var(--primary);
  color: white;
  box-shadow: 0 4px 12px rgba(74, 107, 255, 0.3);
}

.btn-primary:hover {
  background-color: #3a5eff;
  transform: translateY(-2px);
  box-shadow: 0 6px 16px rgba(74, 107, 255, 0.4);
}

.btn-secondary {
  background-color: transparent;
  color: var(--light);
  border: 1px solid var(--primary);
}

.btn-secondary:hover {
  background-color: rgba(74, 107, 255, 0.1);
  transform: translateY(-2px);
}

footer {
  background-color: rgba(7, 12, 31, 0.8);
  padding: 1.5rem;
  text-align: center;
  border-top: 1px solid rgba(74, 107, 255, 0.2);
  font-size: 0.9rem;
  color: #b4bbde;
}

@media (max-width: 768px) {
  .navbar {
    padding: 1rem;
  }
}
</style>
