package ejb;

import entity.Course;
import entity.Student;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class CourseBean {
    @PersistenceContext
    EntityManager entityManager;

    public void create(int code, String name, List<Student> students){
        Course course = new Course(code, name);
        for (Student student : students){
            course.addStudent(student);
        }
        entityManager.persist(course);
    }

    public List<Course> getAllCourses(){
        return (List<Course>) entityManager.createNamedQuery("getAllCourses").getResultList();
    }

    public Course findCourse(int code) {
        return entityManager.find(Course.class, code);
    }
}
