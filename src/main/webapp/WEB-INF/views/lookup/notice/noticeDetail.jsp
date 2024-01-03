<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

    <%@ include file="/WEB-INF/components/Header.jsp"%>
        <style>
            .main-container {
                padding: 20px;
                background-color: #fff;
                border: 1px solid #dee2e6;
                border-radius: 0.25rem;
                margin-top: 50px;
            }
            .post-controls {
                display: flex;
                justify-content: flex-start; /* 왼쪽 정렬 */
                font-size: 0.8rem; /* 폰트 크기를 작게 조정 */
            }

            .post-controls button, .post-controls a {
                margin-right: 10px; /* 버튼 사이의 간격 조정 */
            }
            .comment-md-input {
                width: 700px;
                height: 100px;
                margin-top: 10px;
            }
            .comment-md-btn {
                margin-top: 10px;
            }

            .comments-body-custom {
                width: 1200px; /* 원하는 높이로 설정 */
                height: 100px;
                margin-bottom: 0;
            }

            .custom-container {
                width: 800px;
                height: 100px;
            }

            .custom-row {
                width: 1400px; /* 원하는 크기로 설정 */
            }

    </style>
    <title>Title</title>
</head>
<body>

<%@ include file="/WEB-INF/components/TopBar.jsp"%>
<main>
    <%@ include file="/WEB-INF/components/LookupSidebar.jsp"%>

<form action="noticeUpdate" method="post">

    <div class="container main-container">
        <div class="post-content">
            <h1>${board.title}</h1>
            <p>작성자 : ${board.name}</p>
            <p>작성일: <fmt:formatDate value="${board.createdAt}" type="date" pattern="YY/MM/dd"/></p>
            <p>조회수: ${board.readCount}</p>
            <p>${board.content}</p>
            <c:if test="${not empty fileAddress}">
                <a href="${fileAddress}" download>Download file</a>
            </c:if>
        </div>

        <div class="post-controls">
            <button type="button" class="btn btn-primary2" onclick="location.href='/lookup/board/noticeBoardList'">목록으로</button>
        </div>
    </div>
</form>


<!-- 댓글 출력 -->
<c:choose>
    <c:when test="${board.boardType eq 1 }">
        <div class="container p-3 comment-custom">
            <div class="row row-cols-1 align-items-start">
                <div class="col">
                    <!-- input 영역 -->
                    <div class="container p-0">
                        <form action="commentInsert" method="post" enctype="multipart/form-data">
                            <input type="hidden" name="id" value="${board.id }">
                            <input type="hidden" name="userId" value=${board.userId}>
                            <input type="hidden" name="commentGroupId" value="${board.id}">
                            <input type="hidden" name="commentStep" value="${board.commentStep }">
                            <input type="hidden" name="commentIndent" value="${board.commentIndent }">
                            <input type="hidden" name="boardType" value="${board.boardType}">

                                <c:choose>
                                    <c:when test="${userId != 0 }">
                            <div class="form-group col comment-input">
                                <input type="text" class="form-control" name="content" placeholder="댓글을 입력하세요.">
                            </div>

                            <div class="form-group col comment-btn">

                                        <button type="submit" class="btn btn_detail_custom">등록</button>
                                    </c:when>
                                </c:choose>
                            </div>
                        </form>
                        <!-- input 영역 END -->
                    </div>

                    <!-- 대댓글 출력  -->
                    <div class="container p-3 comments-custom">
                        <c:forEach var="comments" items="${comments }">
                            <div class="row row-cols-2 align-items-start gap-1">
                                <div class="col comments-nickname">
                                    <p>${comments.name }</p>
                                </div>

                                <div class="col comments-content">
                                    <p class="d-inline-flex gap-1">
                                        <c:forEach begin="2" end="${comments.commentIndent }">└▶</c:forEach>
                                        <button class="btn" type="button" data-bs-toggle="collapse"
                                                data-bs-target="#collapseExample${comments.id}"
                                                aria-expanded="false" aria-controls="collapseExample"
                                                style="width: 900px; text-align: left;">
                                                ${comments.content }</button>
                                                 <c:if test="${userId == comments.userId}">
                                                <!-- 작성자와 로그인한 사용자의 ID가 같을 때에만 X 버튼 표시 -->
                                                <button type="button" class="btn btn-danger" onclick="deleteComment(${comments.id})">X</button>
                                                 </c:if>
                                    </p>
                                    <span class="blink" style="font-size: 16px; font-weight: bold; color: #FF4379; margin-left: -640px; margin-top: 6px;">new</span>
                                </div>
                                <c:choose>
                                    <c:when test="${userId != 0 }">
                                        <div class="collapse comments-collapse-custom" id="collapseExample${comments.id}">
                                            <div class="card card-body comments-body-custom">
                                                <!-- input 영역 -->
                                                <div class="container p-0 row row-cols-2 gap-1 custom-container">
                                                    <form class="col" action="commentInsert" method="post" enctype="multipart/form-data">
                                                        <input type="hidden" name="id" value="${board.id }">
                                                        <input type="hidden" name="userId" value=${board.userId}>
                                                        <input type="hidden" name="commentGroupId" value="${board.id }">
                                                        <input type="hidden" name="commentStep" value="${comments.commentStep }">
                                                        <input type="hidden" name="commentIndent" value="${comments.commentIndent }">
                                                        <input type="hidden" name="boardType" value="${board.boardType}">


                                                        <div class="row row-cols-3 p-0 gap-1 custom-row">
                                                            <div class="form-group col comment-md-input">
                                                                <input type="text" class="form-control" name="content"
                                                                       placeholder="댓글을 입력하세요.">
                                                            </div>
                                                            <div class="form-group col comment-md-btn">
                                                                <button type="submit" class="btn">등록</button>
                                                            </div>
                                                        </div>

                                                    </form>
                                                    <!-- input 영역 END -->
                                                </div>
                                            </div>
                                        </div>
                                    </c:when>
                                </c:choose>
                            </div>
                        </c:forEach>
                    </div>

                </div>
            </div>
        </div>
    </c:when>
</c:choose>
</main>
</body>
<script>
    function deleteNotice(id, currentPage) {
        if (confirm('정말로 이 게시글을 삭제하시겠습니까?')) {
            location.href=`noticeDelete?id=${board.id}&currentPage=${currentPage}`;
        }
    }
    function deleteComment(commentId) {
        if (confirm('정말로 이 댓글을 삭제하시겠습니까?')) {
            // 서버에 댓글 삭제 요청을 보냅니다.
            fetch('commentDelete?id=' + parseInt(commentId))
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }

                    // 댓글이 성공적으로 삭제되었음을 알립니다.
                    alert('댓글이 성공적으로 삭제되었습니다.');

                    // 댓글 삭제 후 페이지를 새로고침합니다.
                    location.reload();
                })
        }
    }

</script>
</html>

