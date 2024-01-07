package com.oracle.projectGo.controller;

import com.oracle.projectGo.dto.GameContents;
import com.oracle.projectGo.dto.LearningGroup;
import com.oracle.projectGo.dto.LearningGroupMember;
import com.oracle.projectGo.dto.Users;
import com.oracle.projectGo.service.LearningGroupService;
import com.oracle.projectGo.service.Paging;
import com.oracle.projectGo.service.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/group")
public class GroupController {

    private final LearningGroupService groupService;
    private final UsersService usersService;

    // 로그인한 회원(교육자)이 소유한 게임콘텐츠 리스트 조회
    @RequestMapping(value = "listLearningContent")
    public String listlearningContent(GameContents gameContents, String currentPage, Model model) {
        Users users = usersService.getLoggedInUserInfo();
        int userId = users.getId();

        try {
            // 초기 페이지 : path = 0일때
            int path = 0;

            gameContents.setUserId(userId);

            // LearningContent Count 조회
            int totalLearningContentCnt = groupService.totalLearningContentCnt(gameContents);
            log.info("totalLearningContentCnt : " + totalLearningContentCnt);

            // paging 처리
            Paging page = new Paging(totalLearningContentCnt, currentPage);
            gameContents.setStart(page.getStart());
            gameContents.setEnd(page.getEnd());

            // LearningContentList 조회
            List<GameContents> learningContentList = groupService.learningContentList(gameContents);
            log.info("learningContentList : " + learningContentList);

            model.addAttribute("learningContentCnt", totalLearningContentCnt);
            model.addAttribute("learningContentList", learningContentList);
            model.addAttribute("page", page);
            model.addAttribute("path", path);
        } catch (Exception e) {
            log.error("GroupController listlearningContent e.getMessage() : " + e.getMessage());
        } finally {
            log.info("GroupController listlearningContent end");
        }
        return "educate/learningGroup/listLearningContent";
    }

    // 로그인한 회원(교육자)이 소유한 게임콘텐츠 리스트 조회
    @RequestMapping(value = "listLearningContent1")
    public String SearchListlearningContent(GameContents gameContents, String currentPage, Model model, HttpServletRequest request) {
        Users users = usersService.getLoggedInUserInfo();
        int userId = users.getId();

        try {
            // 선택 페이지 : path = 1일때
            int path = 1;
            String keyword = request.getParameter("keyword");
            log.info("keyword : " + keyword);

            gameContents.setUserId(userId);
            gameContents.setKeyword(keyword);

            // LearningContent Count 조회
            int totalLearningContentCnt = groupService.totalLearningContentCnt(gameContents);
            log.info("totalLearningContentCnt : " + totalLearningContentCnt);

            // paging 처리
            Paging page = new Paging(totalLearningContentCnt, currentPage);
            gameContents.setStart(page.getStart());
            gameContents.setEnd(page.getEnd());
            log.info("page : " + page);

            // LearningContentList 조회
            List<GameContents> learningContentList = groupService.learningContentList(gameContents);
            log.info("learningContentList : " + learningContentList);

            model.addAttribute("learningContentCnt", totalLearningContentCnt);
            model.addAttribute("learningContentList", learningContentList);
            model.addAttribute("page", page);
            model.addAttribute("path", path);
            model.addAttribute("keyword", keyword);

        } catch (Exception e) {
            log.error("GroupController listlearningContent e.getMessage() : " + e.getMessage());
        } finally {
            log.info("GroupController listlearningContent end");
        }
        return "educate/learningGroup/listLearningContent";
    }

    // 게임콘텐츠에서 그룹을 생성하는 폼
    @RequestMapping(value = "insertFormLearningContent")
    public String insertFormLearningContent(GameContents gameContents, Model model, @RequestParam("subscribeEndDate") String subscribeEndDate,
                                                                                    @RequestParam("paymentId") String paymentId){

        Users users = usersService.getLoggedInUserInfo();
        int userId = users.getId();
        gameContents.setUserId(userId);

        log.info("gameContents : " + gameContents);

        try {
            GameContents insertFormLearningContent = groupService.insertFormLearningContent(gameContents);
            log.info("insertFormLearningContent : " + insertFormLearningContent);

            model.addAttribute("insertFormLearningContent", insertFormLearningContent);
            model.addAttribute("subscribeEndDate",subscribeEndDate);
            model.addAttribute("paymentId", paymentId);
        } catch (Exception e) {
            log.error("GroupController insertFormLearningContent e.getMessage() : " + e.getMessage());
        } finally {
            log.info("GroupController insertFormLearningContent end");
        }

        return "educate/learningGroup/insertFormLearningContent";
    }

    // 게임콘텐츠에서 그룹을 생성
    @RequestMapping(value = "insertLearningGroup", method = RequestMethod.POST)
    public String insertLearningGroup(@ModelAttribute LearningGroup learningGroup, Model model) {

        log.info("learningGroup : " + learningGroup);

        try {
            int insertLearningGroup = groupService.insertLearningGroup(learningGroup);
        } catch (Exception e) {
            log.error("GroupController insertLearningGroup e.getMessage() : " + e.getMessage());
        } finally {
            log.info("GroupController insertLearningGroup end");
        }

        return "redirect:/group/listLearningGroup";
    }

