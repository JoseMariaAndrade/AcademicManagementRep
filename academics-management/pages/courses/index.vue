<template>
  <div>
    <!-- easy components usage, already shipped with bootstrap css-->
    <b-container>
      <!-- try to remove :fields=”fields” to see the magic -->
      <b-table striped over :items="courses" :fields="fields" />
    </b-container>
    <nuxt-link to="/create">
      Create a New Course
    </nuxt-link>
  </div>
</template>

<script>
export default {
  data () {
    return {
      fields: ['name'],
      courses: []
    }
  },
  created () {
    this.$axios.$get('/api/courses')
      .then((courses) => {
        this.courses = courses
      })
  }
}
</script>

<style scoped>

</style>
