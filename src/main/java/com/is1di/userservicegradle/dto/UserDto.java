package com.is1di.userservicegradle.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

public class UserDto {
    @Data
    @Schema(name = "userCreate")
    public static final class Create {
        private String username;
        private String fullName;
        private String email;
        private String phoneNumber;
        private String imgUrl;
    }

    @Data
    @Schema(name = "userUpdate")
    public static final class Update {
        private String username;
        private String fullName;
        private String email;
        private String phoneNumber;
        private String imgUrl;
    }

    @Data
    @Schema(name = "userCreate")
    public static final class Output {
        private String id;
        private String username;
        private String fullName;
        private String email;
        private String phoneNumber;
        private String imgUrl;
    }
}
