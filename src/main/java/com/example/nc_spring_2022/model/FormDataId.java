package com.example.nc_spring_2022.model;

import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

public class FormDataId implements Serializable {
    private Long formQuestionId;
    private Long orderId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        FormDataId that = (FormDataId) o;
        return formQuestionId != null && Objects.equals(formQuestionId, that.formQuestionId)
                && orderId != null && Objects.equals(orderId, that.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(formQuestionId, orderId);
    }
}
