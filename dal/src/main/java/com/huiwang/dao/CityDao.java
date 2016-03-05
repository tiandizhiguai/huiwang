package com.huiwang.dao;

import java.util.List;

import com.huiwang.model.CityModel;
import com.huiwang.param.CityParam;

public interface CityDao extends GeneralDao<CityParam, CityModel> {

    List<CityModel> getAll();
}
