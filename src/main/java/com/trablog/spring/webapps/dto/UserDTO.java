//package com.trablog.spring.webapps.dto;
//
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Pattern;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class UserDTO {
//    @NotNull(message = "ID가 전달되지 않았습니다.")
//    @NotBlank(message = "ID는 필수 입력 항목입니다.")
//    @Pattern(message = "잘못된 아이디 형식입니다.", regexp = "^[a-z0-9_-]{6,10}")
//    private String username;
//
//    @NotNull(message = "Password가 전달되지 않았습니다.")
//    @NotBlank(message = "Password는 필수 입력 항목입니다.")
//    @Pattern(message = "", regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-za-z][0-9]$@$!%*#?&]{8,15}")
//    private String password;
//
//    @NotNull(message = "Email이 전달되지 않았습니다.")
//    @NotBlank(message = "Email은 필수 입력 항목입니다.")
//    @Email(message = "이메일 형식이 아닙니다.")
//    private String email;
//}
