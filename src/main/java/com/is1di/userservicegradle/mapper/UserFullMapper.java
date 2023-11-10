package com.is1di.userservicegradle.mapper;

import com.is1di.userservicegradle.dto.UserFullDto;
import com.is1di.userservicegradle.entity.users.UserFull;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public abstract class UserFullMapper {
    public ObjectId toId(String id) {
        return new ObjectId(id);
    }

    public String toStr(ObjectId id) {
        if (id != null)
            return id.toString();
        return null;
    }

    @Mapping(target = "type", source = "_class")
    @Mapping(target = "imgUrl", qualifiedByName = {"UserMapper", "defaultPicture"})
    public abstract UserFullDto.Output toOutput(UserFull userFull);

    @Mapping(target = "type", source = "_class")
    @Mapping(target = "imgUrl", qualifiedByName = {"UserMapper", "defaultPicture"})
    public abstract UserFullDto.OutputWithRole toOutputWithRole(UserFull userFull);
}
