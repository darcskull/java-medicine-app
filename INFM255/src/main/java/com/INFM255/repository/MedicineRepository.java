package com.INFM255.repository;

import com.INFM255.data.Medicine;
import com.INFM255.data.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class MedicineRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MedicineRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createMedicine(Medicine medicine) {
        String query = "INSERT INTO Medicine (id,name, description, diseaseId, price) " +
                "VALUES (nextval('medicine_sequence'),?, ?, ?, ?)";
        jdbcTemplate.update(query,
                medicine.getName(),
                medicine.getDescription(),
                medicine.getDiseaseId(),
                medicine.getPrice());
    }

    public List<Medicine> findAllMedicines() {
        String query = "SELECT * FROM Medicine";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Medicine.class));
    }
}
