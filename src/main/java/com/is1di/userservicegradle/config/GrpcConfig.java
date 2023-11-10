package com.is1di.userservicegradle.config;

import com.university.userservice.grpc.academicsubject.AcademicSubjectServiceGrpc;
import com.university.userservice.grpc.course.CourseServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GrpcConfig {
    private final GrpcConfigProperties grpcConfigProperties;

    @Bean
    public AcademicSubjectServiceGrpc.AcademicSubjectServiceBlockingStub academicServiceBlockingStub() {
        ManagedChannel channel = ManagedChannelBuilder.forTarget(grpcConfigProperties.getClient().get("academic").getAddress())
                .usePlaintext()
                .build();
        return AcademicSubjectServiceGrpc.newBlockingStub(channel);
    }

    @Bean
    public CourseServiceGrpc.CourseServiceBlockingStub courseServiceBlockingStub() {
        ManagedChannel channel = ManagedChannelBuilder.forTarget(grpcConfigProperties.getClient().get("course").getAddress())
                .usePlaintext()
                .build();
        return CourseServiceGrpc.newBlockingStub(channel);
    }
}
