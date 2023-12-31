package com.trablog.spring.webapps.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken(servletRequest); // 요청에서 액세스 토큰 추출
        if(token != null && jwtTokenProvider.validateAccessToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            throw new ServletException("AccessToken is not valid.");
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        // 헤더에서 JWT 를 받아옵니다.
//        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
//        // 유효한 토큰인지 확인합니다.
//        if (token != null && jwtTokenProvider.validateAccessToken(token)) {
//            // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
//            Authentication authentication = jwtTokenProvider.getAuthentication(token);
//            // SecurityContext 에 Authentication 객체를 저장합니다.
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//        chain.doFilter(request, response);

//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        // 헤더에서 JWT 를 받아옵니다.
//        String accessToken = jwtTokenProvider.resolveAccessToken(request);
//        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);
//
//        // 유효한 토큰인지 확인
//        if (accessToken != null) {
//            // 액세스 토큰이 유효한 상황
//            if (jwtTokenProvider.validateAccessToken(accessToken)) {
//                // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
//                Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
//                // SecurityContext 에 Authentication 객체를 저장합니다.
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            } else {
//                // 리프레시 토큰이 일치하는지 확인 - 이건 따로 함수를 만들어서 그것의 리턴값으로 확인하면 됨.
//                if (jwtTokenProvider.validateRefreshToken(refreshToken)) {
//                    // 액세스 토큰과 리프레시 토큰 재발급
//                    /// 리프레시 토큰으로 username 정보 가져오기
//                    String username = jwtTokenProvider.getUserNameByRefreshToken(refreshToken);
//                    /// 권한정보 받아오기
//                    List<String> roles = jwtTokenProvider.getRoles(username);
//                    /// 새 토큰 발급
//                    Token newToken = null;
//                    try {
//                        newToken = jwtTokenProvider.recreationAccessToken(username, roles);
//                    } catch (MemberService.UsernameNotFoundException e) {
//                        throw new RuntimeException(e);
//                    }
//                    /// 헤더에 토큰 추가
//                    jwtTokenProvider.setHeaderAccessToken(response, newToken.getAccessToken());
//                    jwtTokenProvider.setHeaderRefreshToken(response, newToken.getRefreshToken());
//                    //db 갱신
//                    // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
//                    Authentication authentication = jwtTokenProvider.getAuthentication(newToken.getAccessToken());
//                    /// SecurityContext에 Authentication 객체 저장
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                }
//            }
//        }
//        filterChain.doFilter(request, response);
//    }
}
