package com.is1di.userservicegradle.controller.direction;

import com.is1di.userservicegradle.dto.direction.DirectionDto;
import com.is1di.userservicegradle.dto.users.CuratorDto;
import com.is1di.userservicegradle.mapper.direction.DirectionMapper;
import com.is1di.userservicegradle.mapper.users.CuratorMapper;
import com.is1di.userservicegradle.service.direction.DirectionService;
import com.is1di.userservicegradle.service.users.CuratorService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("direction")
@RestController
public class DirectionController {
    private final DirectionService directionService;
    private final DirectionMapper directionMapper;
    private final CuratorService curatorService;
    private final CuratorMapper curatorMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public DirectionDto.Output create(@RequestBody DirectionDto.Create dto) {
        return directionMapper.toOutput(directionService.create(directionMapper.toEntity(dto)));
    }

    @GetMapping("{dirTitle}")
    public DirectionDto.Output findById(@PathVariable String dirTitle) {
        return directionMapper.toOutput(directionService.findByTitle(dirTitle));
    }

    @GetMapping
    public Page<DirectionDto.Output> page(@RequestParam(required = false, defaultValue = "1") int p,
                                          @RequestParam(required = false, defaultValue = "10") int l) {
        return directionMapper.toPage(directionService.getPage(p, l));
    }

/*    @PostMapping("{dirId}/reply/{abiturId}") //TODO security principal
    public ResponseEntity<?> makeReply(@PathVariable ObjectId dirId, @PathVariable ObjectId abiturId) {
        employeeService.makeReply(dirId, abiturId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }*/

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("{dirTitle}/curator/{curId}") //TODO security
    public ResponseEntity<?> addCurator(@PathVariable String dirTitle, @PathVariable ObjectId curId) {
        curatorService.addCurator(dirTitle, curId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("{dirTitle}/curator")
    public CuratorDto.Output getCuratorByDirection(@PathVariable String dirTitle) {
        return curatorMapper.toOutput(curatorService.getByDirection(dirTitle));
    }
}
