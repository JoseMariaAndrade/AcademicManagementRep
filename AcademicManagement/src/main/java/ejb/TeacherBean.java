package ejb;

import entity.Subject;
import entity.Teacher;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class TeacherBean {

    @PersistenceContext
    EntityManager entityManager;

    public void create(String username, String password, String name, String email, String office){
        Teacher teacher = new Teacher(username, password, name, email, office);
        entityManager.persist(teacher);
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
