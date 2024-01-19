package com.demotwosecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder){                      //создаем пользователей с паролем и зашифровываем для БД
        UserDetails admin = User.builder().username("admin").password(encoder.encode("admin")).roles("ADMIN").build();
        UserDetails userOne = User.builder().username("userOne").password(encoder.encode("userOne")).roles("USERONE").build();
        UserDetails userTwo = User.builder().username("userTwo").password(encoder.encode("userTwo")).roles("ADMIN", "USERTWO").build();

        return new InMemoryUserDetailsManager(admin, userOne, userTwo);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){           //шифрует для userDetailsService
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {            //ставим защиту от csrf атак, задаю контрольную точку, которая видна всем, а остальные прячу. + даю всем доступ на авторизацию
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.requestMatchers("api/v1/apps/welcome").permitAll()
                        .requestMatchers("api/v1/apps/**").authenticated())             //область видимости для авторизованых пользователей
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll).build();
    }



}
