export function populateYear() {
    const yearSelect = document.getElementById("year");
    const currentYear = new Date().getFullYear();
    for (let i = currentYear; i <= currentYear + 10; i++) {
        addOption(yearSelect, i);
    }
}

export function populateMonth() {
    const monthSelect = document.getElementById("month");
    for (let j = 1; j <= 12; j++) {
        addOption(monthSelect, j);
    }
}

export function populateDay() {
    const daySelect = document.getElementById("day");
    for (let k = 1; k <= 31; k++) {
        addOption(daySelect, k);
    }
}

export function populateHour() {
    const hourSelect = document.getElementById("hour");
    for (let l = 1; l <= 31; l++) {
        addOption(hourSelect, l);
    }
}

export function addOption(selectElement, value) {
    const option = document.createElement("option");
    option.value = value;
    option.text = value;
    selectElement.appendChild(option);
}

export function updateDate() {
    const today = new Date();
    document.getElementById("year").value = today.getFullYear();
    document.getElementById("month").value = today.getMonth() + 1;
    document.getElementById("day").value = today.getDate();
    document.getElementById("hour").value = today.getHours()+1;
}

export function initializeDate(){
    populateYear();
    populateMonth();
    populateDay();
    populateHour();
    updateDate();
}