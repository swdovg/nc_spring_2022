package com.example.nc_spring_2022.controller;

import com.example.nc_spring_2022.dto.model.Response;
import com.example.nc_spring_2022.dto.model.SubscriptionDto;
import com.example.nc_spring_2022.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/subscription")
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @Operation(summary = "get subscription by id")
    @GetMapping("/{subscriptionId}")
    public Response<SubscriptionDto> getSubscription(@PathVariable Long subscriptionId) {
        return new Response<>(subscriptionService.getDtoById(subscriptionId));
    }

    @Operation(summary = "get subscriptions by category")
    @GetMapping("/category/{categoryId}")
    public Response<Page<SubscriptionDto>> getSubscriptionsByCategory(@PathVariable Long categoryId,
                                                                      Pageable pageable) {
        return new Response<>(subscriptionService.getDtosByCategoryId(categoryId, pageable));
    }

    @Operation(summary = "get all subscriptions")
    @GetMapping
    public Response<Page<SubscriptionDto>> getAllSubscriptions(Pageable pageable) {
        return new Response<>(subscriptionService.getAll(pageable));
    }

    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    @Operation(summary = "get supplier's subscriptions")
    @GetMapping("/supplier")
    public Response<Page<SubscriptionDto>> getSubscriptionsForSupplier(Pageable pageable) {
        return new Response<>(subscriptionService.getDtosBySupplier(pageable));
    }

    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    @Operation(summary = "create new subscription")
    @PostMapping
    public Response<SubscriptionDto> createSubscription(@Valid @RequestBody SubscriptionDto subscriptionDto) {
        return new Response<>(subscriptionService.save(subscriptionDto));
    }

    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    @Operation(summary = "update existing subscription")
    @PutMapping
    public Response<SubscriptionDto> updateSubscription(@Valid @RequestBody SubscriptionDto subscriptionDto) {
        return new Response<>(subscriptionService.save(subscriptionDto));
    }

    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    @Operation(summary = "delete existing subscription")
    @DeleteMapping("/{subscriptionId}")
    public Response<Void> deleteSubscription(@PathVariable Long subscriptionId) {
        subscriptionService.delete(subscriptionId);
        return new Response<>("Subscription was successfully deleted");
    }
}
