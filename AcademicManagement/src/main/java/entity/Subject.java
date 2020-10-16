package entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(name = "getAllSubjects", query = "SELECT s FROM Subject s ORDER BY s.course.name, s.schoolarYear DESC, s.courseYear, s.name")
})
@Table(name = "SUBJECTS", uniqueConstraints = @UniqueConstraint(columnNames = {"NAME", "COURSE", "SCHOLAR_YEAR"}))
public class Subject implements Serializable {

    @Id
    private int code;
    private String name;
    @ManyToOne
    @JoinColumn(name = "COURSE_CODE")
    @NotNull
    private Course course;
    @Column(name = "COURSE_YEAR")
    private int courseYear;
    @Column(name = "SCHOLAR_YEAR")
    private int schoolarYear;
    @ManyToMany
    @JoinTable(name = "SUBJECTS_STUDENTS",
            joinColumns = @JoinColumn(name = "SUBJECT_CODE", referencedColumnName = "CODE"),
            inverseJoinColumns = @JoinColumn(name = "STUDENT_USERNAME", referencedColumnName =
                    "USERNAME"))
    private Set<Student> students;
    @ManyToMany
    @JoinTable(name = "SUBJECTS_TEACHERS",
            joinColumns = @JoinColumn(name = "SUBJECT_CODE", referencedColumnName = "CODE"),
            inverseJoinColumns = @JoinColumn(name = "TEACHERS_USERNAME", referencedColumnName =
                    "USERNAME"))
    private Set<Teacher> teachers;
    @Version
    private int version;

    public Subject() {
        this.students = new HashSet<>();
        this.teachers = new HashSet<>();
    }

    public Subject(int code, String name, Course course, int courseYear, int schoolarYear) {
        this.code = code;
        this.name = name;
        this.course = course;
        this.courseYear = courseYear;
        this.schoolarYear = schoolarYear;
        this.students = new HashSet<>();
        this.teachers = new HashSet<>();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getCourseYear() {
        return courseYear;
    }

    public void setCourseYear(int courseYear) {
        this.courseYear = courseYear;
    }

    public int getSchoolarYear() {
        return schoolarYear;
    }

    public void setSchoolarYear(int schoolarYear) {
        this.schoolarYear = schoolarYear;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }

    public void addStudent(Student student){
        students.add(student);
    }

    public void removeStudent(Student student){
        students.remove(student);
    }

    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
    }

    public void removerTeacher(Teacher teacher){
        teachers.remove(teacher);
    }
}
