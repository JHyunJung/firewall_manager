package com.crosscert.firewall.dto;

import com.crosscert.firewall.entity.Ip;
import com.crosscert.firewall.entity.Role;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.Map;

public enum FireWallDTO {;
    private interface Id { Long getId();}
    private interface Start { String getStart(); }
    private interface Destination { String getStart(); }
    private interface Port { int getPort(); }
    private interface EndDate { String getEndDate(); }
    private interface Ended { boolean isEnded(); }
    private interface Member { String getMember(); }

    public enum Request{;

        @Value
        public static class Create implements Start, Destination, Port, EndDate, Member{
            String start;
            String destination;
            int port;
            String endDate;
            String member;
        }
    }

    public enum Response{;

        @Value
        public static class Create implements Id{
            Long id;
        }

        @Value
        public static class Public implements Id, Start, Destination, Port, EndDate, Ended, Member{
            Long id;
            String start;
            String destination;
            int port;
            String member;
            String endDate;
            boolean ended;
        }

//        public static class ListPage<T> implements Id, Start, Destination, Port, EndDate, Ended{
//
//            private Map<String, Object> pageInfo;
//            private List<T> content;
//
//            Long id;
//            String start;
//            String destination;
//            String port;
//            String endDate;
//            boolean ended;
//        }
    }
}
