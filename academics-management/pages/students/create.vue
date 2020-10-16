<template>
  <div>
    <b-form @submit.prevent="create">
      <b-form-group id="username-input-group" label-for="username-input" label="Username">
        <b-form-input id="username-input" v-model="formData.username" type="text" placeholder="Enter username" />
      </b-form-group>
      <b-form-group id="password-input-group" label-for="password-input" label="Password">
        <b-form-input id="password-input" v-model="formData.password" type="password" />
      </b-form-group>
      <b-form-group id="name-input-group" label-for="name-input" label="Name:">
        <b-form-input id="name-input" v-model="formData.name" type="text" placeholder="Enter your Name" />
      </b-form-group>
      <b-form-group id="email-input-group" label-for="email-input" label="Email:">
        <b-form-input id="email-input" v-model="formData.email" type="email" placeholder="Enter your Email" />
      </b-form-group>
      <b-form-group id="course-input-group" label="Course:" label-for="course-input">
        <b-form-select
          id="course-input"
          v-model="formData.courseCode"
        >
          <template v-for="course in courses">
            <option :key="course.code" :value="course.code">
              {{ course.name }}
            </option>
          </template>
        </b-form-select>
      </b-form-group>
      <p v-show="errorMessage" class="text-danger">
        {{ errorMessage }}
      </p>
      <nuxt-link to="/students">
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
      formData: {
        username: null,
        password: null,
        name: null,
        email: null,
        courseCode: null
      },
      courses: [],
      errorMessage: false
    }
  },
  created () {
    this.$axios.$get('/api/courses')
      .then((courses) => {
        this.courses = courses
      })
  },
  methods: {
    create () {
      this.$axios.$post('/api/students', this.formData)
        .then(() => {
          this.$router.push('/students')
        })
        .catch((error) => {
          this.errorMessage = error.response.data
        })
    }
  }
}
</script>

<style scoped>

</style>
