package com.is1di.userservicegradle.entity.extend;

import com.is1di.userservicegradle.entity.users.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "users")
@TypeAlias("employee")
@NoArgsConstructor
public class Employee extends User {
    private String department;
    private String position;
    private Set<WorkExperience> workExperiences = new HashSet<>();
}
