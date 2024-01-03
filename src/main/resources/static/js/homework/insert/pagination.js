// pagination.js
export function initializePagination() {
    const pageItems = document.querySelectorAll('.page-item a');
    pageItems.forEach(item => {
        item.addEventListener('click', function(event) {
            event.preventDefault();
            location.href = createQueryURL(this.dataset.page);
        });
    });
}

function createQueryURL(page) {
    const params = {
        currentPage: page
    };
    return '/homework/insertHomeworkForm?' + Object.entries(params)
        .filter(([key, value]) => value !== undefined && value !== null && value !== '')
        .map(([key, value]) => key + '=' + value).join('&');
}