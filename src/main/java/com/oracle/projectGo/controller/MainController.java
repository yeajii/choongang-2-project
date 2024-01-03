package com.oracle.projectGo.controller;

import com.oracle.projectGo.dto.Board;
import com.oracle.projectGo.dto.GameContents;
import com.oracle.projectGo.service.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {

    private final UsersService us;
    private final GameService gs;
    private final BoardService bs;

    @GetMapping("/")
    public String home(HttpServletRequest request, String currentPage, Model model) {
        if (request.getParameter("error") != null) {
            model.addAttribute("error", "접근권한이 없습니다.");
        }

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

        model.addAttribute("subscribeGameList", subscribeGameList);

        // 공지 리스트 조회
        int totalnoticeboard = bs.totalnoticeboard();
        Board board = new Board();
        Paging boardpage = new Paging(totalnoticeboard, currentPage);
        board.setStart(boardpage.getStart());
        board.setEnd(boardpage.getEnd());

        List<Board> listnoticeBoard = bs.listnoticeBoard(board);

        // isPinned가 true인 공지만 필터링
        List<Board> pinnedNoticeBoard = listnoticeBoard.stream()
                .filter(Board::getIsPinned)
                .collect(Collectors.toList());

        System.out.println("listnoticeBoard listnoticeBoard.size()-> " + listnoticeBoard.size());
        model.addAttribute("listnoticeBoard", pinnedNoticeBoard);


        return "home";
    }
}
