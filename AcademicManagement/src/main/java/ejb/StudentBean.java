package ejb;

import entity.Course;
import entity.Student;
import entity.Subject;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Stateless
public class StudentBean {

    @PersistenceContext
    EntityManager entityManager;

    public void create(String username, String password, String name, String email, int courseId)
            throws MyEntityNotFoundException, MyEntityExistsException, MyConstraintViolationException {

        Student student = findStudent(username);
        if (student != null){
            throw new MyEntityExistsException(String.format("Student with username %s already exists", username));
        }

        Course course = entityManager.find(Course.class, courseId);
        if (course == null){
            throw new MyEntityNotFoundException(String.format("Course with code %d not found.", courseId));
        }

        try {
            student = new Student(username, password, name, email, course);
            course.addStudent(student);
            entityManager.persist(student);
        }
        catch (ConstraintViolationException constraintViolationException) {
            throw new MyConstraintViolationException(constraintViolationException);
        }
    }

    public void update(String username, String password, String name, String email, int courseCode)
            throws MyEntityNotFoundException, MyConstraintViolationException{

        Student student = entityManager.find(Student.class, username);
        if (student != null) {

            Course course = entityManager.find(Course.class, courseCode);
            if (course != null){

                try {
                    entityManager.lock(student, LockModeType.OPTIMISTIC);
                    student.setCourse(course);
                    student.setName(name);
                    student.setEmail(email);
                    student.setPassword(password);
                }
                catch (ConstraintViolationException constraintViolationException){
                    throw new MyConstraintViolationException(constraintViolationException);
                }
            }
            else {
                System.err.println("ERROR_FINDING_COURSE");
                throw new MyEntityNotFoundException(String.format("Course with code %d not found.", courseCode));
            }
        }
        else {
            System.err.println("ERROR_FINDING_STUDENT");
            throw new MyEntityNotFoundException(String.format("Username with username %d not found.", username));
        }
    }

    public List<Student> getAllStudents() {
        // remember, maps to: “SELECT s FROM Student s ORDER BY s.name”
        return (List<Student>) entityManager.createNamedQuery("getAllStudents").getResultList();
    }

    public Student findStudent(String username){
        return entityManager.find(Student.class, username);
    }

    public void enrollStudentInSubject(int subjectCode, String username){
        Student student = findStudent(username);
        Subject subject = entityManager.find(Subject.class,subjectCode);
        if (student!=null && subject!= null){
            if (student.getCourse().equals(subject.getCourse())){
                if (!student.getSubjects().contains(subject)) {
                    student.addSubject(subject);
                    subject.addStudent(student);
                } else {
                    student.removeSubject(subject);
                    subject.removeStudent(student);
                }
            }

        }
    }
}
