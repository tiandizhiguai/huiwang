package com.example.dao;

import java.util.List;

public interface GeneralDao<T1, T2> {

    List<T2> getList(T1 param);

    T2 getById(Long id);

    void insert(T1 param);

    void update(T1 param);

    void delete(T1 param);

}
