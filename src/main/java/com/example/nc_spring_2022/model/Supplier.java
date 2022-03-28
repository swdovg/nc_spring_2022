package com.example.nc_spring_2022.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Supplier implements User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
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

        if (getId() != null ? !getId().equals(supplier.getId()) : supplier.getId() != null) return false;
        if (getName() != null ? !getName().equals(supplier.getName()) : supplier.getName() != null) return false;
        if (getPhoneNumber() != null ? !getPhoneNumber().equals(supplier.getPhoneNumber()) : supplier.getPhoneNumber() != null)
            return false;
        if (getEmail() != null ? !getEmail().equals(supplier.getEmail()) : supplier.getEmail() != null) return false;
        if (getPassword() != null ? !getPassword().equals(supplier.getPassword()) : supplier.getPassword() != null)
            return false;
        if (getCurrency() != supplier.getCurrency()) return false;
        return getLocation() != null ? getLocation().equals(supplier.getLocation()) : supplier.getLocation() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getPhoneNumber() != null ? getPhoneNumber().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        result = 31 * result + (getCurrency() != null ? getCurrency().hashCode() : 0);
        result = 31 * result + (getLocation() != null ? getLocation().hashCode() : 0);
        return result;
    }
}
