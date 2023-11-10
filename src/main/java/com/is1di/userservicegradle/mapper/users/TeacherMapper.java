package com.is1di.userservicegradle.mapper.users;

import com.is1di.userservicegradle.dto.users.TeacherDto;
import com.is1di.userservicegradle.entity.direction.Discipline;
import com.is1di.userservicegradle.entity.users.Teacher;
import com.is1di.userservicegradle.mapper.UserMapper;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public abstract class TeacherMapper {
    public ObjectId toId(String id) {
        return new ObjectId(id);
    }

    public String toStr(ObjectId id) {
        if (id != null)
            return id.toString();
        return null;
    }

    @Mapping(target = "imgUrl", qualifiedByName = {"UserMapper", "defaultPicture"})
    public abstract TeacherDto.Output toOutput(Teacher teacher);

    public String toStr(Discipline discipline) {
        return discipline.getTitle();
    }

    @Mapping(target = "disciplines", expression = "java(new java.util.HashSet<>())")
    @Mapping(target = "enabled", expression = "java(true)")
    @Mapping(target = "role", expression = "java(com.is1di.userservicegradle.security.RoleConstants.TEACHER)")
    @Mapping(target = "imgUrl", qualifiedByName = {"UserMapper", "defaultPicture"})
    public abstract Teacher toEntity(TeacherDto.Create dto);
}
