<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2023-12-26
  Time: 오전 9:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <%@ include file="/WEB-INF/components/Header.jsp"%>
    <title>Title</title>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script>

        function checkSubmitHandler(event) {
            // 필요한 입력 필드 가져오기
            const email = document.getElementById("email");
            const pw = document.getElementById("password");
            const pw2 = document.getElementById("passwordCheck");
            const name = document.getElementById("name");
            const nickname = document.getElementById("nickname");
            const phone = document.getElementById("phone");
            // 추가 필요한 필드...

            // 패스워드가 제공되었는지 확인
            if (pw.value === "") {
                alert("패스워드를 입력해주세요.");
                event.preventDefault();
                return false;
            }
            // 패스워드 확인이 제공되었는지 확인
            if (pw2.value === "") {
                alert("패스워드 확인을 입력해주세요.");
                event.preventDefault();
                return false;
            }

            // 패스워드와 패스워드 확인이 일치하는지 확인
            if (pw.value !== pw2.value) {
                alert("패스워드가 일치하지 않습니다.");
                event.preventDefault();
                return false;
            }


            // 사용자명이 제공되었는지 확인
            if (name.value === "") {
                alert("사용자명을 입력해주세요.");
                event.preventDefault();
                return false;
            }

            // 이메일이 제공되었는지 확인
            if (email.value === "") {
                alert("이메일을 입력해주세요.");
                event.preventDefault();
                return false;
            }

            // 핸드폰번호가 제공되었는지 확인
            if (phone.value === "") {
                alert("핸드폰번호를 입력해주세요.");
                event.preventDefault();
                return false;
            }
        }

        function fn_register() {

            checkSubmitHandler(event)
        }
</script>
</head>
<body>
<%@ include file="/WEB-INF/components/TopBar.jsp"%>
<main>


    
    <div id="main-content">
        <div class="my-5">
            <div class="" id="detail-main-container">
                <div class="container p-2" id="form-container">
                    <h1 style="text-align: center">회원정보수정</h1>
                    <hr class="hr" />
                    <form action="/userUpdate" method="post">
                        <input type="hidden" id="id" name="id" value="${userDetail.id}">


                        <div class="my-4 row align-items-baseline">
                            <label for="name" class="col-sm-2 col-form-label fw-bold text-end">이름</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" name="name" id="name" value="${userDetail.name}" required="required" readonly>
                            </div>
                        </div>

                        <div class="my-4 row align-items-baseline">
                            <label for="email" class="col-sm-2 col-form-label fw-bold text-end">email</label></label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" value="${userDetail.email}" name="email" id="email" readonly>
                            </div>
                        </div>

                        <div class="my-4 row align-items-baseline">
                            <label for="nickname" class="col-sm-2 col-form-label fw-bold text-end">아이디</label>
                            <div class="col-sm-8">

                                <input type="text" class="form-control" name="nickname" id="nickname" value="${userDetail.nickname}" readonly>
                            </div>
                        </div>


                        <div class="my-4 row align-items-baseline">
                            <label for="password" class="col-sm-2 col-form-label fw-bold text-end">비밀번호<SUP
                                    style="color: #FF4379; font-size: 18px;">*</SUP></label>
                            <div class="col-sm-8">
                                <input type="password" class="form-control" name="password" id="password" required="required">
                            </div>
                        </div>

                        <div class="mmy-4 row align-items-baseline">
                            <label for="passwordCheck" class="col-sm-2 col-form-label fw-bold text-end">비밀번호확인<SUP
                                    style="color: #FF4379; font-size: 18px;">*</SUP></label>
                            <div class="col-sm-8">
                                <input type="password" class="form-control" name="passwordCheck" id="passwordCheck" required="required">
                            </div>
                        </div>



                        <div class="row my-4 align-items-baseline">
                            <label for="userType" class="col-sm-2 col-form-label fw-bold text-end">회원구분<SUP
                                    style="color: #FF4379; font-size: 18px;">*</SUP></label>
                            <div class="col-sm-8">
                                <select class="form-select" name="userType" id="userType">
                                    <option value="1" ${userDetail.userType == 1?"selected":""}>관리자</option>
                                    <option value="2" ${userDetail.userType == 2?"selected":""}>교육자</option>
                                    <option value="3" ${userDetail.userType == 3?"selected":""}>학습자</option>
                                    <option value="4" ${userDetail.userType == 4?"selected":""}>일반인</option>
                                </select>
                            </div>
                        </div>



                        <div class="my-4 row align-items-baseline">
                            <label for="address" class="col-sm-2 col-form-label fw-bold text-end">주소</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" name="address" id="address" value="${userDetail.address}">
                            </div>
                        </div>

                        <div class="my-4 row align-items-baseline">
                            <label for="phone" class="col-sm-2 col-form-label fw-bold text-end">전화번호<SUP
                                    style="color: #FF4379; font-size: 18px;">*</SUP></label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" name="phone" id="phone" value="${userDetail.phone}">
                            </div>
                        </div>

                        <div class="row my-4 align-items-baseline">
                            <label class="col-sm-2 col-form-label fw-bold text-end">성별<SUP
                                    style="color: #FF4379; font-size: 18px;">*</SUP></label>
                            <div class="col-sm-8 d-flex">

                                <div class="col-sm-3 form-check">
                                    <input class="form-check-input" type="radio" name="gender" id="male" value="0"  ${userDetail.gender == 0?"checked":""}>
                                    <label class="form-check-label" for="male">남자</label>
                                </div>

                                <div class="col-sm-3 form-check">
                                    <input class="form-check-input" type="radio" name="gender" id="female" value="1" ${userDetail.gender == 1?"checked":""}>
                                    <label class="form-check-label" for="female">여자</label>
                                </div>
                            </div>
                        </div>

                        <div class="row my-4 align-items-baseline">
                            <label class="col-sm-2 col-form-label fw-bold text-end">광고 메세지 수신</label>
                            <div class="col-sm-8 d-flex">

                                <div class="col-sm-3 form-check">
                                    <input class="form-check-input" type="radio" name="consent1" id="consent1" value="1" ${userDetail.consent1 == 1?"checked":""}>
                                    <label class="form-check-label" for="consent1">EMAIL</label>
                                </div>

                                <div class="col-sm-3 form-check">
                                    <input class="form-check-input" type="radio" name="consent2"id="consent2" value="1"  ${userDetail.consent2 == 1?"checked":""}>
                                    <label class="form-check-label" for="consent2">SMS</label>
                                </div>
                            </div>
                        </div>




                        <hr class="hr" />



                        <div class="d-flex justify-content-between">
                            <div class="col-6 mb-3" >
                                <button type="submit" class="form-control btn btn-primary w-100" onclick="fn_register()">수정</button>
                            </div>
                            <div class="col-3 mb-3">
                                <button type="reset" class="btn btn-outline-secondary w-100" onclick="return confirm('입력하신 내용이 초기화됩니다. 정말 진행하시겠습니까?')">초기화</button>
                            </div>
                            <div class="col-2 mb-3">
                                <button type="button" class="btn btn-outline-secondary w-100" onclick="window.history.back()">취소</button>
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
