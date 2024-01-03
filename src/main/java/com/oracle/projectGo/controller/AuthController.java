package com.oracle.projectGo.controller;

import com.oracle.projectGo.dto.Users;
import com.oracle.projectGo.service.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping
public class AuthController {

    private final UsersService us;



    @GetMapping("/login")
    public String login() {
        return "auth/loginForm";
    }

    @GetMapping("/idSearch")
    public String idSearchForm() {
        return "auth/idSearch";
    }
    @RequestMapping(value = "/idSearchResult")
    public String idSearchResult(@ModelAttribute Users users , Model model) {


        try {

            users = us.idSearchByPhone(users);

            model.addAttribute("users", users);

        } catch (Exception e){
            log.info(e.getMessage());
        }finally {


        }

        return "auth/idSearchResult";
    }
    @GetMapping("/passwordSearch")
    public String passwordSearchForm() {
        return "auth/passwordSearch";
    }
    @RequestMapping(value = "/passwordSearchResult")
    public String passwordSearchResult(@ModelAttribute Users users , Model model) {

        log.info("aaa");

        try {
            String newPassword = us.getPasswordResetToken(users.getNickname());




        } catch (Exception e) {
            log.info(e.getMessage());
        }

        return "auth/passwordSearchResult";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }

    @GetMapping("/join")
    public String joinfoam() {
        return "auth/joinForm";
    }


    @PostMapping("/signUp")
    public String join(Users users, Model model){


        try {
            int result = us.insertUsers(users);
        } catch (Exception e){
            log.info(e.getMessage());
        }finally {

        }
        return "redirect:/login";

    }

    @ResponseBody
    @RequestMapping(value = "/nickCheck", method = RequestMethod.POST)
    public int nickCheck(@RequestBody String nickname, Users users) {
        log.info("nickCheck start{}=",nickname);
        users.setNickname(nickname);
        int result = us.nickCheck(users);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/emailCheck", method = RequestMethod.POST)
    public int emailCheck(@RequestBody String email, Users users) {
        log.info("emailCheck start{}=",email);
        users.setEmail(email);
        int result = us.emailCheck(users);
        return result;
    }

    @GetMapping("/emailAuth")
    public String emailAuthForm() {
        return "auth/mailAuthForm";
    }

    @PostMapping("/emailAuth")
    public String emailAuth(HttpServletRequest request, Model model) {
        log.info("email1 = "+request.getParameter("email"));
        log.info("name1 = "+ request.getParameter("name"));
        String userName = request.getParameter("name");
        String userEmail = request.getParameter("email");
        String token = us.sendEmail(userEmail);
        log.info("token = "+ token);

        // 토큰을 세션에 저장
        request.getSession().setAttribute("authToken", token);
        model.addAttribute("userName",userName );
        model.addAttribute("userEmail", userEmail);
        return "auth/emailAuth";
    }

    @PostMapping("/emailVerify")
    public String emailVerify(HttpServletRequest request, Model model,HttpServletResponse response) throws IOException {
        String userInputToken = request.getParameter("token");
        String sessionToken = (String) request.getSession().getAttribute("authToken");
        String userName = request.getParameter("userName");
        String userEmail = request.getParameter("email");
        log.info("email2 = "+request.getParameter("email"));
        log.info("name2 = "+ request.getParameter("userName"));

        if (userInputToken.equals(sessionToken)) {

            model.addAttribute("userName", userName);
            model.addAttribute("userEmail", userEmail);
            // 인증 성공, 회원 가입 페이지로 이동
            return "auth/joinForm";
        } else {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('인증번호가 일치하지 않습니다. 다시 진행 해주시기 바랍니다'); location.href='/emailAuth';</script>");
            out.flush();
            return null;
        }
    }
}
