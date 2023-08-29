package com.trablog.spring.webapps.data.dto;

public class ChangeMemberNameDto {
    private Long userNo;
    private String userName;

    public ChangeMemberNameDto(Long userNo, String userName) {
        this.userNo = userNo;
        this.userName = userName;
    }

    public ChangeMemberNameDto() {
    }

    public Long getUserNo() {
        return userNo;
    }

    public void setUserNo(Long userNo) {
        this.userNo = userNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
