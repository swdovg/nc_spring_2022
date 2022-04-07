package com.example.nc_spring_2022;

import com.example.nc_spring_2022.repository.NaturalRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = NaturalRepositoryImpl.class)
public class NcSpring2022Application {

    public static void main(String[] args) {
        SpringApplication.run(NcSpring2022Application.class, args);
    }

}
