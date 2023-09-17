package com.apptomio.teacherservice.exception;

public class TeacherException extends RuntimeException {

    private TeacherError teacherError;

    public TeacherException(TeacherError teacherError){
        this.teacherError = teacherError;
    }

    public TeacherError getTeacherError() {
        return teacherError;
    }
}
