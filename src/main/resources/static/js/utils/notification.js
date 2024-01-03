
function showNotification(message, type, duration) {
    var notification = document.createElement('div');
    notification.className = 'notification ' + type;

    // 아이콘 설정
    /*     var icon;
        if (type === 'success') icon = '✅';
        else if (type === 'warn') icon = '⚠️';
        else if (type === 'error') icon = '❌';
        else icon = 'ℹ️'; */

    // 아이콘과 메시지를 추가
    /*     notification.innerHTML = icon + ' ' + message;
     */
    notification.innerHTML = message;

    document.body.appendChild(notification);
    // 알림창이 부드럽게 나타나게 함
    setTimeout(function () {
        notification.classList.add('show');
    }, 50);
    // 일정 시간 후에 알림창이 부드럽게 사라지게 함
    setTimeout(function () {
        notification.classList.remove('show');

        // 알림창이 완전히 사라진 후에 DOM에서 제거
        setTimeout(function () {
            document.body.removeChild(notification);
        }, 300);
    }, duration);
}
export default showNotification;