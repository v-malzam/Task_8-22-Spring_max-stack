package com.cbr.university.controller;

import com.cbr.university.dto.TeacherDto;
import com.cbr.university.model.Course;
import com.cbr.university.model.Teacher;
import com.cbr.university.service.BaseService;
import com.cbr.university.validation.group.RequestUI;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("editing/teachers")
@Validated
public class TeacherController {
    private static final String TEACHER = "teacher";
    private static final String COURSES = "courses";
    private final ModelAndView mv = new ModelAndView();
    private final ModelMapper modelMapper;
    private final BaseService<Teacher> teacherService;
    private final BaseService<Course> courseService;

    @Autowired
    public TeacherController(BaseService<Teacher> teacherService, BaseService<Course> courseService,
                             ModelMapper modelMapper) {
        this.teacherService = teacherService;
        this.courseService = courseService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ModelAndView getAll() {
        mv.clear();
        mv.setViewName("editing/teachers/view");
        mv.addObject("teachers", teacherService.getAll());
        return mv;
    }

    @GetMapping("add")
    public ModelAndView add() {
        mv.clear();
        mv.setViewName("editing/teachers/add");
        mv.addObject(TEACHER, new Teacher());
        mv.addObject(COURSES, courseService.getAll());
        return mv;
    }

    @PostMapping("add")
    public ModelAndView add(@Validated(RequestUI.class) TeacherDto teacherDto,
            BindingResult result) {
        Teacher teacher = modelMapper.map(teacherDto, Teacher.class);
        if (result.hasErrors()) {
            mv.clear();
            mv.setViewName("editing/teachers/add");
            mv.addObject(TEACHER, teacher);
            mv.addObject(COURSES, courseService.getAll());
            return mv;
        } else {
            teacherService.create(teacher);
            return getAll();
        }
    }

    @GetMapping("edit/{id}")
    public ModelAndView edit(@PathVariable int id) {
        mv.clear();
        mv.setViewName("editing/teachers/edit");
        mv.addObject(TEACHER, teacherService.getById(id));
        mv.addObject(COURSES, courseService.getAll());
        return mv;
    }

    @PostMapping("edit/{id}")
    public ModelAndView edit(@Validated(RequestUI.class) TeacherDto teacherDto,
            BindingResult result) {
        Teacher teacher = modelMapper.map(teacherDto, Teacher.class);
        if (result.hasErrors()) {
            mv.clear();
            mv.setViewName("editing/teachers/edit");
            mv.addObject(TEACHER, teacher);
            mv.addObject(COURSES, courseService.getAll());
            return mv;
        } else {
            teacherService.update(teacher);
            return getAll();
        }
    }

    @GetMapping("delete/{id}")
    public ModelAndView delete(@PathVariable int id) {
        teacherService.delete(teacherService.getById(id));
        return getAll();
    }
}
