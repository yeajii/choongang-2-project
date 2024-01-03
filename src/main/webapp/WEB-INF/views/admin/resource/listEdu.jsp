<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <%@ include file="/WEB-INF/components/Header.jsp"%>
    <title>Title</title>
    <style>
        .searchForm {
            width: 1200px;
            border: 0px solid red;
            height: 10px;
        }
        .objectForm {
            width: 1200px;
            border: 0px solid red;
            height: 20px;
            display: flex;
            align-items: end;
            justify-content: end;
        }
        .grid-edutitle {
            color: #6b6bff;
        }
        .grid-edutitle:hover {
            opacity: 0.7; transition: 0.2s all;
        }
    </style>
</head>
<body>
    <%@ include file="/WEB-INF/components/TopBar.jsp"%>
    <main>
        <div class="d-flex">
            <div class="col-second">
                <%@ include file="/WEB-INF/components/AdminSidebar.jsp"%>
            </div>
        </div>
        <div id="main-content" class="container p-5 col-10">
            <h1 style="text-align: center; font-weight: bold;">교육자료</h1>
            <div class="container border my-4 py-3" style="width: 1245px;">
                <div class="searchForm">
<%--                    <input type="radio" id="group" name="categoryEdu" value="그룹명">--%>
<%--                    <input type="radio" id="educator" name="categoryEdu" value="교육자명">--%>
<%--                    <input type="text" id="keyword" name="keyword">--%>
<%--                    <button onclick="callListSearchEdu()"></button>--%>
                </div>
                <div id="grid1"></div>
            </div>

        </div>
    </main>
        <%@ include file="/WEB-INF/components/Footer.jsp"%>
    <script src="/js/admin/educationalList.js"></script>
    <script>
        function uploadEdu() {
            window.location.href = '/admin/resource/uploadForm';
        }
    </script>


</body>
</html>
