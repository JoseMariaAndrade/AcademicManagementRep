<template>
  <b-container>
    <h4>Student Details:</h4>
    <p>Username: {{ student.username }}</p>
    <p>Name: {{ student.name }}</p>
    <p>Email: {{ student.email }}</p>
    <p>Course: {{ student.courseName }}</p>
    <h4>Subjects enrolled:</h4>
    <b-table
      v-if="subjects.length"
      striped
      over
      :items="subjects"
      :fields="subjectFields"
    >
      <template v-slot:cell(actions)="row">
        <b-button @click.prevent="unroll(row.item.code)">
          Unroll
        </b-button>
        <!--nuxt-link
          class="btn btn-link"
          :to="`/students/${student.username}/${row.item.code}`"
        >
          Unroll
        </nuxt-link-->
      </template>
    </b-table>
    <p v-else>
      No subjects enrolled.
    </p>
    <nuxt-link to="/students">
      Go Back
    </nuxt-link>
    &nbsp;
    <nuxt-link :to="`/students/${username}/send-email`">
      Send e-email
    </nuxt-link>
  </b-container>
</template>
<script>
export default {
  data () {
    return {
      student: {},
      subjectFields: ['code', 'name', 'courseCode', 'courseYear', 'schoolYear', 'actions']
    }
  },
  computed: {
    username () {
      return this.$route.params.username
    },
    subjects () {
      return this.student.subjects || []
    }
  },
  created () {
    this.$axios.$get(`/api/students/${this.username}`)
      .then((student) => {
        this.student = student || {}
      })
      /*
      .then(() => this.$axios.$get(`/api/students/${this.username}/subjects`))
      .then((subjects) => {
        this.subjects = subjects
      })
      */
  },
  methods: {
    unroll (subject) {
      this.$axios.$post(`/api/students/${this.username}/${subject}`)
    }
  }
}
</script>

<style scoped>

</style>
