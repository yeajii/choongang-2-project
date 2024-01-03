<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <%@ include file="/WEB-INF/components/Header.jsp"%>
    <title>학습그룹 조회</title>
    <link href="/css/homework.css" rel="stylesheet" type="text/css">
</head>
<style>
    .centered-content {
        vertical-align: middle;
        text-align: center;
    }
</style>
<script>
    // 그룹 리스트의 선택한 항목의 상세정보로 넘어감.
    function goToDetailLearningGroup() {
        var radios = document.getElementsByName('learningGroup');
        var checkedValue;
        for (var i = 0; i < radios.length; i++) {
            if (radios[i].checked) {
                checkedValue = radios[i].value;
                break;
            }
        }
        if (checkedValue) {
            location.href = '/group/detailLearningGroup?id=' + checkedValue;
        } else {
            alert('상세를 보고싶은 학습그룹을 선택해주세요.')
        }
    }

    // 그룹 리스트의 선택한 항목의 수정으로 넘어감.
    function goToUpdateFormLearningGroup() {
        var radios = document.getElementsByName('learningGroup');
        var checkedValue;
        for (var i = 0; i <radios.length; i++) {
            if (radios[i].checked) {
                checkedValue = radios[i].value;
                break;
            }
        }
        if (checkedValue) {
            location.href = '/group/updateFormLearningGroup?id=' + checkedValue;
        } else  {
            alert('수정하고싶은 학습그룹을 선택해주세요.')
        }
    }

    // 그룹 리스트의 선택한 항목의 삭제를 수행함.
    function goToDeleteLearningGroup() {
        var radios = document.getElementsByName('learningGroup');
        var checkedValue;
        for (var i = 0; i <radios.length; i++) {
            if (radios[i].checked) {
                checkedValue = radios[i].value;
                break;
            }
        }
        if (checkedValue) {
            if (confirm('정말로 학습그룹을 삭제하시겠습니까?')) {
                location.href = '/group/deleteLearningGroup?id=' + checkedValue;
            }
        } else {
            alert('삭제하고싶은 학습그룹을 선택해 주세요')
        }
    }

    // 조회하기 검색
    function submitForm() {
        var keyword = document.getElementById('keyword').value;
        window.location.href = '/group/listLearningGroup1?keyword=' + keyword;
    }

</script>
<body>
    <%@ include file="/WEB-INF/components/TopBar.jsp"%>
    <main>
        <div class="d-flex">
            <div class="col-2">
                <%@ include file="/WEB-INF/components/EducateSidebar.jsp"%>
            </div>

            <%--본문 리스트--%>
            <div class="container p-5 col-10">
                <div class="container my-4 py-3">
                    <div class="container">
                        <h1>그룹 리스트 조회</h1>
                    </div>
                    <div class="container">
                        <h2>학습그룹 내역</h2>
                        <hr/>
                    </div>

                    <div class="container p-2 m-2" style="height: auto;">
                        <p>학습그룹 수 :${totalLearningGroupCnt}</p>
                        <div class="d-flex justify-content-between align-items-center mb-3">
                            <div class="d-flex align-items-center mr-3" style="flex-grow: 1;">
                                <input class="form-control keyword-input mr-3" type="text" name="keyword" placeholder="검색어를 입력하세요." id="keyword" value="${keyword}" style="flex-grow: 1;" onkeydown="if(event.keyCode==13) submitForm();">
                                <button class="btn btn-primary mr-3" style="min-width: 90px;" onclick="submitForm();">조회하기</button>
                            </div>
                            <div class="d-flex align-items-center mr-3">
                                <button onclick="goToUpdateFormLearningGroup()" class="btn btn-primary mr-3" style="min-width: 90px;">변경하기</button>
                                <button onclick="goToDeleteLearningGroup()" class="btn btn-primary" style="min-width: 90px;">삭제하기</button>
                            </div>
                        </div>
                    </div>

                    <div class="container border p-2 m-2" style="height: auto;">
                        <c:if test="${learningGroupList.size() == 0}">해당하는 학습그룹 정보가 없습니다.</c:if>
                        <div class="table">
                            <table class="table text-center">
                                <thead>
                                    <tr>
                                        <th></th><th>학습그룹명</th><th>게임콘텐츠명</th><th>그룹 T/O</th><th>구독기간</th><th>등록 학생수</th>
                                    </tr>
                                </thead>
                                <c:forEach var="groupList" items="${learningGroupList}">
                                    <tr>
                                        <td class="centered-content">
                                            <input type="radio" name="learningGroup" id="learningGroup" value="${groupList.id}">
                                        </td>
                                        <td>${groupList.name}</td>
                                        <td>${groupList.title}</td>
                                        <td>${groupList.groupSize}명</td>
                                        <td>
                                            <fmt:parseDate var="parsedStartDate" value="${groupList.startDate}" pattern="yyyy-MM-dd" />
                                            <fmt:formatDate value="${parsedStartDate}" type="date" />
                                            ~
                                            <fmt:parseDate var="parsedEndDate" value="${groupList.endDate}" pattern="yyyy-MM-dd" />
                                            <fmt:formatDate value="${parsedEndDate}" type="date" />
                                        </td>
                                        <td>${groupList.studentCount}명</td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                        <div class="text-center">
                            <button class="btn btn-primary col-lg-2" onclick="goToDetailLearningGroup()">학습그룹 상세조회</button>
                        </div>
                    </div>
                </div>


                <!-- 페이징 처리 -->
                <nav aria-label="Page navigation example ">
                    <ul class="pagination">
                        <c:if test="${page.startPage > page.pageBlock}">
                            <c:choose>
                                <c:when test="${path == 0}">
                                    <li class="page-item">
                                        <a href="/group/listLearningGroup?currentPage=${page.startPage-page.pageBlock}" class="pageblock page-link">Prev</a>
                                    </li>
                                </c:when>
                                <c:when test="${path == 1}">
                                    <li class="page-item">
                                        <a href="/group/listLearningGroup1?currentPage=${page.startPage-page.pageBlock}" class="pageblock page-link">Prev</a>
                                    </li>
                                </c:when>
                            </c:choose>
                        </c:if>

                        <c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
                            <c:choose>
                                <c:when test="${path == 0}">
                                    <li class="page-item">
                                        <a href="/group/listLearningGroup?currentPage=${i}" class="pageblock page-link ${page.currentPage == i ? 'active':'' }">${i}</a>
                                    </li>
                                </c:when>
                                <c:when test="${path == 1}">
                                    <li class="page-item">
                                        <a href="/group/listLearningGroup1?currentPage=${i}&keyword=${keyword}" class="pageblock page-link ${page.currentPage == i ? 'active':'' }">${i}</a>
                                    </li>
                                </c:when>
                            </c:choose>
                        </c:forEach>

                        <c:if test="${page.endPage < page.totalPage}">
                            <c:choose>
                                <c:when test="${path == 0}">
                                    <li class="page-item">
                                        <a href="/group/listLearningGroup?currentPage=${page.startPage+page.pageBlock}" class="pageblock page-link">Next</a>
                                    </li>
                                </c:when>
                                <c:when test="${path == 1}">
                                    <li class="page-item">
                                        <a href="/group/listLearningGroup1?currentPage=${page.startPage+page.pageBlock}" class="pageblock page-link">Next</a>
                                    </li>
                                </c:when>
                            </c:choose>
                        </c:if>
                    </ul>
                </nav>
            </div>
        </div>

    </main>
    <%@ include file="/WEB-INF/components/Footer.jsp"%>

</body>
</html>