    // 로그인한 유저(교육자)의 학습그룹 리스트 조회
    @RequestMapping(value = "listLearningGroup")
    public String listLearningGroup(LearningGroup learningGroup, String currentPage, Model model) {
        Users users = usersService.getLoggedInUserInfo();
        int userId = users.getId();

        try {
            // 초기 페이지 : path = 0일때
            int path = 0;

            learningGroup.setUserId(userId);

            // LearningGroup Count 조회
            int totalLearningGroupCnt = groupService.totalLearningGroupCnt(learningGroup);
            log.info("totalLearningGroupCnt : " + totalLearningGroupCnt);

            // paging 처리
            Paging page = new Paging(totalLearningGroupCnt, currentPage);
            learningGroup.setStart(page.getCurrentPage());
            learningGroup.setEnd(page.getEnd());
            log.info("page : " + page);

            List<LearningGroup> learningGroupList = groupService.learningGroupList(learningGroup);
            log.info("learningGroupList : " + learningGroupList);

            model.addAttribute("totalLearningGroupCnt", totalLearningGroupCnt);
            model.addAttribute("learningGroupList", learningGroupList);
            model.addAttribute("page", page);
            model.addAttribute("path", path);

        } catch (Exception e) {
            log.error("GroupController listGroupContent e.getMessage() : " + e.getMessage());
        } finally {
            log.info("GroupController listGroupContent end");
        }
        return "educate/learningGroup/listLearningGroup";
    }

    // 로그인한 유저(교육자)의 학습그룹 리스트 조회
    @RequestMapping(value = "listLearningGroup1")
    public String SearchlistLearningGroup(LearningGroup learningGroup, String currentPage, Model model, HttpServletRequest request) {
        Users users = usersService.getLoggedInUserInfo();
        int userId = users.getId();

        try {
            // 선택 페이지 : path = 1일때
            int path = 1;
            String keyword = request.getParameter("keyword");
            log.info("keyword : " + keyword);

            learningGroup.setUserId(userId);
            learningGroup.setKeyword(keyword);

            // LearningGroup Count 조회
            int totalLearningGroupCnt = groupService.totalLearningGroupCnt(learningGroup);
            log.info("totalLearningGroupCnt : " + totalLearningGroupCnt);

            // paging 처리
            Paging page = new Paging(totalLearningGroupCnt, currentPage);
            learningGroup.setStart(page.getCurrentPage());
            learningGroup.setEnd(page.getEnd());
            log.info("page : " + page);

            List<LearningGroup> learningGroupList = groupService.learningGroupList(learningGroup);
            log.info("learningGroupList : " + learningGroupList);

            model.addAttribute("totalLearningGroupCnt", totalLearningGroupCnt);
            model.addAttribute("learningGroupList", learningGroupList);
            model.addAttribute("page", page);
            model.addAttribute("path", path);
            model.addAttribute("keyword", keyword);

        } catch (Exception e) {
            log.error("GroupController listGroupContent e.getMessage() : " + e.getMessage());
        } finally {
            log.info("GroupController listGroupContent end");
        }
        return "educate/learningGroup/listLearningGroup";
    }

    // 학습그룹의 상세조회
    @RequestMapping(value = "detailLearningGroup")
    public String detailLearningGroup(LearningGroup learningGroup, Model model){
        log.info("learningGroup : " + learningGroup);
        try {
            List<LearningGroup> detailLearningGroup = groupService.detailLearningGroup(learningGroup);
            log.info("detailLearningGroup : " + detailLearningGroup);

            model.addAttribute("detailLearningGroup", detailLearningGroup);
        } catch (Exception e) {
            log.error("GroupController detailLearningGroup e.getMessage() : " + e.getMessage());
        } finally {
            log.info("GroupController detailLearningGroup end");
        }
        return "educate/learningGroup/detailLearningGroup";
    }

    // 학습그룹 내용 수정
    @RequestMapping(value = "updateFormLearningGroup")
    public String updateFormLearningGroup(int id, Model model) {
        log.info("id : " + id);
        try {
            LearningGroup updateFormLearningGroup = groupService.updateFormLearningGroup(id);
            log.info("updateFormLearningGroup : " + updateFormLearningGroup);

            // 날짜 형식 변환
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");

            Date startDate = originalFormat.parse(updateFormLearningGroup.getStartDate());
            Date endDate = originalFormat.parse(updateFormLearningGroup.getEndDate());

            updateFormLearningGroup.setStartDate(newFormat.format(startDate));
            updateFormLearningGroup.setEndDate(newFormat.format(endDate));

            model.addAttribute("updateFormLearningGroup", updateFormLearningGroup);
        } catch (Exception e) {
            log.error("GroupController updateFormLearningGroup e.getMessage() : " + e.getMessage());
        } finally {
            log.info("GroupController updateFormLearningGroup end");
        }

        return "educate/learningGroup/updateFormLearningGroup";
    }

