package com.josue.service;

import com.josue.dao.GenericDAOImpl;
import com.josue.dao.IGenericDAO;
import com.josue.modelo.Cliente;
import com.josue.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Map;

public class GenericServiceImpl<T> implements IGenericService<T> {
    Logger logger = LogManager.getLogger(GenericServiceImpl.class);
    private IGenericDAO<T> dao;
    private Class<T> cl;
    SessionFactory session;

    public GenericServiceImpl(Class<T> cl, SessionFactory sessionFactory) {
        this.cl = cl;
        dao = new GenericDAOImpl<>(cl, sessionFactory);
        session = sessionFactory;
    }

    @Override
    public T get(Class<T> cl, Integer id) {
        return (T) dao.get(cl, id);
    }

    @Override
    public T get(Class<T> cl, Long id) {
        return (T) dao.get(cl, id);
    }

    @Override
    public T save(T object) {
        return (T) dao.save(object);
    }

    @Override
    public void update(T object) {
        dao.update(object);
    }

    @Override
    public void delete(T object) {
        dao.delete(object);
    }

    @Override
    public List<T> query(String hsql, Map<String, Object> params) {
        return (List<T>) dao.query(hsql, params);
    }

    @Override
    public List<T> getAll() {
        // print log
        logger.info("getAll()");
        logger.info("cl: " + cl);
        logger.info("dao: " + dao);
        logger.info("cl.getSimpleName(): " + cl.getSimpleName());
        logger.info("cl.getName(): " + cl.getName());
        // return all data or null if no data found using ternary operator
        return dao.query("FROM " + cl.getSimpleName(), null) == null ? null : dao.query("FROM " + cl.getSimpleName(), null);

        //return query("from " + cl.getName(), null);
    }

    @Override
    public void deleteAll() {
        query("delete from " + cl.getName(), null);
    }

    @Override
    public T getById(Long id) {
        return dao.get(cl, id);
    }

    @Override
    public T getId(Long i) {
        return dao.get(cl, i);
    }

    @Override
    public T getByName(String name) {
        return (List<T>) dao.query("FROM " + cl.getSimpleName() + " WHERE name = '" + name + "'", null) == null ? null : dao.query("FROM " + cl.getSimpleName() + " WHERE name = '" + name + "'", null).get(0);
    }

    @Override
    public List<T> consultarClientes(String hsql, Map<String, Object> params) {
        return dao.query("FROM " + cl.getSimpleName(), null) == null ? null : dao.query("FROM " + cl.getSimpleName(), null);
    }
}
