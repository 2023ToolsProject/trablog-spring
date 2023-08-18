package com.trablog.spring.webapps.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    private Long userNo;
    private String userName;
    private int loginType;
    private String nickName;
    private String imageUrl;
    private String introduction;
    private String salt;
    private String password;
    private LocalDateTime pwUpdateDate;
}
