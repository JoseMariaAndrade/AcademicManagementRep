<template>
  <div>
    <b-form @submit.prevent="create">
      <b-form-group id="code-input-group" label-for="code-input" label="Code:">
        <b-form-input id="code-input" v-model="code" type="number" />
      </b-form-group>
      <b-form-group id="name-input-group" label-for="name-input" label="Name:">
        <b-form-input id="name-input" v-model="name" type="text" placeholder="Enter course name" />
      </b-form-group>
      <b-form-group id="course-year-input-group" label-for="course-year-input" label="Course Year:">
        <b-form-input id="course-year-input" v-model="courseYear" type="number" placeholder="Enter course year" />
      </b-form-group>
      <b-form-group id="school-year-input-group" label-for="school-year-input" label="School Year:">
        <b-form-input id="school-year-input" v-model="schoolYear" type="number" placeholder="Enter school year" />
      </b-form-group>
      <b-form-group id="course-input-group" label="Course:" label-for="course-input">
        <b-form-select
          id="course-input"
          v-model="courseCode"
        >
          <template v-for="course in courses">
            <option :key="course.code" :value="course.code">
              {{ course.name }}
            </option>
          </template>
        </b-form-select>
      </b-form-group>
      <nuxt-link to="/courses">
        Return
      </nuxt-link>
      <b-button variant="warning" type="reset">
        RESET
      </b-button>
      <b-button variant="success" @click.prevent="create">
        CREATE
      </b-button>
    </b-form>
  </div>
</template>

<script>
export default {
  data () {
    return {
      code: null,
      name: null,
      courseCode: null,
      courseYear: null,
      schoolYear: null,
      students: [],
      teachers: [],
      courses: []
    }
  },
  created () {
    this.$axios.$get('/api/courses')
      .then((courses) => {
        this.courses = courses
      })
  },
  methods: {
    create () {
      this.$axios.$post('/api/subjects', {
        code: this.code,
        name: this.name,
        courseCode: this.courseCode,
        courseYear: this.courseYear,
        schoolYear: this.schoolYear
      }).then(() => {
        this.$router.push('/subjects')
      })
    }
  }
}
</script>

<style scoped>

</style>
