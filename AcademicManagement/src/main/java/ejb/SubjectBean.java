package ejb;

import entity.Course;
import entity.Subject;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Stateless
public class SubjectBean {

    @PersistenceContext
    EntityManager entityManager;

    public void create(int code, String name, int courseId, int courseYear, int scholarYear)
            throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {

        Subject subject = findSubject(code);
        if (subject != null){
            throw new MyEntityExistsException(String.format("Subject with code %s already exists", code));
        }

        Course course = entityManager.find(Course.class, courseId);
        if (course == null) {
            throw new MyEntityNotFoundException(String.format("Course with code: %d not found.", courseId));
        }

        try {
            subject = new Subject(code, name, course, courseYear, scholarYear);
            entityManager.persist(subject);
            course.addSubject(subject);
        }
        catch (ConstraintViolationException constraintViolationException) {
            throw new MyConstraintViolationException(constraintViolationException);
        }
    }

    public List<Subject> getAllSubjects(){
        return (List<Subject>) entityManager.createNamedQuery("getAllSubjects").getResultList();
    }

    public Subject findSubject(int subjectCode) {
        return entityManager.find(Subject.class, subjectCode);
    }
}
