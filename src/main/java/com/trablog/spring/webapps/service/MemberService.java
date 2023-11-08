package com.trablog.spring.webapps.service;

import com.trablog.spring.webapps.domain.Member;
import com.trablog.spring.webapps.security.Token;
import com.trablog.spring.webapps.security.dto.MemberJoinDTO;
import com.trablog.spring.webapps.security.dto.MemberLoginDTO;

import java.util.Optional;

public interface MemberService {

    Optional<Member> getMemberByRefreshToken(String refreshToken);

    static class UsernameExistException extends Exception {

    }

    static class UsernameNotFoundException extends Exception{

    }

    static class WrongPasswordException extends Exception{

    }

    Token join(MemberJoinDTO memberJoinDTO) throws UsernameExistException;

    Token login(MemberLoginDTO memberLoginDTO) throws UsernameNotFoundException, WrongPasswordException;

    Member updateToken(String username, Token token) throws UsernameNotFoundException;

}
