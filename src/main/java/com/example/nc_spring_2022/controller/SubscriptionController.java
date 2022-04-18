package com.example.nc_spring_2022.controller;

import com.example.nc_spring_2022.dto.model.OrderDto;
import com.example.nc_spring_2022.dto.model.Response;
import com.example.nc_spring_2022.dto.model.SubscriptionDto;
import com.example.nc_spring_2022.dto.model.SubscriptionOrderDto;
import com.example.nc_spring_2022.service.OrderService;
import com.example.nc_spring_2022.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/subscription")
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;
    private final OrderService orderService;

    @GetMapping("/{subscriptionId}")
    public Response<SubscriptionDto> getSubscription(@PathVariable Long subscriptionId) {
        return new Response<>(subscriptionService.getDtoById(subscriptionId));
    }

    @GetMapping("/category/{categoryId}")
    public Response<Page<SubscriptionDto>> getSubscriptionsByCategory(@PathVariable Long categoryId,
                                                                      Pageable pageable) {
        return new Response<>(subscriptionService.getDtosByCategoryId(categoryId, pageable));
    }

    @GetMapping
    public Response<Page<SubscriptionOrderDto>> getOrdersForConsumer(Pageable pageable) {
        return new Response<>(orderService.getOrdersForConsumer(pageable));
    }

    @GetMapping("/order/{subscriptionId}")
    public Response<Page<OrderDto>> getOrdersForSupplier(@PathVariable Long subscriptionId, Pageable pageable) {
        return new Response<>(orderService.getOrdersForSupplier(subscriptionId, pageable));
    }

    @PostMapping("/{subscriptionId}")
    public Response<SubscriptionOrderDto> createOrder(@PathVariable Long subscriptionId) {
        return new Response<>(orderService.save(subscriptionId));
    }

    @PostMapping
    public Response<SubscriptionDto> createSubscription(@Valid @RequestBody SubscriptionDto subscriptionDto) {
        return new Response<>(subscriptionService.save(subscriptionDto));
    }

    @PutMapping
    public Response<SubscriptionDto> updateSubscription(@Valid @RequestBody SubscriptionDto subscriptionDto) {
        return new Response<>(subscriptionService.save(subscriptionDto));
    }
}
