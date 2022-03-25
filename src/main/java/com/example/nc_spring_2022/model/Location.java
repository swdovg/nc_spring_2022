package com.example.nc_spring_2022.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(targetEntity = Consumer.class)
    private Consumer consumer;
    @Column(nullable = false)
    private String location;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location location1)) return false;

        if (getId() != null ? !getId().equals(location1.getId()) : location1.getId() != null) return false;
        if (getConsumer() != null ? !getConsumer().equals(location1.getConsumer()) : location1.getConsumer() != null)
            return false;
        return getLocation() != null ? getLocation().equals(location1.getLocation()) : location1.getLocation() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getConsumer() != null ? getConsumer().hashCode() : 0);
        result = 31 * result + (getLocation() != null ? getLocation().hashCode() : 0);
        return result;
    }
}
