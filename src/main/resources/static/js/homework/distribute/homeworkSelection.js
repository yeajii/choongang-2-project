function updateSelectedHomeworkIdList(stateManager) {
    stateManager.selectedHomeworkIdList = []; // 리스트 초기화

    $('#homework-table tr').each(function() {
        const checkbox = $(this).find('input[type="checkbox"]');
        if (checkbox.prop('checked')) { // 체크박스가 체크된 경우
            const homeworkId = $(checkbox).attr('id').split('-')[1]; // 체크박스의 id에서 숙제 ID 추출
            stateManager.selectedHomeworkIdList.push(homeworkId);
        }
    });
}

// 숙제 선택 이벤트
export default function homeworkSelection(stateManager) {
    $('#homework-table').on('change', 'input[type="checkbox"]', function() {
        updateSelectedHomeworkIdList(stateManager);
    });

    $('#homeworkTitles').on('change', function() {
        var selectedTitle = $(this).val();

        // 테이블의 모든 행을 숨깁니다.
        $('#homework-table tbody tr').hide();

        // 체크박스의 상태를 초기화합니다.
        $('#homework-table tbody tr input[type="checkbox"]').prop('checked', false);

        if(selectedTitle === '') {
            // 선택된 숙제명이 없으면 모든 행을 보여줍니다.
            $('#homework-table tbody tr').show();
        } else {
            // 선택된 숙제명에 해당하는 행만 보여줍니다.
            $('#homework-table tbody tr').each(function() {
                const rowTitle = $(this).find('td:nth-child(2)').text();
                if(rowTitle === selectedTitle) {
                    $(this).show();
                }
            });
        }
    });
}