package com.oracle.projectGo.dto;

import lombok.Data;

import java.util.Date;

@Data
public class LearningGroupMember {
    private int    groupId;             // 그룹번호
    private int    userId;              // 회원번호
    private String status;              // 승인여부 승인:1 , 미승인:0
    private Date   approvalDate;        // 승인요청일자
    private String isDeleted;           // 삭제여부 삭제:1, 미삭제:0

    // users
    private String userName;            // 이름
    private String phone;               // 연락처

    // learningGroup
    private int     id;                 // 그룹번호
    private int     contentId;          // 게임번호
    private String  name;               // 그룹명
    private int     groupSize;          // 등록가능인원

    // gameContents
    private String title;               // 게임명

    // 조회용
    private int membersCount;           // 가입 학생수
}
