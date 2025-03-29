package com.example.shinsekai.member.entity;

import com.example.shinsekai.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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

    @Column(unique = true, nullable = false, length = 100, updatable = false)
    private String password;

    @Column(unique = true, nullable = false, length = 50)
    private String nickName;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
    private boolean isActive;

    @Column(unique = true, length = 11)
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
            , String nickName
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
        this.nickName = nickName;
        this.isActive = isActive;
        this.phone = phone;
        this.gender = gender;
        this.name = name;
        this.birth = birth;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", memberUuid='" + memberUuid + '\'' +
                ", loginId='" + loginId + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", nickName='" + nickName + '\'' +
                ", isActive=" + isActive +
                ", phone='" + phone + '\'' +
                ", gender=" + gender +
                ", name='" + name + '\'' +
                ", birth=" + birth +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {    // 사용자의 권한(역할)을 반환
        return Collections.singleton(new SimpleGrantedAuthority("MEMBER"));
    }

    @Override
    public String getUsername() {
        return this.loginId;
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
