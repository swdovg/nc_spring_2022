package com.example.nc_spring_2022.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@IdClass(FormDataId.class)
public class FormData {
    @Id
    private Long formQuestionId;
    @Id
    private Long orderId;
    @Column(nullable = false)
    private String answer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FormData formData)) return false;

        if (getFormQuestionId() != null ? !getFormQuestionId().equals(formData.getFormQuestionId()) : formData.getFormQuestionId() != null)
            return false;
        if (getOrderId() != null ? !getOrderId().equals(formData.getOrderId()) : formData.getOrderId() != null)
            return false;
        return getAnswer() != null ? getAnswer().equals(formData.getAnswer()) : formData.getAnswer() == null;
    }

    @Override
    public int hashCode() {
        int result = getFormQuestionId() != null ? getFormQuestionId().hashCode() : 0;
        result = 31 * result + (getOrderId() != null ? getOrderId().hashCode() : 0);
        result = 31 * result + (getAnswer() != null ? getAnswer().hashCode() : 0);
        return result;
    }
}
