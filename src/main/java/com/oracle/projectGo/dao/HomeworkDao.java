package com.oracle.projectGo.dao;

import com.oracle.projectGo.dto.Homeworks;
import com.oracle.projectGo.utils.error.BusinessException;
import com.oracle.projectGo.utils.error.DatabaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
@Slf4j
public class HomeworkDao {
    private final SqlSession session;
    public int insertHomework(Homeworks homework) {
        return session.insert("insertHomework", homework);
    }

    public int getTotalHomeworksCnt(Homeworks homework) {
        return session.selectOne("getTotalHomeworksCnt", homework);
    }

    public List<Homeworks> getHomeworksList(Homeworks homework){
        return session.selectList("getHomeworksList",homework);
    }

    public List<String> getDistinctHomeworkTitles(Homeworks homeworks) {
        return session.selectList("getDistinctHomeworkTitles",homeworks);
    }

    public List<String> getDistinctHomeworkTitlesByKeyword(int educatorId, String keyword) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", educatorId);
        params.put("keyword", keyword);
        return session.selectList("getDistinctHomeworkTitlesByKeyword", params);
    }
    public Homeworks getHomework(int homeworkId) {
        return session.selectOne("getHomework",homeworkId);
    }

    public int updateHomeworks(Homeworks homework) {
        return session.update("updateHomeworks",homework);
    }
}
