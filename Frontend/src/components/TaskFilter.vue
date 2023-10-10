<template>
    <div class="filters">
      <div class="mb-3 col-md-9">
        <input v-model="keyword" class="form-control" placeholder="Palabra clave">
      </div>
  
      <select v-model="statusFilter" class="form-select mb-3">
        <option value="todos">Todos</option>
        <option value="pendiente">Pendiente</option>
        <option value="completada">Completada</option>
      </select>
  
      <button @click="fetchFilteredTasks" class="btn btn-primary mb-3">Buscar</button>
  
     <!-- <ul class="list-group">
        <li v-for="task in filteredTasks" :key="task.id" class="list-group-item">
          <strong>{{ task.title }}</strong>
          <p>{{ task.description }}</p>
          <span v-if="task.status === 'completada'" class="badge bg-success">Completada</span>
          <span v-else class="badge bg-warning">Pendiente</span>
        </li>
      </ul>-->
    </div>
  </template>
  
  <script>
import axios from 'axios';
import { ref } from 'vue';
import jwtDecode from 'jwt-decode';

export default {
  name: "TaskFilter",
  setup() {
    const tasks = ref([]);
    const keyword = ref('');
    const statusFilter = ref('todos');
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


.filters {
  display: flex;
  
  margin: 10px;

}

.mb-3{
  margin-right: 10px;
}

</style>
