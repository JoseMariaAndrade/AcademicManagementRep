<template>
  <div>
    <!-- easy components usage, already shipped with bootstrap css-->
    <b-container>
      <!-- try to remove :fields=”fields” to see the magic -->
      <b-table striped over :items="courses" :fields="fields">
        <template v-slot:cell(actions)="row">
          <nuxt-link
            class="btn btn-link"
            :to="`/courses/${row.item.code}`"
          >
            Details
          </nuxt-link>
          <nuxt-link
            class="btn btn-link"
            :to="`/courses/${row.item.code}`"
          >
            Remove
          </nuxt-link>
        </template>
      </b-table>
    </b-container>
    <nuxt-link to="/courses/create">
      Create a New Course
    </nuxt-link>
  </div>
</template>

<script>
export default {
  data () {
    return {
      fields: ['name', 'actions'],
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
