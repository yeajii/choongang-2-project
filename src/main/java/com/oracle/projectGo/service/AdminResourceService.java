package com.oracle.projectGo.service;

import com.oracle.projectGo.dao.AdminResourceDao;
import com.oracle.projectGo.dto.EducationalResources;
import com.oracle.projectGo.dto.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminResourceService {
    private final AdminResourceDao adminResourceDao;

    public int educationUpload(EducationalResources educationalResources) {
        return adminResourceDao.educationUpload(educationalResources);
    }

    public List<EducationalResources> listEdu(Users users) {
        return adminResourceDao.listEdu(users);
    }

    public int deleteEdu(int id) {
        return adminResourceDao.deleteEdu(id);
    }

    public EducationalResources detailEdu(int id) {
        return adminResourceDao.detailEdu(id);
    }

    public int readCnt(int id) {
        return adminResourceDao.readCnt(id);
    }

    public int updateEdu(EducationalResources edu) {
        return adminResourceDao.updateEdu(edu);
    }

    public List<EducationalResources> listSearchEdu(Users users) {
        return adminResourceDao.listSearchEdu(users);
    }
}
