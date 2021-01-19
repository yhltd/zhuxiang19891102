$(function () {
    $ajax({
        type: 'post',
        url: '/project_info/getList',
    }, false, function (res) {
        if (res.code == 200) {
            setTable(res.data);
        }
        console.log(res)
    })
})

function setTable(data) {
    $('#table-toolbar').css({
        'opacity': 1
    })
    $('#table').bootstrapTable({
        data: data,
        sortStable: true,
        classes: 'table table-hover',
        idField: 'id',
        pagination: false,
        clickToSelect: true,
        locale: 'zh-CN',
        toolbar: '#table-toolbar',
        toolbarAlign: 'left',
        columns: [
            {
                field: '',
                title: '序号',
                align: 'center',
                width: 50,
                formatter: function (value, row, index) {
                    return index + 1;
                }
            }, {
                field: 'projectName',
                title: '项目名称',
                align: 'left',
                sortable: true,
                width: 150
            }, {
                field: 'projectAddress',
                title: '项目地址',
                align: 'left',
                sortable: true,
                width: 150
            }, {
                field: 'customerName',
                title: '客户名称',
                align: 'left',
                sortable: true,
                width: 150
            }, {
                field: 'createTime',
                title: '创建时间',
                align: 'left',
                sortable: true,
                width: 150
            }
        ]
    })
}