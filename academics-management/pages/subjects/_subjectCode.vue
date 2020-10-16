<template>
  <b-container>
    <h4>Subject Details:</h4>
    <p>Name: {{ subject.name }}</p>
    <p>School Year: {{ subject.schoolYear }}</p>
    <p>Course Year: {{ subject.courseYear }}</p>
    <p>Course Name: {{ subject.courseName }}</p>

    <h4>Teachers associated:</h4>
    <b-table
      v-if="teachers.length"
      striped
      over
      :items="teachers"
      :fields="teacherFields"
    />
    <p v-else>
      No teachers associated.
    </p>
    <h4>Students enrolled:</h4>
    <b-table
      v-if="students.length"
      striped
      over
      :items="students"
      :fields="studentFields"
    />
    <p v-else>
      No students enrolled.
    </p>
    <nuxt-link to="/subjects">
      Back
    </nuxt-link>
  </b-container>
</template>

<script>
export default {
  data () {
    return {
      subject: {},
      teachers: [],
      teacherFields: ['name', 'email', 'office'],
      students: [],
      studentFields: ['name', 'courseName']

    }
  },
  computed: {
    subjectCode () {
      return this.$route.params.subjectCode
    }
  },
  created () {
    this.$axios.$get(`/api/subjects/${this.subjectCode}`)
      .then((subject) => {
        this.subject = subject || {}
      })
      .then(() => this.$axios.$get(`/api/subjects/${this.subjectCode}/teachers`))
      .then((teachers) => {
        this.teachers = teachers
      })
      .then(() => this.$axios.$get(`/api/subjects/${this.subjectCode}/students`))
      .then((students) => {
        this.students = students
      })
  }
}
</script>

<style scoped>

</style>
