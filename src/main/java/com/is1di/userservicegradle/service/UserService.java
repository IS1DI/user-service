package com.is1di.userservicegradle.service;

import com.is1di.userservicegradle.entity.users.User;
import com.is1di.userservicegradle.exception.AlreadyExistsException;
import com.is1di.userservicegradle.exception.NotFoundException;
import com.is1di.userservicegradle.utils.MessageMethod;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final MongoOperations mongoOperations;
    private final PasswordEncoder passwordEncoder;
    private final MessageService messageService;

    public User findById(ObjectId id) {
        return mongoOperations.findById(id, User.class);
    }

    public User create(User user) {
        safeExist(user.getUsername());
        return mongoOperations.save(user);
    }

    public boolean existsByUsername(String username) {
        return mongoOperations.exists(Query.query(Criteria.where("username").is(username)), "users");
    }

    public void safeExist(String username) {
        if (existsByUsername(username)) throw new AlreadyExistsException(
                messageService.getMessage(MessageMethod.USERNAME_ALREADY_EXISTS)
        );
    }

    public void setPassword(ObjectId id, String pass) {
        Optional.ofNullable(mongoOperations.update(User.class)
                        .matching(Query.query(Criteria.where("_id").is(id)))
                        .apply(Update.update("password", passwordEncoder.encode(pass)))
                        .findAndModifyValue())
                .orElseThrow(() -> new NotFoundException(
                        messageService.getMessage(MessageMethod.USER_NOT_FOUND, id.toString())
                ));
    }
}
