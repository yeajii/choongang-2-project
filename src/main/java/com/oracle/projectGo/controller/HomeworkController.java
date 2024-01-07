package com.oracle.projectGo.controller;

import com.oracle.projectGo.dto.GameContents;
import com.oracle.projectGo.dto.Homeworks;
import com.oracle.projectGo.dto.LearningGroup;
import com.oracle.projectGo.dto.Users;
import com.oracle.projectGo.service.*;
import com.oracle.projectGo.utils.error.BusinessException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/homework")
public class HomeworkController {
    private final HomeworkService homeworkService;
    private final LearningGroupService learningGroupService;
    private final UsersService usersService;
    private final GameService gameService;

    @RequestMapping(value = "insertHomeworkForm")
    public String insertHomeworkForm(Homeworks homework, String currentPage, Model model, RedirectAttributes redirectAttributes) {
        /* TODO : PRE-PROCESS*/
        int userId = usersService.getLoggedInId();
        homework.setUserId(userId);

        /* TODO: GET TOTAL HOMEWORKS COUNT  */
        int totalHomeworksCnt = homeworkService.getTotalHomeworksCnt(homework);
        log.info(homework.toString());

        /* TODO: GET GameContents LIST */
        List<GameContents> subscribedGameList= gameService.getSubscribedGameContents(userId);
        log.info("getSubscribedGameContents:{}",subscribedGameList);

        /* TODO: PAGING PROCESS */
        Paging page = new Paging(totalHomeworksCnt, currentPage);
        homework.setStart(page.getStart());
        homework.setEnd(page.getEnd());

        /* TODO: GET HOMEWORK LIST */
        List<Homeworks> homeworkList = homeworkService.getHomeworksList(homework);

        /* TODO: SET ATTRIBUTE */
        model.addAttribute("userId",userId);
        model.addAttribute("page", page);
        model.addAttribute("subscribedGameList", subscribedGameList);
        model.addAttribute("homeworkList", homeworkList);
        model.addAttribute("currentPage", currentPage);

        return "educate/homework/insertHomeworkForm";
    }

    // 숙제 리스트와 회원 리스트를 보여주는 화면
    @RequestMapping(value = "/distributeHomeworkForm")
    public String distributeHomeworkForm(Homeworks homework,LearningGroup learningGroup, Model model) {
        int userId = usersService.getLoggedInId();
        homework.setUserId(userId);
        List<LearningGroup> learningGroupList = null;
        List<Homeworks> homeworkList = null;
        List<String> homeworkTitleList = null;

        /* TODO: 게임 컨텐츠 리스트 받아오기 */
        List<GameContents> subscribedGameList= gameService.getSubscribedGameContents(userId);

        if (homework.getContentId() != 0) {

            /* TODO: 학습 그룹 리스트 받아오기 */
            learningGroup.setUserId(userId);
            learningGroupList = learningGroupService.learningGroupList(learningGroup);

            /* TODO: 숙제명에 따라 숙제를 받아오는 로직 */
            homeworkList = homeworkService.getHomeworksList(homework);

            homeworkTitleList = homeworkService.getDistinctHomeworkTitles(homework);

            /*만약 input로 받은 homework에 0이 아닌 contentId가 있다면
            * learningGroupList에서 contentId가 homework.getContentId()인 것들만 필터해서 반환하고
            * homeworkList도 마찬가지로 contenId가 있는것을 필터해서 반환하도록
            * */
            learningGroupList = learningGroupList.stream()
                    .filter(lg -> lg.getContentId() == homework.getContentId())
                    .collect(Collectors.toList());

            homeworkList = homeworkList.stream()
                    .filter(hw -> hw.getContentId() == homework.getContentId())
                    .collect(Collectors.toList());
        }
        /* 학습 그룹은 AJAX로. */

        /* TODO: SET ATTRIBUTES */
        model.addAttribute("userId",userId);
        model.addAttribute("homeworkTitleList", homeworkTitleList);
        model.addAttribute("subscribedGameList", subscribedGameList);
        model.addAttribute("learningGroupList", learningGroupList);
        model.addAttribute("homeworkList", homeworkList);
        model.addAttribute("searchOptions", homework);

        return "educate/homework/distributeHomeworkForm";
    }
    @RequestMapping(value = "/evaluateHomeworkForm")
    public String evaluateHomeworkForm(Homeworks homework, Model model) {
        /* TODO : PRE-PROCESS*/
        int userId = usersService.getLoggedInId();
        homework.setUserId(userId);

        /* TODO: 숙제명에 따라 숙제를 받아오는 로직 */
        List<Homeworks> homeworkList = homeworkService.getHomeworksList(homework);

        List<String> homeworkTitleList = homeworkService.getDistinctHomeworkTitles(homework);

        /* TODO: SET ATTRIBUTES */
        model.addAttribute("userId",userId);
        model.addAttribute("homeworkList", homeworkList);
        model.addAttribute("homeworkTitleList", homeworkTitleList);
        model.addAttribute("searchOptions", homework);

        return "educate/homework/evaluateHomeworkForm";
    }
}
