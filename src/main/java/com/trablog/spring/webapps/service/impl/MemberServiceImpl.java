package com.trablog.spring.webapps.service.impl;

import com.trablog.spring.webapps.data.dao.MemberDAO;
import com.trablog.spring.webapps.data.dto.MemberDto;
import com.trablog.spring.webapps.data.dto.MemberResponseDto;
import com.trablog.spring.webapps.data.entity.Member;
import com.trablog.spring.webapps.service.MemberService;
import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    private final
    ModelMapper modelMapper;

    private final
    MemberDAO memberDAO;

    public MemberServiceImpl(ModelMapper modelMapper, MemberDAO memberDAO) {
        this.modelMapper = modelMapper;
        this.memberDAO = memberDAO;
    }


    @Override
    public List<MemberResponseDto> getMember() {
        List<Member> members = memberDAO.selectMember();
        List<MemberResponseDto> memberResponseDtos = new ArrayList<>();
        for(Member member: members){
            MemberResponseDto dto = modelMapper.map(member,MemberResponseDto.class);
            memberResponseDtos.add(dto);
//            MemberResponseDto memberResponseDto = new MemberResponseDto();
//            memberResponseDto.setUserNo(member.getUserNo());
//            memberResponseDto.setUserName(member.getUserName());
//            memberResponseDto.setImageUrl(member.getImageUrl());
//            memberResponseDto.setIntroduction(member.getIntroduction());
//            memberResponseDtos.add(memberResponseDto);
        }

        return memberResponseDtos;
    }

    @Override
    public MemberResponseDto getMemberById(Long userNo) {
        Member member = memberDAO.selectMember(userNo);

        MemberResponseDto memberResponseDto = new MemberResponseDto();
        memberResponseDto.setUserNo(member.getUserNo());
        memberResponseDto.setUserName(member.getUserName());
        memberResponseDto.setImageUrl(member.getImageUrl());
        memberResponseDto.setIntroduction(member.getIntroduction());

        return memberResponseDto;
    }

    @Override
    public MemberResponseDto saveMember(MemberDto memberDto) {
        Member member = new Member();
        member.setUserName(memberDto.getUserName());
        member.setImageUrl(memberDto.getImageUrl());
        member.setIntroduction(memberDto.getIntroduction());
        member.setJoinDate(LocalDateTime.now());
        member.setUpdateDate(LocalDateTime.now());
        member.setNickName(memberDto.getNickName());
        // 비밀번호 암호화
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        member.setPassword(passwordEncoder.encode(memberDto.getPassword())); // todo 비밀번호 암호화

        Member savedMember = memberDAO.insertMember(member);

        MemberResponseDto memberResponseDto = new MemberResponseDto();
        memberResponseDto.setUserNo(savedMember.getUserNo());
        memberResponseDto.setUserName(savedMember.getUserName());
        memberResponseDto.setImageUrl(savedMember.getImageUrl());
        memberResponseDto.setIntroduction(savedMember.getIntroduction());

        // 비밀번호 암호화 ( salt 기반 sha256 암호화)
        // jwt 발급
        //
        return memberResponseDto;
    }


    public MemberResponseDto createMember(String username, String email, String password) {
        Member member = new Member();
        member.setUserName(username);
        // 비밀번호 암호화
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        member.setPassword(passwordEncoder.encode(password));

        Member savedMember = memberDAO.insertMember(member);

        MemberResponseDto memberResponseDto = new MemberResponseDto();
        memberResponseDto.setUserName(savedMember.getUserName());
        memberResponseDto.setPassword(savedMember.getPassword());

        return memberResponseDto;
    }

    @Override
    public MemberResponseDto changeMemberName(Long userNo, String userName) throws Exception {
        Member changedMember = memberDAO.updateMemberName(userNo, userName);

        MemberResponseDto memberResponseDto = new MemberResponseDto();
        memberResponseDto.setUserNo(changedMember.getUserNo());
        memberResponseDto.setUserName(changedMember.getUserName());
        memberResponseDto.setImageUrl(changedMember.getImageUrl());
        memberResponseDto.setIntroduction(changedMember.getIntroduction());

        return memberResponseDto;
    }

    @Override
    public void deleteMember(Long userNo) throws Exception {
        memberDAO.deleteMember(userNo);
    }
}
