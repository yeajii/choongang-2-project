import showNotification from '/js/utils/notification.js';

export function showStoredNotification() {
    const notification = localStorage.getItem('notification');
    if (notification) {
        const { message, type } = JSON.parse(notification);
        showNotification(message, type, 3000);
        // Notification을 보여줬으니 Local Storage에서 삭제합니다.
        localStorage.removeItem('notification');
    }
}

export function storeNotification(message, type) {
    localStorage.setItem('notification', JSON.stringify({ message, type }));
}