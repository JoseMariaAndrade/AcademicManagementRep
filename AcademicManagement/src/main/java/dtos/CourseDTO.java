package dtos;

import entity.Student;

import java.io.Serializable;
import java.util.List;

public class CourseDTO implements Serializable {

    private int code;
    private String name;
    private List<Student> students;

    public CourseDTO() {
    }

    public CourseDTO(int code, String name) {
        this.code = code;
        this.name = name;
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
}
