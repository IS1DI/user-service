package com.is1di.userservicegradle.mapper.direction;

import com.is1di.userservicegradle.dto.direction.GroupDto;
import com.is1di.userservicegradle.entity.direction.Group;
import com.is1di.userservicegradle.service.direction.GroupService;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class GroupMapper {
    public ObjectId toId(String id) {
        return new ObjectId(id);
    }

    public String toStr(ObjectId id) {
        if (id != null)
            return id.toString();
        return null;
    }
    @Autowired
    private GroupService groupService;
    @Mapping(target = "countStudents", expression = "java(getCount(group.getStudGroup()))")
    public abstract GroupDto.Output toOutput(Group group);

    public abstract Group toEntity(GroupDto.Create dto);

    public long getCount(String studGroup) {
        return groupService.countStudentsInGroup(studGroup);
    }
}
