<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <%@ include file="/WEB-INF/components/Header.jsp"%>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <title>수정</title>
  <style>
    body {
      font-family: Arial, sans-serif;
    }

    form {
      max-width: 600px;
      margin: auto;
    }

    label {
      display: block;
      margin-top: 20px;
    }

    input[type="text"],
    textarea {
      width: 100%;
      padding: 10px;
      margin-top: 5px;
      box-sizing: border-box;
    }

    #drop_zone {
      margin-top: 20px;
      padding: 10px;
      text-align: center;
      width: 500px;
      height: 200px;
      border: 1px solid black;
    }

    #uploadBtn {
      display: block;
      margin-top: 20px;
    }

    input[type="submit"] {
      margin-top: 20px;
      padding: 10px 20px;
      background-color: #007BFF;
      color: white;
      border: none;
      cursor: pointer;
    }

    input[type="submit"]:hover {
      background-color: #0056b3;
    }
  </style>
</head>
<body>
<%@ include file="/WEB-INF/components/TopBar.jsp"%>
<main>
  <%@ include file="/WEB-INF/components/AdminSidebar.jsp"%>
  <div class="container col-9 justify-content-center align-items-center mb-2 p-3 pt-0">
    <div class="container table-container p-4">
      <form action="FAQUpdate?id=${board.id}&currentPage=${currentPage}" method="post" enctype="multipart/form-data">
        <H1>수정등록</H1>


        <label for="title">제목</label>
        <input type="text" id="title" name="title" value="${board.title}" required>

        <label for="content">내용</label>
        <textarea id="content" name="content" required>${board.content}</textarea>

        <input type="submit" value="수정">
      </form>
    </div>
  </div>
</main>
</body>
</html>