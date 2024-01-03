package com.oracle.projectGo.dao;

import com.oracle.projectGo.dto.GameContents;
import com.oracle.projectGo.dto.LearningGroup;
import com.oracle.projectGo.dto.LearningGroupMember;
import com.oracle.projectGo.dto.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
@Slf4j
public class LearningGroupDao {
    private final SqlSession session;

    public int totalLearningContentCnt(GameContents gameContents) {
        int totalLearningContentCnt = session.selectOne("NoTotalLearningContentCnt", gameContents);
        return totalLearningContentCnt;
    }

    public List<GameContents> learningContentList(GameContents gameContents) {
        List<GameContents> learningContentList = null;

        try {
            learningContentList = session.selectList("NoLearningContentList", gameContents);
        } catch (Exception e) {
            log.info("LearningGroupDao learningContentList e.getMessage() : " + e.getMessage());
        }
        return learningContentList;
    }

    public GameContents insertFormLearningContent(GameContents gameContents) {
        GameContents insertFormLearningContent = null;
        try {
            insertFormLearningContent = session.selectOne("NoInsertFormLearningContent", gameContents);
        } catch (Exception e) {
            log.info("LearningGroupDao insertFormLearningContent e.getMessage() : " + e.getMessage());
        }
        return insertFormLearningContent;
    }

    public int insertLearningGroup(LearningGroup learningGroup) {
        int insertLearningGroup = 0;

        try {
            insertLearningGroup = session.insert("NoInsertLearningGroup", learningGroup);
            log.info("insertLearningGroup : " + insertLearningGroup);
        } catch (Exception e) {
            log.info("LearningGroupDao insertLearningGroup e.getMessage() : " + e.getMessage());
        }
        return insertLearningGroup;
    }

    public int totalLearningGroupCnt(LearningGroup learningGroup) {
        return session.selectOne("NoTotalLearningGroupCnt", learningGroup);
    }

    public List<LearningGroup> learningGroupList(LearningGroup learningGroup) {
        List<LearningGroup> learningGroupList = null;

        try {
            learningGroupList = session.selectList("NoLearningGroupList", learningGroup);
        } catch (Exception e) {
            log.info("LearningGroupDao learningGroupList e.getMessage() : " + e.getMessage());
        }
        return learningGroupList;
    }

    public List<LearningGroup> detailLearningGroup(LearningGroup learningGroup) {
        List<LearningGroup> detailLearningGroup = null;
        try {
            detailLearningGroup = session.selectList("NoDetailLearningGroup", learningGroup);
        } catch (Exception e) {
            log.info("LearningGroupDao detailLearningGroup e.getMessage() : " + e.getMessage());
        }
        return detailLearningGroup;
    }

    public LearningGroup updateFormLearningGroup(int id) {
        LearningGroup updateFormLearningGroup = null;
        try {
            updateFormLearningGroup = session.selectOne("NoUpdateFormLearningGroup", id);
        } catch (Exception e) {
            log.info("LearningGroupDao updateFormLearningGroup e.getMessage() : " + e.getMessage());
        }
        return updateFormLearningGroup;
    }

    public int updateLearningGroup(LearningGroup learningGroup) {
        int updateLearningGroup = 0;
        try {
            updateLearningGroup = session.update("NoUpdateLearningGroup", learningGroup);
        } catch (Exception e) {
            log.info("LearningGroupDao updateLearningGroup e.getMessage() : " + e.getMessage());
        }
        return updateLearningGroup;
    }

    public int deleteLearningGroupMember(int id) {
        int deleteLearningGroupMember = 0;
        try {
            deleteLearningGroupMember = session.delete("NoDeleteLearningGroupMember", id);
        } catch (Exception e) {
            log.info("LearningGroupDao deleteLearningGroupMember e.getMessage() : " + e.getMessage());
        }
        return deleteLearningGroupMember;
    }

    public int deleteLearningGroup(int id) {
        int deleteLearningGroup = 0;
        try {
            deleteLearningGroup = session.delete("NoDeleteLearningGroup", id);
        } catch (Exception e) {
            log.info("LearningGroupDao deleteLearningGroup e.getMessage() : " + e.getMessage());
        }
        return deleteLearningGroup;
    }

    public int totalApprovalGroupMemberCnt(LearningGroupMember learningGroupMember) {
        int totalApprovalGroupMemberCnt = session.selectOne("NoTotalApprovalGroupMemberCnt", learningGroupMember);
        return totalApprovalGroupMemberCnt;
    }

    public List<LearningGroupMember> learningGroup(LearningGroupMember learningGroupMember) {
        List<LearningGroupMember> learningGroup = null;
        try {
            learningGroup = session.selectList("NoLearningGroup", learningGroupMember);
        } catch (Exception e) {
            log.info("LearningGroupDao learningGroup e.getMessage() : " + e.getMessage());
        }
        return learningGroup;
    }

    public List<LearningGroupMember> learningGroupMembers(LearningGroupMember learningGroupMember) {
        List<LearningGroupMember> learningGroupMembers = null;
        try {
            learningGroupMembers = session.selectList("NoLearningGroupMembers", learningGroupMember);
        } catch (Exception e) {
            log.info("LearningGroupDao learningGroupMembers e.getMessage() : " + e.getMessage());
        }
        return learningGroupMembers;
    }

    public int grantMember(LearningGroupMember learningGroupMember) {
        int grantMember = 0;
        try {
            grantMember = session.update("NoGrantMember", learningGroupMember);
        } catch (Exception e) {
            log.info("LearningGroupDao grantMember e.getMessage() : " + e.getMessage());
        }
        return grantMember;
    }


    public List<Users> getGroupMemberByGroupId(int educatorId) {
        return session.selectList("getGroupMemberByGroupId",educatorId);
    }

    public List<Users> getGroupMembersByEducatorId(int educatorId) {
        return session.selectList("getGroupMembersByEducatorId",educatorId);
    }

    public List<Users> getUsersListByGroupInfo(LearningGroup learningGroup) {
        return session.selectList("getUsersListByGroupInfo", learningGroup);
    }

    public List<LearningGroup> signUpLearningGroup() {
        List<LearningGroup> signUpLearningGroup = null;
        try {
            signUpLearningGroup = session.selectList("signUpLearningGroup");
            log.info(signUpLearningGroup.toString());
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return signUpLearningGroup;
    }

    public List<LearningGroup> signUpLearningGroup(String value, String category) {
        List<LearningGroup> signUpLearningGroup = null;
        Map<String, Object> params = new HashMap<>();
        params.put("value", value);
        params.put("category", category);
        try {
            signUpLearningGroup = session.selectList("signUpLearningGroup2", params);
            log.info(signUpLearningGroup.toString());
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return signUpLearningGroup;
    }

}
