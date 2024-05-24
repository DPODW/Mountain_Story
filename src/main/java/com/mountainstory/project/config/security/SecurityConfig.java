package com.mountainstory.project.config.security;

import com.mountainstory.project.config.auth.OAuthMemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuthMemberService OAuthMemberService;

    private final LoginAuthenticationEntryPoint loginAuthenticationEntryPoint;

    String[] publicForm = {"/home","/mountain/info/search/one/{mountainIndex}","/mountain/info/search/list","/member/access/check",

            "/member/reviewer/check",

            "/assets/css/**", "/assets/js/**", "/images/**","/assets/ajax/**"};


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity)throws Exception{
        httpSecurity
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer.disable())
                .authorizeHttpRequests(request -> request.requestMatchers(publicForm).permitAll().anyRequest().authenticated())
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->  httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(loginAuthenticationEntryPoint))
                .oauth2Login(httpSecurityOAuth2LoginConfigurer -> httpSecurityOAuth2LoginConfigurer
                        .successHandler(successHandler())
                        .failureHandler(failureHandler())
                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(OAuthMemberService)))
                .logout(logoutConfig -> logoutConfig.logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler()));
        return httpSecurity.build();
    }


    private AuthenticationSuccessHandler successHandler() {
        return (request, response, authentication) -> {
            redirectUrl(response);
        };
    }


    private AuthenticationFailureHandler failureHandler() {
        return (request, response, authentication) -> {
            redirectUrl(response);
        };
    }

    private LogoutSuccessHandler logoutSuccessHandler() {
        return (request, response, authentication) -> {
            redirectUrl(response);
        };
    }

    private static void redirectUrl(HttpServletResponse response) throws IOException {
        response.sendRedirect("/home");
    }



}
