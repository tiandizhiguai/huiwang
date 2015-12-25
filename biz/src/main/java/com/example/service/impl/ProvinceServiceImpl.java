package com.example.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.example.dao.ProvinceDao;
import com.example.model.ProvinceModel;
import com.example.service.ProvinceService;
import com.example.vo.ProvinceVO;

@Service
public class ProvinceServiceImpl implements ProvinceService {

    @Resource
    private ProvinceDao provinceDao;

    @Override
    public List<ProvinceVO> getAll() {
        List<ProvinceVO> vos = new ArrayList<ProvinceVO>();
        List<ProvinceModel> models = provinceDao.getAll();

        if (!CollectionUtils.isEmpty(models)) {
            for (ProvinceModel model : models) {
                vos.add(model2VO(model));
            }
        }

        return vos;
    }

    public ProvinceVO model2VO(ProvinceModel model) {
        ProvinceVO vo = new ProvinceVO();
        BeanUtils.copyProperties(model, vo);
        return vo;
    }
}
