package com.is1di.userservicegradle.entity.users;

import com.is1di.userservicegradle.utils.EntityClassName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "users")
@TypeAlias(EntityClassName.CURATOR)
public class Curator extends User {
    private String direction;
}