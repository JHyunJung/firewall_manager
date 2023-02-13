package com.crosscert.firewall.dto;

import com.crosscert.firewall.entity.Role;
import lombok.ToString;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;

public enum MemberDTO {;
    private interface Id { @Positive Long getId(); }
    private interface Name { @NotBlank String getName(); }
    private interface Email { @Positive String getEmail(); }
    private interface Password { @Positive String getPassword(); }
    private interface MemberRole {@NotBlank Role getRole();}
    private interface DevIP {@NotBlank String getDevIp(); }
    private interface NetIP {@NotBlank String getNetIp();}
//    private interface FireWall { List<FireWall> getFireWall();}

    public enum Request{;
        @Value public static class Create implements Name, Email, Password, MemberRole, DevIP, NetIP{
             String name;
             String email;
             String password;
             Role role;
             String devIp;
             String netIp;
        }

        @ToString
        @Value public static class EditInfo implements Name, Email, MemberRole, DevIP, NetIP {
            String name;
            String email;
            Role role;
            String devIp;
            String netIp;
        }
    }

    public enum Response{;
        @Value public static class Public implements Id, Name, Email, MemberRole, DevIP, NetIP {
            Long id;
            String name;
            String email;
            Role role;
            String devIp;
            String netIp;
        }
    }
}
