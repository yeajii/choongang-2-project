<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <%@ include file="/WEB-INF/components/Header.jsp"%>
    <title>그룹가입 승인</title>
    <link href="/css/homework.css" rel="stylesheet" type="text/css">
</head>
<script>
    function listSearch() {
        $.ajax({
            url: '/group/approvalGroupMember',
            type: 'POST',
            dataType: 'json',
            data: {
                groupId: $('#groupId').val()
            },
            success: function(data) {
                /*alert(JSON.stringify(data))*/
                var tbody = $("#homework-table tbody");

                // 기존 테이블의 내용을 지웁니다.
                tbody.empty();

                // 새로운 테이블의 내용을 추가합니다.
                $.each(data, function(index, item) {
                    var row = $("<tr>").data('item', item);         // row에 item data를 저장.
                    row.append($("<td>").text(index + 1));
                    row.append($("<td>").text(item.userName));
                    row.append($("<td>").text(item.phone));

                    var approvalDate = new Date(item.approvalDate);
                    var formattedDate = approvalDate.toLocaleDateString('ko-KR', {
                        year: 'numeric',
                        month: '2-digit',
                        day: '2-digit'
                    });
                    row.append($("<td>").text(formattedDate));

                    var status = (item.status == 0) ? '승인대기' : '승인완료';
                    var statusLink = $("<a>").text(status).attr('href', '#').addClass('status-link');
                    row.append($("<td>").append(statusLink));
                    tbody.append(row);
                });
            },
            error: function(error) {
                console.log(error);     // 오류가 발생한 경우 콘솔에 오류 출력
            }
        });
    }

    // 이벤트 위임 방식으로 클릭 이벤트를 처리합니다.
    $(document).on('click', '.status-link', function(e) {
        e.preventDefault();     // 기본 링크 클릭 동작을 막습니다.

        var row = $(this).closest('tr');  // 클릭한 항목의 행을 가져옴.
        var item = row.data('item');  // 행에서 item data를 가져옴.

        // 사용자에게 실행 여부를 물어봅니다.
        var doProceed = confirm("그룹 가입 신청을 승인하시겠습니까?");
        if (!doProceed) {
            return; // 사용자가 취소를 클릭하면 작업을 중단합니다.
        }

        $.ajax({
            url: '/group/grantMember',
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(item), // 여기에서 item은 LearningGroupMember 객체를 나타내는 JavaScript 객체입니다.
            success: function(response) {
                // 컨트롤러에서 반환한 데이터를 처리합니다.
                console.log(response);

                // 추가: AJAX 호출이 성공적으로 완료된 후 해당 행의 상태를 '승인완료'로 변경합니다.
                row.find('.status-link').text('승인완료');  // 상태 링크 텍스트를 '승인완료'로 변경합니다.
            },
            error: function(error) {
                console.log(error);
            }
        });
    });




</script>
<body>
    <%@ include file="/WEB-INF/components/TopBar.jsp"%>
    <main>
        <div class="d-flex">
            <div class="col-2">
                <%@ include file="/WEB-INF/components/EducateSidebar.jsp"%>
            </div>

            <div id="main-content" class="container p-5 col-10">

                <div class="container my-4 py-3">
                    <div class="container">
                        <h1>그룹신청 승인관리</h1>
                    </div>
                    <div class="container">
                        <h2>신청 내역</h2>
                        <hr/>
                    </div>

                    <%--디테일 상단부--%>
                    <div class="container">
                        <form action="/group/approvalGroupMember" method="post">
                            <div class="d-flex justify-content-between">
                                <div class="col-5 d-flex">
                                    <div class="input-group">
                                        <label for="groupId" class="input-group-text fw-bold"
                                               style="font-size: 16px;">그룹명</label>
                                        <select class="form-select text-center searchContent" id="groupId" name="groupId" onchange="listSearch()">
                                            <option value="0">그룹을 선택하세요</option>
                                            <c:forEach var="learningGroup" items="${learningGroup}" varStatus="status">
                                                <option value="${learningGroup.id}">${learningGroup.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>

                    <%--본문 리스트--%>
                    <div class="container border p-2 m-2" style="height: auto;">
                        <div class="table">
                            <table id="homework-table" class="table text-center">
                                <thead>
                                <tr>
                                    <th scope="col" style="">No</th>
                                    <th scope="col" style="">학생이름</th>
                                    <th scope="col" style="">연락처</th>
                                    <th scope="col" style="">가입요청일자</th>
                                    <th scope="col" style="">상태</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="learningGroupMembers" items="${learningGroupMembers}" varStatus="ststus">
                                    <tr>
                                        <td>${ststus.index + 1}</td>
                                        <td>${learningGroupMembers.userName}</td>
                                        <td>${learningGroupMembers.phone}</td>
                                        <td>${learningGroupMembers.approvalDate}</td>
                                        <td>${learningGroupMembers.status}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
    <%@ include file="/WEB-INF/components/Footer.jsp"%>
</body>
</html>
