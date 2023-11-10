package com.is1di.userservicegradle.entity.users;

import com.is1di.userservicegradle.entity.direction.Discipline;
import com.is1di.userservicegradle.utils.EntityClassName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "users")
@TypeAlias(EntityClassName.TEACHER)
public class Teacher extends User {
    @DocumentReference(lookup = "{'teacherId' : ?#{#self._id}", collection = "direction")
    private Set<Discipline> disciplines;
}
