package com.is1di.userservicegradle.service.direction;

import com.is1di.userservicegradle.entity.direction.Direction;
import com.is1di.userservicegradle.entity.direction.Group;
import com.is1di.userservicegradle.entity.users.Student;
import com.is1di.userservicegradle.exception.NotFoundException;
import com.is1di.userservicegradle.exception.NotUniqueException;
import com.is1di.userservicegradle.service.MessageService;
import com.is1di.userservicegradle.utils.MessageMethod;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupService {
    private final MongoOperations mongoOperations;
    private final DirectionService directionService;
    private final MessageService messageService;

    public Group create(String title, Group group) {
        if (directionService.existsByTitle(title)) {
            if (!exists(group.getStudGroup())) {
                Direction direction = mongoOperations.update(Direction.class)
                        .matching(Query.query(Criteria.where("title").is(title)))
                        .apply(new Update().addToSet("groups", group))
                        .withOptions(FindAndModifyOptions.options().returnNew(true))
                        .findAndModifyValue();
                return direction.getGroups().stream().filter(g -> g.getStudGroup().equals(group.getStudGroup()))
                        .findFirst()
                        .orElseThrow(() -> new NotFoundException(
                                messageService.getMessage(MessageMethod.GROUP_NOT_FOUND, group.getStudGroup())
                        ));
            } else throw new NotUniqueException(
                    messageService.getMessage(MessageMethod.GROUP_ALREADY_EXISTS, group.getStudGroup())
            );
        } else throw new NotFoundException(
                messageService.getMessage(MessageMethod.DIRECTION_NOT_FOUND, title)
        );
    }

    public Group findByDirectionTitle(String dirTitle, String studGroup) {
        if (directionService.existsByTitle(dirTitle)) {
            if (exists(studGroup)) {
                Direction direction = mongoOperations.findOne(Query.query(Criteria.where("title").is(dirTitle).and("groups").elemMatch(Criteria.where("studGroup").is(studGroup))), Direction.class);
                return direction.getGroups().stream().filter(g -> g.getStudGroup().equals(studGroup)).findFirst()
                        .orElseThrow(() -> new NotFoundException(
                                messageService.getMessage(MessageMethod.GROUP_NOT_FOUND, studGroup)
                        ));
            } else throw new NotUniqueException(
                    messageService.getMessage(MessageMethod.GROUP_ALREADY_EXISTS, studGroup)
            );
        } else throw new NotFoundException(
                messageService.getMessage(MessageMethod.DIRECTION_NOT_FOUND, dirTitle)
        );
    }

    public boolean exists(String studGroup) {
        return (mongoOperations.exists(Query.query(Criteria.where("groups").elemMatch(Criteria.where("studGroup").is(studGroup))), Direction.class));
    }

    public Set<Group> findAll(String dirTitle) {
        Direction direction = mongoOperations.findOne(Query.query(Criteria.where("title").is(dirTitle)), Direction.class);
        return Optional.ofNullable(direction).map(Direction::getGroups)
                .orElseThrow(() -> new NotFoundException(
                        messageService.getMessage(MessageMethod.DIRECTION_NOT_FOUND, dirTitle)
                ));
    }

    public long countStudentsInGroup(String studGroup) {
        return mongoOperations.count(Query.query(Criteria.where("studGroup").is(studGroup)), Student.class);
    }

    public Group findByStudGroup(String studGroup) {
        Set<Group> groups = Optional.ofNullable(mongoOperations.findOne(Query.query(Criteria.where("groups").elemMatch(Criteria.where("studGroup").is(studGroup))), Direction.class))
                .map(Direction::getGroups)
                .orElseThrow(() -> new NotFoundException(
                        messageService.getMessage(MessageMethod.GROUP_NOT_FOUND, studGroup)
                ));
        return groups.stream().filter(g -> g.getStudGroup().equals(studGroup))
                .findFirst().orElseThrow(() -> new NotFoundException(
                        messageService.getMessage(MessageMethod.GROUP_NOT_FOUND, studGroup)
                ));
    }
}
