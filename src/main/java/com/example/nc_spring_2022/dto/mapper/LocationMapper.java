package com.example.nc_spring_2022.dto.mapper;

import com.example.nc_spring_2022.dto.model.LocationDto;
import com.example.nc_spring_2022.model.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LocationMapper {
    public LocationDto createFrom(Location location) {
        LocationDto locationDto = new LocationDto();

        locationDto.setId(location.getId());
        locationDto.setLocation(location.getLocation());
        locationDto.setUserId(location.getUser().getId());

        return locationDto;
    }

    public List<LocationDto> createFrom(List<Location> locations) {
        List<LocationDto> locationDtos = new ArrayList<>();
        for (Location location : locations) {
            locationDtos.add(createFrom(location));
        }
        return locationDtos;
    }
}
