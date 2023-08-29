package com.trablog.spring.webapps.data.dao.impl;

import com.trablog.spring.webapps.data.dao.MemberDAO;
import com.trablog.spring.webapps.data.entity.Member;
import com.trablog.spring.webapps.data.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class MemberDAOImpl implements MemberDAO {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberDAOImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Member insertMember(Member member) {
        Member savedMember = memberRepository.save(member);

        return savedMember;
    }

    @Override
    public List<Member> selectMember() {
        List<Member> selectedMembers = memberRepository.findAll();
        return selectedMembers;
    }

    @Override
    public Member selectMember(Long userNo) {
        Member selectedMember = memberRepository.getById(userNo);

        return selectedMember;
    }

    @Override
    public Member updateMemberName(Long userNo, String userName) throws Exception {
        Optional<Member> selectedMember = memberRepository.findById(userNo);

        Member updatedMember;
        if(selectedMember.isPresent()) {
            Member member = selectedMember.get();

            member.setUserName(userName);
            member.setUpdateDate(LocalDateTime.now());

            updatedMember = memberRepository.save(member);
        } else {
            throw new Exception();
        }
        return updatedMember;
    }

    @Override
    public void deleteMember(Long userNo) throws Exception {
        Optional<Member> selectedMember = memberRepository.findById(userNo);

        if(selectedMember.isPresent()) {
            Member member = selectedMember.get();

            memberRepository.delete(member);
        } else {
            throw new Exception();
        }
    }
}
