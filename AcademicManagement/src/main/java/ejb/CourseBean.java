package ejb;

import entity.Course;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityExistsException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Stateless
public class CourseBean {

    @PersistenceContext
    EntityManager entityManager;

    public void create(int code, String name)
            throws MyEntityExistsException, MyConstraintViolationException {
        Course course = findCourse(code);

        if (course != null) {
            throw new MyEntityExistsException(String.format("Course with code %s already exists", code));
        }

        try {
            course = new Course(code, name);
            entityManager.persist(course);
        }
        catch (ConstraintViolationException constraintViolationException) {
            throw new MyConstraintViolationException(constraintViolationException);
        }

    }

    public List<Course> getAllCourses(){
        return (List<Course>) entityManager.createNamedQuery("getAllCourses").getResultList();
    }

    public Course findCourse(int courseCode) {
        return entityManager.find(Course.class, courseCode);
    }

    public Course removeCourse(int courseCode){
        Course course = findCourse(courseCode);
        if (course!=null) {
            /*for (Student student: course.getStudents()) {
                course.removeStudent(student);
            }
            entityManager.persist(course);*/
            entityManager.remove(course);
        }

        return course;
    }
}
