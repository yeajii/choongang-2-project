import showNotification from '/js/utils/notification.js'; // 모듈을 불러옵니다.
export function saveEvaluate() {
    var evaluations = [];
    $('#distributedHomework-table tbody tr').each(function() {
        var idParts = $(this).attr('id').split('-');
        var homeworkId = idParts[1];
        var userId = idParts[3];
        var evaluation = $(this).find('select').val();
        evaluations.push({
            homeworkId: homeworkId,
            userId: userId,
            evaluation: evaluation
        });
    });

    $.ajax({
        url: '/homework/updateEvaluations',
        type: 'POST',
        dataType: "json",
        contentType: 'application/json',
        data: JSON.stringify(evaluations),
        success: function(response){
            showNotification(response.message,"success",3000)
            // 성공 메시지 표시 등의 추가 동작을 여기에 작성합니다.
        },
        error: function(jqXHR, textStatus, errorThrown) {
            const error = jqXHR.responseJSON;
            showNotification(error.message, "error", 3000);
        }
    });
}