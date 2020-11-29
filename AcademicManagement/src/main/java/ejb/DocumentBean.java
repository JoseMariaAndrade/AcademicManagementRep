package ejb;

import entity.Document;
import entity.Student;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class DocumentBean {

    @PersistenceContext
    EntityManager entityManager;

    public void create(String username, String filepath, String filename) {

        Student student = entityManager.find(Student.class, username);

        Document document = new Document(filepath, filename, student);

        entityManager.persist(document);
    }

    public Document findDocument(Long id) {
        return entityManager.find(Document.class, id);
    }

    public List<Document> getStudentDocuments(String username) {
        try {
            return entityManager.createNamedQuery("getStudentDocuments", Document.class)
                    .setParameter("username", username).getResultList();
        } catch (Exception exceptione) {
            throw new EJBException("ERRO", exceptione);
        }
    }
}
