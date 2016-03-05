package com.huiwang.dao;

import java.util.List;

import com.huiwang.model.ProvinceModel;
import com.huiwang.param.ProvinceParam;

public interface ProvinceDao extends GeneralDao<ProvinceParam, ProvinceModel> {

    List<ProvinceModel> getAll();
}
