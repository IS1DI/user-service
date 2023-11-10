package com.is1di.userservicegradle.mapper;

import com.is1di.userservicegradle.dto.UserDto;
import com.is1di.userservicegradle.entity.users.User;
import com.is1di.userservicegradle.service.direction.DirectionService;
import com.is1di.userservicegradle.utils.StorageConstants;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Named("UserMapper")
@Mapper(componentModel = "spring")
public abstract class UserMapper { //TODO
    @Autowired
    private StorageConstants storageConstants;
    @Autowired
    private DirectionService directionService;

    @Named("defaultPicture")
    public String defaultPictureIfNull(String imgUrl) {
        if (imgUrl == null) {
            return storageConstants.getDefaultUrlPath();
        }
        return imgUrl;
    }

    @Mapping(target = "imgUrl", qualifiedByName = "defaultPicture")
    public abstract UserDto.Output toOutput(User user);

    @Mapping(target = "enabled", expression = "java(true)")
    @Mapping(target = "role", expression = "java(com.is1di.userservicegradle.security.RoleConstants.ADMISSION)")
    @Mapping(target = "imgUrl", qualifiedByName = "defaultPicture")
    public abstract User toEntity(UserDto.Create dto);


    public ObjectId toId(String id) {
        return new ObjectId(id);
    }

    public String toStr(ObjectId id) {
        if (id != null)
            return id.toString();
        return null;
    }
}
