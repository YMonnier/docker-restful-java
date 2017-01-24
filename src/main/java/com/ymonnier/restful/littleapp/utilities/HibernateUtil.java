package com.ymonnier.restful.littleapp.utilities;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Project Project.
 * Package com.ymonnier.restful.littleapp.utilities.
 * File HibernateUtil.java.
 * Created by Ysee on 24/01/2017 - 22:38.
 * www.yseemonnier.com
 * https://github.com/YMonnier
 */
public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    /**
     * Create the SessionFactory from hibernate.cfg.xml
     *
     * @return A new SessionFactory
     */
    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable exception) {
            System.err.println("Initial SessionFactory creation failed." + exception);
            throw new ExceptionInInitializerError(exception);
        }
    }

    /**
     * Getter session factory.
     *
     * @return session factory.
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Close the current session
     * (Close caches and connection pools).
     */
    public static void shutdown() {
        getSessionFactory().close();
    }
}