    // 학습그룹 내용 수정
    @RequestMapping(value = "updateLearningGroup")
    public String updateLearningGroup(@ModelAttribute LearningGroup learningGroup) {

        log.info("learningGroup : " + learningGroup);

        try {
            int updateLearningGroup = groupService.updateLearningGroup(learningGroup);
            log.info("updateLearningGroup : " + updateLearningGroup);
        } catch (Exception e) {
            log.error("GroupController updateLearningGroup e.getMessage() : " + e.getMessage());
        } finally {
            log.info("GroupController updateLearningGroup end");
        }

        return "redirect:/group/listLearningGroup";
    }

    // 학습그룹 삭제
    @RequestMapping(value = "deleteLearningGroup")
    public String deleteLearningGroup(int id) {
        log.info("id : " + id);
        try {
            int deleteLearningGroupMember = groupService.deleteLearningGroupMember(id);
            log.info("deleteLearningGroupMember : " + deleteLearningGroupMember);

            int deleteLearningGroup = groupService.deleteLearningGroup(id);
            log.info("deleteLearningGroup : " + deleteLearningGroup);

        } catch (Exception e) {
            log.error("GroupController deleteLearningGroup e.getMessage() : " + e.getMessage());
        } finally {
            log.info("GroupController deleteLearningGroup end");
        }

        return "redirect:/group/listLearningGroup";
    }

    @RequestMapping(value = "listApprovalMember")
    public String listApprovalMember(LearningGroupMember learningGroupMember, Model model, String currentPage) {
        Users users = usersService.getLoggedInUserInfo();
        int userId = users.getId();
        log.info("userId : " + userId);

        try {
            learningGroupMember.setUserId(userId);

            int totalApprovalGroupMemberCnt = groupService.totalApprovalGroupMemberCnt(learningGroupMember);

            List<LearningGroupMember> learningGroup = groupService.learningGroup(learningGroupMember);

            List<LearningGroupMember> learningGroupMembers = groupService.learningGroupMembers(learningGroupMember);
            log.info("learningGroupMembers : " + learningGroupMembers);

            model.addAttribute("totalApprovalMemberCnt", totalApprovalGroupMemberCnt);
            model.addAttribute("learningGroup", learningGroup);
            model.addAttribute("learningGroupMembers", learningGroupMembers);
        } catch (Exception e) {
            log.error("GroupController listApprovalMember e.getMessage() : " + e.getMessage());
        } finally {
            log.info("GroupController listApprovalMember end");
        }
        return "educate/learningGroup/approvalGroupMember";
    }

    @ResponseBody
    @RequestMapping(value = "approvalGroupMember")
    public List<LearningGroupMember> approvalGroupMember(@ModelAttribute LearningGroupMember learningGroupMember,Model model, String currentPage) {
        Users users = usersService.getLoggedInUserInfo();
        int userId = users.getId();
        log.info("userId : " + userId);

        List<LearningGroupMember> learningGroupMembers = null;

        try {
            learningGroupMember.setUserId(userId);

            learningGroupMembers = groupService.learningGroupMembers(learningGroupMember);
            log.info("learningGroupMembers : " + learningGroupMembers);

            model.addAttribute("learningGroupMembers", learningGroupMembers);
        } catch (Exception e) {
            log.error("GroupController approvalGroupMember e.getMessage() : " + e.getMessage());
        } finally {
            log.info("GroupController approvalGroupMember end");
        }

        return learningGroupMembers;
    }

    @RequestMapping(value = "grantMember", method = RequestMethod.POST)
    public String grantMember(@RequestBody LearningGroupMember learningGroupMember) {
        log.info("learningGroupMember : " + learningGroupMember);

        try {
            int grantMember = groupService.grantMember(learningGroupMember);
            log.info("grantMember : " + grantMember);

        } catch (Exception e) {
            log.error("GroupController grantMember e.getMessage() : " + e.getMessage());
        } finally {
            log.info("GroupController grantMember end");
        }
        return "redirect:/group/approvalGroupMember";
    }

    @ResponseBody
    @RequestMapping(value="getGroupMemberByGroupId",method = RequestMethod.GET)
    public ResponseEntity<List<Users>> getGroupMemberByGroupId(@RequestParam int groupId) {

        List<Users> homeworkTitleList = groupService.getGroupMemberByGroupId(groupId);

        return ResponseEntity.ok(homeworkTitleList);
    }

    @ResponseBody
    @RequestMapping(value="getGroupMembersByEducatorId",method = RequestMethod.GET)
    public ResponseEntity<List<Users>> getGroupMembersByEducatorId(@RequestParam int educatorId) {

        List<Users> homeworkTitleList = groupService.getGroupMembersByEducatorId(educatorId);

        return ResponseEntity.ok(homeworkTitleList);
    }

    @ResponseBody
    @RequestMapping(value="getUsersListByGroupInfo",method = RequestMethod.GET)
    public ResponseEntity<List<Users>> getUsersListByGroupInfo(@ModelAttribute LearningGroup learningGroup) {
        log.info("{}",learningGroup);
        List<Users> userList = groupService.getUsersListByGroupInfo(learningGroup);

        return ResponseEntity.ok(userList);
    }
}
