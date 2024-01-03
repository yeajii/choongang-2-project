package com.oracle.projectGo.dao;

import com.oracle.projectGo.dto.DistributedHomeworks;
import com.oracle.projectGo.dto.Homeworks;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class DistributedHomeworksDao {
    private final SqlSession session;

    public int bulkInsert(List<DistributedHomeworks> distributedHomeworksList) {
        return session.insert("insertDistributedHomeworksByBulk",distributedHomeworksList);
    }

    public List<DistributedHomeworks> getDistributedHomeworksList(DistributedHomeworks pDistributedHomework) {
        try{
            return session.selectList("getDistributedHomeworksList",pDistributedHomework);
        } catch (Exception e){
//            log.error(e.getMessage());
            return null;
        }
    }

    public int updateEvaluation(List<DistributedHomeworks> evaluations) {
        return session.update("updateDistributedHomeworks",evaluations);
    }

    public int updateSubmissionList(List<DistributedHomeworks> submissions) {
        return session.update("updateSubmissionList", submissions);
    }

    public int updateSubmission(DistributedHomeworks submission) {
        return session.update("updateSubmission", submission);
    }

    public DistributedHomeworks getDistributedHomeworks(DistributedHomeworks distributedHomeworks){
        DistributedHomeworks result = null;
        try{
            result = session.selectOne("getDistributedHomeworks",distributedHomeworks);
            return result;
        } catch (Exception e){
//            log.error(e.getMessage());
            return result;
        }
    }

    public int insertDistributedHomeworks(DistributedHomeworks distributedHomeworks) {
        return session.insert("insertDistributedHomeworks",distributedHomeworks);
    }

    public String getUserHomeworkProgress(DistributedHomeworks distributedHomeworks) {
        try{
            return session.selectOne("getUserHomeworkProgress",distributedHomeworks);
        } catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }
}
