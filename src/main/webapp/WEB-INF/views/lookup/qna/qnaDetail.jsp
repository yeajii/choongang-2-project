<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
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
    </style>
    <title>Title</title>
</head>
<body>

<%@ include file="/WEB-INF/components/TopBar.jsp"%>
<main>
    <%@ include file="/WEB-INF/components/LookupSidebar.jsp"%>

    <form action="QNAUpdate" method="post">

        <div class="container main-container">
            <div class="post-content">
                <h1>${board.title}</h1>
                <p>작성자 : ${board.name}</p>
                <p>작성일 : <fmt:formatDate value="${board.createdAt}" type="date" pattern="YY/MM/dd"/></p>
                <p>조회수: ${board.readCount}</p>
                <p>${board.content}</p>
            </div>

            <div class="post-controls">
                <button type="button" class="btn btn-primary2" onclick="location.href='/lookup/board/QNABoardList'">목록으로</button>
                <c:if test="${board.userId == userId}">
                    <button type="button" class="btn btn-primary2" onclick="location.href='/lookup/board/QNAUpdateForm?id=${board.id}&currentPage=${currentPage}'">수정하기</button>
                    <button type="button" class="btn btn-danger" onclick="deleteQNA('${board.id}', '${currentPage}')">삭제</button>
                </c:if>
            </div>
        </div>
    </form>
    <form name="updateForm">
        <input type="hidden" name="target_id" value="${board.id}">
        <input type="hidden" name="title" id="${board.title}">
        <input type="hidden" name="content" id="${board.content}">
        <input type="hidden" name="content" id="${board.createdAt}">

    </form>
    <c:choose>
        <c:when test="${board.boardType eq 3 }">
            <div class="container p-3 comment-custom">
                <div class="row row-cols-1 align-items-start">

                    <div class="col">
                        <!-- input 영역 -->


                        <!-- 대댓글 출력  -->
                        <div class="container p-3 comments-custom">
                            <c:forEach var="comments" items="${comments }">
                                <div class="row row-cols-2 align-items-start">
                                    <div class="col comments-nickname">
                                        <p>${comments.name }</p>
                                    </div>

                                    <div class="col comments-content" style="width: 100%; height: 200px;">
                                        <p class="d-inline-flex gap-1" style="width: 100%; height: 100%;">
                                            <c:forEach begin="2" end="${comments.commentIndent }">└▶</c:forEach>
                                            <button class="btn" type="button" data-bs-toggle="collapse"
                                                    data-bs-target="#collapseExample${comments.id}"
                                                    aria-expanded="false" aria-controls="collapseExample"
                                                    style="width: 100%; text-align: left;">
                                                    ${comments.content }
                                            </button>

                                        </p>
                                    </div>
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
</html>

<script>
    function deleteQNA(id, currentPage) {
        if (confirm('정말로 이 게시글을 삭제하시겠습니까?')) {
            location.href=`QNADelete?id=${board.id}&currentPage=${currentPage}`;
        }
    }
    function registerComment() {
        $.ajax({
            type: "POST",
            url: "commentInsert",
            data: $("#commentGroupId").serialize(),
            success: function(response) {
                // 댓글이 성공적으로 등록되면, 버튼을 숨김
                if (response.status === 'success') {
                    $('button.btn_detail_custom').hide();
                    // 댓글이 성공적으로 등록되면, 입력란을 초기화
                    $('input[name="content"]').val('');
                }
            },
            error: function(error) {
                console.error('댓글 등록 실패:', error);
            }
        });
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



