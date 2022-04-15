package com.example.nc_spring_2022.service;

import com.example.nc_spring_2022.dto.mapper.FeedbackMapper;
import com.example.nc_spring_2022.dto.model.FeedbackDto;
import com.example.nc_spring_2022.exception.EntityAlreadyExistsException;
import com.example.nc_spring_2022.model.Feedback;
import com.example.nc_spring_2022.model.Subscription;
import com.example.nc_spring_2022.repository.FeedbackRepository;
import com.example.nc_spring_2022.repository.SubscriptionRepository;
import com.example.nc_spring_2022.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;
    private final AuthenticationFacade authenticationFacade;
    private final SubscriptionRepository subscriptionRepository;

    public Page<Feedback> findBySubscriptionId(Long subscriptionId, Pageable pageable) {
        return feedbackRepository.findAllBySubscriptionId(subscriptionId, pageable);
    }

    public Page<FeedbackDto> getDtosBySubscriptionId(Long subscriptionId, Pageable pageable) {
        return findBySubscriptionId(subscriptionId, pageable).map(feedbackMapper::createFrom);
    }

    public boolean isExists(FeedbackDto feedbackDto) {
        Long userId = authenticationFacade.getUserId();
        return feedbackRepository.findByConsumerIdAndSubscriptionId(userId, feedbackDto.getSubscriptionId()).isPresent();
    }

    @Transactional
    public Feedback save(Feedback feedback) {
        updateAverageRating(feedback);
        return feedbackRepository.save(feedback);
    }

    private void updateAverageRating(Feedback feedback) {
        Subscription subscription = subscriptionRepository.getById(feedback.getSubscriptionId());
        subscription.setQuantityOfFeedbacks(subscription.getQuantityOfFeedbacks() + 1);
        subscription.setRatings(subscription.getRatings() + feedback.getRating());
        subscriptionRepository.save(subscription);
    }

    public FeedbackDto save(FeedbackDto feedbackDto) {
        if (isExists(feedbackDto)) {
            throw new EntityAlreadyExistsException("You have already posted your feedback on this subscription");
        }
        Feedback feedback = feedbackMapper.createFrom(feedbackDto);
        feedback = save(feedback);
        return feedbackMapper.createFrom(feedback);
    }

    public String delete(Long subscriptionId) {
        Long userId = authenticationFacade.getUserId();
        Optional<Feedback> optionalFeedback = feedbackRepository.findByConsumerIdAndSubscriptionId(userId, subscriptionId);
        if (optionalFeedback.isPresent()) {
            feedbackRepository.delete(optionalFeedback.get());
            return "Your feedback was successfully deleted";
        }
        return "Feedback was not found";
    }
}
