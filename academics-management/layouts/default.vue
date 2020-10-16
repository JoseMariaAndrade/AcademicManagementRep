<template>
  <div>
    <div>
      <b-navbar toggleable="lg" type="dark" variant="dark">
        <b-navbar-brand href="/">
          Academics Management
        </b-navbar-brand>

        <b-navbar-toggle target="nav-collapse" />

        <b-collapse id="nav-collapse" is-nav>
          <b-navbar-nav>
            <b-nav-item href="/students">
              Students
            </b-nav-item>
            <b-nav-item href="/courses">
              Courses
            </b-nav-item>
            <b-nav-item href="/teachers">
              Teachers
            </b-nav-item>
            <b-nav-item href="/subjects">
              Subjects
            </b-nav-item>
          </b-navbar-nav>
        </b-collapse>
      </b-navbar>
    </div>
    <Nuxt />
  </div>
</template>
<style>

</style>
