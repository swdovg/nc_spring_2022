package com.example.nc_spring_2022.controller;

import com.example.nc_spring_2022.dto.model.Response;
import com.example.nc_spring_2022.dto.model.SubscriptionDto;
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
    public Response<Page<SubscriptionDto>> getOrders(Pageable pageable) {
        return new Response<>(orderService.getOrders(pageable));
    }

    @PostMapping("/{subscriptionId}")
    public Response<Void> createOrder(@PathVariable Long subscriptionId) {
        orderService.save(subscriptionId);
        return new Response<>("You successfully got this subscription");
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
