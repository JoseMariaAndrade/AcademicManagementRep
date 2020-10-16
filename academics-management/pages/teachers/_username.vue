<template>
  <b-container>
    <h4>Teacher Details:</h4>
    <p>Username: {{ teacher.username }}</p>
    <p>Name: {{ teacher.name }}</p>
    <p>Email: {{ teacher.email }}</p>
    <p>Course: {{ teacher.office }}</p>
    <h4>Subjects enrolled:</h4>
    <b-table
      v-if="subjects.length"
      striped
      over
      :items="subjects"
      :fields="subjectFields"
    />
    <p v-else>
      No subjects enrolled.
    </p>
    <nuxt-link to="/teacher">
      Back
    </nuxt-link>
  </b-container>
</template>
<script>
export default {
  data () {
    return {
      teacher: {},
      subjects: [],
      subjectFields: ['code', 'name', 'courseCode', 'courseYear', 'schoolYear']
    }
  },
  computed: {
    username () {
      return this.$route.params.username
    }
  },
  created () {
    this.$axios.$get(`/api/teachers/${this.username}`)
      .then((teacher) => {
        this.teacher = teacher || {}
      })
      .then(() => this.$axios.$get(`/api/teachers/${this.username}/subjects`))
      .then((subjects) => {
        this.subjects = subjects
      })
  }
}
</script>

<style scoped>

</style>
