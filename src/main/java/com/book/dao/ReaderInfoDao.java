package com.book.dao;

import com.book.domain.ReaderInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class ReaderInfoDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String ADD_READER_INFO_SQL = "INSERT INTO reader_info VALUES(?,?,?,?,?,?,?)";
    private static final String DELETE_READER_INFO_SQL = "DELETE FROM reader_info where reader_id = ? ";
    private static final String GET_READER_INFO_SQL = "SELECT * FROM reader_info where reader_id = ? ";
    private static final String UPDATE_READER_INFO = "UPDATE reader_info set name = ? ,sex = ? ,birth = ? ,address = ? ,telcode = ?, nation = ? where reader_id = ? ";
    private static final String ALL_READER_INFO_SQL = "SELECT * FROM reader_info";
    private static final String COUNT_READER_SQL = "SELECT count(*) FROM reader_info where reader_id = ? ";

    public List<ReaderInfo> getAllReaderInfo() {
        final ArrayList<ReaderInfo> readers = new ArrayList<>();
        jdbcTemplate.query(ALL_READER_INFO_SQL, resultSet -> {
           resultSet.beforeFirst();
           while (resultSet.next()) {
               ReaderInfo reader = new ReaderInfo();
               setReader(resultSet, reader);
               readers.add(reader);
           }
       });
        return readers;
    }

    private void setReader(final ResultSet resultSet, final ReaderInfo reader) throws SQLException {
        reader.setAddress(resultSet.getString("address"));
        reader.setBirth(resultSet.getDate("birth"));
        reader.setName(resultSet.getString("name"));
        reader.setReaderId(resultSet.getInt("reader_id"));
        reader.setSex(resultSet.getString("sex"));
        reader.setTelcode(resultSet.getString("telcode"));
        reader.setNation(resultSet.getString("nation"));
    }

    public ReaderInfo findReaderInfoByReaderId(int readerId) {
        final ReaderInfo reader = new ReaderInfo();
        jdbcTemplate.query(GET_READER_INFO_SQL, new Object[]{readerId}, resultSet -> {
            setReader(resultSet, reader);
        });
        return reader;
    }

    public int deleteReaderInfo(int readerId) {
        return jdbcTemplate.update(DELETE_READER_INFO_SQL, readerId);
    }

    public int editReaderInfo(ReaderInfo readerInfo) {
        String address = readerInfo.getAddress();
        Date birth = readerInfo.getBirth();
        String name = readerInfo.getName();
        int readerId = readerInfo.getReaderId();
        String sex = readerInfo.getSex();
        String telcode = readerInfo.getTelcode();
        String nation = readerInfo.getNation();
        return jdbcTemplate.update(UPDATE_READER_INFO, name, sex, birth, address, telcode, nation, readerId);
    }

    public int addReaderInfo(ReaderInfo readerInfo) {
        String address = readerInfo.getAddress();
        Date birth = readerInfo.getBirth();
        String name = readerInfo.getName();
        int readerId = readerInfo.getReaderId();
        String sex = readerInfo.getSex();
        String telcode = readerInfo.getTelcode();
        String nation = readerInfo.getNation();
        return jdbcTemplate.update(ADD_READER_INFO_SQL, readerId, name, sex, birth, address, telcode, nation);
    }

    public List<ReaderInfo> findReader(final int readerId, final String name) {
        final StringBuilder sql = new StringBuilder("SELECT * FROM reader_info where 1=1 ");

//        书名、作者、出版社等关键字查询和分类查询
        final List<Object> objects = new ArrayList<>();
        if(readerId > 0){
            sql.append(" and reader_id like ? ");
            objects.add("%" + readerId + "%");
        }
        if(StringUtils.isNotBlank(name)){
            sql.append(" and name like ? ");
            objects.add("%" + name + "%");
        }
        final List<ReaderInfo> readers = new ArrayList<>();
        jdbcTemplate.query(sql.toString(), objects.toArray(), resultSet -> {
            resultSet.beforeFirst();
            while (resultSet.next()) {
                ReaderInfo reader = new ReaderInfo();
                setReader(resultSet, reader);
                readers.add(reader);
            }
        });
        return readers;
    }

    public boolean readerExist(final int readerId) {
        return jdbcTemplate.queryForObject(COUNT_READER_SQL, new Object[]{readerId}, Integer.class) > 0;
    }

}
