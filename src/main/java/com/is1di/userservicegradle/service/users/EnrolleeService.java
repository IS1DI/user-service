package com.is1di.userservicegradle.service.users;

import com.is1di.userservicegradle.entity.direction.Group;
import com.is1di.userservicegradle.entity.users.Enrollee;
import com.is1di.userservicegradle.entity.users.Student;
import com.is1di.userservicegradle.exception.NotFoundException;
import com.is1di.userservicegradle.mapper.users.EnrolleeMapper;
import com.is1di.userservicegradle.service.MessageService;
import com.is1di.userservicegradle.service.TypeService;
import com.is1di.userservicegradle.service.UserService;
import com.is1di.userservicegradle.service.direction.DirectionService;
import com.is1di.userservicegradle.service.direction.GroupService;
import com.is1di.userservicegradle.utils.EntityClassName;
import com.is1di.userservicegradle.utils.MessageMethod;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class EnrolleeService {
    private final GroupService groupService;
    private final EnrolleeMapper enrolleeMapper;
    private final MongoOperations mongoOperations;
    private final TypeService typeService;
    private final MessageService messageService;
    private final DirectionService directionService;
    private final UserService userService;

    public Enrollee getById(ObjectId id) {
        return typeService.findById(id, EntityClassName.ENROLLEE, Enrollee.class)
                .orElseThrow(() -> new NotFoundException(
                        messageService.getMessage(MessageMethod.ENROLLEE_NOT_FOUND, id.toString())
                ));
    }

    public void addToGroup(ObjectId abiturtId, String studGroup) {
        Group group = groupService.findByStudGroup(studGroup);
        Student student = enrolleeMapper.toStudent(getById(abiturtId), group.getStudGroup());
        mongoOperations.save(student); //FIXME  mb not working
    }

    public Page<Enrollee> getAll(int p, int l) {
        return typeService.getPage(p, l, EntityClassName.ENROLLEE, Enrollee.class);
    }

    public Enrollee create(Enrollee enrollee) {
        userService.safeExist(enrollee.getUsername());
        if (directionService.existsByTitle(enrollee.getDirection()))
            return mongoOperations.save(enrollee);
        else throw new NotFoundException(
                messageService.getMessage(MessageMethod.DIRECTION_NOT_FOUND, enrollee.getDirection())
        );
    }
}
