package dtos;

public class SubjectDTO {

    private int code;
    private String name;
    private int courseCode;
    private String courseName;
    private int courseYear;
    private int schoolYear;

    public SubjectDTO() {
    }

    public SubjectDTO(int code, String name, int courseCode, String courseName, int courseYear, int schoolYear) {
        this.code = code;
        this.name = name;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.courseYear = courseYear;
        this.schoolYear = schoolYear;
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

    public int getCourseYear() {
        return courseYear;
    }

    public void setCourseYear(int courseYear) {
        this.courseYear = courseYear;
    }

    public int getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(int schoolYear) {
        this.schoolYear = schoolYear;
    }
}
