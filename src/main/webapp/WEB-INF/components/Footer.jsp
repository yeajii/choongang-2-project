<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2023-12-05
  Time: 오후 4:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    footer {
        flex-shrink: 0;
        background-color:#003983;
    }
    #footer-icon {
        font-size: 20px;
        letter-spacing: 10px;
        color:white;
    }
    #footer-title {
        font-size: 20px;
        color: #F6D675;
    }
    #footer-cp {
        font-size: 13px;
        color: #9E9E9E;
        text-align: start;
    }
    #footer-team {
        font-size: 15px;
        color : #F8FCF4;
    }
    #footer-hr {
        color: white;
    }
    .footer-link {
        font-size: 15px;
        color: #9E9E9E;
    }
</style>
<footer class="py-3">
    <div class="container justify-content-center">
        <div class="row  row-cols-1">
            <div class="col-2"></div>
            <div class="col-10 row  row-cols-1">
                <div class="col-6 my-3">
                    <h3 id="footer-title">(주)진격의 거민</h3>
                    <span id="footer-team">팀원 : 황남오(팀장), 강한빛, 윤상엽, 이규현, 정송환, 차예지</span>
                    <div class="">
                        <span><a class="footer-link" href="#">이용약관</a></span>
                        <span><a class="footer-link" href="#">개인정보처리방침</a></span>
                        <span><a class="footer-link" href="#">저작권보호정책</a></span>
                    </div>
                </div>
                <div class="col-3">
                </div>
                <div class="col-3">
                    <div class="container my-3">
                        <h2 id="footer-icon">
                            <i class="bi bi-facebook"></i>
                            <i class="bi bi-instagram"></i>
                            <i class="bi bi-twitter-x"></i>
                            <i class="bi bi-youtube"></i>
                        </h2>
                    </div>

                </div>
                <hr id="footer-hr">
                <div class="container">
                    <h4 id="footer-cp" class="text-center bold">© Copyright 진격의거민. All Rights Reserved</h4>
                </div>

            </div>
        </div>

    </div>
</footer>

