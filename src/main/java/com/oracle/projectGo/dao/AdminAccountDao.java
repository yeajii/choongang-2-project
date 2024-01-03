package com.oracle.projectGo.dao;

import com.oracle.projectGo.controller.admin.AdminAccountController;
import com.oracle.projectGo.dto.Payments;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class AdminAccountDao {
    private final SqlSession session;

    public List<Payments> listSales() {
        return session.selectList("listSales");
    }

    public int listSalesCount() {
        return session.selectOne("listSalesCount");
    }

    public List<Payments> saleSearchList(AdminAccountController.SearchForSales search) {
        return session.selectList("saleSearchList", search);
    }

    public List<Payments> chartSelector(int value) {
        List<Payments> list = null;
        try {
            if ( value > 1000 ) {
                list = session.selectList("chartSelector1", value);

            } else {
                list = session.selectList("chartSelector2", value);
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return list;
    }
}
