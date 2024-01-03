<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>BoardUpdateForm</title>


    <script>
        function closeAndRedirect() {
            // 기본 제출 동작 막기
            event.preventDefault();

            // 취소 시 이전페이지 이동
            window.history.back();
        }
    </script>

</head>
<body>

<div id="content_title" class="container p-5 mb-4 text-center"></div>

<!-- 전체 content 영역  Start-->
<div class="container p-0 general_board_custom">

    <!-- 상단 title 영역  -->

    <!-- input 영역 -->
    <div class="container p-0 text-left">
        <form action="commentInsert" method="post" enctype="multipart/form-data">
            <input type="hidden" name="id" value="${board.id }">
            <input type="hidden" name="userId" value="1">
            <input type="hidden" name="commentGroupId" value="${board.commentGroupId }">
            <input type="hidden" name="commentStep" value="${board.commentStep }">
            <input type="hidden" name="commentIndent" value="${board.commentIndent }">

            <div class="row row-cols-1 p-0 insert_row_custom">

                <div class="row row-cols-2 p-0 insert_row2_custom">
                    <div class="form-group col title_row">
                        <label for="title">제&nbsp;목</label>
                    </div>
                    <div class="form-group col">
                        <input type="text" class="form-control title_input" id="title"
                               name="title" required="required" value="답변 : ${board.title }">
                    </div>
                </div>

                <div class="row row-cols-2 p-0 insert_row2_custom">
                    <div class="form-group col text_row">
                        <label for="content">내&nbsp;용</label>
                    </div>
                    <div class="form-group col">
							<textarea class="form-control text_input" id="content"
                                      name="content" rows="5" required>${board.content }</textarea>
                    </div>
                </div>

                <div class="row row-cols-1 p-0 insert_row2_custom">
                    <div class="form-group col btn_row">
                        <button type="submit" class="btn btn_detail_custom">등록</button>
                        <button class="btn btn_detail_custom" onclick="closeAndRedirect()">취소</button>
                    </div>
                </div>

            </div>
        </form>
        <!-- input 영역 END -->
    </div>
    <!-- 전체 content 영역  END-->
</div>
</body>
</html>