import model.WebsiteEntity;
import util.DBUtil;

import javax.persistence.EntityManager;

/**
 * Created by Marc on 3-5-2017.
 */
public class Application {

    public static void main(String[] args) {

        EntityManager entityManager = DBUtil.getEntityManager();

        WebsiteEntity w = new WebsiteEntity();
        w.setUrl("www.kaas.nl");


        entityManager.getTransaction().begin();
        entityManager.persist(w);
        entityManager.getTransaction().commit();

    }

}
