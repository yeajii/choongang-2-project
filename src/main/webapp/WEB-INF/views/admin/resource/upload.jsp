<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <%@ include file="/WEB-INF/components/Header.jsp"%>
    <title>Title</title>
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
                    <h1>학습자료등록</h1>
                </div>
                <div>
                    <form action="/admin/resource/upload" method="post" enctype="multipart/form-data">
                        <div class="my-4 row align-items-baseline">
                            <label for="title" class="col-sm-2 col-form-label fw-bold text-end"
                                   style="font-size: 20px;">학습자료명</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" id="title" name="title">
                            </div>
                        </div>
                        <div class="my-4 row align-items-baseline">
                            <label for="title" class="col-sm-2 col-form-label fw-bold text-end"
                                   style="font-size: 20px;">게임컨텐츠</label>
                            <div class="col-8 d-flex ">

                                <select class="form-select mx-2" id="contentId" name="contentId">
                                    <c:forEach items="${gameContentsList}" var="game">
                                        <option value="${game.id}">${game.title}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="my-4 row align-items-baseline ">
                            <label for="title" class="col-sm-2 col-form-label fw-bold text-end"
                                   style="font-size: 20px;">자료구분</label>
                            <div class="col-sm-8">
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="resourceType" id="tutorial" value="1" checked="checked">
                                    <label class="form-check-label" for="free" style="font-size: 20px; font-weight: bold;">
                                        튜토리얼
                                    </label>
                                </div>
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="resourceType" id="eduVideo" value="2">
                                    <label class="form-check-label" for="paid" style="font-size: 20px; font-weight: bold;">
                                        교육영상
                                    </label>
                                </div>
                            </div>
                        </div>

                        <div class="my-4 row align-items-baseline ">
                            <label for="title" class="col-sm-2 col-form-label fw-bold text-end"
                                   style="font-size: 20px;">자료유형</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" id="fileType" name="fileType">
                            </div>
                        </div>
                        <div class="my-4 row align-items-baseline ">
                            <label for="title" class="col-sm-2 col-form-label fw-bold text-end"
                                   style="font-size: 20px;">자료file주소</label>
                            <div class="col-sm-8" style="display: flex;">
                                https://youtu.be/&nbsp;<input type="text" class="form-control" id="fileAddress" name="fileAddress">
                            </div>
                        </div>

                        <div class="my-4 row align-items-baseline ">
                            <label for="title" class="col-sm-2 col-form-label fw-bold text-end"
                                   style="font-size: 20px;">서비스구분</label>
                            <div class="col-sm-8">
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="serviceType" id="free" value="1" checked="checked">
                                    <label class="form-check-label" for="free" style="font-size: 20px; font-weight: bold;">
                                        무료
                                    </label>
                                </div>
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="serviceType" id="paid" value="2">
                                    <label class="form-check-label" for="paid" style="font-size: 20px; font-weight: bold;">
                                        유료
                                    </label>
                                </div>
                            </div>
                        </div>

                        <div class="my-4 row align-items-baseline ">
                            <label for="content" class="col-sm-2 col-form-label fw-bold text-end"
                                   style="font-size: 20px;">자료내용</label>
                            <div class="col-sm-8">
                            <textarea class="form-control" aria-label="With textarea" id="content" rows="5"
                                      name="content"></textarea>
                                <p id="textarea-validate" class="helptext text-end"></p>
                            </div>
                        </div>

                        <div class="my-4 row align-items-baseline ">
                            <label for="title" class="col-sm-2 col-form-label fw-bold text-end"
                                   style="font-size: 20px;">썸네일</label>
                            <div class="col-sm-8">
                                <input type="file" class="form-control" id="file" name="file">
                            </div>
                        </div>

                        <div class="container row justify-content-center my-5">

                            <button type="submit" class="btn btn-primary col-4 px-3 mx-2"
                                    style="background: #52525C; border: none">저장하기
                            </button>
                            <button type="reset" class="btn btn-primary col-4 px-3 mx-2">취소</button>

                        </div>
                    </form>
                </div>
            </div>

        </div>
    </main>
    <%@ include file="/WEB-INF/components/Footer.jsp"%>
    <script>

    </script>
</body>
</html>
