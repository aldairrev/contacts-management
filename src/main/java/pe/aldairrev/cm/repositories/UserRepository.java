package pe.aldairrev.cm.repositories;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import pe.aldairrev.cm.models.User;

@Repository
public class UserRepository extends UserJdbcRepository {    
    public User findByUsername(String username) {
        final String SQL = "SELECT * FROM users WHERE username=? LIMIT 1";
        User user;
        try {
            user = jdbcTemplate.queryForObject(SQL, getRowMapper(), username);
        } catch (DataAccessException e) {
            user = null;
        }
        return user;
    }

    public User findByEmail(String email) {
        final String SQL = "SELECT * FROM users WHERE email=? LIMIT 1";
        User user;
        try {
            user = jdbcTemplate.queryForObject(SQL, getRowMapper(), email);
        } catch (DataAccessException e) {
            user = null;
        }
        return user;
    }
}
