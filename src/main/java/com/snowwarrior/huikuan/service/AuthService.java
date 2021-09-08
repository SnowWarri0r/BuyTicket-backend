package com.snowwarrior.huikuan.service;

import com.snowwarrior.huikuan.auth.JwtUser;
import com.snowwarrior.huikuan.constant.UserRoleConstants;
import com.snowwarrior.huikuan.dto.UserDTO;
import com.snowwarrior.huikuan.dto.UserLoginDTO;
import com.snowwarrior.huikuan.model.User;
import com.snowwarrior.huikuan.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public JwtUser authLogin(UserLoginDTO userLogin) {
        String username = userLogin.getUsername();
        String password = userLogin.getPassword();

        Optional<User> userOptional = userService.getUserByName(username);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        User user = userOptional.get();
        if (this.bCryptPasswordEncoder.matches(password, user.getPassword())) {
            String role = user.getRole();
            if (Objects.isNull(role)) {
                role = UserRoleConstants.ROLE_USER;
            }
            String token = JwtUtils.generateToken(username, role);

            Authentication authentication = JwtUtils.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(username);
            userDTO.setBalance(user.getBalance());
            userDTO.setRole(user.getRole());

            return new JwtUser(token, userDTO);
        }
        throw new BadCredentialsException("The username or password error.");
    }

    public void logout() {
        SecurityContextHolder.clearContext();
    }
}
