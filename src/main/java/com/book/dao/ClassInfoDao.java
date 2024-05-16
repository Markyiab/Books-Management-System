package com.book.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.TreeMap;

@Repository
public class ClassInfoDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String QUERY_ALL_CLASS_SQL = "SELECT * FROM class_info ";

    public Map<Integer, String> getAllBooks() {
        final Map<Integer, String> map = new TreeMap<>();
        jdbcTemplate.query(QUERY_ALL_CLASS_SQL, resultSet -> {
            resultSet.beforeFirst();
            while (resultSet.next()) {
                map.put(resultSet.getInt("class_id"), resultSet.getString("class_name"));
            }
        });
        return map;
    }
}
