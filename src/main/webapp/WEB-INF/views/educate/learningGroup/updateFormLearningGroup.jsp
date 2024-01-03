<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <%@ include file="/WEB-INF/components/Header.jsp"%>
    <title>학습그룹 수정</title>
    <link href="/css/homework.css" rel="stylesheet" type="text/css">
</head>
<style>
    .large-font {
        font-size: 1.2em; /* 기본 글자 크기의 1.2배 */
    }
</style>
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
                    <h1>학습그룹 수정</h1>
                    <hr/>
                </div>

                <%--컨텐츠 헤더--%>
                <div class="container p-5">
                    <div class="table">
                        <table class="table text-center">
                            <tr>
                                <th class="col-4 large-font">게임콘텐츠명 : ${updateFormLearningGroup.title}</th>
                                <th class="col-4 large-font">학습가능인원 : ${updateFormLearningGroup.maxSubscribers}명</th>
                                <th class="col-4 large-font">그룹 총 배정인원 : ${updateFormLearningGroup.assignedPeople}명</th>
                            </tr>
                        </table>
                    </div>
                </div>

                <%--컨텐츠 메인--%>
                <div class="container p-5">
                    <h2 class="pb-3">그룹 상세 정보</h2>
                    <form id="insertForm" action="/group/updateLearningGroup" method="post">
                        <div class="my-4">
                            <input type="hidden" id="id" name="id" value="${updateFormLearningGroup.id}">
                            <input type="hidden" id="userId" name="userId" value="${updateFormLearningGroup.userId}">
                            <div class="row align-items-baseline mb-3">
                                <label class="col-sm-2 col-form-label fw-bold text-end">교육자명</label>
                                <div class="col-sm-10">
                                    <label>${updateFormLearningGroup.userName}</label>
                                </div>
                            </div>
                            <div class="row align-items-baseline mb-3">
                                <label class="col-sm-2 col-form-label fw-bold text-end">그룹명</label>
                                <div class="col-sm-10">
                                    <input type="text" class="typeahead form-control md-0 pd-0" id="name" name="name" value="${updateFormLearningGroup.name}" required="required">
                                </div>
                            </div>
                            <div class="row align-items-baseline mb-3">
                                <label class="col-sm-2 col-form-label fw-bold text-end">그룹인원</label>
                                <div class="col-sm-10">
                                    <input type="text" class="typeahead form-control md-0 pd-0" id="groupSize" name="groupSize" value="${updateFormLearningGroup.groupSize}" required="required">
                                </div>
                            </div>
                            <div class="row align-items-center mb-3">
                                <label class="col-sm-2 col-form-label fw-bold text-end">구독기간</label>
                                <div class="col-sm-3">
                                    <input type="date" class="form-control" id="startDate" name="startDate" value="${updateFormLearningGroup.startDate}" required="required">
                                </div>
                                <div class="col-sm-2 text-center">
                                    ~
                                </div>
                                <div class="col-sm-3">
                                    <input type="date" class="form-control" id="endDate" name="endDate" value="${updateFormLearningGroup.endDate}" required="required">
                                </div>
                            </div>
                            <div class="row align-items-baseline mb-3">
                                <label class="col-sm-2 col-form-label fw-bold text-end">기타항목1</label>
                                <div class="col-sm-10">
                                    <textarea class="typeahead form-control md-0 pd-0" id="etc1" name="etc1">${updateFormLearningGroup.etc1}</textarea>
                                </div>
                            </div>
                            <div class="row align-items-baseline mb-3">
                                <label class="col-sm-2 col-form-label fw-bold text-end">기타항목2</label>
                                <div class="col-sm-10">
                                    <textarea class="typeahead form-control md-0 pd-0" id="etc2" name="etc2">${updateFormLearningGroup.etc2}</textarea>
                                </div>
                            </div>
                            <div class="text-center">
                                <button type="submit" class="btn btn-primary col-lg-2">학습 그룹 수정</button>
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
