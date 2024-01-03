<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    body {
        position: relative; /* 부모 요소에 position: relative; 설정 */
    }

    .biz-page-sidebar {
        background-color: #171717;
        position: absolute; /* 사이드바에 position: static; 설정 */
        height: 100%; /* 사이드바의 높이를 부모 요소의 높이와 동일하게 설정 */
        overflow-y: auto; /* 사이드바의 내용이 화면을 넘어갈 경우 스크롤바를 표시 */
    }

    .my-page-nav-link {
        color: white;
        font-size: 16px;
        font-family: Noto Sans;
        font-weight: 600;
        word-wrap: break-word;
        padding: 20px 0;
    }

    .my-page-nav-item a {
        align-items: center;
    }

    .my-page-nav-link:hover {
        background-color: white; /* 마우스를 올렸을 때의 배경색 */
        color: #171717 !important;
    }

    .my-page-nav-link.active {
        background-color: white !important; /* 마우스를 올렸을 때의 배경색 */
        color: #171717
    }

    .title-bi {
        font-size: 20px;
    }
</style>
<div class="d-flex flex-column flex-shrink-0 p-0 sidebar biz-page-sidebar"
     style="width: 280px;">
    <ul class="nav flex-column mt-5 pt-4">
        <li class="nav-item my-page-nav-item">
            <div>
                <a href="/game/gameContentSelect"
                   class="nav-link my-page-nav-link px-3 ">
                    <i class=" title-bi bi bi-controller px-3"></i>
                    게임 콘텐츠 조회
                </a>
            </div>
        </li>
        <li class="nav-item my-page-nav-item">
            <div>
                <a href="/game/gameContent"
                   class="nav-link my-page-nav-link px-3 ">
                    <i class="title-bi bi bi-controller px-3"></i>
                    게임 콘텐츠 등록
                </a>
            </div>
        </li>
        <li class="nav-item my-page-nav-item">
            <div>
                <a href="/admin/resource/uploadForm"
                   class="nav-link my-page-nav-link px-3 ">
                    <i class="title-bi bi bi-pencil-square px-3"></i>
                    학습자료 등록
                </a>
            </div>
        </li>
        <li class="nav-item my-page-nav-item">
            <div>
                <a href="/admin/board/noticeBoardList"
                   class="nav-link my-page-nav-link px-3 ">
                    <i class="title-bi bi bi-chat-square-dots-fill px-3"></i>
                    공지 관리
                </a>
            </div>
        </li>
        <li class="nav-item my-page-nav-item">
            <div>
                <a href="/admin/board/QNABoardList"
                   class="nav-link my-page-nav-link px-3 ">
                    <i class="title-bi bi bi-chat-square-dots-fill px-3"></i>
                    Q&A 관리
                </a>
            </div>
        </li>
        <li class="nav-item my-page-nav-item">
            <div>
                <a href="/admin/board/FAQBoardList"
                   class="nav-link my-page-nav-link px-3 ">
                    <i class="title-bi bi bi-chat-square-dots-fill px-3"></i>
                    FAQ 관리
                </a>
            </div>
        </li>
        <li class="nav-item my-page-nav-item">
            <div>
                <a href="/admin/account/listSales"
                   class="nav-link my-page-nav-link px-3 ">
                    <i class="title-bi bi bi-graph-up-arrow px-3"></i>
                    매출 조회
                </a>
            </div>
        </li>
        <li class="nav-item my-page-nav-item">
            <div>
                <a href="/userList"
                   class="nav-link my-page-nav-link px-3 ">
                    <i class="title-bi bi-people-fill px-3"></i>
                    회원 조회
                </a>
            </div>
        </li>
    </ul>
</div>