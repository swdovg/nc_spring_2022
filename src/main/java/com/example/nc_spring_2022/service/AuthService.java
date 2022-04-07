package com.example.nc_spring_2022.service;

import com.example.nc_spring_2022.dto.model.LoginDto;
import com.example.nc_spring_2022.dto.model.RegisterDto;
import com.example.nc_spring_2022.exception.EntityAlreadyExistsException;
import com.example.nc_spring_2022.model.Role;
import com.example.nc_spring_2022.model.User;
import com.example.nc_spring_2022.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User getByUsernameAndPassword(String email, String password) {
        User user = userService.findByEmail(email);

        if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        throw new EntityNotFoundException("Wrong password");
    }

    public Map<String, String> login(LoginDto loginDTO) {
        User user = getByUsernameAndPassword(loginDTO.getEmail(), loginDTO.getPassword());

        return getToken(user);
    }

    public Map<String, String> register(RegisterDto registerDto) {
        if (userService.isUserExists(registerDto.getEmail(), registerDto.getPhoneNumber())) {
            throw new EntityAlreadyExistsException("User with this email address or phone number is already registered");
        }

        User user = createUser(registerDto);
        return getToken(user);
    }

    private User createUser(RegisterDto registerDto) {
        User user = new User();
        user.setEmail(registerDto.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(registerDto.getPassword()));
        user.setName(registerDto.getName());
        user.setPhoneNumber(registerDto.getPhoneNumber());
        if (registerDto.isConsumer()) {
            user.setRole(Role.ROLE_CONSUMER);
        } else {
            user.setRole(Role.ROLE_SUPPLIER);
        }
        return userService.save(user);
    }

    private Map<String, String> getToken(User user) {
        String token = jwtTokenProvider.createToken(user.getId(), user.getRole(), user.getVersion());

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("userId", user.getId().toString());
        response.put("role", user.getRole().toString());
        return response;
    }
}
