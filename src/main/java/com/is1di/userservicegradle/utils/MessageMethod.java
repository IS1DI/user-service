package com.is1di.userservicegradle.utils;

import lombok.Getter;

@Getter
public enum MessageMethod {

    DIRECTION_NOT_FOUND("dir.error.notFound"),
    DIRECTION_ALREADY_EXISTS("dir.error.alreadyExists"),

    GROUP_NOT_FOUND("group.error.notFound"),
    GROUP_ALREADY_EXISTS("group.error.alreadyExists"),
    DISC_NOT_FOUND("disc.error.notFound"),
    DISC_ALREADY_EXISTS("disc.error.alreadyExists"),

    CURATOR_NOT_FOUND("curator.error.notFound"),
    ENROLLEE_NOT_FOUND("enrollee.error.notFound"),
    STUDENT_NOT_FOUND("student.error.notFound"),
    TEACHER_NOT_FOUND("teacher.error.notFound"),
    USER_NOT_FOUND("user.error.notFound"),

    DEPARTMENT_NOT_FOUND("department.error.notFound"),
    DEPARTMENT_ALREADY_EXISTS("department.error.alreadyExists"),
    SUPERVISOR_NOT_FOUND("supervisor.error.notFound"),
    EMPLOYEE_NOT_FOUND("employee.error.notFound"), USERNAME_ALREADY_EXISTS("user.username.alreadyExists");

    private final String val;

    MessageMethod(String val) {
        this.val = val;
    }

}
