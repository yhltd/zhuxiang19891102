$(function(){
    getList();

    //查询方法
    $('#select-btn').click(function(){
        let params = {
            setOrder: $('#setOrder-select').val(),
            productName: $('#productName-select').val()
        }

        $ajax({
            type: 'post',
            url: '/set_stock/select_list',
            data: JSON.stringify(params),
            contentType: "application/json;charset=utf-8",
            dataType: 'json'
        }, false, '', function (res) {
            alert(res.msg);
            if (res.code == 200) {
                setTable(res.data);
            }
        })
    })

    //入库点击事件
    $('#add-btn').click(function(){
        $('#add-modal').modal('show');

        $('#workOrder-select').html('');
        let $option = '<option value="">请选择派工单号</option>'
        $('#workOrder-select').append($option);

        $ajax({
            type: 'post',
            url: '/work_order_info/getListByState',
        }, false, '', function (res) {
            if (res.code == 200) {
                $.each(res.data,function(index,data){
                    $option = '<option value="'+data.workOrder+'">'+data.workOrder+'</option>'
                    $('#workOrder-select').append($option);
                })
            }
        })
    })

    //选择派工单号事件
    $('#workOrder-select').change(function(){
        let workOrder = $(this).val();
        if(workOrder == ''){
            $('#work-table').bootstrapTable('removeAll');
            return;
        }

        $ajax({
            type: 'post',
            url: '/work_order_detail/getListByWorkOrder',
            data: {
                workOrder: workOrder
            }
        },false,'',function(res){
            if(res.code == 200){
                setWorkTable(res.data)
            }else{
                alert(res.msg)
                $('#work-table').bootstrapTable('removeAll');
            }
        })
    })

    //入库提交按钮
    $('#add-form-submit-btn').click(function(){
        let rows = $('#work-table').bootstrapTable('getData');
        let list = []
        let check = true;

        $.each(rows,function(index,row){
            let setNum = $('#work-table tbody tr:eq('+index+') td:eq(4) input').val();

            if(setNum == '' || parseFloat(setNum) > row.workNum){
                alert('入库数量不能为0或者大于生产数量，行号：' + (index+1));
                check = false;
                return false;
            }else{
                list.push({
                    productInfoId: row.productInfoId,
                    setNum: parseFloat(setNum)
                })
            }
        })

        if(check){
            $ajax({
                type: 'post',
                url: '/set_stock/add',
                data: JSON.stringify({
                    workOrder: $('#workOrder-select').val(),
                    list: list
                }),
                contentType: "application/json;charset=utf-8",
                dataType: 'json'
            }, false, '', function (res) {
                alert(res.msg);
                if (res.code == 200) {
                    $('#add-form-close-btn').click();
                    getList();
                }
            })
        }
    })

    //取消入库按钮
    $('#add-form-close-btn').click(function(){
        $('#work-table').bootstrapTable('removeAll');
        $('#add-modal').modal('hide')
    })

    //删除按钮点击事件
    $('#delete-btn').click(function () {
        let rows = getTableSelection('#table');
        if (rows.length == 0) {
            alert("请至少选择一条数据删除")
            return;
        }

        $('#delete-modal').modal('show');
    })

    //确定删除点击事件
    $('#delete-submit-btn').click(function () {
        let idList = [];
        let rows = getTableSelection('#table');

        $.each(rows, function (index, row) {
            idList.push(row.data.setStockId)
        })

        $ajax({
            type: 'post',
            url: '/set_stock/delete',
            data: JSON.stringify({
                idList: idList
            }),
            contentType: "application/json;charset=utf-8",
            dataType: 'json'
        }, false, '', function (res) {
            alert(res.msg);
            if (res.code == 200) {
                $('#delete-close-btn').click();
                getList();
            }
        })
    })

    //取消删除按钮点击事件
    $('#delete-close-btn').click(function (){
        $('#delete-modal').modal('hide');
    })

    //刷新按钮点击事件
    $('#refresh-btn').click(function () {
        $('#setOrder-select').val('')
        $('#productName-select').val('')
        getList(function () {
            alert('已刷新');
        });
    })
})


//获取明细汇总
function getList(callback) {
    $ajax({
        type: 'post',
        url: '/set_stock/post_list',
    }, false, '', function (res) {
        if (res.code == 200) {
            setTable(res.data);
            if (callback != undefined) {
                callback();
            }
        }
    })
}

//设置明细表格
function setTable(data) {

    if ($('#table').html != '') {
        $('#table').bootstrapTable('load', data);
    }

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
                field: 'setStockId',
                title: '序号',
                align: 'center',
                width: 50,
                formatter: function (value, row, index) {
                    return index + 1;
                }
            }, {
                field: 'setOrder',
                title: '入库单号',
                align: 'left',
                sortable: true,
                width: 150
            }, {
                field: 'productName',
                title: '产品名称',
                align: 'left',
                sortable: true,
                width: 120
            }, {
                field: 'createTime',
                title: '入库时间',
                align: 'left',
                sortable: true,
                width: 150,
                formatter: function(value,index,row){
                    return formatDate(new Date(value),"yyyy-MM-dd HH:mm:ss");
                }
            }, {
                field: 'setNum',
                title: '入库数量',
                align: 'left',
                sortable: true,
                width: 100
            }, {
                field: 'productPrice',
                title: '产品单价',
                align: 'left',
                sortable: true,
                width: 100
            }, {
                field: 'setPrice',
                title: '入库金额',
                align: 'left',
                sortable: true,
                width: 100,
                formatter: function (value, row, index) {
                    return (row.setNum * row.productPrice).toFixed(2);
                }
            }, {
                field: 'man',
                title: '入库人',
                align: 'left',
                sortable: true,
                width: 100
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
}

function setWorkTable(data){

    if ($('#work-table').html != '') {
        $('#work-table').bootstrapTable('load', data);
    }

    $('#work-table').bootstrapTable({
        data: data,
        sortStable: true,
        classes: 'table table-hover',
        idField: 'id',
        locale: 'zh-CN',
        columns: [
            {
                field: 'setStockId',
                title: '序号',
                align: 'center',
                width: 50,
                formatter: function (value, row, index) {
                    return index + 1;
                }
            }, {
                field: 'productName',
                title: '产品名称',
                align: 'left',
                sortable: true,
                width: 120
            }, {
                field: 'workDate',
                title: '生产日期',
                align: 'left',
                sortable: true,
                width: 100,
                formatter: function(value,index,row){
                    return formatDate(new Date(value),"yyyy-MM-dd")
                }
            }, {
                field: 'workNum',
                title: '生产数量',
                align: 'left',
                sortable: true,
                width: 100
            }, {
                field: 'setNum',
                title: '入库数量',
                align: 'left',
                sortable: true,
                width: 100,
                formatter: function(){
                    return "<input class='form-control' type='number'/>"
                }
            }
        ]
    })
}