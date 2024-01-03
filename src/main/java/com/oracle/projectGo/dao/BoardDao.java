package com.oracle.projectGo.dao;

import com.oracle.projectGo.dto.Board;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class BoardDao {

    private final SqlSession session;
    private final PlatformTransactionManager transactionManager;

    public int boardCount(Board board) {
        // 쿼리를 작성하고 실행하여 게시글의 수를 반환해야 합니다.
        int countBoard = 0;

        try {
            countBoard = session.selectOne("boardCount", board);
        } catch (Exception e) {
            log.error("BoardDao boardCount Exception : {}", e.getMessage());
        }

        return countBoard;
    }


    public int totalnotioceboard() {

        int totalnotioceboard = 0;

        try {
            totalnotioceboard = session.selectOne("noticeboardCount");
        } catch (Exception e) {
            log.error("BoardDao boardCount Exception : {}", e.getMessage());
        }

        return totalnotioceboard;

    }

    public List<Board> listnoticeBoard(Board board) {
        List<Board> listnoticeBoard = null;

        try {
            log.info("board->{}",board.toString());
            listnoticeBoard = session.selectList("listNoticeBoard", board);
            log.info("BoardDao getlistnoticeBoard size : {}", listnoticeBoard.size());

        } catch (Exception e) {
            log.error("BoardDao getlistnoticeBoard Exception : {}", e.getMessage());
        }

        return listnoticeBoard;
    }

    public int InsertnoticeBoard(Board board) {
        int result = 0;
        try {
            result = session.insert("InsertnoticeBoard", board);
        } catch(Exception e) {
            log.info("BoardDaoImpl InsertnoticeBoard Exception => " + e.getMessage());
        }

        return result;

    }

    public int noticeUpdate(Board board) {
        int result = 0;
        TransactionStatus txStatus =
                transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            result = session.update("noticeUpdate", board);
            log.info("noticeUpdate noticeUpdate result => " + result);
            transactionManager.commit(txStatus);
        } catch(Exception e) {
            transactionManager.rollback(txStatus);
            log.info("AccomodationDaoImpl updateAccomodation Exception => " + e.getMessage());
            result = -1;
        }

        return result;

    }

    public Board detailnotice(int id) {
        Board board = new Board();

        try {
            board = session.selectOne("noticeDetail", id);
            log.info("Board noticeDetail() notice.getTitle ->" + board.getTitle());

        } catch (Exception e) {
            log.info("Board noticeDetail() ->" + e.getMessage());
        }
        return board;

    }

    public int noticeDelete(int id) {
        int noticeDelete = 0;
        try {
            noticeDelete = session.delete("noticeDelete",id);
        } catch (Exception e) {
        }

        return noticeDelete;


    }

    public int totalQNAboard() {

        int totalQNAboard = 0;

        try {
            totalQNAboard = session.selectOne("QNAboardCount");
        } catch (Exception e) {
            log.error("BoardDao boardCount Exception : {}", e.getMessage());
        }

        return totalQNAboard;

    }

    public List<Board> listQNABoard(Board board) {

        List<Board> listQNABoard = null;

        try {
            log.info("board->{}",board.toString());
            listQNABoard = session.selectList("listQNABoard", board);
            log.info("BoardDao getlistQNABoard size : {}", listQNABoard.size());

        } catch (Exception e) {
            log.error("BoardDao getlistQNABoard Exception : {}", e.getMessage());
        }

        return listQNABoard;
    }

    public Board detailQNA(int id) {
        Board board = new Board();

        try {
            board = session.selectOne("detailQNA", id);
            log.info("Board detailQNA() QNA.getTitle ->" + board.getTitle());

        } catch (Exception e) {
            log.info("Board detailQNA() ->" + e.getMessage());
        }
        return board;
    }

    public int InsertQNABoard(Board board) {
        int result = 0;
        try {
            result = session.insert("InsertQNABoard", board);
        } catch(Exception e) {
            log.info("BoardDaoImpl InsertQNABoard Exception => " + e.getMessage());
        }

        return result;
    }


    public int QNAUpdate(Board board) {
        int result = 0;
        TransactionStatus txStatus =
                transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            result = session.update("QNAUpdate", board);
            log.info("QNAUpdate QNAUpdate result => " + result);
            transactionManager.commit(txStatus);
        } catch(Exception e) {
            transactionManager.rollback(txStatus);
            log.info("BoardDaoImpl QNAUpdate Exception => " + e.getMessage());
            result = -1;
        }

        return result;
    }

    public int QNADelete(int id) {
        int QNADelete = 0;
        try {
            QNADelete = session.delete("QNADelete",id);
        } catch (Exception e) {
        }

        return QNADelete;
    }

    public int totalFAQboard() {
        int totalFAQboard = 0;

        try {
            totalFAQboard = session.selectOne("FAQboardCount");
        } catch (Exception e) {
            log.error("BoardDao boardCount Exception : {}", e.getMessage());
        }

        return totalFAQboard;
    }

    public List<Board> listFAQBoard(Board board) {
        List<Board> listFAQBoard = null;

        try {
            log.info("board->{}",board.toString());
            listFAQBoard = session.selectList("listFAQBoard", board);
            log.info("BoardDao getlistFAQBoard size : {}", listFAQBoard.size());

        } catch (Exception e) {
            log.error("BoardDao getlistFAQBoard Exception : {}", e.getMessage());
        }

        return listFAQBoard;
    }

    public Board detailFAQ(int id) {
        Board board = new Board();

        try {
            board = session.selectOne("detailFAQ", id);
            log.info("Board detailFAQ() QNA.getTitle ->" + board.getTitle());

        } catch (Exception e) {
            log.info("Board detailQNA() ->" + e.getMessage());
        }
        return board;
    }

    public int InsertFAQBoard(Board board) {
        int result = 0;
        try {
            result = session.insert("InsertFAQBoard", board);
        } catch(Exception e) {
            log.info("BoardDaoImpl InsertFAQBoard Exception => " + e.getMessage());
        }

        return result;
    }

    public int FAQUpdate(Board board) {
        int result = 0;
        TransactionStatus txStatus =
                transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            result = session.update("FAQUpdate", board);
            log.info("FAQUpdate FAQUpdate result => " + result);
            transactionManager.commit(txStatus);
        } catch(Exception e) {
            transactionManager.rollback(txStatus);
            log.info("BoardDaoImpl FAQUpdate Exception => " + e.getMessage());
            result = -1;
        }

        return result;
    }

    public int FAQDelete(int id) {
        int FAQDelete = 0;
        try {
            FAQDelete = session.delete("FAQDelete",id);
        } catch (Exception e) {
        }

        return FAQDelete;
    }

    public void increaseReadCount(int id) {
        session.update("increaseReadCount", id);
    }

    public int totalSearchnotice(Board board) {
        int totalSearchnotice = 0;
        try {
            totalSearchnotice = session.selectOne("totalSearchnotice",board);
            log.info("noticeDaoImpl totalSearchnotice()->"+totalSearchnotice);
        } catch (Exception e) {
            log.info("noticeDaoImpl totalSearchnotice ->"+e.getMessage() );
        }
        return totalSearchnotice;
    }


    public List<Board> listSearchNotice(Board board) {
        List<Board> listSearchNotice = null;

        try {
            listSearchNotice = session.selectList("listSearchNotice",board);
            log.info("listSearchNoticeDaoImpl listSearchExperience() => " + listSearchNotice.size());

        } catch (Exception e) {
            log.info("listSearchNoticeDaoImpl listSearchExperience() => " + e.getMessage());
        }
        return listSearchNotice;
    }

    public void commentInsert(Board board) {
        log.info("BoardDao commentInsert Start!!");

        commentShape(board);

        int commentGroupId = board.getCommentGroupId();
        int commentStep =  board.getCommentStep();
        int commentIndent = board.getCommentIndent();

        board.setCommentGroupId(commentGroupId);
        board.setCommentStep(commentStep + 1);
        board.setCommentIndent(commentIndent + 1);

        log.info("BoardDao commentInsert getId : {}", board.getId());
        log.info("BoardDao commentInsert getUserId : {}", board.getUserId());
        log.info("BoardDao commentInsert getCommentGroupId : {}", commentGroupId);
        log.info("BoardDao commentInsert getCommentStep : {}", commentStep);
        log.info("BoardDao commentInsert getCommentIndent : {}", commentIndent);

        session.insert("commentInsert", board);

    }

    private void commentShape(Board board) {
        log.info("BoardDao commentShape Start!!");

        int comment_group_id = 0;
        int comment_step = 0;

        comment_group_id = board.getCommentGroupId();
        comment_step = board.getCommentStep();

        log.info("BoardDao commentInsert comment_group_id : {}", comment_group_id);
        log.info("BoardDao commentInsert comment_step : {}", comment_step);

        session.update("commentInsertTest", board);

    }


        public List<Board> commentDetail(int id) {
            List<Board> comments = session.selectList("commentDetail", id);
            log.info("BoardDao comments size : {}", comments.size());
            return comments;
        }

    public int totalSearchQNA(Board board) {
        int totalSearchQNA = 0;
        try {
            totalSearchQNA = session.selectOne("totalSearchQNA",board);
            log.info("noticeDaoImpl totalSearchQNA()->"+totalSearchQNA);
        } catch (Exception e) {
            log.info("noticeDaoImpl totalSearchQNA ->"+e.getMessage() );
        }
        return totalSearchQNA;
    }

    public List<Board> listSearchQNA(Board board) {
        List<Board> listSearchQNA = null;

        try {
            listSearchQNA = session.selectList("listSearchQNA",board);
            log.info("listSearchQNADaoImpl listSearchExperience() => " + listSearchQNA.size());

        } catch (Exception e) {
            log.info("listSearchQNADaoImpl listSearchExperience() => " + e.getMessage());
        }
        return listSearchQNA;
    }

    public int getCommentCountForBoard(int id) {
            return session.selectOne("getCommentCountForBoard", id);
        }

    public int commentDelete(int id) {
        int commentDelete = 0;
        try {
            commentDelete = session.delete("commentDelete",id);
        } catch (Exception e) {
        }

        return commentDelete;

    }

    public List<Board> listSearchFAQ(Board board) {
        List<Board> listSearchFAQ = null;

        try {
            listSearchFAQ = session.selectList("listSearchFAQ",board);
            log.info("listSearchQNADaoImpl listSearchFAQ() => " + listSearchFAQ.size());

        } catch (Exception e) {
            log.info("listSearchQNADaoImpl listSearchFAQ() => " + e.getMessage());
        }
        return listSearchFAQ;
    }

    public int totalSearchFAQ(Board board) {
        int totalSearchFAQ = 0;
        try {
            totalSearchFAQ = session.selectOne("totalSearchFAQ",board);
            log.info("noticeDaoImpl totalSearchFAQ()->"+totalSearchFAQ);
        } catch (Exception e) {
            log.info("noticeDaoImpl totalSearchFAQ ->"+e.getMessage() );
        }
        return totalSearchFAQ;
    }

}
