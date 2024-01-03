<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <%@ include file="/WEB-INF/components/Header.jsp" %>
    <title>Title</title>
    <link href="/css/homework.css" rel="stylesheet" type="text/css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/corejs-typeahead/1.2.1/typeahead.bundle.min.js"></script>
    <script type="module">
        import {submitForm} from "/js/homework/insert/formUtils.js";
        import {initializePage} from "/js/homework/insert/initialization.js";
        import {showStoredNotification }from '/js/utils/notificationManager.js';

        document.addEventListener("DOMContentLoaded", initializePage);
        document.getElementById('submitBtn').addEventListener('click', submitForm);
        showStoredNotification();
        function createQueryURL(page) {
            const params = {
                currentPage: page
            };
            return '/homework/insertHomeworkForm?' + Object.entries(params)
                .filter(([key, value]) => value !== undefined && value !== null && value !== '')
                .map(([key, value]) => key + '=' + value).join('&');
        }
    </script>
    <style>


    </style>
</head>
<body>
<%@ include file="/WEB-INF/components/TopBar.jsp" %>
<main>
    <div class="d-flex">
        <div class="col-2">
            <%@ include file="/WEB-INF/components/EducateSidebar.jsp" %>
        </div>
        <div id="main-content" class="container p-5 col-10">
            <div class="container border my-1 py-1">
                <div class="container my-1 py-1">
                    <h1>숙제정보입력</h1>
                </div>
                <div>
                    <form action="/homework/insertHomeworkForm" method="post">
                        <input type="hidden" name="userId" id="userId" value="${userId}">
                        <div class="my-1 row align-items-baseline ">
                            <label class="col-sm-2 col-form-label fw-bold text-end"
                                   style="font-size: 20px;">게임컨텐츠</label>
                            <div class="col-sm-8 d-flex align-items-center">
                                <select class="form-select text-center " id="contentId" name="contentId">
                                    <c:forEach var="game" items="${subscribedGameList}" varStatus="st">
                                        <option value="${game.id}">${game.title}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="my-1 row align-items-baseline ">
                            <label for="title" class="col-sm-2 col-form-label fw-bold text-end"
                                   style="font-size: 20px;">숙제명</label>
                            <div class="col-sm-8" >
                                <input type="text" class="typeahead form-control md-0 pd-0" id="title" name="title">
                            </div>
                        </div>
                        <div class="my-1 row align-items-baseline ">
                            <label for="progress" class="col-sm-2 col-form-label fw-bold text-end"
                                   style="font-size: 20px;">숙제진도</label>
                            <div class="col-sm-8">
                                <input type="number" class="form-control" id="progress" name="progress">
                                <p style="color: #6D6A6A" class="helptext text-start">숙제 진도는 숫자만 입력바랍니다.</p>
                            </div>
                        </div>


                        <div class="my-1 row align-items-center ">
                            <label class="col-sm-2 col-form-label fw-bold text-end"
                                   style="font-size: 20px;">제출기한</label>
                            <div class="col-sm-8 d-flex align-items-center">
                                <div class="col-3">
                                    <select class="form-select text-center" id="year">
                                        <option value=""></option>
                                    </select>
                                </div>
                                <span class="date-text">년</span>
                                <div class="col-2">
                                    <select class="form-select text-center" id="month">
                                        <option value=""></option>
                                    </select>
                                </div>
                                <span class="date-text">월</span>
                                <div class="col-2 ">
                                    <select class="form-select text-center" id="day">
                                        <option value=""></option>
                                    </select>
                                </div>
                                <span class="date-text">일</span>
                                <div class="col-2 ">
                                    <select class="form-select text-center" id="hour">
                                        <option value=""></option>
                                    </select>
                                </div>
                                <span class="date-text">시</span>
                            </div>
                        </div>

                        <div class="my-1 row align-items-baseline ">
                            <label for="content" class="col-sm-2 col-form-label fw-bold text-end"
                                   style="font-size: 20px;">숙제내용</label>
                            <div class="col-sm-8">
                            <textarea class="form-control" aria-label="With textarea" id="content" rows="3"
                                      name="content"></textarea>
                                <p id="textarea-validate" class="helptext text-end"></p>
                            </div>
                        </div>


                        <div class="container row justify-content-center my-1">

                            <button type="submit" id="submitBtn" class="btn btn-primary col-4 px-3 mx-2"
                                    style="background: #52525C; color: #F8FCF4; border: none"
                                    onclick="event.preventDefault();">저장하기
                            </button>
                            <button type="reset" class="btn btn-primary col-4 px-3 mx-2">취소</button>

                        </div>
                    </form>

                </div>

            </div>
            <div class="container mt-5 ">
                <div class="">
                    <table id="homework-table" class="table text-center">
                        <thead>
                            <tr>
                                <th scope="col">No</th>
                                <th scope="col">숙제명</th>
                                <th scope="col">숙제내용</th>
                                <th scope="col">진도</th>
                                <th scope="col">제출기한</th>
                                <th scope="col">전송일자</th>
                                <th scope="col">생성일자</th>
<%--                                <th scope="col"></th>--%>
                            </tr>
                        </thead>
                        <tbody>
                            <c:set var="num" value="${page.start}"/>
                            <c:forEach var="homework" items="${homeworkList}" varStatus="st">
                                <tr id="homework-${st.index}">
                                    <td>${homework.id}</td>
                                    <td>${homework.title}</td>
                                    <td>${homework.content}</td>
                                    <td>${homework.progress}</td>
                                    <td><fmt:formatDate value="${homework.deadline}" pattern="yyyy/MM/dd (HH시)"/></td>
                                    <td>${homework.distributionDate==null?"미전송":homework.distributionDate.toLocaleString()}</td>
                                    <td>${homework.createdAt.toLocaleString()}</td>
<%--                                    <td><a class="detail-btn">관리</a></td>--%>
                                </tr>
                            <c:set var="num" value="${num + 1}"/>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div>
                    <nav aria-label="Page navigation example ">
                        <ul class="pagination">
                            <c:if test="${page.startPage > page.pageBlock}">
                                <li class="page-item">
                                    <a href="#" data-page="${page.startPage-page.pageBlock}" class="page-link ${page.currentPage == i ? "active":"" }">이전</a>
                                </li>
                            </c:if>
                            <c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
                                <li class="page-item">
                                    <a href="#" data-page="${i}" class="page-link ${page.currentPage == i ? "active":"" }">${i}</a>
                                </li>
                            </c:forEach>
                            <c:if test="${page.endPage < page.totalPage}">
                                <li class="page-item">
                                    <a href="#" data-page="${page.startPage+page.pageBlock}" class="page-link ${page.currentPage == i ? "active":"" }">다음</a>
                                </li>
                            </c:if>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
    </div>


    <!-- Modal -->
    <div class="modal fade" id="modal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">숙제 상세정보</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">

                </div>
                <div class="modal-footer container d-flex  justify-content-around ">
                    <button type="button" class="btn btn-primary col-sm-5">수정</button>
                    <button type="button" class="btn col-sm-5">삭제</button>
                </div>
            </div>
        </div>
    </div>

</main>
<%@ include file="/WEB-INF/components/Footer.jsp" %>
</body>
</html>
