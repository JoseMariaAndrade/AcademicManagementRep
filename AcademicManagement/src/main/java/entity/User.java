package entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Entity
@Table(name = "USERS")
public class User implements Serializable {

    @Id
    public String username;
    @NotNull
    public String password;
    @NotNull
    public String name;
    @NotNull
    @Email
    public String email;
    @Version
    private int version;

    public User() {
    }

    public User(String username, @NotNull String password, @NotNull String name, @NotNull @Email String email) {
        this.username = username;
        this.password = hashPassword(password);
        this.name = name;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static String hashPassword(String password) {
        char[] encoded = null;
        try {
            ByteBuffer passwordBuffer = Charset.defaultCharset().encode(CharBuffer.wrap(password));
            byte[] passwordBytes = passwordBuffer.array();
            MessageDigest messageDigestEnconde = MessageDigest.getInstance("SHA-256");
            messageDigestEnconde.update(passwordBytes, 0, password.toCharArray().length);
            encoded = new BigInteger(1, messageDigestEnconde.digest()).toString(16).toCharArray();
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, noSuchAlgorithmException);
        }

        return new String(encoded);
    }
}
