package com.project.shopapp.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.project.shopapp.models.User;
import com.project.shopapp.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

      private final UserRepository userRepository;
      @Bean
      public UserDetailsService userDetailsService(){
            return phoneNumber->{
                  User existingUser =  userRepository
                  .findByPhoneNumber(phoneNumber)
                  .orElseThrow(()->
                  new UsernameNotFoundException("Cannot find user phone with = " +phoneNumber));
                  return existingUser;
            }; 
      }

      @Bean
      public PasswordEncoder passwordEncoder(){
           
            return new BCryptPasswordEncoder();
      }
      @Bean
      public AuthenticationProvider authenticationProvider(){
            DaoAuthenticationProvider authenProvider = new DaoAuthenticationProvider();
            authenProvider.setUserDetailsService(userDetailsService());
            authenProvider.setPasswordEncoder(passwordEncoder());
            return authenProvider;
      }

      @Bean
      public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
            return config.getAuthenticationManager();
      }
}
