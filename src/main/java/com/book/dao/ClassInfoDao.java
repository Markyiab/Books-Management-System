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

    private static final String QUERY_ALL_CLASS_SQL = "SELECT class_id,class_name  FROM class_info ";

    private static final String QUERY_BY_ID_SQL = "SELECT class_name FROM class_info where class_id = ?";


    public Map<Integer, String> getAllClasses() {
        final Map<Integer, String> map = new TreeMap<>();
        jdbcTemplate.query(QUERY_ALL_CLASS_SQL, resultSet -> {
            resultSet.beforeFirst();
            while (resultSet.next()) {
                map.put(resultSet.getInt("class_id"), resultSet.getString("class_name"));
            }
        });
        return map;
    }

    public String findById(int classId) {
        return jdbcTemplate.queryForObject(QUERY_BY_ID_SQL, new Object[]{classId}, String.class);
    }

}
