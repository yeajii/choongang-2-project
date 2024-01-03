package com.oracle.projectGo.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class Board {

    private int    id;              // 게시판ID
    private int    userId;          // 회원번호
    private String boardType;       // 게시판유형 공지:1, FAQ:2, QnA:3
    private String title;           // 제목
    private String content;         // 내용
    private String status;          // 상태 공개:1, 비공개:0
    private int    readCount;       // 조회수


    private int    commentGroupId;  // 댓글그룹
    private int    commentIndent;   // 댓글밀기
    private int    commentStep;     // 댓글최신

    @DateTimeFormat(pattern = "EEE MMM dd HH:mm:ss zzz yyyy")
    public  Date   createdAt;       // 등록일
    private Date   updateAt;        // 수정일
    private int    commentCount;    // 댓글 수

    /* 페이지 처리 */
    private String pageNum;
    private int    start;
    private int    end;

    /* 검색처리 */
    private String keyword;
    private String searchType;
    private String name;

    /*스케줄 처리*/
    @DateTimeFormat(pattern = "EEE MMM dd HH:mm:ss zzz yyyy")
    private LocalDateTime publishDate;


    /*파일 업로드*/
    private String fileName;        // 파일이름
    private int    fileSize;        // 파일용량
    private String filePath;        // 저장경로
    private String fileAddress;         // 파일다운주소

    /*고정처리*/
    private boolean isPinned;

    public boolean getIsPinned() {
        return this.isPinned;
    }

    public void setIsPinned(boolean isPinned) {
        this.isPinned = isPinned;
    }

    public void setSearchType(String searchType) { this.searchType = searchType; }

    public void setFileUrl(String fileAddress) { this.fileAddress = fileAddress; }

    }

