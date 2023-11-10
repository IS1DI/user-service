package com.is1di.userservicegradle.mapper.users;

import com.is1di.userservicegradle.dto.users.CuratorDto;
import com.is1di.userservicegradle.entity.users.Curator;
import com.is1di.userservicegradle.mapper.UserMapper;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public abstract class CuratorMapper {
    public ObjectId toId(String id) {
        return new ObjectId(id);
    }

    public String toStr(ObjectId id) {
        if (id != null)
            return id.toString();
        return null;
    }

    @Mapping(target = "imgUrl", qualifiedByName = {"UserMapper", "defaultPicture"})
    public abstract CuratorDto.Output toOutput(Curator curator);

    @Mapping(target = "imgUrl", qualifiedByName = {"UserMapper", "defaultPicture"})
    @Mapping(target = "enabled", expression = "java(true)")
    @Mapping(target = "role", expression = "java(com.is1di.userservicegradle.security.RoleConstants.CURATOR)")
    public abstract Curator toEntity(CuratorDto.Create dto);
}
