package entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "getAllStudents", query = "SELECT s FROM Student s ORDER BY s.name") //JPQL
})
@Table(name = "STUDENTS")
public class Student  implements Serializable {

    @Id
    private String username;
    @NotNull
    private String name;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String password;
    @ManyToOne
    @JoinColumn(name = "COURSE_CODE")
    @NotNull
    private Course course;
    @ManyToMany(mappedBy = "students")
    private List<Subject> subjects;

    public Student() {
        subjects = new ArrayList<>();
    }

    public Student(String username, @NotNull String name, @NotNull @Email String email, @NotNull String password, Course course) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.course = course;
        subjects = new ArrayList<>();
    }

    public void addSubject(Subject subject){
        subjects.add(subject);
    }

    public void removeSubject(Subject subject){
        subjects.remove(subject);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Id
    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
