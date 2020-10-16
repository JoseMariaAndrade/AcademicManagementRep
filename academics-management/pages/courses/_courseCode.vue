<template>
  <b-container>
    <h4>Course Details:</h4>
    <p>Name: {{ course.name }}</p>
    <nuxt-link to="/courses">
      Back
    </nuxt-link>
  </b-container>
</template>

<script>
export default {
  data () {
    return {
      course: {}
    }
  },
  computed: {
    courseCode () {
      return this.$route.params.courseCode
    }
  },
  created () {
    this.$axios.$get(`/api/courses/${this.courseCode}`)
      .then((course) => {
        this.course = course || {}
      })
  }
}
</script>

<style scoped>

</style>
