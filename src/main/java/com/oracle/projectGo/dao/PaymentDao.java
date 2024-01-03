package com.oracle.projectGo.dao;

import com.oracle.projectGo.dto.Payments;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PaymentDao {

    private final SqlSession session;
    private final PlatformTransactionManager transactionManager;

// -----------------------------------------------------------

    // 결제하기 클릭 후 payments 테이블에 insert
    public int subscribePayInsert(Payments payments) {
        int subscribePayInsert = 0;
        TransactionStatus txStatus =
                transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            subscribePayInsert = session.insert("subscribePayInsert", payments);
            log.info("subscribePayInsert =  "+ subscribePayInsert);

            // 결제 하면 Users Table에 Qualification도 동시 업데이트
            subscribePayInsert = session.update("userQualificationUpdate", payments);
            log.info("subscribePayInsert =  "+ subscribePayInsert);
            transactionManager.commit(txStatus);
            System.out.println("PaymentDao subscribePayInsert-> " + subscribePayInsert);
        }catch (Exception e){
            System.out.println("PaymentDao subscribePayInsert e-> " + e);
            transactionManager.rollback(txStatus);
            subscribePayInsert = -1;
        }
        log.info("subscribePayInsert =  "+ subscribePayInsert);
        return subscribePayInsert;
    }

// -----------------------------------------------------------

    // 내가 구독한 게임 컨텐츠 리스트 총 갯수
    public int subscribeUserPayTotalCount(int loginUserId) {
        int subscribeUserPayTotalCount = 0;
        try {
            subscribeUserPayTotalCount = session.selectOne("subscribeUserPayTotalCount", loginUserId);
            System.out.println("PaymentDao subscribeUserPayTotalCount-> " + subscribeUserPayTotalCount);
        } catch (Exception e) {
            System.out.println("PaymentDao subscribeUserPayTotalCount e-> " + e);
        }
        return subscribeUserPayTotalCount;
    }

    // 내가 구독한 게임 컨텐츠 리스트 조회
    public List<Payments> mySubscribePayList(Payments payments) {
        List<Payments> mySubscribePayList = new ArrayList<Payments>();
        try {
            mySubscribePayList = session.selectList("mySubscribePayList", payments);
            System.out.println("PaymentDao mySubscribePayList-> " + mySubscribePayList);
        }catch (Exception e){
            System.out.println("PaymentDao mySubscribePayList e-> " + e);
        }
        return mySubscribePayList;
    }

    // Payments 테이블에 gameContents의 id가 있는지 체크
    public int deleteCheck(Payments payments) {
        int deleteCheck = 0;
        try {
            deleteCheck = session.selectOne("deleteCheck", payments);
            System.out.println("PaymentDao deleteCheck 갯수-> " + deleteCheck);
        }catch (Exception e){
            System.out.println("PaymentDao deleteCheck e-> " + e);
        }
        return deleteCheck;
    }


}

