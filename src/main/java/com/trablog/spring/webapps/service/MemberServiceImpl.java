package com.trablog.spring.webapps.service;

import com.trablog.spring.webapps.domain.Member;
import com.trablog.spring.webapps.domain.MemberRole;
import com.trablog.spring.webapps.dto.MemberResponseDTO;
import com.trablog.spring.webapps.repository.MemberRepository;
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


    @Override
    public MemberResponseDTO join(MemberJoinDTO memberJoinDTO) throws UsernameExistException{

        String username = memberJoinDTO.getUsername();
        log.info(username);

//        boolean exist = memberRepository.existsById(username);
//        log.info(exist);

//        if(exist){
//            throw new UsernameExistException();
//        }

        Optional<Member> member = memberRepository.existsByUsername(username);
        log.info(member);

        if(member.isPresent()) {
            throw new UsernameExistException();
        } else {

//        Member member = modelMapper.map(memberJoinDTO, Member.class);
            Member newMember = Member.builder()
                    .username(memberJoinDTO.getUsername())
                    .password(memberJoinDTO.getPassword())
                    .email(memberJoinDTO.getEmail())
                    .build();
            newMember.changePassword(passwordEncoder.encode(memberJoinDTO.getPassword()));
            newMember.addRole(MemberRole.USER);

            log.info("=======================");
            log.info(newMember);
            log.info(newMember.getRoleSet()); // 여기까지는 잘됨

            Member savedMember = memberRepository.save(newMember);
            log.info(savedMember);

            MemberResponseDTO memberResponseDTO = new MemberResponseDTO();
            memberResponseDTO.setUsername(savedMember.getUsername());

            return memberResponseDTO;
        }
    }
}
