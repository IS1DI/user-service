package com.is1di.userservicegradle.controller.user;

import com.is1di.userservicegradle.dto.UserDto;
import com.is1di.userservicegradle.dto.UserFullDto;
import com.is1di.userservicegradle.dto.users.CuratorDto;
import com.is1di.userservicegradle.dto.users.EnrolleeDto;
import com.is1di.userservicegradle.dto.users.StudentDto;
import com.is1di.userservicegradle.dto.users.TeacherDto;
import com.is1di.userservicegradle.mapper.UserFullMapper;
import com.is1di.userservicegradle.mapper.UserMapper;
import com.is1di.userservicegradle.mapper.users.CuratorMapper;
import com.is1di.userservicegradle.mapper.users.EnrolleeMapper;
import com.is1di.userservicegradle.mapper.users.StudentMapper;
import com.is1di.userservicegradle.mapper.users.TeacherMapper;
import com.is1di.userservicegradle.service.UserFullService;
import com.is1di.userservicegradle.service.UserService;
import com.is1di.userservicegradle.service.users.CuratorService;
import com.is1di.userservicegradle.service.users.EnrolleeService;
import com.is1di.userservicegradle.service.users.StudentService;
import com.is1di.userservicegradle.service.users.TeacherService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {
    private final StudentMapper studentMapper;
    private final TeacherMapper teacherMapper;
    private final EnrolleeMapper enrolleeMapper;
    private final CuratorMapper curatorMapper;
    private final UserFullMapper userFullMapper;
    private final UserMapper userMapper;

    private final StudentService studentService;
    private final TeacherService teacherService;
    private final EnrolleeService enrolleeService;
    private final CuratorService curatorService;
    private final UserService userService;
    private final UserFullService userFullService;

    @GetMapping
    public Page<UserFullDto.Output> getUsersPage(@RequestParam(required = false, defaultValue = "1") int p,
                                                 @RequestParam(required = false, defaultValue = "10") int l) {
        return userFullService.getPage(p, l).map(userFullMapper::toOutput);
    }

    @GetMapping("admin")
    public Page<UserFullDto.OutputWithRole> getUsersPageWithRole(@RequestParam(required = false, defaultValue = "1") int p,
                                                                 @RequestParam(required = false, defaultValue = "10") int l) {
        return userFullService.getPage(p, l).map(userFullMapper::toOutputWithRole);
    }

    @GetMapping("{id}")
    public UserFullDto.Output getUser(@PathVariable ObjectId id) {
        return userFullMapper.toOutput(userFullService.findById(id));
    }

    @GetMapping("admin/{id}")
    public UserFullDto.OutputWithRole getUserWithRole(@PathVariable ObjectId id) {
        return userFullMapper.toOutputWithRole(userFullService.findById(id));
    }

    @GetMapping("search")
    public Page<UserFullDto.Output> getSearchPage(@RequestParam(required = false, defaultValue = "1") int p,
                                                  @RequestParam(required = false, defaultValue = "10") int l,
                                                  @RequestParam(required = false, defaultValue = " ") String q) {
        return userFullService.search(p, l, q).map(userFullMapper::toOutput);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("admission")
    public UserDto.Output create(@RequestBody UserDto.Create dto) {
        return userMapper.toOutput(userService.create(userMapper.toEntity(dto)));
    }

    @GetMapping("student")
    public Page<StudentDto.Output> getStudentsPage(@RequestParam(required = false, defaultValue = "1") int p,
                                                   @RequestParam(required = false, defaultValue = "10") int l) {
        return studentService.getAll(p, l).map(studentMapper::toOutput);
    }

    @GetMapping("student/{id}")
    public StudentDto.Output getStudent(@PathVariable ObjectId id) {
        return studentMapper.toOutput(studentService.getById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("student")
    public StudentDto.Output createStudent(@RequestBody StudentDto.Create dto) {
        return studentMapper.toOutput(studentService.create(studentMapper.toEntity(dto)));
    }

    @GetMapping("teacher")
    public Page<TeacherDto.Output> getTeachersPage(@RequestParam(required = false, defaultValue = "1") int p,
                                                   @RequestParam(required = false, defaultValue = "10") int l) {
        return teacherService.getAll(p, l).map(teacherMapper::toOutput);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("teacher")
    public TeacherDto.Output createTeacher(@RequestBody TeacherDto.Create dto) {
        return teacherMapper.toOutput(teacherService.create(teacherMapper.toEntity(dto)));
    }

    @GetMapping("teacher/{id}")
    public TeacherDto.Output getTeacher(@PathVariable ObjectId id) {
        return teacherMapper.toOutput(teacherService.getById(id));
    }

    @GetMapping("enrollee")
    public Page<EnrolleeDto.Output> getEnrolleesPage(@RequestParam(required = false, defaultValue = "1") int p,
                                                     @RequestParam(required = false, defaultValue = "10") int l) {
        return enrolleeService.getAll(p, l).map(enrolleeMapper::toOutput);
    }

    @GetMapping("enrollee/{id}")
    public EnrolleeDto.Output getEnrollee(@PathVariable ObjectId id) {
        return enrolleeMapper.toOutput(enrolleeService.getById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("enrollee")
    public EnrolleeDto.Output createEnrollee(@RequestBody EnrolleeDto.Create dto) {
        return enrolleeMapper.toOutput(enrolleeService.create(enrolleeMapper.toEntity(dto)));
    }

    @GetMapping("curator")
    public Page<CuratorDto.Output> getCuratorsPage(@RequestParam(required = false, defaultValue = "1") int p,
                                                   @RequestParam(required = false, defaultValue = "10") int l) {
        return curatorService.getAll(p, l).map(curatorMapper::toOutput);
    }

    @GetMapping("curator/{id}")
    public CuratorDto.Output getCurator(@PathVariable ObjectId id) {
        return curatorMapper.toOutput(curatorService.getById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("curator")
    public CuratorDto.Output createCurator(@RequestBody CuratorDto.Create dto) {
        return curatorMapper.toOutput(curatorService.create(curatorMapper.toEntity(dto)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}/password")
    public ResponseEntity<?> setPassword(@PathVariable ObjectId id, @RequestParam String pass) {
        userService.setPassword(id, pass);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
