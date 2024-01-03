function eduList() {
    window.location.href = '/lookup/board/listEdu';
}

function eduUpdate(id) {
    window.location.href = '/admin/resource/updateEduForm?id=' + id;
}

function eduDelete(id) {

    var isConfirmed = confirm("정말로 삭제하시겠습니까?");
    if (isConfirmed) {
        console.log("아작스 실행전");
        $.ajax({
            url: '/admin/resource/deleteEdu',
            method: 'DELETE',
            data: {
                id: id
            },
            success: function (response) {
                if (response.result > 0) {
                    alert("교육자료 삭제성공!");
                    window.location.href = '/lookup/board/listEdu';
                } else {
                    alert("교육자료 삭제실패..");
                }
            },
            error: function (error) {
                console.log(error);
            }
        });
    } else {
        return null;
    }
}
