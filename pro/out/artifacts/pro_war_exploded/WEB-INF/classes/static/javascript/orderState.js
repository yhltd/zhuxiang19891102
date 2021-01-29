$(function(){
    $('#select-btn').click(function(){
        let params = {
            orderId: $('#orderId-select').val()
        }

        $ajax({
            type: 'post',
            url: '/order_info/select_state_list',
            data: JSON.stringify(params),
            contentType: 'application/json;charset=utf-8',
            dataType: 'json'
        }, false, '', function (res) {
            alert(res.msg)
            if (res.code == 200) {
                setTable(res.data);
            }
        })
    })

    getList();
})

//获取订单明细
function getList(callback) {
    $ajax({
        type: 'post',
        url: '/order_info/post_state_list',
    }, false, '', function (res) {
        if (res.code == 200) {
            setTable(res.data);
            if (callback != undefined) {
                callback();
            }
        }
    })
}

//设置订单明细表格
function setTable(data) {

    if ($('#table').html != '') {
        $('#table').bootstrapTable('load', data);
    }

    $('#table').bootstrapTable({
        data: data,
        sortStable: true,
        classes: 'table table-hover',
        idField: 'id',
        pagination: true,
        locale: 'zh-CN',
        columns: [
            {
                field: 'id',
                title: '序号',
                align: 'center',
                width: 70,
                formatter: function (value, row, index) {
                    return index + 1;
                }
            }, {
                field: 'projectName',
                title: '项目名称',
                align: 'left',
                sortable: true,
                width: 250
            }, {
                field: 'orderId',
                title: '订单号',
                align: 'left',
                sortable: true,
                width: 250
            }, {
                field: 'createTime',
                title: '创建时间',
                align: 'left',
                sortable: true,
                width: 200,
                formatter: function(value,row,index){
                    return formatDate(new Date(value),"yyyy-MM-dd HH:mm:ss")
                }
            }, {
                field: 'work_date',
                title: '开始生产日期',
                align: 'left',
                sortable: true,
                width: 200,
                formatter: function(value,row,index){
                    return formatDate(new Date(value),"yyyy-MM-dd")
                }
            }, {
                field: 'work_num',
                title: '生成数量',
                align: 'left',
                sortable: true,
                width: 150
            }, {
                field: 'setStockDate',
                title: '第一次入库日期',
                align: 'left',
                sortable: true,
                width: 200,
                formatter: function(value,row,index){
                    return formatDate(new Date(value),"yyyy-MM-dd HH:mm:ss")
                }
            }, {
                field: 'setStockNum',
                title: '入库数量',
                align: 'left',
                sortable: true,
                width: 150
            }, {
                field: 'outStockDate',
                title: '第一次出库日期',
                align: 'left',
                sortable: true,
                width: 200
            }, {
                field: 'outStockNum',
                title: '出库数量',
                align: 'left',
                sortable: true,
                width: 150
            }, {
                field: 'state',
                title: '状态',
                align: 'left',
                width: 100
            }
        ]
    })
}