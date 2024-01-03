package com.oracle.projectGo.controller;

import com.oracle.projectGo.dto.Board;
import com.oracle.projectGo.dto.GameContents;
import com.oracle.projectGo.dto.Users;
import com.oracle.projectGo.service.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/lookup/board")
public class BoardController {

    private final BoardService boardService;
    private final UsersService usersService;
    private final GameService  gs;
    private final AdminResourceService adminResourceService;


    //공지사항 리스트
    @RequestMapping(value = "/noticeBoardList")
    public String noticeBoardList(Integer pageSize, Board board, String currentPage, Model model) {

        if (pageSize == null) {
            pageSize = 10; // 기본값 설정
        }
        log.info("PageSize: " + pageSize);

        try {
            log.info("[{}]:{}", "Noticeboard", "start");

            int path = 0;

            int totalnoticeboard = boardService.totalnoticeboard();

            BoardPaging page = new BoardPaging(totalnoticeboard, currentPage, pageSize);
            board.setStart(page.getStart());
            board.setEnd(page.getEnd());

            List<Board> listnoticeBoard = boardService.listnoticeBoard(board);

            /*for (Board notice : listnoticeBoard) {
                int commentCount = boardService.getCommentCountForBoard(notice.getId());
                notice.setCommentCount(commentCount);
            }*/


            listnoticeBoard = boardService.listnoticeBoard(board);

            model.addAttribute("pageSize", pageSize);
            model.addAttribute("totalnoticeboard", totalnoticeboard);
            model.addAttribute("listnoticeBoard", listnoticeBoard);
            model.addAttribute("page", page);
            model.addAttribute("path", path);

        } catch (Exception e) {
            log.error("[{}]:{}", "Noticeboard", e.getMessage());
        } finally {
            log.info("[{}]:{}", "Noticeboard", "end");
        }

        return "lookup/notice/notice";
    }

    @RequestMapping(value = "noticeDetail")
    public String noticeDetail(int id, String currentPage, Model model) {


        try {
            log.info("[{}]:{}", "lookup noticeDetail", "start");
            int userId = usersService.getLoggedInId();
            Board board = boardService.detailnotice(id);




            boardService.increaseReadCount(id); // 조회수 증가
            List<Board> comments = boardService.commentDetail(id);
            board.setCommentCount(board.getCommentCount());


            model.addAttribute("board", board);
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("fileAddress", board.getFileAddress());
            model.addAttribute("comments", comments);
            model.addAttribute("userId", userId);


            log.info("[{}]:{}", "fileAddress", board.getFileAddress());

        } catch (Exception e) {
            log.error("[{}]:{}", "lookup noticeDetail", e.getMessage());
        } finally {
            log.info("[{}]:{}", "lookup noticeDetail", "end");
        }
        return "lookup/notice/noticeDetail";
    }




    @Value("${spring.servlet.multipart.location}")
    private String uploadDirectory;

