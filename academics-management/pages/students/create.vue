<template>
  <div>
    <b-form :disabled="!isFormValid" @submit.prevent="create">
      <b-form-group
        id="username-input-group"
        description="The username is required"
        label-for="username-input"
        label="Username"
        :state="isUsernameValid"
        :invalid-feedback="invalidUsernameFeedback"
      >
        <b-form-input
          id="username-input"
          v-model.trim="formData.username"
          :state="isUsernameValid"
          type="text"
          required
          trim
          placeholder="Enter username"
        />
      </b-form-group>
      <b-form-group id="password-input-group" label-for="password-input" label="Password">
        <b-form-input
          id="password-input"
          v-model="formData.password"
          :state="isPasswordValid"
          required
          type="password"
        />
      </b-form-group>
      <b-form-group id="name-input-group" label-for="name-input" label="Name:">
        <b-form-input
          id="name-input"
          v-model.trim="formData.name"
          :state="isNameValid"
          required
          type="text"
          placeholder="Enter your Name"
        />
      </b-form-group>
      <b-form-group id="email-input-group" label-for="email-input" label="Email:">
        <b-form-input
          id="email-input"
          ref="email"
          v-model.trim="formData.email"
          :state="isEmailValid"
          required
          pattern=".+@my.ipleiria.pt"
          type="email"
          placeholder="Enter your Email"
        />
      </b-form-group>
      <b-form-group id="course-input-group" label="Course:" label-for="course-input">
        <b-form-select
          id="course-input"
          v-model="formData.courseCode"
          :state="isCourseValid"
          required
          value-field="code"
        >
          <template v-slot:first>
            <option :value="null" disabled>
              --- Please select the Course ---
            </option>
          </template>
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
      <b-button variant="warning" type="reset" @click="reset">
        RESET
      </b-button>
      <b-button variant="success" :disabled="!isFormValid" @click.prevent="create">
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
      namesa: null,
      courses: [],
      errorMessage: false
    }
  },
  computed: {
    invalidUsernameFeedback () {
      if (!this.formData.username) {
        return null
      }
      const usernameLen = this.formData.username.length
      if (usernameLen < 3 || usernameLen > 15) {
        return 'The username must be between [3,15] characters.'
      }
      return ''
    },
    isUsernameValid () {
      if (this.invalidUsernameFeedback === null) {
        return null
      }

      return this.invalidUsernameFeedback === ''
    },
    isPasswordValid () {
      if (!this.formData.password) {
        return null
      }

      const passwordLen = this.formData.password.length
      if (passwordLen < 3 || passwordLen > 255) {
        return false
      }
      return true
    },
    isNameValid () {
      if (!this.formData.name) {
        return null
      }
      const nameLen = this.formData.name.length
      if (nameLen < 3 || nameLen > 25) {
        return false
      }
      return true
    },
    isEmailValid () {
      if (!this.formData.email) {
        return null
      }

      return this.$refs.email.checkValidity()
    },
    isCourseValid () {
      if (!this.formData.courseCode) {
        return null
      }
      return this.courses.some(course => this.formData.courseCode === course.code)
    },
    isFormValid () {
      if (!this.isUsernameValid) {
        return false
      }
      if (!this.isPasswordValid) {
        return false
      }
      if (!this.isNameValid) {
        return false
      }
      if (!this.isEmailValid) {
        return false
      }
      if (!this.isCourseValid) {
        return false
      }
      return true
    }
  },
  created () {
    this.$axios.$get('/api/courses')
      .then((courses) => {
        this.courses = courses
      })
  },
  methods: {
    reset () {
      this.errorMessage = false
    },
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
