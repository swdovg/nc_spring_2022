package com.example.nc_spring_2022.service;

import com.example.nc_spring_2022.dto.mapper.OrderMapper;
import com.example.nc_spring_2022.dto.mapper.SubscriptionMapper;
import com.example.nc_spring_2022.dto.model.OrderDto;
import com.example.nc_spring_2022.dto.model.SubscriptionDto;
import com.example.nc_spring_2022.exception.AuthorizationException;
import com.example.nc_spring_2022.model.Order;
import com.example.nc_spring_2022.model.Subscription;
import com.example.nc_spring_2022.model.User;
import com.example.nc_spring_2022.repository.OrderRepository;
import com.example.nc_spring_2022.repository.SubscriptionRepository;
import com.example.nc_spring_2022.repository.UserRepository;
import com.example.nc_spring_2022.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final AuthenticationFacade authenticationFacade;
    private final SubscriptionMapper subscriptionMapper;
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final OrderMapper orderMapper;

    public Page<SubscriptionDto> getOrders(Pageable pageable) {
        Long userId = authenticationFacade.getUserId();
        Page<Order> orders = orderRepository.findAllByUserId(userId, pageable);
        return mapPageOfOrdersToPageOfSubscriptions(orders).map(subscriptionMapper::createFrom);
    }

    private Page<Subscription> mapPageOfOrdersToPageOfSubscriptions(Page<Order> orders) {
        List<Subscription> subscriptions = orders.getContent().stream().map(Order::getSubscription).toList();
        return new PageImpl<>(subscriptions, orders.getPageable(), orders.getTotalElements());
    }

    public Page<OrderDto> getOrdersForSupplier(Long subscriptionId, Pageable pageable) {
        checkPermission(subscriptionId);
        Page<Order> orders = orderRepository.findAllBySubscriptionId(subscriptionId, pageable);
        return orders.map(orderMapper::createFrom);
    }

    public Order save(Long subscriptionId) {
        User user = userRepository.getById(authenticationFacade.getUserId());
        Subscription subscription = subscriptionRepository.getById(subscriptionId);

        Order order = new Order();
        order.setSubscription(subscription);
        order.setUser(user);

        Optional<Order> orderOptional = orderRepository.findByUserAndSubscription(user, subscription);

        return orderOptional.orElseGet(() -> orderRepository.save(order));
    }

    private void checkPermission(Long subscriptionId) {
        Long userId = authenticationFacade.getUserId();
        Subscription subscription = subscriptionRepository.getById(subscriptionId);
        if (!subscription.getSupplier().getId().equals(userId)) {
            throw new AuthorizationException("You can't see orders on another's subscriptions");
        }
    }
}
