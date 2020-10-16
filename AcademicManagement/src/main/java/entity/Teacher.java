package entity;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(name = "getAllTeachers", query = "SELECT t FROM Teacher t ORDER BY t.name") //JPQL
})
public class Teacher extends User implements Serializable {

    private String office;
    @ManyToMany(mappedBy = "teachers")
    private Set<Subject> subjects;

    public Teacher() {
        super();
    }

    public Teacher(String username, @NotNull String password, @NotNull String name, @NotNull @Email String email, String office) {
        super(username, password, name, email);
        this.office = office;
        this.subjects = new LinkedHashSet<>();
    }

    public void addSubject(Subject subject){
        this.subjects.add(subject);
    }

    public void removeSubject(Subject subject){
        this.subjects.remove(subject);
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }
}
