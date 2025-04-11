package com.example.shinsekai.member.dto.in;

import com.example.shinsekai.member.entity.Gender;
import com.example.shinsekai.member.entity.Member;
import com.example.shinsekai.member.vo.in.SignUpRequestVo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Builder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@ToString
public class SignUpRequestDto {

    private String memberUuid;
    private String loginId;
    private String email;
    private String password;
    private String nickname;
    private String phone;
    private Gender gender;
    private String name;
    private LocalDate birth;
    private List<Long> agreementIdList;

    public Member toEntity(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .memberUuid(memberUuid)
                .loginId(loginId)
                .email(email)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .phone(phone)
                .gender(gender)
                .name(name)
                .birth(birth)
                .isActive(true)
                .build();
    }

    @Builder
    public SignUpRequestDto(
                String memberUuid,
                String loginId,
                String email,
                String password,
                String nickName,
                String phone,
                Gender gender,
                String name,
                LocalDate birth,
                List<Long> agreementIdList
    ) {
        this.memberUuid = memberUuid;
        this.loginId = loginId;
        this.email = email;
        this.password = password;
        this.nickname = nickName;
        this.phone = phone;
        this.gender = gender;
        this.name = name;
        this.birth = birth;
        this.agreementIdList = agreementIdList;
    }

    public static SignUpRequestDto from(SignUpRequestVo signUpRequestVo) {
        return SignUpRequestDto.builder()
                .memberUuid(generateMemberUuid())
                .loginId(signUpRequestVo.getLoginId())
                .email(signUpRequestVo.getEmail())
                .password(signUpRequestVo.getPassword())
                .nickName(signUpRequestVo.getNickname())
                .phone(signUpRequestVo.getPhone())
                .gender(signUpRequestVo.getGender())
                .name(signUpRequestVo.getName())
                .birth(signUpRequestVo.getBirth())
                .agreementIdList(signUpRequestVo.getAgreementIdList())
                .build();
    }

    public static String generateMemberUuid() {
        String prefix = "M";
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String uuidPart = UUID.randomUUID().toString().substring(0, 8);

        return prefix + date + "-" + uuidPart;
    }
}
