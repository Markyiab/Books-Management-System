package com.book.service;

import com.book.dao.ReaderInfoDao;
import com.book.domain.ReaderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReaderInfoService {

    private ReaderInfoDao readerInfoDao;

    @Autowired
    public void setReaderInfoDao(ReaderInfoDao readerInfoDao) {
        this.readerInfoDao = readerInfoDao;
    }

    public List<ReaderInfo> readerInfos() {
        return readerInfoDao.getAllReaderInfo();
    }

    public boolean deleteReaderInfo(int readerId) {
        return readerInfoDao.deleteReaderInfo(readerId) > 0;
    }

    public ReaderInfo getReaderInfo(int readerId) {
        return readerInfoDao.findReaderInfoByReaderId(readerId);
    }

    public boolean readerExist(int readerId) {
        return readerInfoDao.readerExist(readerId);
    }

    public boolean editReaderInfo(ReaderInfo readerInfo) {
        return readerInfoDao.editReaderInfo(readerInfo) > 0;
    }

    public boolean addReaderInfo(ReaderInfo readerInfo) {
        return readerInfoDao.addReaderInfo(readerInfo) > 0;
    }

    public List<ReaderInfo> queryReader(final int readerId, final String name) {
        return readerInfoDao.findReader(readerId, name);
    }

}
