package com.oracle.projectGo.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class Payments {
    private int    id;           // 결제 번호
    private int    userId;       // 회원 번호
    private int    contentId;    // 게임 번호
    private String paymentType;  // 결제방법    무통장입금: 1, 계좌이체: 2, 카카오페이:3
    private String status;       // 결제상태    결제완료:1, 미결제:2
    private Date   purchaseDate; // 구매일자

    // 조회용 by 강한빛
    private String name;  // 회원 이름
    private String title; // 게임컨텐츠 제목

    // 페이징 작업
    private int rn;
    private String search;   	private String keyword;
    private String pageNum;		private int total;
    private int start; 		 	private int end;

    // gameContents 조회용
    private String imageName;           // 이미지명
    private String imagePath;           // 이미지경로
    private int    discountPrice;       // 판매가
    private int    subscribeDate;       // 구독기간
    private Date   createdAt;           // 생성일


}
