<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <%@ include file="/WEB-INF/components/Header.jsp"%>
    <title>Title</title>
</head>
<style>
    #main-content {
        margin-left: 440px;
    }
    .date-text {
        font-size: 16px;
        font-weight: 600;
        margin-right: 20px;
        margin-left: 10px;
        text-align: center;
    }

    h1 {
        color: black;
        font-size: 32px;
        font-weight: 600;
        word-wrap: break-word;
        text-align: center;
    }
    .form-check-input[type=radio] {
        width: 20px;
        height: 20px;
        border-width: 2px;
    }

</style>

<body>
<%@ include file="/WEB-INF/components/TopBar.jsp"%>
<main>
    <div class="d-flex">
        <div class="col-second">
            <%@ include file="/WEB-INF/components/AdminSidebar.jsp"%>
        </div>
    </div>
    <div id="main-content" class="container p-5 col-10" style="border: 0px solid red;">
        <div class="container border my-4 py-3">
            <div class="container my-3 py-3" style="text-align: center">
                <H1>공지 등록</H1>
            </div>
            <div>
                <form id="myForm" action="noticeInsert" method="post" enctype="multipart/form-data">
                    <div class="my-4 row align-items-baseline">
                        <label for="title" class="col-sm-2 col-form-label fw-bold text-end"
                               style="font-size: 20px;">제목</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" id="title" name="title" required>
                        </div>
                    </div>
                    <div class="my-4 row align-items-baseline">
                        <label for="content" class="col-sm-2 col-form-label fw-bold text-end"
                               style="font-size: 20px;">내용</label>
                        <div class="col-sm-8">
                            <textarea id="content" name="content" class="form-control" required></textarea>
                        </div>
                    </div>

                    <div class="my-4 row align-items-baseline ">
                        <input type="hidden" id="isPinnedHidden" name="isPinned" value="0">
                        <label for="isPinned" class="col-sm-2 col-form-label fw-bold text-end" style="font-size: 20px;"> 상단에 고정 </label>
                        <div class="col-sm-8">
                            <input type="checkbox" id="isPinned" name="isPinned">
                        </div>
                    </div>
                    <div class="my-4 row align-items-baseline ">
                        <label for="immediate" class="col-sm-2 col-form-label fw-bold text-end"
                               style="font-size: 20px;">등록 일자 </label>
                        <div class="col-sm-8">
                            <div class="form-check">
                                <input class="form-check-input" type="radio" id="immediate" name="publishOption" value="immediate" checked>
                                <label class="form-check-label" for="immediate" style="font-size: 20px; font-weight: bold;">
                                    즉시 등록
                                </label>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" id="scheduled" name="publishOption" value="scheduled">
                                <label class="form-check-label" for="scheduled" style="font-size: 20px; font-weight: bold;">원하는 날짜에 등록
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="my-4 row align-items-baseline ">
                        <label for="publishDate" class="col-sm-2 col-form-label fw-bold text-end"
                               style="font-size: 20px; display: none;" id="publishDateLabel">게시일자</label>
                        <div class="col-sm-8">
                            <input type="datetime-local" id="publishDate" name="publishDate" style="display: none; width: 300px;"><!-- 게시일자 입력 필드 추가 -->
                        </div>
                    </div>

                    <div class="my-4 row align-items-baseline ">
                        <label for="title" class="col-sm-2 col-form-label fw-bold text-end"
                               style="font-size: 20px;">파일 업로드</label>
                        <div class="col-sm-8" id="drop_zone" style="width: 300px; height: 200px; border: 1px solid black;">
                            <div id="drag_message">파일을 여기에 드래그하세요.<p>(최대 30MB, 최대 1개)</p></div>
                            <div id="file_info"></div>
                        </div>
                        <div id="existingFiles"></div>
                        <button id="uploadBtn" type="button" style="width: 300px; margin-left: 204px;">파일 선택 및 업로드</button>
                        <input type="file" id="file" name="file" multiple style="display: none;">
                    </div>

            <div class="container row justify-content-center my-5">
                <button type="button" id="saveButton" class="btn btn-primary col-4 px-3 mx-2"
                        style="background: #52525C; border: none">저장하기
                </button>
                <button type="reset" class="btn btn-primary col-4 px-3 mx-2" onclick="window.location.href='noticeBoardList'">취소</button>
            </div>
            </form>
            </div>
        </div>
    </div>
