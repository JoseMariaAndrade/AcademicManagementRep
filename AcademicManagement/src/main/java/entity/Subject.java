package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "getAllSubjects", query = "SELECT s FROM Subject s ORDER BY s.course.name, s.scholarYear DESC, s.courseYear, s.name")
})
@Table(name = "SUBJECTS", uniqueConstraints = @UniqueConstraint(columnNames = {"NAME", "COURSE", "SCHOLAR_YEAR"}))
public class Subject implements Serializable {

    @Id
    private int code;
    private String name;
    @ManyToOne
    @JoinColumn(name = "COURSE_CODE")
    private Course course;
    @Column(name = "COURSE_YEAR")
    private String courseYear;
    @Column(name = "SCHOLAR_YEAR")
    private String scholarYear;
    @ManyToMany
    @JoinTable(name = "SUBJECTS_STUDENTS",
    joinColumns = @JoinColumn(name = "SUBJECT_CODE", referencedColumnName = "CODE"),
    inverseJoinColumns = @JoinColumn(name = "STUDENT_USERNAME", referencedColumnName = "USERNAME"))
    private LinkedHashSet<Student> students;

    public Subject() {
        this.students = new LinkedHashSet();
    }

    public Subject(int code, String name, Course course, String courseYear, String scholarYear) {
        this.code = code;
        this.name = name;
        this.course = course;
        this.courseYear = courseYear;
        this.scholarYear = scholarYear;
        this.students = new LinkedHashSet();
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

    public String getCourseYear() {
        return courseYear;
    }

    public void setCourseYear(String courseYear) {
        this.courseYear = courseYear;
    }

    public String getScholarYear() {
        return scholarYear;
    }

    public void setScholarYear(String scholarYear) {
        this.scholarYear = scholarYear;
    }

    public LinkedHashSet<Student> getStudents() {
        return students;
    }

    public void setStudents(LinkedHashSet<Student> students) {
        this.students = students;
    }

    public void addStudent(Student student){
        students.add(student);
    }

    public void removeStudent(Student student){
        students.remove(student);
    }
}
