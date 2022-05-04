package com.example.nc_spring_2022.controller;

import com.example.nc_spring_2022.dto.model.OrderDto;
import com.example.nc_spring_2022.dto.model.Response;
import com.example.nc_spring_2022.dto.model.SubscriptionOrderDto;
import com.example.nc_spring_2022.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {
    private final OrderService orderService;

    @PreAuthorize("hasRole('ROLE_CONSUMER')")
    @Operation(summary = "get orders for consumer")
    @GetMapping
    public Response<Page<SubscriptionOrderDto>> getOrdersForConsumer(Pageable pageable) {
        return new Response<>(orderService.getOrdersForConsumer(pageable));
    }

    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    @Operation(summary = "get orders for supplier")
    @GetMapping("/{subscriptionId}")
    public Response<Page<OrderDto>> getOrdersForSupplier(@PathVariable Long subscriptionId, Pageable pageable) {
        return new Response<>(orderService.getOrdersForSupplier(subscriptionId, pageable));
    }

    @PreAuthorize("hasRole('ROLE_CONSUMER')")
    @Operation(summary = "buy subscription")
    @PostMapping("/{subscriptionId}")
    public Response<SubscriptionOrderDto> createOrder(@PathVariable Long subscriptionId) {
        return new Response<>(orderService.save(subscriptionId));
    }

    @PreAuthorize("hasRole('ROLE_CONSUMER')")
    @Operation(summary = "cancel subscription")
    @DeleteMapping("/{orderId}")
    public Response<Void> deleteOrder(@PathVariable Long orderId) {
        orderService.delete(orderId);
        return new Response<>("Order was successfully canceled");
    }
}
