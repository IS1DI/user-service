package com.is1di.userservicegradle.dto.extend;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

public class SupervisorDto {
    @Data
    @Schema(name = "supervisorCreate")
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
        private String position;
        @NotNull
        @NotBlank
        private String department;
        private String imgUrl;
    }

    @Data
    @Schema
    public static final class Output {
        private String id;
        private String username;
        private String fullName;
        private String email;
        private String phoneNumber;
        private String position;
        private String department;
        private String imgUrl;
    }
}
