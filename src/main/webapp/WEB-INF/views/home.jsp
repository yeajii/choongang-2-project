<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <%@ include file="/WEB-INF/components/Header.jsp"%>
    <title>Title</title>
    <script>
        if (window.location.search.includes('error')) {
            alert('접근할 수 없는 페이지입니다.');
        }
        $(document).ready(function() {
            const next=document.querySelector('#next')
            const prev=document.querySelector('#prev')

            function handleScrollNext (direction) {
                const cards = document.querySelector('.card-content')
                cards.scrollLeft=cards.scrollLeft += window.innerWidth / 2 > 600 ? window.innerWidth /2 : window.innerWidth -100
            }

            function handleScrollPrev (direction) {
                const cards = document.querySelector('.card-content')
                cards.scrollLeft=cards.scrollLeft -= window.innerWidth / 2 > 600 ? window.innerWidth /2 : window.innerWidth -100
            }

            next.addEventListener('click', handleScrollNext)
            prev.addEventListener('click', handleScrollPrev)
        });
    </script>
    <style>
        .container_top {
            display: flex;
            flex-wrap: wrap;
            justify-content: space-between;
            /*align-items: center;*/
        }
        .carousel-item {
            height: 250px;
            object-fit: cover;
        }
        .carousel-item img{
            height: 250px;
            object-fit: cover;

        }

        h2 {
            color: #0C4DA2;
            font-size: 24px;
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
        td {
            text-align: center  ;
        }
        .section-title {
            color: #6D6A6A;
            font-size: 20px;
            font-weight: 600;
            word-wrap: break-word
        }
    </style>

    <style>

        .card {
            width: 250px;
            min-width: 250px;
            height:auto;
            background:#fff;
            /*border-radius:30px;*/
            position:relative;
            z-index:10;
            margin:15px;
            min-height:356px;
            cursor:pointer;
            transition: all .25s ease;
            box-shadow: 0px 0px 0px 0px rgba(0,0,0, .08);
        }

        .card:hover {
            transform:translate(0, -10px);
            box-shadow: 0px 17px 35px 0px rgba(0,0,0,.07);
        }

        .card .card-text {
            padding: 20px;
        }

        p {
            font-size: .8rem;
            opacity: .6;
            margin-top: 10px;
        }

        .card .card-img {
            margin: 25px 0;
            /*padding: 25px 0;*/
            display:flex;
            align-items: center;
            justify-content:center;
            transition: all .35s ease-out;
        }

        .card img {
            height:180px;
        }

        .card img.blur {
            position:absolute;
            filter:blur(15px);
            z-index:-1;
            opacity:.40;
            transition: all .35s ease-out;
        }

        .card:hover .card-img {
            transform:scale(1.10);
        }

        .card:hover .card-img img.blur {
            transform:translate(-100px,35px) scale(.85);
            opacity:.25;
            filter:blur(20px);
        }

        .card-content {
            display:flex;
            align-items:center;
            justify-content:flex-start;
            width:100%;
            overflow:auto;
            scroll-behavior:smooth;
        }

        .card-content::-webkit-scrollbar {
            height:0px;
        }

        .card-content:after {
            display:block;
            min-width:20px;
            position:relative;
        }

        .btn{
            min-width:60px;
            margin:auto 10px;
            height:60px;
            border-radius:20px;
            background:rgb(242,243,248);
            border:0px;
            outline:none;
            cursor:pointer;
            z-index:9999;
            box-shadow: 0px 0px 0px 0px rgba(0,0,0,.08);
            transition: all .25s ease;
        }

        .btn:hover{
            box-shadow: 0px 17px 35px 0px rgba(0,0,0,.07);
        }

        .btn i {
            font-size:1.2rem;
        }

        .slider {
            display:flex;
            align-items:center;
            justify-content:center;
            overflow:hidden;
        }

    </style>
</head>
<body>
    <%@ include file="/WEB-INF/components/TopBar.jsp"%>
    <main>
        <div class="container">
            <div id="carouselExampleDark" class="carousel carousel-dark slide">
                <div class="carousel-indicators">
                    <button type="button" data-bs-target="#carouselExampleDark" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
                    <button type="button" data-bs-target="#carouselExampleDark" data-bs-slide-to="1" aria-label="Slide 2"></button>
                    <button type="button" data-bs-target="#carouselExampleDark" data-bs-slide-to="2" aria-label="Slide 3"></button>
                </div>
                <div class="carousel-inner justify-content-center">
                    <div class="carousel-item active" data-bs-interval="2000">
                        <img src="https://file.tygem.com/updata/ckimages/20219/92023846x298.jpg" class="d-block w-100" alt="...">
                    </div>
                    <div class="carousel-item" data-bs-interval="2000">
                        <img src="https://mgameimage.gscdn.com/mgamezzang/games/baduk_2009/lecture/091215_banner_2.jpg" class="d-block w-100" alt="...">
                    </div>
                    <div class="carousel-item">
                        <img src="http://file.tygem.com/updata/ckimages/20218/155327201.jpg" class="d-block w-100" alt="...">
                        </div>
                </div>
                <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleDark" data-bs-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Previous</span>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleDark" data-bs-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Next</span>
                </button>
            </div>
        </div>
        <div class="d-flex ">
            <div id="main-content" class="container">
                <div class="">
                    <div class=" container_top mt-5">
                        <div class=" col-5 ">
                            <label class="section-title"> 공지사항</label>
                            <hr class="">
                            <table  class="table table-md text-center p-3 border">
                                <thead>
                                <tr>
                                    <th style="width: 80%;text-align: start;padding-left: 20px  ;">제목</th>
                                    <th style="width: 20%">작성일</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="notice" items="${listnoticeBoard}" varStatus="st">
                                    <tr id="notice${st.index}">
                                        <input type="hidden" value="${notice.id}" id="id${st.index}">
                                        <td>
                                            <c:choose>
                                                <c:when test="${notice.isPinned eq true}">
                                                    <a href="noticeDetail?id=${notice.id}" style="font-weight: bold;">${notice.title}</a>
                                                </c:when>
                                                <c:otherwise>
                                                    <a href="noticeDetail?id=${notice.id}">${notice.title}</a>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td><fmt:formatDate value="${notice.createdAt}" type="date" pattern="YY/MM/dd"/></td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>

                        <div class=" col-6" >
                            <label  class="section-title"> 인기 교육자료</label>
                            <hr class="">
                            <div class="d-flex justify-content-around">
                                <iframe width="45%" height="170px" src="https://www.youtube.com/embed/sSi4Nf0goLo"
                                        title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write;
                                        encrypted-media; gyroscope; picture-in-picture" allowfullscreen>
                                </iframe>
                                <iframe width="45%" height="170px" src="https://www.youtube.com/embed/r2YIc57hUiE"
                                        title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write;
                                        encrypted-media; gyroscope; picture-in-picture" allowfullscreen>
                                </iframe>
                            </div>
                        </div>
                    </div>
                    <div class=" mt-5" >
                        <h3 class=""> 인기 게임</h3>
                        <hr class="">
                    </div>
                </div>
                <div class="my-3">
                    <link rel="stylesheet" href="https://maxst.icons8.com/vue-static/landings/line-awesome/line-awesome/1.3.0/css/line-awesome.min.css">
                    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;700&display=swap" rel="stylesheet">

                    <div class="slider" >
                        <button id="prev" class="btn">
                            <i class="las la-angle-left"></i>
                        </button>
                        <div class="card-content">
                            <c:forEach var="gameContent" begin="0" end="14" items="${subscribeGameList}">
                                <!-- Card -->
                                <div class="card">
                                    <div class="card-img">
                                        <img id="gameImg" alt="UpLoad Image" src="${pageContext.request.contextPath}/upload/gameContents/${gameContent.imageName}">
                                    </div>
                                    <div class="card-text">
                                        <h3>${gameContent.title}</h3>
                                        <p>${gameContent.content}<p>
                                    </div>
                                </div>
                                <!-- Card End -->
                            </c:forEach>
                        </div>
                        <button id="next" class="btn">
                            <i class="las la-angle-right"></i>
                        </button>
                    </div>
                </div>

            </div>
        </div>

    </main>

    <%@ include file="/WEB-INF/components/Footer.jsp"%>
</body>
</html>
