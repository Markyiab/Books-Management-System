package com.book.service;

import com.book.dao.ClassInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ClassInfoService {

    @Autowired
    private ClassInfoDao classInfoDao;

    public Map<Integer, String> getAllClassInfo(){
        return classInfoDao.getAllBooks();
    }
}
