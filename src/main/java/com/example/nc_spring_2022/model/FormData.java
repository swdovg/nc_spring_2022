package com.example.nc_spring_2022.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.util.Objects;

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
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        FormData formData = (FormData) o;
        return formQuestionId != null && Objects.equals(formQuestionId, formData.formQuestionId)
                && orderId != null && Objects.equals(orderId, formData.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(formQuestionId, orderId);
    }
}
