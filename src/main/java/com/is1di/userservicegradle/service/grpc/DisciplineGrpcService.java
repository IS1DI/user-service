package com.is1di.userservicegradle.service.grpc;

import com.is1di.userservicegradle.entity.direction.Discipline;
import com.is1di.userservicegradle.service.direction.DirectionService;
import com.is1di.userservicegradle.service.direction.DisciplineService;
import com.is1di.userservicegradle.service.users.TeacherService;
import com.university.userservice.grpc.academicsubject.AcademicSubjectServiceGrpc;
import com.university.userservice.grpc.academicsubject.AcademicSubjectRequest;
import com.university.userservice.grpc.course.CourseRequest;
import com.university.userservice.grpc.course.CourseServiceGrpc;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.lognet.springboot.grpc.GRpcService;

import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@GRpcService
public class DisciplineGrpcService {
    private final AcademicSubjectServiceGrpc.AcademicSubjectServiceBlockingStub academicServiceBlockingStub;

    private final CourseServiceGrpc.CourseServiceBlockingStub courseServiceBlockingStub;

    private final DisciplineService disciplineService;
    private final TeacherService teacherService;

    public Discipline create(String dirTitle, Discipline discipline) {
        discipline.setCourseId(discipline.getTitle());
        discipline.setAcademicSubjectId(discipline.getTitle());
        Discipline created = disciplineService.create(dirTitle, discipline);
        String teacherFullName = teacherService.getById(created.getTeacherId()).getFullName();
        discipline.setAcademicSubjectId(
                academicServiceBlockingStub.createAcademicSubject(
                        AcademicSubjectRequest.newBuilder()
                                .setTeacherId(created.getTeacherId().toString())
                                .setTeacherFullName(teacherFullName)
                                .setTitle(created.getTitle())
                                .setDirection(dirTitle)
                                .build()
                ).getAcademicSubjectId()
        );
        discipline.setCourseId(
                courseServiceBlockingStub.createCourse(
                        CourseRequest.newBuilder()
                                .setTitle(created.getTitle())
                                .setTeacherFullName(teacherFullName)
                                .setTeacherId(created.getTeacherId().toString())
                                .setDescription(created.getDescription())
                                .setStartAt(created.getStartsAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                                .setEndsAt(created.getEndsAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                                .setDirectionTitle(dirTitle)
                                .build()
                ).getCourseId()
        );
        return created;
    }
}
