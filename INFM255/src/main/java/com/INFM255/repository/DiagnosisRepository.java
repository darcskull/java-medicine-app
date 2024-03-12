package com.INFM255.repository;

import com.INFM255.data.Diagnosis;
import com.INFM255.data.Disease;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DiagnosisRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DiagnosisRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Diagnosis> findDiagnosesByUserId(Integer userId) {
        String query = "SELECT * FROM DIAGNOSIS WHERE userId = ?";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Diagnosis.class), userId);
    }

    public void createDiagnosis(Diagnosis diagnosis) {
        String query = "INSERT INTO DIAGNOSIS (id,userId, diseaseId) VALUES (nextval('diagnosis_sequence'),?, ?)";
        jdbcTemplate.update(query, diagnosis.getUserId(), diagnosis.getDiseaseId());
    }

    public List<Diagnosis> findAllDiagnoses() {
        String query = "SELECT * FROM DIAGNOSIS";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Diagnosis.class));
    }

}
