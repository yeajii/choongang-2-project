<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/WEB-INF/components/Header.jsp"%>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <title>Title</title>
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
            <form action="noticeInsert" method="post" enctype="multipart/form-data">
                <H1>공지 등록</H1>

                <label for="title">제목</label>
                <input type="text" id="title" name="title" required>

                <label for="content">내용</label>
                <textarea id="content" name="content" required></textarea>

                <div style="display: flex; align-items: center;">
                    <input type="hidden" id="isPinnedHidden" name="isPinned" value="0">
                    <input type="checkbox" id="isPinned" name="isPinned">
                    <label for="isPinned">상단에 고정</label>
                </div>

                <div style="display: flex; align-items: center;">
                    <input type="radio" id="immediate" name="publishOption" value="immediate" checked>
                    <label for="immediate">즉시 등록</label>
                </div>

                <div style="display: flex; align-items: center;">
                    <input type="radio" id="scheduled" name="publishOption" value="scheduled">
                    <label for="scheduled">원하는 날짜에 등록</label>
                </div>

                <label for="publishDate" style="display: none;" id="publishDateLabel">게시일자</label>
                <input type="datetime-local" id="publishDate" name="publishDate" style="display: none;"><!-- 게시일자 입력 필드 추가 -->

                <div id="drop_zone" style="width: 300px; height: 200px; border: 1px solid black;">파일을 여기에 드래그하세요.(최대 30MB)</div>
                <button id="uploadBtn" type="button">파일 선택 및 업로드</button>
                <input type="file" id="file" name="file" multiple style="display: none;">

                <input type="submit" value="등록">
            </form>

        </div>
    </div>
</main>
</body>
<script>
    $(document).ready(function() {
        $('input[type=radio][name=publishOption]').change(function () {
            if (this.value == 'scheduled') {
                // 라디오 버튼이 '원하는 날짜에 게시'에 체크되면 '게시일자' 입력 필드와 라벨을 보여줌
                $('#publishDate').show();
                $('#publishDateLabel').show();
                alert('이 경우 해당 글을 게시일까지 삭제 및 수정 할 수 없습니다'); // 알림창 띄우기
            } else if (this.value == 'immediate') {
                // 라디오 버튼이 '즉시 등록'에 체크되면 '게시일자' 입력 필드와 라벨을 다시 숨김
                $('#publishDate').hide();
                $('#publishDateLabel').hide();

                // 현재 날짜와 시간을 얻어옴
                var now = new Date();
                var year = now.getFullYear();
                var month = ("0" + (now.getMonth() + 1)).slice(-2);
                var day = ("0" + now.getDate()).slice(-2);
                var hours = ("0" + now.getHours()).slice(-2);
                var minutes = ("0" + now.getMinutes()).slice(-2);

                // HTML datetime-local input에 현재 날짜와 시간을 설정
                $('#publishDate').val(year + "-" + month + "-" + day + "T" + hours + ":" + minutes);
            }
        });

        var dropZone = document.getElementById('drop_zone');
        var fileInfo = "";

        dropZone.ondragover = function (event) {
            event.preventDefault();
            this.style.background = "#cccccc";
        };

        dropZone.ondragleave = function (event) {
            event.preventDefault();
            this.style.background = "white";
        };

        dropZone.ondrop = function (event) {
            event.preventDefault();
            this.style.background = "white";

            var files = event.dataTransfer.files;
            if (handleFiles(files)) {
                fileInfo += displayFileInfo(files);
                document.getElementById("drop_zone").innerHTML = fileInfo;
            }
        };

        function displayFileInfo(files) {
            var result = "";
            for (var i = 0; i < files.length; i++) {
                var file = files[i];
                var sizeInMB = (file.size / 1000000).toFixed(2); // 파일 크기를 메가바이트로 변환
                result += "File " + (i + 1) + ":<br>"; // 파일 번호
                result += "Name: " + file.name + "<br>";
                result += "Size: " + sizeInMB + " MB<br><br>";
            }
            return result;
        }

        function handleFiles(files) {
            for (var i = 0; i < files.length; i++) {
                var file = files[i];
                var sizeInMB = (file.size / 1000000).toFixed(2); // 파일 크기를 메가바이트로 변환
                if (sizeInMB > 30) { // 파일 크기가 30MB를 초과하는지 체크
                    alert('파일 크기가 30MB를 초과하였습니다. 다른 파일을 선택해주세요.');
                    return false;
                }
            }
            return true;
        }

        document.getElementById('uploadBtn').addEventListener('click', function() {
            document.getElementById('file').click();
        });

        document.getElementById('file').addEventListener('change', function() {
            if (handleFiles(this.files)) {
                fileInfo += displayFileInfo(this.files);
                document.getElementById("drop_zone").innerHTML = fileInfo;
            }
        });
    })
    $(document).ready(function() {
        // 체크박스 상태 감지
        $('#isPinned').change(function() {
            if($(this).is(":checked")) {
                // 체크박스가 체크되면 hidden input의 value를 1로 변경
                $('#isPinnedHidden').val('1');
            } else {
                // 체크박스가 체크 해제되면 hidden input의 value를 0으로 변경
                $('#isPinnedHidden').val('0');
            }
        });

    });
</script>
</html>
