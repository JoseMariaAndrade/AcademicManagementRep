package ejb;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.ArrayList;

@Singleton(name = "ConfigBean")
@Startup
public class ConfigBean {
    @EJB
    StudentBean studentBean;
    @EJB
    CourseBean courseBean;
    @EJB
    SubjectBean subjectBean;
    @PostConstruct
    void populateDB(){
        System.out.println("Hello Java EE!");
        courseBean.create(1, "Engenharia Informática", new ArrayList<>());
        subjectBean.create(1, "ASD", 1, "ASD","asd");
        studentBean.create("JoseMaria", "Jose Maria","jose@mail.pt", "1234",1);
        studentBean.create("JoseMarias", "Jose Marias","jose@mails.pt", "12345",1);
    }
}
