package com.example.nc_spring_2022.dto.mapper;

import com.example.nc_spring_2022.dto.model.SubscriptionDto;
import com.example.nc_spring_2022.exception.WrongCredentialsException;
import com.example.nc_spring_2022.model.Subscription;
import com.example.nc_spring_2022.repository.OrderRepository;
import com.example.nc_spring_2022.security.AuthenticationFacade;
import com.example.nc_spring_2022.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SubscriptionMapper {
    private final CategoryMapper categoryMapper;
    private final UserMapper userMapper;
    private final OrderRepository orderRepository;
    private final AuthenticationFacade authenticationFacade;
    @Lazy
    private final SubscriptionService subscriptionService;

    public SubscriptionDto createFrom(Subscription subscription) {
        SubscriptionDto subscriptionDto = new SubscriptionDto();

        subscriptionDto.setId(subscription.getId());
        subscriptionDto.setTitle(subscription.getTitle());
        subscriptionDto.setDescription(subscription.getDescription());
        subscriptionDto.setCategory(categoryMapper.createFrom(subscription.getCategory()));
        subscriptionDto.setCurrency(subscription.getCurrency());
        subscriptionDto.setPrice(subscription.getPrice());
        subscriptionDto.setSupplier(userMapper.createFrom(subscription.getSupplier()));
        subscriptionDto.setAverageRating(getAverageRating(subscription));
        setOrdered(subscriptionDto);

        return subscriptionDto;
    }

    private double getAverageRating(Subscription subscription) {
        if (subscription.getQuantityOfFeedbacks() == 0) {
            return 0.0;
        } else {
            return (double) (subscription.getRatings() / subscription.getQuantityOfFeedbacks());
        }
    }

    private void setOrdered(SubscriptionDto subscriptionDto) {
        try {
            Long userId = authenticationFacade.getUserId();
            subscriptionDto.setOrdered(
                    orderRepository.findByUserIdAndSubscriptionId(
                            userId,
                            subscriptionDto.getId()
                    ).isPresent()
            );
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
        Subscription subscription = getSubscriptionFromDbOrNew(subscriptionDto);

        subscription.setTitle(subscriptionDto.getTitle());
        subscription.setDescription(subscriptionDto.getDescription());
        subscription.setCategory(categoryMapper.createFrom(subscriptionDto.getCategory()));
        subscription.setSupplier(userMapper.createFrom(subscriptionDto.getSupplier()));
        subscription.setCurrency(subscriptionDto.getCurrency());
        subscription.setPrice(subscriptionDto.getPrice());

        return subscription;
    }

    private Subscription getSubscriptionFromDbOrNew(SubscriptionDto subscriptionDto) {
        if (subscriptionDto.getId() != null) {
            return subscriptionService.findById(subscriptionDto.getId());
        } else {
            return new Subscription();
        }
    }
}
