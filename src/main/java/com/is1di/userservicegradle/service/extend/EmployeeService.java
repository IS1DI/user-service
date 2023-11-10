package com.is1di.userservicegradle.service.extend;

import com.is1di.userservicegradle.entity.extend.Employee;
import com.is1di.userservicegradle.entity.extend.WorkExperience;
import com.is1di.userservicegradle.entity.users.Enrollee;
import com.is1di.userservicegradle.exception.NotFoundException;
import com.is1di.userservicegradle.mapper.extend.EmployeeMapper;
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

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeService {
    private final DirectionService directionService;
    private final MongoOperations mongoOperations;
    private final EmployeeMapper employeeMapper;
    private final DepartmentService departmentService;
    private final TypeService typeService;
    private final MessageService messageService;
    private final UserService userService;

    public boolean existsById(ObjectId id) {
        return typeService.existsById(id, EntityClassName.EMPLOYEE, Employee.class);
    }

    public Employee getById(ObjectId id) {
        return typeService.findById(id, EntityClassName.EMPLOYEE, Employee.class)
                .orElseThrow(() -> new NotFoundException(
                        messageService.getMessage(MessageMethod.EMPLOYEE_NOT_FOUND, id.toString())
                ));
    }

    public void makeReply(String dirTitle, ObjectId employId) {
        String dTitle = directionService.findByTitle(dirTitle).getTitle();
        Enrollee enrollee = employeeMapper.toEnrollee(getById(employId), dTitle);
        mongoOperations.save(enrollee); //FIXME  mb not working and add check by department

    }

    public Page<Employee> getAll(int p, int l) {
        return typeService.getPage(p, l, EntityClassName.EMPLOYEE, Employee.class);
    }

    public void addExp(ObjectId id, WorkExperience workExperience) {
        if (existsById(id))
            mongoOperations.update(Employee.class)
                    .matching(Query.query(Criteria.where("_id").is(id)))
                    .apply(new Update().addToSet("workExperiences", workExperience))
                    .findAndModifyValue();
        else throw new NotFoundException(
                messageService.getMessage(MessageMethod.EMPLOYEE_NOT_FOUND, id.toString())
        );
    }

    public Employee create(Employee empl) {
        userService.safeExist(empl.getUsername());
        if (departmentService.existsByTitle(empl.getDepartment()))
            return mongoOperations.save(empl);
        else throw new NotFoundException(
                messageService.getMessage(MessageMethod.DEPARTMENT_NOT_FOUND, empl.getDepartment())
        );
    }

    public void addToDep(String depTitle, ObjectId id) {
        if (existsById(id)) {
            if (departmentService.existsByTitle(depTitle))
                mongoOperations.update(Employee.class)
                        .matching(Query.query(Criteria.where("_id").is(id)))
                        .apply(Update.update("department", depTitle))
                        .findAndModifyValue();
            else throw new NotFoundException(
                    messageService.getMessage(MessageMethod.DEPARTMENT_NOT_FOUND)
            );
        } else throw new NotFoundException(messageService.getMessage(MessageMethod.EMPLOYEE_NOT_FOUND, id.toString()));
    }

}
