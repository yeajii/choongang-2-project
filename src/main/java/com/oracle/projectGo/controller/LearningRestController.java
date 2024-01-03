package com.oracle.projectGo.controller;

import com.oracle.projectGo.dto.GameContents;
import com.oracle.projectGo.dto.LearningGroup;
import com.oracle.projectGo.dto.LearningGroupMember;
import com.oracle.projectGo.dto.Users;
import com.oracle.projectGo.service.LearningGroupService;
import com.oracle.projectGo.service.LearningRequestService;
import com.oracle.projectGo.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/learning")
public class LearningRestController {
    private final LearningGroupService learningGroupService;
    private final LearningRequestService learningRequestService;
    private final UsersService usersService;

    @GetMapping("/api/signUpLearningGroup")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> signUpLearningGroup(@RequestParam(required = false) String value,
                                                                   @RequestParam(required = false) String category) throws ParseException {
        Map<String, Object> response = new HashMap<>();
        Users users = usersService.getLoggedInUserInfo();
        int userId = users.getId();



        LearningGroupMember learningGroupMember = new LearningGroupMember();
        learningGroupMember.setUserId(userId);
        List<LearningGroup> learningGroupList = learningGroupService.signUpLearningGroup();
        if (value != null) {
            learningGroupList = learningGroupService.signUpLearningGroup(value, category);
        }
        List<LearningGroupMember> member = learningRequestService.remainRequest(learningGroupMember);
        List<LearningGroupMember> member2 = learningRequestService.remainRequest2(learningGroupMember);
        List<LearningGroup> overLimit = learningRequestService.overLimit();
        List<GameContents> bringImage = learningRequestService.bringImage();

        for (int i = 0; i < learningGroupList.size(); i++) {
            if (member != null) {
                // applied -> 1이면 신청을 안한 그룹
                learningGroupList.get(i).setApplied(1);

                for (int j = 0; j < member.size(); j++) {

                    if(learningGroupList.get(i).getContentId() == member.get(j).getContentId()) {
                        // applied -> 3이면 신청불가 상태
                        learningGroupList.get(i).setApplied(3);
                    }
                    if(learningGroupList.get(i).getId() == member.get(j).getGroupId() && userId == member.get(j).getUserId()) {
                        // applied -> 2이면 신청중인 상태
                        // 신청중인 학습그룹인지 확인하는 로직
                        learningGroupList.get(i).setApplied(2);
                    }
                }
                for (int j = 0; j < member2.size(); j++) {
                    if(learningGroupList.get(i).getId() == member2.get(j).getGroupId() && userId == member2.get(j).getUserId()) {
                        // applied -> 4이면 신청완료 상태
                        // 신청완료한 학습그룹인지 확인하는 로직
                        learningGroupList.get(i).setApplied(4);
                    }
                }
                for (int j = 0; j < overLimit.size() ; j++) {
                    if (overLimit.get(j).getGroupId() == learningGroupList.get(i).getGroupId()) {
                        // applied -> 5이면 학습그룹 정원초과 상태
                        // 정원이 초과된 학습그룹인지 확인하는 로직
                        learningGroupList.get(i).setApplied(5);
                    }
                }

                for (int j = 0; j < bringImage.size(); j++) {
                    if (bringImage.get(j).getId() == learningGroupList.get(i).getContentId()) {
                        // 게임컨텐츠 이미지를 교육자료 썸네일에 등록하는 로직
                        learningGroupList.get(i).setImage(bringImage.get(j).getImageName());
                        
                    }
                }


            }
        }

        log.info(learningGroupList.toString());

        response.put("learningGroupList", learningGroupList);
        response.put("userType", users.getUserType());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/requestSignUp")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> requestSignUp(int groupId) {
        Users users = usersService.getLoggedInUserInfo();
        int userId = users.getId();

        Map<String, Object> response = new HashMap<>();
        LearningGroupMember member = new LearningGroupMember();
        member.setUserId(userId);
        member.setGroupId(groupId);
        int result = learningRequestService.requestSignUp(member);
        log.info("성공여부->"+result);

        response.put("result", result);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/api/cancelSignUp")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> cancelSignUp(int groupId) {
        Users users = usersService.getLoggedInUserInfo();
        int userId = users.getId();

        Map<String, Object> response = new HashMap<>();
        LearningGroupMember member = new LearningGroupMember();
        member.setUserId(userId);
        member.setGroupId(groupId);
        int result = learningRequestService.cancelSignUp(member);
        log.info("성공여부->"+result);

        response.put("result", result);

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/api/selected")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> slgSelected(String keyword) {
        Map<String, Object> response = new HashMap<>();
        List<String> slgSelected = learningRequestService.slgSelected(keyword);
        response.put("slg", slgSelected);
        return ResponseEntity.ok(response);
    }

    private void formatDate(List<LearningGroup> list) throws ParseException {
        SimpleDateFormat newDtFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strNewDtFormat1 = "";
        String strNewDtFormat2 = "";
        for (int i = 0; i < list.size(); i++) {
            strNewDtFormat1 = newDtFormat.format(list.get(i).getStartDate());
            list.get(i).setFormatStartDate(strNewDtFormat1);
            strNewDtFormat2 = newDtFormat.format(list.get(i).getEndDate());
            list.get(i).setFormatEndDate(strNewDtFormat2);
        }
    }
}
