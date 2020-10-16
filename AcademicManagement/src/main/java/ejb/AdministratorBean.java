package ejb;

import entity.Administrator;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class AdministratorBean {

    @PersistenceContext
    EntityManager entityManager;

    public void create(String username, String name, String email, String password){
        Administrator administrator = new Administrator(username, password, name, email);
        entityManager.persist(administrator);
    }
}
