package com.is1di.userservicegradle.service;

import com.is1di.userservicegradle.entity.users.UserFull;
import com.is1di.userservicegradle.exception.NotFoundException;
import com.is1di.userservicegradle.utils.MessageMethod;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserFullService {
    private final MessageService messageService;
    private final MongoOperations mongoOperations;

    public UserFull findById(ObjectId id) {
        return Optional.ofNullable(mongoOperations.findById(id, UserFull.class))
                .orElseThrow(() -> new NotFoundException(
                        messageService.getMessage(MessageMethod.USER_NOT_FOUND, id.toString())
                ));
    }

    public Page<UserFull> getPage(int p, int l) {
        return getPage(p, l, new Query());
    }

    public Page<UserFull> getPage(int p, int l, Query q) {
        Pageable page = PageRequest.of(Math.max(1, p) - 1, Math.max(l, 10));
        return PageableExecutionUtils.getPage(mongoOperations.find(q, UserFull.class),
                page,
                () -> mongoOperations.count(Query.of(q).limit(-1).skip(-1), UserFull.class));
    }

    public Page<UserFull> search(int p, int l, String q) {
        return getPage(p, l, Query.query(Criteria.where("fullName").regex(".*" + q + ".*", "i")));
    }

    public Page<UserFull> searchByClass(int p, int l, String q, String className) {
        return getPage(p, l, Query.query(Criteria.where("fullName").regex(".*" + q + ".*", "i").and("_class").is(className)));
    }
}
