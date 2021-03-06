package com.example.nc_spring_2022.controller;

import com.example.nc_spring_2022.dto.model.FormQuestionDto;
import com.example.nc_spring_2022.dto.model.Response;
import com.example.nc_spring_2022.model.FormData;
import com.example.nc_spring_2022.service.FormService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/form")
public class FormController {
    private final FormService formService;

    @GetMapping("/answer/{orderId}")
    public Response<List<FormData>> getAnswers(@PathVariable Long orderId) {
        return new Response<>(formService.findFormData(orderId));
    }

    @GetMapping("/question/{subscriptionId}")
    public Response<List<FormQuestionDto>> getQuestions(@PathVariable Long subscriptionId) {
        return new Response<>(formService.getFormQuestionDtos(subscriptionId));
    }

    @PreAuthorize("hasRole('ROLE_CONSUMER')")
    @PostMapping("/answer")
    public Response<FormData> postAnswer(@RequestBody FormData formData) {
        return new Response<>(formService.saveAnswer(formData));
    }

    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    @PostMapping("/question")
    public Response<FormQuestionDto> postQuestion(@RequestBody FormQuestionDto formQuestionDto) {
        return new Response<>(formService.saveQuestion(formQuestionDto));
    }
}
