package com.trablog.spring.webapps.service;

import com.trablog.spring.webapps.domain.Member;
import com.trablog.spring.webapps.domain.MemberRole;
import com.trablog.spring.webapps.dto.MemberResponseDTO;
import com.trablog.spring.webapps.repository.MemberRepository;
import com.trablog.spring.webapps.security.dto.MemberJoinDTO;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final ModelMapper modelMapper;

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public MemberResponseDTO join(MemberJoinDTO memberJoinDTO) throws UsernameExistException{

        String username = memberJoinDTO.getUsername();

        boolean exist = memberRepository.existsById(username);

        if(exist){
            throw new UsernameExistException();
        }

        Member member = modelMapper.map(memberJoinDTO, Member.class);
//        Member member = Member.builder()
//                        .username(memberJoinDTO.getUsername())
//                        .password(memberJoinDTO.getPassword())
//                        .email(memberJoinDTO.getEmail())
//                .build();
        member.changePassword(passwordEncoder.encode(memberJoinDTO.getPassword()));
        member.addRole(MemberRole.USER);

        log.info("=======================");
        log.info(member);
        log.info(member.getRoleSet());

        Member savedMember = memberRepository.save(member);

        MemberResponseDTO memberResponseDTO = new MemberResponseDTO();
        memberResponseDTO.setUsername(savedMember.getUsername());

        return memberResponseDTO;
    }
}
