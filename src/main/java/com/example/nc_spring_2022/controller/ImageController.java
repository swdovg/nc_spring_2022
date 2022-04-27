package com.example.nc_spring_2022.controller;

import com.example.nc_spring_2022.dto.model.ImageDto;
import com.example.nc_spring_2022.dto.model.Response;
import com.example.nc_spring_2022.model.Image;
import com.example.nc_spring_2022.service.ImageService;
import com.example.nc_spring_2022.util.ImageUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.zip.DataFormatException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/image")
public class ImageController {
    private final ImageService imageService;

    @PostMapping("/user")
    public Response<ImageDto> uploadNewUserImage(@RequestParam("image") MultipartFile image) throws IOException {
        return new Response<>(imageService.saveUserImage(image));
    }

    @PostMapping("/subscription")
    public Response<ImageDto> uploadNewSubscriptionImage(@RequestParam("image") MultipartFile image,
                                                         @RequestParam("subscriptionId") Long subscriptionId)
            throws IOException {
        return new Response<>(imageService.saveSubscriptionImage(image, subscriptionId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) throws DataFormatException, IOException {
        Image image = imageService.findById(id);

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(image.getType()))
                .body(ImageUtility.decompressImage(image.getImageBytes()));
    }

    @GetMapping("/info/{id}")
    public Response<ImageDto> getInfo(@PathVariable Long id) {
        return new Response<>(imageService.getInfo(id));
    }
}
