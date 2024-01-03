<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <%@ include file="/WEB-INF/components/Header.jsp"%>
    <title>학습그룹 상세조회</title>
    <link href="/css/homework.css" rel="stylesheet" type="text/css">
</head>
<style>
    .large-font {
        font-size: 1.1em; /* 기본 글자 크기의 1.1배 */
    }
</style>
<body>
    <%@ include file="/WEB-INF/components/TopBar.jsp"%>
    <main>
        <div class="d-flex">
            <div class="col-2">
                <%@ include file="/WEB-INF/components/EducateSidebar.jsp"%>
            </div>

            <div class="container p-5 col-10">
                <div class="container border my-4 py-3">
                    <div class="container my-3 py-3">
                        <h1>학습그룹 상세조희</h1>
                        <hr/>
                    </div>

                    <%--디테일 상단부--%>
                    <div class="container p-5">
                        <h2 class="pb-3">그룹 상세 정보</h2>
                        <div class="table">
                            <table class="table text-center">
                                <tr>
                                    <th class="col-2 text-end large-font">학습그룹명 : </th><td class="col-2 text-start large-font">${detailLearningGroup[0].name}</td>
                                    <th class="col-2 text-end large-font">게임콘텐츠명 : </th><td class="d-flex text-start large-font">${detailLearningGroup[0].title}</td>
                                </tr>
                                <tr>
                                    <th class="col-2 text-end large-font">그룹제한인원 : </th><td class="col-2 text-start large-font">${detailLearningGroup[0].groupSize}명</td>
                                    <th class="col-2 text-end large-font">그룹가입인원 : </th><td class="d-flex text-start large-font">${detailLearningGroup[0].studentCount}명</td>
                                </tr>
                                <tr>
                                    <th class="col-2 text-end large-font">그룹가입내역 : </th><td class="col-2 text-start large-font"></td>
                                    <th class="col-2 text-end large-font"></th><td class="col-2 text-start large-font"></td>
                                </tr>
                            </table>
                        </div>
                    </div>

                    <%--디테일 리스트 메인--%>
                    <div class="container border p-5">
                        <h2 class="pb-3">가입 학생 리스트</h2>
                        <div class="table">
                            <table class="table text-center">
                                <thead>
                                    <tr>
                                        <th>No</th><th>학생이름</th><th>연락처</th><th>이메일</th><th>주소</th><th>가입일자</th>
                                    </tr>
                                </thead>
                                <c:forEach var="detailGroup" items="${detailLearningGroup}" varStatus="status">
                                    <tr>
                                        <td>${status.index + 1}</td>
                                        <td>${detailGroup.studentName}</td>
                                        <td>${detailGroup.phone}</td>
                                        <td>${detailGroup.email}</td>
                                        <td>${detailGroup.address}</td>
                                        <td><fmt:formatDate value="${detailGroup.approvalDate}" pattern="yyyy-MM-dd"/></td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
    <%@ include file="/WEB-INF/components/Footer.jsp"%>
</body>
</html>
