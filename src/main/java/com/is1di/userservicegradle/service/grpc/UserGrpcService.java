package com.is1di.userservicegradle.service.grpc;

import com.is1di.userservicegradle.entity.direction.Group;
import com.is1di.userservicegradle.entity.extend.Employee;
import com.is1di.userservicegradle.entity.users.Enrollee;
import com.is1di.userservicegradle.entity.users.UserFull;
import com.is1di.userservicegradle.service.UserFullService;
import com.is1di.userservicegradle.service.direction.DirectionService;
import com.is1di.userservicegradle.service.direction.GroupService;
import com.is1di.userservicegradle.service.extend.EmployeeService;
import com.is1di.userservicegradle.service.users.EnrolleeService;
import com.is1di.userservicegradle.utils.StorageConstants;
import com.university.userservice.grpc.user.*;
import com.university.userservice.grpc.user.UserServiceGrpc.UserServiceImplBase;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.lognet.springboot.grpc.GRpcService;

import java.util.Comparator;
import java.util.stream.Collectors;

@GRpcService
@RequiredArgsConstructor
public class UserGrpcService extends UserServiceImplBase {
    private final UserFullService userFullService;
    private final StorageConstants storageConstants;
    private final EmployeeService employeeService;
    private final DirectionService directionService;
    private final EnrolleeService enrolleeService;
    private final GroupService groupService;

    @Override
    public void getUserById(UserRequest request, StreamObserver<UserResponse> responseObserver) {
        ObjectId userId = new ObjectId(request.getUserId());
        UserFull user = userFullService.findById(userId);
        responseObserver.onNext(UserResponse.newBuilder()
                .setFullName(user.getFullName())
                .setImgUrl(user.getImgUrl() == null ? storageConstants.getDefaultUrlPath() : user.getImgUrl())
                .setType(user.get_class())
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void getWorkExperiencesById(UserRequest request, StreamObserver<WorkExperiences> responseObserver) {
        ObjectId userId = new ObjectId(request.getUserId());
        Employee employee = employeeService.getById(userId);
        responseObserver.onNext(WorkExperiences.newBuilder()
                .addAllWorkExperiences(
                        employee.getWorkExperiences().stream().map(wE ->
                                WorkExperience.newBuilder()
                                        .setStartDate(wE.getStartDate().toString())
                                        .setEndDate(wE.getEndDate().toString())
                                        .setDepartmentName(wE.getDepartmentName())
                                        .setDescription(wE.getDescription())
                                        .build()
                        ).collect(Collectors.toList())
                ).build());
        responseObserver.onCompleted();
    }

    @Override
    public void setEnrolleeStatus(UserWithDirRequest request, StreamObserver<EmptyMessage> responseObserver) {
        ObjectId userId = new ObjectId(request.getUserId());
        employeeService.makeReply(request.getDirectionTitle(), userId);
        responseObserver.onNext(EmptyMessage.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void setStudentStatus(UserRequest request, StreamObserver<EmptyMessage> responseObserver) {
        ObjectId userId = new ObjectId(request.getUserId());
        Enrollee e = enrolleeService.getById(userId);
        directionService.findByTitle(e.getDirection())
                .getGroups()
                .stream()
                .min(Comparator.comparingInt(o -> Math.toIntExact(groupService.countStudentsInGroup(o.getStudGroup()))))
                .ifPresentOrElse(
                        gr -> enrolleeService.addToGroup(userId,gr.getStudGroup()),
                        () -> {
                            Group newGroup = new Group();
                            newGroup.setStudGroup(e.getDirection() + "-1");
                            newGroup = groupService.create(e.getDirection(),newGroup);
                            enrolleeService.addToGroup(userId, newGroup.getStudGroup());
                        });
        responseObserver.onNext(EmptyMessage.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void setEmployeeStatus(UserRequest request, StreamObserver<EmptyMessage> responseObserver) {
        //TODO
    }
}
