package com.huiwang.service;

import java.util.List;

public interface GeneralService<T1, T2> {

    List<T2> getList(T1 param);
    
    T2 get(T1 param);

    T2 get(Long id);
    
    void add(T1 param);

    void update(T1 param);

    void delete(T1 param);
}
