package com.example.shinsekai.member.entity;

import com.example.shinsekai.common.entity.BaseEntity;
import com.example.shinsekai.member.dto.in.ChangePasswordRequestDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

@ToString
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String memberUuid;

    @Column(unique = true, nullable = false)
    private String loginId;

    @Column(unique = true, nullable = false, length = 255)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(unique = true, nullable = false, length = 50)
    private String nickname;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
    private boolean isActive;

    @Column(unique = true, length = 20)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false, length = 8)
    private LocalDate birth;

    @Builder
    public Member(Long id
            , String memberUuid
            , String loginId
            , String email
            , String password
            , String nickname
            , boolean isActive
            , String phone
            , Gender gender
            , String name
            , LocalDate birth) {
        this.id = id;
        this.memberUuid = memberUuid;
        this.loginId = loginId;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.isActive = isActive;
        this.phone = phone;
        this.gender = gender;
        this.name = name;
        this.birth = birth;
    }

    public void updatePassword(String encodedNewPassword) {
        this.password = encodedNewPassword;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {    // 사용자의 권한(역할)을 반환
        return Collections.singleton(new SimpleGrantedAuthority("MEMBER"));
    }

    @Override
    public String getUsername() {
        return this.memberUuid;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
