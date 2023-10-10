import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import TaskListView from '../views/TaskListView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/task',
      name: 'task',
      component: () => import('../views/Task.vue'),
      meta: { requiresAuth: true }  // Indicamos que esta ruta requiere autenticación.
    },
    {
      path: '/taskList',
      name: 'taskList',
      component: TaskListView
    }
  ]
  
})


router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token');

  // Comprueba si la ruta requiere autenticación y si el token no existe.
  if (to.matched.some(record => record.meta.requiresAuth) && !token) {
    next('/')  // Redirigir al inicio o a la página de inicio de sesión.
  } else {
    next()  // De lo contrario, continuar con la ruta.
  }
})

export default router