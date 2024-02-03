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

    String[] publicForm = {"/home","/home/result","/mountain/info/search",

            "/assets/css/**", "/assets/js/**", "/images/**"};


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity)throws Exception{
        httpSecurity
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer.disable())
                .authorizeHttpRequests(request -> request.requestMatchers(publicForm).permitAll().anyRequest().authenticated())
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

    @Bean
    public UserDetailsService userDetailsService() {
        //TODO:시큐리티 비밀번호 자동 설정을 방지하기 위한 UserDetail 생성 (추후 제거할것)
        return new InMemoryUserDetailsManager();
    }

}
