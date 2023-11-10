package com.is1di.userservicegradle.service.extend;

import com.is1di.userservicegradle.entity.extend.Department;
import com.is1di.userservicegradle.exception.AlreadyExistsException;
import com.is1di.userservicegradle.exception.NotFoundException;
import com.is1di.userservicegradle.service.MessageService;
import com.is1di.userservicegradle.service.TypeService;
import com.is1di.userservicegradle.utils.EntityClassName;
import com.is1di.userservicegradle.utils.MessageMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class DepartmentService {
    private final MongoOperations mongoOperations;
    private final TypeService typeService;
    private final MessageService messageService;

    public Department create(Department department) {
        if (!existsByTitle(department.getTitle()))
            return mongoOperations.save(department);
        else throw new AlreadyExistsException(
                messageService.getMessage(MessageMethod.DEPARTMENT_ALREADY_EXISTS, department.getTitle())
        );
    }

    public boolean existsByTitle(String title) {
        return typeService.exists(Query.query(Criteria.where("title").is(title)), EntityClassName.DEPARTMENT, Department.class);
    }

    public Department getByTitle(String title) {
        return typeService.findOne(Query.query(Criteria.where("title").is(title)), EntityClassName.DEPARTMENT, Department.class)
                .orElseThrow(() -> new NotFoundException(
                        messageService.getMessage(MessageMethod.DEPARTMENT_NOT_FOUND, title)
                ));
    }

    public Page<Department> getPage(int p, int l) {
        return typeService.getPage(p, l, EntityClassName.DEPARTMENT, Department.class);
    }


}
