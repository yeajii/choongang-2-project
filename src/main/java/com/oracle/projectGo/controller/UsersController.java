package com.oracle.projectGo.controller;

import com.oracle.projectGo.dto.Users;
import com.oracle.projectGo.service.Paging;
import com.oracle.projectGo.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping
public class UsersController {

private final UsersService us;

    @RequestMapping(value = "userList")
    public String userList(Model model, Users users,String currentPage) {
        UUID transactionId = UUID.randomUUID();
        List<Users> listUsers = null;
        try {
            log.info("[{}]{}:{}", transactionId, "userList", "start");

            int totalUsersCount = us.totalUsers(users);
            log.info("totalUsersCount = "+ totalUsersCount);

            Paging page = new Paging(totalUsersCount,currentPage);
            users.setStart(page.getStart());
            users.setEnd(page.getEnd());


            listUsers = us.getSearchUserList(users);
            if (listUsers == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "회원 리스트가 존재하지 않습니다.");
            }
            model.addAttribute("listUsers", listUsers);
            model.addAttribute("page", page);
            model.addAttribute("searchOption", users);
            model.addAttribute("total",totalUsersCount);
        } catch (Exception e) {
            log.error("[{}]{}:{}", transactionId, "userList", e.getMessage());
        } finally {
            log.info("[{}]{}:{}", transactionId, "userList", "end");
        }
        return "admin/user/userList";
    }

    @GetMapping("userDetail/{id}")
    public String userDetail(Model model, Users users, @PathVariable int id) {
        UUID transactionId = UUID.randomUUID();

        try {
            users.setId(id);
            log.info("[{}]{}:{}", transactionId, "userList", "start");

            Users userDetail = us.getUserById(users);
            int buyCount = us.getBuyCount(id);
            log.info("buyCount = "+ buyCount);

            log.info("userDetail = "+ userDetail);
            if (userDetail == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "상세정보가 없습니다");
            }
            model.addAttribute("userDetail", userDetail);
            model.addAttribute("buyCount", buyCount);

        } catch (Exception e) {
            log.error("[{}]{}:{}", transactionId, "userDetail", e.getMessage());
        } finally {
            log.info("[{}]{}:{}", transactionId, "userDetail", "end");
        }
        return "admin/user/userDetail";
    }


    //admin user 정보수정
    @RequestMapping(value = "/userUpdateForm/{id}")
    public String userUpdateForm (Model model, Users users, @PathVariable int id){
        UUID transactionId = UUID.randomUUID();

        try {
            users.setId(id);
            log.info("[{}]{}:{}", transactionId, "userUpdateForm", "start");

            Users userDetail = us.getUserById(users);
            log.info("userDetail = "+ userDetail);

            if (userDetail == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "상세정보가 없습니다");
            }
            model.addAttribute("userDetail", userDetail);

        } catch (Exception e) {
            log.error("[{}]{}:{}", transactionId, "userDetail", e.getMessage());
        } finally {
            log.info("[{}]{}:{}", transactionId, "userDetail", "end");
        }
        return "admin/user/userUpdateForm";
    }


    //개인 user 정보수정
    @RequestMapping(value = "/userUpdateForm1")
    public String userUpdateForm1 (Model model, Users users){
        UUID transactionId = UUID.randomUUID();
        int id = us.getLoggedInId();
        try {
            users.setId(id);
            log.info("[{}]{}:{}", transactionId, "userUpdateForm", "start");

            Users userDetail = us.getUserById(users);
            log.info("userDetail = "+ userDetail);

            if (userDetail == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "상세정보가 없습니다");
            }
            model.addAttribute("userDetail", userDetail);

        } catch (Exception e) {
            log.error("[{}]{}:{}", transactionId, "userDetail", e.getMessage());
        } finally {
            log.info("[{}]{}:{}", transactionId, "userDetail", "end");
        }
        return "admin/user/userUpdateForm";
    }

    @PostMapping(value = "userUpdate")
    public String userUpdate (Model model, Users users){
        UUID transactionId = UUID.randomUUID();
        String userType1 = String.valueOf(us.getLoggedInUserRole());
        try {
            log.info("[{}]{}:{}", transactionId, "userUpdateForm", "start");

            int userUpdateResult = us.userUpdate(users);
            log.info("userUpdate = "+ userUpdateResult);

            log.info("userType1 = "+userType1 );

        } catch (Exception e) {
            log.error("[{}]{}:{}", transactionId, "userUpdate", e.getMessage());
        } finally {
            log.info("[{}]{}:{}", transactionId, "userUpdate", "end");
        }

        if (userType1.equals("ADMIN"))
            return "redirect:userList";
        else {
            return "redirect:/";
        }


    }
}
