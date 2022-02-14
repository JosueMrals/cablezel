package com.josue.service;

import com.josue.dao.IGenericDAO;

import java.util.List;

public interface IGenericService<T> extends IGenericDAO<T> {
    List<T> getAll();
    void deleteAll();
}
