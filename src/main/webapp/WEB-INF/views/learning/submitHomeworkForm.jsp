<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <%@ include file="/WEB-INF/components/Header.jsp" %>
    <title>Title</title>
    <link href="/css/homework.css" rel="stylesheet" type="text/css">
    <script type="module">
        import showNotification from '/js/utils/notification.js'; // 모듈을 불러옵니다.
        import {storeNotification,showStoredNotification }from '/js/utils/notificationManager.js';

        $(document).ready(function(){
            $("form").on("submit", function(e){
                e.preventDefault();
                let checkedItems = [];
                $("input[type=checkbox]:checked").each(function(){
                    const index = $(this).attr("data-index");
                    const item = {
                        homeworkId: $("input[name='distributedHomeworks[" + index + "].homeworkId']").val(),
                        userId: $("input[name='distributedHomeworks[" + index + "].userId']").val(),
                        content: $("textarea[name='distributedHomeworks[" + index + "].content']").val(),
                        questions: $("textarea[name='distributedHomeworks[" + index + "].questions']").val(),
                        progress: $("input[name='distributedHomeworks[" + index + "].progress']").val()
                    };
                    checkedItems.push(item);
                });
                if(checkedItems.length > 0){
                    $.ajax({
                        url: "/homework/submissionHomework",
                        type: "POST",
                        data: JSON.stringify(checkedItems),
                        contentType: "application/json",
                        success: function(response) {
                            storeNotification(response.message, "success");

                            // 페이지를 새로고침합니다.
                            location.reload();            },
                        error: function(jqXHR, textStatus, errorThrown) {
                            const error = jqXHR.responseJSON;
                            showNotification(error.message, "error", 3000);
                        }
                    });
                } else{
                    showNotification("선택된 숙제가 없습니다.", "warn", 3000);
                }
            });

            $("button[type=button]").on("click", function(e){
                var $this = $(this);
                var index = $this.data("index");
                console.log(index);

                if ($this.text() === "수정하기") {
                    $("#content-" + index + ", #questions-" + index +", #progress-" + index).prop('disabled', false);
                    $this.text("수정 완료");
                    // $('<button type="button" class="btn">취소</button>').insertAfter($this);
                } else {
                    // '수정 완료' 버튼을 클릭했을 때
                    // AJAX 요청을 보내서 서버에 데이터를 전달하고, 버튼의 텍스트를 '수정하기'로 변경
                    var item = {
                        homeworkId: $this.data("homework-id"),
                        userId: $this.data("user-id"),
                        content: $("#content-" + index).val(),
                        questions: $("#questions-" + index).val(),
                        progress: $("#progress-"+index).val()
                    };
                     console.log(item);

                    $.ajax({
                        url: "/homework/editSubmissionHomework", // 수정할 URL
                        type: "POST", // 요청 메서드
                        dataType: "json",
                        data: JSON.stringify(item), // 서버로 보낼 데이터
                        contentType: "application/json", // 데이터의 MIME 타입
                        success: function(response) {
                            storeNotification(response.message, "success");
                            location.reload();
                        },
                        error: function(jqXHR, textStatus, errorThrown) {
                            const error = jqXHR.responseJSON;
                            showNotification(error.message, "error", 3000);

                        }
                    });
                }
            });
            showStoredNotification();
        });
    </script>
