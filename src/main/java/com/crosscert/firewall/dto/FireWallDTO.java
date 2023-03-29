package com.crosscert.firewall.dto;

import com.crosscert.firewall.entity.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

public enum FireWallDTO {;
    private interface Id { Long getId();}
    private interface Start { String getStart(); }
    private interface Destination { String getDestination(); }
    private interface Port { List<IpPort> getPort(); }
    private interface EndDate { LocalDate getEndDate(); }
    private interface Ended { boolean isEnded(); }
    private interface MemberId { Long getMemberId(); }
    private interface MemberName { String getMemberName(); }
    private interface Description { String getDescription(); }

    public enum Request{;

        @NoArgsConstructor
        @AllArgsConstructor
        @Getter
        public static class Create implements Start, Destination, Port, EndDate, MemberId {
            String start;
            String destination;
            List<IpPort> port;
            LocalDate endDate;
            Long memberId;

        }
    }

    public enum Response{;

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
        public static class Public implements Id, Start, Destination, EndDate, Ended, MemberName, Description {
            Long id;
            String start;
            String destination;
            String memberName;
            String description;
            LocalDate endDate;
            boolean ended;
        }
    }
}
