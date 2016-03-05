package com.huiwang.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.huiwang.dao.CityDao;
import com.huiwang.model.CityModel;
import com.huiwang.param.CityParam;
import com.huiwang.service.CityService;
import com.huiwang.vo.City;

@Service
public class CityServiceImpl implements CityService {

    @Resource
    private CityDao cityDao;

    @Override
    public List<City> getAll() {
        List<City> vos = new ArrayList<City>();
        List<CityModel> models = cityDao.getAll();

        if (!CollectionUtils.isEmpty(models)) {
            for (CityModel model : models) {
                vos.add(model2VO(model));
            }
        }

        return vos;
    }

    @Override
    public List<City> getList(CityParam param) {
        List<City> vos = new ArrayList<City>();
        List<CityModel> models = cityDao.getList(param);

        if (!CollectionUtils.isEmpty(models)) {
            for (CityModel model : models) {
                vos.add(model2VO(model));
            }
        }

        return vos;
    }

    public City model2VO(CityModel model) {
        City vo = new City();
        BeanUtils.copyProperties(model, vo);
        return vo;
    }
}
