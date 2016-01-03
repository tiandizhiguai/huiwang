package com.example.service;

import java.util.List;

import com.example.param.CityParam;
import com.example.vo.City;

public interface CityService {

    List<City> getAll();
    
    List<City> getList(CityParam param);
}
