$(function () {
    getList();
    $('#delete-btn').click(function(){
        let rows = getTableSelection("#workOrderDetailTable");
        if(rows.length == 0){
            alert('')
            return;
        }

        let idList = [];
        $.each(rows,function(index,row){
            idList.push(row.data.id)
        })

        $ajax({
            type: 'post',
            url: '/work_order_detail/delete',
            data: JSON.stringify({
                idList: idList
            }),
            dataType: 'json',
            contentType: 'application/json;charset=utf-8'

        },false, function (res) {
                if (res.code == 200) {
                    setTable(res.data);
                }
                console.log(res)
        })
    })
})

function getList(){
    $ajax({
        type: 'post',
        url: '/work_order_detail/getList',
    }, false, function (res) {
        if (res.code == 200) {
            setTable(res.data);
        }
        console.log(res)
    })
}

/**
 * 动态表格
 * @param data
 */
function setTable(data) {
    $('#table-toolbar').css({
        'opacity': 1
    })

    if ($('#workOrderDetailTable').html != '') {
        $('#workOrderDetailTable').bootstrapTable('load', data);
    }
    $('#workOrderDetailTable').bootstrapTable({
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
            },{
                field: 'workOrder',
                title: '派工单单号',
                align: 'left',
                sortable: true,
                width: 150
            }, {
                field: 'workDate',
                title: '周几',
                align: 'left',
                sortable: true,
                width: 150,
                formatter: function (value, row, index) {
                    let date = new Date(row.workDate);
                    let week = date.getDay();
                    let weekArray = ["星期日","星期一","星期二","星期三","星期四","星期五","星期六"]
                    return weekArray[week];
                }
            }, {
                field: 'workShop',
                title: '车间',
                align: 'left',
                sortable: true,
                width: 150
            }, {
                field: 'workLine',
                title: '产线',
                align: 'left',
                sortable: true,
                width: 150
            },
            {
                field: 'productName',
                title: '产品名称',
                align: 'left',
                sortable: true,
                width: 150
            },{
                field: 'workNum',
                title: '数量',
                align: 'left',
                sortable: true,
                width: 150
            }
        ],
        onClickRow: function (row, el) {
            let isSelect = $(el).hasClass('selected')
            if (isSelect) {
                $(el).removeClass('selected')
            } else {
                $(el).addClass('selected')
            }
        }
    })
    $('.fixed-table-container').addClass('border-top').addClass('border-bottom');
}

/**
 * 弹出窗体进行修改
 */
function updateDetail(){
    $('#detail-modal').modal('show');
    $('#detail-table').bootstrapTable({
        sortStable: true,
        classes: 'table table-hover',
        idField: 'id',
        pagination: true,
        clickToSelect: true,
        locale: 'zh-CN',
        toolbar: '#table-toolbar',
        toolbarAlign: 'left',
        columns:[{
            title: '派工单单号',
            align: 'left',
            sortable: true,
            width: 150,
            formatter: function (value, row, index) {
                return '<input type="text" id="">'
            }
        },{
            title: '车间',
            align: 'left',
            sortable: true,
            width: 150,
            formatter: function (value, row, index) {
                return '<input type="text" id="workShop">'
            }
        },{
            title: '产线',
            align: 'left',
            sortable: true,
            width: 150,
            formatter: function (value, row, index) {
                return '<input type="text" id="workLine">'
            }
        },{
            title: '产品名称',
            align: 'left',
            sortable: true,
            width: 150,
            formatter: function (value, row, index) {
                return '<input type="text" id="productName">'
            }
        },{
            title: '数量',
            align: 'left',
            sortable: true,
            width: 150,
            formatter: function (value, row, index) {
                return '<input type="text" id="workNum">'
            }
        }]
    })
}


//获取表格选择行
function getTableSelection(tableEl) {
    let result = [];
    let tableData = $(tableEl).bootstrapTable('getData');
    $(tableEl + ' tr').each(function (i, tr) {
        let index = $(tr).data('index');
        if (index != undefined) {
            if ($(tr).hasClass('selected')) {
                result.push({
                    index: index,
                    data: tableData[index]
                })
            }
        }
    })
    return result;
}

//选择表格行
function setTableSelection(tableEl, rowIndex, isSelect) {
    $(tableEl + ' tr').each(function (i, tr) {
        let index = $(tr).data('index');
        if (index == rowIndex) {
            if (isSelect) {
                $(tr).addClass('selected')
            } else {
                $(tr).removeClass('selected')
            }
        }
    })
}
