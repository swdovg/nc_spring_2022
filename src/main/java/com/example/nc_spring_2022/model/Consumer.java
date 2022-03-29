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
@Embeddable
public class Consumer implements User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NaturalId
    @Column(nullable = false, unique = true)
    private UUID businessKey = IdGenerator.createId();
    @Column(nullable = false)
    private String name;
    @Column(unique = true)
    private String phoneNumber;
    @Column(unique = true, nullable = false)
    private String email;
    @Column
    private String password;
    @Column
    @Enumerated(EnumType.STRING)
    private Currency currency = Currency.RUB;
    @OneToOne(targetEntity = Location.class)
    private Location defaultLocation;
    @Column
    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_CONSUMER;
    @Column
    private String providerId;
    @Column
    @Enumerated(EnumType.STRING)
    private AuthProvider provider = AuthProvider.LOCAL;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Consumer consumer)) return false;

        return getBusinessKey().equals(consumer.getBusinessKey());
    }

    @Override
    public int hashCode() {
        return getBusinessKey().hashCode();
    }
}
