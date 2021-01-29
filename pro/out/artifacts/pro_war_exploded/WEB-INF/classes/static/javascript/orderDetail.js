$(function(){
    //查询按钮点击事件
    $('#select-btn').click(function(){
        let params = {
            orderId: $('#orderId-select').val(),
            productName: $('#productName-select').val()
        }

        $ajax({
            type: 'post',
            url: '/product_info/select_list',
            data: JSON.stringify(params),
            contentType: 'application/json;charset=utf-8',
            dataType: 'json'
        }, false, '', function (res) {
            alert(res.msg);
            if(res.code == 200){
                setTable(res.data)
            }
        })
    })

    //修改按钮点击事件
    $('#edit-btn').click(function(){
        let rows = getTableSelection('#table');
        if(rows.length != 1){
            alert('请选择一条数据修改');
            return;
        }

        $('#edit-modal').modal('show');
        setForm(rows[0].data,'#edit-form');
    })

    //修改产品提交按钮点击事件
    $('#edit-form-submit-btn').click(function(){
        if(checkForm('#edit-form')){
            let params = formToJson('#edit-form');

            $ajax({
                type: 'post',
                url: '/product_info/update',
                data: {
                    productInfoJson: JSON.stringify(params)
                },
                contentType: 'application/json;charset=utf-8',
                dataType: 'json'
            }, false, '', function (res) {
                alert(res.msg);
                if(res.code == 200){
                    $('#edit-form-close-btn').click();
                    let rows = getTableSelection('#table');
                    $('#table').bootstrapTable('updateRow', {
                        index: rows[0].index,
                        row: res.data
                    })
                }
            })
        }
    })

    //修改产品关闭按钮点击事件
    $('#edit-form-close-btn').click(function(){
        $('#edit-form')[0].reset();
        $('#edit-modal').modal('hide');
    });

    //删除按钮点击事件
    $('#delete-btn').click(function(){
        let rows = getTableSelection('#table');
        if(rows.length == 0){
            alert('请至少选择一条数据删除');
            return;
        }

        $('#delete-modal').modal('show')
    })

    //确定删除点击事件
    $('#delete-submit-btn').click(function(){
        let rows = getTableSelection('#table');

        let idList = [];
        $.each(rows,function(index,row){
            idList.push(row.data.id)
        })

        $ajax({
            type: 'post',
            url: '/product_info/delete',
            data: JSON.stringify({
                idList: idList
            }),
            contentType: 'application/json;charset=utf-8',
            dataType: 'json'
        }, false, '', function (res) {
            alert(res.msg);
            if(res.code == 200){
                $('#delete-close-btn').click();
                getList();
            }
        })
    })

    //取消删除点击事件
    $('#delete-close-btn').click(function(){
        $('#delete-modal').modal('hide')
    })

    //刷新
    $('#refresh-btn').click(function(){
        getList(function(){
            alert('已刷新');
        });
    })

    //修改物料确定按钮点击事件
    $("#upd-matter-submit-btn").click(function(){
        let tableData = $('#matter-table').bootstrapTable('getData');
        let productInfoId = parseInt($('#productId').val());
        let productMatterList = [];

        $.each(tableData,function(index,row){
            let num = parseInt($('#matter-table tbody tr:eq('+index+') td:eq(2) input').val());
            let price = parseFloat($('#matter-table tbody tr:eq('+index+') td:eq(3) input').val());

            if(row.num != num || row.price != price){
                productMatterList.push({
                    matterProjectId: row.matterProjectId,
                    num: num,
                    price: price
                })
            }
        })

        $ajax({
            type: 'post',
            url: '/product_matter/update',
            data: JSON.stringify({
                productInfoId: productInfoId,
                productMatterList: productMatterList
            }),
            contentType: 'application/json;charset=utf-8',
            dataType: 'json'
        }, false, '', function (res) {
            alert(res.msg);
        })
    })

    $('#upd-matter-close-btn').click(function(){
        $('#show-matter-modal').modal('hide');
    })

    getList();
})

//获取订单明细
function getList(callback) {
    $ajax({
        type: 'post',
        url: '/product_info/post_list',
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
        pagination: false,
        clickToSelect: true,
        locale: 'zh-CN',
        toolbar: '#table-toolbar',
        toolbarAlign: 'left',
        columns: [
            {
                field: 'id',
                title: '序号',
                align: 'center',
                width: 50,
                formatter: function (value, row, index) {
                    return index + 1;
                }
            }, {
                field: 'orderInfoId',
                title: '订单号',
                align: 'left',
                sortable: true,
                width: 150
            }, {
                field: 'productName',
                title: '产品名称',
                align: 'left',
                sortable: true,
                width: 150
            }, {
                field: 'productNum',
                title: '数量',
                align: 'left',
                sortable: true,
                width: 100
            }, {
                field: 'productPrice',
                title: '单价',
                align: 'left',
                sortable: true,
                width: 100
            }, {
                field: 'id',
                title: '操作',
                align: 'left',
                width: 100,
                formatter: function (value, row, index) {
                    return '<button onclick="javascript:selectMatterByOrderId(' + row.id + ')" type="button" class="btn btn-primary">' +
                        '<i class="bi bi-inbox icon"></i>' +
                        '查看物料' +
                        '</button>'
                }
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

function selectMatterByOrderId(id) {
    $('#productId').val(id);
    $ajax({
        type: 'post',
        url: '/matter_info/selectListByProductId',
        data: JSON.stringify({
            productId: id
        }),
        contentType: 'application/json;charset=utf-8',
        dataType: 'json'
    }, false, '', function (res) {
        if(res.code == 200){
            $('#show-matter-modal').modal('show')
            setMatterTable(res.data);
        }else{
            alert(res.msg);
        }
    })
}

function setMatterTable(data) {
    if ($('#matter-table').html() != '') {
        $('#matter-table').bootstrapTable('load', data);
        return;
    }

    $('#matter-table').bootstrapTable({
        data: data,
        sortStable: true,
        classes: 'table table-hover',
        idField: 'id',
        pagination: false,
        clickToSelect: false,
        locale: 'zh-CN',
        columns: [
            {
                field: 'id',
                title: '序号',
                align: 'center',
                width: 50,
                formatter: function (value, row, index) {
                    return index + 1;
                }
            }, {
                field: 'code',
                title: '物料代码',
                align: 'left',
                sortable: true,
                width: 150
            }, {
                field: 'num',
                title: '数量',
                align: 'left',
                sortable: true,
                width: 70,
                formatter: function (value, row, index) {
                    return '<input type="number" class="form-control" value="' + value + '"/>'
                }
            }, {
                field: 'price',
                title: '单价',
                align: 'left',
                sortable: true,
                width: 70,
                formatter: function (value, row, index) {
                    return '<input type="number" class="form-control" value="' + value + '"/>'
                }
            }
        ]
    })
}