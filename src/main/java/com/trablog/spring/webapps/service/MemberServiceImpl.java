package com.trablog.spring.webapps.service;

import com.trablog.spring.webapps.domain.Member;
import com.trablog.spring.webapps.domain.MemberRole;
import com.trablog.spring.webapps.dto.MemberResponseDTO;
import com.trablog.spring.webapps.repository.MemberRepository;
import com.trablog.spring.webapps.security.JwtTokenProvider;
import com.trablog.spring.webapps.security.Token;
import com.trablog.spring.webapps.security.dto.MemberJoinDTO;
import com.trablog.spring.webapps.security.dto.MemberLoginDTO;
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
    public Token join(MemberJoinDTO memberJoinDTO) throws UsernameExistException{

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
            member.addRole("ROLE_USER");

            Token jwtToken = jwtTokenProvider.createToken(member.getUsername(), member.getRoles());

            String accessToken = jwtToken.getAccessToken();
            log.info(accessToken);
            String refreshToken = jwtToken.getRefreshToken();
            log.info(refreshToken);

            member.setAccessToken(accessToken);
            member.setRefreshToken(refreshToken);

            log.info("=======================");
            log.info(member);
            log.info(member.getRoles()); // 여기까지는 잘됨
            log.info(member.getAccessToken());

            Member savedMember = memberRepository.save(member);
            log.info(savedMember);
//
//            String jwtToken = jwtTokenProvider.createToken(savedMember.getUsername(), savedMember.getRoles());
//
//            log.info(jwtToken);

//            MemberResponseDTO memberResponseDTO = new MemberResponseDTO();
//            memberResponseDTO.setHttpStatus(201);

            return jwtToken;
        }
        @Override
    public Token login(MemberLoginDTO memberLoginDTO) throws UsernameNotFoundException, WrongPasswordException{

            String username = memberLoginDTO.getUsername();
            log.info(username);

            Optional<Member> exist = memberRepository.getWithRoles(username);

            if(!exist.isPresent()) {
                throw new UsernameNotFoundException();
            }

            if(!passwordEncoder.matches(memberLoginDTO.getPassword(), exist.get().getPassword())) {
                throw new WrongPasswordException();
            }

            Token jwtToken = jwtTokenProvider.createToken(exist.get().getUsername(), exist.get().getRoles());

            String accessToken = jwtToken.getAccessToken();
            log.info(accessToken);
            String refreshToken = jwtToken.getRefreshToken();
            log.info(refreshToken);

            //update 하기
            updateToken(username, jwtToken);

            return jwtToken;
        }

        public Member updateToken(String username, Token token) throws UsernameNotFoundException {
            Optional<Member> selectedMember = memberRepository.getWithRoles(username);

            Member updatedMember;
            if(selectedMember.isPresent()) {
                Member member = selectedMember.get();
                member.setAccessToken(token.getAccessToken());
                member.setRefreshToken(token.getRefreshToken());

                updatedMember = memberRepository.save(member);
            } else {
                throw new UsernameNotFoundException();
            }
            return updatedMember;
        }


    }