    @RequestMapping(value = "/noticeInsert")
    public String noticeInsert(@ModelAttribute Board board, @RequestParam("publishDate") String publishDateStr,
                               @RequestParam("publishOption") String publishOption, @RequestParam("isPinned") boolean isPinned,
                               @RequestParam("file") MultipartFile file, Model model) {

        int userId = usersService.getLoggedInId();
        board.setUserId(userId);

        log.info("userId: {}", board.getUserId());

        try {
            log.info("[{}]:{}", "lookup noticeInsert", "start");

            // 게시일자 처리
            Timestamp createdAt;
            if ("immediate".equals(publishOption)) {
                createdAt = Timestamp.valueOf(LocalDateTime.now());
            } else {
                createdAt = Timestamp.valueOf(LocalDateTime.parse(publishDateStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            }
            board.setCreatedAt(createdAt);

            if (!file.isEmpty()) {
                // 파일을 파일시스템에 저장
                String fileName = file.getOriginalFilename();
                String absolutePath = new File(uploadDirectory).getAbsolutePath();
                Path path = Paths.get(absolutePath, fileName);
                file.transferTo(path.toFile());

                // 파일 경로를 Board에 설정
                board.setFilePath(path.toString());
                board.setFileName(fileName);

                // 파일 URL 생성. 실제 서비스에서는 적절한 URL로 변경해야 합니다.
                String fileAddress = "http://localhost:8585/file/" + file.getOriginalFilename();
                board.setFileAddress(fileAddress);  // Board 클래스에 setFileAddress 메서드가 필요합니다.
            }

            board.setIsPinned(isPinned);
            board.setBoardType("1");
            boardService.noticeInsert(board);

        } catch (Exception e) {
            log.error("[{}]:{}", "lookup noticeInsert", e.getMessage());
        } finally {
            log.info("[{}]:{}", "lookup noticeInsert", "end");
        }

        return "redirect:/lookup/notice/noticeBoardList";
    }


    @RequestMapping(value = "/noticeInsertForm")
    public String noticeInsertForm(Board board, Model model) {

        int userId = usersService.getLoggedInId();
        board.setUserId(userId);

        log.info("userId: {}", board.getUserId());


        try {
            log.info("[{}]:{}", "lookup noticeInsertForm", "start");

        } catch (Exception e) {
            log.error("[{}]:{}", "lookup accomodationInsertForm", e.getMessage());
        } finally {
            log.info("[{}]:{}", "lookup accomodationInsertForm", "end");
        }
        return "lookup/notice/noticeInsertForm";
    }

    @RequestMapping(value = "noticeUpdate")
    public String noticeUpdate(@ModelAttribute Board board, @RequestParam("publishDate") String publishDateStr,
                               @RequestParam("publishOption") String publishOption, @RequestParam("isPinned") boolean isPinned,
                               @RequestParam("file") MultipartFile file, Model model) {

        board.setBoardType("1");
        int id = board.getId();
        int userId = usersService.getLoggedInId();
        board.setUserId(userId);

        boardService.noticeUpdate(board);

        log.info("id->"+id);

        try {
            Timestamp createdAt;
            if ("immediate".equals(publishOption)) {
                createdAt = Timestamp.valueOf(LocalDateTime.now());
            } else {
                createdAt = Timestamp.valueOf(LocalDateTime.parse(publishDateStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            }
            board.setCreatedAt(createdAt);

            if (!file.isEmpty()) {
                // 파일을 파일시스템에 저장
                String fileName = file.getOriginalFilename();
                String absolutePath = new File(uploadDirectory).getAbsolutePath();
                Path path = Paths.get(absolutePath, fileName);
                file.transferTo(path.toFile());

                // 파일 경로를 Board에 설정
                board.setFilePath(path.toString());
                board.setFileName(fileName);

                // 파일 URL 생성. 실제 서비스에서는 적절한 URL로 변경해야 합니다.
                String fileAddress = "http://localhost:8585/file/" + file.getOriginalFilename();
                board.setFileAddress(fileAddress);  // Board 클래스에 setFileAddress 메서드가 필요합니다.
            }


        } catch (Exception e) {
            log.error("[{}]:{}", "lookup noticeUpdate", e.getMessage());
        } finally {
            log.info("[{}]:{}", "lookup noticeUpdate", "end");
        }
        return "redirect:/lookup/noticeDetail?id="+id;
    }

    @GetMapping(value="/noticeUpdateForm")
    public String noticeUpdateForm(int id, String currentPage, Model model) {
        try {
            log.info("[{}]:{}", "lookup noticeUpdateForm", "start");

            Board board = boardService.detailnotice(id);


            model.addAttribute("currentPage", currentPage);
            model.addAttribute("id",id);
            model.addAttribute("board", board);

        } catch (Exception e) {
            log.error("[{}]:{}", "lookup noticeUpdateForm", e.getMessage());
        } finally {
            log.info("[{}]:{}", "lookup noticeUpdateForm", "end");
        }
        return "lookup/notice/noticeUpdateForm";
    }

    @RequestMapping(value = "noticeDelete")
    public String noticeDelete(int id) {
        try {
            log.info("[{}]:{}", "lookup noticeDelete", "start");
            int result = boardService.noticeDelete(id);
            log.info("Delete result: " + result);
        } catch (Exception e) {
            log.error("[{}]:{}", "lookup noticeDelete", e.getMessage());
        } finally {
            log.info("[{}]:{}", "lookup noticeDelete", "end");
        }
        return "redirect:/lookup/noticeBoardList";
    }


    @RequestMapping(value = "noticeSearch")
    public String noticeSearch(Board board, Integer pageSize, String currentPage, Model model, HttpServletRequest request) {
        try {
            log.info("[{}]:{}", "lookup noticeSearch", "start");
            int totalSearchnotice = boardService.totalSearchnotice(board);

            if (pageSize == null) {
                pageSize = 10; // 기본값 설정
            }


            int path = 1;
            String keyword = request.getParameter("keyword").toLowerCase();
            String title = request.getParameter("title");
            String userId = request.getParameter("userId");
            String content = request.getParameter("content");
            String searchType = request.getParameter("searchType");



            BoardPaging page = new BoardPaging(totalSearchnotice, currentPage, pageSize);
            board.setStart(page.getStart());
            board.setEnd(page.getEnd());
            board.setSearchType(searchType);


            List<Board> listSearchNotice = boardService.listSearchNotice(board);


            model.addAttribute("totalnoticeboard", totalSearchnotice);
            model.addAttribute("listnoticeBoard", listSearchNotice);
            model.addAttribute("page", page);
            model.addAttribute("path", path);
            model.addAttribute("keyword", keyword);
            model.addAttribute("title", title);
            model.addAttribute("content", content);
            model.addAttribute("userId", userId);

        } catch(Exception e){
            log.error("[{}]:{}", "lookup noticeSearch", e.getMessage());
        } finally{
            log.info("[{}]:{}", "lookup notice", "end");
        }
        return "lookup/notice/notice";


    }


    @RequestMapping(value = "/QNABoardList")
    public String QNABoardList(Integer pageSize, Board board, String currentPage, Model model) {

        if (pageSize == null) {
            pageSize = 10; // 기본값 설정
        }
        log.info("PageSize: " + pageSize);

        int userId = usersService.getLoggedInId();
        board.setUserId(userId);

        log.info("userId: " + userId);

        try {
            log.info("[{}]:{}", "Noticeboard", "start");

            int path = 0;

            int totalQNAboard = boardService.totalQNAboard();


            BoardPaging page = new BoardPaging(totalQNAboard, currentPage, pageSize);
            board.setStart(page.getStart());
            board.setEnd(page.getEnd());

            List<Board> listQNABoard = boardService.listQNABoard(board);


            model.addAttribute("totalQNAboard", totalQNAboard);
            model.addAttribute("listQNABoard", listQNABoard);
            model.addAttribute("page", page);
            model.addAttribute("path", path);
            model.addAttribute("userId", userId);

        } catch (Exception e) {
            log.error("[{}]:{}", "Noticeboard", e.getMessage());
        } finally {
            log.info("[{}]:{}", "Noticeboard", "end");
        }

        return "lookup/qna/qna";
    }

    @RequestMapping(value = "QNADetail")
    public String QNADetail(int id, String currentPage, Model model) {

        boardService.increaseReadCount(id);
        List<Board> comments = boardService.commentDetail(id);



        try {
            log.info("[{}]:{}", "lookup QNADetail", "start");
            Board board = boardService.detailQNA(id);
            int userId = usersService.getLoggedInId();

            model.addAttribute("board", board);
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("comments", comments);
            model.addAttribute("userId", userId);

        } catch (Exception e) {
            log.error("[{}]:{}", "lookup QNADetail", e.getMessage());
        } finally {
            log.info("[{}]:{}", "lookup QNADetail", "end");
        }
        return "lookup/qna/qnaDetail";
    }

    @RequestMapping(value = "/QNAInsert")
    public String QNAInsert(Board board, Model model) {

        int userId = usersService.getLoggedInId();
        board.setUserId(userId);

        try {
            log.info("[{}]:{}", "lookup QNAInsert", "start");

            board.setBoardType("3");
            boardService.QNAInsert(board);

        } catch (Exception e) {
            log.error("[{}]:{}", "lookup QNAInsert", e.getMessage());
        } finally {
            log.info("[{}]:{}", "lookup QNAInsert", "end");
        }

        return "redirect:/lookup/board/QNABoardList";
    }

    @RequestMapping(value = "/QNAInsertForm")
    public String QNAInsertForm(Board board, Model model) {



        try {
            log.info("[{}]:{}", "lookup QNAInsertForm", "start");

        } catch (Exception e) {
            log.error("[{}]:{}", "lookup QNAInsertForm", e.getMessage());
        } finally {
            log.info("[{}]:{}", "lookup QNAInsertForm", "end");
        }
        return "lookup/qna/qnaInsertForm";
    }

    @RequestMapping(value = "/QNAUpdate")
    public String QNAUpdate(Board board, String currentPage, Model model) {

        board.setBoardType("3");
        int id = board.getId();

        log.info("id->"+id);

        try {
            log.info("[{}]:{}", "lookup QNAUpdate", "start");
            int result = boardService.QNAUpdate(board);

            model.addAttribute("currentPage", currentPage);
            model.addAttribute("id", board.getId());
        } catch (Exception e) {
            log.error("[{}]:{}", "lookup QNAUpdate", e.getMessage());
        } finally {
            log.info("[{}]:{}", "lookup QNAUpdate", "end");
        }
        return "redirect:/lookup/board/QNADetail?id="+id;
    }

    @GetMapping(value="/QNAUpdateForm")
    public String QNAUpdateForm(int id, String currentPage, Model model) {
        try {
            log.info("[{}]:{}", "lookup QNAUpdateForm", "start");

            Board board = boardService.detailQNA(id);


            model.addAttribute("currentPage", currentPage);
            model.addAttribute("id",id);
            model.addAttribute("board", board);

        } catch (Exception e) {
            log.error("[{}]:{}", "lookup QNAUpdateForm", e.getMessage());
        } finally {
            log.info("[{}]:{}", "lookup QNAUpdateForm", "end");
        }
        return "lookup/qna/qnaUpdateForm";
    }

    @RequestMapping(value = "QNADelete")
    public String QNADelete(int id) {
        try {
            log.info("[{}]:{}", "lookup QNADelete", "start");
            int result = boardService.QNADelete(id);
            log.info("QNADelete result: " + result);
        } catch (Exception e) {
            log.error("[{}]:{}", "lookup noticeDelete", e.getMessage());
        } finally {
            log.info("[{}]:{}", "lookup noticeDelete", "end");
        }
        return "redirect:/lookup/board/QNABoardList";
    }

    @RequestMapping(value = "QNASearch")
    public String QNASearch(Board board, Integer pageSize, String currentPage, Model model, HttpServletRequest request) {
        try {
            log.info("[{}]:{}", "lookup noticeSearch", "start");
            int totalSearchQNA = boardService.totalSearchQNA(board);

            if (pageSize == null) {
                pageSize = 10; // 기본값 설정
            }


            int path = 1;
            String keyword = request.getParameter("keyword");
            String title = request.getParameter("title");
            String userId = request.getParameter("userId");
            String content = request.getParameter("content");
            String searchType = request.getParameter("searchType");



            BoardPaging page = new BoardPaging(totalSearchQNA, currentPage, pageSize);
            board.setStart(page.getStart());
            board.setEnd(page.getEnd());
            board.setSearchType(searchType);


            List<Board> listSearchQNA = boardService.listSearchQNA(board);


            model.addAttribute("totalQNAboard", totalSearchQNA);
            model.addAttribute("listQNABoard", listSearchQNA);
            model.addAttribute("page", page);
            model.addAttribute("path", path);
            model.addAttribute("keyword", keyword);
            model.addAttribute("title", title);
            model.addAttribute("content", content);
            model.addAttribute("userId", userId);

        } catch(Exception e){
            log.error("[{}]:{}", "lookup noticeSearch", e.getMessage());
        } finally{
            log.info("[{}]:{}", "lookup notice", "end");
        }
        return "lookup/qna/qna";


    }

    @RequestMapping(value = "/FAQBoardList")
    public String FAQBoardList(Integer pageSize, Board board, String currentPage, Model model) {

        if (pageSize == null) {
            pageSize = 10; // 기본값 설정
        }

        try {
            log.info("[{}]:{}", "FAQboard", "start");

            int path = 0;

            int totalFAQboard = boardService.totalFAQboard();

            Paging page = new Paging(totalFAQboard, currentPage);
            board.setStart(page.getStart());
            board.setEnd(page.getEnd());

            List<Board> listFAQBoard = boardService.listFAQBoard(board);


            model.addAttribute("totalFAQboard", totalFAQboard);
            model.addAttribute("listFAQBoard", listFAQBoard);
            model.addAttribute("page", page);
            model.addAttribute("path", path);

        } catch (Exception e) {
            log.error("[{}]:{}", "FAQboard", e.getMessage());
        } finally {
            log.info("[{}]:{}", "FAQboard", "end");
        }

        return "lookup/faq/faq";
    }

    @RequestMapping(value = "FAQDetail")
    public String FAQDetail(int id, String currentPage, Model model) {

        try {
            log.info("[{}]:{}", "lookup FAQDetail", "start");
            Board board = boardService.detailFAQ(id);
            boardService.increaseReadCount(id);

            model.addAttribute("board", board);
            model.addAttribute("currentPage", currentPage);

        } catch (Exception e) {
            log.error("[{}]:{}", "lookup FAQDetail", e.getMessage());
        } finally {
            log.info("[{}]:{}", "lookup FAQDetail", "end");
        }
        return "lookup/faq/faqDetail";
    }

    @RequestMapping(value = "/FAQInsert")
    public String FAQInsert(Board board, Model model) {

        try {
            log.info("[{}]:{}", "lookup FAQInsert", "start");

            board.setBoardType("2");
            boardService.FAQInsert(board);

        } catch (Exception e) {
            log.error("[{}]:{}", "lookup FAQInsert", e.getMessage());
        } finally {
            log.info("[{}]:{}", "lookup FAQInsert", "end");
        }

        return "redirect:/lookup/board/FAQBoardList";
    }

    @RequestMapping(value = "/FAQInsertForm")
    public String FAQInsertForm(Model model) {


        try {
            log.info("[{}]:{}", "lookup FAQInsertForm", "start");

        } catch (Exception e) {
            log.error("[{}]:{}", "lookup FAQInsertForm", e.getMessage());
        } finally {
            log.info("[{}]:{}", "lookup FAQInsertForm", "end");
        }
        return "lookup/faq/faqInsertForm";
    }

    @RequestMapping(value = "/FAQUpdate")
    public String FAQUpdate(Board board, String currentPage, Model model) {

        board.setBoardType("2");
        int id = board.getId();

        log.info("id->"+id);

        try {
            log.info("[{}]:{}", "lookup FAQUpdate", "start");
            int result = boardService.FAQUpdate(board);

            model.addAttribute("currentPage", currentPage);
            model.addAttribute("id", board.getId());
        } catch (Exception e) {
            log.error("[{}]:{}", "lookup FAQUpdate", e.getMessage());
        } finally {
            log.info("[{}]:{}", "lookup FAQUpdate", "end");
        }
        return "redirect:/lookup/board/FAQDetail?id="+id;
    }

    @GetMapping(value="/FAQUpdateForm")
    public String FAQUpdateForm(int id, String currentPage, Model model) {
        try {
            log.info("[{}]:{}", "lookup FAQUpdateForm", "start");

            Board board = boardService.detailFAQ(id);


            model.addAttribute("currentPage", currentPage);
            model.addAttribute("id",id);
            model.addAttribute("board", board);

        } catch (Exception e) {
            log.error("[{}]:{}", "lookup FAQUpdateForm", e.getMessage());
        } finally {
            log.info("[{}]:{}", "lookup FAQUpdateForm", "end");
        }
        return "lookup/faq/faqUpdateForm";
    }

    @RequestMapping(value = "FAQDelete")
    public String FAQDelete(int id) {
        try {
            log.info("[{}]:{}", "lookup FAQDelete", "start");
            int result = boardService.FAQDelete(id);
            log.info("FAQDelete result: " + result);
        } catch (Exception e) {
            log.error("[{}]:{}", "lookup FAQDelete", e.getMessage());
        } finally {
            log.info("[{}]:{}", "lookup FAQDelete", "end");
        }
        return "forward:lookup/faq/faq";
    }

    @RequestMapping(value = "FAQSearch")
    public String FAQSearch(Board board, Integer pageSize, String currentPage, Model model, HttpServletRequest request) {
        try {
            log.info("[{}]:{}", "lookup noticeSearch", "start");
            int totalSearchFAQ = boardService.totalSearchFAQ(board);

            if (pageSize == null) {
                pageSize = 10; // 기본값 설정
            }


            int path = 1;
            String keyword = request.getParameter("keyword");
            String title = request.getParameter("title");
            String userId = request.getParameter("userId");
            String content = request.getParameter("content");
            String searchType = request.getParameter("searchType");


            BoardPaging page = new BoardPaging(totalSearchFAQ, currentPage, pageSize);
            board.setStart(page.getStart());
            board.setEnd(page.getEnd());
            board.setSearchType(searchType);


            List<Board> listSearchFAQ = boardService.listSearchFAQ(board);


            model.addAttribute("totalFAQboard", totalSearchFAQ);
            model.addAttribute("listFAQBoard", listSearchFAQ);
            model.addAttribute("page", page);
            model.addAttribute("path", path);
            model.addAttribute("keyword", keyword);
            model.addAttribute("title", title);
            model.addAttribute("content", content);
            model.addAttribute("userId", userId);

        } catch (Exception e) {
            log.error("[{}]:{}", "lookup noticeSearch", e.getMessage());
        } finally {
            log.info("[{}]:{}", "lookup notice", "end");
        }
        return "lookup/faq/faq";

    }

    @RequestMapping(value = "/sitemap")
    public String sitemap(Model model) {


        try {
            log.info("[{}]:{}", "lookup sitemap", "start");

        } catch (Exception e) {
            log.error("[{}]:{}", "lookup sitemap", e.getMessage());
        } finally {
            log.info("[{}]:{}", "lookup sitemap", "end");
        }
        return "sitemap/sitemap";
    }
    // 댓글 기능 form Logic
    @RequestMapping(value = "/commentInsertForm")
    public String commentInsertForm(int id, int userId, Model model) {

        log.info("BoardController commentInsertForm boardId : {} ", id);
        log.info("BoardController commentInsertForm userId : {} ", userId);

        Board boards = boardService.detailnotice(id);

        log.info("BoardController commentInsertForm getComment_group_id : {} ", boards.getCommentGroupId());
        log.info("BoardController commentInsertForm getComment_step : {} ", boards.getCommentStep());
        log.info("BoardController commentInsertForm getComment_indent : {} ", boards.getCommentIndent());

        model.addAttribute("board", boards);
        model.addAttribute("userId", userId);

        return "lookup/notice/commentInsertForm";
    }

    // 댓글 기능 생성 Logic
    @RequestMapping(value = "/commentInsert")
    public String commentInsert(Board board, Model model) {


        board.setCommentGroupId(board.getId());
        board.setUserId(usersService.getLoggedInId()); // 사용자 아이디 설정
        boardService.commentInsert(board);
        int userId = usersService.getLoggedInId();

        log.info("BoardController commentInsert boardId : {} ", board.getId());
        log.info("BoardController commentInsert userId : {} ", board.getUserId());
        log.info("BoardController commentInsert Content : {} ", board.getContent());
        log.info("BoardController commentInsert CommentGroupId : {} ", board.getCommentGroupId());

        model.addAttribute("id", board.getCommentGroupId());
        model.addAttribute("userId", userId);

        int boardType = Integer.parseInt(board.getBoardType());

        log.info("BoardController commentInsert boardType : {} ", board.getBoardType());

        if (boardType == 1) {
            return "redirect:noticeDetail?id=" + board.getCommentGroupId();
        } else if (boardType == 3) {
            return "redirect:QNADetail?id=" + board.getCommentGroupId();
        } else {
            return "redirect:home";
        }

    }
    @RequestMapping(value = "commentDelete")
    public String commentDelete(int id, Board board, Model model) {
        try {
            log.info("[{}]:{}", "commentDelete", "start");

            board.setId(board.getId());
            int result = boardService.commentDelete(id);

            log.info("CommentDelete result: " + result);


            model.addAttribute("id", board.getId());

            log.info("CommentDelete id : " + id);

        } catch (Exception e) {
            log.error("[{}]:{}", "commentDelete", e.getMessage());
        } finally {
            log.info("[{}]:{}", "commentDelete", "end");
        }

        // 댓글 삭제 후 리다이렉트 등의 작업을 수행할 수 있음
        return "lookup/notice/noticeDetail";
    }

    @RequestMapping(value = "/gameContentSelect")
    public String gameSelect(String currentPage, Model model){

        // 총 갯수(운영자 화면)
        int gameContentsTotalCount = gs.gameContentsTotalCount();
        System.out.println("GameController gameContentsTotalCount-> " + gameContentsTotalCount);

        // 페이징 작업
        GameContents gameContents = new GameContents();
        Paging page = new Paging(gameContentsTotalCount, currentPage);
        gameContents.setStart(page.getStart());
        gameContents.setEnd(page.getEnd());

        // 리스트 조회(운영자 화면)
        List<GameContents> gameContentsList = gs.gameContentsList(gameContents);
        System.out.println("GameController gameContentsList.size()-> " + gameContentsList.size());

        model.addAttribute("gameContentsTotalCount", gameContentsTotalCount);
        model.addAttribute("page", page);
        model.addAttribute("gameContentsList", gameContentsList);

        return "lookup/game/gameContentSelect";
    }

    @GetMapping(value = "/listEdu")
    public String educationList(Users users, Model model) {

        users = usersService.getLoggedInUserInfo();
        model.addAttribute("users", users);

        return "lookup/resource/listEdu";
    }

    @RequestMapping(value = "/subscribeView")
    public String gameSubscribe(String currentPage, Model model) {

        // 총 갯수(리스트에서 구독할 컨텐츠 조회 페이지)
        int subscribeTotalCount = gs.subscribeTotalCount();
        System.out.println("GameController subscribeTotalCount-> " + subscribeTotalCount);

        // 페이징 작업
        GameContents gameContents = new GameContents();
        Paging page = new Paging(subscribeTotalCount, currentPage);
        gameContents.setStart(page.getStart());
        gameContents.setEnd(page.getEnd());

        // 리스트 조회(리스트에서 구독할 컨텐츠 조회 페이지)
        List<GameContents> subscribeGameList = gs.subscribeGameList(gameContents);
        System.out.println("GameController subscribeGameList.size()-> " + subscribeGameList.size());

        model.addAttribute("subscribeTotalCount", subscribeTotalCount);
        model.addAttribute("page", page);
        model.addAttribute("subscribeGameList", subscribeGameList);

        return "lookup/subscribe/subscribeView";
    }
















}









