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
            url: '/out_stock/select_list',
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

    //出库点击事件
    $('#add-btn').click(function(){
        $('#product-table').bootstrapTable('removeAll');
        $('#add-modal').modal('show');

        $('#order-select').html('');
        let $option = '<option value="">请选择订单号</option>'
        $('#order-select').append($option);

        $ajax({
            type: 'post',
            url: '/order_info/post_list',
        }, false, '', function (res) {
            if (res.code == 200) {
                $.each(res.data,function(index,data){
                    $option = '<option value="'+data.orderId+'">'+data.orderId+'</option>'
                    $('#order-select').append($option);
                })
            }
        })
    })

    //选择订单号事件
    $('#order-select').change(function(){
        let orderId = $(this).val();
        if(orderId == ''){
            $('#product-table').bootstrapTable('removeAll');
            return;
        }

        $ajax({
            type: 'post',
            url: '/out_stock/select_list_outStock',
            data: JSON.stringify({
                orderId: orderId
            }),
            contentType: "application/json;charset=utf-8",
            dataType: 'json'
        },false,'',function(res){
            if(res.code == 200){
                setProductTable(res.data)
            }else{
                alert(res.msg)
                $('#work-table').bootstrapTable('removeAll');
            }
        })
    })

    //出库提交按钮
    $('#add-form-submit-btn').click(function(){
        let rows = $('#product-table').bootstrapTable('getData');
        let list = []
        let check = true;
        let outAddress = $('#outAddress').val();
        if(outAddress == ''){
            alert('请输入出货地');
            return;
        }

        $.each(rows,function(index,row){
            let outNum = $('#product-table tbody tr:eq('+index+') td:eq(2)').text();

            list.push({
                outAddress: outAddress,
                productInfoId: row.productInfoId,
                outNum: parseFloat(outNum)
            })
        })

        if(check){
            $ajax({
                type: 'post',
                url: '/out_stock/add',
                data: JSON.stringify({
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

    //出库入库按钮
    $('#add-form-close-btn').click(function(){
        $('#product-table').bootstrapTable('removeAll');
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
            idList.push(row.data.outStockId)
        })

        $ajax({
            type: 'post',
            url: '/out_stock/delete',
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
        url: '/out_stock/post_list',
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
    $('#table-toolbar').css({
        'opacity': 1
    })

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
                field: 'outOrder',
                title: '出库单号',
                align: 'left',
                sortable: true,
                width: 150
            }, {
                field: 'productName',
                title: '产品名称',
                align: 'left',
                sortable: true,
                width: 100
            }, {
                field: 'createTime',
                title: '出库时间',
                align: 'left',
                sortable: true,
                width: 150,
                formatter: function(value,index,row){
                    return formatDate(new Date(value),"yyyy-MM-dd HH:mm:ss");
                }
            }, {
                field: 'outNum',
                title: '出库数量',
                align: 'left',
                sortable: true,
                width: 70
            }, {
                field: 'productPrice',
                title: '产品单价',
                align: 'left',
                sortable: true,
                width: 70
            }, {
                field: 'outPrice',
                title: '出库金额',
                align: 'left',
                sortable: true,
                width: 70,
                formatter: function (value, row, index) {
                    return (row.outNum * row.productPrice).toFixed(2);
                }
            }, {
                field: 'man',
                title: '出库人',
                align: 'left',
                sortable: true,
                width: 100
            }, {
                field: 'outAddress',
                title: '出库地',
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

    $('.fixed-table-container').addClass('border-top').addClass('border-bottom');
}

function setProductTable(data){

    if ($('#product-table').html != '') {
        $('#product-table').bootstrapTable('load', data);
    }

    $('#product-table').bootstrapTable({
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
                width: 150
            }, {
                field: 'productNum',
                title: '订单数量',
                align: 'left',
                sortable: true,
                width: 70
            }, {
                field: 'outNum',
                title: '库存数量',
                align: 'left',
                sortable: true,
                width: 70,
                formatter: function (value, row, index){
                    if(value < row.productNum){
                        $('#add-form-submit-btn').attr('disabled','disabled');
                    }else{
                        $('#add-form-submit-btn').removeAttr('disabled');
                    }
                    return value;
                }
            }
        ]
    })

    $('.fixed-table-container').addClass('border-top').addClass('border-bottom');
}