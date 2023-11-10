package com.is1di.userservicegradle.service.users;

import com.is1di.userservicegradle.entity.users.Student;
import com.is1di.userservicegradle.exception.NotFoundException;
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
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentService {
    private final TypeService typeService;
    private final MongoOperations mongoOperations;
    private final MessageService messageService;
    private final DirectionService directionService;
    private final GroupService groupService;
    private final UserService userService;

    public Page<Student> getPageStudentsByGroup(String studGroup, int p, int l) {
        return typeService.getPage(p, l, Query.query(Criteria.where("studGroup").is(studGroup).and("enabled").is(true)), EntityClassName.STUDENT, Student.class);
    }

    public boolean existsById(ObjectId id) {
        return typeService.existsById(id, EntityClassName.STUDENT, Student.class);
    }

    public Page<Student> getAll(int p, int l) {
        return typeService.getPage(p, l, EntityClassName.STUDENT, Student.class);
    }

    public Student getById(ObjectId id) {
        return typeService.findById(id, EntityClassName.STUDENT, Student.class)
                .orElseThrow(() -> new NotFoundException(
                        messageService.getMessage(MessageMethod.STUDENT_NOT_FOUND, id.toString())
                ));

    }

    public Student create(Student student) {
        userService.safeExist(student.getUsername());
        groupService.findByDirectionTitle(student.getDirection(), student.getStudGroup());
        return mongoOperations.save(student);
    }
}
