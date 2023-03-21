package com.crosscert.firewall.dto;

import com.crosscert.firewall.entity.Role;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Builder;
import lombok.Value;

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
        @Builder
        @Value public static class Create implements Name, Email, Password, MemberRole, DevIp, NetIp {
             String name;
             String email;
             String password;
             Role role;
             String devIp;
             String netIp;
        }

        @Value
//        @Builder
        public static class Edit implements MemberRole, DevIp, NetIp {
            Role role;
            String devIp;
            String netIp;

        }
    }

    public enum Response{;
        @Value public static class Public implements Id, Name, Email, MemberRole, DevIp, NetIp {
            Long id;
            String name;
            String email;
            Role role;
            String devIp;
            String netIp;
        }

        @Value public static class Create implements Id{
            Long id;
        }

        @Value public static class Edit implements Id, MemberRole, DevIp, NetIp {
            Long id;
            Role role;
            String devIp;
            String netIp;
        }
    }
}
