<template>
  <div>
    <!-- easy components usage, already shipped with bootstrap css-->
    <b-container>
      <!-- try to remove :fields=”fields” to see the magic -->
      <b-table striped over :items="teachers" :fields="fields">
        <template v-slot:cell(actions)="row">
          <nuxt-link
            class="btn btn-link"
            :to="`/teachers/${row.item.username}`"
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
      <nuxt-link to="/teachers/create">
        Create a New Teacher
      </nuxt-link>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      fields: ['username', 'name', 'email', 'office', 'actions'],
      teachers: []
    }
  },
  created () {
    this.$axios.$get('/api/teachers')
      .then((teachers) => {
        this.teachers = teachers
      })
  }
}
</script>
<style>
</style>
