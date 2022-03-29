package com.example.nc_spring_2022.model;

import com.example.nc_spring_2022.util.IdGenerator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.util.UUID;

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
    @NaturalId
    @Column(nullable = false, unique = true)
    private UUID businessKey = IdGenerator.createId();
    @Column(nullable = false)
    private String answer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FormData formData)) return false;

        return getBusinessKey().equals(formData.getBusinessKey());
    }

    @Override
    public int hashCode() {
        return getBusinessKey().hashCode();
    }
}
