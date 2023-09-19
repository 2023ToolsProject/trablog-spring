package com.trablog.spring.webapps.service;

import com.trablog.spring.webapps.dto.MemberResponseDTO;
import com.trablog.spring.webapps.security.dto.MemberJoinDTO;

public interface MemberService {

    static class UsernameExistException extends Exception {

    }

    MemberResponseDTO join(MemberJoinDTO memberJoinDTO) throws UsernameExistException;
}
