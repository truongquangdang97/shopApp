package com.project.shopapp.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.project.shopapp.filters.JwtTokenFilters;

import lombok.RequiredArgsConstructor;

//
import com.project.shopapp.models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpMethod.*;

//

@Configuration
//@EnableMethodSecurity
@EnableWebSecurity
@EnableWebMvc
@RequiredArgsConstructor
public class WebSecurityConfig {

      // private static final String PUT = null;

      private final JwtTokenFilters jwtTokenFilters;

      @org.springframework.beans.factory.annotation.Value("${api.prefix}")
      private String apiPrefix;
      
      @Bean
      public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
            http
            .csrf(AbstractHttpConfigurer::disable)
            .addFilterBefore(jwtTokenFilters, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(requests->{
                  
                  requests
                  .requestMatchers(
                        String.format("%s/users/register", apiPrefix),
                        String.format("%s/users/login", apiPrefix)
                  )
                  .permitAll()
                  .requestMatchers(POST,
                              String.format("%s/order/**", apiPrefix)).hasAnyRole("USER","ADMIN") 
                  .requestMatchers(GET,
                        String.format("%s/order/**", apiPrefix)).hasAnyRole("USER","ADMIN")      
                  .requestMatchers(PUT,
                        String.format("%s/order/**", apiPrefix)).hasRole("ADMIN")
                  .requestMatchers(DELETE,
                        String.format("%s/order/**", apiPrefix)).hasRole("ADMIN") 

                  .requestMatchers(POST,
                        String.format("%s/products/**", apiPrefix)).hasRole("ADMIN")
                  .requestMatchers(GET,
                        String.format("%s/products/**", apiPrefix)).hasRole("ADMIN")
                  .requestMatchers(PUT,
                        String.format("%s/products/**", apiPrefix)).hasRole("ADMIN")
                  .requestMatchers(DELETE,
                        String.format("%s/products/**", apiPrefix)).hasRole("ADMIN") 

                   .anyRequest().authenticated();
                  
            });
            return http.build();
      }
}
