package entity;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class Administrator extends User implements Serializable {

    public Administrator() {
        super();
    }

    public Administrator(String username, @NotNull String password, @NotNull String name, @NotNull @Email String email) {
        super(username, password, name, email);
    }
}
