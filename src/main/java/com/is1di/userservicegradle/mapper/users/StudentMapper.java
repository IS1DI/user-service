package com.is1di.userservicegradle.mapper.users;

import com.is1di.userservicegradle.dto.users.StudentDto;
import com.is1di.userservicegradle.entity.users.Student;
import com.is1di.userservicegradle.mapper.UserMapper;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public abstract class StudentMapper {
    public ObjectId toId(String id) {
        return new ObjectId(id);
    }

    public String toStr(ObjectId id) {
        if (id != null)
            return id.toString();
        return null;
    }

    @Mapping(target = "imgUrl", qualifiedByName = {"UserMapper", "defaultPicture"})
    public abstract StudentDto.Output toOutput(Student student);

    @Mapping(target = "enabled", expression = "java(true)")
    @Mapping(target = "role", expression = "java(com.is1di.userservicegradle.security.RoleConstants.STUDENT)")
    @Mapping(target = "imgUrl", qualifiedByName = {"UserMapper", "defaultPicture"})
    public abstract Student toEntity(StudentDto.Create dto);
}
