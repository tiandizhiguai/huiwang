package com.example.service;

import java.util.List;

import com.example.param.CityParam;
import com.example.vo.CityVO;

public interface CityService {

    List<CityVO> getAll();
    
    List<CityVO> getList(CityParam param);
}
