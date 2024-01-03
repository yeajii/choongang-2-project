<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <%@ include file="/WEB-INF/components/Header.jsp" %>
    <title>Title</title>
    <link href="/css/homework.css" rel="stylesheet" type="text/css">
    <script type="module">
        import studentSelection from '/js/homework/distribute/studentSelection.js';
        import homeworkSelection from '/js/homework/distribute/homeworkSelection.js';
        import homeworkDistributor from '/js/homework/distribute/homeworkDistributor.js';
        import gameSelection from '/js/homework/distribute/gameSelection.js';
        import {showStoredNotification} from '/js/utils/notificationManager.js';

        $(document).ready(function () {

            const contentId = '${searchOptions.contentId != null ? searchOptions.contentId :""}';
            if (contentId !== '') {
                $('#contentId').val(contentId);
            }

            const userId = document.getElementById("userId").value;
            const stateManager = {
                selectedStudentsList: [],
                selectedHomeworkIdList: [],
            };
            studentSelection(stateManager, userId);
            homeworkSelection(stateManager);
            homeworkDistributor(stateManager);
            gameSelection();

            showStoredNotification();
        });

    </script>
</head>
<body>
<%@ include file="/WEB-INF/components/TopBar.jsp" %>
<main>
    <div class="d-flex">
        <div class="col-2">
            <%@ include file="/WEB-INF/components/EducateSidebar.jsp" %>
        </div>
        <div id="main-content" class="container p-5 col-10">

            <div class="container my-4 py-3">
                <div class="container">
                    <h1>숙제 전송</h1>
                </div>
                <div class="container">
                    <h2>숙제 선택</h2>
                    <hr/>
                </div>
                <div class="container">
                    <form action="/homework/distributeHomeworkForm" method="post">
                        <input type="hidden" id="userId" value="${userId}"/>
                        <div class="d-flex justify-content-between">
                            <div class="col-5 d-flex">
                                <div class="input-group">
                                    <label for="contentId" class="input-group-text fw-bold"
                                           style="font-size: 16px;">게임명</label>
                                    <select class="form-select text-center " id="contentId" name="contentId">
                                        <option value="0">게임을 선택하세요</option>
                                        <c:forEach var="game" items="${subscribedGameList}" varStatus="st">
                                            <option value="${game.id}">${game.title}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="col-5 d-flex">
                                <div class="input-group">
                                    <label for="homeworkTitles" class="input-group-text fw-bold"
                                           style="font-size: 16px;">숙제명</label>
                                    <select class="form-select text-center" id="homeworkTitles">
                                        <option value="" selected>숙제 전체
                                        <c:forEach var="homeworkTitle" items="${homeworkTitleList}" varStatus="st">
                                            <option value="${homeworkTitle}">${homeworkTitle}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>


                        </div>
                    </form>

                </div>
                <div class="container p-2 m-2" style="height: auto;">
                    <div class="table-responsive">
                        <table id="homework-table" class="table text-center">
                            <thead>
                            <tr>
                                <th scope="col" style="width: 5%">선택</th>
                                <th scope="col" style="width: 15%">숙제명</th>
                                <th scope="col" style="width: 20%">숙제내용</th>
                                <th scope="col" style="width: 10%">진도</th>
                                <th scope="col" style="width: 15%">제출기한</th>
                                <th scope="col" style="width: 15%">전송일자</th>
                                <th scope="col" style="width: 15%">생성일자</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="homework" items="${homeworkList}" varStatus="st">
                                <tr id="${homework.id}">
                                    <td style="width: 5%"><input type="checkbox" class="form-check-input"
                                                                 id="checkbox-${homework.id}-contentId-${homework.contentId}">
                                    </td>
                                    <td style="width: 15%">${homework.title}</td>
                                    <td style="width: 20%">${homework.content}</td>
                                    <td style="width: 10%">${homework.progress}</td>
                                    <td style="width: 15%"><fmt:formatDate value="${homework.deadline}"
                                                                           pattern="yyyy/MM/dd (HH시)"/></td>
                                    <td style="width: 15%">${homework.distributionDate != null? homework.distributionDate.toLocaleString()  :"미전송" }</td>
                                    <td style="width: 15%">${homework.createdAt.toLocaleString() }</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <div class="container my-4 py-3">
                <div class="container my-1">
                    <h2>학습자 선택</h2>
                    <hr/>
                </div>
                <!-- 학습 그룹 선택 -->
                <div class="container my-3 py-4 d-flex justify-content-between ">
                    <div class="col-9">
                        <div class="input-group">
                            <label class="input-group-text fw-bold " for="learningGroupSelect" style="font-size: 16px;">그룹명</label>
                            <select id="learningGroupSelect" class="form-control form-select">
                                <option id="group-${group.id}-${group.contentId}" value="groupAll" selected>그룹 전체
                                </option>
                                <c:forEach var="group" items="${learningGroupList}" varStatus="st">
                                    <option id="group-${group.id}-${group.contentId}"
                                            value="${group.id}">${group.name}</option>
                                </c:forEach>
                            </select>
                            <!-- 학생 목록과 선택된 학생 목록 -->
                            <button id="selectAll" class="btn">전체 선택</button>
                            <button id="deselectAll" class="btn ">전체 해제</button>
                        </div>
                    </div>
                    <!-- 숙제 배포 버튼 -->
                    <div class="container col-3 row ">
                        <button id="distributeHomework" type="button" class="btn btn-primary">전송하기</button>
                    </div>
                </div>
                <div class="d-flex justify-content-between ">
                    <div class="col-6 px-2">
                        <h3>그룹 내 학습자</h3>
                        <table id="studentList" class="table border  text-center">
                            <thead>
                            <tr>
                                <th>선택</th>
                                <th>아이디</th>
                                <th>학습자명</th>
                                <th>전화번호</th>
                                <th>레벨</th>
                            </tr>
                            </thead>
                            <tbody>
                            <!-- 학생 목록 -->
                            </tbody>
                        </table>
                    </div>
                    <div class="col-6 px-2">
                        <h3>선택된 학습자</h3>
                        <table id="selectedStudents" class="table border text-center">
                            <thead>
                            <tr>
                                <th>아이디</th>
                                <th>학습자명</th>
                                <th>전화번호</th>
                                <th>레벨</th>
                                <th>제외</th>
                            </tr>
                            </thead>
                            <tbody>
                            <!-- 선택된 학생 목록 -->
                            </tbody>
                        </table>
                    </div>
                </div>

            </div>
        </div>
    </div>
</main>
<%@ include file="/WEB-INF/components/Footer.jsp" %>
</body>
</html>
