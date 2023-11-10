package com.is1di.userservicegradle.entity.users;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@EqualsAndHashCode(callSuper = true)
@Data
@TypeAlias("userFull")
public class UserFull extends User {
    private String direction;
    private String studGroup;
    private String department;
    private String position;
    private String _class;
}
