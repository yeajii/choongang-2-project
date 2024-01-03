package com.oracle.projectGo.controller.admin;

import com.oracle.projectGo.dto.Board;
import com.oracle.projectGo.service.BoardPaging;
import com.oracle.projectGo.service.BoardService;
import com.oracle.projectGo.service.Paging;
import com.oracle.projectGo.service.UsersService;
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
@RequestMapping(value = "/admin/board")

public class AdminBoardController {

	@Value("${spring.servlet.multipart.location}")

	private String uploadDirectory;

	private final BoardService boardService;
	private final UsersService usersService;

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

		return "admin/notice/notice";
	}

	@RequestMapping(value = "noticeDetail")
	public String noticeDetail(int id, String currentPage, Model model) {

		try {
			log.info("[{}]:{}", "admin noticeDetail", "start");
			Board board = boardService.detailnotice(id);


			boardService.increaseReadCount(id); // 조회수 증가
			List<Board> comments = boardService.commentDetail(id);
			board.setCommentCount(board.getCommentCount());


			model.addAttribute("board", board);
			model.addAttribute("currentPage", currentPage);
			model.addAttribute("fileAddress", board.getFileAddress());
			model.addAttribute("comments", comments);

			log.info("[{}]:{}", "fileAddress", board.getFileAddress());

		} catch (Exception e) {
			log.error("[{}]:{}", "admin noticeDetail", e.getMessage());
		} finally {
			log.info("[{}]:{}", "admin noticeDetail", "end");
		}
		return "admin/notice/noticeDetail";
	}


	@RequestMapping(value = "/noticeInsert")
	public String noticeInsert(@ModelAttribute Board board, @RequestParam("publishDate") String publishDateStr, @RequestParam("publishOption") String publishOption, @RequestParam("isPinned") boolean isPinned,
							   @RequestParam("file") MultipartFile file, Model model) {

		int userId = usersService.getLoggedInId();
		board.setUserId(userId);

		log.info("userId: {}", board.getUserId());

		try {
			log.info("[{}]:{}", "admin noticeInsert", "start");

			// 게시일자 처리
			Timestamp createdAt;
			if ("immediate".equals(publishOption)) {
				createdAt = Timestamp.valueOf(LocalDateTime.now());
			} else {
				createdAt = Timestamp.valueOf(LocalDateTime.parse(publishDateStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
			}
			board.setCreatedAt(createdAt);

			// 파일 처리 부분을 `MultipartFile` 파라미터를 사용하여 진행
			if (!file.isEmpty()) {
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

			/*if (!file.isEmpty()) {
				// 파일을 파일시스템에 저장
				String fileName = file.getOriginalFilename();

				// 프로젝트의 경로를 가져옵니다.
				String projectPath = new File("").getAbsolutePath();

				// 상대 경로를 생성합니다. 이 예에서는 프로젝트 경로 아래의 'uploaded' 폴더에 파일을 저장합니다.
				String relativePath = "/upload/notice/";

				// 상대 경로를 절대 경로로 변환합니다.
				String absolutePath = Paths.get(projectPath, relativePath).toString();

				Path path = Paths.get(absolutePath, fileName);
				file.transferTo(path.toFile());

				// 파일 경로를 Board에 설정
				board.setFilePath(path.toString());
				board.setFileName(fileName);

				// 파일 URL 생성. 실제 서비스에서는 적절한 URL로 변경해야 합니다.
				// 이 예에서는 프로젝트 경로를 기반으로 상대 URL을 생성합니다.
				String fileAddress = "http://localhost:8585/file/" + file.getOriginalFilename();
				board.setFileAddress(fileAddress);  // Board 클래스에 setFileAddress 메서드가 필요합니다.
			}*/

			board.setIsPinned(isPinned);
			board.setBoardType("1");
			boardService.noticeInsert(board);

		} catch (Exception e) {
			log.error("[{}]:{}", "admin noticeInsert", e.getMessage());
		} finally {
			log.info("[{}]:{}", "admin noticeInsert", "end");
		}

		return "redirect:/admin/board/noticeBoardList";
	}


	@RequestMapping(value = "/noticeInsertForm")
	public String noticeInsertForm(Board board, Model model) {

		int userId = usersService.getLoggedInId();
		board.setUserId(userId);

		log.info("userId: {}", board.getUserId());


		try {
			log.info("[{}]:{}", "admin noticeInsertForm", "start");

		} catch (Exception e) {
			log.error("[{}]:{}", "admin accomodationInsertForm", e.getMessage());
		} finally {
			log.info("[{}]:{}", "admin accomodationInsertForm", "end");
		}
		return "admin/notice/noticeInsertForm";
	}


	@RequestMapping(value = "/noticeUpdate")
	public String noticeUpdate(@ModelAttribute Board board, @RequestParam("file") MultipartFile file, Model model) {
		board.setBoardType("1");
		int id = board.getId();

		log.info("id->" + id);

		String filePath = board.getFilePath();
		String fileName = board.getFileName();
		String fileAddress = board.getFileAddress();

		try {
			// 기존 파일의 정보 가져오기
			Board existingBoard = boardService.detailnotice(id);
			String existingFilePath = existingBoard.getFilePath();
			String existingFileName = existingBoard.getFileName();
			String existingFileAddress = existingBoard.getFileAddress();

			if (!file.isEmpty()) {
				// 새로운 파일이 전송된 경우
				fileName = file.getOriginalFilename();
				String absolutePath = new File(uploadDirectory).getAbsolutePath();
				Path path = Paths.get(absolutePath, fileName);
				file.transferTo(path.toFile());

				// 새로운 파일 정보 설정
				filePath = path.toString();
				fileAddress = "http://localhost:8585/file/" + file.getOriginalFilename();
			} else {
				// 새로운 파일이 전송되지 않은 경우, 기존 파일 정보 유지
				filePath = existingFilePath;
				fileName = existingFileName;
				fileAddress = existingFileAddress;
			}

			// 파일 정보를 모델에 추가
			model.addAttribute("filePath", filePath);
			model.addAttribute("fileName", fileName);
			model.addAttribute("fileAddress", fileAddress);

			// 기존 파일 정보 설정
			board.setFilePath(filePath);
			board.setFileName(fileName);
			board.setFileAddress(fileAddress);

			boardService.noticeUpdate(board);
		} catch (Exception e) {
			log.error("[{}]:{}", "admin noticeUpdate", e.getMessage());
		} finally {
			log.info("[{}]:{}", "admin noticeUpdate", "end");
		}
		return "redirect:/admin/board/noticeDetail?id=" + id;
	}

	@GetMapping(value = "/noticeUpdateForm")
	public String noticeUpdateForm(int id, String currentPage, Model model) {
		try {
			log.info("[{}]:{}", "admin noticeUpdateForm", "start");

			Board board = boardService.detailnotice(id);

			// 기존 파일의 정보 가져오기
			String filePath = board.getFilePath();
			String fileName = board.getFileName();
			String fileAddress = board.getFileAddress();

			// 모델에 파일 정보를 추가
			model.addAttribute("currentPage", currentPage);
			model.addAttribute("id", id);
			model.addAttribute("board", board);
			model.addAttribute("filePath", filePath);
			model.addAttribute("fileName", fileName);
			model.addAttribute("fileAddress", fileAddress);

		} catch (Exception e) {
			log.error("[{}]:{}", "admin noticeUpdateForm", e.getMessage());
		} finally {
			log.info("[{}]:{}", "admin noticeUpdateForm", "end");
		}
		return "admin/notice/noticeUpdateForm";
	}

	@RequestMapping(value = "noticeDelete")
	public String noticeDelete(int id) {
		try {
			log.info("[{}]:{}", "admin noticeDelete", "start");
			int result = boardService.noticeDelete(id);
			log.info("Delete result: " + result);
		} catch (Exception e) {
			log.error("[{}]:{}", "admin noticeDelete", e.getMessage());
		} finally {
			log.info("[{}]:{}", "admin noticeDelete", "end");
		}
		return "redirect:/admin/board/noticeBoardList";
	}


	@RequestMapping(value = "noticeSearch")
	public String noticeSearch(Board board, Integer pageSize, String currentPage, Model model, HttpServletRequest request) {
		try {
			log.info("[{}]:{}", "admin noticeSearch", "start");
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
				log.error("[{}]:{}", "admin noticeSearch", e.getMessage());
			} finally{
				log.info("[{}]:{}", "admin notice", "end");
			}
			return "admin/notice/notice";


	}


	@RequestMapping(value = "/QNABoardList")
	public String QNABoardList(Integer pageSize, Board board, String currentPage, Model model) {

		if (pageSize == null) {
			pageSize = 10; // 기본값 설정
		}
		log.info("PageSize: " + pageSize);

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


		} catch (Exception e) {
			log.error("[{}]:{}", "Noticeboard", e.getMessage());
		} finally {
			log.info("[{}]:{}", "Noticeboard", "end");
		}

		return "admin/qna/qna";
	}

	@RequestMapping(value = "QNADetail")
	public String QNADetail(int id, String currentPage, Model model) {

		boardService.increaseReadCount(id);
		List<Board> comments = boardService.commentDetail(id);

		try {
			log.info("[{}]:{}", "admin QNADetail", "start");
			Board board = boardService.detailQNA(id);

			model.addAttribute("board", board);
			model.addAttribute("currentPage", currentPage);
			model.addAttribute("comments", comments);

		} catch (Exception e) {
			log.error("[{}]:{}", "admin QNADetail", e.getMessage());
		} finally {
			log.info("[{}]:{}", "admin QNADetail", "end");
		}
		return "admin/qna/qnaDetail";
	}

	@RequestMapping(value = "/QNAInsert")
	public String QNAInsert(Board board, Model model) {

		int userId = usersService.getLoggedInId();
		board.setUserId(userId);

		try {
			log.info("[{}]:{}", "admin QNAInsert", "start");

			board.setBoardType("3");
			boardService.QNAInsert(board);

		} catch (Exception e) {
			log.error("[{}]:{}", "admin QNAInsert", e.getMessage());
		} finally {
			log.info("[{}]:{}", "admin QNAInsert", "end");
		}

		return "redirect:/admin/board/QNABoardList";
	}

	@RequestMapping(value = "/QNAInsertForm")
	public String QNAInsertForm(Board board, Model model) {

		int userId = usersService.getLoggedInId();
		board.setUserId(userId);


		try {
			log.info("[{}]:{}", "admin QNAInsertForm", "start");

		} catch (Exception e) {
			log.error("[{}]:{}", "admin QNAInsertForm", e.getMessage());
		} finally {
			log.info("[{}]:{}", "admin QNAInsertForm", "end");
		}
		return "admin/qna/qnaInsertForm";
	}

	@RequestMapping(value = "/QNAUpdate")
	public String QNAUpdate(Board board, String currentPage, Model model) {

		board.setBoardType("3");
		int id = board.getId();

		log.info("id->"+id);

		try {
			log.info("[{}]:{}", "admin QNAUpdate", "start");
			int result = boardService.QNAUpdate(board);

			model.addAttribute("currentPage", currentPage);
			model.addAttribute("id", board.getId());
		} catch (Exception e) {
			log.error("[{}]:{}", "admin QNAUpdate", e.getMessage());
		} finally {
			log.info("[{}]:{}", "admin QNAUpdate", "end");
		}
		return "redirect:/admin/board/QNADetail?id="+id;
	}

	@GetMapping(value="/QNAUpdateForm")
	public String QNAUpdateForm(int id, String currentPage, Model model) {
		try {
			log.info("[{}]:{}", "admin QNAUpdateForm", "start");

			Board board = boardService.detailQNA(id);


			model.addAttribute("currentPage", currentPage);
			model.addAttribute("id",id);
			model.addAttribute("board", board);

		} catch (Exception e) {
			log.error("[{}]:{}", "admin QNAUpdateForm", e.getMessage());
		} finally {
			log.info("[{}]:{}", "admin QNAUpdateForm", "end");
		}
		return "admin/qna/qnaUpdateForm";
	}

	@RequestMapping(value = "QNADelete")
	public String QNADelete(int id) {
		try {
			log.info("[{}]:{}", "admin QNADelete", "start");
			int result = boardService.QNADelete(id);
			log.info("QNADelete result: " + result);
		} catch (Exception e) {
			log.error("[{}]:{}", "admin noticeDelete", e.getMessage());
		} finally {
			log.info("[{}]:{}", "admin noticeDelete", "end");
		}
		return "redirect:/admin/board/QNABoardList";
	}

	@RequestMapping(value = "QNASearch")
	public String QNASearch(Board board, Integer pageSize, String currentPage, Model model, HttpServletRequest request) {
		try {
			log.info("[{}]:{}", "admin noticeSearch", "start");
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
			log.error("[{}]:{}", "admin noticeSearch", e.getMessage());
		} finally{
			log.info("[{}]:{}", "admin notice", "end");
		}
		return "admin/qna/qna";


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

		return "admin/faq/faq";
	}

	@RequestMapping(value = "FAQDetail")
	public String FAQDetail(int id, String currentPage, Model model) {

		try {
			log.info("[{}]:{}", "admin FAQDetail", "start");
			Board board = boardService.detailFAQ(id);
			boardService.increaseReadCount(id);

			model.addAttribute("board", board);
			model.addAttribute("currentPage", currentPage);

		} catch (Exception e) {
			log.error("[{}]:{}", "admin FAQDetail", e.getMessage());
		} finally {
			log.info("[{}]:{}", "admin FAQDetail", "end");
		}
		return "admin/faq/faqDetail";
	}

	@RequestMapping(value = "/FAQInsert")
	public String FAQInsert(Board board, Model model) {

		int userId = usersService.getLoggedInId();
		board.setUserId(userId);

		try {
			log.info("[{}]:{}", "admin FAQInsert", "start");

			board.setBoardType("2");
			boardService.FAQInsert(board);

		} catch (Exception e) {
			log.error("[{}]:{}", "admin FAQInsert", e.getMessage());
		} finally {
			log.info("[{}]:{}", "admin FAQInsert", "end");
		}

		return "redirect:/admin/board/FAQBoardList";
	}

	@RequestMapping(value = "/FAQInsertForm")
	public String FAQInsertForm(Model model) {


		try {
			log.info("[{}]:{}", "admin FAQInsertForm", "start");

		} catch (Exception e) {
			log.error("[{}]:{}", "admin FAQInsertForm", e.getMessage());
		} finally {
			log.info("[{}]:{}", "admin FAQInsertForm", "end");
		}
		return "admin/faq/faqInsertForm";
	}

	@RequestMapping(value = "/FAQUpdate")
	public String FAQUpdate(Board board, String currentPage, Model model) {

		board.setBoardType("2");
		int id = board.getId();

		log.info("id->"+id);

		try {
			log.info("[{}]:{}", "admin FAQUpdate", "start");
			int result = boardService.FAQUpdate(board);

			model.addAttribute("currentPage", currentPage);
			model.addAttribute("id", board.getId());
		} catch (Exception e) {
			log.error("[{}]:{}", "admin FAQUpdate", e.getMessage());
		} finally {
			log.info("[{}]:{}", "admin FAQUpdate", "end");
		}
		return "redirect:/admin/board/FAQDetail?id="+id;
	}

	@GetMapping(value="/FAQUpdateForm")
	public String FAQUpdateForm(int id, String currentPage, Model model) {
		try {
			log.info("[{}]:{}", "admin FAQUpdateForm", "start");

			Board board = boardService.detailFAQ(id);


			model.addAttribute("currentPage", currentPage);
			model.addAttribute("id",id);
			model.addAttribute("board", board);

		} catch (Exception e) {
			log.error("[{}]:{}", "admin FAQUpdateForm", e.getMessage());
		} finally {
			log.info("[{}]:{}", "admin FAQUpdateForm", "end");
		}
		return "admin/faq/faqUpdateForm";
	}

	@RequestMapping(value = "FAQDelete")
	public String FAQDelete(int id) {
		try {
			log.info("[{}]:{}", "admin FAQDelete", "start");
			int result = boardService.FAQDelete(id);
			log.info("FAQDelete result: " + result);
		} catch (Exception e) {
			log.error("[{}]:{}", "admin FAQDelete", e.getMessage());
		} finally {
			log.info("[{}]:{}", "admin FAQDelete", "end");
		}
		return "redirect:/admin/board/FAQBoardList";
	}

	@RequestMapping(value = "FAQSearch")
	public String FAQSearch(Board board, Integer pageSize, String currentPage, Model model, HttpServletRequest request) {
		try {
			log.info("[{}]:{}", "admin noticeSearch", "start");
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
			log.error("[{}]:{}", "admin noticeSearch", e.getMessage());
		} finally {
			log.info("[{}]:{}", "admin notice", "end");
		}
		return "admin/faq/faq";

	}

	@RequestMapping(value = "/sitemap")
	public String sitemap(Model model) {


		try {
			log.info("[{}]:{}", "admin sitemap", "start");

		} catch (Exception e) {
			log.error("[{}]:{}", "admin sitemap", e.getMessage());
		} finally {
			log.info("[{}]:{}", "admin sitemap", "end");
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

		return "admin/notice/commentInsertForm";
	}

	// 댓글 기능 생성 Logic
	@RequestMapping(value = "/commentInsert")
	public String commentInsert(Board board, Model model) {




		int userId = usersService.getLoggedInId();
		board.setUserId(userId);
		board.setCommentGroupId(board.getId());
		boardService.commentInsert(board);

		log.info("BoardController commentInsert boardId : {} ", board.getId());
		log.info("BoardController commentInsert userId : {} ", board.getUserId());
		log.info("BoardController commentInsert Content : {} ", board.getContent());
		log.info("BoardController commentInsert CommentGroupId : {} ", board.getCommentGroupId());

		model.addAttribute("id", board.getCommentGroupId());
		model.addAttribute("userId", board.getUserId());

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
		return "admin/notice/noticeDetail";
	}

















}









