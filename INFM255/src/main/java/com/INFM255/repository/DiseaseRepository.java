package com.INFM255.repository;

import com.INFM255.data.Disease;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class DiseaseRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DiseaseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Disease> findAllDiseases() {
        String query = "SELECT * FROM DISEASE";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Disease.class));
    }

    public void createDisease(Disease disease) {
        String query = "INSERT INTO DISEASE (id,name, type, description) VALUES (nextval('disease_sequence'),?, ?, ?)";
        jdbcTemplate.update(query, disease.getName(), disease.getType(), disease.getDescription());
    }

    public Disease findDiseaseById(int id) {
        String query = "SELECT * FROM DISEASE WHERE id = ?";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query, id);
        return getDisease(rows);
    }

    public Disease findDiseaseByName(String name) {
        String query = "SELECT * FROM DISEASE WHERE name = ?";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query, name);
        return getDisease(rows);
    }

    public boolean doesDiseaseExist(String name) {
        String query = "SELECT COUNT(*) FROM DISEASE WHERE name = ?";
        int count = jdbcTemplate.queryForObject(query, Integer.class, name);
        return count > 0;
    }


    private Disease getDisease(List<Map<String, Object>> rows) {
        if (rows.isEmpty()) {
            return null;
        }

        Map<String, Object> row = rows.get(0);
        Disease disease = new Disease();
        disease.setId((Integer) row.get("id"));
        disease.setName((String) row.get("name"));
        disease.setDescription((String) row.get("description"));
        disease.setType((String) row.get("type"));

        return disease;
    }

}
