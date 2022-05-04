package com.example.nc_spring_2022.service;

import com.example.nc_spring_2022.dto.mapper.OrderMapper;
import com.example.nc_spring_2022.dto.mapper.SubscriptionOrderMapper;
import com.example.nc_spring_2022.dto.model.OrderDto;
import com.example.nc_spring_2022.dto.model.SubscriptionOrderDto;
import com.example.nc_spring_2022.exception.AuthorizationException;
import com.example.nc_spring_2022.exception.EntityAlreadyExistsException;
import com.example.nc_spring_2022.model.Order;
import com.example.nc_spring_2022.model.Subscription;
import com.example.nc_spring_2022.model.User;
import com.example.nc_spring_2022.repository.OrderRepository;
import com.example.nc_spring_2022.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final AuthenticationFacade authenticationFacade;
    private final UserService userService;
    private final OrderMapper orderMapper;
    private final SubscriptionOrderMapper subscriptionOrderMapper;
    private final SubscriptionService subscriptionService;

    public Page<SubscriptionOrderDto> getOrdersForConsumer(Pageable pageable) {
        Long userId = authenticationFacade.getUserId();
        Page<Order> orders = orderRepository.findAllByUserId(userId, pageable);
        return orders.map(subscriptionOrderMapper::createFrom);
    }

    public Page<OrderDto> getOrdersForSupplier(Long subscriptionId, Pageable pageable) {
        checkPermission(subscriptionId);
        Page<Order> orders = orderRepository.findAllBySubscriptionId(subscriptionId, pageable);
        return orders.map(orderMapper::createFrom);
    }

    public SubscriptionOrderDto save(Long subscriptionId) {
        Long userId = authenticationFacade.getUserId();
        User user = userService.findById(userId);
        Subscription subscription = subscriptionService.findById(subscriptionId);
        checkIsOrderExists(user, subscription);

        Order order = new Order();
        order.setSubscription(subscription);
        order.setUser(user);
        order = orderRepository.save(order);

        return subscriptionOrderMapper.createFrom(order);
    }

    private void checkIsOrderExists(User user, Subscription subscription) {
        Optional<Order> orderOptional = orderRepository.findByUserAndSubscription(user, subscription);
        if (orderOptional.isPresent()) {
            throw new EntityAlreadyExistsException("Order already exists");
        }
    }

    private void checkPermission(Long subscriptionId) {
        Long userId = authenticationFacade.getUserId();
        Subscription subscription = subscriptionService.findById(subscriptionId);
        if (!subscription.getSupplier().getId().equals(userId)) {
            throw new AuthorizationException("You can't see orders on another's subscriptions");
        }
    }

    public void delete(Long id) {
        orderRepository.deleteById(id);
    }
}
