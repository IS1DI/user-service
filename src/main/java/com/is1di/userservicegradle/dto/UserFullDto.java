package com.is1di.userservicegradle.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

public class UserFullDto {
    @Data
    @Schema(name = "userFullWithRole")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class OutputWithRole {
        private String id;
        private String username;
        private String fullName;
        private String email;
        private String phoneNumber;
        private String role;
        private String direction;
        private String studGroup;
        private String department;
        private String position;
        private String imgUrl;
        private String type;
    }

    @Data
    @Schema(name = "userFull")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Output {
        private String id;
        private String username;
        private String fullName;
        private String email;
        private String phoneNumber;
        private String direction;
        private String studGroup;
        private String department;
        private String position;
        private String imgUrl;
        private String type;
    }
}
