<script setup lang="ts">
import AppNavigation from '@/components/public/AppNavigation.vue';
import PublicLayout from '@/components/public/PublicLayout.vue';

// Auth providers configuration - same as in LoginPage for consistency
const authProviders = [
  {
    name: 'google',
    url: '/api/auth/google',
    text: 'Sign up with Google',
    uniqueStyle: 'bg-white text-gray-800',
    svgFill: '',
    svgPaths: [
      {
        fill: '#4285F4',
        d: 'M22.56 12.25c0-.78-.07-1.53-.2-2.25H12v4.26h5.92c-.26 1.37-1.04 2.53-2.21 3.31v2.77h3.57c2.08-1.92 3.28-4.74 3.28-8.09z'
      },
      {
        fill: '#34A853',
        d: 'M12 23c2.97 0 5.46-.98 7.28-2.66l-3.57-2.77c-.98.66-2.23 1.06-3.71 1.06-2.86 0-5.29-1.93-6.16-4.53H2.18v2.84C3.99 20.53 7.7 23 12 23z'
      },
      {
        fill: '#FBBC05',
        d: 'M5.84 14.09c-.22-.66-.35-1.36-.35-2.09s.13-1.43.35-2.09V7.07H2.18C1.43 8.55 1 10.22 1 12s.43 3.45 1.18 4.93l2.85-2.22.81-.62z'
      },
      {
        fill: '#EA4335',
        d: 'M12 5.38c1.62 0 3.06.56 4.21 1.64l3.15-3.15C17.45 2.09 14.97 1 12 1 7.7 1 3.99 3.47 2.18 7.07l3.66 2.84c.87-2.6 3.3-4.53 6.16-4.53z'
      }
    ]
  },
  {
    name: 'github',
    url: '/api/auth/github',
    text: 'Sign up with GitHub',
    uniqueStyle: 'bg-gray-900 text-white',
    svgFill: 'white',
    svgPaths: [
      {d: 'M12 .297c-6.63 0-12 5.373-12 12 0 5.303 3.438 9.8 8.205 11.385.6.113.82-.258.82-.577 0-.285-.01-1.04-.015-2.04-3.338.724-4.042-1.61-4.042-1.61C4.422 18.07 3.633 17.7 3.633 17.7c-1.087-.744.084-.729.084-.729 1.205.084 1.838 1.236 1.838 1.236 1.07 1.835 2.809 1.305 3.495.998.108-.776.417-1.305.76-1.605-2.665-.3-5.466-1.332-5.466-5.93 0-1.31.465-2.38 1.235-3.22-.135-.303-.54-1.523.105-3.176 0 0 1.005-.322 3.3 1.23.96-.267 1.98-.399 3-.405 1.02.006 2.04.138 3 .405 2.28-1.552 3.285-1.23 3.285-1.23.645 1.653.24 2.873.12 3.176.765.84 1.23 1.91 1.23 3.22 0 4.61-2.805 5.625-5.475 5.92.42.36.81 1.096.81 2.22 0 1.606-.015 2.896-.015 3.286 0 .315.21.69.825.57C20.565 22.092 24 17.592 24 12.297c0-6.627-5.373-12-12-12'}
    ]
  },
  {
    name: 'facebook',
    url: '/api/auth/facebook',
    text: 'Sign up with Facebook',
    uniqueStyle: 'bg-blue-900 text-white',
    svgFill: 'white',
    svgPaths: [
      {d: 'M22.675 0H1.325C.593 0 0 .593 0 1.325v21.351C0 23.407.593 24 1.325 24H12.82v-9.294H9.692v-3.622h3.128V8.413c0-3.1 1.893-4.788 4.659-4.788 1.325 0 2.463.099 2.795.143v3.24l-1.918.001c-1.504 0-1.795.715-1.795 1.763v2.313h3.587l-.467 3.622h-3.12V24h6.116c.73 0 1.323-.593 1.323-1.325V1.325C24 .593 23.407 0 22.675 0z'}
    ]
  }
];

// Form data
import { ref } from 'vue';

const username = ref('');
const email = ref('');
const password = ref('');
const confirmPassword = ref('');
const acceptTerms = ref(false);
const formError = ref('');

// Form validation
const validateForm = () => {
  formError.value = '';

  if (!username.value) {
    formError.value = 'Username is required';
    return false;
  }

  if (!email.value) {
    formError.value = 'Email is required';
    return false;
  }

  if (!password.value) {
    formError.value = 'Password is required';
    return false;
  }

  if (password.value !== confirmPassword.value) {
    formError.value = 'Passwords do not match';
    return false;
  }

  if (!acceptTerms.value) {
    formError.value = 'You must accept the terms and conditions';
    return false;
  }

  return true;
};

// Form submission
const handleSubmit = (e: Event) => {
  e.preventDefault();

  if (validateForm()) {
    // Form is valid, proceed with submission
    alert('Registration form submitted!');
    // In a real application, you would send the data to your backend
    // fetch('/api/auth/register', {
    //   method: 'POST',
    //   headers: {
    //     'Content-Type': 'application/json',
    //   },
    //   body: JSON.stringify({
    //     username: username.value,
    //     email: email.value,
    //     password: password.value
    //   }),
    // })
  }
};
</script>

<template>
  <PublicLayout>

    <!-- Navigation -->
    <AppNavigation>
    </AppNavigation>

    <!-- Register Section -->
    <div class="register-container flex-1 flex items-center justify-center p-8">
      <div
        class="register-card
          bg-gradient-to-br from-dark/80 to-darker/95 rounded-xl shadow-lg border border-primary/20 w-full max-w-450 p-12 text-center backdrop-blur-sm">
        <div class="register-header mb-8 text-center">
          <h1 class="text-4xl font-bold mb-4 text-light">Join the Galaxy</h1>
          <p class="text-blue-200 text-lg leading-relaxed">Create your account and begin your quest to dominate the Cold Frontier</p>
        </div>

        <div class="auth-options flex flex-col gap-4 mb-8">
          <a
            v-for="provider in authProviders"
            :key="provider.name"
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
        </div>

        <p class="login-link mt-8 text-center text-blue-200">
          Already have an account?
          <RouterLink to="/login"
             class="font-semibold
            text-primary no-underline transition-colors duration-300 hover:text-primary-dark">Sign in</RouterLink>
        </p>

        <RouterLink to="/"
                    class="inline-flex items-center gap-2 mt-8
              font-medium
              text-primary no-underline transition-colors duration-300 hover:text-primary-dark">
          <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
               fill="currentColor">
            <path d="M20 11H7.83l5.59-5.59L12 4l-8 8 8 8 1.41-1.41L7.83 13H20v-2z"/>
          </svg>
          Back to Home
        </RouterLink>
      </div>
    </div>
  </PublicLayout>
</template>

<style scoped>
@reference "@/assets/public.css";

.register-card {
  background: linear-gradient(135deg, rgba(15, 22, 49, 0.8) 0%, rgba(7, 12, 31, 0.95) 100%);
  border-radius: 1rem;
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.3);
  border: 1px solid rgba(74, 107, 255, 0.2);
  width: 100%;
  max-width: 450px;
  padding: 3rem;
  backdrop-filter: blur(10px);
}

@media (max-width: 768px) {
  .register-card {
    padding: 2rem;
  }
}
</style>
