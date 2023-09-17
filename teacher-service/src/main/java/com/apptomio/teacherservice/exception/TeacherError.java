package com.apptomio.teacherservice.exception;

public enum TeacherError {

    TEACHER_NOT_FOUND("Teacher not found!"),
    TEACHER_STATUS_IS_NOT_ACTIVE("Teacher status is not Active!"),
    TEACHER_EMAIL_ALREADY_EXISTS("Teacher email already exists!");

    private String message;

    TeacherError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
