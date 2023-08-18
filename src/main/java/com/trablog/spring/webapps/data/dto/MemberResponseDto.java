package com.trablog.spring.webapps.data.dto;

import com.trablog.spring.webapps.data.entity.Member;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResponseDto {
    private Long userNo;
    private String userName;
    private int loginType;
    private String nickName;
    private String imageUrl;
    private String introduction;
    private LocalDateTime joinDate;
    private LocalDateTime updateDate;
    private String salt;
    private String password;
    private LocalDateTime pwUpdateDate;

    public static MemberResponseDto toDTO(Member member) {
        return MemberResponseDto.builder()
                .imageUrl(member.getImageUrl())
                .introduction(member.getIntroduction())
                .build();
    }

//    public static Builder builder() {
//        return new Builder();
//    }
//
//
//    // Builder class
//    public static class Builder {
//        private Long userNo;
//        private String userName;
//        private int loginType;
//        private String nickName;
//        private String imageUrl;
//        private String introduction;
//        private LocalDateTime joinDate;
//        private LocalDateTime updateDate;
//        private String salt;
//        private String password;
//        private LocalDateTime pwUpdateDate;
//
//        // Private constructor to prevent direct instantiation of the builder
//        private Builder() {
//        }
//
//        public MemberResponseDto.Builder userNo(Long userNo) {
//            this.userNo = userNo;
//            return this;
//        }
//
//        public MemberResponseDto.Builder userName(String userName) {
//            this.userName = userName;
//            return this;
//        }
//
//        public MemberResponseDto.Builder loginType(int loginType) {
//            this.loginType = loginType;
//            return this;
//        }
//
//        public MemberResponseDto.Builder nickName(String nickName) {
//            this.nickName = nickName;
//            return this;
//        }
//
//        public MemberResponseDto.Builder imageUrl(String imageUrl) {
//            this.imageUrl = imageUrl;
//            return this;
//        }
//
//        public MemberResponseDto.Builder introduction(String introduction) {
//            this.introduction = introduction;
//            return this;
//        }
//
//        public MemberResponseDto.Builder joinDate(LocalDateTime joinDate) {
//            this.joinDate = joinDate;
//            return this;
//        }
//
//        public MemberResponseDto.Builder updateDate(LocalDateTime updateDate) {
//            this.updateDate = updateDate;
//            return this;
//        }
//
//        public MemberResponseDto.Builder salt(String salt) {
//            this.salt = salt;
//            return this;
//        }
//
//        public MemberResponseDto.Builder password(String password) {
//            this.password = password;
//            return this;
//        }
//
//        public MemberResponseDto.Builder pwUpdateDate(LocalDateTime pwUpdateDate) {
//            this.pwUpdateDate = pwUpdateDate;
//            return this;
//        }
//
//        public MemberResponseDto build() {
//            MemberResponseDto memberResponseDto = new MemberResponseDto();
//            memberResponseDto.userNo = this.userNo;
//            memberResponseDto.userName = this.userName;
//            memberResponseDto.loginType = this.loginType;
//            memberResponseDto.nickName = this.nickName;
//            memberResponseDto.imageUrl = this.imageUrl;
//            memberResponseDto.introduction = this.introduction;
//            memberResponseDto.joinDate = this.joinDate;
//            memberResponseDto.updateDate = this.updateDate;
//            memberResponseDto.salt = this.salt;
//            memberResponseDto.password = this.password;
//            memberResponseDto.pwUpdateDate = this.pwUpdateDate;
//            return memberResponseDto;
//        }
//    }

}
