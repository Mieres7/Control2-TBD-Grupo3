<template>
  <div class="container mt-5">
    <h2>Registro</h2>
    <form @submit.prevent="register">
      <div class="form-group">
        <label for="newUsername">Nuevo Usuario:</label>
        <input type="text" class="form-control" id="newUsername" v-model="newUsername" required>
      </div>
      <div class="form-group">
        <label for="newPassword">Nueva Contraseña:</label>
        <input type="password" class="form-control" id="newPassword" v-model="newPassword" required>
      </div>
      <button type="submit" class="btn btn-primary">Registrarse</button>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      newUsername: '',
      newPassword: ''
    };
  },
  methods: {
    register() {
      // Realizar una solicitud POST al servidor para autenticar al usuario
      axios.post('http://localhost:8080/auth/register', credentials)
        .then(response => {
          // Manejar la respuesta del servidor aquí
          console.log('Respuesta del servidor:', response.data);
          
          // Lógica adicional, como redireccionar a una página de inicio de sesión exitosa.
          localStorage.setItem('token', response.data.token);
          
          axios.defaults.headers.common['Authorization'] = 'Bearer ' + localStorage.getItem('token');

          this.$router.push('/about');
        })
        .catch(error => {
          // Manejar los errores aquí, como mostrar un mensaje de error al usuario.
          console.error('Error al registrarse:', error);
        })
    }
  }
};
</script>