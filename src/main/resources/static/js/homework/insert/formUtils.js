// formUtils.js
import showNotification from '/js/utils/notification.js'; // 모듈을 불러옵니다.
import {storeNotification }from '/js/utils/notificationManager.js';
export function validateInput(title, progress, deadline, content) {
    if(title.length < 2){
        return "숙제명은 2글자 이상 입력해야 합니다.";
    }

    if(progress < 0){
        return "숙제 진도는 0 이상의 숫자만 입력할 수 있습니다.";
    }

    if(deadline.getTime() < Date.now()){
        return "제출기한은 현재 시간 이전으로는 등록할 수 없습니다.";
    }

    if(content.length > 2000){
        return "숙제 내용은 2000자를 넘어서 안됩니다.";
    }

    return null;
}

export function getFormData() {
    const title = document.getElementById("title").value;
    const content = document.getElementById("content").value;
    const progress = document.getElementById("progress").value;
    const userId = document.getElementById("userId").value;
    const contentId = document.getElementById("contentId").value;
    const year = document.getElementById("year").value;
    const month = document.getElementById("month").value;
    const day = document.getElementById("day").value;
    const hour = document.getElementById("hour").value;
    const deadline = new Date(year, month - 1, day, hour);

    return {
        'title': title,
        'userId': userId,
        'contentId' :contentId,
        'content': content,
        'progress': progress,
        'deadline': deadline
    };
}

export function sendRequest(body) {
    $.ajax({
        url: "/homework/insertHomework",
        method: "POST",
        dataType: "json",
        data: JSON.stringify(body),
        contentType: "application/json",
        success: function (response) {
            storeNotification(response.message,"success")
            window.location.href = "/homework/insertHomeworkForm";
        },
        error: function (jqXHR) {
            console.log("insertHomework() failed");
            const errorResponse = JSON.parse(jqXHR.responseText);
            console.log(errorResponse);
            showNotification(errorResponse.message,"error",3000);
        }
    })
}

export function submitForm(event) {
    event.preventDefault();
    const formData = getFormData();
    const validationMessage = validateInput(formData.title, formData.progress, formData.deadline, formData.content);

    if(validationMessage !== null) {
        showNotification(validationMessage,"warn",3000);
        return;
    }
    console.log(formData);
    sendRequest(formData);
}