package com.example.nc_spring_2022.service;

import com.example.nc_spring_2022.dto.mapper.FormQuestionMapper;
import com.example.nc_spring_2022.dto.model.FormQuestionDto;
import com.example.nc_spring_2022.exception.AuthorizationException;
import com.example.nc_spring_2022.model.FormData;
import com.example.nc_spring_2022.model.FormQuestion;
import com.example.nc_spring_2022.repository.FormDataRepository;
import com.example.nc_spring_2022.repository.FormQuestionRepository;
import com.example.nc_spring_2022.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FormService {
    private final FormDataRepository formDataRepository;
    private final FormQuestionRepository formQuestionRepository;
    private final FormQuestionMapper formQuestionMapper;
    private final AuthenticationFacade authenticationFacade;

    public List<FormQuestion> findFormQuestions(Long subscriptionId) {
        return formQuestionRepository.findAllBySubscriptionId(subscriptionId);
    }

    public List<FormQuestionDto> getFormQuestionDtos(Long subscriptionId) {
        return formQuestionMapper.createFrom(findFormQuestions(subscriptionId));
    }

    public List<FormData> findFormData(Long orderId) {
        return formDataRepository.findAllByOrderId(orderId);
    }

    public FormQuestion saveQuestion(FormQuestion formQuestion) {
        return formQuestionRepository.save(formQuestion);
    }

    public FormQuestionDto saveQuestion(FormQuestionDto formQuestionDto) {
        FormQuestion formQuestion = formQuestionMapper.createFrom(formQuestionDto);
        checkPermission(formQuestion);
        return formQuestionMapper.createFrom(saveQuestion(formQuestion));
    }

    public FormData saveAnswer(FormData formData) {
        return formDataRepository.save(formData);
    }

    private void checkPermission(FormQuestion formQuestion) {
        Long userId = authenticationFacade.getUserId();
        if (!formQuestion.getSubscription().getSupplier().getId().equals(userId)) {
            throw new AuthorizationException("You cann't add question to another's subscription");
        }
    }
}
