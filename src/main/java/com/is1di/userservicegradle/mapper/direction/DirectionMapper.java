package com.is1di.userservicegradle.mapper.direction;

import com.is1di.userservicegradle.dto.direction.DirectionDto;
import com.is1di.userservicegradle.entity.direction.Direction;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public abstract class DirectionMapper {
    public abstract DirectionDto.Output toOutput(Direction entity);

    public abstract Direction toEntity(DirectionDto.Create dto);

    public ObjectId toId(String id) {
        return new ObjectId(id);
    }

    public String toStr(ObjectId id) {
        if (id != null)
            return id.toString();
        return null;
    }

    public Page<DirectionDto.Output> toPage(Page<Direction> page) {
        return page.map(this::toOutput);
    }
}
