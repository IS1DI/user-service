package com.is1di.userservicegradle.entity.direction;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.TypeAlias;

import java.time.LocalDate;

@Data
@TypeAlias("discipline")
public class Discipline {
    private String title;
    private String description;
    private ObjectId teacherId;
    private String courseId;
    private String academicSubjectId;
    private LocalDate startsAt;
    private LocalDate endsAt;
}
