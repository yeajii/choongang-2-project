<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
  <%@ include file="/WEB-INF/components/Header.jsp"%>
  <style>
    .main-container {
      padding: 20px;
      background-color: #fff;
      border: 1px solid #dee2e6;
      border-radius: 0.25rem;
      margin-top: 50px;
    }
    .post-controls {
      display: flex;
      justify-content: flex-start; /* 왼쪽 정렬 */
      font-size: 0.8rem; /* 폰트 크기를 작게 조정 */
    }

    .post-controls button, .post-controls a {
      margin-right: 10px; /* 버튼 사이의 간격 조정 */
    }
  </style>
  <title>Title</title>
</head>
<body>

<%@ include file="/WEB-INF/components/TopBar.jsp"%>
<main>
  <%@ include file="/WEB-INF/components/LookupSidebar.jsp"%>

  <form action="FAQUpdate" method="post">

    <div class="container main-container">
      <div class="post-content">
        <h1>${board.title}</h1>
        <p>작성자 : ${board.name}</p>
        <p>작성일 : <fmt:formatDate value="${board.createdAt}" type="date" pattern="YY/MM/dd"/></p>
        <p>조회수: ${board.readCount}</p>
        <p>${board.content}</p>
      </div>

      <div class="post-controls">
        <button type="button" class="btn btn-primary2" onclick="location.href='/lookup/board/FAQBoardList'">목록으로</button>
      </div>
    </div>
  </form>
  <form name="updateForm">
    <input type="hidden" name="target_id" value="${board.id}">
    <input type="hidden" name="title" id="${board.title}">
    <input type="hidden" name="content" id="${board.content}">
    <input type="hidden" name="content" id="${board.createdAt}">

  </form>
</main>
</body>
</html>

<script>
  function deleteFAQ(id, currentPage) {
    if (confirm('정말로 이 게시글을 삭제하시겠습니까?')) {
      location.href=`FAQDelete?id=${board.id}&currentPage=${currentPage}`;
    }
  }

</script>
