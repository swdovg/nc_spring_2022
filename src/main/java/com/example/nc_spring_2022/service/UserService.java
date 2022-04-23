package com.example.nc_spring_2022.service;

import com.example.nc_spring_2022.dto.mapper.UserMapper;
import com.example.nc_spring_2022.dto.model.LocationDto;
import com.example.nc_spring_2022.dto.model.RequestDto;
import com.example.nc_spring_2022.dto.model.UserDto;
import com.example.nc_spring_2022.exception.AuthorizationException;
import com.example.nc_spring_2022.model.Currency;
import com.example.nc_spring_2022.model.Location;
import com.example.nc_spring_2022.model.User;
import com.example.nc_spring_2022.repository.UserRepository;
import com.example.nc_spring_2022.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationFacade authenticationFacade;
    private final UserMapper userMapper;
    @Lazy
    private final LocationService locationService;

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException(String.format("User with id: %d was not found", userId))
        );
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new EntityNotFoundException(String.format("User with email address: %s was not found", email))
        );
    }

    public User getUser() {
        Long userId = authenticationFacade.getUserId();
        return findById(userId);
    }

    public UserDto getUserDto() {
        User user = getUser();
        return userMapper.createFrom(user);
    }

    public boolean isUserExists(String email, String phoneNumber) {
        return userRepository.findByEmail(email).isPresent() || userRepository.findByPhoneNumber(phoneNumber).isPresent();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public UserDto updateCurrency(RequestDto requestDto) {
        Currency newCurrency = Currency.valueOf(requestDto.getValue());
        Long userId = authenticationFacade.getUserId();
        User user = findById(userId);
        user.setCurrency(newCurrency);
        user = save(user);
        return userMapper.createFrom(user);
    }

    public UserDto updateName(RequestDto requestDto) {
        String newName = requestDto.getValue();
        Long userId = authenticationFacade.getUserId();
        User user = findById(userId);
        user.setName(newName);
        user = save(user);
        return userMapper.createFrom(user);
    }

    public UserDto updateDefaultLocation(LocationDto newDefaultLocation) {
        checkPermissionToUpdateLocation(newDefaultLocation);

        Long userId = authenticationFacade.getUserId();
        User user = findById(userId);
        Location location = locationService.findById(newDefaultLocation.getId());

        user.setDefaultLocation(location);
        user = save(user);
        return userMapper.createFrom(user);
    }

    private void checkPermissionToUpdateLocation(LocationDto locationDto) {
        Long userId = authenticationFacade.getUserId();
        if (!locationDto.getUserId().equals(userId)) {
            throw new AuthorizationException("You can't set default location to another user");
        }
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
