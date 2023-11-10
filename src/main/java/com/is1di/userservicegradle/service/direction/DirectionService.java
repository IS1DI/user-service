package com.is1di.userservicegradle.service.direction;

import com.is1di.userservicegradle.entity.direction.Direction;
import com.is1di.userservicegradle.exception.AlreadyExistsException;
import com.is1di.userservicegradle.exception.NotFoundException;
import com.is1di.userservicegradle.service.MessageService;
import com.is1di.userservicegradle.service.TypeService;
import com.is1di.userservicegradle.utils.EntityClassName;
import com.is1di.userservicegradle.utils.MessageMethod;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class DirectionService {
    private final MongoOperations mongoOperations;
    private final TypeService typeService;
    private final MessageService messageService;

    public Direction create(Direction direction) {
        if (!existsByTitle(direction.getTitle())) {
            return mongoOperations.save(direction);
        } else throw new AlreadyExistsException(
                messageService.getMessage(MessageMethod.DIRECTION_ALREADY_EXISTS, direction.getTitle())
        );
    }

    public boolean existsByTitle(String title) {
        return typeService.exists(Query.query(Criteria.where("title").is(title)), EntityClassName.DIRECTION, Direction.class);
    }

    public Direction findById(ObjectId id) {
        return typeService.findById(id, EntityClassName.DIRECTION, Direction.class)
                .orElseThrow(() -> new NotFoundException(messageService.getMessage(MessageMethod.DIRECTION_NOT_FOUND, id.toString())));
    }

    public Page<Direction> getPage(int p, int l) {
        return typeService.getPage(p, l, EntityClassName.DIRECTION, Direction.class);
    }

    public Direction findByTitle(String title) {
        return typeService.findOne(Query.query(Criteria.where("title").is(title)), EntityClassName.DIRECTION, Direction.class)
                .orElseThrow(() -> new NotFoundException(
                        messageService.getMessage(MessageMethod.DISC_NOT_FOUND, title)
                ));
    }
}
