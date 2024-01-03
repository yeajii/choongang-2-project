package com.oracle.projectGo.controller;

import com.oracle.projectGo.dto.DistributedHomeworks;
import com.oracle.projectGo.dto.DistributionRequestDto;
import com.oracle.projectGo.dto.Homeworks;
import com.oracle.projectGo.dto.ResponseMessage;
import com.oracle.projectGo.service.HomeworkService;
import com.oracle.projectGo.service.UsersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.tags.shaded.org.apache.xpath.operations.Mod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/homework")
public class HomeworkRestController {
    private final HomeworkService homeworkService;

    @PostMapping("/insertHomework")
    public ResponseEntity<ResponseMessage> insertHomework(@Valid @RequestBody Homeworks homework)  {
        int result = homeworkService.insertHomework(homework);
        return ResponseEntity.ok(new ResponseMessage("숙제가 정상적으로 등록 되었습니다."));
    }

    @GetMapping("/getHomework/{homeworkId}")
    public ResponseEntity<Homeworks> getHomework(@PathVariable int homeworkId) {
        Homeworks homework = homeworkService.getHomework(homeworkId);
        if (homework == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(homework);
    }

    @GetMapping("/getHomeworkTitleList")
    public ResponseEntity<List<String>> getHomeworkTitleList(@ModelAttribute Homeworks homeworks) {
        List<String> homeworkTitleList = homeworkService.getDistinctHomeworkTitles(homeworks);
        return ResponseEntity.ok(homeworkTitleList);
    }

    @GetMapping("/getHomeworkTitleListByKeyword")
    public ResponseEntity<List<String>> getHomeworkTitleListByKeyword(@RequestParam("userId") int userId, @RequestParam("keyword") String keyword) {
        List<String> homeworkTitleList = homeworkService.getDistinctHomeworkTitlesByKeyword(userId, keyword);
        return ResponseEntity.ok(homeworkTitleList);
    }

    @PostMapping("/distributeHomework")
    public ResponseEntity<ResponseMessage> distributeHomework(@RequestBody DistributionRequestDto request)  {
        log.info(request.toString());
        try{
            homeworkService.distributeHomework(request);
            return ResponseEntity.ok(new ResponseMessage("숙제가 성공적으로 배포되었습니다."));
        } catch (Exception e){
            log.error("Error distributing homework", e);
            return ResponseEntity.internalServerError().body(new ResponseMessage("숙제 배포에 실패했습니다."));
        }
    }

    @PostMapping("/getDistributedHomeworks")
    public ResponseEntity<List<DistributedHomeworks>> getDistributedHomeworks(@RequestBody DistributedHomeworks pDistributedHomework) {
        log.info("{}",pDistributedHomework);
        List<DistributedHomeworks> distributedHomeworks = homeworkService.getDistributedHomeworksList(pDistributedHomework);
        log.info("{}",distributedHomeworks);
        return ResponseEntity.ok(distributedHomeworks);
    }


    @PostMapping("/updateEvaluations")
    public ResponseEntity<ResponseMessage> updateEvaluations(@RequestBody List<DistributedHomeworks> evaluations) {
        try {
            homeworkService.updateEvaluation(evaluations);
            return ResponseEntity.ok(new ResponseMessage("평가가 성공적으로 저장되었습니다."));
        } catch (Exception e) {
            log.error("Error updating evaluations", e);
            return ResponseEntity.internalServerError().body(new ResponseMessage("평가 저장에 실패했습니다."));
        }
    }

    @PostMapping("submissionHomework")
    public ResponseEntity<ResponseMessage> submissionHomework(@RequestBody List<DistributedHomeworks> submissions) {
        try {
            for (DistributedHomeworks distributedHomework : submissions) {
                log.info("distributedHomework:{}",distributedHomework);
            }
            homeworkService.updateSubmissionList(submissions);
            return ResponseEntity.ok(new ResponseMessage("숙제가 성공적으로 제출되었습니다."));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(new ResponseMessage("숙제 제출에 실패했습니다."));
        }
    }

    @PostMapping("editSubmissionHomework")
    public ResponseEntity<ResponseMessage> editSubmissionHomework(@RequestBody DistributedHomeworks submission) {
        try {
            log.info("submission:{}",submission);
            homeworkService.updateSubmission(submission);
            return ResponseEntity.ok(new ResponseMessage("숙제가 성공적으로 수정되었습니다."));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(new ResponseMessage("숙제 수정에 실패했습니다."));
        }
    }

    @GetMapping(value="getUserHomeworkProgress")
    public ResponseEntity<String> getUserHomeworkProgress(@ModelAttribute DistributedHomeworks distributedHomeworks) {
        try {
            log.info("getUserHomeworkProgress:{}",distributedHomeworks);
            String result = homeworkService.getUserHomeworkProgress(distributedHomeworks);
            log.info("result:{}",result);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body("{\"message\": \"숙제 수정에 실패했습니다.\"}");
        }
    }


}
