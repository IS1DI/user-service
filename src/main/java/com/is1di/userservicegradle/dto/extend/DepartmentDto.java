package com.is1di.userservicegradle.dto.extend;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

public class DepartmentDto {
    @Data
    @Schema(name = "departmentCreate")
    public static final class Create {
        @NotNull
        @NotBlank
        private String title;
    }

    @Data
    @Schema(name = "departmentOutput")
    public static final class Output {
        private String id;
        private String title;
        private String supervisorId;
        private Set<String> employeeIds;
    }
}
