package com.josue.dao;


import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory()
    {
        if(sessionFactory == null)
        {
            try
            {
                sessionFactory = new Configuration().configure().buildSessionFactory();
            }
            catch (HibernateException e)
            {
                e.printStackTrace();
                if (sessionFactory != null)
                {
                    sessionFactory.close();
                }
            }
        }
        return sessionFactory;
    }

    private static void shutdown()
    {
        if(sessionFactory != null)
        {
            sessionFactory.close();
        }
    }
}
