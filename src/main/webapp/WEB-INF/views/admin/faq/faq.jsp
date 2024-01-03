
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>
        function changePageSize() {
            var pageSize = document.getElementById("pageSize").value;
            document.cookie = "pageSize=" + pageSize;

            // Get the search type and keyword values from cookies
            var searchType = getCookie("searchType");
            var keyword = getCookie("keyword");

            console.log("pageSize:", pageSize);
            console.log("searchType:", searchType);
            console.log("keyword:", keyword);

            // Get the current URL
            var currentUrl = new URL(window.location.href);

            // Set the new parameters
            currentUrl.searchParams.set("pageSize", pageSize);
            if (searchType) currentUrl.searchParams.set("searchType", searchType);
            if (keyword) currentUrl.searchParams.set("keyword", keyword);

            // Redirect to the new URL
            location.href = currentUrl.href;
        }

        // Function to get cookie by name
        function getCookie(name) {
            var nameEQ = name + "=";
            var ca = document.cookie.split(';');
            for(var i=0;i < ca.length;i++) {
                var c = ca[i];
                while (c.charAt(0)==' ') c = c.substring(1,c.length);
                if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
            }
            return null;
        }
    </script>
    <style>

        .custom-select-box {
            width: 100px;
            height: 30px;
            margin-top: 30px;
        }

        .my-custom-class {
            align-items: center;
            height: 38px;
        }

        #searchType {
            width: 300px;
        }

        input[name="keyword"] {
            width: 600px;
            margin-left: 0px;
        }

        .col-15.my-5.d-flex.align-items-center {
            width: 1450px;
            margin-right: 70px;
            padding-right: 0;
        }

        .p-4 {
            width: 1600px;
            margin-left: 0;
            margin-right: 0;
        }

        .table-responsive {
            width: 1350px;
            margin-left: -50px;
        }

        .search-and-register {
            display: flex;
            justify-content: flex-start;
            margin-top: 20px;
        }
    </style>

    <%@ include file="/WEB-INF/components/Header.jsp"%>

    <title>Title</title>
