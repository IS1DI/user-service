package com.is1di.userservicegradle.dto.direction;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

public class GroupDto {
    @Data
    @Schema(name = "groupCreate")
    public static final class Create {
        @NotBlank
        @NotNull
        private String studGroup;
    }

    @Data
    @Schema(name = "groupOutput")
    public static final class Output {
        //TODO mb id direction
        private String studGroup;
        private Long countStudents;
    }
}
