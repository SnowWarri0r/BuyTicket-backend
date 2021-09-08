package com.snowwarrior.huikuan.service;

import com.snowwarrior.huikuan.constant.UserRoleConstants;
import com.snowwarrior.huikuan.dto.ChargeDTO;
import com.snowwarrior.huikuan.dto.UserDTO;
import com.snowwarrior.huikuan.dto.UserRegisterDTO;
import com.snowwarrior.huikuan.exception.AlreadyExistsException;
import com.snowwarrior.huikuan.exception.InvalidOperationException;
import com.snowwarrior.huikuan.exception.UnaffordableException;
import com.snowwarrior.huikuan.mapper.UserMapper;
import com.snowwarrior.huikuan.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            throw new UsernameNotFoundException("can't get your profile.");
        }
        User user = userOptional.get();
        UserDTO dto = new UserDTO();
        dto.setBalance(user.getBalance());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole());
        return dto;
    }

    @Transactional
    public void charge(ChargeDTO charge) {
        Optional<User> receiverOptional = this.getUserByName(charge.getReceiver());
        if (receiverOptional.isEmpty()) {
            throw new UsernameNotFoundException("can't find receiver.");
        }
        String senderName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> senderOptional = this.getUserByName(senderName);
        if (senderOptional.isEmpty()) {
            throw new UsernameNotFoundException("can't get sender info.");
        }
        User sender = senderOptional.get();
        User receiver = receiverOptional.get();
        if (sender.getUsername().equals(receiver.getUsername())) {
            throw new InvalidOperationException("You can't charge to yourself.");
        }
        double amount = charge.getAmount();
        double fee;
        if (Double.compare(amount, 200) < 0) {
            fee = 2;
        } else if (Double.compare(5000, amount) < 0) {
            fee = 50;
        } else {
            fee = amount * 0.01;
        }
        if (sender.getBalance() < amount + fee) {
            throw new UnaffordableException("Your account balance is lower than fee and send amount.");
        }
        sender.setBalance(sender.getBalance() - (amount + fee));
        receiver.setBalance(receiver.getBalance() + amount);
        userMapper.updateUserById(sender);
        userMapper.updateUserById(receiver);
    }


    public String hello() {
        return "Hello World";
    }
}
