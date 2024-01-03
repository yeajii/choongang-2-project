<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <%@ include file="/WEB-INF/components/Header.jsp" %>
    <title>Title</title>
    <link href="/css/homework.css" rel="stylesheet" type="text/css">

    <script type="module">
        import homeworkTitleOption from '/js/homework/evaluate/homeworkTitleOption.js';
        import {getDistributedHomeworks} from "/js/homework/evaluate/getDistributedHomeworks.js";
        import {saveEvaluate} from "/js/homework/evaluate/saveEvaluate.js";

        $(document).ready(function () {
            const title = '${searchOptions.title != null ? searchOptions.title : ""}';
            homeworkTitleOption(title);

            $('#homework-table tbody tr').on('click', function () {
                const homeworkId = $(this).attr('id');
                console.log(homeworkId)
                getDistributedHomeworks(homeworkId);
                $('#homework-table tbody tr').removeClass('table-primary');
                $(this).addClass('table-primary');
            });

            $('#saveEvaluate').click(saveEvaluate);
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
                    <h1>숙제 평가</h1>
                </div>
                <div class="container">
                    <h2>숙제 선택</h2>
                    <hr/>
                </div>
                <div class="container">
                    <form action="/homework/evaluateHomeworkForm" method="post">
                        <div class="col-5">
                            <div class="input-group ">
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
                    </form>
                </div>
                <div class="container border p-2 m-2" style="height: auto;">
                    <div class="table-responsive">
                        <table id="homework-table" class="table text-center">
                            <thead>
                            <tr>
                                <th scope="col" style="width: 5%">번호</th>
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
                                <c:if test="${homework.distributionDate != null}">
                                    <tr id="${homework.id}">
                                        <td>${st.index}</td>
                                        <td>${homework.title}</td>
                                        <td>${homework.content}</td>
                                        <td>${homework.progress}</td>
                                        <td><fmt:formatDate value="${homework.deadline}"
                                                            pattern="yyyy/MM/dd (HH시)"/></td>
                                        <td>${homework.distributionDate.toLocaleString()}</td>
                                        <td>${homework.createdAt.toLocaleString()}</td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>

                <div class="mt-5">
                    <div class="container d-flex justify-content-between">
                        <h2>제출 내역</h2>
                        <button id="saveEvaluate" type="button" class="btn btn-primary col-lg-2">저장하기</button>
                    </div>
                    <hr/>
                    <div class="container border p-2 m-2" style="height: auto;">
                        <div class="table-responsive" style="height: auto; overflow: auto;">
                            <table id="distributedHomework-table" class="table text-center">
                                <thead>
                                <tr>
                                    <th scope="col" style="width: 5%">번호</th>
                                    <th scope="col" style="width: 15%">학습자명</th>
                                    <th scope="col" style="width: 20%">숙제제출일자</th>
                                    <th scope="col" style="width: 10%">제출내용</th>
                                    <th scope="col" style="width: 15%">학습진도</th>
                                    <th scope="col" style="width: 15%">질문</th>
                                    <th scope="col" style="width: 15%">평가</th>
                                </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
<%@ include file="/WEB-INF/components/Footer.jsp" %>
</body>
</html>
