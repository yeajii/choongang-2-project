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
        <form action="/passwordSearchResult" method="post">
            <div class="mt-5 mb-5">
                <div class="justify-content-center pt-3 pb-3" align="center">
                    <h1>비밀번호 찾기</h1>
                </div>
                <div class="justify-content-center pb-1" align="center">
                    <p>입력하신 내용이 회원정보와 일치하면 <br>
                        해당하는 아이디의 비밀번호를 확인하실 수 있습니다.</p>
                </div>
                <hr>
                <div class="justify-content-start pb-3">
                    <label for="nickname" class="form-label mb-2">회원ID</label>
                    <input type="text" class="form-control" name="nickname" id ="nickname" required>
                </div>
                <div class="justify-content-start pb-3">
                    <label for="email" class="form-label mb-2">EMAIL</label>
                    <input type="text" class="form-control" name="email"
                           placeholder="***@***.***" id ="email" required>
                </div>
                <div class="justify-content-center pt-3 pb-3" align="center">
                    <button type="submit" class="btn btn-primary">다음</button>
                    <button type="button" class="btn btn-primary" onclick="location.href='/login'">취소</button>
                </div>
            </div>
        </form>
    </div>
</main>

</body>
</html>
