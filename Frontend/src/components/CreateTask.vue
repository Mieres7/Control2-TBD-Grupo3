<template>
  <div class="container">
    <h2 class="my-3">Crear tarea mágica</h2>
    <form @submit.prevent="crearTareaMágica">
      <div class="form-group">
        <label>Titulo:</label>
        <input class="form-control" v-model="tarea.titulo" />
      </div>
      
      <div class="form-group">
        <label>Descripción:</label>
        <textarea class="form-control" v-model="tarea.descripcion"></textarea>
      </div>
      
      <div class="form-group">
        <label>Fecha de Vencimiento:</label>
        <input type="date" class="form-control" v-model="tarea.fechaVencimiento" />
      </div>
      
      <div class="form-group">
        <label>Hora de Vencimiento:</label>
        <input type="time" class="form-control" v-model="tarea.horaVencimiento" />
      </div>

      <button type="submit" class="btn btn-primary">Invocar tarea</button>
    </form>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  data() {
    return {
      tarea: {
        title_task: '',
        descripcion: '',
        fechaVencimiento: '',
        horaVencimiento: '',
        estado: 'pendiente',
        idUsuario: 'idDelUsuario'
      }
    }
  },
  methods: {
    async crearTareaMágica() {
      try {
        const respuesta = await axios.post('http://localhost:8080/task', this.tarea)
        console.log('Tarea mágicamente creada:', respuesta.data)
      } catch (error) {
        console.error('El hechizo falló:', error)
      }
    }
  }
}
</script>