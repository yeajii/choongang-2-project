<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <%@ include file="/WEB-INF/components/Header.jsp"%>
    <title>Title</title>

    <style>
        .error {color: red}
    </style>

    <script>
        function calculateDiscountedPrice(){
            // 사용자한테 정가(price)와 할인율 입력 받기
            const priceInput = document.getElementById("price");
            const discountRateInput = document.getElementById("discountRate");
            const discountPriceInput = document.getElementById("discountPrice");

            // 정가와 할인율 값 가져오기
            const price = parseFloat(priceInput.value);
            const discountRate = parseFloat(discountRateInput.value);

            // 판매가 계산
            const discountedPrice = calculateDiscountedPriceValue(price, discountRate);

            // 결과 출력
            discountPriceInput.value = discountedPrice.toFixed(0);
        }

        function calculateDiscountedPriceValue(price, discountRate){
            return price * (1 - discountRate / 100);
        }
    </script>
</head>
<style>
    h1 {
        color: black;
        font-size: 32px;
        font-weight: 600;
        word-wrap: break-word;
        text-align: center;
    }
    h4{
        color: black;
        font-weight: 600;
        word-wrap: break-word;
        text-align: center;
        margin: 30px;
    }
    /*#submit{*/
    /*    width: auto;*/
    /*}*/
    .table tr td {
        border: solid 0px;
        padding-left: 40px;

    }
    .table tr th {
        border: solid 0px;
        text-align: end;
        white-space: nowrap; /* 줄바꿈을 방지 */
    }
</style>
<body>
<%@ include file="/WEB-INF/components/TopBar.jsp"%>
<main>
    <div class="d-flex">
        <div class="col-2">
            <%@ include file="/WEB-INF/components/AdminSidebar.jsp"%>
        </div>
        <div id="main-content" class="container p-5 col-10">
            <%-- 이곳에 작성을 해주세요 --%>
            <div class="container border">
                <div class="my-4">
                    <h1>게임콘텐츠 등록</h1>
                </div>
                <div class="m-5 px-5">
                    <form:form action="gameContentInsert" method="post" enctype="multipart/form-data" modelAttribute="gameContents">
                        <table class="table ">
                            <tr>
                                <th style="width: 12%">게임 콘텐츠명</th>
                                <td>
                                    <input type="text" class="form-control" name="title">
                                        <%--                            <textarea cols="50" rows="1" name="title"></textarea>--%>
                                    <form:errors path="title" cssClass="error"/>
                                </td>
                            </tr>

                            <tr>
                                <th>학습 난이도</th>
                                <td>
                                    <select name="gameLevel" class="form-select">
                                        <form:errors path="gameLevel" cssClass="error"/>
                                        <option value="1">초급</option>
                                        <option value="2">중급</option>
                                        <option value="3">고급</option>
                                    </select>
                                </td>
                            </tr>

                            <tr>
                                <th>구독 기간</th>
                                <td>
                                    <div class="input-group">
                                        <label for="months"></label>
                                        <input type="number" class="form-control" id="months" name="subscribeDate" min="1" max="12" >
                                            <%--<form:errors path="subscribeDate" cssClass="error"/>--%>
                                        <span class="input-group-text" >개월</span>
                                    </div>
                                </td>
                            </tr>

                            <tr>
                                <th>구독 가능 인원 수</th>
                                <td>
                                    <div class="input-group">
                                        <input type="number" class="form-control" min="1" name="maxSubscribers">
                                            <%--<form:errors path="maxSubscribers" cssClass="error"/>--%>
                                        <span class="input-group-text" >명</span>
                                    </div>
                                </td>
                            </tr>

                            <tr>
                                <th>
                                    <label for="price">정가</label>
                                </th>
                                <td >
                                    <div class="input-group">
                                        <input type="number" class="form-control" name="price" id="price" oninput="calculateDiscountedPrice()" placeholder="정가를 입력하세요">
                                            <%--                                <form:errors path="price" cssClass="error"/>--%>
                                        <span class="input-group-text" >원</span>
                                    </div>
                                </td>
                            </tr>

                            <tr>
                                <th>
                                    <label for="discountRate">할인율</label>
                                </th>
                                <td>
                                    <div class="input-group">
                                        <input type="number" class="form-control" name="discountRate" id="discountRate"
                                               oninput="calculateDiscountedPrice()" placeholder="할인율을 입력하세요">
                                            <%--<form:errors path="discountRate" cssClass="error"/>--%>
                                        <span class="input-group-text" >%</span>
                                    </div>
                                </td>
                            </tr>

                            <tr>
                                <th>판매가</th>
                                <td>
                                    <div class="input-group">
                                        <input type="number" class="form-control" name="discountPrice" id="discountPrice" readonly>
                                        <span class="input-group-text" >원</span>
                                    </div>
                                </td>
                            </tr>

                            <tr>
                                <th>패키지 내용</th>
                                <td>
                                    <textarea class="form-control" cols="50" rows="10" name="content"></textarea>
                                    <form:errors path="content" cssClass="error"/>
                                </td>
                            </tr>

                            <tr>
                                <th>썸네일</th>
                                <td>
                                    <input type="file" id="file" name="file1">
                                </td>
                            </tr>

                            <tr>
                                <th></th>
                                <td>
                                    <div class="d-flex justify-content-around my-3">
                                        <button type="submit" class="btn btn-primary col-5" id="submit">등록하기</button>
                                        <button type="reset" class="btn  col-5" id="reset">취소하기</button>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </form:form>
                </div>
            </div>

        </div>
    </div>

</main>
<%@ include file="/WEB-INF/components/Footer.jsp"%>
</body>
</html>