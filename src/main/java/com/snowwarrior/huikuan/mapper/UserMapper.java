package com.snowwarrior.huikuan.mapper;

import com.snowwarrior.huikuan.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 数据库CRUD
 * @author SnowWarrior
 */
@Repository
public interface UserMapper {
    @Select("select * from user")
    List<User> getAllUsers();

    @Select("select * from user where id=#{id}")
    Optional<User> getUserById(Long id);

    @Insert("insert into user(" +
            "username,password,balance,role" +
            ")" +
            "values(" +
            "#{username},#{password},#{balance},#{role}" +
            ")")
    Long addUser(User user);

    @Update("update user set username=#{username},password=#{password},balance=#{balance},role=#{role}" +
            "where id=#{id}")
    Long updateUserById(User user);

    @Delete("delete from user where id=#{id}")
    Long deleteUserById(Long id);

    @Select("select * from user where username=#{username}")
    Optional<User> getUserByUsername(String username);

}
