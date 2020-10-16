package entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(name = "getAllStudents", query = "SELECT s FROM Student s ORDER BY s.name") //JPQL
})
public class Student extends User implements Serializable{

    @ManyToOne
    @JoinColumn(name = "COURSE_CODE")
    @NotNull
    private Course course;
    @ManyToMany(mappedBy = "students")
    private Set<Subject> subjects;

    public Student() {
        super();
        subjects = new LinkedHashSet<>();
    }

    public Student(String username, @NotNull String password, @NotNull String name, @NotNull @Email String email, @NotNull Course course) {
        super(username, password, name, email);
        this.course = course;
        subjects = new LinkedHashSet<>();
    }

    public void addSubject(Subject subject){
        subjects.add(subject);
    }

    public void removeSubject(Subject subject){
        subjects.remove(subject);
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }
}
