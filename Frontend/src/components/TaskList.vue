<script>
import axios from "axios";
import { useRouter } from "vue-router";
import { ref, onMounted } from "vue";
export default {
  name: "TaskList",
  setup() {
    const d = document;
    // const taskList = ref([
    //   {
    //     id_task: "0",
    //     title_task: "lala",
    //     description_task: "adfjadslkjfkldsjf",
    //     status_task: "Pendiente",
    //     expire_date: "2023-10-08",
    //     expire_time: "18:0:00.100",
    //     id_user: "0",
    //   },
    //   {
    //     title_task: "laaaa",
    //     description_task: "asdfadsfd",
    //     status_task: "Pendiente",
    //     expire_date: "2023-10-08",
    //     expire_time: "18:0:00.100",
    //   },
    //   {},
    // ]);
    const taskList = ref([]);
    const title_task = "",
      description_task = "",
      date_task = "",
      time_task = "";
    const router = useRouter();

    function deleteTask(task_id) {
      axios.defaults.headers.common["Authorization"] =
        "Bearer " + localStorage.getItem("token");

      axios.delete("http://localhost:8080/task/" + task_id);
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
    function updateTask(index) {
      const newTask = {
        id_task: taskList[index].id_task,
        title_task: this.title_task,
        description_task: this.description_task,
        status_task: taskList[index].status_task,
        expire_date: this.date_task,
        expire_time: this.time_task,
        id_user: taskList[index].id_user,
      };

      try {
        axios.put(
          "http://localhost:8080/task/" + taskList[index].id_task,
          newTask
        );
      } catch (error) {
        console.log(error);
      }
    }

    function getTask() {
      const token = localStorage.getItem("token");
      axios.defaults.headers.common["Authorization"] =
        "Bearer " + localStorage.getItem("token");
      if (token) {
        const decodedToken = jwtDecode(token);
        task.value.id_user = decodedToken.id;
        taskList = axios.get(
          "http://localhost:8080/tasks/" + task.value.id_user
        );
      }
    }

    function completeTask(index) {
      const updatedTask = {
        id_task: taskList[index].id_task,
        title_task: taskList[index].title_task,
        description_task: taskList[index].description_task,
        status_task: "COMPLETADA",
        expire_date: taskList[index].expire_date,
        expire_time: taskList[index].expire_time,
        id_user: taskList[index].id_user,
      };

      try {
        axios.put(
          "http://localhost:8080/task/" + taskList[index].id_task,
          updatedTask
        );
      } catch (error) {
        console.log(error);
      }
    }

    // function changeView() {
    //   router.push("/task");
    // }

    onMounted(() => {
      getTask();
    });

    return {
      taskList,
      deleteTask,
      modifier,
      close,
      updateTask,
      getTask,
      completeTask,
      title_task,
      description_task,
      date_task,
      time_task,
    };
  },
};
</script>

<template>
  <div class="task-container">
    <div class="filters"></div>
    <div class="tasks">
      <div class="task" v-for="(t, index) in taskList" :key="index">
        <div class="info">
          <p>Nombre: {{ t.title_task }}</p>
        </div>
        <div>
          <p>Estado: {{ t.status_task }}</p>
        </div>
        <div>
          <button @click="deleteTask">Eliminar</button>
          <button @click="modifier">Editar</button>
          <button @click="completeTask(index)">Completar</button>
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
            <button @click="updateTask(index)">Confirmar</button>
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
}

.filters {
  grid-area: filters;
  background-color: aliceblue;
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
