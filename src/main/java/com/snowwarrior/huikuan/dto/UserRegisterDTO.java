package com.snowwarrior.huikuan.dto;

import javax.validation.constraints.*;

public class UserRegisterDTO {

    @NotBlank
    @Size(min = 4, max = 30)
    private String username;

    @NotBlank
    @Size(min = 4, max = 30)
    private String password;

    @NotNull
    @Min(value = 100)
    @Max(value = 50000)
    private Double balance;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
