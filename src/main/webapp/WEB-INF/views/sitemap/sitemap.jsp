<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <title>사이트맵</title>
    <%@ include file="/WEB-INF/components/Header.jsp"%>
    <style>
        .use-case{
            height: auto;
        }
        h1 {
            color: black;
            font-size: 28px;
            font-weight: bold !important;
            /*word-wrap: break-word;*/
            text-align: start;
        }
        h2 {
            color: #171717;
            font-size: 16px;
            font-weight: 800;
            word-wrap: break-word
        }
        h3 {
            color: #6D6A6A;
            font-size: 20px;
            font-weight: 600;
            word-wrap: break-word
        }
        h4 {
            color: black;
            font-size: 15px;
            font-weight: 500;
            word-wrap: break-word
        }
    </style>
</head>
<body>
    <%@ include file="/WEB-INF/components/TopBar.jsp"%>
    <main>
        <div class="container d-flex justify-content-center my-5" >
            <div class="container  w-75">
                <h1 >이용 안내</h1>
                <h2>사용자 유형별로 다음과 같은 역할을 수행할 수 있습니다</h2>
                <div class="d-flex justify-content-center my-3">
                    <img class="use-case img-fluid" src="/asset/usecase.png">
                </div>


            </div>

        </div>
    </main>
    <%@ include file="/WEB-INF/components/Footer.jsp"%>
</body>
</html>
