package com.example.todojdbc.dao;

import com.example.todojdbc.dto.ChangePasswordDto;
import com.example.todojdbc.dto.UserLoginDto;
import com.example.todojdbc.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    @Autowired
    JdbcTemplate jdbcTemplate;


    @Override
    public int createUser(User user) {
        int result = 0;
        String SQL_CREATE = "insert into users(id,first_name,last_name) values(?,?,?)";
        try {
            result = jdbcTemplate.update(SQL_CREATE, user.getId(), user.getFirstName(), user.getLastName());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int updateUser(User user) {
        int result = 0;
        String SQL_CREATE = "update users set first_name=?,last_name=? where id=?";
        try {
            result = jdbcTemplate.update(SQL_CREATE, user.getFirstName(), user.getLastName());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean activateUser(int userId) {
        int result = 0;
        String SQL_CREATE = "update users set is_active=1 where id=?";
        try {
            result = jdbcTemplate.update(SQL_CREATE, userId);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return result > 0;
    }

    @Override
    public boolean deactivateUser(int userId) {
        int result = 0;
        String SQL_CREATE = "update users set is_active=0 where id=?";
        try {
            result = jdbcTemplate.update(SQL_CREATE, userId);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return result > 0;
    }

    @Override
    public boolean changePassword(ChangePasswordDto changePasswordDto) {
        int result = 0;
        String SQL_CREATE = "update users set password=0 where password=? and user_id=?";
        try {
            result = jdbcTemplate.update(SQL_CREATE, changePasswordDto.getOldPassword(), changePasswordDto.getNewPassword(), changePasswordDto.getUserId());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return result > 0;
    }

    @Override
    public User login(UserLoginDto userLoginDto) {
        Optional<User> optionalUser = Optional.empty();
        String SQL_SELECT = "select * from users where email=? and password=?";
        User result = null;
        try {
            result = jdbcTemplate.queryForObject(SQL_SELECT, (rs, num) -> {
                User user = new User();
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setActive(rs.getBoolean("is_active"));
                return user;
            });
            if (result != null) {
                optionalUser = Optional.of(result);
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return optionalUser.orElse(null);
    }
}
