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
        String query = "INSERT INTO MEDICINE (id,name, description, diseaseId, price) " +
                "VALUES (nextval('medicine_sequence'),?, ?, ?, ?)";
        jdbcTemplate.update(query,
                medicine.getName(),
                medicine.getDescription(),
                medicine.getDiseaseId(),
                medicine.getPrice());
    }

    public List<Medicine> findAllMedicines() {
        String query = "SELECT * FROM MEDICINE";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Medicine.class));
    }

    public List<Medicine> findMedicinesByDiseaseId(Integer diseaseId) {
        String query = "SELECT * FROM MEDICINE WHERE diseaseId = ?";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Medicine.class), diseaseId);
    }

    public Medicine findMedicineById(int medicineId) {
        String query = "SELECT * FROM MEDICINE WHERE id = ?";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query, medicineId);

        if (rows.isEmpty()) {
            return null;
        }

        Map<String, Object> row = rows.get(0);
        Medicine medicine = new Medicine();
        medicine.setId((Integer) row.get("id"));
        medicine.setName((String) row.get("name"));
        medicine.setDescription((String) row.get("description"));
        medicine.setDiseaseId((Integer) row.get("diseaseId"));
        medicine.setPrice((BigDecimal) row.get("price"));

        return medicine;
    }

    public boolean doesMedicineExistByName(String name) {
        String query = "SELECT COUNT(*) FROM MEDICINE WHERE name = ?";
        int count = jdbcTemplate.queryForObject(query, Integer.class, name);
        return count > 0;
    }


}
