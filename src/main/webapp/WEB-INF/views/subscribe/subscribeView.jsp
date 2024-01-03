<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/components/Header.jsp"%>
<title>Title</title>

<style>
    th, td {
        text-align: center;
        vertical-align: middle;
    }
    #gameImg{
        width: 110px;
    }
    h4{
        color: black;
        font-weight: 600;
        word-wrap: break-word;
        text-align: left;
    }
    p{
        text-align: right;
    }
</style>

<script>
    function subscribe() {
        var checkboxes = document.getElementsByName('gameIds');
        var selectedGameIds = Array.from(checkboxes)
            .filter(checkbox => checkbox.checked)
            .map(checkbox => checkbox.value);

        var form = document.getElementById('subscribeForm');

        if (selectedGameIds.length === 0) {
            alert("구독할 게임을 선택하세요");
        } else {
            // 선택된 게임이 있으면 폼을 서버로 제출
            form.submit();
        }
    }
</script>
</head>

<body>
<%@ include file="/WEB-INF/components/TopBar.jsp"%>
<main>
    <div class="d-flex">
        <div class="col-2">
            <%@ include file="/WEB-INF/components/SubscribeSidebar.jsp"%>
        </div>
        <div id="main-content" class="container p-5 col-10">
            <%-- 이곳에 작성을 해주세요 --%>
            <h4>컨텐츠 구독 신청</h4>
            <p>총 건수: ${subscribeTotalCount}</p>

            <form id="subscribeForm" action="/subscribe/subscribeClick" method="post">
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>구독</th> <th>No.</th> <th>콘텐츠 이미지</th> <th>가격 / 구독 기간(개월)</th> <th>상품 소개</th>
                        </tr>
                    </thead>

                    <c:forEach var="gameContent" items="${subscribeGameList}">
                        <tr id="gameContent${gameContent.rn}">
                            <td><input type="checkbox" name="gameIds" value="${gameContent.id}"></td>
                            <td>${gameContent.rn}</td>
                            <td><img id="gameImg" alt="UpLoad Image" src="${pageContext.request.contextPath}/upload/gameContents/${gameContent.imageName}"></td>
                            <td>
                                ${gameContent.discountPrice}원 / ${gameContent.subscribeDate}개월<br>
                            </td>
                            <td>${gameContent.content}</td>
                        </tr>
                    </c:forEach>
                </table>
                <c:if test="${result == 1}">
                    <button type="submit" onclick="event.preventDefault();subscribe()" class="btn btn-primary col-lg-2">구독하기</button>
                </c:if>
            </form>

            <!-- 페이징 작업 -->
            <nav aria-label="Page navigation example ">
                <ul class="pagination justify-content-center">
                    <c:if test="${page.startPage > page.pageBlock}">
                        <li class="page-item">
                            <a class="page-link ${page.currentPage == i ? "active":"" }" href="subscribeView?currentPage=${page.startPage - page.pageBlock}">이전</a>
                        </li>
                    </c:if>

                    <c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
                        <li class="page-item">
                            <a class="page-link ${page.currentPage == i ? "active":"" }" href="subscribeView?currentPage=${i}">${i}</a>
                        </li>
                    </c:forEach>

                    <c:if test="${page.endPage < page.totalPage}">
                        <li class="page-item">
                            <a class="page-link ${page.currentPage == i ? "active":"" }" href="subscribeView?currentPage=${page.startPage + page.pageBlock}">다음</a>
                        </li>
                    </c:if>
                </ul>
            </nav>

        </div>
    </div>
</main>
<%@ include file="/WEB-INF/components/Footer.jsp"%>
</body>
</html>
