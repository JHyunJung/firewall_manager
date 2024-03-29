package com.crosscert.firewall.dto;

import com.crosscert.firewall.entity.FireWall;
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

    private interface CurrentPassword { @Positive String getCurrentPassword(); }
    private interface NewPassword { @Positive String getNewPassword(); }

    public enum Request{;
        @NoArgsConstructor
        @AllArgsConstructor
        @Getter
        @Setter
        public static class Create implements Name, Email, Password, MemberRole{
             String name;
             String email;
             String password;
             Role role;
        }

        @NoArgsConstructor
        @AllArgsConstructor
        @Getter
        public static class Edit implements MemberRole, DevIp, NetIp {
            Role role;
            String devIp;
            String netIp;
        }

        @NoArgsConstructor
        @AllArgsConstructor
        @Getter
        public static class EditMyIp implements DevIp, NetIp {
            String devIp;
            String netIp;
        }

        @NoArgsConstructor
        @AllArgsConstructor
        @Getter
        public static class EditPassword implements CurrentPassword, NewPassword{
            String currentPassword;
            String newPassword;
        }

        @NoArgsConstructor
        @AllArgsConstructor
        @Getter
        public static class ResetPassword implements NewPassword{
            String newPassword;
        }
    }

    public enum Response{;
        @NoArgsConstructor
        @AllArgsConstructor
        @Getter
        @Setter
        @Builder
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
        @Setter
        @Builder
        public static class Create implements Id{
            Long id;
        }

        @NoArgsConstructor
        @AllArgsConstructor
        @Getter
        @Setter
        @Builder
        public static class Edit implements Id, Name, MemberRole, DevIp, NetIp {

            Long id;
            String name;
            Role role;
            String devIp;
            String netIp;
        }
    }
}
