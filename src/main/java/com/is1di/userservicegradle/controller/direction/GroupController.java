package com.is1di.userservicegradle.controller.direction;

import com.is1di.userservicegradle.dto.direction.GroupDto;
import com.is1di.userservicegradle.mapper.direction.GroupMapper;
import com.is1di.userservicegradle.service.direction.GroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("direction/{dirTitle}/group")
public class GroupController {
    private final GroupService groupService;
    private final GroupMapper groupMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public GroupDto.Output createGroup(@PathVariable String dirTitle,
                                       @RequestBody @Valid GroupDto.Create group) {
        return groupMapper.toOutput(groupService.create(dirTitle, groupMapper.toEntity(group)));
    }

    @GetMapping("{studGroup}")
    public GroupDto.Output getOne(@PathVariable String dirTitle, @PathVariable String studGroup) {
        return groupMapper.toOutput(groupService.findByDirectionTitle(dirTitle, studGroup));
    }

    @GetMapping
    public List<GroupDto.Output> getAllByDir(@PathVariable String dirTitle) {
        return groupService.findAll(dirTitle).stream().map(groupMapper::toOutput).collect(Collectors.toList());
    }

}
