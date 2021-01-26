let productInfoList = []

let matterList = []

$(function () {

    //查询按钮点击事件
    $('#select-btn').click(function () {
        let params = {
            projectName: $('#projectName-select').val(),
            orderId: $('#orderId-select').val(),
            startDate: $('#startDate-select').val(),
            endDate: $('#endDate-select').val(),
        }

        $ajax({
            type: 'post',
            url: '/order_info/select_list',
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

    //新增按钮点击事件
    $('#add-btn').click(function () {
        $('#add-form')[0].reset();
        $ajax({
            type: 'post',
            url: '/project_info/getList'
        }, false, '', function (res) {
            if (res.code == 200) {
                if (res.data.length == 0) {
                    alert('请先添加一个项目')
                } else {
                    $('#projectSelect').html('');
                    $.each(res.data, function (index, projectInfo) {
                        let option = $('<option value="' + projectInfo.id + '">' + projectInfo.projectName + '</option>')
                        $('#projectSelect').append(option);
                    })
                    $('#add-modal').modal('show');
                }
            }
        })
    })

    //新增订单提交按钮
    $('#add-form-submit-btn').click(function () {
        if (checkForm('#add-form')) {
            productInfoList = [];
            matterList = [];
            $('#add-modal').modal('hide');
            $('#product-modal').modal('show');
            setProductTable();
        }
    })

    //新增产品按钮点击事件
    $('#add-product').click(function () {
        productInfoList.push({
            productName: '',
            productNum: 0,
            productPrice: 0,
            matterInfo: []
        })
        setProductTable();
        if (matterList.length == 0) {
            getMatterList();
        }
    })

    //产品窗口提交按钮点击事件
    $('#product-submit-btn').click(function(){
        $ajax({
            type: 'post',
            url: '/order_info/add',
            data: JSON.stringify({
                orderInfo: formToJson('#add-form'),
                productInfoList: productInfoList
            }),
            contentType: 'application/json;charset=utf-8',
            dataType: 'json'
        },false,'',function(res){
            alert(res.msg);
            if(res.code == 200){
                $('#product-modal').modal('hide');
            }
        })
    })

    //产品窗口返回按钮点击事件
    $('#product-close-btn').click(function(){
        $('#product-modal').modal('hide');
        $('#add-modal').modal('show');
    })

    //选择物料确定按钮
    $('#matter-submit-btn').click(function () {
        let index = parseInt($('#productIndex').val());
        let check = true;
        $('#matter-table tbody tr').each(function (i, tr) {
            let num = 0
            let price = 0;
            check = true;
            $(tr).children().each(function (j, td) {
                if (j == 3) {
                    //用户输入的数量
                    num = parseInt($(td).children().val());
                    if(num == 0){
                        check = false;
                        return false;
                    }
                    //产品数量
                    let productNum = productInfoList[index].productNum;
                    //该行物料总可用数量
                    let matterNum = matterList[i].matterNum;

                    if (num*productNum > matterNum) {
                        alert('订单使用数量不能大于总数量，序号' + (i + 1))
                        check = false;
                        return;
                    }
                } else if (j == 4) {
                    //用户输入的单价
                    price = parseInt($(td).children().val());
                }
            })
            if(check){
                productInfoList[index].matterInfo.push(JSON.parse(JSON.stringify(matterList[i])));
                let ii = productInfoList[index].matterInfo.length-1
                productInfoList[index].matterInfo[ii].num = num;
                productInfoList[index].matterInfo[ii].price = price;
            }
        })
        if(check) {
            $('#matter-back-btn').click();
        }
    })

    //选择物料返回按钮
    $('#matter-back-btn').click(function () {
        $('#matter-modal').modal('hide');
        $('#product-modal').modal('show');
    })

    //新增订单关闭按钮
    $('#add-form-close-btn').click(function () {
        $('#add-modal').modal('hide');
    })

    //修改按钮点击事件
    $('#edit-btn').click(function () {
        let rows = getTableSelection('#table');
        if (rows.length != 1) {
            alert('请选择一条数据修改')
            return;
        }

        $('#edit-modal').modal('show');
        setForm(rows[0].data, '#edit-form');
    })

    //修改订单提交按钮点击事件
    $('#edit-form-submit-btn').click(function () {
        if (checkForm('#edit-form')) {
            let params = formToJson('#edit-form');

            $ajax({
                type: 'post',
                url: '/order_info/update',
                data: {
                    orderInfoItemJson: JSON.stringify(params)
                },
                contentType: "application/json;charset=utf-8",
                dataType: 'json'
            }, false, '', function (res) {
                alert(res.msg)
                if (res.code == 200) {
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

    //修改订单关闭按钮点击事件
    $('#edit-form-close-btn').click(function () {
        $('#edit-form')[0].reset();
        $('#edit-modal').modal('hide')
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
            idList.push(row.data.id)
        })

        $ajax({
            type: 'post',
            url: '/order_info/delete',
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
        $('#projectName-select').val('')
        $('#orderId-select').val('')
        $('#startDate-select').val('')
        $('#endDate-select').val('')
        getList(function () {
            alert('已刷新');
        });
    })

    getList();
})

//获取订单汇总
function getList(callback) {
    $ajax({
        type: 'post',
        url: '/order_info/post_list',
    }, false, '', function (res) {
        if (res.code == 200) {
            setTable(res.data);
            if (callback != undefined) {
                callback();
            }
        }
    })
}

//设置订单表格
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
                field: 'id',
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
                field: 'orderId',
                title: '订单号',
                align: 'left',
                sortable: true,
                width: 150
            }, {
                field: 'supplier',
                title: '供应商',
                align: 'left',
                sortable: true,
                width: 100
            }, {
                field: 'comment',
                title: '贸易条款',
                align: 'left',
                sortable: true,
                width: 100
            }, {
                field: 'createTime',
                title: '创建时间',
                align: 'left',
                sortable: true,
                width: 150,
                formatter: function (value, row, index) {
                    return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss')
                }
            }, {
                field: 'state',
                title: '订单状态',
                align: 'left',
                sortable: true,
                width: 70
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

//设置产品表格
function setProductTable() {
    $('#product-table-toolbar').css({
        'display': 'block'
    })

    if ($('#product-table').html != '') {
        $('#product-table').bootstrapTable('load', productInfoList);
    }

    $('#product-table').bootstrapTable({
        data: productInfoList,
        classes: 'table table-hover',
        clickToSelect: true,
        locale: 'zh-CN',
        toolbar: '#product-table-toolbar',
        toolbarAlign: 'left',
        columns: [
            {
                field: 'productName',
                title: '产品名称',
                align: 'left',
                width: 150,
                formatter: function (value, row, index) {
                    return '<input type="text" class="form-control" value="' + value + '"/>'
                }
            }, {
                field: 'productNum',
                title: '数量',
                align: 'left',
                width: 70,
                formatter: function (value, row, index) {
                    return '<input type="text" class="form-control" value="' + value + '"/>'
                }
            }, {
                field: 'productPrice',
                title: '单价',
                align: 'left',
                width: 70,
                formatter: function (value, row, index) {
                    return '<input type="text" class="form-control" value="' + value + '"/>'
                }
            }, {
                field: 'matterInfo',
                title: '操作',
                align: 'left',
                width: 70,
                formatter: function (value, row, index) {
                    let btnType = 'primary';
                    if (value.length == 0) {
                        btnType = 'danger'
                    }
                    return '<button onclick="javascript:showMatterInfo(' + index + ')" type="button" class="btn btn-' + btnType + '">' +
                        '<i class="bi bi-inbox icon"></i>' +
                        '选择物料' +
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

    $('.fixed-table-container').addClass('border-top').addClass('border-bottom');
}

//显示物料窗体
function showMatterInfo(index) {
    let productName = $('#product-table tbody tr:eq(' + index + ') td:eq(0)').children().val();
    let productNum = parseInt($('#product-table tbody tr:eq(' + index + ') td:eq(1)').children().val());
    let productPrice = parseInt($('#product-table tbody tr:eq(' + index + ') td:eq(2)').children().val());
    if (productName == '' || productNum == 0 || productPrice == 0) {
        alert('请先填写产品信息')
        return;
    } else {
        productInfoList[index].productName = productName;
        productInfoList[index].productNum = productNum;
        productInfoList[index].productPrice = productPrice;
    }

    $('#product-modal').modal('hide');
    $('#productIndex').val(index);
    $('#matter-modal').modal('show');
    setMatterTable(index);

    $('#matter-modal').on('hidden.bs.modal', function (e) {
        setProductTable();
    })
}

//获取物料集合
function getMatterList() {
    let projectId = $('#projectSelect').val();
    $ajax({
        type: 'post',
        url: '/matter_info/selectListOfUseByProjectId',
        data: JSON.stringify({
            projectId: projectId
        }),
        contentType: "application/json;charset=utf-8",
        dataType: 'json'
    }, false, '', function (res) {
        if (res.code != 200) {
            alert(res.msg)
        } else {
            matterList = res.data;
        }
    })
}

//设置物料表格
function setMatterTable(idx) {

    if ($('#matter-table').html() != '') {
        $('#matter-table').bootstrapTable('load', matterList);
    }

    $('#matter-table').bootstrapTable({
        data: matterList,
        sortStable: true,
        classes: 'table table-hover',
        idField: 'id',
        locale: 'zh-CN',
        columns: [
            {
                field: 'matterProjectId',
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
                width: 100
            }, {
                field: 'matterNum',
                title: '可用数量',
                align: 'left',
                width: 50
            }, {
                field: 'num',
                title: '订单数量',
                align: 'left',
                width: 50,
                formatter: function (value, row, index) {
                    let idx = parseInt($('#productIndex').val());
                    let num = productInfoList[idx].matterInfo.num
                    return '<input type="number" class="form-control" value="' + num + '"/>'
                }
            }, {
                field: 'price',
                title: '单价',
                align: 'left',
                width: 70,
                formatter: function (value, row, index) {
                    let idx = parseInt($('#productIndex').val());
                    let price = productInfoList[idx].matterInfo.price
                    return '<input type="number" class="form-control" value="' + price + '"/>'
                }
            }
        ]
    })
    $('.fixed-table-container').addClass('border-top').addClass('border-bottom');
}

