package com.example.dao;

import java.util.List;

import com.example.model.CityModel;
import com.example.param.CityParam;

public interface CityDao extends GeneralDao<CityParam, CityModel> {

    List<CityModel> getAll();
}
