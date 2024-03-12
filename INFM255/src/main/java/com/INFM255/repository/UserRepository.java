package com.INFM255.repository;

import com.INFM255.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> findAllUsers() {
        List<Map<String, Object>> rows =  jdbcTemplate.queryForList("SELECT * FROM \"USER\" WHERE isDoctor = ?", false);
        List<User> users = new ArrayList<>();

        for (Map<String, Object> row : rows) {
            User user = new User();
            user.setId((Integer) row.get("id"));
            user.setFirstName((String) row.get("firstName"));
            user.setLastName((String) row.get("lastName"));
            user.setPhoneNumber((String) row.get("phoneNumber"));
            user.setPersonalNumber((String) row.get("personalNumber"));
            user.setEmail((String) row.get("email"));
            user.setIsDoctor(false);
            users.add(user);
        }

        return users;
    }

    public User findUserByEmailAndPassword(String email, String password) {
        String query = "SELECT * FROM \"USER\" WHERE email = ? AND password = ?";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query, email, password);

        if (rows.isEmpty()) {
            return null;
        }

        Map<String, Object> row = rows.get(0);
        User user = new User();
        user.setId((Integer) row.get("id"));
        user.setFirstName((String) row.get("firstName"));
        user.setLastName((String) row.get("lastName"));
        user.setEmail((String) row.get("email"));
        user.setPhoneNumber((String) row.get("phoneNumber"));
        user.setPersonalNumber((String) row.get("personalNumber"));
        user.setIsDoctor((Boolean) row.get("isDoctor"));

        return user;
    }

    public void createUser(User user) {
        String query = "INSERT INTO \"USER\" (id, firstName, lastName, email, password, phoneNumber, personalNumber, isDoctor) " +
                "VALUES (nextval('user_sequence'),?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(query,
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getPhoneNumber(),
                user.getPersonalNumber(),
                user.getIsDoctor());
    }

    public Boolean doesUserExistByEmail(String email) {
        String query = "SELECT COUNT(*) FROM \"USER\" WHERE email = ?";
        int count = jdbcTemplate.queryForObject(query, Integer.class, email);
        return count > 0;
    }

}
