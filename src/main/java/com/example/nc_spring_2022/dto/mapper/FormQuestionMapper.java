package com.example.nc_spring_2022.dto.mapper;

import com.example.nc_spring_2022.dto.model.FormQuestionDto;
import com.example.nc_spring_2022.model.FormQuestion;
import com.example.nc_spring_2022.model.Subscription;
import com.example.nc_spring_2022.repository.FormQuestionRepository;
import com.example.nc_spring_2022.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FormQuestionMapper {
    private final FormQuestionRepository formQuestionRepository;
    @Lazy
    private final SubscriptionService subscriptionService;

    public FormQuestionDto createFrom(FormQuestion formQuestion) {
        FormQuestionDto formQuestionDto = new FormQuestionDto();

        formQuestionDto.setId(formQuestion.getId());
        formQuestionDto.setSubscriptionId(formQuestion.getSubscription().getId());
        formQuestionDto.setQuestion(formQuestion.getQuestion());

        return formQuestionDto;
    }

    public List<FormQuestionDto> createFrom(List<FormQuestion> formQuestions) {
        List<FormQuestionDto> formQuestionDtos = new ArrayList<>();
        for (FormQuestion formQuestion : formQuestions) {
            formQuestionDtos.add(createFrom(formQuestion));
        }
        return formQuestionDtos;
    }

    public FormQuestion createFrom(FormQuestionDto formQuestionDto) {
        Subscription subscription = subscriptionService.findById(formQuestionDto.getSubscriptionId());
        FormQuestion formQuestion = getFormQuestionFromDbOrNew(formQuestionDto);

        formQuestion.setQuestion(formQuestionDto.getQuestion());
        formQuestion.setSubscription(subscription);

        return formQuestion;
    }

    private FormQuestion getFormQuestionFromDbOrNew(FormQuestionDto formQuestionDto) {
        if (formQuestionDto.getId() != null) {
            Optional<FormQuestion> optionalFormQuestion = formQuestionRepository.findById(formQuestionDto.getId());
            return optionalFormQuestion.orElseGet(FormQuestion::new);
        } else {
            return new FormQuestion();
        }
    }
}
