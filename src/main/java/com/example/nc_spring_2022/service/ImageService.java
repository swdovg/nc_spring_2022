package com.example.nc_spring_2022.service;

import com.example.nc_spring_2022.dto.mapper.ImageMapper;
import com.example.nc_spring_2022.dto.model.ImageDto;
import com.example.nc_spring_2022.exception.AuthorizationException;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
                new EntityNotFoundException(String.format("Image with id: %d was not found", id))
        );
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
        newImage.setImageBytes(ImageUtility.compressImage(image.getBytes()));
        newImage.setRelatedEntityId(relatedEntityId);

        return save(newImage);
    }

    @Transactional
    public ImageDto saveUserImage(MultipartFile multipartFile) throws IOException {
        Long userId = authenticationFacade.getUserId();
        User user = userService.findById(userId);
        deleteOldImage(user.getImageId());

        Image newImage = save(multipartFile, userId);
        user.setImageUrl(getImageUrl(newImage));
        userService.save(user);

        return imageMapper.createFrom(newImage);
    }


    private void deleteOldImage(Long imageId) {
        if (imageId != null) {
            delete(imageId);
        }
    }

    @Transactional
    public ImageDto saveSubscriptionImage(MultipartFile multipartFile, Long subscriptionId) throws IOException {
        Subscription subscription = subscriptionService.findById(subscriptionId);
        checkPermissionsToUpdateSubscriptionImage(subscription);
        deleteOldImage(subscription.getImageId());

        Image newImage = save(multipartFile, subscriptionId);
        subscription.setImageUrl(getImageUrl(newImage));
        subscriptionService.save(subscription);

        return imageMapper.createFrom(newImage);
    }

    private void checkPermissionsToUpdateSubscriptionImage(Subscription subscription) {
        Long userId = authenticationFacade.getUserId();
        if (!subscription.getSupplier().getId().equals(userId)) {
            throw new AuthorizationException("You can not edit another's subscription");
        }
    }

    private String getImageUrl(Image image) {
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        String imagesEndpoint = baseUrl + "/api/v1/image/";
        return imagesEndpoint + image.getId();
    }

    public void delete(Long id) {
        imageRepository.deleteById(id);
    }
}
