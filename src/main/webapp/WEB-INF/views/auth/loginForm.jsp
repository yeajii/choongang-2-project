<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2023-12-07
  Time: 오전 9:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <%@ include file="/WEB-INF/components/Header.jsp"%>
    <title>Title</title>
</head>
<body>
<%@ include file="/WEB-INF/components/TopBar.jsp"%>
<main>
    <div id="main-content">
        <main class=" m-5 p-5" >
            <div class="container col-4 justify-content-start ">
                <form action="/login" method="post">
                    <div class="mt-5 mb-5">
                        <div class="justify-content-start pt-5 pb-3">
                            <label class="form-label mb-2">회원ID</label>
                            <input type="text" class="form-control" id="nickname" name="nickname">
                        </div>
                        <div class="justify-content-start pb-3">
                            <label class="form-label mb-2">비밀번호</label>
                            <input type="password" class="form-control" id="password" name="password"><p>
                        </div>
                        <input type="submit" value="로그인" class="btn btn-primary form-control">
                        <div class="form-check form-switch my-4">
                            <input class="form-check-input" type="checkbox" id="stayLogin">
                            <label class="form-check-label" for="stayLogin">로그인 상태 유지</label>
                        </div>
                        <hr>
                        <div class="d-flex justify-content-between">
                            <div>
                                <a class="search-text" href="idSearch">아이디 찾기</a> |
                                <a class="search-text" href="passwordSearch">비밀번호 찾기</a>
                            </div>
                            <div class="go-login">
                                <span class="member-yet">아직 회원이 아니세요? <a id="link-join" href="emailAuth">회원가입 하기</a></span>
                            </div>
                        </div>
                    </div>
                </form>
                <!--<button onclick="loginWithKakao()">카카오 로그인</button> -->
            </div>
        </main>
    </div>
</main>
<%@ include file="/WEB-INF/components/Footer.jsp"%>
</body>
</html>
