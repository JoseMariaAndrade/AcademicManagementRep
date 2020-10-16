package ejb;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton(name = "ConfigBean")
@Startup
public class ConfigBean {
    @EJB
    StudentBean studentBean;
    @EJB
    CourseBean courseBean;
    @EJB
    SubjectBean subjectBean;
    private static final Logger LOGGER = Logger.getLogger("ebjs.ConfigBean");
    @EJB
    TeacherBean teacherBean;
    @EJB
    AdministratorBean administratorBean;

    @PostConstruct
    void populateDB(){

        try {
            courseBean.create(1, "Engenharia Informática");
            courseBean.create(2, "Engenharia Gestão Industrial");
            administratorBean.create("JoseMaria", "Jose Maria", "asdjoseMa@asd.com", "1232311");
            teacherBean.create("JoseMariaMa","1234567", "JoseMariaMa","asdasdasd@asd.com","1.02.1");
            subjectBean.create(1, "ASD", 1, 1,1);
            subjectBean.create(2, "ASDasdasd", 1, 1,1);
            studentBean.create("Manuel", "12212", "Manuela", "main@amail.pt", 1);
            studentBean.enrollStudentInSubject(1, "Manuel");
            //studentBean.enrollStudentInSubject(2, "Manuel");
            teacherBean.associateTeacherInSubject(1, "JoseMariaMa");

            //studentBean.create("foo", "bar", "foo", "foo@mail.pt", 1000);
        } catch (Exception exception){
            LOGGER.log(Level.SEVERE, exception.getMessage());
        }
    }
}
