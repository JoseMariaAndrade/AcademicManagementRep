<template>
  <div>
    <!-- easy components usage, already shipped with bootstrap css-->
    <b-container>
      <!-- try to remove :fields=”fields” to see the magic -->
      <b-table striped over :items="students" :fields="fields">
        <template v-slot:cell(actions)="row">
          <nuxt-link
            class="btn btn-link"
            :to="`/students/${row.item.username}`"
          >
            Details
          </nuxt-link>
        </template>
      </b-table>
      <nuxt-link to="/">
        Back
      </nuxt-link>
    </b-container>
    <div>
      <nuxt-link to="/students/create">
        Create a New Student
      </nuxt-link>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      fields: ['username', 'name', 'email', 'courseName', 'actions'],
      students: []
    }
  },
  created () {
    this.$axios.$get('/api/students')
      .then((students) => {
        this.students = students
      })
  }
}
</script>
<style>
</style>