</head>
<body>
<%@ include file="/WEB-INF/components/TopBar.jsp"%>
<main>
    <%@ include file="/WEB-INF/components/AdminSidebar.jsp"%>
    <div class="container col-9 justify-content-center align-items-center mb-2 p-3 pt-0" style="text-align: center;">
        <h1 style="margin-top: 50px; font-weight: bold; ">FAQ 리스트</h1>
        <div class="container justify-content-end p-0" style="text-align: left; margin-left: 70px;">
            <div class="search-and-register">
                <form action="FAQSearch" method="get" class="container justify-content-center" style="display: inline-block; text-align: center; margin-left: 0;">
                    <div class="col-15 my-5 d-flex align-items-center" style="display: inline-block; text-align: left; margin-left: -20px;">
                        <label for="searchType" class="col-form-label col-1  mx-2">검색어</label>
                        <div class="col-4">
                            <select id="searchType" name="searchType" class="form-select">
                                <option value="title" ${title ? 'selected' : ''}>제목</option>
                                <option value="content" ${content ? 'selected' : ''}>내용</option>
                                <option value="name" ${name ? 'selected' : ''}>작성자</option>
                            </select>
                        </div>
                        <div class="d-flex col-6 mx-2 my-custom-class">
                            <label>
                                <input type="text" name="keyword" class="form-control" placeholder="검색어를 입력하세요">
                            </label>
                            <button type="submit" class="btn btn-primary mx-3">검색</button>
                        </div>
                        <div style="display: flex; justify-content: center; align-items: center; margin-top: 22px; margin-left: -50px;">
                            <button style="float: right; margin-right: 12px;" id="regist-btn" type="button" class="btn btn-primary mb-4" onclick="location.href='FAQInsertForm'">등록</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <div class="container table-container p-4" style="text-align: center;">
            <div class="table-responsive" style="text-align: left; margin-left: 50px;">
                <select id="pageSize" onchange="changePageSize()" class="custom-select-box">
                    <c:forEach var="size" items="${['10', '20', '30']}">
                        <c:choose>
                            <c:when test="${param.pageSize eq size or (empty param.pageSize and size eq '10')}">
                                <option value="${size}" selected>${size}개씩 보기</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${size}">${size}개씩 보기</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
                <h5 style="text-align: right;">총 게시글 수 : ${totalFAQboard}</h5>
                <table id="userTable" class="table table-md text-center p-3">
                    <thead>
                    <tr>
                        <th scope="col">순번</th>
                        <th scope="col">분류</th>
                        <th scope="col">제목</th>
                        <th scope="col">작성자</th>
                        <th scope="col">등록일시</th>
                        <th scope="col">조회수</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:set var="num" value="${page.start}"/>
                    <c:forEach var="FAQ" items="${listFAQBoard}" varStatus="st">
                        <tr id="FAQ${st.index}">
                            <td><input type="hidden" value="${FAQ.id}" id="id${st.index}">${num}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${FAQ.boardType == 1}">공지</c:when>
                                    <c:when test="${FAQ.boardType == 2}">FAQ</c:when>
                                    <c:when test="${FAQ.boardType == 3}">QnA</c:when>
                                </c:choose>
                            </td>
                            <td><a href="FAQDetail?id=${FAQ.id}">${FAQ.title}</a></td>
                            <td>${FAQ.name}</td>
                            <td><fmt:formatDate value="${FAQ.createdAt}" type="date" pattern="YY/MM/dd"/></td>
                            <td>${FAQ.readCount}</td>

                        </tr>
                        <c:set var="num" value="${num + 1}"/>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <nav aria-label="Page navigation example" style="text-align: center; margin-left: 30px;">
            <ul class="pagination" style="margin-left: 120px;">
                <c:choose>
                    <c:when test="${path ==0}">
                        <c:if test="${page.startPage > page.pageBlock}">
                            <li class="page-item">
                                <a href="FAQBoardList?pageSize=${pageSize}&currentPage=${page.startPage-page.pageBlock}"
                                   class="pageblock page-link">[이전]</a></li>
                        </c:if>
                        <c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
                            <li class="page-item">
                                <a href="FAQBoardList?pageSize=${pageSize}&currentPage=${i}" class="pageblock page-link ${page.currentPage == i ? 'active':'' }">${i}</a>
                            </li>
                        </c:forEach>
                        <c:if test="${page.endPage < page.totalPage}">
                            <li class="page-item">
                                <a href="FAQBoardList?pageSize=${pageSize}&currentPage=${page.startPage+page.pageBlock}"
                                   class="pageblock page-link">[다음]</a></li>
                        </c:if>
                    </c:when>
                    <c:otherwise>
                        <c:if test="${page.startPage > page.pageBlock}">
                            <li class="page-item">
                                <a href="FAQSearch?pageSize=${pageSize}&currentPage=${page.startPage-page.pageBlock}"
                                   class="pageblock page-link">[이전]</a></li>
                        </c:if>
                        <c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
                            <li class="page-item">
                                <a href="FAQSearch?pageSize=${pageSize}&currentPage=${i}&searchType=${param.searchType}&keyword=${param.keyword}" class="pageblock page-link ${page.currentPage == i ? 'active':'' }">${i}</a>
                            </li>
                        </c:forEach>
                        <c:if test="${page.endPage < page.totalPage}">
                            <li class="page-item">
                                <a href="FAQSearch?pageSize=${pageSize}&currentPage=${page.startPage+page.pageBlock}"
                                   class="pageblock page-link">[다음]</a></li>
                        </c:if>
                    </c:otherwise>
                </c:choose>
            </ul>
        </nav>
    </div>
</main>
<%@ include file="/WEB-INF/components/Footer.jsp"%>
</body>
</html>
