package com.josue.dao;

import com.josue.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class GenericDao {
    private static GenericDao genericDao = null;

    private SessionFactory factory;
    private Transaction transaccion;

    public GenericDao()
    {
        factory = HibernateUtil.getSessionFactory();
    }

    public static GenericDao getInstance()
    {
        if (genericDao == null)
        {
            genericDao = new GenericDao();
        }
        return genericDao;
    }

    public boolean insertar(Object o)
    {
        boolean retorno = false;
        Session session = factory.openSession();
        try {
           transaccion = session.beginTransaction();
           session.save(o);
           transaccion.commit();
           retorno = true;
        }
        catch (Exception e)
        {
            transaccion.rollback();
            retorno = false;
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        return retorno;
    }

    public boolean eliminar(Object o)
    {
        boolean retorno = false;
        Session session = factory.openSession();
        try {
            transaccion = session.beginTransaction();
            session.delete(o);
            transaccion.commit();
            retorno = true;
        }
        catch (Exception e)
        {
            transaccion.rollback();
            retorno = false;
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        return retorno;

    }

}
