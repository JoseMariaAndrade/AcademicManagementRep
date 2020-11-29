package ejb;

import entity.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UserBean {

    @PersistenceContext
    EntityManager entityManager;

    public User authenticate(final String username, final String password)
            throws Exception {
        User user =  entityManager.find(User.class, username);
        if (user != null && user.getPassword().equals(User.hashPassword(password))) {
            return user;
        }
        throw new Exception(String.format("Failed logging in with username %s : unknown username or wrong password",username));
    }
}
