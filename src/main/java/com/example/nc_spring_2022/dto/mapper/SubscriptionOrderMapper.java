package com.example.nc_spring_2022.dto.mapper;

import com.example.nc_spring_2022.dto.model.SubscriptionOrderDto;
import com.example.nc_spring_2022.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubscriptionOrderMapper {
    private final SubscriptionMapper subscriptionMapper;

    public SubscriptionOrderDto createFrom(Order order) {
        SubscriptionOrderDto subscriptionOrderDto = new SubscriptionOrderDto();

        subscriptionOrderDto.setSubscription(subscriptionMapper.createFrom(order.getSubscription()));
        subscriptionOrderDto.setOrderId(order.getId());
        subscriptionOrderDto.setDate(order.getDate());

        return subscriptionOrderDto;
    }
}
