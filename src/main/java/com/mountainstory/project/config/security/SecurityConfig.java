package com.mountainstory.project.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    String[] publicForm = {"/home","/home/result",

            "/assets/css/**", "/assets/js/**", "/images/**"};


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity)throws Exception{
        httpSecurity
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer.disable())
                .authorizeHttpRequests(request -> request.requestMatchers(publicForm).permitAll().anyRequest().authenticated())
                .oauth2Login();
        return httpSecurity.build();
    }


    @Bean
    public UserDetailsService userDetailsService() {
        //TODO:시큐리티 비밀번호 자동 설정을 방지하기 위한 UserDetail 생성 (추후 제거할것)
        return new InMemoryUserDetailsManager();
    }

}
