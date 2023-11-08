package com.trablog.spring.webapps.service;

import com.trablog.spring.webapps.domain.Member;
import com.trablog.spring.webapps.repository.MemberRepository;
import com.trablog.spring.webapps.security.JwtTokenProvider;
import com.trablog.spring.webapps.security.Token;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final MemberRepository memberRepository;

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    public boolean CheckToken(String username, String refreshToken) {
        // username으로 refreshtoken 필드 조회해서 tokenDTO의 refreshtoken 필드와 일치하는지 확인
        Optional<Member> member = memberRepository.getWithRoles(username);
        String userRefreshToken = member.get().getRefreshToken();
        if(refreshToken.equals(userRefreshToken)) { return true;}
        else { return false;}
    }

    public Token TokenReissue(String username) throws MemberService.UsernameNotFoundException {
        Optional<Member> member = memberRepository.getWithRoles(username);
        Token token = jwtTokenProvider.createToken(username, member.get().getRoles());
        memberService.updateToken(username, token);
        return token;
    }

    public boolean CheckTokenValidTime(String username) {
        Optional<Member> member = memberRepository.getWithRoles(username);
        Date tokenIssueDate = member.get().getTokenIssueDate();
        Long tokenValidTime = member.get().getTokenValidTime();
        Date now = new Date();
        tokenIssueDate.setTime(tokenIssueDate.getTime() + tokenValidTime);
        if(now.before(tokenIssueDate)) {
            return true;
        } else { return false;}
    }

}
