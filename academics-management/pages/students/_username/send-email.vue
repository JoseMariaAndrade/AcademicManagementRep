<template>
  <div>
    <h1>Send an E-mail to Student {{ student.name }}</h1>
    <b-form @submit.prevent="send">
      <b-form-group id="subject-input-group" label-for="subject-input" label="Subject:">
        <b-form-input id="subject-input" v-model="subject" type="text" />
      </b-form-group>
      <b-form-group id="message-input-group" label-for="message-input" label="Message:">
        <b-form-textarea
          id="textarea"
          v-model="message"
          placeholder="Enter something..."
          rows="3"
          max-rows="6"
        />
      </b-form-group>
    </b-form>
    <nuxt-link :to="`/students`">
      Go to Students
    </nuxt-link>
    <nuxt-link :to="`/students/${username}`">
      Go to Students Details
    </nuxt-link>
    <b-button @click.prevent="send">
      Send
    </b-button>
  </div>
</template>

<script>
export default {
  data () {
    return {
      student: {},
      subject: null,
      message: null
    }
  },
  computed: {
    username () {
      return this.$route.params.username
    }
  },
  created () {
    this.$axios.get(`/api/students/${this.username}`)
      .then((student) => { this.student = student })
  },
  methods: {
    send () {
      this.$axios.$post(`/api/students/${this.username}/email/send`, {
        subject: this.subject,
        message: this.message
      })
        .then((message) => { this.$toast.success(message) })
        // eslint-disable-next-line handle-callback-err
        .catch((error) => {
          this.$toast.error('error sendind the e-amil')
        })
    }
  }
}
</script>

<style scoped>

</style>