</head>
<body>
<%@ include file="/WEB-INF/components/TopBar.jsp" %>
<main>
    <div class="d-flex">
        <div class="col-2">
            <%@ include file="/WEB-INF/components/LearningSidebar.jsp"%>
        </div>
        <div id="main-content" class=" container p-5  col-9">
            <div class="container  my-4 py-3">
                <h1>숙제 제출</h1>
                <form action="/homework/submissionHomework" method="post">
                    <hr/>
                    <div class="container d-flex justify-content-between">
                       학습자 : ${user.name}
                        <button class="btn btn-primary col-lg-2">제출하기</button>
                    </div>
                    <hr/>
                    <div class="d-flex border justify-content-around" style="border-radius:15px;background-color: black">
                        <div class="col-5  d-flex p-3 text-center" >
                            <div class="col-1 d-flex justify-content-center align-content-center flex-wrap">
                            </div>
                            <div class="container ">
                                <span class="submission-table-header" >숙제 내용</span>
                            </div>
                        </div>
                        <div class="col-5 p-3 text-center">
                            <span class="submission-table-header">제출 내용</span>
                        </div>
                    </div>

                <c:forEach var="distributedHomeworks" items="${distributedHomeworksList}" varStatus="st">
                    <input type="hidden" name="distributedHomeworks[${st.index}].userId" value="${distributedHomeworks.userId}">
                    <input type="hidden" name="distributedHomeworks[${st.index}].homeworkId" value="${distributedHomeworks.homeworkId}">
                    <input type="hidden" name="distributedHomeworks[${st.index}].userId" value="${distributedHomeworks.userId}">
                    <div id="slot-${st.index}" class="d-flex slot " >
                        <div class="col-5  d-flex p-2 my-4" >
                            <div class="col-1 d-flex justify-content-center align-content-center flex-wrap">
                                <input type="checkbox" class="form-check-input" name="distributedHomeworks[${st.index}].checked"  style="font-size: 30px"
                                       value="true" data-index="${st.index}" ${(distributedHomeworks.submissionDate != null)? 'disabled':''}>
                            </div>
                            <div class="vr mx-3"></div>
                            <div class="container ">
                                <h3>${distributedHomeworks.homeworkTitle}</h3>
                                <h4>교육자 : ${distributedHomeworks.educatorName}</h4>
                                <h4>진도 : ${distributedHomeworks.homeworkProgress}</h4>
                                <h4>제출 마감일 : <fmt:formatDate value="${distributedHomeworks.homeworkDeadline}" pattern="yyyy/MM/dd (HH시)"/></h4>
                                <h4>숙제내용</h4>
                                <textarea class="form-control" disabled>${distributedHomeworks.homeworkContent}</textarea>
                            </div>
                        </div>
                        <div class="vr my-5"></div>
                        <div class="col-5 p-2 my-4">
                            <c:choose>
                                <c:when test="${distributedHomeworks.submissionDate != null}">
                                    <div class="d-flex justify-content-between align-content-center"  >
                                        제출일 :
                                        <fmt:formatDate value="${distributedHomeworks.submissionDate}"
                                                        pattern="yyyy/MM/dd (HH시)"/>
                                        <div>
                                            <button type="button" class="btn" data-index="${st.index}" data-homework-id="${distributedHomeworks.homeworkId}" data-user-id="${distributedHomeworks.userId}">수정하기</button>
                                        </div>
                                    </div>
                                    <label>진도</label>
                                    <input type="number" class="form-control" id="progress-${st.index}" disabled value="${distributedHomeworks.progress}">
                                    <label>학습 내용</label>
                                    <textarea class="form-control" id="content-${st.index}" disabled>${distributedHomeworks.content}</textarea>
                                    <label>추가 질문</label>
                                    <textarea class="form-control" id="questions-${st.index}" disabled>${distributedHomeworks.questions}</textarea>
                                </c:when>
                                <c:otherwise>
                                    <label>진도</label>
                                    <input type="number" class="form-control" id="progress-${st.index}" name="distributedHomeworks[${st.index}].progress" value="${distributedHomeworks.progress}">
                                    <label>학습 내용</label>
                                    <textarea class="form-control" id="content-${st.index}" name="distributedHomeworks[${st.index}].content">${distributedHomeworks.content}</textarea>
                                    <label>추가 질문</label>
                                    <textarea class="form-control" id="questions-${st.index}" name="distributedHomeworks[${st.index}].questions">${distributedHomeworks.questions}</textarea>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </c:forEach>
            </form>
            <div>
                <nav aria-label="Page navigation example ">
                    <ul class="pagination">
                        <c:if test="${page.startPage > page.pageBlock}">
                            <li class="page-item">
                                <a href="javascript:void(0)"
                                   onclick="location.href=createQueryURL(${page.startPage-page.pageBlock})"
                                   class="pageblock page-link">[이전]</a>
                            </li>
                        </c:if>
                        <c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
                            <li class="page-item">
                                <a href="javascript:void(0)" onclick="location.href=createQueryURL(${i})"
                                   class="pageblock page-link ${page.currentPage == i ? "active":"" }">${i}</a>
                            </li>
                        </c:forEach>
                        <c:if test="${page.endPage < page.totalPage}">
                            <li class="page-item">
                                <a href="javascript:void(0)"
                                   onclick="location.href=createQueryURL(${page.startPage+page.pageBlock})"
                                   class="pageblock page-link">[다음]</a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </div>
        </div>
        </div>
    </div>
</main>
<%@ include file="/WEB-INF/components/Footer.jsp" %>
</body>
</html>
