package com.is1di.userservicegradle.mapper.direction;

import com.is1di.userservicegradle.dto.direction.DisciplineDto;
import com.is1di.userservicegradle.entity.direction.Discipline;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class DisciplineMapper {
    public ObjectId toId(String id) {
        return new ObjectId(id);
    }

    public String toStr(ObjectId id) {
        if (id != null)
            return id.toString();
        return null;
    }

    public abstract Discipline toEntity(DisciplineDto.Create dto);

    public abstract Discipline toEntity(DisciplineDto.Update dto);

    public abstract DisciplineDto.Output toOutput(Discipline entity);
}
