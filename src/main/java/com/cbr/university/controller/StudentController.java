package com.cbr.university.controller;

import com.cbr.university.model.Group;
import com.cbr.university.model.Student;
import com.cbr.university.service.BaseService;
import com.cbr.university.validation.group.RequestUI;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("editing/students")
@Validated
public class StudentController {
    private static final String STUDENT = "student";
    private static final String GROUPS = "groups";
    private final BaseService<Student> studentService;
    private final BaseService<Group> groupService;

    public StudentController(BaseService<Student> studentService, BaseService<Group> groupService) {
        this.studentService = studentService;
        this.groupService = groupService;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("students", studentService.getAll());
        return "editing/students/view";
    }

    @GetMapping("add")
    public String add(Model model) {
        model.addAttribute(STUDENT, Student.class);
        model.addAttribute(GROUPS, groupService.getAll());
        return "editing/students/add";
    }

    @PostMapping("add")
    public String add(@Validated(RequestUI.class) Student student, Model model, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute(STUDENT, student);
            model.addAttribute(GROUPS, groupService.getAll());
            return "editing/students/add";
        } else {
            studentService.create(student);
            return getAll(model);
        }
    }

    @GetMapping("edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        model.addAttribute(studentService.getById(id));
        model.addAttribute(GROUPS, groupService.getAll());
        return "editing/students/edit";
    }

    @PostMapping("edit")
    public String edit(@Validated(RequestUI.class) Student student, Model model, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute(STUDENT, student);
            model.addAttribute(GROUPS, groupService.getAll());
            return "editing/students/edit";
        } else {
            studentService.update(student);
            return getAll(model);
        }
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable int id, Model model) {
        studentService.deleteById(id);
        return getAll(model);
    }
}
