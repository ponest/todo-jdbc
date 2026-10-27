package com.example.todojdbc.dao;

import com.example.todojdbc.dto.ChangePasswordDto;
import com.example.todojdbc.dto.UserLoginDto;
import com.example.todojdbc.entity.User;

public interface UserService {
    int createUser(User user);

    int updateUser(User user);

    boolean activateUser(int userId);

    boolean deactivateUser(int userId);

    boolean changePassword(ChangePasswordDto changePasswordDto);

    User login(UserLoginDto userLoginDto);

}
