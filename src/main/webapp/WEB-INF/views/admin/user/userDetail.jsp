<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2023-12-21
  Time: 오전 9:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/WEB-INF/components/Header.jsp"%>
<html>
<head>
    <title>Title</title>
</head>
<body style="margin-top: 0;">
<table class="table table-striped-columns" style="margin-top: 10px" border="2px">
    <tr>
        <td>성명</td>
        <td>${userDetail.name}</td>
        <td>생년월일</td>
        <td>${fn:substring(userDetail.birthdate, 2, 4)}/${fn:substring(userDetail.birthdate, 4, 6)}/${fn:substring(userDetail.birthdate, 6, 8)}</td>
    </tr>
    <tr>
        <td>주소</td>
        <td>${userDetail.address}</td>
        <td>자격</td>
        <td>
            <c:choose>
                <c:when test="${userDetail.qualification == 1}">
                    유료회원
                </c:when>
                <c:otherwise>
                    무료회원
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
    <tr>
        <td>연락처</td>
        <td>${userDetail.phone}</td>
        <td>구매건수</td>
        <td>${buyCount}</td>
    </tr>
    <tr>
        <td>이메일</td>
        <td>${userDetail.email}</td>
        <td>메세지 수신</td>
        <td>
            <c:choose>
                <c:when test="${userDetail.consent1 == 1}">
                    EMAIL 수신동의
                </c:when>
                <c:otherwise>
                    EMAIL 수신거부
                </c:otherwise>
            </c:choose>
            /
            <c:choose>
                <c:when test="${userDetail.consent2 == 1}">
                    SMS 수신동의
                </c:when>
                <c:otherwise>
                    SMS 수신거부
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
</table>
<div class="d-flex justify-content-center align-items-center">
    <div class="col-6 mb-3">
        <button type="button" class="form-control btn btn-primary" onclick="window.close();">확인</button>
    </div>
</div>



</body>
</html>
