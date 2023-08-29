package com.trablog.spring.webapps.data.dao;

import com.trablog.spring.webapps.data.entity.Member;


import java.util.List;

public interface MemberDAO {
    Member insertMember(Member member);

    List<Member> selectMember();

    Member selectMember(Long userNo);

    Member updateMemberName(Long userNo, String userName) throws Exception;

    void deleteMember(Long userNo) throws Exception;

}
