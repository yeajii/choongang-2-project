<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <%@ include file="/WEB-INF/components/Header.jsp"%>
    <title>학습그룹 등록</title>
    <link href="/css/homework.css" rel="stylesheet" type="text/css">
</head>
<style>
    .large-font {
        font-size: 1.2em; /* 기본 글자 크기의 1.2배 */
    }
</style>
<script type="text/javascript">

    // HTML 페이지가 로드되면 실행되는 함수
    window.onload = function() {
        var subscribeDate = ${insertFormLearningContent.subscribeDate}; // subscribeDate를 가져옵니다.
        var createdAt = new Date("${insertFormLearningContent.createdAt}"); // createdAt를 가져옵니다.

        // subscribeEndDate를 계산합니다.
        createdAt.setMonth(createdAt.getMonth() + subscribeDate);

        // subscribeEndDate를 yyyy-mm-dd 형식으로 변환합니다.
        var year = createdAt.getFullYear();
        var month = ("0" + (createdAt.getMonth() + 1)).slice(-2);
        var day = ("0" + createdAt.getDate()).slice(-2);
        var subscribeEndDate = year + "-" + month + "-" + day;

        // endDate input 필드의 max 속성을 설정합니다.
        document.getElementById("endDate").max = subscribeEndDate;



        // 'groupSize'라는 ID를 가진 input 요소를 찾고
        var groupSizeInput = document.getElementById('groupSize');

        // 만약 해당 요소가 존재한다면
        if (groupSizeInput) {
            // input 요소에 'input' 이벤트 리스너를 추가
            groupSizeInput.addEventListener('input', function(e) {
                var maxSubscribers = ${insertFormLearningContent.maxSubscribers};
                var assignedPeople = ${insertFormLearningContent.assignedPeople};

                // 사용자가 입력한 값
                var inputValue = e.target.value;

                // 만약 할당된 사람들의 수와 사용자가 입력한 수의 합이 최대 구독자 수를 초과한다면
                if (assignedPeople + parseInt(inputValue) > maxSubscribers) {
                    // 알림을 통해 사용자에게 알리고
                    alert('입력하신 값이 너무 큽니다. ' + (maxSubscribers - assignedPeople) + ' 이하의 값을 입력해주세요.');

                    // 입력된 값을 초기화합니다.
                    e.target.value = '';
                }
            });
        }

        var startDateInput = document.getElementById('startDate');
        var endDateInput = document.getElementById('endDate');

        if (startDateInput && endDateInput) {
            var subscribeEndDate = new Date(`${subscribeEndDate}`);

            startDateInput.addEventListener('change', function(e) {
                var today = new Date();
                today.setHours(0, 0, 0, 0);

                var selectedDate = new Date(e.target.value);

                var endDate = new Date(endDateInput.value);

                if (selectedDate < today) {
                    alert('시작 날짜는 오늘 날짜 이후로 선택해주세요.');
                    e.target.value = '';
                } else if (selectedDate >= subscribeEndDate) {
                    alert('시작 날짜는 종료 날짜 이전으로 선택해주세요.');
                    e.target.value = '';
                }
            });

            endDateInput.addEventListener('change', function(e) {
                var startDate = new Date(startDateInput.value);
                var selectedDate = new Date(e.target.value);

                if (selectedDate <= startDate) {
                    alert('종료 날짜는 시작 날짜 이후로 선택해주세요.');
                    e.target.value = '';
                } else if (selectedDate > subscribeEndDate) {
                    alert('종료 날짜는 구독 종료 날짜 이전으로 선택해주세요.');
                    e.target.value = '';
                }
            });
        }
    };


</script>
<body>
    <%@ include file="/WEB-INF/components/TopBar.jsp"%>
    <main>
        <div class="d-flex">
            <div class="col-2">
                <%@ include file="/WEB-INF/components/EducateSidebar.jsp"%>
            </div>

            <div class="container p-5 col-10">
                <div class="container border my-4 py-3">
                    <div class="container my-3 py-3">
                        <h1>학습그룹 생성</h1>
                        <hr/>
                    </div>

                    <%--컨텐츠 헤더--%>
                    <div class="container p-5">
                        <div class="table">
                            <table class="table text-center">
                                <tr>
                                    <th class="col-4 large-font">게임콘텐츠명 : ${insertFormLearningContent.title}</th>
                                    <th class="col-4 large-font">학습가능인원 : ${insertFormLearningContent.maxSubscribers}명</th>
                                    <th class="col-4 large-font" >그룹 총 배정인원 : ${insertFormLearningContent.assignedPeople}명</th>
                                </tr>
                            </table>
                        </div>
                    </div>

                    <%--컨텐츠 메인--%>
                    <div class="container p-5">
                        <h2 class="pb-3">그룹 상세 정보</h2>
                        <form id="insertForm" action="/group/insertLearningGroup" method="post">
                            <div class="my-4">
                                <input type="hidden" id="id" name="id" value="${insertFormLearningContent.contentId}">
                                <input type="hidden" id="userId" name="userId" value="${insertFormLearningContent.payUserId}">
                                <div class="row align-items-baseline mb-3">
                                    <label class="col-sm-2 col-form-label fw-bold text-end">교육자명</label>
                                    <div class="col-sm-10">
                                        <label>${insertFormLearningContent.name}</label>
                                    </div>
                                </div>
                                <div class="row align-items-baseline mb-3">
                                    <label class="col-sm-2 col-form-label fw-bold text-end">그룹명</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="typeahead form-control md-0 pd-0" id="name" name="name" required="required">
                                    </div>
                                </div>
                                <div class="row align-items-baseline mb-3">
                                    <label class="col-sm-2 col-form-label fw-bold text-end">그룹인원</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="typeahead form-control md-0 pd-0" id="groupSize" name="groupSize" required="required">
                                    </div>
                                </div>
                                <div class="row align-items-center mb-3">
                                    <label class="col-sm-2 col-form-label fw-bold text-end">구독기간</label>
                                    <div class="col-sm-3">
                                        <input type="date" class="form-control" id="startDate" name="startDate" required>
                                    </div>
                                    <div class="col-sm-2 text-center">
                                        <span class="mx-2">~</span>
                                    </div>
                                    <div class="col-sm-3">
                                        <input type="date" class="form-control" id="endDate" name="endDate" required>
                                    </div>
                                </div>
                                <div class="row align-items-baseline mb-3">
                                    <label class="col-sm-2 col-form-label fw-bold text-end">기타항목1</label>
                                    <div class="col-sm-10">
                                        <textarea class="typeahead form-control md-0 pd-0" id="etc1" name="etc1"></textarea>
                                    </div>
                                </div>
                                <div class="row align-items-baseline mb-3">
                                    <label class="col-sm-2 col-form-label fw-bold text-end">기타항목2</label>
                                    <div class="col-sm-10" >
                                        <textarea class="typeahead form-control md-0 pd-0" id="etc2" name="etc2"></textarea>
                                    </div>
                                </div>
                                <div class="text-center">
                                    <button type="submit" class="btn btn-primary col-lg-2">학습 그룹 등록</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </main>
    <%@ include file="/WEB-INF/components/Footer.jsp"%>
</body>
</html>
