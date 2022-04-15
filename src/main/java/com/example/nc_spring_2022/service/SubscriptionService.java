package com.example.nc_spring_2022.service;

import com.example.nc_spring_2022.dto.mapper.SubscriptionMapper;
import com.example.nc_spring_2022.dto.model.SubscriptionDto;
import com.example.nc_spring_2022.exception.AuthorizationException;
import com.example.nc_spring_2022.model.Subscription;
import com.example.nc_spring_2022.repository.SubscriptionRepository;
import com.example.nc_spring_2022.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;
    private final AuthenticationFacade authenticationFacade;

    public Subscription findById(Long id) {
        return subscriptionRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Subscription with id: %d was not found", id)));
    }

    public SubscriptionDto getDtoById(Long id) {
        return subscriptionMapper.createFrom(findById(id));
    }

    public Page<SubscriptionDto> getDtosByCategoryId(Long categoryId, Pageable pageable) {
        return subscriptionRepository.findAllByCategoryId(categoryId, pageable)
                .map(subscriptionMapper::createFrom);
    }

    public Subscription save(Subscription subscription) {
        checkPermission(subscription);
        return subscriptionRepository.save(subscription);
    }

    public SubscriptionDto save(SubscriptionDto subscriptionDto) {
        Subscription subscription = subscriptionMapper.createFrom(subscriptionDto);
        return subscriptionMapper.createFrom(save(subscription));
    }

    private void checkPermission(Subscription subscription) {
        Long userId = authenticationFacade.getUserId();

        verifyNewSubscriptionsOwner(subscription, userId);
        if (subscription.getId() != null) {
            verifyExistingSubscriptionsOwner(subscription, userId);
        }
    }

    private void verifyNewSubscriptionsOwner(Subscription subscription, Long userId) {
        if (!subscription.getSupplier().getId().equals(userId)) {
            throw new AuthorizationException("You can not edit another's subscription");
        }
    }

    private void verifyExistingSubscriptionsOwner(Subscription subscription, Long userId) {
        Subscription dbSubscription = findById(subscription.getId());
        if (!dbSubscription.getSupplier().getId().equals(userId)) {
            throw new AuthorizationException("You can not edit another's subscription");
        }
    }
}
