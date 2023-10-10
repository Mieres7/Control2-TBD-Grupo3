<script>
import axios from "axios";
import { useRouter } from "vue-router";
import { ref, onMounted } from "vue";
import jwtDecode from "jwt-decode";
import TaskFilter from "./TaskFilter.vue";
export default {
  name: "TaskList",
  components: { TaskFilter },
  setup() {
    const d = document;
    const taskList = ref([]);
    const title_task = ref(""),
      description_task = ref(""),
      date_task = ref(""),
      time_task = ref(""),
      id_user = ref(""),
      id_task = ref(""),
      status_task = ref("");

    function deleteTask(task_id) {
      const token = localStorage.getItem("token");
      axios.defaults.headers.common["Authorization"] =
        "Bearer " + localStorage.getItem("token");
      if (token) {
        const decodedToken = jwtDecode(token);
        id_user.value = decodedToken.id;

        axios
          .delete("http://localhost:8080/task/" + task_id)
          .then((response) => {
            taskList.value = response.data;
            getTask();
            console.log("Respuesta del servidor:", response.data);
          })
          .catch((e) => {
            error.value = "Error al crear la tarea: " + e.message;
            setTimeout(() => {
              error.value = "";
            }, 5000);
          });
      }
    }

    function modifier() {
      const $modifier = d.querySelector(".modifier"),
        $overlay = d.querySelector(".overlay");

      $overlay.style.zIndex = "3";
      $modifier.style.opacity = "1";
    }

    function close() {
      const $modifier = d.querySelector(".modifier"),
        $overlay = d.querySelector(".overlay");

      $overlay.style.zIndex = "-1";
      $modifier.style.opacity = "0";
    }

    function updateTask(id_task, index) {
      const status = taskList.value[index].status_task;
      const token = localStorage.getItem("token");
      axios.defaults.headers.common["Authorization"] =
        "Bearer " + localStorage.getItem("token");
      if (token) {
        const decodedToken = jwtDecode(token);
        id_user.value = decodedToken.id;

        const formattedTime = `${time_task.value}:00`;

        const newTask = {
          title_task: title_task.value,
          description_task: description_task.value,
          expire_date: date_task.value,
          expire_time: formattedTime,
          status_task: status,
          id_user: id_user.value,
        };

        axios
          .put("http://localhost:8080/task/" + id_task, newTask)
          .then((response) => {
            getTask();
            console.log("Respuesta del servidor:", response.data);
          })
          .catch((e) => {
            error.value = "Error al modificar la tarea: " + e.message;
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
    }

    function getTask() {
      const token = localStorage.getItem("token");
      axios.defaults.headers.common["Authorization"] =
        "Bearer " + localStorage.getItem("token");
      if (token) {
        const decodedToken = jwtDecode(token);
        id_user.value = decodedToken.id;

        axios
          .get("http://localhost:8080/tasks/" + id_user.value)
          .then((response) => {
            taskList.value = response.data;
          })
          .catch((e) => {
            error.value = "Error al crear la tarea: " + e.message;
            setTimeout(() => {
              error.value = "";
            }, 5000);
          });
      }
    }

    function completeTask(id_task, index) {
      const token = localStorage.getItem("token");
      axios.defaults.headers.common["Authorization"] =
        "Bearer " + localStorage.getItem("token");

      if (token) {
        const newTask = taskList.value[index];
        const status = "COMPLETADA";

        console.log(newTask);

        newTask.status_task = status;

        axios
          .put("http://localhost:8080/task/" + id_task, newTask)
          .then((response) => {
            getTask();
          })
          .catch((e) => {
            error.value = "Error al modificar la tarea: " + e.message;
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
    }

    const getTaskFiltered = async (keyword, statusFilter) => {
      const token = localStorage.getItem("token");
      axios.defaults.headers.common["Authorization"] = "Bearer " + token;
      if (token) {
        try {
          const decodedToken = jwtDecode(token);
          const userId = decodedToken.id;
          const response = await axios.get(
            "http://localhost:8080/tasks/" +
              userId +
              "/key/" +
              keyword +
              "/status/" +
              statusFilter.toUpperCase()
          );
          taskList.value = response.data;
        } catch (e) {
          console.log(e);
          error.value = "Error al buscar tareas: " + e.message;
          setTimeout(() => {
            error.value = "";
          }, 5000);
        }
      } else {
        error.value = "Token no encontrado. Por favor, autentíquese primero.";
        setTimeout(() => {
          error.value = "";
        }, 5000);
      }
    };

    onMounted(() => {
      getTask();
    });

    return {
      deleteTask,
      modifier,
      close,
      updateTask,
      getTask,
      completeTask,
      getTaskFiltered,
      taskList,
      title_task,
      description_task,
      date_task,
      time_task,
      status_task,
      id_task,
    };
  },
};
</script>

<template>
  <div class="task-container">
    <div class="filters">
      <TaskFilter @filterTaskSignal="getTaskFiltered" />
    </div>
    <div class="tasks">
      <div class="task" v-for="(t, index) in taskList" :key="index">
        <div class="info">
          <p>Nombre: {{ t.title_task }}</p>
        </div>
        <div>
          <p>Estado: {{ t.status_task }}</p>
        </div>
        <div>
          <button @click="deleteTask(t.id_task)">Eliminar</button>
          <button @click="modifier">Editar</button>
          <button @click="completeTask(t.id_task, index)">Completar</button>
        </div>
        <div class="overlay" @click="close">
          <div class="modifier" @click.stop>
            <h2>Editar tarea</h2>
            <p>Titulo</p>
            <input type="text" v-model="title_task" />
            <p>Descripción</p>
            <textarea cols="40" rows="5" v-model="description_task"></textarea>
            <p>Fecha de vencimiento</p>
            <input type="date" v-model="date_task" />
            <p>Hora de vencimiento</p>
            <input type="time" v-model="time_task" />
            <button @click="updateTask(t.id_task, index)">Confirmar</button>
          </div>
        </div>
      </div>
    </div>
  </div>

  <router-link to="/task">Crear Tarea</router-link>
</template>

<style scoped>
.task-container {
  width: 80%;
  height: 80%;
  border: 1px solid #fff;
  display: grid;
  grid-template-columns: 100%;
  grid-template-rows: 10% 90%;
  grid-template-areas: "filters" "tasks";
  border-radius: 16px;
}

.tasks {
  grid-area: tasks;
  overflow: hidden;
  overflow-y: auto;
}

.filters {
  grid-area: filters;

  justify-content: center;
  align-items: center;
}

.task {
  display: flex;
  flex-direction: row;
  align-items: start;
  text-align: center;
  justify-content: space-between;
  font-size: 1.2rem;

  margin: 10px;
  padding: 10px;
  border-radius: 10px;
  background-color: #cecece;
}

.info {
  display: flex;
  flex-direction: row;
}

.overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.3);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: -1;
  transition: zIndex 0.3s ease;
}

.modifier {
  position: absolute;
  display: flex;
  flex-direction: column;
  display: flex;
  padding: 25px;
  font-size: 20px;
  font-weight: 500;
  background-color: #fff;
  font-family: "Quicksand", sans-serif;
  border-radius: 10px;
  z-index: 4;
  opacity: 0;
  transition: opacity 0.3s ease;
  text-align: start;
}

.modifier > button {
  margin: 10px 0px 10px 0px;
}

.modifier > button:hover {
  background-color: #000000;
  color: #ffffff;
}

a {
  margin: 5px;
  color: #fff;
}

button {
  background-color: #ffffff;
  color: #000000;
  border: 1px solid #000000;
  margin: 10px 0px 10px 10px;
  padding: 5px 10px;
  font-family: "Quicksand", sans-serif;
  font-size: 16px;
  border-radius: 5px;
  outline: none;
}

p {
  margin: 10px;
}
</style>
