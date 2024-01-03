<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2023-12-15
  Time: 오전 11:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>

<html>
<head>
    <%@ include file="/WEB-INF/components/Header.jsp"%>
    <title>Title</title>
</head>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>

    var isEmailchecked = false;

    function fn_nickCheck(){
        const email = $("#email").val();
        const data = {"email" : email}
        console.log(data);
        $.ajax({
            url : "/emailCheck",
            type : "POST",
            dataType: "json",
            data: email,
            contentType: 'application/json',
            success : function (data){
                if (data == 1){
                    alert("중복된 email입니다");
                } else if (data == 0 ){
                    isEmailchecked = true;  // 중복 체크 완료
                    alert("사용 가능한 email 입니다")
                }
            }
        })
    }

    function fn_register() {
        // 필수 checkbox들을 가져옴
        var terms1 = document.getElementById("terms1");
        var terms2 = document.getElementById("terms2");
        var terms5 = document.getElementById("terms5");

        if (!isEmailchecked) {
            alert("email 중복 확인을 해주세요.");
            event.preventDefault();
        }
        else if (!terms1.checked || !terms2.checked || !terms5.checked) {
            alert("필수 약관에 동의해주세요.");
            event.preventDefault();
        }
        else {
            // 필수 항목에 모두 동의하고 이메일 중복 확인도 완료한 경우 폼 제출
            document.getElementById('mailAuth').submit(); // 'formId'는 실제 폼의 id로 변경해주세요.
        }
    }

    function checkInput(input) {
        const regex = /[^\u1100-\u11FF\u3130-\u318F\uAC00-\uD7AF]/g;
        if (regex.test(input.value)) {
            input.value = input.value.replace(regex, '');
            alert("한글만 입력해주세요.");
        }
    }
</script>
<body>
<%@ include file="/WEB-INF/components/TopBar.jsp"%>
<main>
    <div class="container col-4 justify-content-start border mt-3 p-5">
        <form id="mailAuth" action="/emailAuth" method="post">
            <div class="mt-2 mb-5">
                <div class="justify-content-center pt-3 pb-3">
                    <h3>약관 동의</h3>
                </div>
                <h6>
                    <input type="checkbox" id="terms1" name="terms1">
                    <label for="terms1">이용 약관 <span style="color: red;">[필수]</span></label>
                </h6>
                <h6>
                    <input type="checkbox" id="terms2" name="terms2">
                    <label for="terms2">개인정보 필수 항목에 대한 처리 및 이용 <span style="color: red;">[필수]</span></label>
                </h6>
                <h6>
                    <input type="checkbox" id="terms3" name="terms3">
                    <label for="terms3">개인정보 선택항목에 대한 처리 및 이용 [선택]</label>
                </h6>
                <h6>
                    <input type="checkbox" id="terms4" name="terms4">
                    <label for="terms4">개인정보 마케팅 및 광고 활용 [선택]</label>
                </h6>
                <h6>
                    <input type="checkbox" id="terms5" name="terms5">
                    <label for="terms5">개인정보의 위탁 <span style="color: red;">[필수]</span></label>
                </h6>
            </div>

            <hr>
            <div class="mt-2 mb-2">
                <div class="justify-content-center pt-3 pb-3">
                    <h3>이메일 인증 </h3>
                </div>
                <div class="justify-content-center pb-1">
                    <h6>입력하신 EMAIL로 인증번호를 보냅니다</h6>
                </div>
                <div class="justify-content-start pb-3">
                    <label for="name" class="form-label mb-2">이름</label>
                    <input type="text" class="form-control" name="name" id ="name" required oninput="checkInput(this)">
                </div>

                <div class="justify-content-start pb-3">
                    <label for="email" class="form-label mb-2">Email</label>
                    <input type="text" class="form-control" name="email" id ="email" required>
                </div>
                <div class="col-lm-2">
                    <button type="button" class="btn btn-primary w-100" onclick="fn_nickCheck()">중복 확인</button>
                </div>
                <div>
                <div class="justify-content-center pt-3 pb-3" align="center">
                    <button type="submit" class="btn btn-primary" onclick="fn_register()">다음</button>
                    <button type="reset" class="btn btn-primary" onclick="">초기화</button>
                    <button type="button" class="btn btn-primary" onclick="window.history.back()">취소</button>
                </div>
                </div>
            </div>
        </form>
    </div>
</main>

</body>
</html>
