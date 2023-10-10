<template>
  <div class="filters">
    <div class="mb-3 col-md-9">
      <input
        v-model="keyword"
        class="form-control"
        placeholder="Palabra clave"
      />
    </div>

    <select v-model="statusFilter" class="form-select mb-3">
      <option value="todos">Todos</option>
      <option value="pendiente">Pendiente</option>
      <option value="completada">Completada</option>
    </select>

    <button @click="filterTask" class="btn btn-primary mb-3">Buscar</button>
  </div>
</template>

<script>
import axios from "axios";
import { ref } from "vue";
import jwtDecode from "jwt-decode";

export default {
  name: "TaskFilter",
  emits: ["filterTaskSignal"],
  setup(_, { emit }) {
    const tasks = ref([]);
    const keyword = ref("all");
    const statusFilter = ref("todos");

    const error = ref("");

    const filterTask = () => {
      emit("filterTaskSignal", keyword.value, statusFilter.value);
    };

    return {
      filterTask,
      tasks,
      keyword,
      statusFilter,

      error,
    };
  },
};
</script>

<style scoped>
.filters {
  display: flex;

  margin: 10px;
}

.mb-3 {
  margin-right: 10px;
}
</style>
