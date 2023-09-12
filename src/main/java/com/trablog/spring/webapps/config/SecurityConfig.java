package com.trablog.spring.webapps.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {
    // 사용자가 입력한 username으로 사용자를 인증하는 객체.
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    private final JwtTokenProvider jwtTokenProvider;

    // authenticationManager를 Bean 등록합니다.
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic(HttpBasicConfigurer::disable)
                .csrf(CsrfConfigurer::disable)
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize ->
                        authorize.requestMatchers("/", "/auth/**", "/webjars/**", "/js/**", "/image/**").permitAll()
                // 나머저 모든 요청은 인증을 해야한다.
//              .anyRequest().hasRole(String.valueOf(RoleType.USER)
                );
        return httpSecurity.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers( "/api-docs/**","/v2/api-docs/**", "/v3/api-docs/**", "/swagger-resources/**",
                "/swagger-ui/**", "/webjars/**", "/swagger/**", "/sign-api/exception", "/error");
    }
}
