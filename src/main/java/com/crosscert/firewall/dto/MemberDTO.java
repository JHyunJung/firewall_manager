package com.crosscert.firewall.dto;

import com.crosscert.firewall.entity.Role;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public enum MemberDTO {;
    private interface Id { @Positive Long getId(); }
    private interface Name { @NotBlank String getName(); }
    private interface Email { @Positive String getEmail(); }
    private interface Password { @Positive String getPassword(); }
    private interface MemberRole {@NotBlank Role getRole();}
    private interface DevIp {@NotBlank String getDevIp(); }
    private interface NetIp {@NotBlank String getNetIp();}
//    private interface FireWall { List<FireWall> getFireWall();}

    public enum Request{;
        @NoArgsConstructor
        @AllArgsConstructor
        @Getter
        public static class Create implements Name, Email, Password, MemberRole, DevIp, NetIp{
             String name;
             String email;
             String password;
             Role role;
             String devIp;
             String netIp;
        }

        @NoArgsConstructor
        @AllArgsConstructor
        @Getter
        public static class Edit implements MemberRole, DevIp, NetIp {
            Role role;
            String devIp;
            String netIp;
        }
    }

    public enum Response{;
        @NoArgsConstructor
        @AllArgsConstructor
        @Getter
        public static class Public implements Id, Name, Email, MemberRole, DevIp, NetIp {
            Long id;
            String name;
            String email;
            Role role;
            String devIp;
            String netIp;
        }

        @NoArgsConstructor
        @AllArgsConstructor
        @Getter
        public static class Create implements Id{
            Long id;
        }

        @NoArgsConstructor
        @AllArgsConstructor
        @Getter
        public static class Edit implements Id, MemberRole, DevIp, NetIp {
            Long id;
            Role role;
            String devIp;
            String netIp;
        }
    }
}
