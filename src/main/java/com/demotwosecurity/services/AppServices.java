package com.demotwosecurity.services;

import com.demotwosecurity.models.Application;
import com.github.javafaker.Faker;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

import static com.demotwosecurity.models.Application.*;

@Service
public class AppServices {


    private List<Application> applications;

    @PostConstruct
    public void loadAppInDB(){
        Faker faker = new Faker();

        applications = IntStream.rangeClosed(1,100)
                .mapToObj(i -> builder().id(i)
                .name(faker.app().name())
                .author(faker.app().author())
                        .version(faker.app().version()).build()).toList();

//        applications = IntStream.rangeClosed(1,100).mapToObj(i -> builder().id(i).name(faker.app().name()).author(faker.app().author()).version(faker.app().version()).build()).toList();

    }


    public List<Application> allApplications(){
        return applications;
    }


    public Application applicationByID(int id){

        return applications.stream()
                .filter(app -> app.getId() == id)
                .findFirst()
                .orElse(null);
    }

}
