package com.is1di.userservicegradle.controller.extend;

import com.is1di.userservicegradle.dto.extend.DepartmentDto;
import com.is1di.userservicegradle.mapper.extend.DepartmentMapper;
import com.is1di.userservicegradle.service.extend.DepartmentService;
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
@RequestMapping("extends/department")
@RequiredArgsConstructor
public class ExtendsDepartmentController {
    private final DepartmentService departmentService;
    private final DepartmentMapper departmentMapper;
    private final EmployeeService employeeService;
    private final SupervisorService supervisorService;

    @GetMapping
    public Page<DepartmentDto.Output> getDepartmentPage(@RequestParam(required = false, defaultValue = "1") int p,
                                                        @RequestParam(required = false, defaultValue = "10") int l) {
        return departmentService.getPage(p, l).map(departmentMapper::toOutput);
    }

    @GetMapping("{title}")
    public DepartmentDto.Output getByTitle(@PathVariable String title) {
        return departmentMapper.toOutput(departmentService.getByTitle(title));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{depTitle}/employee/{id}")
    public ResponseEntity<?> addToDep(@PathVariable String depTitle, @PathVariable ObjectId id) {
        employeeService.addToDep(depTitle, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{depTitle}/supervisor/{id}")
    public ResponseEntity<?> addSuperToDep(@PathVariable String depTitle, @PathVariable ObjectId id) {
        supervisorService.addToDep(depTitle, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public DepartmentDto.Output create(@RequestBody @Valid DepartmentDto.Create dep) {
        return departmentMapper.toOutput(departmentService.create(departmentMapper.toEntity(dep)));
    }
}
