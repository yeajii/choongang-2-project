package com.oracle.projectGo.service;

import com.oracle.projectGo.dao.BoardDao;
import com.oracle.projectGo.dto.Board;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    @Autowired
    private BoardDao boardDao;

    public int boardCount(Board board) {
        return boardDao.boardCount(board);
    }

    public int totalnoticeboard() {

        int totalnotioceboard = boardDao.totalnotioceboard();

        return totalnotioceboard;
     }

    public List<Board> listnoticeBoard(Board board) {
        List<Board> listnoticeBoard = boardDao.listnoticeBoard(board);
        if(listnoticeBoard==null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "공지 리스트가 존재하지 않습니다");
        }

        return listnoticeBoard;
    }



    public void noticeInsert(Board board) {

        /*if (board.getFile() != null) {
            // 파일을 원하는 위치에 저장
            storageService.store(board.getFile());
        }*/

        int noticeBoardResult = 0;
        noticeBoardResult = boardDao.InsertnoticeBoard(board);



        if(noticeBoardResult <= 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "공지 정보 등록에 실패하였습니다.");
        }

    }

    public int noticeUpdate(Board board) {
        int result = boardDao.noticeUpdate(board);
        if(result <= 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "공지 정보 수정에 실패하였습니다.");
        }
        return result;
    }

    public Board detailnotice(int id) {
        Board board = boardDao.detailnotice(id);
        log.info("noticeImpl detailnotice Start...");
        if(board==null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "공지 정보가 존재하지 않습니다");
        }

        return board;


    }

    public int noticeDelete(int id) {
        int noticeDelete = 0;

        noticeDelete = boardDao.noticeDelete(id);

        return noticeDelete;
    }

    public int totalQNAboard() {
        int totalQNAboard = boardDao.totalQNAboard();

        return totalQNAboard;
    }

    public List<Board> listQNABoard(Board board) {
        List<Board> listQNABoard = boardDao.listQNABoard(board);
        if(listQNABoard==null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "공지 리스트가 존재하지 않습니다");
        }

        return listQNABoard;
    }

    public Board detailQNA(int id) {
        Board board = boardDao.detailQNA(id);
        log.info("QNAImpl detailQNA Start...");
        if(board==null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "QNA 정보가 존재하지 않습니다");
        }

        return board;
    }

    public void QNAInsert(Board board) {
        int QNAInsert = 0;
        QNAInsert = boardDao.InsertQNABoard(board);

        if(QNAInsert <= 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "공지 정보 등록에 실패하였습니다.");
        }
    }

    public int QNAUpdate(Board board) {
        int result = boardDao.QNAUpdate(board);
        if(result <= 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "공지 정보 수정에 실패하였습니다.");
        }
        return result;
    }

    public int QNADelete(int id) {
        int QNADelete = 0;

        QNADelete = boardDao.QNADelete(id);

        return QNADelete;
    }

    public int totalFAQboard() {
        int totalFAQboard = boardDao.totalFAQboard();

        return totalFAQboard;
    }

    public List<Board> listFAQBoard(Board board) {
        List<Board> listFAQBoard = boardDao.listFAQBoard(board);
        if(listFAQBoard==null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "FAQ 리스트가 존재하지 않습니다");
        }

        return listFAQBoard;
    }

    public Board detailFAQ(int id) {
        Board board = boardDao.detailFAQ(id);
        log.info("FAQImpl detailFAQ Start...");
        if(board==null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "FAQ 정보가 존재하지 않습니다");
        }

        return board;
    }

    public void FAQInsert(Board board) {
        int FAQInsert = 0;
        FAQInsert = boardDao.InsertFAQBoard(board);

        if(FAQInsert <= 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "공지 정보 등록에 실패하였습니다.");
        }
    }

    public int FAQUpdate(Board board) {
        int result = boardDao.FAQUpdate(board);
        if(result <= 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "공지 정보 수정에 실패하였습니다.");
        }
        return result;
    }

    public int FAQDelete(int id) {
        int FAQDelete = 0;

        FAQDelete = boardDao.FAQDelete(id);

        return FAQDelete;
    }

    public void increaseReadCount(int id) {
        boardDao.increaseReadCount(id);
    }

    public int totalSearchnotice(Board board) {
        int totalSearchnotice = boardDao.totalSearchnotice(board);
        return totalSearchnotice;
    }

    public List<Board> listSearchNotice(Board board) {
        List<Board> listSearchNotice = boardDao.listSearchNotice(board);
        return listSearchNotice;

    }

    public void commentInsert(Board board) {

        boardDao.commentInsert(board);

    }


    public List<Board> commentDetail(int id) {
        List<Board> comments = boardDao.commentDetail(id);
        return comments;
    }

    public int totalSearchQNA(Board board) {
        int totalSearchQNA = boardDao.totalSearchQNA(board);
        return totalSearchQNA;
    }

    public List<Board> listSearchQNA(Board board) {
        List<Board> listSearchQNA = boardDao.listSearchQNA(board);
        return listSearchQNA;
    }

    public int commentDelete(int id) {
        int commentDelete = 0;

        commentDelete = boardDao.commentDelete(id);

        return commentDelete;
    }

    public List<Board> listSearchFAQ(Board board) {
        List<Board> listSearchFAQ = boardDao.listSearchFAQ(board);
        return listSearchFAQ;
    }

    public int totalSearchFAQ(Board board) {
        int totalSearchFAQ = boardDao.totalSearchFAQ(board);
        return totalSearchFAQ;
    }
}