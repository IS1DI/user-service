package com.is1di.userservicegradle.dto.extend;

import com.is1di.userservicegradle.entity.extend.WorkExperience;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

public class EmployeeDto {

    @Data
    @Schema(name = "employeeCreate")
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
    @Schema(name = "employeeOutput")
    public static final class Output {
        private String id;
        private String username;
        private String fullName;
        private String email;
        private String phoneNumber;
        private String department;
        private Set<WorkExperience> workExperiences;
        private String imgUrl;
    }
}
