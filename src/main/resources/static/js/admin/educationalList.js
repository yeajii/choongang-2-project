var grid;
var gridData;

document.addEventListener("DOMContentLoaded", function () {
    listEdu();
});

function listEdu() {
    $.ajax({
        url: '/admin/resource/api/listEdu',
        method: 'GET',
        success: function (response) {
            var listEdu = response.listEdu;
            var count = response.count;
            gridData = listEdu.map(function (item) {
                var createdAt = new Date(item.createdAt);

                // 현재 날짜와 비교
                var today = new Date();
                var formattedDate = '';
                if (createdAt.toDateString() === today.toDateString()) {
                    // 오늘 날짜인 경우
                    var hours = createdAt.getHours().toString().padStart(2, '0');
                    var minutes = createdAt.getMinutes().toString().padStart(2, '0');
                    formattedDate = hours + ':' + minutes;
                } else {
                    // 오늘 날짜가 아닌 경우
                    var year = createdAt.getFullYear();
                    var month = (createdAt.getMonth() + 1).toString().padStart(2, '0');
                    var day = createdAt.getDate().toString().padStart(2, '0');
                    formattedDate = year + '-' + month + '-' + day;
                }
                return {
                    id: item.id,
                    gameTitle: item.gameTitle,
                    title: item.title,
                    resourceType: item.resourceType,
                    serviceType: item.serviceType,
                    createdAt: formattedDate,
                    readCount: item.readCount,
                    image: item.image
                };
            });


            grid = new tui.Grid({
                el: document.getElementById('grid1'),
                data: gridData,
                scrollX: false,
                scrollY: false,
                rowHeaders: [{
                    type: 'rowNum',
                    header: "NO",
                    align: 'center',
                    width: 50
                }],
                rowHeight: 'auto',
                columns: [
                    {
                        header: '썸네일',
                        name: 'image',
                        align: 'center',
                        width: 150,
                        formatter: function(cellData) {
                            var value = cellData.value;
                            return '<img src="' + contextPath + '/upload/educationalResources/' + value +'" style="width: 110px; height: 110px;">';
                        }
                    },
                    {
                        header: '학습자료',
                        name: 'title',
                        align: 'center',
                        width: 350,
                        minWidth: 150,
                        formatter: function(cellData) {
                            return '<span class="grid-edutitle" style="cursor:pointer;">' + cellData.value + '</span>';
                        }
                    },
                    {
                        header: '자료구분',
                        name: 'resourceType',
                        align: 'center',
                        width: 'auto',
                        minWidth: 150,
                        sortable: true,
                        sortingType: 'desc'
                    },
                    {
                        header: '서비스',
                        name: 'serviceType',
                        align: 'center',
                        width: 'auto',
                        minWidth: 150,
                        sortable: true,
                        sortingType: 'desc'
                    },
                    {
                        header: '자료업로드일자',
                        name: 'createdAt',
                        align: 'center',
                        width: 150,
                        sortable: true,
                        sortingType: 'desc'
                    },
                    {
                        header: '관련컨텐츠',
                        name: 'gameTitle',
                        align: 'center',
                        width: 'auto',
                        minWidth: 150
                    },
                    {
                        header: '조회수',
                        name: 'readCount',
                        align: 'center',
                        width: 70
                    },
                ],
                pageOptions: {
                    useClient: true,
                    perPage: 5
                },
            });

            grid.on('click', function(ev) {
                var column = ev.columnName;
                var rowKey = ev.rowKey;

                console.log(column,rowKey);

                if (column === 'title' && rowKey !== undefined) {
                    var id = grid.getValue(rowKey, 'id');
                    window.location.href = '/admin/resource/detailEdu?id='+id;
                }
            });

        }
    });

}

function listSearchEdu(keyword, category) {
    a.ajax({
        url: "admin/resource/api/listSearchEdu",
        method: "GET",
        data: {
          keyword:keyword,
          category:category
        },
        success: function (response) {
            console.log(response.listSearchEdu);
        }
    })
}

function callListSearchEdu() {
    var keyword = document.getElementById("keyword").value;
    var categoryEdu;
    if (document.getElementById("group").checked) {
        categoryEdu = document.getElementById("group").value;
    } else if (document.getElementById("educator").checked) {
        categoryEdu = document.getElementById("educator").value;
    }
    listSearchEdu(keyword, categoryEdu);
}







