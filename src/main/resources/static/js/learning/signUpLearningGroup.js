var grid;
var gridData;

document.addEventListener("DOMContentLoaded", function (){
    learningGroupList();
})

function learningGroupList(value, category) {
    $.ajax({
        url: "/learning/api/signUpLearningGroup",
        method: "GET",
        data: {
          value: value,
          category: category
        },
        success: function (response) {
            let learningGroupList = response.learningGroupList;
            let userType = response.userType;

            gridData = learningGroupList.map(function (item) {
                return {
                    id: item.id,
                    image: item.image,
                    name: item.name,
                    userName: item.userName,
                    groupSize: item.groupSize,
                    etc: item.etc1+"<br/>/"+"<br/>"+item.etc2,
                    startDate: item.startDate,
                    endDate: item.endDate,
                    applied: item.applied,
                    value: item.value,
                    category: item.category
                };
            });

            function BoldTextRenderer(props) {
                var el = document.createElement('div');
                el.innerHTML = props.value;
                el.style.fontWeight = 'bold';
                return {
                    el: el,
                    render: function(props) {
                        this.el.innerHTML = props.value;
                    },
                    getElement: function() {
                        return this.el;
                    }
                };
            }

            let columns = [
                {
                    header: '썸네일',
                    name: 'image',
                    align: 'center',
                    width: 120,
                    formatter: function(cellData) {
                        console.log(contextPath);
                        var value = cellData.value;
                        return '<img src="' + contextPath + '/upload/gameContents/' + value +'" style="width: 120px; height: 120px;">';
                    }
                },
                {
                    header: '그룹명',
                    name: 'name',
                    align: 'center',
                    width: 200,
                    minWidth: 170,
                    renderer: BoldTextRenderer
                },
                {
                    header: '교육자명',
                    name: 'userName',
                    align: 'center',
                    width: 100
                },
                {
                    header: '등록가능인원',
                    name: 'groupSize',
                    align: 'center',
                    sortable: true,
                    sortingType: 'desc',
                    width: 150
                },
                {
                    header: '그룹소개',
                    name: 'etc',
                    align: 'center',
                    minWidth: 100,
                    width: 300,
                },
                {
                    header: '학습시작날짜',
                    name: 'startDate',
                    align: 'center',
                    width: 130
                },
                {
                    header: '학습종료날짜',
                    name: 'endDate',
                    align: 'center',
                    width: 130
                }
            ];
            if (userType === '3') {
                console.log(userType);
                columns.push({
                    header: '상태',
                    name: '',
                    align: 'center',
                    width: 100,
                    formatter: function ({row}) {
                        const id = row.id;
                        const name = row.name;
                        const value = row.value;
                        const category = row.category;
                        switch (row.applied) {
                            case 1: return `<button class="myButton" id="requestSignUp-${id}" style="border-radius: 10px; width: 40px; height: 35px; background: #0C4DA2; color: white;" onclick="requestSignUp(${id}, '${name}', '${value}', '${category}')">신청</button>`;
                            case 2: return `<button class="myButton" id="cancelSignUp-${id}" style="border-radius: 10px; width: 40px; height: 35px; background: #0C4DA2; color: white;" onclick="cancelSignUp(${id}, '${name}', '${value}', '${category}')">취소</button>`;
                            case 3: return `신청불가`;
                            case 4: return `신청완료`;
                            case 5: return `정원초과`;
                        }
                    }
                });
            }

            grid = new tui.Grid({
                el: document.getElementById('grid1'),
                data: gridData,
                scrollX: false,
                scrollY: false,
                rowHeight: 'auto',
                columns: columns,
                rowHeaders: ['rowNum'],
                pageOptions: {
                    useClient: true,
                    perPage: 5
                },
            });
        },
    })
}

function requestSignUp(id, name, value, category) {
    let confirmMessage = "그룹명 '" + name + "'에 그룹가입을 신청하시겠습니까?";
    if (confirm(confirmMessage)) {
        $.ajax({
            url: "/learning/api/requestSignUp?groupId=" + id,
            method: "POST",
            success: function (response) {
                let result = response.result;
                if (result === 1) {
                    alert("신청이 완료되었습니다.");

                    // 버튼 업데이트
                    let button = document.getElementById(`requestSignUp-${id}`);
                    button.textContent = '취소';
                    button.id = `cancelSignUp-${id}`;
                    button.setAttribute('onclick', `cancelSignUp(${id})`);
                    grid.destroy();
                    learningGroupList(value, category);
                } else {
                    alert("신청실패..");
                }
            }
        });
    }
}

function cancelSignUp(id, name, value, category) {
    let confirmMessage = "그룹명 '" + name + "'에 그룹가입신청을 취소하시겠습니까?";
    if (confirm(confirmMessage)) {
        $.ajax({
            url: "/learning/api/cancelSignUp?groupId=" + id,
            method: "DELETE",
            success: function (response) {
                let result = response.result;
                if (result === 1) {
                    alert("신청취소이 취소되었습니다.");

                    // 버튼 업데이트
                    let button = document.getElementById(`cancelSignUp-${id}`);
                    button.textContent = '신청';
                    button.id = `requestSignUp-${id}`;
                    button.setAttribute('onclick', `requestSignUp(${id})`);
                    grid.destroy();
                    learningGroupList(value, category);
                } else {
                    alert("신청취소실패..");
                }
            }

        })
    }
}

function updateOptions(selectedValue) {
    var slgSelected = document.getElementById('slg-selected');

    while (slgSelected.firstChild) {
        slgSelected.removeChild(slgSelected.firstChild);
    }

    if (selectedValue === '그룹명') {
        $.ajax({
            url: '/learning/api/selected?keyword='+selectedValue,
            method: 'GET',
            success: function (response) {
                var groupNames = response.slg;

                groupNames.forEach(function(groupName) {
                    var option = document.createElement('option');
                    option.text = groupName;
                    slgSelected.add(option);
                });
            }
        })

    } else if (selectedValue === '교육자명') {
        $.ajax({
            url: '/learning/api/selected?keyword='+selectedValue,
            method: 'GET',
            success: function (response) {
                var userNames = response.slg;

                userNames.forEach(function(userName) {
                    var option = document.createElement('option');
                    option.text = userName;
                    slgSelected.add(option);
                });
            }
        })
    } else if (selectedValue === '선택없음') {
        var option = document.createElement('option');
        option.hidden = true;
        slgSelected.add(option);

        grid.destroy();
        learningGroupList();
    }
}

// 'slg-select'의 변경을 감지
document.getElementById('slg-select').addEventListener('change', function() {
    updateOptions(this.value);
});

// 페이지 로드 시 '그룹명'에 해당하는 옵션을 'slg-selected'에 추가
window.addEventListener('load', function() {
    updateOptions('선택없음');
});

function slgsearch(selectedValue, category) {
    grid.destroy();
    learningGroupList(selectedValue, category);
}