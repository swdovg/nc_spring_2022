package com.example.nc_spring_2022.dto.mapper;

import com.example.nc_spring_2022.dto.model.OrderDto;
import com.example.nc_spring_2022.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderMapper {
    private final UserMapper userMapper;

    public OrderDto createFrom(Order order) {
        OrderDto orderDto = new OrderDto();

        orderDto.setId(order.getId());
        orderDto.setConsumer(userMapper.createFrom(order.getUser()));
        orderDto.setSubscriptionId(order.getSubscription().getId());
        orderDto.setSubscriptionName(order.getSubscription().getTitle());

        return orderDto;
    }

    public List<OrderDto> createFrom(List<Order> orders) {
        List<OrderDto> orderDtos = new ArrayList<>();
        for (Order order : orders) {
            orderDtos.add(createFrom(order));
        }
        return orderDtos;
    }

    public Page<OrderDto> createPageFrom(List<Order> orders) {
        return new PageImpl<>(createFrom(orders));
    }
}
