<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <%@ include file="/WEB-INF/components/Header.jsp"%>
    <title>QNA수정</title>
</head>
<style>
    #main-content {
        margin-left: 440px;
    }
    .date-text {
        font-size: 16px;
        font-weight: 600;
        margin-right: 20px;
        margin-left: 10px;
        text-align: center;
    }

    h1 {
        color: black;
        font-size: 32px;
        font-weight: 600;
        word-wrap: break-word;
        text-align: center;
    }
    .form-check-input[type=radio] {
        width: 20px;
        height: 20px;
        border-width: 2px;
    }

</style>

<body>
<%@ include file="/WEB-INF/components/TopBar.jsp"%>
<main>
    <div class="d-flex">
        <div class="col-second">
            <%@ include file="/WEB-INF/components/AdminSidebar.jsp"%>
        </div>
    </div>
    <div id="main-content" class="container p-5 col-10" style="border: 0px solid red;">
        <div class="container border my-4 py-3">
            <div class="container my-3 py-3" style="text-align: center">
                <H1>QNA수정</H1>
            </div>
            <div>
                <form action="QNAUpdate?id=${board.id}&currentPage=${currentPage}" method="post" enctype="multipart/form-data">
                    <div class="my-4 row align-items-baseline">
                        <label for="title" class="col-sm-2 col-form-label fw-bold text-end"
                               style="font-size: 20px;">제목</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" id="title" name="title" value="${board.title}" required>
                        </div>
                        <div class="my-4 row align-items-baseline">
                            <label for="content" class="col-sm-2 col-form-label fw-bold text-end"
                                   style="font-size: 20px;">내용</label>
                            <div class="col-sm-8">
                                <textarea id="content" name="content" class="form-control" required>${board.content}</textarea>
                            </div>
                        </div>
                    </div>

                    <div class="container row justify-content-center my-5">

                        <button type="submit" class="btn btn-primary col-4 px-3 mx-2"
                                style="background: #52525C; border: none">저장하기
                        </button>
                        <button type="reset" class="btn btn-primary col-4 px-3 mx-2" onclick="window.location.href='QNADetail?id=${board.id}'">취소</button>

                    </div>

                </form>

            </div>
        </div>
    </div>
</main>
</body>
</html>
