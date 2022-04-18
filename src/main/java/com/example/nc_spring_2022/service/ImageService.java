package com.example.nc_spring_2022.service;

import com.example.nc_spring_2022.dto.mapper.ImageMapper;
import com.example.nc_spring_2022.dto.model.ImageDto;
import com.example.nc_spring_2022.model.Image;
import com.example.nc_spring_2022.model.Subscription;
import com.example.nc_spring_2022.model.User;
import com.example.nc_spring_2022.repository.ImageRepository;
import com.example.nc_spring_2022.security.AuthenticationFacade;
import com.example.nc_spring_2022.util.ImageUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;


@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;
    private final AuthenticationFacade authenticationFacade;
    private final UserService userService;
    private final SubscriptionService subscriptionService;

    public Image findById(Long id) {
        return imageRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Image with id: %d was not found", id)));
    }

    public ImageDto getInfo(Long id) {
        Image image = findById(id);
        return imageMapper.createFrom(image);
    }

    public Image save(Image image) {
        return imageRepository.save(image);
    }

    public Image save(MultipartFile image, Long relatedEntityId) throws IOException {
        Image newImage = new Image();

        newImage.setName(image.getOriginalFilename());
        newImage.setType(image.getContentType());
        newImage.setImage(ImageUtility.compressImage(image.getBytes()));
        newImage.setRelatedEntityId(relatedEntityId);

        return save(newImage);
    }

    @Transactional
    public ImageDto saveUserImage(MultipartFile multipartFile) throws IOException {
        Long userId = authenticationFacade.getUserId();
        User user = userService.findById(userId);
        deleteOldImage(user.getImage());

        Image newImage = save(multipartFile, userId);
        user.setImage(newImage);
        userService.save(user);

        return imageMapper.createFrom(newImage);
    }

    @Transactional
    public ImageDto saveSubscriptionImage(MultipartFile multipartFile, Long subscriptionId) throws IOException {
        Subscription subscription = subscriptionService.findById(subscriptionId);
        deleteOldImage(subscription.getImage());

        System.out.println(subscriptionId.toString());
        Image newImage = save(multipartFile, subscriptionId);
        subscription.setImage(newImage);
        subscriptionService.save(subscription);

        return imageMapper.createFrom(newImage);
    }

    private void deleteOldImage(Image image) {
        if (image != null) {
            delete(image);
        }
    }

    public void delete(Image image) {
        imageRepository.delete(image);
    }
}
