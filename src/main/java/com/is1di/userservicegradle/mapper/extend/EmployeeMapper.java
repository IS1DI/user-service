package com.is1di.userservicegradle.mapper.extend;

import com.is1di.userservicegradle.dto.extend.EmployeeDto;
import com.is1di.userservicegradle.entity.extend.Employee;
import com.is1di.userservicegradle.entity.users.Enrollee;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class EmployeeMapper {
    public ObjectId toId(String id) {
        return new ObjectId(id);
    }

    public String toStr(ObjectId id) {
        if (id != null)
            return id.toString();
        return null;
    }

    @Mapping(target = "role", expression = "java(com.is1di.userservicegradle.security.RoleConstants.ENROLLEE)")
    @Mapping(target = "direction", expression = "java(direction)")
    public abstract Enrollee toEnrollee(Employee employee, String direction);

    public abstract EmployeeDto.Output toOutput(Employee employee);

    @Mapping(target = "enabled", expression = "java(true)")
    @Mapping(target = "role", expression = "java(com.is1di.userservicegradle.security.RoleConstants.EMPLOYEE)")
    public abstract Employee toEntity(EmployeeDto.Create empl);
}
