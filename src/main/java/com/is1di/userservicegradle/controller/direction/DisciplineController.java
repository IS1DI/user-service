package com.is1di.userservicegradle.controller.direction;

import com.is1di.userservicegradle.dto.direction.DisciplineDto;
import com.is1di.userservicegradle.dto.users.TeacherDto;
import com.is1di.userservicegradle.mapper.direction.DisciplineMapper;
import com.is1di.userservicegradle.mapper.users.TeacherMapper;
import com.is1di.userservicegradle.service.direction.DisciplineService;
import com.is1di.userservicegradle.service.grpc.DisciplineGrpcService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("direction/{dirTitle}/discipline")
public class DisciplineController {
    private final DisciplineMapper disciplineMapper;
    private final DisciplineService disciplineService;
    private final TeacherMapper teacherMapper;
    private final DisciplineGrpcService disciplineGrpcService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public DisciplineDto.Output create(@PathVariable String dirTitle, @RequestBody @Valid DisciplineDto.Create dto) {
        return disciplineMapper.toOutput(disciplineGrpcService.create(dirTitle, disciplineMapper.toEntity(dto)));
    }

    @GetMapping("{discTitle}")
    public DisciplineDto.Output getByDisc(@PathVariable String dirTitle, @PathVariable String discTitle) {
        return disciplineMapper.toOutput(disciplineService.getDiscipline(dirTitle, discTitle));
    }

    @GetMapping
    public List<DisciplineDto.Output> getAllByDir(@PathVariable String dirTitle) {
        return disciplineService.getAllByDir(dirTitle).stream().map(disciplineMapper::toOutput).collect(Collectors.toList());
    }

    @GetMapping("{discTitle}/teacher")
    public TeacherDto.Output getTeacherByDisc(@PathVariable String dirTitle, @PathVariable String discTitle) {
        return teacherMapper.toOutput(disciplineService.getByDisc(dirTitle, discTitle));
    }
}
