package com.is1di.userservicegradle.entity.extend;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(name = "workExperience")
public class WorkExperience {
    private LocalDate startDate;
    private LocalDate endDate;
    private String departmentName;
    private String description;
}
