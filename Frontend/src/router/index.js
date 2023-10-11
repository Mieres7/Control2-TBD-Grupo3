import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import TaskListView from '../views/TaskListView.vue'
import jwtDecode from "jwt-decode";

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
      meta: { requiresAuth: true }
    },
    {
      path: '/taskList',
      name: 'taskList',
      component: TaskListView,
      meta: { requiresAuth: true } 
    }
  ]
  
})


router.beforeEach((to, next) => {
  const token = localStorage.getItem('token');
  if(token) {
    const decodedToken = jwtDecode(token);
    const tokenExpireTime = decodedToken.exp;
    const currentTimeInSeconds = Math.floor(Date.now() / 1000);

    // Comprueba si la ruta requiere autenticación y si el token es valido aún.
    if (to.matched.some(record => record.meta.requiresAuth) && (tokenExpireTime < currentTimeInSeconds)) {
      next('/')
    } else {
      next()
    }
  } else {
    next('/')
  }
})

export default router