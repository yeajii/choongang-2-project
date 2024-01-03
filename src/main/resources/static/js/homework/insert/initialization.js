// initialization.js
import {initializeDate} from './dateUtils.js';
import { initializeAutocomplete } from './autocomplete.js';
import {initializePagination} from "./pagination.js";
export function initializePage() {
    $('.detail-btn').on('click', function() {
        var row = $(this).closest('tr');
        var homeworkId = row.find('td:first').text();
        console.log(homeworkId)
        // 숙제 상세정보를 가져오는 AJAX 요청을 실행
        $.ajax({
            url: '/homework/getHomework/' + homeworkId,
            type: 'GET',
            success: function(response) {
                // 응답을 모달창에 출력
                $('.modal-body').html(response);
                var modal = new bootstrap.Modal(document.getElementById('modal'));
                modal.show();
            }
        });
    });
    initializeDate();
    initializePagination();
    initializeAutocomplete();
}