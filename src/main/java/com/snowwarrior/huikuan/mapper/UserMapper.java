package com.snowwarrior.huikuan.mapper;

import com.snowwarrior.huikuan.model.User;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author SnowWarrior
 */
public interface UserMapper {
    List<User> getAllUsers();

    User getUserById(Long id);

    long addUser(User user);

    long updateUserById(User user);

    long deleteUserById(Long id);
}
