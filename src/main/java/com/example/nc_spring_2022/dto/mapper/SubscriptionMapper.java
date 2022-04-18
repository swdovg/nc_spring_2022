package com.example.nc_spring_2022.dto.mapper;

import com.example.nc_spring_2022.dto.model.SubscriptionDto;
import com.example.nc_spring_2022.exception.WrongCredentialsException;
import com.example.nc_spring_2022.model.Subscription;
import com.example.nc_spring_2022.repository.OrderRepository;
import com.example.nc_spring_2022.repository.SubscriptionRepository;
import com.example.nc_spring_2022.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SubscriptionMapper {
    private final SubscriptionRepository subscriptionRepository;
    private final CategoryMapper categoryMapper;
    private final UserMapper userMapper;
    private final OrderRepository orderRepository;
    private final AuthenticationFacade authenticationFacade;

    public SubscriptionDto createFrom(Subscription subscription) {
        SubscriptionDto subscriptionDto = new SubscriptionDto();

        subscriptionDto.setId(subscription.getId());
        subscriptionDto.setTitle(subscription.getTitle());
        subscriptionDto.setDescription(subscription.getDescription());
        subscriptionDto.setCategory(categoryMapper.createFrom(subscription.getCategory()));
        subscriptionDto.setCurrency(subscription.getCurrency());
        subscriptionDto.setPrice(subscription.getPrice());
        subscriptionDto.setSupplier(userMapper.createFrom(subscription.getSupplier()));
        if (subscription.getQuantityOfFeedbacks() == 0) {
            subscriptionDto.setAverageRating(0.0);
        } else {
            subscriptionDto.setAverageRating((double) (subscription.getRatings() / subscription.getQuantityOfFeedbacks()));
        }
        setOrdered(subscriptionDto);

        return subscriptionDto;
    }

    private void setOrdered(SubscriptionDto subscriptionDto) {
        try {
            Long userId = authenticationFacade.getUserId();
            subscriptionDto.setOrdered(orderRepository.findByUserIdAndSubscriptionId(userId, subscriptionDto.getId()).isPresent());
        } catch (WrongCredentialsException ignored) {
        }
    }

    public List<SubscriptionDto> createFrom(List<Subscription> subscriptions) {
        List<SubscriptionDto> subscriptionDtos = new ArrayList<>();
        for (Subscription subscription : subscriptions) {
            subscriptionDtos.add(createFrom(subscription));
        }
        return subscriptionDtos;
    }

    public Subscription createFrom(SubscriptionDto subscriptionDto) {
        Subscription subscription;
        if (subscriptionDto.getId() != null) {
            subscription = subscriptionRepository.findById(subscriptionDto.getId()).orElseThrow(() ->
                    new EntityNotFoundException(String.format("Subscription with id: %d was not found",
                            subscriptionDto.getId())));
        } else {
            subscription = new Subscription();
        }
        subscription.setTitle(subscriptionDto.getTitle());
        subscription.setDescription(subscriptionDto.getDescription());
        subscription.setCategory(categoryMapper.createFrom(subscriptionDto.getCategory()));
        subscription.setSupplier(userMapper.createFrom(subscriptionDto.getSupplier()));
        subscription.setCurrency(subscriptionDto.getCurrency());
        subscription.setPrice(subscriptionDto.getPrice());
        return subscription;
    }
}
