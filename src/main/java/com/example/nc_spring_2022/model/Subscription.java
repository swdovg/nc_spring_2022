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
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Long price;
    @Column
    @Enumerated(EnumType.STRING)
    private Currency currency = Currency.RUB;
    @Column
    private Double averageRating = 0.0;
    @OneToOne(targetEntity = Supplier.class)
    private Supplier supplier;
    @OneToOne(targetEntity = Category.class)
    private Category category;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subscription that)) return false;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getTitle() != null ? !getTitle().equals(that.getTitle()) : that.getTitle() != null) return false;
        if (getDescription() != null ? !getDescription().equals(that.getDescription()) : that.getDescription() != null)
            return false;
        if (getPrice() != null ? !getPrice().equals(that.getPrice()) : that.getPrice() != null) return false;
        if (getCurrency() != that.getCurrency()) return false;
        if (getAverageRating() != null ? !getAverageRating().equals(that.getAverageRating()) : that.getAverageRating() != null)
            return false;
        if (getSupplier() != null ? !getSupplier().equals(that.getSupplier()) : that.getSupplier() != null)
            return false;
        return getCategory() != null ? getCategory().equals(that.getCategory()) : that.getCategory() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getPrice() != null ? getPrice().hashCode() : 0);
        result = 31 * result + (getCurrency() != null ? getCurrency().hashCode() : 0);
        result = 31 * result + (getAverageRating() != null ? getAverageRating().hashCode() : 0);
        result = 31 * result + (getSupplier() != null ? getSupplier().hashCode() : 0);
        result = 31 * result + (getCategory() != null ? getCategory().hashCode() : 0);
        return result;
    }
}
