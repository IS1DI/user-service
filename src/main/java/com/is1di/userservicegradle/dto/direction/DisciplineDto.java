package com.is1di.userservicegradle.dto.direction;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class DisciplineDto {
    @Data
    @Schema(name = "disciplineCreate")
    public static final class Create {
        @NotBlank
        @NotNull
        private String title;
        @NotBlank
        @NotNull
        private String description;
        @NotBlank
        @NotNull
        private String teacherId;
        @NotNull
        @NotBlank
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate startsAt;
        @NotBlank
        @NotNull
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate endsAt;
    }

    @Data
    @Schema(name = "disciplineUpdate")
    public static final class Update {
        private String description;
    }

    @Data
    @Schema(name = "disciplineOutput")
    public static final class Output {
        private String title;
        private String description;
        private String teacherId;
        private String courseId;
        private String academicSubjectId;
        private LocalDate startsAt;
        private LocalDate endsAt;
    }
}
