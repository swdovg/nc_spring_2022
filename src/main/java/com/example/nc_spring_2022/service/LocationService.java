package com.example.nc_spring_2022.service;

import com.example.nc_spring_2022.dto.mapper.LocationMapper;
import com.example.nc_spring_2022.dto.model.LocationDto;
import com.example.nc_spring_2022.dto.model.RequestDto;
import com.example.nc_spring_2022.exception.AuthorizationException;
import com.example.nc_spring_2022.model.Location;
import com.example.nc_spring_2022.model.User;
import com.example.nc_spring_2022.repository.LocationRepository;
import com.example.nc_spring_2022.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;
    private final AuthenticationFacade authenticationFacade;
    private final UserService userService;
    private final LocationMapper locationMapper;

    public List<Location> findByUserId(Long userId) {
        return locationRepository.findAllByUserId(userId);
    }

    public List<LocationDto> getDtosByUserId() {
        Long userId = authenticationFacade.getUserId();
        List<Location> locations = findByUserId(userId);
        return locationMapper.createFrom(locations);
    }

    public Location findById(Long id) {
        return locationRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Location with id: %d was not found", id)));
    }

    public LocationDto getDtoById(Long id) {
        Location location = findById(id);
        return locationMapper.createFrom(location);
    }

    public Location save(Location location) {
        checkForPermission(location);
        return locationRepository.save(location);
    }

    public LocationDto save(RequestDto requestDto) {
        String locationName = requestDto.getValue();
        Long userId = authenticationFacade.getUserId();
        User user = userService.findById(userId);
        Location location = new Location();
        location.setLocation(locationName);
        location.setUser(user);
        location = locationRepository.save(location);
        return locationMapper.createFrom(location);
    }

    public LocationDto update(LocationDto locationDto) {
        Long userId = authenticationFacade.getUserId();
        User user = userService.findById(userId);
        Location location = findById(locationDto.getId());
        if (location.getUser().equals(user)) {
            location.setLocation(locationDto.getLocation());
            location = locationRepository.save(location);
            return locationMapper.createFrom(location);
        } else {
            throw new AuthorizationException("You can't edit another's locations");
        }
    }

    public void delete(Long locationId) {
        Long userId = authenticationFacade.getUserId();
        User user = userService.findById(userId);
        Location location = findById(locationId);
        if (location.getUser().equals(user)) {
            locationRepository.delete(location);
        } else {
            throw new AuthorizationException("You can't edit another's locations");
        }
    }

    private void checkForPermission(Location location) {
        Location dbLocation = findById(location.getId());
        Long userId = authenticationFacade.getUserId();
        if (!userId.equals(dbLocation.getUser().getId())) {
            throw new AuthorizationException("You can't edit another's locations");
        }
    }
}
