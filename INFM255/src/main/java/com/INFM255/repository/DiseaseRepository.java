package com.INFM255.repository;

import com.INFM255.data.Disease;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DiseaseRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DiseaseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Disease> findAllDiseases() {
        String query = "SELECT * FROM Disease";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Disease.class));
    }

    public void createDisease(Disease disease) {
        String query = "INSERT INTO Disease (id,name, type, description) VALUES (nextval('disease_sequence'),?, ?, ?)";
        jdbcTemplate.update(query, disease.getName(), disease.getType(), disease.getDescription());
    }

}
