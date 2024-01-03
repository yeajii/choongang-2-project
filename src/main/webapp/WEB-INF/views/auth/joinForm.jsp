<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2023-12-07
  Time: 오전 10:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <%@ include file="/WEB-INF/components/Header.jsp"%>
    <title>Title</title>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script>
        var isNicknameChecked = false;

        document.addEventListener("DOMContentLoaded", function () {
            const yearSelect = document.getElementById("year");
            const currentYear = new Date().getFullYear();
            for (let i = currentYear; i >= currentYear - 100; i--) {
                const option = document.createElement("option");
                option.value = i;
                option.text = i;
                yearSelect.appendChild(option);
            }

            // 월 옵션 생성
            const monthSelect = document.getElementById("month");
            for (let j = 1; j <= 12; j++) {
                const option = document.createElement("option");
                option.value = j;
                option.text = j;
                monthSelect.appendChild(option);
            }

            // 일 옵션 생성
            const daySelect = document.getElementById("day");
            for (let k = 1; k <= 31; k++) {
                const option = document.createElement("option");
                option.value = k;
                option.text = k;
                daySelect.appendChild(option);
            }
        });

        function updateBirthday() {
            const yearSelect = document.getElementById("year");
            const monthSelect = document.getElementById("month");
            const daySelect = document.getElementById("day");


            const year = yearSelect.value;
            const month = monthSelect.value.padStart(2, '0');
            const day = daySelect.value.padStart(2, '0');
            const birthdate = year + month + day;
            document.getElementById("birthdate").value = birthdate;
        }

        function checkSubmitHandler(event) {
            // 필요한 입력 필드 가져오기
            const email = document.getElementById("email");
            const pw = document.getElementById("password");
            const pw2 = document.getElementById("passwordCheck");
            const name = document.getElementById("name");
            const nickname = document.getElementById("nickname");
            const phone = document.getElementById("phone");
            // 추가 필요한 필드...


            // 닉네임이 제공되었는지 확인
            if (nickname.value === "") {
                alert("닉네임을 입력해주세요.");
                event.preventDefault();
                return false;
            }

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

            var pwReg = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,10}$/;
            if (!pwReg.test(pw.value)) {
                alert("패스워드는 공백 없이 영문, 숫자 및 특수문자의 조합으로 8~10자로 설정해주세요.");
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



        function fn_nickCheck(){
            const nickname = $("#nickname").val();

            // 공백없이 영문, 숫자 조합 6자 이상을 체크하는 정규식
            const regExp = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6}$/;

            if(!regExp.test(nickname)){
                alert("공백 없이 영문, 숫자 조합 6자로 입력해주세요.");
                $("#nickname").val("");
                return;
            }

            const data = {"nickname" : nickname}
            console.log(data);
            $.ajax({
                url : "/nickCheck",
                type : "POST",
                dataType: "json",
                data: nickname,
                contentType: 'application/json',
                success : function (data){
                    if (data == 1){
                        alert("중복된 닉네임입니다");
                    } else if (data == 0 ){
                        isNicknameChecked = true;  // 중복 체크 완료
                        alert("사용 가능한 닉네임 입니다")
                    }
                }
            })
        }


        function fn_register() {
            if (!isNicknameChecked) {
                alert("닉네임 중복 확인을 해주세요.");
                event.preventDefault()
                return;
            } else{
                updateBirthday();
                checkSubmitHandler(event)
            }

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
                    <h1 style="text-align: center">회원가입</h1>
                    <hr class="hr" />
                    <form action="/signUp" method="post">
                        <input type="text" id="birthdate" name="birthdate" style="display: none;">


                        <div class="my-4 row align-items-baseline">
                            <label for="name" class="col-sm-2 col-form-label fw-bold text-end">이름<SUP
                                    style="color: #FF4379; font-size: 18px;">*</SUP></label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" name="name" id="name" value="${userName}" required="required" readonly>
                            </div>
                        </div>

                        <div class="my-4 row align-items-baseline">
                            <label for="email" class="col-sm-2 col-form-label fw-bold text-end">email<SUP
                                    style="color: #FF4379; font-size: 18px;">*</SUP></label></label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" value="${userEmail}" name="email" id="email" readonly>
                            </div>
                        </div>

                        <div class="my-4 row align-items-baseline">
                            <label for="nickname" class="col-sm-2 col-form-label fw-bold text-end">아이디<SUP
                                    style="color: #FF4379; font-size: 18px;">*</SUP></label>
                            <div class="col-sm-8 d-flex">
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" name="nickname" id="nickname" required="required" placeholder="공백없이 영문/숫자 6자">
                                </div>
                                <div class="col-sm-2">
                                    <button type="button" class="btn btn-primary w-100" onclick="fn_nickCheck()">중복 확인</button>
                                </div>
                            </div>
                        </div>


                        <div class="my-4 row align-items-baseline">
                            <label for="password" class="col-sm-2 col-form-label fw-bold text-end">비밀번호<SUP
                                    style="color: #FF4379; font-size: 18px;">*</SUP></label>
                            <div class="col-sm-8">
                                <input type="password" class="form-control" name="password" id="password" required="required" placeholder="공백없이 영문/숫자 및 특수문자 포함 8~10자">
                            </div>
                        </div>

                        <div class="mmy-4 row align-items-baseline">
                            <label for="passwordCheck" class="col-sm-2 col-form-label fw-bold text-end">비밀번호확인<SUP
                                    style="color: #FF4379; font-size: 18px;">*</SUP></label>
                            <div class="col-sm-8">
                                <input type="password" class="form-control" name="passwordCheck" id="passwordCheck" required="required" placeholder="공백없이 영문/숫자 및 특수문자 포함 8~10자">
                            </div>
                        </div>



                        <div class="my-4 row align-items-center ">
                            <label class="col-sm-2 col-form-label fw-bold text-end">생년월일<SUP
                                    style="color: #FF4379; font-size: 18px;">*</SUP></label>
                            <div class="col-sm-9 d-flex align-items-center">
                                <div class="col-4"	>
                                    <select class="form-select text-center" id="year" required>
                                        <option value=""></option>
                                    </select>
                                </div>
                                <span class="birthday-text">년</span>
                                <div class="col-3"	>
                                    <select class="form-select text-center" id="month" required>
                                        <option value=""></option>
                                    </select>
                                </div>
                                <span class="birthday-text">월</span>
                                <div class="col-3 "	>
                                    <select class="form-select text-center" id="day" required>
                                        <option value=""></option>
                                    </select>
                                </div>
                                <span class="birthday-text">일</span>
                            </div>
                        </div>

                        <div class="row my-4 align-items-baseline">
                            <label for="userType" class="col-sm-2 col-form-label fw-bold text-end">회원구분<SUP
                                    style="color: #FF4379; font-size: 18px;">*</SUP></label>
                            <div class="col-sm-8">
                                <select class="form-select" name="userType" id="userType">
                                    <option value="1">관리자</option>
                                    <option value="2">교육자</option>
                                    <option value="3">학습자</option>
                                    <option value="4">일반인</option>
                                </select>
                            </div>
                        </div>



                        <div class="my-4 row align-items-baseline">
                            <label for="address" class="col-sm-2 col-form-label fw-bold text-end">주소</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" name="address" id="address">
                            </div>
                        </div>

                        <div class="my-4 row align-items-baseline">
                            <label for="phone" class="col-sm-2 col-form-label fw-bold text-end">전화번호<SUP
                                    style="color: #FF4379; font-size: 18px;">*</SUP></label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" name="phone" id="phone" placeholder="(-)제외한 지역번호 포함 번호">
                            </div>
                        </div>

                        <div class="row my-4 align-items-baseline">
                            <label class="col-sm-2 col-form-label fw-bold text-end">성별<SUP
                                    style="color: #FF4379; font-size: 18px;">*</SUP></label>
                            <div class="col-sm-8 d-flex">

                                <div class="col-sm-3 form-check">
                                    <input class="form-check-input" type="radio" name="gender" id="male"value="0" checked>
                                    <label class="form-check-label" for="male">남자</label>
                                </div>

                                <div class="col-sm-3 form-check">
                                    <input class="form-check-input" type="radio" name="gender" id="female"value="1" >
                                    <label class="form-check-label" for="female">여자</label>
                                </div>
                            </div>
                        </div>

                        <div class="row my-4 align-items-baseline">
                            <label class="col-sm-2 col-form-label fw-bold text-end">광고 메세지 수신</label>
                            <div class="col-sm-8 d-flex">

                                <div class="col-sm-3 form-check">
                                    <input class="form-check-input" type="radio" name="consent1" id="consent1" value="1" >
                                    <label class="form-check-label" for="consent1">EMAIL</label>
                                </div>

                                <div class="col-sm-3 form-check">
                                    <input class="form-check-input" type="radio" name="consent2"id="consent2" value="1" >
                                    <label class="form-check-label" for="consent2">SMS</label>
                                </div>
                            </div>
                        </div>




                        <hr class="hr" />



                        <div class="d-flex justify-content-between">
                            <div class="col-6 mb-3" >
                                <button type="submit" class="form-control btn btn-primary w-100" onclick="fn_register()">등록</button>
                            </div>
                            <div class="col-3 mb-3">
                                <button type="reset" class="btn btn-outline-secondary w-100" onclick="return confirm('입력하신 내용이 초기화됩니다. 정말 진행하시겠습니까?')">초기화</button>
                            </div>
                            <div class="col-2 mb-3">
                                <button type="button" class="btn btn-outline-secondary w-100" onclick="location.href='/login'">취소</button>
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