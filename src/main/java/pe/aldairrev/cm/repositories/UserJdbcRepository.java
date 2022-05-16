package pe.aldairrev.cm.repositories;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import pe.aldairrev.cm.models.User;

public class UserJdbcRepository implements IRepository<User, Long> {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public User create(User t) {
        final String SQL = "INSERT INTO users" +
            "(username, firstname, lastname, birthday, email)" +
            "values(?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final PreparedStatementCreator psc = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(final Connection connection) throws SQLException {
				final PreparedStatement ps = connection.prepareStatement(
                    SQL,
                    Statement.RETURN_GENERATED_KEYS
                );
                ps.setString(1, t.getUsername());
                ps.setString(2, t.getFirstname());
                ps.setString(3, t.getLastname());
                ps.setDate(4, Date.valueOf(t.getBirthday()));
                ps.setString(5, t.getEmail());

				return ps;
			}
		};

        jdbcTemplate.update(psc, keyHolder);
        Long newId = keyHolder.getKey().longValue();
        t.setId(newId);

        return t;
    }

    @Override
    public boolean deleteById(Long id) {
        final String SQL = "DELETE FROM users WHERE id=?";
        try {
            jdbcTemplate.update(SQL, id);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public User findById(Long id) {
        final String SQL = "SELECT * FROM users where id=? LIMIT 1";
        User user;
        try {
            user = jdbcTemplate.queryForObject(SQL, getRowMapper(), id);
        } catch (DataAccessException e) {
            user = null;
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        final String SQL = "SELECT * FROM users";
        List<User> users = jdbcTemplate.query(SQL, getRowMapper());
        return users;
    }

    @Override
    public User update(User t) {
        final String SQL = "UPDATE users SET " +
        "username=?, firstname=?, lastname=?, birthday=?, email=? " +
        "WHERE id=?";

        try {
            jdbcTemplate.update(
                SQL,
                t.getUsername(),
                t.getFirstname(),
                t.getLastname(),
                Date.valueOf(t.getBirthday()),
                t.getEmail(),
                t.getId()
            );
        } catch (DataAccessException e) {
            throw new IllegalStateException(e.getMessage());
        }
        return t;
    }

    RowMapper<User> getRowMapper() {
        return (r, i) -> {
            Long id = r.getLong("id");
            String username = r.getString("username");
            String firstname = r.getString("firstname");
            String lastname = r.getString("lastname");
            LocalDate birthday = r.getDate("birthday").toLocalDate();
            String email = r.getString("email");
            return new User(id, username, firstname, lastname, email, birthday);
        };
    }
}
