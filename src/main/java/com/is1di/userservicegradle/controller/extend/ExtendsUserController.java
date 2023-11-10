package com.is1di.userservicegradle.controller.extend;

import com.is1di.userservicegradle.dto.extend.EmployeeDto;
import com.is1di.userservicegradle.dto.extend.SupervisorDto;
import com.is1di.userservicegradle.entity.extend.WorkExperience;
import com.is1di.userservicegradle.mapper.extend.EmployeeMapper;
import com.is1di.userservicegradle.mapper.extend.SupervisorMapper;
import com.is1di.userservicegradle.service.extend.EmployeeService;
import com.is1di.userservicegradle.service.extend.SupervisorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("extends/user")
@RequiredArgsConstructor
public class ExtendsUserController {
    private final EmployeeService employeeService;
    private final SupervisorService supervisorService;
    private final EmployeeMapper employeeMapper;
    private final SupervisorMapper supervisorMapper;

    @GetMapping("employee")
    public Page<EmployeeDto.Output> getEmployeePage(@RequestParam(required = false, defaultValue = "1") int p,
                                                    @RequestParam(required = false, defaultValue = "10") int l) {
        return employeeService.getAll(p, l).map(employeeMapper::toOutput);
    }

    @GetMapping("employee/{id}")
    public EmployeeDto.Output getEmployeeById(@PathVariable ObjectId id) {
        return employeeMapper.toOutput(employeeService.getById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("employee")
    public EmployeeDto.Output create(@RequestBody @Valid EmployeeDto.Create dto) {
        return employeeMapper.toOutput(employeeService.create(employeeMapper.toEntity(dto)));
    }

    @GetMapping("supervisor")
    public Page<SupervisorDto.Output> getSupervisorPage(@RequestParam(required = false, defaultValue = "1") int p,
                                                        @RequestParam(required = false, defaultValue = "10") int l) {
        return supervisorService.getPage(p, l).map(supervisorMapper::toOutput);
    }

    @GetMapping("supervisor/{id}")
    public SupervisorDto.Output getSupervisorById(@PathVariable ObjectId id) {
        return supervisorMapper.toOutput(supervisorService.getById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("supervisor")
    public SupervisorDto.Output create(@RequestBody @Valid SupervisorDto.Create dto) {
        return supervisorMapper.toOutput(supervisorService.create(supervisorMapper.toEntity(dto)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("employee/{id}")
    public ResponseEntity<?> addExp(@PathVariable ObjectId id, @RequestBody @Valid WorkExperience exp) {
        employeeService.addExp(id, exp);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
