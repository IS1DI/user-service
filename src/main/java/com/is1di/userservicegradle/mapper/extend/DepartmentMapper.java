package com.is1di.userservicegradle.mapper.extend;

import com.is1di.userservicegradle.dto.extend.DepartmentDto;
import com.is1di.userservicegradle.entity.extend.Department;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class DepartmentMapper {
    public ObjectId toId(String id) {
        return new ObjectId(id);
    }

    public String toStr(ObjectId id) {
        if (id != null)
            return id.toString();
        return null;
    }

    @Mapping(target = "supervisorId", expression = "java(toStr(department.getSupervisor() != null ? department.getSupervisor().getId() : null ))")
    @Mapping(target = "employeeIds", expression = "java(department.getEmployees() != null?department.getEmployees().stream().map(com.is1di.userservicegradle.entity.extend.Employee::getId).map(this::toStr).collect(java.util.stream.Collectors.toSet()) : new java.util.HashSet<>())")
    public abstract DepartmentDto.Output toOutput(Department department);

    public abstract Department toEntity(DepartmentDto.Create dto);
}
