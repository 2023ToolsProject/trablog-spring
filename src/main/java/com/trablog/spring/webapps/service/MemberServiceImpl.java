package com.trablog.spring.webapps.service;

import com.trablog.spring.webapps.domain.Member;
import com.trablog.spring.webapps.domain.MemberRole;
import com.trablog.spring.webapps.dto.MemberResponseDTO;
import com.trablog.spring.webapps.repository.MemberRepository;
import com.trablog.spring.webapps.security.JwtTokenProvider;
import com.trablog.spring.webapps.security.dto.MemberJoinDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final ModelMapper modelMapper;

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public String join(MemberJoinDTO memberJoinDTO) throws UsernameExistException{

        String username = memberJoinDTO.getUsername();
        log.info(username);

        Optional<Member> exist = memberRepository.getWithRoles(username);

        if(exist.isPresent()) {
            throw new UsernameExistException();
        }

//        Member member = modelMapper.map(memberJoinDTO, Member.class);
            Member member = Member.builder()
                    .username(memberJoinDTO.getUsername())
                    .password(memberJoinDTO.getPassword())
                    .email(memberJoinDTO.getEmail())
                    .build();
            member.changePassword(passwordEncoder.encode(memberJoinDTO.getPassword()));
            member.addRole("MEMBER_USER");

            log.info("=======================");
            log.info(member);
            log.info(member.getRoles()); // 여기까지는 잘됨

            Member savedMember = memberRepository.save(member);
            log.info(savedMember);

            String jwtToken = jwtTokenProvider.createToken(savedMember.getUsername(), savedMember.getRoles());

            log.info(jwtToken);

//            MemberResponseDTO memberResponseDTO = new MemberResponseDTO();
//            memberResponseDTO.setHttpStatus(201);

            return jwtToken;
        }
    }

