package com.example.shinsekai.member.dto.in;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class SignUpRequestDto {

//    private String email;
//    private String password;
//    private String name;
//    private String phoneNumber;
//    private String nickname;
//    private Date birth;
//    private String address;
//    private Gender gender;
//
//    public Member toEntity(PasswordEncoder passwordEncoder) {
//
//        return Member.builder()
//                .email(email)
//                .password(passwordEncoder.encode(password))
//                .uuid(UUID.randomUUID().toString())
//                .name(name)
//                .phone(phoneNumber)
//                .nickname(nickname)
//                .birth(birth)
//                .gender(gender)
//                .address(address)
//                .build();
//    }
//
//    @Builder
//    public SignUpRequestDto(
//            String email,
//            String password,
//            String name,
//            String phoneNumber,
//            String nickname,
//            Date birth,
//            String address,
//            Gender gender
//    ) {
//        this.email = email;
//        this.password = password;
//        this.name = name;
//        this.phoneNumber = phoneNumber;
//        this.nickname = nickname;
//        this.birth = birth;
//        this.address = address;
//        this.gender = gender;
//    }
//
//    public static SignUpRequestDto from(SignUpRequestVo signUpRequestVo) {
//        return SignUpRequestDto.builder()
//                .email(signUpRequestVo.getEmail())
//                .password(signUpRequestVo.getPassword())
//                .name(signUpRequestVo.getName())
//                .phoneNumber(signUpRequestVo.getPhoneNumber())
//                .nickname(signUpRequestVo.getNickname())
//                .birth(signUpRequestVo.getBirth())
//                .address(signUpRequestVo.getAddress())
//                .gender(signUpRequestVo.getGender())
//                .build();
//    }
}
