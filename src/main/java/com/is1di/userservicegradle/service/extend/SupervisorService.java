package com.is1di.userservicegradle.service.extend;

import com.is1di.userservicegradle.entity.extend.Supervisor;
import com.is1di.userservicegradle.exception.NotFoundException;
import com.is1di.userservicegradle.service.MessageService;
import com.is1di.userservicegradle.service.TypeService;
import com.is1di.userservicegradle.service.UserService;
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

@Service
@Transactional
@RequiredArgsConstructor
public class SupervisorService {
    private final MongoOperations mongoOperations;
    private final DepartmentService departmentService;
    private final TypeService typeService;
    private final MessageService messageService;
    private final UserService userService;

    public Supervisor getById(ObjectId id) {
        return typeService.findById(id, EntityClassName.SUPERVISOR, Supervisor.class)
                .orElseThrow(() -> new NotFoundException(
                        messageService.getMessage(MessageMethod.SUPERVISOR_NOT_FOUND, id.toString())
                ));
    }

    public Page<Supervisor> getPage(int p, int l) {
        return typeService.getPage(p, l, EntityClassName.SUPERVISOR, Supervisor.class);
    }

    public boolean existById(ObjectId id) {
        return typeService.existsById(id, EntityClassName.SUPERVISOR, Supervisor.class);
    }

    public Supervisor create(Supervisor supervisor) {
        userService.safeExist(supervisor.getUsername());
        if (departmentService.existsByTitle(supervisor.getDepartment()))
            return mongoOperations.save(supervisor);
        else throw new NotFoundException(
                messageService.getMessage(MessageMethod.DEPARTMENT_NOT_FOUND, supervisor.getDepartment())
        );
    }

    public void addToDep(String depTitle, ObjectId id) {
        if (existById(id)) {
            if (departmentService.existsByTitle(depTitle))
                mongoOperations.update(Supervisor.class)
                        .matching(Query.query(Criteria.where("_id").is(id)))
                        .apply(Update.update("department", depTitle))
                        .findAndModifyValue();
            else throw new NotFoundException(
                    messageService.getMessage(MessageMethod.DEPARTMENT_NOT_FOUND, depTitle)
            );
        } else throw new NotFoundException(
                messageService.getMessage(MessageMethod.SUPERVISOR_NOT_FOUND, id.toString())
        );
    }
}
