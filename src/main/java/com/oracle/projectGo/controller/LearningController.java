package com.oracle.projectGo.controller;

import com.oracle.projectGo.dto.DistributedHomeworks;
import com.oracle.projectGo.dto.Homeworks;
import com.oracle.projectGo.dto.Users;
import com.oracle.projectGo.service.HomeworkService;
import com.oracle.projectGo.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/learning")
public class LearningController {
    private final HomeworkService homeworkService;
    private final UsersService usersService;

    @RequestMapping("/signUpLearningGroup")
    public String signUpLearningGroup(Model model){
        Users users = usersService.getLoggedInUserInfo();
        model.addAttribute("users", users);

        return "learning/signUpLearningGroup";
    }

    @RequestMapping(value = "submitHomeworkForm")
    public String submitHomeworkForm(DistributedHomeworks distributedHomeworks, Model model) {
        Users user = usersService.getLoggedInUserInfo();

        distributedHomeworks.setUserId(user.getId());
        List<DistributedHomeworks> distributedHomeworksList = homeworkService.getDistributedHomeworksList(distributedHomeworks);
        for (DistributedHomeworks homeworks : distributedHomeworksList) {
            log.info("{}", homeworks);
        }
        model.addAttribute("user", user);
        model.addAttribute("distributedHomeworksList", distributedHomeworksList);

        return "learning/submitHomeworkForm";
    }



}
