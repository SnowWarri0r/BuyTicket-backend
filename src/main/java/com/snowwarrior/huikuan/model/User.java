package com.snowwarrior.huikuan.model;

import com.snowwarrior.huikuan.dto.UserRegisterDTO;
import org.springframework.beans.BeanUtils;

/**
 * user模型
 * @author SnowWarrior
 */
public class User {
    private Long id;

    private String username;

    private String password;

    private Double balance;

    private String role;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getBalance() {
        return balance;
    }

    public String getRole() {
        return role;
    }

    public static User convertOfUserRegisterDTO(UserRegisterDTO dto) {
        User user = new User();
        BeanUtils.copyProperties(dto, user);

        return user;
    }
}
