package com.is1di.userservicegradle.service.users;

import com.is1di.userservicegradle.entity.direction.Direction;
import com.is1di.userservicegradle.entity.users.Curator;
import com.is1di.userservicegradle.exception.NotFoundException;
import com.is1di.userservicegradle.service.MessageService;
import com.is1di.userservicegradle.service.TypeService;
import com.is1di.userservicegradle.service.UserService;
import com.is1di.userservicegradle.service.direction.DirectionService;
import com.is1di.userservicegradle.utils.EntityClassName;
import com.is1di.userservicegradle.utils.MessageMethod;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CuratorService {
    private final DirectionService directionService;
    private final MongoOperations mongoOperations;
    private final TypeService typeService;
    private final MessageService messageService;
    private final UserService userService;

    public void addCurator(String dirTitle, ObjectId curatorId) {
        Direction d = directionService.findByTitle(dirTitle);
        if (existById(curatorId))
            mongoOperations.update(Curator.class)
                    .matching(Query.query(Criteria.where("_id").is(curatorId)))
                    .apply(new Update().set("direction", dirTitle))
                    .findAndModifyValue();
        else throw new NotFoundException(
                messageService.getMessage(MessageMethod.CURATOR_NOT_FOUND, curatorId.toString())
        );
    }

    public boolean existById(ObjectId id) {
        return typeService.existsById(id, EntityClassName.CURATOR, Curator.class);
    }

    public Curator getById(ObjectId id) {
        return typeService.findById(id, EntityClassName.CURATOR, Curator.class)
                .orElseThrow(() -> new NotFoundException(
                        messageService.getMessage(MessageMethod.CURATOR_NOT_FOUND, id.toString())
                ));
    }

    public Page<Curator> getAll(int p, int l) {
        return typeService.getPage(p, l, EntityClassName.CURATOR, Curator.class);
    }

    public Curator create(Curator curator) {
        userService.safeExist(curator.getUsername());
        if (directionService.existsByTitle(curator.getDirection()))
            return mongoOperations.save(curator);
        else throw new NotFoundException(
                messageService.getMessage(
                        MessageMethod.DIRECTION_NOT_FOUND, curator.getDirection()
                )
        );
    }

    public Curator getByDirection(String dirTitle) {
        return Optional.ofNullable(directionService.findByTitle(dirTitle).getCurator())
                .orElseThrow(() -> new NotFoundException(
                        messageService.getMessage(MessageMethod.CURATOR_NOT_FOUND,dirTitle)
                ));
    }
}
