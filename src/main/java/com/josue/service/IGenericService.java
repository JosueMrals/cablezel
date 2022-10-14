package com.josue.service;

import com.josue.dao.IGenericDAO;

import java.util.List;
import java.util.Map;

public interface IGenericService<T> extends IGenericDAO<T> {
    List<T> getAll();
    void deleteAll();

    T getById(Long id);

    T getId(Long i);

    T getByName(String name);

    List<T> consultarClientes(String hsql, Map<String, Object> params);

}
