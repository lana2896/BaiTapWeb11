package shop.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil_24110347 {
    private static final EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("dataSource");

    private JPAUtil_24110347() {
    }

    public static EntityManager getEntityManager() {
        return FACTORY.createEntityManager();
    }

    public static void shutdown() {
        if (FACTORY.isOpen()) {
            FACTORY.close();
        }
    }
}
