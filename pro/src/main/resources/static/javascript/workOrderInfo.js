$(function () {
    $ajax({
        type: 'post',
        url: '/work_order_info/getList',
    }, false, '', function (res) {
        if (res.code == 200) {
            setTable(res.data);
        }
        console.log(res)
    })
})

/**
 * 点击按钮弹出弹窗查看对应的订单明细
 * @param workOrderInfoId
 */
function showDetail(workOrder) {

    $ajax({
        type: 'post',
        url: '/work_order_detail/getListByWorkOrder',
        data: {
            workOrder: workOrder
        }
    }, false, '', function (res) {
        if (res.code == 200) {
            $('#detail-modal').modal('show');
            $('#detail-table').bootstrapTable('load', res.data).bootstrapTable({
                data: res.data,
                sortStable: true,
                classes: 'table table-hover',
                idField: 'id',
                pagination: true,
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
                        field: 'workOrder',
                        title: '派工单单号',
                        align: 'left',
                        sortable: true,
                        width: 150
                    }, {
                        field: 'workDate',
                        title: '派工日期',
                        align: 'left',
                        sortable: true,
                        width: 150,
                        formatter: function(value, row, index){
                            return formatDate(new Date(value),'yyyy-MM-dd')
                        }
                    }, {
                        field: 'workShop',
                        title: '车间',
                        align: 'left',
                        sortable: true,
                        width: 100
                    }, {
                        field: 'workLine',
                        title: '产线',
                        align: 'left',
                        sortable: true,
                        width: 100
                    }, {
                        field: 'matterName',
                        title: '物料',
                        align: 'left',
                        sortable: true,
                        width: 150
                    }
                    , {
                        field: 'workNum',
                        title: '数量',
                        align: 'left',
                        sortable: true,
                        width: 100
                    }
                ]
            })
        }
    })
}


/**
 * 点击按钮根据派工单单号模糊查询
 */
$(function () {
    $("#select-btn").click(function () {
        let params = {
            workOrder: $('#workOrder').val(),
            startDate: $('#startTime').val(),
            endDate: $('#endDate').val()
        }

        $ajax({
            type: 'post',
            url: '/work_order_info/selectList',
            data: JSON.stringify(params),
            contentType: "application/json;charset=utf-8",
            dataType: 'json'
        }, false, '', function (res) {
            if (res.code == 200) {
                $('#workOrderInfoTable').bootstrapTable('load', res.data);
            }
            console.log(res)
        })
    })
})

/**
 * 点击按钮根据起始时间查询
 */
$(function () {
    $("#select-btn2").click(function () {
        var startTime = $("#startTime").val() + ':00';
        var endTime = $("#endTime").val() + ':00';
        if (startTime == ":00") {
            alert("请选择时间再点击查询")
        } else if (endTime == ":00") {
            alert("请选择时间再点击查询")
        } else {
            $ajax({
                type: 'post',
                url: '/work_order_info/getListByCreateTime',
                data: JSON.stringify({
                    startTime: startTime,
                    endTime: endTime
                }),
                dataType: 'json',
                contentType: 'application/json;charset=utf-8'
            }, false, '', function (res) {
                if (res.code == 200) {
                    $('#workOrderInfoTable').bootstrapTable('load', res.data);
                }
                console.log(res)
            })
        }
    })
})

/**
 * 动态表格
 * @param data
 */
function setTable(data) {

    if ($('#workOrderDetailTable').html != '') {
        $('#workOrderDetailTable').bootstrapTable('load', data);
    }

    $('#workOrderInfoTable').bootstrapTable({
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
                field: 'createTime',
                title: '创建日期',
                align: 'left',
                sortable: true,
                width: 150,
                formatter: function(value, row, index){
                    return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss')
                }
            }, {
                field: 'workOrder',
                title: '派工单单号',
                align: 'left',
                sortable: true,
                width: 150
            }, {
                field: 'workWeek',
                title: '日期(年月周)',
                align: 'left',
                sortable: true,
                width: 150,
                formatter: function (value, row, index) {
                    let createTime = row.createTime;
                    var getMonthWeek = function (a, b, c) {
                        var date = new Date(a, parseInt(b) - 1, c), w = date.getDay(), d = date.getDate();
                        return Math.ceil(
                            (d + 6 - w) / 7
                        );
                    }
                    var date = new Date(createTime);
                    var y = date.getFullYear();
                    var m = date.getMonth() + 1;
                    var d = date.getDate();
                    index = y + "年" + m + "月" + "第" + getMonthWeek(y, m, d) + "周";
                    return index;
                }
            }, {
                field: 'state',
                title: '状态',
                align: 'left',
                sortable: true,
                width: 100,
                formatter: function(value, row, index){
                    return value == 0 ? '未入库' : '已入库'
                }
            }, {
                field: '',
                title: '操作',
                align: 'left',
                sortable: true,
                width: 100,
                formatter: function (value, row, index) {
                    return '<button onclick="javascript:showDetail(' +
                        "'" + row.workOrder + "'" +
                        ')" class="btn btn-primary" >查看明细</button>'
                }
            }
        ]
    })
}
