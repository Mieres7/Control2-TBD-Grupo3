<template>
    <div>
      <div class="mb-3">
        <input v-model="keyword" class="form-control" placeholder="Palabra clave">
      </div>
  
      <select v-model="statusFilter" class="form-select mb-3">
        <option value="todos">Todos</option>
        <option value="pendiente">Pendiente</option>
        <option value="completada">Completada</option>
      </select>
  
      <button @click="fetchFilteredTasks" class="btn btn-primary mb-3">Buscar</button>
  
      <ul class="list-group">
        <li v-for="task in filteredTasks" :key="task.id" class="list-group-item">
          <strong>{{ task.title }}</strong>
          <p>{{ task.description }}</p>
          <span v-if="task.status === 'completada'" class="badge bg-success">Completada</span>
          <span v-else class="badge bg-warning">Pendiente</span>
        </li>
      </ul>
    </div>
  </template>
  
  <script>
import axios from 'axios';
import { ref } from 'vue';
import jwtDecode from 'jwt-decode';

export default {
  name: "fetchFilteredTasksComponent",
  setup() {
    const tasks = ref([]);
    const keyword = ref('');
    const statusFilter = ref('');
    const filteredTasks = ref([]);
    const error = ref("");

    const fetchFilteredTasks = async () => {
      const token = localStorage.getItem('token');
      axios.defaults.headers.common['Authorization'] = 'Bearer ' + token;
      if (token) {
        try {
        const decodedToken = jwtDecode(token);
          const userId = decodedToken.id;
          const response = await axios.get('http://localhost:8080/tasks/' + userId + '/key/' + keyword.value + '/status/' + statusFilter.value.toUpperCase());
          filteredTasks.value = response.data;
          console.log(response.data);
        } catch (e) {
            console.log(e);
          error.value = 'Error al buscar tareas: ' + e.message;
          setTimeout(() => {
            error.value = ""
          }, 5000);
        }
      } else {
        error.value = 'Token no encontrado. Por favor, autentíquese primero.';
        setTimeout(() => {
          error.value = ""
        }, 5000);
      }
    };

    return { fetchFilteredTasks, tasks, keyword, statusFilter, filteredTasks, error }
  }
};
</script>

<style scoped>
/* Estilos personalizados */
.custom-input {
  background-color: #f0f0f0; /* Cambia el color de fondo del input */
  color: #333; /* Cambia el color de la fuente del input */
}

.custom-select {
  background-color: #f0f0f0; /* Cambia el color de fondo del select */
  color: #333; /* Cambia el color de la fuente del select */
}

.custom-button {
  background-color: #007bff; /* Cambia el color de fondo del botón */
  color: #fff; /* Cambia el color de la fuente del botón */
}

.custom-list-item {
  background-color: #114c5f; /* Cambia el color de fondo de los elementos de la lista */
  color: white; /* Cambia el color de la fuente de los elementos de la lista */
  border: 1px solid #ccc; /* Agrega un borde a los elementos de la lista */
  margin-bottom: 5px;
  padding: 10px;
}

.custom-badge {
  /* Cambia el estilo de los badges (etiquetas) */
  font-size: 12px; /* Tamaño de fuente más pequeño */
  padding: 3px 6px; /* Espaciado interno personalizado */
}
</style>