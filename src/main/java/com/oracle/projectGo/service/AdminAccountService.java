package com.oracle.projectGo.service;

import com.oracle.projectGo.controller.admin.AdminAccountController;
import com.oracle.projectGo.dao.AdminAccountDao;
import com.oracle.projectGo.dto.Payments;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminAccountService {
    private final AdminAccountDao adminAccountDao;
    public List<Payments> listSales() {
        return adminAccountDao.listSales();
    }

    public int listSalesCount() {
        return adminAccountDao.listSalesCount();
    }

    public List<Payments> saleSearchList(AdminAccountController.SearchForSales search) {
        return adminAccountDao.saleSearchList(search);
    }

    public List<Payments> chartSelector(int value) {
        return adminAccountDao.chartSelector(value);
    }
}
