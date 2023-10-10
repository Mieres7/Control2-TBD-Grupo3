<script setup>
import TaskList from "../components/TaskList.vue";
</script>

<template>
  <div class="main">
    <TaskList />
  </div>
</template>

<style scoped>
.main {
  width: 100%;
  height: 100vh;
  background-color: grey;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
}
</style>
