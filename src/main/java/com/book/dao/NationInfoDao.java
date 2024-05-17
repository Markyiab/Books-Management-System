package com.book.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.TreeMap;

@Repository
public class NationInfoDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String QUERY_ALL_NATION_SQL = "SELECT nation_id,nation_name FROM nation_info ";
    private static final String QUERY_BY_ID_SQL = "SELECT nation_name FROM nation_info where nation_id = ?";

    public Map<Integer, String> getAllNations() {
        final Map<Integer, String> map = new TreeMap<>();
        jdbcTemplate.query(QUERY_ALL_NATION_SQL, resultSet -> {
            resultSet.beforeFirst();
            while (resultSet.next()) {
                map.put(resultSet.getInt("nation_id"), resultSet.getString("nation_name"));
            }
        });
        return map;
    }

    public String findById(int nationId) {
        return jdbcTemplate.queryForObject(QUERY_BY_ID_SQL, new Object[]{nationId}, String.class);
    }
}
