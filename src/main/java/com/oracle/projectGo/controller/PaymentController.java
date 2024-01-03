package com.oracle.projectGo.controller;

import com.oracle.projectGo.dto.GameContents;
import com.oracle.projectGo.dto.Payments;
import com.oracle.projectGo.dto.Users;
import com.oracle.projectGo.service.GameService;
import com.oracle.projectGo.service.Paging;
import com.oracle.projectGo.service.PaymentService;
import com.oracle.projectGo.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/subscribe")
public class PaymentController {

    private final UsersService us;
    private final GameService gs;
    private final PaymentService ps;
//-----------------------------------------------------------------

    // 구독 신청 - 리스트에서 구독할 컨텐츠 조회 페이지
    @RequestMapping(value = "/subscribeView")
    public String gameSubscribe(String currentPage, Model model) {

        // 교육자(2), 일반인(4)인 경우에만 구독 버튼 보임
        Users users = us.getLoggedInUserInfo();
        log.info("로그인 getUserType : {}", users.getUserType());
        String loginUserType = users.getUserType();

        int result = 0;
        if(loginUserType != null && (loginUserType.equals("2") || loginUserType.equals("4"))){
            result = 1;
            System.out.println("교육자(2), 일반인(4)인 경우");
        } else {
            System.out.println("교육자(2), 일반인(4)이 아닌 경우 또는 loginUserType이 null인 경우");
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

        model.addAttribute("subscribeTotalCount", subscribeTotalCount);
        model.addAttribute("page", page);
        model.addAttribute("subscribeGameList", subscribeGameList);
        model.addAttribute("result", result);

        return "subscribe/subscribeView";
    }

//-----------------------------------------------------------------

    // 구독 신청 - 리스트에서 구독할 컨텐츠 클릭한 값들 처리
    @RequestMapping(value = "/subscribeClick", method = RequestMethod.POST)
    public String gameSubscribePay(@RequestParam List<Integer> gameIds, Model model) {
        log.info("gameIds: {}", gameIds);

        // 로그인 한 유저 정보 = 구매자 정보
        Users users = us.getLoggedInUserInfo();
        log.info("로그인 userName  : {}", users.getName());
        log.info("로그인 userId    : {}", users.getId());
        log.info("로그인 userPhone : {}", users.getPhone());

        // 게임 ID 리스트들로 게임정보 받아오기 (버전1)
//      List<GameContents> gameContentsList =  gs.gameContentsListByIds(gameIds);
//      log.info("gameContentsList:{}",gameContentsList);

        // 게임 ID 리스트들로 게임 정보 받아오기 (버전2)
        List<GameContents> gameContentsList = new ArrayList<>();
        int totalPrice = 0;
        String title = "";

        for (Integer gameId : gameIds) {
            GameContents gameContents = gs.getGameContentsById(gameId);     // 각 아이디들의 리스트 조회
            totalPrice += gameContents.getPrice();                          // 결제 총 금액
            gameContentsList.add(gameContents);                             // add()-> ArrayList에서 맨 뒤에 데이터 추가
        }

        if (!gameContentsList.isEmpty()) {
            title = gameContentsList.get(0).getTitle() + " 외 " + (gameContentsList.size() - 1);
        }

        model.addAttribute("gameIds", gameIds);
        model.addAttribute("title", title);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("users", users);

        return "subscribe/subscribePay";
    }

//-----------------------------------------------------------------

    // 결제 - 결제 방법 선택 후 결제
    @PostMapping(value = "/subscribePay")
    public String subscribePay(@RequestParam String gameIds, @RequestParam String paymentType,
                               RedirectAttributes  redirectAttributes) {
        System.out.println("PaymentController subscribePay !");
        System.out.println("구독 클릭한 gameIds-> " + gameIds);

        // 로그인 한 유저 정보 = 구매자 정보
        Users users = us.getLoggedInUserInfo();
        log.info("로그인 loginUserId : {}", users.getId());
        int loginUserId = users.getId();

        // gameIds 값 확인
        for (String gameId : gameIds.split(",")){
            Payments payments  = new Payments();
            String cleanGameId = gameId.replaceAll("[^0-9]", ""); // 숫자 이외의 모든 문자 제거
            payments.setUserId(loginUserId);
            payments.setPaymentType(paymentType);
            payments.setContentId(Integer.parseInt(cleanGameId));

            // 결제하기 클릭 후 payments 테이블에 insert
            ps.subscribePayInsert(payments);
        }
        redirectAttributes.addAttribute("loginUserId", loginUserId);

        return "redirect:/subscribe/subscribeUserPay";
    }

//-----------------------------------------------------------------

    // 내가 구독한 게임 컨텐츠 리스트 조회
    @GetMapping(value = "/subscribeUserPay")
    public String subscribeUserPay(String currentPage ,Model model) {

        Users users = us.getLoggedInUserInfo();
        int loginUserId = users.getId();
        log.info("로그인 loginUserId : {}", users.getId());

        // 내가 구독한 게임 컨텐츠 리스트 총 갯수
        int subscribeUserPayTotalCount = ps.subscribeUserPayTotalCount(loginUserId);
        System.out.println("PaymentController subscribeUserPay subscribeUserPayTotalCount-> " + subscribeUserPayTotalCount);

        // 페이징 작업
        Payments payments = new Payments();
        Paging page = new Paging(subscribeUserPayTotalCount, currentPage);
        payments.setStart(page.getStart());
        payments.setEnd(page.getEnd());
        payments.setUserId(loginUserId);

        // 내가 구독한 게임 컨텐츠 리스트 조회
        List<Payments> mySubscribePayList = ps.mySubscribePayList(payments);
        System.out.println("PaymentController subscribeUserPay mySubscribePayList-> " + mySubscribePayList);

        model.addAttribute("subscribeUserPayTotalCount", subscribeUserPayTotalCount);
        model.addAttribute("page", page);
        model.addAttribute("mySubscribePayList", mySubscribePayList);

        return "subscribe/subscribeUserPay";
    }



}
