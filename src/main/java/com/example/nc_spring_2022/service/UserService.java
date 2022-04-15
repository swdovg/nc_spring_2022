package com.example.nc_spring_2022.service;

import com.example.nc_spring_2022.dto.model.LocationDto;
import com.example.nc_spring_2022.dto.model.RequestDto;
import com.example.nc_spring_2022.exception.AuthorizationException;
import com.example.nc_spring_2022.model.Currency;
import com.example.nc_spring_2022.model.Location;
import com.example.nc_spring_2022.model.User;
import com.example.nc_spring_2022.repository.LocationRepository;
import com.example.nc_spring_2022.repository.UserRepository;
import com.example.nc_spring_2022.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationFacade authenticationFacade;
    private final LocationRepository locationRepository;

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

    public boolean isUserExists(String email, String phoneNumber) {
        return userRepository.findByEmail(email).isPresent() || userRepository.findByPhoneNumber(phoneNumber).isPresent();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User updateCurrency(RequestDto requestDto) {
        Currency newCurrency = Currency.valueOf(requestDto.getValue());
        Long userId = authenticationFacade.getUserId();
        User user = findById(userId);
        user.setCurrency(newCurrency);
        return save(user);
    }

    public User updateName(RequestDto requestDto) {
        String newName = requestDto.getValue();
        Long userId = authenticationFacade.getUserId();
        User user = findById(userId);
        user.setName(newName);
        return save(user);
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
