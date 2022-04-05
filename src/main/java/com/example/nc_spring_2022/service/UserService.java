package com.example.nc_spring_2022.service;

import com.example.nc_spring_2022.dto.model.LocationDto;
import com.example.nc_spring_2022.dto.model.PasswordChangeDto;
import com.example.nc_spring_2022.exception.AuthorizationException;
import com.example.nc_spring_2022.model.Currency;
import com.example.nc_spring_2022.model.Location;
import com.example.nc_spring_2022.model.User;
import com.example.nc_spring_2022.repository.LocationRepository;
import com.example.nc_spring_2022.repository.UserRepository;
import com.example.nc_spring_2022.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationFacade authenticationFacade;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final LocationRepository locationRepository;

    public List<User> findAll() {
        return userRepository.findAll()
                .stream()
                .map(User.class::cast)
                .collect(Collectors.toList());
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException(String.format("User with id: %d was not found", userId)));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new EntityNotFoundException(String.format("User with email address: %s was not found", email)));
    }

    public User getUser() {
        Long userId = authenticationFacade.getUserId();
        return findById(userId);
    }

    public boolean isUserExists(Long userId) {
        return userRepository.findById(userId).isPresent();
    }

    public boolean isUserExists(String email, String phoneNumber) {
        return userRepository.findByEmail(email).isPresent() || userRepository.findByPhoneNumber(phoneNumber).isPresent();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User updateCurrency(Currency newCurrency) {
        Long userId = authenticationFacade.getUserId();
        User user = findById(userId);
        user.setCurrency(newCurrency);
        return save(user);
    }

    public User updateName(String newName) {
        Long userId = authenticationFacade.getUserId();
        User user = findById(userId);
        user.setName(newName);
        return save(user);
    }

    public void updatePassword(PasswordChangeDto passwordChangeDto) {
        Long userId = authenticationFacade.getUserId();
        User user = findByIdAndPassword(userId, passwordChangeDto.getOldPassword());
        user.setVersion(user.getVersion() + 1);
        user.setPassword(bCryptPasswordEncoder.encode(passwordChangeDto.getNewPassword()));
        save(user);
    }

    private User findByIdAndPassword(Long userId, String password) {
        User user = findById(userId);

        if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        throw new EntityNotFoundException("Wrong password");
    }

    public User updateDefaultLocation(LocationDto newDefaultLocation) {
        Long userId = authenticationFacade.getUserId();
        User user = findById(userId);
        if (!newDefaultLocation.getUserId().equals(user.getId())) {
            throw new AuthorizationException("You can't set default location to another user");
        }
        Location location = locationRepository.getById(newDefaultLocation.getId());

        user.setDefaultLocation(location);
        return save(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }
}
