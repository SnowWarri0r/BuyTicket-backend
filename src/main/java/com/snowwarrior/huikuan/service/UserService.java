package com.snowwarrior.huikuan.service;

import com.snowwarrior.huikuan.constant.UserRoleConstants;
import com.snowwarrior.huikuan.dto.UserDTO;
import com.snowwarrior.huikuan.dto.UserRegisterDTO;
import com.snowwarrior.huikuan.exception.AlreadyExistsException;
import com.snowwarrior.huikuan.mapper.UserMapper;
import com.snowwarrior.huikuan.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    public UserDTO profile() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> userOptional = this.getUserByName(username);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("can't get your profile");
        }
        User user = userOptional.get();
        UserDTO dto = new UserDTO();
        dto.setBalance(user.getBalance());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole());
        return dto;
    }


    public String hello() {
        return "Hello World";
    }
}
