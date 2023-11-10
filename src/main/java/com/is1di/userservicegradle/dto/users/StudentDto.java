package com.is1di.userservicegradle.dto.users;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

public class StudentDto {
    @Data
    @Schema(name = "studentCreate")
    public static final class Create {
        @NotNull
        @NotBlank
        private String username;
        @NotNull
        @NotBlank
        private String fullName;
        @NotNull
        @NotBlank
        private String email;
        @NotNull
        @NotBlank
        private String phoneNumber;
        @NotNull
        @NotBlank
        private String direction;
        @NotNull
        @NotBlank
        private String studGroup;
        private String imgUrl;
    }

    @Data
    @Schema(name = "studentOutput")
    public static final class Output {
        private String id;
        private String username;
        private String fullName;
        private String email;
        private String phoneNumber;
        private String direction;
        private String studGroup;
        private String imgUrl;
    }
}
