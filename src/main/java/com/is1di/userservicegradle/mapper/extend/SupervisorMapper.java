package com.is1di.userservicegradle.mapper.extend;

import com.is1di.userservicegradle.dto.extend.SupervisorDto;
import com.is1di.userservicegradle.entity.extend.Supervisor;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class SupervisorMapper {
    public ObjectId toId(String id) {
        return new ObjectId(id);
    }

    public String toStr(ObjectId id) {
        if (id != null)
            return id.toString();
        return null;
    }

    public abstract SupervisorDto.Output toOutput(Supervisor supervisor);

    @Mapping(target = "enabled", expression = "java(true)")
    @Mapping(target = "role", expression = "java(com.is1di.userservicegradle.security.RoleConstants.SUPERVISOR)")
    public abstract Supervisor toEntity(SupervisorDto.Create supervisor);
}
