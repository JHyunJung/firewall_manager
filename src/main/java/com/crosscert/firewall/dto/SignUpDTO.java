package com.crosscert.firewall.dto;

import com.crosscert.firewall.entity.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SignUpDTO {
    private String name;
    private String email;
    private String password;
    private Role role;
    private String devIp;
    private String netIp;

    //TODO:클라이언트로 부터 비밀번호 받아오면서 바로 암호화 진행
    public void setPassword(String password) {
        this.password = password+"(암호화 처리후)";
    }
}
