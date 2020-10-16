<template>
  <div>
    <b-form @submit.prevent="create">
      <b-form-group id="username-input-group" label-for="username-input" label="Username">
        <b-form-input id="username-input" v-model="username" type="text" placeholder="Enter username" />
      </b-form-group>
      <b-form-group id="password-input-group" label-for="password-input" label="Password">
        <b-form-input id="password-input" v-model="password" type="password" />
      </b-form-group>
      <b-form-group id="name-input-group" label-for="name-input" label="Name:">
        <b-form-input id="name-input" v-model="name" type="text" placeholder="Enter your Name" />
      </b-form-group>
      <b-form-group id="email-input-group" label-for="email-input" label="Email:">
        <b-form-input id="email-input" v-model="email" type="email" placeholder="Enter your Email" />
      </b-form-group>
      <b-form-group id="office-input-group" label-for="office-input" label="Office:">
        <b-form-input id="office-input" v-model="office" type="text" placeholder="Enter your Office" />
      </b-form-group>
      <nuxt-link to="/teachers">
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
      username: null,
      password: null,
      name: null,
      email: null,
      office: null
    }
  },
  methods: {
    create () {
      this.$axios.$post('/api/teachers', {
        username: this.username,
        password: this.password,
        name: this.name,
        email: this.email,
        office: this.office
      }).then(() => {
        this.$router.push('/teachers')
      })
    }
  }
}
</script>

<style scoped>

</style>
