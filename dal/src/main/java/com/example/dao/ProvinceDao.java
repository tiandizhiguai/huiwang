package com.example.dao;

import java.util.List;

import com.example.model.ProvinceModel;
import com.example.param.ProvinceParam;

public interface ProvinceDao extends GeneralDao<ProvinceParam, ProvinceModel> {

    List<ProvinceModel> getAll();
}
