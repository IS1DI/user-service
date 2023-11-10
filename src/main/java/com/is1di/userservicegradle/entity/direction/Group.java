package com.is1di.userservicegradle.entity.direction;

import com.is1di.userservicegradle.utils.EntityClassName;
import lombok.Data;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@TypeAlias("group")
@Document
public class Group {
    private String studGroup;
    private String studentType = EntityClassName.STUDENT;
}