</main>
</body>
<script>
    $(document).ready(function () {
        $('input[type=radio][name=publishOption]').change(function () {
            if (this.value == 'scheduled') {
                $('#publishDate').show();
                $('#publishDateLabel').show();
                alert('이 경우 해당 글을 게시일까지 삭제 및 수정 할 수 없습니다');
            } else if (this.value == 'immediate') {
                $('#publishDate').hide();
                $('#publishDateLabel').hide();

                var now = new Date();
                var year = now.getFullYear();
                var month = ("0" + (now.getMonth() + 1)).slice(-2);
                var day = ("0" + now.getDate()).slice(-2);
                var hours = ("0" + now.getHours()).slice(-2);
                var minutes = ("0" + now.getMinutes()).slice(-2);

                $('#publishDate').val(year + "-" + month + "-" + day + "T" + hours + ":" + minutes);
            }
        });

        document.getElementById('uploadBtn').addEventListener('click', function () {
            document.getElementById('file').click();
        });

        document.getElementById('file').addEventListener('change', function (event) {
            var files = event.target.files;
            if (handleFiles(files)) {
                displayFileInfo(files);
            }
        });

        document.getElementById('saveButton').addEventListener('click', function (event) {
            event.preventDefault();

            var formData = new FormData(document.getElementById('myForm'));

            var dropZoneFiles = document.getElementById('file').files;
            if (dropZoneFiles.length > 0) {
                for (var i = 0; i < dropZoneFiles.length; i++) {
                    formData.append("files", dropZoneFiles[i]);
                }
            }

            $.ajax({
                url: "/admin/board/noticeInsert",
                type: "POST",
                data: formData,
                processData: false,
                contentType: false,
                success: function (data) {
                    console.log(data);
                    window.location.href = 'noticeBoardList';
                },
                error: function (error) {
                    console.error(error);
                    // 에러 시 원하는 동작을 추가하세요.
                }
            });
        });

        var dropZone = document.getElementById('drop_zone');
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
                displayFileInfo(files);
                document.getElementById('file').files = files;
            }
        };

        function displayFileInfo(files) {
            var result = "";
            for (var i = 0; i < files.length; i++) {
                var file = files[i];
                var sizeInMB = (file.size / 1000000).toFixed(2);
                result += "File " + (i + 1) + ":<br>";
                result += "Name: " + file.name + "<br>";
                result += "Size: " + sizeInMB + " MB<br><br>";
                result += "<button class='cancelBtn' data-file-index='" + i + "'>X</button><br><br>";
            }
            document.getElementById("drop_zone").innerHTML = result;

            var cancelBtns = document.getElementsByClassName('cancelBtn');
            for (var i = 0; i < cancelBtns.length; i++) {
                cancelBtns[i].addEventListener('click', function (event) {
                    event.preventDefault();
                    document.getElementById("drop_zone").innerHTML = "";
                });
            }
        }

        function handleFiles(files) {
            if (files.length > 1) {
                alert('한 개의 파일만 선택해주세요.');
                return false;
            }
            var file = files[0];
            var sizeInMB = (file.size / 1000000).toFixed(2);
            if (sizeInMB > 30) {
                alert('파일 크기가 30MB를 초과하였습니다. 다른 파일을 선택해주세요.');
                return false;
            }
            return true;
        }

        $(document).ready(function () {
            $('#isPinned').change(function () {
                if ($(this).is(":checked")) {
                    $('#isPinnedHidden').val('1');
                } else {
                    $('#isPinnedHidden').val('0');
                }
            });
        });

        // '저장하기' 버튼 클릭 시 처리
        $('#saveButton').on('click', function() {
            // 폼 데이터를 서버로 전송하는 부분
            $.ajax({
                url: '/admin/board/noticeInsert',
                type: 'POST',
                data: formData,
                processData: false,
                contentType: false,
                success: function(data) {
                    console.log('Success:', data);
                    window.location.href = 'noticeBoardList';
                },
                error: function(error) {
                    console.error('Error:', error);
                    // 에러 시 원하는 동작을 추가하세요.
                }
            });
        });
    });
</script>
</html>
