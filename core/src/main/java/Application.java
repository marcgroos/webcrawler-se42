import constants.DBConstants;
import model.WebsiteEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Marc on 3-5-2017.
 */
public class Application {

    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(DBConstants.DB_PROVIDER);
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        WebsiteEntity w = new WebsiteEntity();
        w.setUrl("www.test.nl");

        entityManager.getTransaction().begin();
        entityManager.persist(w);
        entityManager.getTransaction().commit();

    }

}
