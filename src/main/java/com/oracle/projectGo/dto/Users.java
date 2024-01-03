package com.oracle.projectGo.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Users {
    private int    id;          // 회원번호
    private String nickname;    // 아이디
    private String userType;    // 회원구분 관리자:1, 교육자:2, 학습자:3, 일반인:4
    private String name;        // 이름
    private String password;    // 비밀번호
    private String birthdate;   // 생년월일
    private String gender;      // 성별
    private String address;     // 주소
    private String phone;       // 연락처
    private String email;       // 이메일
    private String consent1;    // 수신동의1
    private String consent2;    // 수신동의2
    private String isDeleted;   // 삭제여부
    private Date   createdAt;   // 가입일자
    private Date   updateAt;    // 수정일
    private String qualification; //자격구분

    private String pageNum;
    private int    start;
    private int    end;

    private String dateOptions;
    private String searchType;
    private String keyword;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
}
