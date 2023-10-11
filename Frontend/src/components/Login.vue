<template>
  <body>
    <div class="cars login">
      <h2>Iniciar Sesión</h2>
      <form @submit.prevent="login">
        <div class="form-group">
          <label for="username">Usuario:</label>
          <input
            type="text"
            class="form-control"
            v-model="username"
            required
          />
        </div>
        <div class="form-group">
          <label for="password">Contraseña:</label>
          <input
            type="password"
            class="form-control"
            v-model="password"
            required
          />
        </div>
        <button type="submit" class="btn btn-primary">Iniciar Sesión</button>
        <button class="regBtn" id="btnLogin" @click.prevent="change">
          Regístrate
        </button>
        <div v-if="error" class="alert alert-danger">
          {{ error }}
        </div>
      </form>
    </div>

    <div class="cars hide register">
      <h2>Regístrate</h2>
      <form @submit.prevent="register">
        <div class="form-group">
          <label for="username">Usuario:</label>
          <input
            type="text"
            class="form-control"
            v-model="username"
            required
          />
        </div>
        <div class="form-group">
          <label for="password">Contraseña:</label>
          <input
            type="password"
            class="form-control"
            v-model="password"
            required
          />
        </div>
        <button type="submit" class="btn btn-primary">Regístrate</button>
        <button class="regBtn" id="btnRegister" @click.prevent="change">
          Inicia sesión
        </button>
        <div v-if="error" class="alert alert-danger">
          {{ error }}
        </div>
      </form>
    </div>
  </body>
</template>

<script>
import axios from 'axios';
import {ref} from 'vue';
import { useRouter } from 'vue-router';

export default {
  name: "login",
  setup() {
    const username=ref(""),
    password=ref(""),
    error=ref("");
    const router = useRouter();

    const login=() => {
      const credentials = {
        username: username.value,
        password: password.value
      };

      // Solicitud POST al servidor para autenticar al usuario
      axios.post('http://localhost:8080/auth/login', credentials)
        .then(response => {
          localStorage.setItem('token', response.data.token);
          
          axios.defaults.headers.common['Authorization'] = 'Bearer ' + localStorage.getItem('token');

          router.push('/task');
        })
        .catch(e => {
          error.value = 'Usuario o contraseña incorrectos.';

          setTimeout(() => {
            error.value = ""
          }, 5000);        
        });
    }

    const register=() => {
      const credentials = {
        username: username.value,
        password: password.value
      };
      // Solicitud POST al servidor para autenticar al usuario
      axios.post('http://localhost:8080/auth/register', credentials)
        .then(response => {
          localStorage.setItem('token', response.data.token);
          
          axios.defaults.headers.common['Authorization'] = 'Bearer ' + localStorage.getItem('token');

          router.push('/task');
        })
        .catch(e => {
          error.value = 'Usuario existente.';

          setTimeout(() => {
            error.value = ""
          }, 5000);
        })
    }

    const change = () => {
      const d = document;

      const $login = d.querySelector(".login"),
        $register = d.querySelector(".register");
      const $btnLogin = d.getElementById("btnLogin"),
        $btnRegister = d.getElementById("btnRegister");

      $btnRegister.addEventListener("click", (e) => {
        setTimeout(() => {
          $login.classList.remove("hide");
        }, 200);

        $register.classList.add("hide");
      });

      $btnLogin.addEventListener("click", (e) => {
        $login.classList.add("hide");

        setTimeout(() => {
          $register.classList.remove("hide");
        }, 200);
      });
    };

    return {login, register, change, username, password, error}
  }
};
</script>

<style scoped>
.cars {
  width: 300px;
  border: 1px solid white;
  padding: 10px;
  height: 350px;
  border-radius: 16px;
  transform: translateY(0);
  transition: transform 1.5s ease;
  color:white;
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
  color: white;
}

.regBtn:hover {
  color: #9cd2d3;
  transition: 0.1 ease;
}

.hide {
  position: fixed;
  transform: translate(0, -300%);
  transition: transform 2s ease;
}
</style>
