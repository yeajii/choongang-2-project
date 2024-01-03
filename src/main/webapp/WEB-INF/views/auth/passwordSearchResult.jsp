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

    <main class=" m-5 p-5" >
        <div class="container col-4 justify-content-start border mt-3 p-5">
            <div class="mt-5 mb-5">
                <div class="justify-content-center pt-3 pb-3" align="center">
                    <h1>비밀번호 찾기</h1>
                </div>
                <div class="justify-content-center pb-1" align="center">
                    <p>입력하신 EMAIL로 임시비밀번호 발급하였습니다 .</p>
                </div>

                <div class="justify-content-center pt-3 pb-3" align="center">
                    <div>
                        <button type="button" class="btn btn-primary" onclick="location.href='login'">로그인</button>
                    </div>
                </div>
            </div>
        </div>
    </main>

</body>
</html>
