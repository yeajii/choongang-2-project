<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>

<html>
<head>
    <%@ include file="/WEB-INF/components/Header.jsp"%>

    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
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
                <H1>공지 수정</H1>
            </div>
            <div>
                <form id="myForm" action="noticeUpdate" method="post" enctype="multipart/form-data">
                    <div class="my-4 row align-items-baseline">
                        <label for="title" class="col-sm-2 col-form-label fw-bold text-end"
                               style="font-size: 20px;">제목</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" id="title" name="title" value="${board.title}" required>
                        </div>
                        <div class="my-4 row align-items-baseline">
                            <label for="content" class="col-sm-2 col-form-label fw-bold text-end"
                                   style="font-size: 20px;">내용</label>
                            <div class="col-sm-8">
                                <textarea id="content" name="content" class="form-control" required>${board.content}</textarea>
                            </div>
                        </div>

                        <div class="my-4 row align-items-baseline ">
                            <input type="hidden" id="isPinnedHidden" name="isPinned" value="${board.isPinned}">
                            <label for="isPinned" class="col-sm-2 col-form-label fw-bold text-end" style="font-size: 20px;"> 상단에 고정 </label>
                            <div class="col-sm-8">
                                <input type="checkbox" id="isPinned" name="isPinned" ${board.isPinned ? "checked" : ""} onchange="updateIsPinnedValue()">
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
                        <button id="uploadBtn" type="button" style="width: 300px; margin-left: 200px;">파일 선택 및 업로드</button>
                        <input type="file" id="file" name="file" multiple style="display: none;">
                    </div>
            </div>

            <div class="container row justify-content-center my-5">

                <button type="button" id="saveButton"  class="btn btn-primary col-4 px-3 mx-2"
                        style="background: #52525C; border: none">수정하기
                </button>
                <button type="reset" class="btn btn-primary col-4 px-3 mx-2" onclick="window.location.href='noticeDetail?id=${board.id}'">취소</button>

            </div>
            </form>

        </div>
    </div>
    </div>
</main>
</body>

<script>
    function updateIsPinnedValue() {
        var checkbox = document.getElementById('isPinned');
        var hiddenInput = document.getElementById('isPinnedHidden');
        if (checkbox.checked) {
            hiddenInput.value = 1;
        } else {
            hiddenInput.value = 0;
        }
    }


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

            var dropZoneFiles = document.getElementById('file').files;
            var form = document.getElementById('myForm'); // form 요소를 변수에 저장

            var formData = new FormData(form); // 저장한 form 변수 사용
            for (var i = 0; i < dropZoneFiles.length; i++) {
                formData.append("files", dropZoneFiles[i]);
            }


            $.ajax({
                url: "noticeUpdate?id=${board.id}&currentPage=${currentPage}",
                type: "POST",
                data: formData,
                processData: false,
                contentType: false,
                success: function (data) {
                    console.log(data);
                    window.location.href = 'noticeDetail?id=${board.id}';
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

    function handleFileSelect(evt) {
        evt.stopPropagation();
        evt.preventDefault();

        var files = evt.dataTransfer.files; // FileList object.

        // files is a FileList of File objects. List some properties.
        displayFileInfo(files);

        // 파일이 드롭되었으므로 'drag_message'를 숨깁니다.
        document.getElementById("drag_message").style.display = "none";
    }


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
            document.getElementById("file_info").innerHTML = result;

            var cancelBtns = document.getElementsByClassName('cancelBtn');
            for (var i = 0; i < cancelBtns.length; i++) {
                cancelBtns[i].addEventListener('click', function (event) {
                    event.preventDefault();
                    document.getElementById("file_info").innerHTML = "";
                    // 파일이 취소되었으므로 'drag_message'를 다시 보여줍니다.
                    document.getElementById("drag_message").style.display = "";
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

        // '수정하기' 버튼 클릭 시 처리
        $('#saveButton').on('click', function () {
            // 폼 데이터를 서버로 전송하는 부분
            $.ajax({
                url: 'noticeUpdate?id=${board.id}&currentPage=${currentPage}',
                type: 'POST',
                data: formData,
                processData: false,
                contentType: false,
                success: function (data) {
                    console.log('Success:', data);
                    window.location.href = 'noticeDetail?id=${board.id}';
                },
                error: function (error) {
                    console.error('Error:', error);
                    // 에러 시 원하는 동작을 추가하세요.
                }
            });
        });

</script>
</html>
