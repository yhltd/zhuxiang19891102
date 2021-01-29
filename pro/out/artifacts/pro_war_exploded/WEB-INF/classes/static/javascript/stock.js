$(function () {
    getList();

    //查询方法
    $('#select-btn').click(function () {
        let params = {
            productName: $('#productName-select').val()
        }

        $ajax({
            type: 'post',
            url: '/stock/select_list',
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

    //修改按钮点击事件
    $('#edit-btn').click(function () {
        let rows = getTableSelection('#table')
        if (rows.length != 1) {
            alert('请选择一条数据修改');
            return;
        }

        $('#edit-modal').modal('show');
        setForm(rows[0].data, '#edit-form');
    })

    //修改项目提交按钮点击事件
    $('#edit-form-submit-btn').click(function () {
        if (checkForm('#edit-form')) {
            let params = formToJson('#edit-form');
            let rows = getTableSelection('#table');
            let stock = rows[0].data
            stock.stockNum = params.stockNum

            $ajax({
                type: 'post',
                url: '/stock/update',
                data: {
                    stockJson: JSON.stringify(stock)
                }
            }, false, '', function (res) {
                alert(res.msg);
                if (res.code == 200) {
                    $('#edit-form-close-btn').click();
                    $('#table').bootstrapTable('updateRow', {
                        index: rows[0].index,
                        row: stock
                    })
                }
            })
        }
    })

    //修改项目关闭按钮点击事件
    $('#edit-form-close-btn').click(function () {
        $('#edit-form')[0].reset();
        $('#edit-modal').modal('hide');
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
            idList.push(row.data.stockId)
        })

        $ajax({
            type: 'post',
            url: '/stock/delete',
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
    $('#delete-close-btn').click(function () {
        $('#delete-modal').modal('hide');
    })

    //刷新按钮点击事件
    $('#refresh-btn').click(function () {
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
        url: '/stock/post_list',
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
                field: 'stockId',
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
                field: 'productPrice',
                title: '产品单价',
                align: 'left',
                sortable: true,
                width: 100
            }, {
                field: 'stockNum',
                title: '库存数量',
                align: 'left',
                sortable: true,
                width: 100
            }, {
                field: 'stockPrice',
                title: '库存金额',
                align: 'left',
                sortable: true,
                width: 100,
                formatter: function (value, row, index) {
                    return (row.stockNum * row.productPrice).toFixed(2);
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