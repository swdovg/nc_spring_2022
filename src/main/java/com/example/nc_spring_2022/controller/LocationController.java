package com.example.nc_spring_2022.controller;

import com.example.nc_spring_2022.dto.Response;
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

    @GetMapping("/{id}")
    public Response<Location> getLocation(@PathVariable Long id) {
        return new Response<>(locationService.findById(id));
    }

    @PostMapping
    public Response<Location> addLocation(@RequestBody Location location) {
        return new Response<>(locationService.save(location));
    }

    @PutMapping
    public Response<Location> updateLocation(@RequestBody Location location) {
        return new Response<>(locationService.save(location));
    }

    @DeleteMapping
    public Response<?> deleteLocation(@Valid @RequestBody Location location) {
        locationService.delete(location);
        return new Response<>("Location was successfully deleted");
    }
}
