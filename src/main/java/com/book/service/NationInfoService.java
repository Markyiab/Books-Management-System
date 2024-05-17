package com.book.service;

import com.book.dao.NationInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class NationInfoService {
    
    @Autowired
    private NationInfoDao nationInfoDao;

    public Map<Integer, String> getAllNationInfo(){
        return nationInfoDao.getAllNations();
    }

}
