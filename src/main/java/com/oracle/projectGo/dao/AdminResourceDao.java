package com.oracle.projectGo.dao;

import com.oracle.projectGo.dto.EducationalResources;
import com.oracle.projectGo.dto.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class AdminResourceDao {
    private final SqlSession session;


    public int educationUpload(EducationalResources educationalResources) {
        return session.insert("educationUpload", educationalResources);
    }

    public List<EducationalResources> listEdu(Users users) {
        List<EducationalResources> listEdu = null;
        try {
            listEdu = session.selectList("listEdu", users);
            log.info(listEdu.toString());
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return listEdu;
    }

    public int deleteEdu(int id) {
        return session.delete("deleteEdu", id);
    }

    public EducationalResources detailEdu(int id) {
        return session.selectOne("detailEdu", id);
    }

    public int readCnt(int id) {
        return session.update("readCnt", id);
    }

    public int updateEdu(EducationalResources edu) {
        return session.update("updateEdu", edu);
    }

    public List<EducationalResources> listSearchEdu(Users users) {

        return session.selectList("listSearchEdu", users);
    }
}
