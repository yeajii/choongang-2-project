<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <%@ include file="/WEB-INF/components/Header.jsp"%>
    <title>Title</title>
</head>
<link rel="stylesheet" href="/css/edu/detailEdu.css?after">
<style>
</style>
<body>
    <%@ include file="/WEB-INF/components/TopBar.jsp"%>
    <main>
        <div class="d-flex">
            <div class="col-2">
                <%@ include file="/WEB-INF/components/LookupSidebar.jsp"%>
            </div>
            <div id="main-content" class="container p-5 col-10">
                <div class="container border my-4 py-3">
                    <div class="detailEdu-header">
                        <div class="detailEdu-title">
                            <span class="title">${edu.title}</span>
                        </div>
                        <div class="detailEdu-object">
                            <button onclick="eduList()">목록</button>
                            <c:if test="${users.userType == '1'}">
                                <button onclick="eduUpdate(${id})">수정</button>
                                <button onclick="eduDelete(${id})">삭제</button>
                            </c:if>
                        </div>
                    </div>
                    <div class="detailEdu-body">
                        <div class="detailEdu-source">
                            <c:if test="${edu.fileAddress != null}">
                                <iframe style="margin-left: 110px;" width="900" height="620" src="https://www.youtube.com/embed/${edu.fileAddress}"
                                        title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write;
                                        encrypted-media; gyroscope; picture-in-picture" allowfullscreen>
                                </iframe>
                            </c:if>
                        </div>
                        <br>
                        <div class="detailEdu-textarea">
                            <span class="contents" style="white-space: pre-line;">${edu.content}</span>
                        </div>


                    </div>

                </div>
            </div>
        </div>

    </main>
    <%@ include file="/WEB-INF/components/Footer.jsp"%>
    <script src="/js/admin/edu/detailEdu.js"></script>
</body>
</html>
