package entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "COURSES", uniqueConstraints = @UniqueConstraint(columnNames = {"NAME"}))
@NamedQueries({
        @NamedQuery(name = "getAllCourses",query = "SELECT c FROM Course c ORDER BY c.name" // JPQL)
        )})
public class Course implements Serializable {

    @Id
    private int code;
    @NotNull
    private String name;
    @OneToMany(mappedBy = "course", cascade = CascadeType.REMOVE)
    private List<Student> students;
    @OneToMany(mappedBy = "course", cascade = CascadeType.REMOVE)
    private List<Subject> subjects;
    @Version
    private int version;

    public Course() {
        students = new ArrayList<>();
        subjects = new ArrayList<>();
    }

    public Course(int code, @NotNull String name) {
        this.code = code;
        this.name = name;
        students = new ArrayList<>();
        subjects = new ArrayList<>();
    }

    public void addStudent(Student student){
        students.add(student);
    }

    public void removeStudent(Student student){
        students.remove(student);
    }

    public void addSubject(Subject subject){
        subjects.add(subject);
    }

    public void removeSubject(Subject subject){
        subjects.remove(subject);
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

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
