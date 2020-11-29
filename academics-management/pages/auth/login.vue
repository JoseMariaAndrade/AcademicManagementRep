<template>
  <b-container>
    <h3>Login into Academics Management</h3>
    <b-form @submit.prevent="onSubmit" @reset="onReset">
      <b-form-group label="Username" description="Enter your username">
        <b-input v-model.trim="username" name="username" placeholder="Your Username" required />
      </b-form-group>
      <b-form-group label="Password" description="Enter your password">
        <b-input
          v-model="password"
          name="password"
          type="password"
          required
        />
      </b-form-group>
      <b-button type="reset" class="btn-warning">
        Reset
      </b-button>
      <b-button type="submit" class="btn-success">
        Submit
      </b-button>
    </b-form>
  </b-container>
</template>

<script>
export default {
  auth: false,
  data () {
    return {
      username: null,
      password: null
    }
  },
  methods: {
    onSubmit () {
      const promise = this.$auth.loginWith('local', {
        data: {
          username: this.username,
          password: this.password
        }
      })
      promise.then(() => {
        this.$toast.success('You are logged in!')
        // check if the user $auth.user object is set
        console.log(this.$auth.user)
      })
      promise.catch(() => {
        this.$toast.error('Sorry, you can\'t login, Ensure your credentials are correct')
      })

      // TODO redirect based on the user role
      // eg:
      // if (this.$auth.user.groups.includes('Teacher')) {
      // this.$router.push('url-to-teacher-subjects')
      // } else if (...) {
      // ...
      // }
    },
    onReset () {
      this.username = null
      this.password = null
    }
  }
}
</script>

<style scoped>

</style>
