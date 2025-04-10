package com.example.shinsekai.member.vo.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
public class ChangePasswordVo {
    private String loginId;

    @NotBlank(message = "INVALID_PASSWORD_FORMAT")
    @Size(min = 8, max = 20, message = "INVALID_PASSWORD_FORMAT")
    @Pattern(regexp = "^(?=\\S+$)(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[a-zA-Z\\d@$!%*?&]{8,20}$", message = "INVALID_PASSWORD_FORMAT")
    private String newPassword;
}
