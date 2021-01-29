let displays = {
    '车间生产汇总' : {
        '#add-display': 'none',
        '#delete-display': 'none',
        '#update-display': 'none',
        '#select-display': 'block'
    },
    '产线生产汇总' : {
        '#add-display': 'none',
        '#delete-display': 'none',
        '#update-display': 'none',
        '#select-display': 'block'
    }
}

let userInfoId;

function getList() {
    $ajax({
        type: 'post',
        url: '/user/getList',
    }, false, '', function (res) {
        if (res.code == 200) {
            setTable(res.data);
        }
    })
}

$(function () {
    //显示所有user
    getList();

    $('#add-viewName-power').change(function(){
        let vn = $(this).val();
        if(displays[vn] == undefined){
            $('#add-display').css('display','block');
            $('#delete-display').css('display','block');
            $('#update-display').css('display','block');
            $('#select-display').css('display','block');
        }else{
            for(let id in displays[vn]){
                $(id).css('display',displays[vn][id]);
            }
        }
    })

    //点击新增按钮显示弹窗
    $("#add-btn").click(function () {
        $('#add-modal').modal('show');
    })

    //新增弹窗里点击提交按钮
    $("#add-submit-btn").click(function () {
        if (checkForm("#add-form")) {
            let params = formToJson("#add-form")
            $ajax({
                type: 'post',
                url: '/user/add',
                data: JSON.stringify({
                    addUserInfo: params
                }),
                dataType: 'json',
                contentType: 'application/json;charset=utf-8'
            }, false, '', function (res) {
                alert(res.msg)
                if(res.code == 200){
                    $('#add-form')[0].reset();
                    getList();
                    $('#add-close-btn').click();
                }
            })
        }
    })

    //新增弹窗里点击关闭按钮
    $('#add-close-btn').click(function () {
        $('#add-modal').modal('hide');
    })

    //点击修改按钮显示弹窗
    $('#update-btn').click(function () {
        let rows = getTableSelection('#userInfoTable')
        if (rows.length > 1 || rows.length == 0) {
            alert('请选择一条数据修改');
            return;
        }
        $('#update-modal').modal('show');
        setForm(rows[0].data, '#update-form');
    })

    //修改弹窗里点击提交按钮
    $('#update-submit-btn').click(function () {
        var msg = confirm("确认要修改吗？")
        if (msg) {
            if (checkForm('#update-form')) {
                let params = formToJson('#update-form');
                $ajax({
                    type: 'post',
                    url: '/user/update',
                    data: {
                        userInfoJson: JSON.stringify(params)
                    },
                    dataType: 'json',
                    contentType: 'application/json;charset=utf-8'
                }, false, '', function (res) {
                    alert(res.msg);
                    if (res.code == 200) {
                        $('#update-close-btn').click();
                        $('#update-modal').modal('hide');
                        getList();
                    }
                })
            }
        }
    })

    //修改弹窗点击关闭按钮
    $('#update-close-btn').click(function () {
        $('#update-form')[0].reset();
        $('#update-modal').modal('hide');
    })

    //点击删除按钮
    $('#delete-btn').click(function () {
        var msg = confirm("确认要删除吗？")
        if (msg) {
            let rows = getTableSelection("#userInfoTable");
            if (rows.length == 0) {
                alert('')
                return;
            }
            let idList = [];
            $.each(rows, function (index, row) {
                idList.push(row.data.id)
            })
            $ajax({
                type: 'post',
                url: '/user/delete',
                data: JSON.stringify({
                    idList: idList
                }),
                dataType: 'json',
                contentType: 'application/json;charset=utf-8'
            }, false, '', function (res) {
                alert(res.msg);
                if (res.code == 200) {
                    getList();
                }
            })
        }
    })

    /**
     * 权限
     */
    //点击权限新增按钮
    $("#add-btn-userPower").click(function () {
        let data = $('#power-table').bootstrapTable('getData');
        let options = $('#add-viewName-power option');
        options.each(function(j,option){
            $(option).css('display','block');
        })

        $.each(data,function(i,d){
            options.each(function(j,option){
                if($(option).val() == d.viewName){
                    $(option).css('display','none');
                    return false;
                }
            })
        })
        $('#add-modal-power').modal('show');
    })


    //权限新增弹窗里点击提交按钮
    $("#add-submit-btn-power").click(function () {
        if (checkForm("#add-form-power")) {
            let addUserPower = formToJson("#add-form-power")
            addUserPower.userInfoId = userInfoId;
            $ajax({
                type: 'post',
                url: '/user_power/add',
                data: JSON.stringify({
                    addUserPower: addUserPower
                }),
                dataType: 'json',
                contentType: 'application/json;charset=utf-8'
            }, false, '', function (res) {
                alert(res.msg)
                if(res.code == 200){
                    $('#power-table').bootstrapTable('insertRow',{
                        index: $('#power-table').bootstrapTable('getData').length,
                        row: res.data
                    })
                    $('#add-close-btn-power').click();
                }
            })
        }
    })

    //权限新增弹窗里点击关闭按钮
    $('#add-close-btn-power').click(function () {
        $('#add-modal-power').modal('hide');
        $('#add-form-power')[0].reset();
    })

    //点击权限修改按钮显示弹窗
    $('#update-btn-userPower').click(function () {
        let rows = getTableSelection('#power-table')
        if (rows.length > 1 || rows.length == 0) {
            alert('请选择一条数据修改');
            return;
        }
        $('#update-modal-power').modal('show');
        setForm(rows[0].data, '#update-form-power');
    })

    //权限修改弹窗里点击提交按钮
    $('#update-submit-btn-power').click(function () {
        var msg = confirm("确认要修改吗？")
        if (msg) {
            if (checkForm('#update-form-power')) {
                let params = formToJson('#update-form-power');
                params.userInfoId = userInfoId
                $ajax({
                    type: 'post',
                    url: '/user_power/update',
                    data: {
                        userInfoJson: JSON.stringify(params)
                    },
                    dataType: 'json',
                    contentType: 'application/json;charset=utf-8'
                }, false, '', function (res) {
                    alert(res.msg);
                    if (res.code == 200) {
                        $('#update-close-btn-power').click();
                        let rows = getTableSelection('#power-table');
                        $('#power-table').bootstrapTable('updateRow', {
                            index: rows[0].index,
                            row: res.data
                        })
                    }
                })
            }
        }
    })

    //修改弹窗点击关闭按钮
    $('#update-close-btn-power').click(function () {
        $('#update-modal-power').modal('hide');
    })

    //权限点击删除按钮
    $('#delete-btn-userPower').click(function () {
        let rows = getTableSelection("#power-table");
        if (rows.length == 0) {
            alert('请至少选择一条数据删除')
            return;
        }

        let msg = confirm("确认要删除吗？")
        if (msg) {
            let idList = [];
            $.each(rows, function (index, row) {
                idList.push(row.data.id)
            })
            $ajax({
                type: 'post',
                url: '/user_power/delete',
                data: JSON.stringify({
                    idList: idList
                }),
                dataType: 'json',
                contentType: 'application/json;charset=utf-8'
            }, false, '', function (res) {
                alert(res.msg)
                if (res.code == 200) {
                    $('#power-table').bootstrapTable('remove',{
                        field: 'id',
                        values: idList
                    })
                }
            })
        }
    })
})

