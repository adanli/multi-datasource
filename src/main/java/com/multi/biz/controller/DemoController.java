package com.multi.biz.controller;

import com.multi.biz.service.ProjectService;
import com.multi.biz.service.UserService;
import com.multi.common.db.model.one.User;
import com.multi.common.db.model.two.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author adanl
 */
@RestController
@RequestMapping(value = "/rest/demoService/v1")
public class DemoController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;

    @PostMapping(value = "/save/one")
    public String saveOne() {
        User user = new User();
        user.setName("one");
        user.setAge(24);
        userService.save(user);
        return "ok";
    }

    @PostMapping(value = "/save/two")
    public String saveTwo() {
        Project project = new Project();
        project.setName("two");
        project.setCode("two");
        projectService.save(project);
        return "ok";
    }

}
