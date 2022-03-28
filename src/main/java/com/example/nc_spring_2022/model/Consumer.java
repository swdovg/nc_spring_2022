package com.example.nc_spring_2022.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Embeddable
public class Consumer implements User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
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

        if (getId() != null ? !getId().equals(consumer.getId()) : consumer.getId() != null) return false;
        if (getName() != null ? !getName().equals(consumer.getName()) : consumer.getName() != null) return false;
        if (getPhoneNumber() != null ? !getPhoneNumber().equals(consumer.getPhoneNumber()) : consumer.getPhoneNumber() != null)
            return false;
        if (getEmail() != null ? !getEmail().equals(consumer.getEmail()) : consumer.getEmail() != null) return false;
        if (getPassword() != null ? !getPassword().equals(consumer.getPassword()) : consumer.getPassword() != null)
            return false;
        if (getCurrency() != consumer.getCurrency()) return false;
        if (getDefaultLocation() != null ? !getDefaultLocation().equals(consumer.getDefaultLocation()) : consumer.getDefaultLocation() != null)
            return false;
        if (getRole() != consumer.getRole()) return false;
        if (getProviderId() != null ? !getProviderId().equals(consumer.getProviderId()) : consumer.getProviderId() != null)
            return false;
        return getProvider() == consumer.getProvider();
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getPhoneNumber() != null ? getPhoneNumber().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        result = 31 * result + (getCurrency() != null ? getCurrency().hashCode() : 0);
        result = 31 * result + (getDefaultLocation() != null ? getDefaultLocation().hashCode() : 0);
        result = 31 * result + (getRole() != null ? getRole().hashCode() : 0);
        result = 31 * result + (getProviderId() != null ? getProviderId().hashCode() : 0);
        result = 31 * result + (getProvider() != null ? getProvider().hashCode() : 0);
        return result;
    }
}
