package com.is1di.userservicegradle.mapper.users;

import com.is1di.userservicegradle.dto.users.EnrolleeDto;
import com.is1di.userservicegradle.entity.users.Enrollee;
import com.is1di.userservicegradle.entity.users.Student;
import com.is1di.userservicegradle.mapper.UserMapper;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public abstract class EnrolleeMapper {
    public ObjectId toId(String id) {
        return new ObjectId(id);
    }

    public String toStr(ObjectId id) {
        if (id != null)
            return id.toString();
        return null;
    }

    @Mapping(target = "imgUrl", qualifiedByName = {"UserMapper", "defaultPicture"})
    @Mapping(target = "role", expression = "java(com.is1di.userservicegradle.security.RoleConstants.STUDENT)")
    @Mapping(target = "studGroup", expression = "java(studGroup)")
    public abstract Student toStudent(Enrollee enrollee, String studGroup);

    @Mapping(target = "imgUrl", qualifiedByName = {"UserMapper", "defaultPicture"})
    public abstract EnrolleeDto.Output toOutput(Enrollee enrollee);

    @Mapping(target = "imgUrl", qualifiedByName = {"UserMapper", "defaultPicture"})
    @Mapping(target = "enabled", expression = "java(true)")
    @Mapping(target = "role", expression = "java(com.is1di.userservicegradle.security.RoleConstants.ENROLLEE)")
    public abstract Enrollee toEntity(EnrolleeDto.Create dto);
}
