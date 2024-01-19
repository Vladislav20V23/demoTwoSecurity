package com.demotwosecurity.controllers;

import com.demotwosecurity.models.Application;
import com.demotwosecurity.services.AppServices;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/apps")
@AllArgsConstructor
public class AppController {

    private AppServices services;

    @GetMapping("/welcome")
    public String welcome(){
        return "<h1>Welcome to my web</h1>";
    }

    @GetMapping("/all-app")
    @PreAuthorize("hasAuthority('ROLE_USERONE') && hasAuthority('ROLE_ADMIN')")
    public List<Application> allAplications(){
        return services.allApplications();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USERTWO')")
    public Application getApplication(@PathVariable int id){
        return services.applicationByID(id);
    }



}
