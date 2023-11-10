package com.is1di.userservicegradle.service.users;

import com.is1di.userservicegradle.entity.users.Teacher;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TeacherService {
    private final TypeService typeService;
    private final MongoOperations mongoOperations;
    private final MessageService messageService;
    private final UserService userService;

    public boolean existsById(ObjectId id) {
        return typeService.existsById(id, EntityClassName.TEACHER, Teacher.class);
    }

    public Teacher getById(ObjectId id) {
        return typeService.findById(id, EntityClassName.TEACHER, Teacher.class)
                .orElseThrow(() -> new NotFoundException(
                        messageService.getMessage(MessageMethod.TEACHER_NOT_FOUND, id.toString())
                ));
    }

    public Page<Teacher> getAll(int p, int l) {
        return typeService.getPage(p, l, EntityClassName.TEACHER, Teacher.class);
    }

    public Teacher create(Teacher teacher) {
        userService.safeExist(teacher.getUsername());
        return mongoOperations.save(teacher);
    }
}
