package com.book.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;
import com.book.domain.Lend;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class LendDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String BOOK_RETURN_SQL_ONE = "UPDATE lend_list SET back_date = ? WHERE book_id = ? AND back_date is NULL";

    private static final String BOOK_RETURN_SQL_TWO = "UPDATE book_info SET state = 1 WHERE book_id = ? ";

    private static final String BOOK_LEND_SQL_ONE = "INSERT INTO lend_list (book_id,reader_id,lend_date) VALUES ( ? , ? , ? )";

    private static final String BOOK_LEND_SQL_TWO = "UPDATE book_info SET state = 0 WHERE book_id = ? ";

    private static final String LEND_LIST_SQL = "SELECT * FROM lend_list";

    private static final String MY_LEND_LIST_SQL = "SELECT * FROM lend_list WHERE reader_id = ? ";

    public int bookReturnOne(long bookId) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return jdbcTemplate.update(BOOK_RETURN_SQL_ONE, df.format(new Date()), bookId);
    }

    public int bookReturnTwo(long bookId) {
        return jdbcTemplate.update(BOOK_RETURN_SQL_TWO, bookId);
    }

    public int bookLendOne(long bookId, int readerId) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return jdbcTemplate.update(BOOK_LEND_SQL_ONE, bookId, readerId, df.format(new Date()));
    }

    public int bookLendTwo(long bookId) {
        return jdbcTemplate.update(BOOK_LEND_SQL_TWO, bookId);
    }

    public List<Lend> lendList() {
        final ArrayList<Lend> list = new ArrayList<>();

        jdbcTemplate.query(LEND_LIST_SQL, getRowCallbackHandler(list));
        return list;
    }

    public List<Lend> myLendList(int readerId) {
        final ArrayList<Lend> list = new ArrayList<>();
        jdbcTemplate.query(MY_LEND_LIST_SQL, new Object[]{readerId}, getRowCallbackHandler(list));
        return list;
    }

    private RowCallbackHandler getRowCallbackHandler(final ArrayList<Lend> list) {
        return resultSet -> {
            resultSet.beforeFirst();
            while (resultSet.next()) {
                Lend lend = new Lend();
                lend.setBackDate(resultSet.getDate("back_date"));
                lend.setBookId(resultSet.getLong("book_id"));
                lend.setLendDate(resultSet.getDate("lend_date"));
                lend.setReaderId(resultSet.getInt("reader_id"));
                lend.setSernum(resultSet.getLong("sernum"));
                list.add(lend);
            }
        };
    }

}
