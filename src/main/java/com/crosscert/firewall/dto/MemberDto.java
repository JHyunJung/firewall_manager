package com.crosscert.firewall.dto;

import com.crosscert.firewall.entity.Member;
import com.crosscert.firewall.entity.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


public class MemberDto {

    @Getter
    @NoArgsConstructor
    @ToString
    public static class FindAllMemberDto {
        private Long id;
        private String name;
        private String email;
        private Role role;
        private String devIp;
        private String netIp;

        public FindAllMemberDto(Member member) {
            this.id = member.getId();
            this.name = member.getName();
            this.email = member.getEmail();
            this.role = member.getRole();
            this.devIp = member.getDevIp().toString();
            this.netIp = member.getNetIp().toString();
        }
    }

}
