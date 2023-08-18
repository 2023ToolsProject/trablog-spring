//package com.trablog.spring.webapps.config;
//
//import jakarta.servlet.DispatcherType;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//import static org.springframework.security.config.Customizer.withDefaults;
//
//@Configuration
//public class SecurityConfig {
//    @Bean
//    public PasswordEncoder getPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(request -> request
//                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
//                        .anyRequest().authenticated()) // 모든 요청에 인증 필요하게 함
//                .formLogin(login -> login // form 방식 로그인 사용
//                        .defaultSuccessUrl("/api/boards", true) // 성공시 게시판으로
//                        .permitAll() //
//                )
//                .logout(withDefaults());
//        return http.build();
//    }
//}
