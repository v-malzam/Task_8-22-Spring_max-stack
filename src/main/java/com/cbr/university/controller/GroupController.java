package com.cbr.university.controller;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cbr.university.model.Group;
import com.cbr.university.service.BaseService;

@Controller
@RequestMapping("groups")
public class GroupController {
    private static final String GROUPS = "groups";
    private static final String GROUPS_ADD = "group-add";
    private static final String GROUPS_EDIT = "group-edit";
    private BaseService<Group> groupService;
    private EntityManager entityManager;
    private ModelAndView mv = new ModelAndView();

    @Autowired
    public GroupController(BaseService<Group> groupService, EntityManager entityManager) {
        this.groupService = groupService;
        this.entityManager = entityManager;
    }

    @GetMapping
    public ModelAndView getAll() {
        entityManager.clear();
        mv.clear();
        mv.setViewName(GROUPS);
        mv.addObject(GROUPS, groupService.getAll());
        return mv;
    }

    @GetMapping("add")
    public ModelAndView add() {
        mv.clear();
        mv.setViewName(GROUPS_ADD);
        return mv;
    }

    @PostMapping("add")
    public ModelAndView add(Group group, BindingResult result) {
        groupService.create(group);
        return getAll();
    }

    @GetMapping("edit/{id}")
    public ModelAndView edit(@PathVariable int id) {
        mv.clear();
        mv.setViewName(GROUPS_EDIT);
        mv.addObject("group", groupService.getById(id));
        return mv;
    }

    @PostMapping("edit/{id}")
    public ModelAndView edit(Group group) {
        groupService.update(group);
        return getAll();
    }

    @GetMapping("delete/{id}")
    public ModelAndView delete(@PathVariable int id) {
        groupService.delete(groupService.getById(id));
        return getAll();
    }
}
