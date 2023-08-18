package com.trablog.spring.webapps.service;

import com.trablog.spring.webapps.data.dto.MemberDto;
import com.trablog.spring.webapps.data.dto.MemberResponseDto;
import org.modelmapper.ModelMapper;

import java.util.List;

public interface MemberService {

    List<MemberResponseDto> getMember();
    MemberResponseDto getMemberById(Long userNo);
    MemberResponseDto saveMember(MemberDto memberDto);
    MemberResponseDto createMember(String username, String email, String password);
    MemberResponseDto changeMemberName(Long userNo, String userName) throws Exception;
    void deleteMember(Long userNo) throws Exception;
}
