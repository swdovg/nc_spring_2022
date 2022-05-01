package com.example.nc_spring_2022.controller;

import com.example.nc_spring_2022.dto.model.FeedbackDto;
import com.example.nc_spring_2022.dto.model.Response;
import com.example.nc_spring_2022.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/feedback")
public class FeedbackController {
    private final FeedbackService feedbackService;

    @GetMapping("/{subscriptionId}")
    public Response<Page<FeedbackDto>> getFeedbacksForSubscription(@PathVariable Long subscriptionId,
                                                                   Pageable pageable) {
        return new Response<>(feedbackService.getDtosBySubscriptionId(subscriptionId, pageable));
    }

    @RolesAllowed("CONSUMER")
    @PostMapping
    public Response<FeedbackDto> createFeedback(@Valid @RequestBody FeedbackDto feedbackDto) {
        return new Response<>(feedbackService.save(feedbackDto));
    }

    @RolesAllowed("CONSUMER")
    @DeleteMapping("/{subscriptionId}")
    public Response<Void> deleteFeedback(@PathVariable Long subscriptionId) {
        return new Response<>(feedbackService.delete(subscriptionId));
    }
}
