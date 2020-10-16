package dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StudentDTO  implements Serializable {

    private String username;
    private String name;
    private String email;
    private String password;
    private int courseCode;
    private String courseName;
    private List<SubjectDTO> subjects;

    public StudentDTO() {
        this.subjects = new ArrayList<>();
    }

    public StudentDTO(String username, String password, String name, String email, int courseCode, String courseName) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.subjects = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public int getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(int courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public List<SubjectDTO> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectDTO> subjects) {
        this.subjects = subjects;
    }
}

/*
* Passo 15 ficha 4
*
* */
