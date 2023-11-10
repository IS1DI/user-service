package com.is1di.userservicegradle.controller;

import com.is1di.userservicegradle.dto.users.StudentDto;
import com.is1di.userservicegradle.mapper.users.StudentMapper;
import com.is1di.userservicegradle.service.users.EnrolleeService;
import com.is1di.userservicegradle.service.users.StudentService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("group/{studGroup}")
public class GroupChangeController {
    private final StudentMapper studentMapper;
    private final EnrolleeService enrolleeService;
    private final StudentService studentService;

    @GetMapping("students")
    public Page<StudentDto.Output> getPageStudents(@PathVariable String studGroup,
                                                   @RequestParam(required = false, defaultValue = "1") int p,
                                                   @RequestParam(required = false, defaultValue = "10") int l) {
        return studentService.getPageStudentsByGroup(studGroup, p, l).map(studentMapper::toOutput);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("student/{id}")
    public ResponseEntity<?> addToGroup(@PathVariable String studGroup, @PathVariable ObjectId id) {
        enrolleeService.addToGroup(id, studGroup);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
