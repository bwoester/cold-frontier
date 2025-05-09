<script setup lang="ts">
import Navigation from "@/views/public/components/Navigation.vue";
import PublicLayout from "@/layouts/PublicLayout.vue";
import Footer from "@/views/public/components/Footer.vue";
</script>

<template>
  <PublicLayout>

    <template #navigation>
      <Navigation :showAuthButtons="false" :showNavLinks="false"></Navigation>
    </template>

    <!-- Login Section -->
    <div class="login-container">
      <div class="login-card">
        <div class="login-header">
          <h1>Welcome Back</h1>
          <p>Login to continue your conquest of the Cold Frontier</p>
        </div>

        <div class="auth-options">
          <a href="/api/auth/google" class="auth-btn google">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24">
              <path fill="#4285F4"
                    d="M22.56 12.25c0-.78-.07-1.53-.2-2.25H12v4.26h5.92c-.26 1.37-1.04 2.53-2.21 3.31v2.77h3.57c2.08-1.92 3.28-4.74 3.28-8.09z"/>
              <path fill="#34A853"
                    d="M12 23c2.97 0 5.46-.98 7.28-2.66l-3.57-2.77c-.98.66-2.23 1.06-3.71 1.06-2.86 0-5.29-1.93-6.16-4.53H2.18v2.84C3.99 20.53 7.7 23 12 23z"/>
              <path fill="#FBBC05"
                    d="M5.84 14.09c-.22-.66-.35-1.36-.35-2.09s.13-1.43.35-2.09V7.07H2.18C1.43 8.55 1 10.22 1 12s.43 3.45 1.18 4.93l2.85-2.22.81-.62z"/>
              <path fill="#EA4335"
                    d="M12 5.38c1.62 0 3.06.56 4.21 1.64l3.15-3.15C17.45 2.09 14.97 1 12 1 7.7 1 3.99 3.47 2.18 7.07l3.66 2.84c.87-2.6 3.3-4.53 6.16-4.53z"/>
            </svg>
            Continue with Google
          </a>
          <a href="/api/auth/github" class="auth-btn github">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"
                 fill="white">
              <path
                d="M12 .297c-6.63 0-12 5.373-12 12 0 5.303 3.438 9.8 8.205 11.385.6.113.82-.258.82-.577 0-.285-.01-1.04-.015-2.04-3.338.724-4.042-1.61-4.042-1.61C4.422 18.07 3.633 17.7 3.633 17.7c-1.087-.744.084-.729.084-.729 1.205.084 1.838 1.236 1.838 1.236 1.07 1.835 2.809 1.305 3.495.998.108-.776.417-1.305.76-1.605-2.665-.3-5.466-1.332-5.466-5.93 0-1.31.465-2.38 1.235-3.22-.135-.303-.54-1.523.105-3.176 0 0 1.005-.322 3.3 1.23.96-.267 1.98-.399 3-.405 1.02.006 2.04.138 3 .405 2.28-1.552 3.285-1.23 3.285-1.23.645 1.653.24 2.873.12 3.176.765.84 1.23 1.91 1.23 3.22 0 4.61-2.805 5.625-5.475 5.92.42.36.81 1.096.81 2.22 0 1.606-.015 2.896-.015 3.286 0 .315.21.69.825.57C20.565 22.092 24 17.592 24 12.297c0-6.627-5.373-12-12-12"/>
            </svg>
            Continue with GitHub
          </a>
          <a href="/api/auth/facebook" class="auth-btn facebook">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"
                 fill="white">
              <path
                d="M22.675 0H1.325C.593 0 0 .593 0 1.325v21.351C0 23.407.593 24 1.325 24H12.82v-9.294H9.692v-3.622h3.128V8.413c0-3.1 1.893-4.788 4.659-4.788 1.325 0 2.463.099 2.795.143v3.24l-1.918.001c-1.504 0-1.795.715-1.795 1.763v2.313h3.587l-.467 3.622h-3.12V24h6.116c.73 0 1.323-.593 1.323-1.325V1.325C24 .593 23.407 0 22.675 0z"/>
            </svg>
            Continue with Facebook
          </a>
        </div>

        <p class="create-account">
          Don't have an account?
          <RouterLink to="/register" class="font-semibold">Sign up</RouterLink>
        </p>

        <RouterLink to="/" class="font-medium block mt-8">Back to Home</RouterLink>

      </div>
    </div>

    <template #footer>
      <Footer :show-container="false"></Footer>
    </template>

  </PublicLayout>
</template>

<style>

a {
  color: var(--primary);
}


/*
:root {
  --primary: #4a6bff;
  --secondary: #21d07c;
  --dark: #0f1631;
  --darker: #070c1f;
  --light: #e6e9ff;
  --accent: #ff5b7a;
}
*/

/*
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}
*/

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
    opacity: 0.6;
  }
  50% {
    opacity: 0.8;
  }
  100% {
    opacity: 0.6;
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

.login-container {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 2rem;
}

.login-card {
  background: linear-gradient(135deg, rgba(15, 22, 49, 0.8) 0%, rgba(7, 12, 31, 0.95) 100%);
  border-radius: 1rem;
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.3);
  border: 1px solid rgba(74, 107, 255, 0.2);
  width: 100%;
  max-width: 450px;
  padding: 3rem;
  text-align: center;
  backdrop-filter: blur(10px);
}

.login-header {
  margin-bottom: 2rem;
}

.login-header h1 {
  font-size: 2.5rem;
  font-weight: 700;
  margin-bottom: 1rem;
  color: var(--light);
}

.login-header p {
  color: #b4bbde;
  font-size: 1.1rem;
  line-height: 1.6;
}

.auth-options {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  margin-bottom: 2rem;
}

.auth-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.75rem;
  padding: 1rem;
  border-radius: 0.5rem;
  background: var(--dark);
  border: 1px solid rgba(74, 107, 255, 0.2);
  color: var(--light);
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s;
  text-decoration: none;
}

.auth-btn:hover {
  transform: translateY(-3px);
  box-shadow: 0 8px 15px rgba(0, 0, 0, 0.2);
  border-color: rgba(74, 107, 255, 0.4);
}

.auth-btn img {
  width: 24px;
  height: 24px;
}

.auth-btn.google {
  background-color: #ffffff;
  color: #444;
}

.auth-btn.github {
  background-color: #24292e;
}

.auth-btn.facebook {
  background-color: #3b5998;
}

.auth-divider {
  display: flex;
  align-items: center;
  margin: 1.5rem 0;
}

.auth-divider::before,
.auth-divider::after {
  content: "";
  flex: 1;
  height: 1px;
  background-color: rgba(74, 107, 255, 0.2);
}

.auth-divider span {
  padding: 0 1rem;
  color: #b4bbde;
  font-size: 0.9rem;
}

.back-to-home {
  color: var(--primary);
  text-decoration: none;
  display: inline-flex;
  align-items: center;
  gap: 0.5rem;
  margin-top: 2rem;
  font-weight: 500;
  transition: color 0.3s;
}

.back-to-home:hover {
  color: #3a5eff;
}

.create-account {
  margin-top: 2rem;
  color: #b4bbde;
}

.create-account a {
  color: var(--primary);
  text-decoration: none;
  font-weight: 600;
  transition: color 0.3s;
}

.create-account a:hover {
  color: #3a5eff;
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

  .login-card {
    padding: 2rem;
  }

  .login-header h1 {
    font-size: 2rem;
  }
}
</style>
