package com.example.nc_spring_2022.controller;

import com.example.nc_spring_2022.dto.mapper.LocationMapper;
import com.example.nc_spring_2022.dto.model.LocationDto;
import com.example.nc_spring_2022.dto.model.RequestDto;
import com.example.nc_spring_2022.dto.model.Response;
import com.example.nc_spring_2022.model.Location;
import com.example.nc_spring_2022.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/location")
public class LocationController {
    private final LocationService locationService;
    private final LocationMapper locationMapper;

    @GetMapping("/{id}")
    public Response<LocationDto> getLocation(@PathVariable Long id) {
        Location location = locationService.findById(id);
        return new Response<>(locationMapper.createFrom(location));
    }

    @PostMapping
    public Response<LocationDto> addLocation(@RequestBody RequestDto requestDto) {
        Location location = locationService.save(requestDto);
        return new Response<>(locationMapper.createFrom(location));
    }

    @PutMapping
    public Response<LocationDto> updateLocation(@Valid @RequestBody LocationDto locationDto) {
        Location location = locationService.update(locationDto);
        return new Response<>(locationMapper.createFrom(location));
    }

    @DeleteMapping
    public Response<?> deleteLocation(@RequestBody Long locationId) {
        locationService.delete(locationId);
        return new Response<>("Location was successfully deleted");
    }
}
