<script>
import axios from "axios";
import { ref } from "vue";
import jwtDecode from "jwt-decode";

export default {
  name: "createTask",
  setup() {
    const task = ref({
      title_task: "",
      description_task: "",
      expire_date: "",
      expire_time: "",
      status_task: "PENDIENTE",
      id_user: "",
    });
    const error = ref("");
    const advice = ref("");

    const createTask = () => {
      const token = localStorage.getItem("token");
      axios.defaults.headers.common["Authorization"] =
        "Bearer " + localStorage.getItem("token");
      if (token) {
        const decodedToken = jwtDecode(token);
        task.value.id_user = decodedToken.id;

        const formattedTime = `${task.value.expire_time}:00`;

        const newTask = {
          title_task: task.value.title_task,
          description_task: task.value.description_task,
          expire_date: task.value.expire_date,
          expire_time: formattedTime,
          status_task: task.value.status_task,
          id_user: task.value.id_user,
        };

        axios.post("http://localhost:8080/task", newTask)
          .then((response) => {
            advice.value = "Tarea creada correctamente.";
            setTimeout(() => {
              advice.value = "";
            }, 5000);
          })
          .catch((e) => {
            error.value = "Error al crear la tarea: " + e.message;
            setTimeout(() => {
              error.value = "";
            }, 5000);
          });
      } else {
        error.value = "Token no encontrado. Por favor, autentíquese primero.";
        setTimeout(() => {
          error.value = "";
        }, 5000);
      }
    };

    return { createTask, task, error, advice};
  },
};
</script>

<template>
  <body>
    <div class="cards createTask">
      <h2>Crear Tarea</h2>
      <form @submit.prevent="createTask">
        <div class="form-group">
          <label for="title">Título:</label>
          <input
            type="text"
            class="form-control"
            id="title_task"
            v-model="task.title_task"
            required
          />
        </div>
        <div class="form-group">
          <label for="description">Descripción:</label>
          <textarea
            class="form-control"
            id="description_task"
            v-model="task.description_task"
            required
            rows="3"
          ></textarea>
        </div>
        <div class="form-group">
          <label for="dueDate">Fecha de Vencimiento:</label>
          <input
            type="date"
            class="form-control"
            id="expire_time"
            v-model="task.expire_date"
            required
          />
        </div>
        <div class="form-group">
          <label for="Time">Hora de Vencimiento:</label>
          <input
            type="time"
            class="form-control"
            id="dueTime"
            v-model="task.expire_time"
            required
          />
        </div>
        <button type="submit" class="btn btn-primary">Crear Tarea</button>
      </form>
    </div>
    <router-link to="/taskList">Listado de Tareas</router-link>
    <p id="advice">{{ advice }}</p>
  </body>
</template>

<style scoped>
.cards {
  width: 500px;
  border: 1px solid white;
  padding: 10px;
  height: 480px;
  border-radius: 16px;
  transform: translateY(0);
  transition: transform 1.5s ease;
  color:white;
}

#advice {
  color: white;
}

.form-group,
h2,
.btn {
  margin: 10px;
}

body {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  height: 100vh;
  background: none;
}

.regBtn {
  background: none;
  border: none;
  padding: 0;
  font-size: inherit;
  cursor: pointer;
  text-decoration: underline;
  font-family: "Quicksand", sans-serif;
  color: #333;
}

.regBtn:hover {
  color: #003366;
  transition: 0.1 ease;
}

.hide {
  position: fixed;
  transform: translate(0, -300%);
  transition: transform 2s ease;
}

#description_task {
  resize: none;
}

a {
  color: #fff;
  margin: 10px;
}
</style>
