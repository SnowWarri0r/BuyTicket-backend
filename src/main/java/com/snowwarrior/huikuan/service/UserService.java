package com.snowwarrior.huikuan.service;

import com.snowwarrior.huikuan.constant.UserRoleConstants;
import com.snowwarrior.huikuan.dto.UserRegisterDTO;
import com.snowwarrior.huikuan.exception.AlreadyExistsException;
import com.snowwarrior.huikuan.mapper.UserMapper;
import com.snowwarrior.huikuan.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author SnowWarrior
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void register(UserRegisterDTO dto) {
        Optional<User> userOptional = this.getUserByName(dto.getUsername());
        if (userOptional.isPresent()) {
            throw new AlreadyExistsException("Save failed,the username already exist.");
        }
        User user = User.convertOfUserRegisterDTO(dto);
        String cryptPassword = bCryptPasswordEncoder.encode(dto.getPassword());
        user.setPassword(cryptPassword);
        user.setRole(UserRoleConstants.ROLE_USER);
        try {
            userMapper.addUser(user);
        } catch (DataIntegrityViolationException e) {
            throw new AlreadyExistsException("Save failed,the username already exist.");
        }
    }

    public Optional<User> getUserByName(String username) {
        return userMapper.getUserByUsername(username);
    }


    public String hello() {
        return "Hello World";
    }
}
