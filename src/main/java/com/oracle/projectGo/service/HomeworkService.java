package com.oracle.projectGo.service;

import com.oracle.projectGo.dao.DistributedHomeworksDao;
import com.oracle.projectGo.dao.HomeworkDao;
import com.oracle.projectGo.dto.DistributedHomeworks;
import com.oracle.projectGo.dto.DistributionRequestDto;
import com.oracle.projectGo.dto.Homeworks;
import com.oracle.projectGo.type.HomeworksEvaluateType;
import com.oracle.projectGo.utils.error.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HomeworkService {
    private final HomeworkDao homeworkDao;
    private final DistributedHomeworksDao distributedHomeworksDao;
    public int insertHomework(Homeworks homework){
        return homeworkDao.insertHomework(homework);
    }

    public  int getTotalHomeworksCnt(Homeworks homework){
        return homeworkDao.getTotalHomeworksCnt(homework);
    }

    public List<Homeworks> getHomeworksList(Homeworks homework){
        return homeworkDao.getHomeworksList(homework);
    }

    public List<String> getDistinctHomeworkTitles(Homeworks homeworks) {
        return homeworkDao.getDistinctHomeworkTitles(homeworks);
    }

    public int distributedHomeworksBulkInsert(List<DistributedHomeworks> distributedHomeworksList) {
        return distributedHomeworksDao.bulkInsert(distributedHomeworksList);
    }

    public List<DistributedHomeworks> getDistributedHomeworksList(DistributedHomeworks pDistributedHomework) {
        return distributedHomeworksDao.getDistributedHomeworksList(pDistributedHomework);
    }
    public int updateHomeworks(Homeworks homework){
        return homeworkDao.updateHomeworks(homework);
    }
    public int updateEvaluation(List<DistributedHomeworks> evaluations) {
        return distributedHomeworksDao.updateEvaluation(evaluations);
    }

    public int updateSubmissionList(List<DistributedHomeworks> submissions) {
        return distributedHomeworksDao.updateSubmissionList(submissions);
    }

    public int updateSubmission(DistributedHomeworks submission) {
        return distributedHomeworksDao.updateSubmission(submission);
    }

    public Homeworks getHomework(int homeworkId) {
        return homeworkDao.getHomework(homeworkId);
    }

    public List<String> getDistinctHomeworkTitlesByKeyword(int userId, String keyword) {
        return homeworkDao.getDistinctHomeworkTitlesByKeyword(userId,keyword);
    }
    public DistributedHomeworks getDistributedHomeworks(DistributedHomeworks distributedHomeworks){
        try{
            return distributedHomeworksDao.getDistributedHomeworks(distributedHomeworks);
        } catch (Exception e){
            return null;
        }
    }
    public int insertDistributedHomeworks(DistributedHomeworks distributedHomeworks){
        return distributedHomeworksDao.insertDistributedHomeworks(distributedHomeworks);
    }

    public String getUserHomeworkProgress(DistributedHomeworks distributedHomeworks) {
        return distributedHomeworksDao.getUserHomeworkProgress(distributedHomeworks);
    }

    @Transactional
    public void distributeHomework(DistributionRequestDto request) {
        for (int homeworkId : request.getHomeworkIds()) {
            for (int studentId : request.getStudentIds()) {
                DistributedHomeworks distributedHomeworks = new DistributedHomeworks();
                distributedHomeworks.setHomeworkId(homeworkId);
                distributedHomeworks.setUserId(studentId);
                DistributedHomeworks getResult = this.getDistributedHomeworks(distributedHomeworks);
                if (getResult == null) {
                    this.insertDistributedHomeworks(distributedHomeworks);
                    log.info("INSERT(user:{})", distributedHomeworks.getUserId());
                } else {
                    log.info("EXIST(user:{})", distributedHomeworks.getUserId());
                }
            }
            Homeworks homeworks = new Homeworks();
            homeworks.setId(homeworkId);
            homeworks.setDistributionDate(new Date());
            this.updateHomeworks(homeworks);
        }
    }
}
