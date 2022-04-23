package com.example.nc_spring_2022.controller;

import com.example.nc_spring_2022.dto.model.LocationDto;
import com.example.nc_spring_2022.dto.model.RequestDto;
import com.example.nc_spring_2022.dto.model.Response;
import com.example.nc_spring_2022.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/location")
public class LocationController {
    private final LocationService locationService;

    @GetMapping("/{id}")
    public Response<LocationDto> getLocation(@PathVariable Long id) {
        return new Response<>(locationService.getDtoById(id));
    }

    @PostMapping
    public Response<LocationDto> addLocation(@RequestBody RequestDto requestDto) {
        return new Response<>(locationService.save(requestDto));
    }

    @PutMapping
    public Response<LocationDto> updateLocation(@Valid @RequestBody LocationDto locationDto) {
        return new Response<>(locationService.update(locationDto));
    }

    @DeleteMapping("/{id}")
    public Response<?> deleteLocation(@PathVariable Long id) {
        locationService.delete(id);
        return new Response<>("Location was successfully deleted");
    }
}
