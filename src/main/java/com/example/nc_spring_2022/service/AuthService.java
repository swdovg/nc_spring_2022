package com.example.nc_spring_2022.service;

import com.example.nc_spring_2022.dto.model.LoginDto;
import com.example.nc_spring_2022.dto.model.PasswordChangeDto;
import com.example.nc_spring_2022.dto.model.RegisterDto;
import com.example.nc_spring_2022.exception.EntityAlreadyExistsException;
import com.example.nc_spring_2022.model.AuthProvider;
import com.example.nc_spring_2022.model.Role;
import com.example.nc_spring_2022.model.User;
import com.example.nc_spring_2022.repository.UserRepository;
import com.example.nc_spring_2022.security.AuthenticationFacade;
import com.example.nc_spring_2022.security.jwt.JwtTokenProvider;
import com.example.nc_spring_2022.security.oauth2.user.OAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationFacade authenticationFacade;

    public Map<String, String> login(LoginDto loginDTO) {
        User user = getByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());

        return getToken(user);
    }

    private User getByEmailAndPassword(String email, String password) {
        User user = userService.findByEmail(email);

        if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        throw new EntityNotFoundException("Wrong password");
    }

    public Map<String, String> register(RegisterDto registerDto) {
        if (userService.isUserExists(registerDto.getEmail(), registerDto.getPhoneNumber())) {
            throw new EntityAlreadyExistsException("User with this email address or phone number is already registered");
        }

        User user = createUser(registerDto);
        return getToken(user);
    }

    public Map<String, String> updatePassword(PasswordChangeDto passwordChangeDto) {
        Long userId = authenticationFacade.getUserId();
        User user = getByIdAndPassword(userId, passwordChangeDto.getOldPassword());
        user.setVersion(user.getVersion() + 1);
        user.setPassword(bCryptPasswordEncoder.encode(passwordChangeDto.getNewPassword()));
        user = userService.save(user);

        return getToken(user);
    }

    private User getByIdAndPassword(Long userId, String password) {
        User user = userService.findById(userId);

        if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        throw new EntityNotFoundException("Wrong password");
    }

    public Map<String, String> registerOrUpdateOAuthUser(OAuth2User oAuth2User) {
        Optional<User> optionalUser = userRepository.findByEmail(oAuth2User.getEmail());
        User user;

        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            user.setName(oAuth2User.getName());
            user.setProviderId(oAuth2User.getId());
        } else {
            user = new User();
            user.setName(oAuth2User.getName());
            user.setEmail(oAuth2User.getEmail());
            user.setProviderId(oAuth2User.getId());
            user.setRole(Role.ROLE_CONSUMER);
            user.setProvider(AuthProvider.GOOGLE);
        }

        user = userService.save(user);
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
