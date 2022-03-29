package com.example.nc_spring_2022.model;

import com.example.nc_spring_2022.util.IdGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Supplier implements User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NaturalId
    @Column(nullable = false, unique = true)
    private UUID businessKey = IdGenerator.createId();
    @Column(nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String phoneNumber;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column
    @Enumerated(EnumType.STRING)
    private Currency currency = Currency.RUB;
    @Column
    private String location;
    @Column
    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_SUPPLIER;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Supplier supplier)) return false;

        return getBusinessKey().equals(supplier.getBusinessKey());
    }

    @Override
    public int hashCode() {
        return getBusinessKey().hashCode();
    }
}
