package com.josue.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Map;

public class GenericDAOImpl<T> implements IGenericDAO<T>{
    private SessionFactory sessionFactory;

    public GenericDAOImpl(Class<T> cl, SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        if (sessionFactory == null)
            throw new RuntimeException("The Session Factory is null!!!");
    }

    @Override
    public T get(Class<T> cl, Integer id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        T element = (T) session.get(cl, id);
        session.getTransaction().commit();
        return element;
    }

    @Override
    public T save(T object) {
        return null;
    }

    @Override
    public void update(T object) {

    }

    @Override
    public void delete(T object) {

    }

    @Override
    public List<T> query(String hsql, Map<String, Object> params) {
        return null;
    }
}