/**
 * 动态表格
 * @param data
 */
function setTable(data) {
    if ($('#userInfoTable').html != '') {
        $('#userInfoTable').bootstrapTable('load', data);
    }

    $('#userInfoTable').bootstrapTable({
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
                field: 'name',
                title: '账号',
                align: 'left',
                sortable: true,
                width: 100
            }, {
                field: 'pwd',
                title: '密码',
                align: 'left',
                sortable: true,
                width: 100,
            }, {
                field: 'powerName',
                title: '身份',
                align: 'left',
                sortable: true,
                width: 100
            }, {
                field: '',
                title: '操作',
                align: 'left',
                sortable: true,
                width: 100,
                formatter: function (value, row, index) {
                    $('#add-userInfoId-power').val(row.id);
                    return '<button onclick="javascript:showDetail(' + row.id + ')" class="btn btn-primary">管理权限</button>'
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

function showDetail(id) {
    userInfoId = id;
    $('#power-modal').modal('show');

    $ajax({
        type: 'post',
        url: '/user_power/getList',
        data: {
            id: id
        }
    }, false, '', function (res) {
        if (res.code == 200) {
            setPowerTable(res.data)
        }
    })
}

function setPowerTable(data){
    if($('#power-table').html() != ''){
        $('#power-table').bootstrapTable('load',data);
    }

    $('#power-table').bootstrapTable({
        data: data,
        sortStable: true,
        classes: 'table table-hover',
        idField: 'id',
        pagination: true,
        clickToSelect: true,
        locale: 'zh-CN',
        toolbar: '#table-toolbar-userPower',
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
                field: 'userInfoId',
                title: '用户Id',
                visible: false
            }, {
                field: 'viewName',
                title: '页面',
                align: 'left',
                sortable: true,
                width: 150
            }, {
                field: 'adds',
                title: '新增',
                align: 'left',
                sortable: true,
                width: 150,
                formatter: function (value, row, index) {
                    return value == '1' ? '可操作' : '无权限';
                }
            }, {
                field: 'deletes',
                title: '删除',
                align: 'left',
                sortable: true,
                width: 150,
                formatter: function (value, row, index) {
                    return value == '1' ? '可操作' : '无权限';
                }
            }, {
                field: 'updates',
                title: '修改',
                align: 'left',
                sortable: true,
                width: 150,
                formatter: function (value, row, index) {
                    return value == '1' ? '可操作' : '无权限';
                }
            }
            , {
                field: 'selects',
                title: '查询',
                align: 'left',
                sortable: true,
                width: 150,
                formatter: function (value, row, index) {
                    return value == '1' ? '可操作' : '无权限';
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