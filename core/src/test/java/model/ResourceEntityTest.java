package model;

import org.junit.Before;
import org.junit.Test;
import util.DBUtil;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

/**
 * Created by mjgroos on 5/31/17.
 */
public class ResourceEntityTest {

    EntityManager entityManager;

    @Before
    public void setUp() throws Exception {
        entityManager = DBUtil.getEntityManager();
    }

    @Test
    public void testImage() {

        // Parameters
        String name = "Image";
        String url = "www.site.com";
        int size = 2048000;
        int width = 640;
        int height = 480;

        // Create image
        ImageEntity image = new ImageEntity(name, url, size, width, height);

        // Persist
        entityManager.getTransaction().begin();
        entityManager.persist(image);
        entityManager.getTransaction().commit();

        // Retrieve image with ID
        ImageEntity returnImage = entityManager.find(ImageEntity.class, image.getResourceId());

        // Testing
        assertEquals(returnImage.getName(), image.getName());
        assertEquals(returnImage.getSize(), image.getSize());
        assertEquals(returnImage.getUrl(), image.getUrl());
        assertEquals(returnImage.getWidth(), image.getWidth());
        assertEquals(returnImage.getHeight(), image.getHeight());
    }

}