package com.book.service;

import com.book.dao.LendDao;
import com.book.domain.Lend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LendService {

    @Autowired
    private LendDao lendDao;

    public boolean bookReturn(long bookId) {
        return lendDao.bookReturnOne(bookId) > 0 && lendDao.bookReturnTwo(bookId) > 0;
    }

    public boolean bookLend(long bookId, int readerId) {
        return lendDao.bookLendOne(bookId, readerId) > 0 && lendDao.bookLendTwo(bookId) > 0;
    }

    public List<Lend> lendList() {
        return lendDao.lendList();
    }

    public List<Lend> myLendList(int readerId) {
        return lendDao.myLendList(readerId);
    }

    public boolean bookLose(final long bookId) {
        return lendDao.bookLose(bookId) > 0;
    }

    public int lendReaderOut(final long bookId) {
        return lendDao.lendReaderOut(bookId);
    }

}
