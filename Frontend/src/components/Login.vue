<script>
import { defineComponent } from "vue";
export default defineComponent({
  name: "Login",
});
</script>

<template>
  <body>
    <div class="form-card">
      <div class="titles">aaaaaaaaaa</div>
      <div class="form">bbbbbbbbbbbbb</div>
      <div class="bot">ccccccccccccccc</div>
    </div>
  </body>
</template>

<style scoped>
body {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  height: 100vh;
}

/* Login component */

.form-card {
  display: grid;
  grid-template-areas: "title", "form", "bot";
  grid-template-columns: 100vh;
  grid-template-rows: 20% 20% 60%;

  height: 540px;
  width: 400px;
  border: solid red;
  border-radius: 20px;
  /* background-color: var(--white-bg); */
}

.titles {
  grid-area: title;
  background-color: red;
}

.form {
  grid-area: form;
}

.bot {
  grid-area: bot;
}
</style>
