<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <%@ include file="/WEB-INF/components/Header.jsp"%>
    <title>Title</title>
</head>
<style>
    #main-content {
        margin-left: 420px;
    }
    .search-form {
        border: 1px solid black;
        width: 100%;
        display: flex;
        align-items: center;
        justify-content: center;
        height: 50px;
        margin-bottom: 20px;
        margin-top: 50px;
        border-radius: 16px;
    }
    #chart-area {
        width: 1200px;
        height: 700px;
    }
    .ls-object input {
        border: 0px solid black;
        width: 120px;
        text-align: end;
    }
    .ls-object input[type="radio"] {
        width: 50px;
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
        <div id="main-content" class="container p-5 col-10">
            <div class="container border my-4 py-3">
                <h2 style="text-align: center; font-weight: bold;">매출 정보 조회</h2>
                <div id="search" style="display: flex; align-items: end; justify-content: end;">
                    <div class="search-form">
                        조회기간:&nbsp;&nbsp;
                        <input type="date" name="keyword" id="keywordDate1" style="border-radius: 5px;">&nbsp;-&nbsp;
                        <input type="date" name="keyword" id="keywordDate2" style="margin-right: 100px; border-radius: 5px;">

                        검색필터:&nbsp;&nbsp;
                        <select id="searchType" style="margin-right: 100px;">
                            <option value="content" selected="selected">게임</option>
                            <option value="user">이름</option>
                        </select>

                        검색:&nbsp;&nbsp;
                        <input type="text" name="keyword" id="keyword" style="border-radius: 3px;">
                        <button class="btn-primary" id="btnSearch2" onclick="search()">조회</button>
                    </div>
                </div>
                <div class="ls-object">
                    <h5>총 매출액 : <input type="text" id="total-discountPrice" readonly>원
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        건수 : <input type="text" id="totalCount" readonly>회
                    </h5>
                </div>
                <div id="grid1" style="width: 1200px;"></div>
                <hr>
                <div style="display: flex;">
                    <div style="text-align: start; border: 1px solid black;">
                        <h5 style="border-bottom: 1px solid rgb(128,128,128); text-align: center">그래프보기</h5>
                        <input type="radio" id="salesMonth" name="charts-selector"><label for="salesMonth">월간 내역</label>
                        <input type="radio" id="salesDays" name="charts-selector"><label for="salesDays">일간 내역</label>
                        <select id="selectOption" class="form-select">
                            <option>선택없음</option>
                        </select>
                    </div>
                </div>

                <div id="chart-area" style="display: none;"></div>
            </div>

        </div>
    </main>
    <%@ include file="/WEB-INF/components/Footer.jsp"%>

    <script src="/js/admin/sales.js"></script>

</body>
</html>
