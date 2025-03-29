package com.example.shinsekai.member.dto.in;

import com.example.shinsekai.member.entity.Gender;
import com.example.shinsekai.member.entity.Member;
import com.example.shinsekai.member.vo.SignUpRequestVo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Builder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@NoArgsConstructor
@ToString
public class SignUpRequestDto {

    private String loginId;
    private String email;
    private String password;
    private String nickName;
    private String phone;
    private Gender gender;
    private String name;
    private LocalDate birth;
    private boolean isActive;

    public Member toEntity(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .memberUuid(UUID.randomUUID().toString())
                .loginId(this.loginId)
                .email(this.email)
                .password(passwordEncoder.encode(password))
                .nickName(this.nickName)
                .phone(this.phone)
                .gender(gender)
                .name(name)
                .birth(birth)
                .isActive(true)
                .build();
    }

    @Builder
    public SignUpRequestDto(
                String loginId,
                String email,
                String password,
                String nickName,
                String phone,
                Gender gender,
                String name,
                LocalDate birth
    ) {
        this.loginId = loginId;
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.phone = phone;
        this.gender = gender;
        this.name = name;
        this.birth = birth;
    }

    public static SignUpRequestDto from(SignUpRequestVo signUpRequestVo) {
        return SignUpRequestDto.builder()
                .loginId(signUpRequestVo.getLoginId())
                .email(signUpRequestVo.getEmail())
                .password(signUpRequestVo.getPassword())
                .nickName(signUpRequestVo.getNickName())
                .phone(signUpRequestVo.getPhone())
                .gender(signUpRequestVo.getGender())
                .name(signUpRequestVo.getName())
                .birth(signUpRequestVo.getBirth())
                .build();
    }
}
