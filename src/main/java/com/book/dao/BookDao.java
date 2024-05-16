package com.book.dao;

import com.book.domain.Book;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class BookDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String ADD_BOOK_SQL = "INSERT INTO book_info VALUES(NULL ,?,?,?,?,?,?,?,?,?,?,?)";

    private static final String DELETE_BOOK_SQL = "delete from book_info where book_id = ?  ";

    private static final String EDIT_BOOK_SQL = "update book_info set name= ? ,author= ? ,publish= ? ,ISBN= ? ,introduction= ? ,language= ? ,price= ? ,pubdate= ? ,class_id= ? ,pressmark= ? ,state= ?  where book_id= ? ;";

    private static final String QUERY_ALL_BOOKS_SQL = "SELECT * FROM book_info ";

    private static final String QUERY_BOOK_SQL = "SELECT * FROM book_info WHERE book_id like  ?  or name like ?   ";

    //查询匹配图书的个数
    private static final String MATCH_BOOK_SQL = "SELECT count(*) FROM book_info WHERE book_id like ?  or name like ?  ";

    //根据书号查询图书
    private static final String GET_BOOK_SQL = "SELECT * FROM book_info where book_id = ? ";

    public int matchBook(String searchWord) {
        String swcx = "%" + searchWord + "%";
        return jdbcTemplate.queryForObject(MATCH_BOOK_SQL, new Object[]{swcx, swcx}, Integer.class);
    }

    public List<Book> queryBook(String sw) {
        String swcx = "%" + sw + "%";
        final ArrayList<Book> books = new ArrayList<>();
        jdbcTemplate.query(QUERY_BOOK_SQL, new Object[]{swcx, swcx}, resultSet -> {
            resultSet.beforeFirst();
            while (resultSet.next()) {
                Book book = new Book();
                book.setAuthor(resultSet.getString("author"));
                book.setBookId(resultSet.getLong("book_id"));
                book.setClassId(resultSet.getInt("class_id"));
                book.setIntroduction(resultSet.getString("introduction"));
                book.setIsbn(resultSet.getString("isbn"));
                book.setLanguage(resultSet.getString("language"));
                book.setName(resultSet.getString("name"));
                book.setPressmark(resultSet.getInt("pressmark"));
                book.setPubdate(resultSet.getDate("pubdate"));
                book.setPrice(resultSet.getBigDecimal("price"));
                book.setState(resultSet.getInt("state"));
                book.setPublish(resultSet.getString("publish"));
                books.add(book);
            }
        });
        return books;
    }

    public List<Book> queryBook(Book queryBook) {
        final StringBuilder sql = new StringBuilder("SELECT * FROM book_info where 1=1 ");

//        书名、作者、出版社等关键字查询和分类查询
        final List<Object> objects = new ArrayList<>();
        final String name = queryBook.getName();
        if(StringUtils.isNotBlank(name)){
            sql.append(" and name like ? ");
            objects.add("%" + name + "%");
        }
        final String author = queryBook.getAuthor();
        if(StringUtils.isNotBlank(author)){
            sql.append(" and author like ? ");
            objects.add("%" + author + "%");
        }
        final String publish = queryBook.getPublish();
        if(StringUtils.isNotBlank(publish)){
            sql.append(" and publish like ? ");
            objects.add("%" + publish + "%");
        }
        final int classId = queryBook.getClassId();
        if (classId > 0){
            sql.append(" and class_id = ?");
            objects.add(classId);
        }
        final String orderBy = queryBook.getOrderBy();
        if(StringUtils.isNotBlank(orderBy)){
            sql.append(" order by ")
               .append(orderBy)
               .append(" ")
               .append(StringUtils.defaultIfBlank(queryBook.getOrder(), "desc"));
        }
        final ArrayList<Book> books = new ArrayList<>();
        jdbcTemplate.query(sql.toString(), objects.toArray(), resultSet -> {
            books.addAll(createBook(resultSet));
        });

        return books;
    }

    private List<Book> createBook(ResultSet resultSet) throws SQLException{
        resultSet.beforeFirst();
        final ArrayList<Book> books = new ArrayList<>();
        while (resultSet.next()) {
            Book book = new Book();
            book.setAuthor(resultSet.getString("author"));
            book.setBookId(resultSet.getLong("book_id"));
            book.setClassId(resultSet.getInt("class_id"));
            book.setIntroduction(resultSet.getString("introduction"));
            book.setIsbn(resultSet.getString("isbn"));
            book.setLanguage(resultSet.getString("language"));
            book.setName(resultSet.getString("name"));
            book.setPressmark(resultSet.getInt("pressmark"));
            book.setPubdate(resultSet.getDate("pubdate"));
            book.setPrice(resultSet.getBigDecimal("price"));
            book.setState(resultSet.getInt("state"));
            book.setPublish(resultSet.getString("publish"));
            books.add(book);
        }
        return books;
    }

    public List<Book> getAllBooks() {
        final ArrayList<Book> books = new ArrayList<>();

        jdbcTemplate.query(QUERY_ALL_BOOKS_SQL, new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                resultSet.beforeFirst();
                while (resultSet.next()) {
                    Book book = new Book();
                    book.setPrice(resultSet.getBigDecimal("price"));
                    book.setState(resultSet.getInt("state"));
                    book.setPublish(resultSet.getString("publish"));
                    book.setPubdate(resultSet.getDate("pubdate"));
                    book.setName(resultSet.getString("name"));
                    book.setIsbn(resultSet.getString("isbn"));
                    book.setClassId(resultSet.getInt("class_id"));
                    book.setBookId(resultSet.getLong("book_id"));
                    book.setAuthor(resultSet.getString("author"));
                    book.setIntroduction(resultSet.getString("introduction"));
                    book.setPressmark(resultSet.getInt("pressmark"));
                    book.setLanguage(resultSet.getString("language"));
                    books.add(book);
                }
            }
        });
        return books;
    }

    public int deleteBook(long bookId) {
        return jdbcTemplate.update(DELETE_BOOK_SQL, bookId);
    }

    public int addBook(Book book) {
        String name = book.getName();
        String author = book.getAuthor();
        String publish = book.getPublish();
        String isbn = book.getIsbn();
        String introduction = book.getIntroduction();
        String language = book.getLanguage();
        BigDecimal price = book.getPrice();
        Date pubdate = book.getPubdate();
        int classId = book.getClassId();
        int pressmark = book.getPressmark();
        int state = book.getState();
        return jdbcTemplate.update(ADD_BOOK_SQL, new Object[]{name, author, publish, isbn, introduction, language, price, pubdate, classId, pressmark, state});
    }

    public Book getBook(Long bookId) {
        final Book book = new Book();
        jdbcTemplate.query(GET_BOOK_SQL, new Object[]{bookId}, resultSet -> {
            book.setAuthor(resultSet.getString("author"));
            book.setBookId(resultSet.getLong("book_id"));
            book.setClassId(resultSet.getInt("class_id"));
            book.setIntroduction(resultSet.getString("introduction"));
            book.setIsbn(resultSet.getString("isbn"));
            book.setLanguage(resultSet.getString("language"));
            book.setName(resultSet.getString("name"));
            book.setPressmark(resultSet.getInt("pressmark"));
            book.setPubdate(resultSet.getDate("pubdate"));
            book.setPrice(resultSet.getBigDecimal("price"));
            book.setState(resultSet.getInt("state"));
            book.setPublish(resultSet.getString("publish"));
        });
        return book;
    }

    public int editBook(Book book) {
        long bookId = book.getBookId();
        String name = book.getName();
        String author = book.getAuthor();
        String publish = book.getPublish();
        String isbn = book.getIsbn();
        String introduction = book.getIntroduction();
        String language = book.getLanguage();
        BigDecimal price = book.getPrice();
        Date pubdate = book.getPubdate();
        int classId = book.getClassId();
        int pressmark = book.getPressmark();
        int state = book.getState();

        return jdbcTemplate.update(EDIT_BOOK_SQL, name, author, publish, isbn, introduction, language, price, pubdate, classId, pressmark, state, bookId);
    }

}
