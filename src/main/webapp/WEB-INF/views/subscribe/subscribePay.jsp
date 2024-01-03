<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <%@ include file="/WEB-INF/components/Header.jsp"%>
    <title>Title</title>
</head>
<style>
    h4{
        color: black;
        font-weight: 600;
        word-wrap: break-word;
    }
</style>
<body>
<%@ include file="/WEB-INF/components/TopBar.jsp"%>
<main>
    <div class="d-flex">
        <div class="col-2">
            <%--<%@ include file="/WEB-INF/components/Sidebar.jsp"%>--%>
        </div>
        <div id="main-content" class="container p-5 col-10">
            <%-- 이곳에 작성을 해주세요 --%>
            <h4>결제 선택 방법</h4>
            <p>모바일에서도 신용카드, 무통장 입금 등 결제가 가능합니다.</p><hr>

            <form action="/subscribe/subscribePay" method="post">
                <div id="confirmation"></div>

                <input type="hidden" id="gameIds"  value="${gameIds}" name="gameIds"/>
                <%--확인용 - 구독한 게임 아이디(여러 개 가능) : ${gameIds}--%>

                <table class="table table-bordered">
                    <tr>
                        <th colspan="4">주문하실 상품</th>
                    </tr>
                    <tr>
                        <th>구매상품명</th> <td colspan="3">${title}</td>
                    </tr>

                    <tr>
                        <th colspan="4">구매자 정보</th>
                    </tr>
                    <tr>
                       <th>구매자명</th> <td colspan="3">${users.name}</td>
                    </tr>

                    <tr>
                        <th>연락처</th> <td colspan="3">${users.phone}</td>
                    </tr>

                    <tr>
                        <th>주문합계</th> <th colspan="3">${totalPrice}원</th>
                    </tr>

                    <tr>
                        <th>결제 방법 선택</th>
                        <th><input type="radio" name="paymentType" value="1">무통장입금</th>
                        <th><input type="radio" name="paymentType" value="2">계좌이체</th>
                        <th><input type="radio" name="paymentType" value="3">카카오페이</th>
                    </tr>

                    <tr>
                        <th>입금자명</th> <td colspan="3">${users.name}</td>
                    </tr>
                </table>
                <button type="submit" class="btn btn-primary col-lg-2">결제하기</button>
            </form>

        </div>
    </div>
</main>
<%@ include file="/WEB-INF/components/Footer.jsp"%>
</body>
</html>
