package com.huiwang.service;

import java.util.List;

import com.huiwang.param.CityParam;
import com.huiwang.vo.City;

public interface CityService {

    List<City> getAll();
    
    List<City> getList(CityParam param);
}
