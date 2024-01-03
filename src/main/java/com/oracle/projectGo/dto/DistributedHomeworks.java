package com.oracle.projectGo.dto;

import com.oracle.projectGo.type.HomeworksEvaluateType;
import lombok.Data;

import java.util.Date;

@Data
public class DistributedHomeworks {
    private int    homeworkId;      // 숙제ID
    private int    userId;          // 회원번호
    private Date   submissionDate;  // 숙제 제출일
    private String content;         // 학습 제출내용
    private int    progress;        // 학습 진도
    private String questions;       // 추가 질문내용
    private HomeworksEvaluateType evaluation;      // 평가  미흡:1, 보통:2, 우수:3
    private Date   createdAt;       // 생성일
    private Date   updatedAt;       // 수정일
    private String homeLevel;           // 난이도

    // 조회용
    private String userName;
    private int educatorId;
    private String educatorName;
    private String homeworkTitle;
    private String homeworkContent;
    private Date homeworkDeadline;
    private int homeworkProgress;
    private int contentId;

    /* 페이지 처리 */
    private String pageNum;
    private int    start;
    private int    end;
}
