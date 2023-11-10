package com.is1di.userservicegradle.dto.direction;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

public class DirectionDto {
    @Data
    @Schema(name = "directionCreate")
    public static final class Create {
        @NotBlank
        @NotNull
        private String title;
    }

    @Data
    @Schema(name = "directionOutput")
    public static final class Output {
        private String id;
        private String title;
    }
}
