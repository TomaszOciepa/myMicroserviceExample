package com.apptomio.teacherservice.controller;

import com.apptomio.teacherservice.model.Status;
import com.apptomio.teacherservice.model.Teacher;
import com.apptomio.teacherservice.service.TeacherService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
@AllArgsConstructor
public class TeacherConttroller {

    private final TeacherService teacherService;

    @GetMapping
    public List<Teacher> getAll(@RequestParam(required = false) Status status){
        return teacherService.getAll(status);
    }

    @GetMapping("/{id}")
    public Teacher getById(@PathVariable String id){
        return teacherService.getById(id);
    }

    @PostMapping
    public Teacher add(@RequestBody Teacher teacher){
        return teacherService.add(teacher);
    }

    @PutMapping
    public Teacher put(@PathVariable String id, @RequestBody Teacher teacher){
        return teacherService.put(id, teacher);
    }

    @PatchMapping
    public Teacher patch(@PathVariable String id, @RequestBody Teacher teacher){
        return teacherService.patch(id, teacher);
    }

    @DeleteMapping
    public void delete(@PathVariable String id){
        teacherService.delete(id);
    }

    @PostMapping("/enroll/{teacherId}/course/{courseName}")
    public void courseEnrollment(@PathVariable String teacherId, @PathVariable String courseName){
        teacherService.courseEnrollment(teacherId, courseName);
    }
}
