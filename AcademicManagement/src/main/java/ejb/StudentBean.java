package ejb;

import entity.Course;
import entity.Student;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class StudentBean {

    @PersistenceContext
    EntityManager entityManager;

    public void create(String username, String name, String email, String password, int courseId){
        Course course = entityManager.find(Course.class, courseId);
        if (course!=null) {
            Student s = new Student(username, name, email, password, course);
            entityManager.persist(s);
        }
    }

    public List<Student> getAllStudents() {
        // remember, maps to: “SELECT s FROM Student s ORDER BY s.name”
        return (List<Student>) entityManager.createNamedQuery("getAllStudents").getResultList();
    }

    public Student findStudent(String username){
        return entityManager.find(Student.class, username);
    }
}
