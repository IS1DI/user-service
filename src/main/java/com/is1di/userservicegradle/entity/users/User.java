package com.is1di.userservicegradle.entity.users;

import com.is1di.userservicegradle.utils.EntityClassName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document(collection = "users")
@TypeAlias(EntityClassName.USER)
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private ObjectId id;

    @Indexed(unique = true)
    private String username;

    private String fullName;

    private String email;

    private String imgUrl;

    private String phoneNumber;

    private boolean enabled;

    private String role;
}
