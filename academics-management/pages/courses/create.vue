<template>
  <div>
    <b-form @submit.prevent="create">
      <b-form-group id="code-input-group" label-for="code-input" label="Code:">
        <b-form-input id="code-input" v-model="code" type="number" />
      </b-form-group>
      <b-form-group id="name-input-group" label-for="name-input" label="Name:">
        <b-form-input id="name-input" v-model="name" type="text" placeholder="Enter course name" />
      </b-form-group>
      <nuxt-link to="/courses">
        Return
      </nuxt-link>
      <b-button variant="warning" type="reset">
        RESET
      </b-button>
      <b-button variant="success" @click.prevent="create">
        CREATE
      </b-button>
    </b-form>
  </div>
</template>

<script>
export default {
  data () {
    return {
      code: null,
      name: null,
      students: []
    }
  },
  methods: {
    create () {
      this.$axios.$post('/api/courses', {
        code: this.code,
        name: this.name,
        students: null
      }).then(() => {
        this.$router.push('/courses')
      })
    }
  }
}
</script>

<style scoped>

</style>
