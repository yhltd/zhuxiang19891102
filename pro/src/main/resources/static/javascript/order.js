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

    //新增按钮点击事件(新增订单：查询projectInfo表得到项目信息)
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
                    //add-modal:新增订单的form表单的id
                    $('#add-modal').modal('show');
                }
            }
        })
    })

    //新增订单提交按钮
    $('#add-form-submit-btn').click(function () {
        if (checkForm('#add-form')) {
            $('#add-modal').modal('hide');
            getMatterList($('#projectSelect').val());
            $('#matter-modal').modal('show');
        }
    })

    //选择物料确定按钮
    $('#matter-submit-btn').click(function () {
        let projectId = $('#projectSelect').val();
        let orderId=$('#add-orderId').val();
        let comment = $('#add-comment').val();
        let matterOrderList = [];
        let check = true;
        $('#matter-table tbody tr').each(function(index, tr){
            let useNum = $(tr).children().last().children().val();
            if(useNum==''||useNum<=0){
                return check
            }
            if(useNum > matterList[index].matterNum){
                alert('订单所用数量不能大于物料数量，物料代码：' + matterList[index].code + ',行号：' + (index+1));
                check = false;
                return check;
            }else{
                matterOrderList.push({
                    matterId: matterList[index].id,
                    num: useNum
                })
            }
        })

        if(check){
            $ajax({
                type: 'post',
                url: '/order_info/add',
                data: JSON.stringify({
                    projectId,
                    orderId,
                    comment,
                    matterOrderList
                }),
                contentType: "application/json;charset=utf-8",
                dataType: 'json'
            }, false, '', function (res) {
                alert(res.msg)
                if (res.code == 200) {
                    $('#matter-modal').modal('hide');
                    getList();
                }
            })
        }
    })

    //选择物料返回按钮
    $('#matter-back-btn').click(function () {
        $('#matter-modal').modal('hide');
        $('#add-modal').modal('show');
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

    if ($('#table').html != '') {
        $('#table').bootstrapTable('load', data);
    }

    $('#table').bootstrapTable({
        data: data,
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
                width: 120
            }, {
                field: 'orderId',
                title: '订单号',
                align: 'left',
                sortable: true,
                width: 120
            }, {
                field: 'comment',
                title: '贸易条款',
                align: 'left',
                sortable: true,
                width: 120
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
                width: 100
            },{
                field: 'outNum',
                title: '出库数量',
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

//获取物料集合
function getMatterList(projectId) {
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
            //设置物料表格
            setMatterTable(matterList)
        }
    })
}

//设置物料表格
function setMatterTable() {
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
                width: 150
            }, {
                field: 'materialDescription',
                title: '物料描述',
                align: 'left',
                sortable: true,
                width: 120
            }, {
                field: 'matterNum',
                title: '可用数量',
                align: 'left',
                width: 120
            }, {
                field: 'num',
                title: '物料数量',
                align: 'left',
                width: 120,
                 formatter: function (value, row, index) {
                     return '<input type="number" class="form-control"/>'
                 }
            }
        ]
    })
}

