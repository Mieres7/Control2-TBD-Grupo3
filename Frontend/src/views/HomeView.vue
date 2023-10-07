<template>
  <div class="home"> 
    <login/>
  </div>
</template>

<script setup>
import login from '../components/Login.vue';
</script>

<style scoped>
.home{
  min-height: 100vh;
  width: 100%;
  background-color: grey;
  background-size: cover;
}
</style>