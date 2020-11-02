package ejb;

import entity.Student;
import entity.Subject;
import entity.Teacher;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityExistsException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Stateless
public class TeacherBean {

    @PersistenceContext
    EntityManager entityManager;

    public void create(String username, String password, String name, String email, String office)
            throws MyEntityExistsException, MyConstraintViolationException {
        Teacher teacher = findTeacher(username);
        if (teacher != null) {
            throw new MyEntityExistsException(String.format("Teacher with username %s already exists", username));
        }

        try {
            teacher = new Teacher(username, password, name, email, office);
            entityManager.persist(teacher);
        }
        catch (ConstraintViolationException constraintViolationException) {
            throw new MyConstraintViolationException(constraintViolationException);
        }
    }

    public List<Teacher> getAllTeachers() {
        return (List<Teacher>) entityManager.createNamedQuery("getAllTeachers").getResultList();
    }

    public Teacher findTeacher(String username) {
        return entityManager.find(Teacher.class, username);
    }

    public void associateTeacherInSubject(int subjectCode, String username){
        Teacher teacher = findTeacher(username);
        Subject subject = entityManager.find(Subject.class,subjectCode);

        if (teacher!=null && subject!= null){
            if (!teacher.getSubjects().contains(subject)){
                teacher.addSubject(subject);
                subject.addTeacher(teacher);
            }
        }
    }
}
