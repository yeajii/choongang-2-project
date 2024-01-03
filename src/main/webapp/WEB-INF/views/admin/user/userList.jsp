<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <%@ include file="/WEB-INF/components/Header.jsp"%>
    <title>Title</title>
</head>
<script type="text/javascript">
    window.onload = function() {
        var table = document.getElementById("userTable"); // 여기에 실제 테이블의 id를 적어주세요
        for (var i = 0; i < table.rows.length; i++) {
            table.rows[i].ondblclick = function() { // onclick -> ondblclick
                var id = this.id;
                window.open('userDetail/' + id, 'User Detail', 'width=920,height=250,left=' + (screen.width/2 - 300) + ',top=' + (screen.height/2 - 300)); // 팝업창 설정
            };
        }
    }
</script>
<style>
    #main-content {
        margin-left: 440px;
    }
    h1 {
        color: black;
        font-size: 32px;
        font-weight: 600;
        word-wrap: break-word;
        text-align: center;
    }
    th, td { text-align: center; }

</style>
<body>
<%@ include file="/WEB-INF/components/TopBar.jsp"%>
<main>
    <div class="d-flex">
        <div class="col-second">
            <%@ include file="/WEB-INF/components/AdminSidebar.jsp"%>
        </div>

    </div>
    <div class="container p-5 col-10" id="main-content" style="border: 0px solid red;">
        <div class="container border my-4 py-3">
            <div class="container my-3 py-3" style="text-align: center">
                <h1>회원 관리</h1>
            </div>
        <div align="center">
        <form action="userList" method="POST" class="container">

            <!-- 기간 -->
        <div class="col-12 my-4 d-flex ">
            <label for="searchType" class="col-sm-2 col-form-label fw-bold" style="font-size: 20px;">가입기간</label>
            <div class="col-10 d-flex">
                <div class="col-10 d-flex">
                    <input type="date" class="mx-2" id="startDatePicker" name="startDate" value="${startDate}">
                    <input type="date" class="mx-2" id="endDatePicker" name="endDate" value="${endDate}">
                </div>
            </div>
        </div>
        <div class="col-12 my-4 d-flex">
            <label for="searchType" class="col-sm-2 col-form-label fw-bold" style="font-size: 20px;">검색어</label>
            <div class="col-4 mx-2">
                <select id="searchType" name="searchType" class="form-select">
                    <option selected value="name">사용자 이름</option>
                    <option value="email">이메일</option>
                    <option value="nickname">닉네임</option>
                </select>
            </div>
            <div class="col-4 mx-2">
                <input type="text" name="keyword" class="form-control" value="${keyword}" placeholder="검색어를 입력하세요">
            </div>
        </div>


    <!-- 옵션 -->
        <div class="col-12 my-4 d-flex">
            <label for="searchType" class="col-sm-2 col-form-label fw-bold" style="font-size: 20px;">구분</label>
        <div class="col-2 d-flex ">

                <select class="form-select mx-2" id="userType" name="userType">
                    <option value="" selected>전체 회원</option>
                    <option value="1">관리자</option>
                    <option value="2">교육자</option>
                    <option value="3">학습자</option>
                    <option value="4">일반인</option>
                </select>
        </div>
            <label for="searchType" class="col-sm-2 col-form-label fw-bold" style="margin-left: 40px; font-size: 20px">자격</label>
        <div class="col-2 d-flex ">

                <select class="form-select mx-2" id="qualification" name="qualification">
                    <option value="" selected>전체 회원</option>
                    <option value="0">무료회원</option>
                    <option value="1">유료회원</option>
                </select>
        </div>
        </div>
    <div class="container col-10 d-flex justify-content-center">
        <button type="submit" class="btn btn-primary col-3 mx-3">검색</button>
        <button type="reset" class="btn btn-outline-secondary col-3 mx-3">초기화</button>
    </div>
    </form>
    </div>

    <div style="padding: 15px">
        <div class="table-responsive">
            <h6 style="margin-left: 15px">총 <strong>${total}</strong>명  조회되었습니다</h6>
            <table id="userTable" class="table table-md text-center p-3">
                <thead>
                <tr>
                    <th scope="col">구분</th>
                    <th scope="col">이름</th>
                    <th scope="col">아이디</th>
                    <th scope="col">연락처</th>
                    <th scope="col">이메일</th>
                    <th scope="col">자격</th>
                    <th scope="col">가입일자</th>
                    <th scope="col">관리</th>
                </tr>
                </thead>
                <tbody>
                <c:set var="num" value="${page.start}"/>
                <c:forEach var="user" items="${listUsers}" varStatus="st">
                    <tr id="${user.id}">

                        <td>
                            <c:choose>
                                <c:when test="${user.userType == 1}">
                                    관리자
                                </c:when>
                                <c:when test="${user.userType == 2}">
                                    교육자
                                </c:when>
                                <c:when test="${user.userType == 3}">
                                    학습자
                                </c:when>
                                <c:when test="${user.userType == 4}">
                                    일반인
                                </c:when>
                            </c:choose>
                        </td>
                        <td>${user.name}</td>
                        <td>${user.nickname}</td>
                        <td>${user.phone}</td>
                        <td>${user.email}</td>
                        <td>
                            <c:choose>
                                <c:when test="${user.qualification == 1}">
                                    유료회원
                                </c:when>
                                <c:otherwise>
                                    무료회원
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td><fmt:formatDate value="${user.createdAt}" type="date" pattern="YY/MM/dd"/></td>
                        <td><a class="detail-btn" href="/userUpdateForm/${user.id}?">수정</a></td>
                    </tr>
                    <c:set var="num" value="${num + 1}"/>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <nav aria-label="Page navigation example ">
        <ul class="pagination">
            <c:if test="${page.startPage > page.pageBlock}">
                <li class="page-item">
                    <a href="userList?currentPage=${page.startPage-page.pageBlock}&searchType=${searchType}&keyword=${keyword}"
                       class="pageblock page-link">Prev</a>
                </li>
            </c:if>
            <c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
                <li class="page-item">
                    <a href="userList?currentPage=${i}&searchType=${searchType}&keyword=${keyword}"
                       class="pageblock page-link ${page.currentPage == i ? 'active':'' }">${i}</a>
                </li>
            </c:forEach>
            <c:if test="${page.endPage < page.totalPage}">
                <li class="page-item">
                    <a href="userList?currentPage=${page.startPage+page.pageBlock}&searchType=${searchType}&keyword=${keyword}"
                       class="pageblock page-link">Next</a>
                </li>
            </c:if>
        </ul>
    </nav>
    </div>
</main>
<%@ include file="/WEB-INF/components/Footer.jsp"%>
</body>
</html>
