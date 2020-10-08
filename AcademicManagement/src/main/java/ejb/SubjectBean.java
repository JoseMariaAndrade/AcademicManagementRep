package ejb;

import entity.Course;
import entity.Subject;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class SubjectBean {

    @PersistenceContext
    EntityManager entityManager;

    public void create(int code, String name, int courseId, String courseYear, String scholarYear){
        Course course = entityManager.find(Course.class, courseId);
        if (course!=null) {
            Subject subject = new Subject(code, name, course, courseYear, scholarYear);
            entityManager.persist(subject);
        }
    }

    public List<Subject> getAllSubjects(){
        return (List<Subject>) entityManager.createNamedQuery("getAllSubjects").getResultList();
    }
}
