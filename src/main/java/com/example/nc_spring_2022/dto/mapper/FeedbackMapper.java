package com.example.nc_spring_2022.dto.mapper;

import com.example.nc_spring_2022.dto.model.FeedbackDto;
import com.example.nc_spring_2022.model.Feedback;
import com.example.nc_spring_2022.model.User;
import com.example.nc_spring_2022.repository.FeedbackRepository;
import com.example.nc_spring_2022.security.AuthenticationFacade;
import com.example.nc_spring_2022.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FeedbackMapper {
    private final FeedbackRepository feedbackRepository;
    @Lazy
    private final UserService userService;
    private final AuthenticationFacade authenticationFacade;

    public FeedbackDto createFrom(Feedback feedback) {
        User user = userService.findById(feedback.getConsumerId());
        FeedbackDto feedbackDto = new FeedbackDto();

        feedbackDto.setTitle(feedback.getTitle());
        feedbackDto.setText(feedback.getText());
        feedbackDto.setRating(feedback.getRating());
        feedbackDto.setUserName(user.getName());
        feedbackDto.setSubscriptionId(feedback.getSubscriptionId());

        return feedbackDto;
    }

    public List<FeedbackDto> createFrom(List<Feedback> feedbacks) {
        List<FeedbackDto> feedbackDtos = new ArrayList<>();
        for (Feedback feedback : feedbacks) {
            feedbackDtos.add(createFrom(feedback));
        }
        return feedbackDtos;
    }

    public Feedback createFrom(FeedbackDto feedbackDto) {
        Long userId = authenticationFacade.getUserId();

        Optional<Feedback> optionalFeedback =
                feedbackRepository.findByConsumerIdAndSubscriptionId(userId, feedbackDto.getSubscriptionId());
        Feedback feedback = optionalFeedback.orElseGet(Feedback::new);

        feedback.setTitle(feedbackDto.getTitle());
        feedback.setText(feedbackDto.getText());
        feedback.setRating(feedbackDto.getRating());
        feedback.setSubscriptionId(feedbackDto.getSubscriptionId());
        feedback.setConsumerId(userId);

        return feedback;
    }
}
