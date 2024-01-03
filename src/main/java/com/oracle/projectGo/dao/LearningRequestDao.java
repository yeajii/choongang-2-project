package com.oracle.projectGo.dao;

import com.oracle.projectGo.dto.GameContents;
import com.oracle.projectGo.dto.LearningGroup;
import com.oracle.projectGo.dto.LearningGroupMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class LearningRequestDao {
    private final SqlSession session;

    public int requestSignUp(LearningGroupMember member) {
        return session.insert("requestSignUp", member);
    }

    public int cancelSignUp(LearningGroupMember member) {
        return session.delete("cancelSignUp",member);
    }

    public List<LearningGroupMember> remainRequest(LearningGroupMember learningGroupMember) {
        LearningGroupMember member = null;
        try {
            member = session.selectOne("remainRequest", learningGroupMember);
            log.info(String.valueOf(member));
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return session.selectList("remainRequest", learningGroupMember);
    }

    public List<LearningGroupMember> remainRequest2(LearningGroupMember learningGroupMember) {
        return session.selectList("remainRequest2", learningGroupMember);
    }

    public List<LearningGroup> overLimit() {
        return session.selectList("overLimit");
    }

    public List<GameContents> bringImage() {
        return session.selectList("bringImage");
    }

    public List<String> slgSelected(String keyword) {
        System.out.println(keyword);
        return session.selectList("slgSelected", keyword);
    }
}
