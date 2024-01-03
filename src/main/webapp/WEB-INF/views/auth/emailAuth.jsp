<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2023-12-15
  Time: 오전 11:29
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
    <div class="container col-4 justify-content-start border mt-3 p-5">
        <form action="/emailVerify" method="post">
            <input type="hidden" name="userName" value="${userName}">
            <input type="hidden" name="email" value="${userEmail}">
            <div class="mt-5 mb-5">
                <div class="justify-content-center pt-3 pb-3" align="center">
                    <h1>이메일 인증 </h1>
                </div>
                <div class="justify-content-center pb-1" align="center">
                    <p>입력하신 EMAIL에 전송된 인증코드를 입력하시면 <br>
                        회원가입페이지로 이동합니다.</p>
                </div>
                <hr>
                <div class="justify-content-start pb-3">
                    <label for="token" class="form-label mb-2">인증번호입력</label>
                    <input type="text" class="form-control" name="token"id ="token" required>
                </div>
                <div class="justify-content-center pt-3 pb-3" align="center">
                    <button type="submit" class="btn btn-primary">다음</button>
                </div>
            </div>
        </form>
    </div>
</main>

</body>
</html>

