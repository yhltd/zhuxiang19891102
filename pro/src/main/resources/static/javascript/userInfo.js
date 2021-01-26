function getList(){
    $ajax({
        type: 'post',
        url: '/user/getList',
    }, false,'' , function (res) {
        if (res.code == 200) {
            setTable(res.data);
        }
        console.log(res)
    })
}

$(function (){
    //显示所有user
    getList();

    //点击新增按钮显示弹窗
    $("#add-btn").click(function (){
        $('#add-modal').modal('show');
    })

    //新增弹窗里点击提交按钮
    $("#add-submit-btn").click(function (){
        if (checkForm("add-form")){
            let addUserInfo=formToJson("#add-form")
            $ajax({
                type: 'post',
                url: '/user/add',
                data:JSON.stringify({
                    addUserInfo:addUserInfo
                }),
                dataType: 'json',
                contentType: 'application/json;charset=utf-8'
            }, false, '', function (res) {
                alert("提交成功")
                $('#add-modal').modal('hide');
                getList();
            })
        }
    })

    //新增弹窗里点击关闭按钮
    $('#add-close-btn').click(function () {
        $('#add-modal').modal('hide');
    })

    //点击修改按钮显示弹窗
    $('#update-btn').click(function (){
        let rows = getTableSelection('#userInfoTable')
        if (rows.length > 1 || rows.length == 0) {
            alert('请选择一条数据修改');
            return;
        }
        $('#update-modal').modal('show');
        setForm(rows[0].data, '#update-form');
    })

    //修改弹窗里点击提交按钮
    $('#update-submit-btn').click(function (){
        var msg=confirm("确认要修改吗？")
        if(msg){
            if (checkForm('#update-form')) {
                let params = formToJson('#update-form');
                $ajax({
                    type: 'post',
                    url: '/user/update',
                    data:{
                        userInfoJson:JSON.stringify(params)
                    },
                    dataType: 'json',
                    contentType: 'application/json;charset=utf-8'
                },false, '', function (res){
                    alert(res.msg);
                    if (res.code == 200) {
                        $('#update-close-btn').click();
                        let rows = getTableSelection('#userInfoTable');
                        // $('#matterInfoTable').bootstrapTable('updateRow', {
                        //     index: rows[0].index,
                        //     row: res.data
                        // })
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
    $('#delete-btn').click(function(){
        var msg=confirm("确认要删除吗？")
        if(msg){
            let rows = getTableSelection("#userInfoTable");
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
                url: '/user/delete',
                data: JSON.stringify({
                    idList: idList
                }),
                dataType: 'json',
                contentType: 'application/json;charset=utf-8'
            },false,'' ,function (res){
                if (res.code == 200) {
                    getList();
                }
                console.log(res)
            })
        }
    })

    /**
     * 权限
     */
    //点击权限新增按钮
    $("#add-btn-userPower").click(function (){
        $('#add-modal-power').modal('show');
    })


    //权限新增弹窗里点击提交按钮
    $("#add-submit-btn-power").click(function (){
        if (checkForm("add-form-power")){
            let addUserPower=formToJson("#add-form-power")
            $ajax({
                type: 'post',
                url: '/user_power/add',
                data:JSON.stringify({
                    addUserPower:addUserPower
                }),
                dataType: 'json',
                contentType: 'application/json;charset=utf-8'
            }, false, '', function (res) {
                alert("提交成功")
                $('#add-modal-power').modal('hide');
                //getList();
                $('#power-table').bootstrapTable('load', data);
            })
        }
    })

    //权限新增弹窗里点击关闭按钮
    $('#add-close-btn-power').click(function () {
        $('#add-modal-power').modal('hide');
    })

    //点击权限修改按钮显示弹窗
    $('#update-btn-userPower').click(function (){
        let rows = getTableSelection('#power-table')
        if (rows.length > 1 || rows.length == 0) {
            alert('请选择一条数据修改');
            return;
        }
        $('#update-modal-power').modal('show');
        setForm(rows[0].data, '#update-form-power');
    })

    //权限修改弹窗里点击提交按钮
    $('#update-submit-btn-power').click(function (){
        var msg=confirm("确认要修改吗？")
        if(msg){
            if (checkForm('#update-form-power')) {
                let params = formToJson('#update-form-power');
                $ajax({
                    type: 'post',
                    url: '/user_power/update',
                    data:{
                        userInfoJson:JSON.stringify(params)
                    },
                    dataType: 'json',
                    contentType: 'application/json;charset=utf-8'
                },false, '', function (res){
                    alert(res.msg);
                    if (res.code == 200) {
                        $('#update-close-btn-power').click();
                        let rows = getTableSelection('#power-table');
                        $('#power-table').bootstrapTable('updateRow', {
                            index: rows[0].index,
                            row: res.data
                        })
                        $('#update-modal-power').modal('hide');
                        $('#power-table').bootstrapTable('load', data);
                        //getList();
                    }
                })
            }
        }
    })

    //修改弹窗点击关闭按钮
    $('#update-close-btn-power').click(function () {
        $('#update-form-power')[0].reset();
        $('#update-modal-power').modal('hide');
    })

    //权限点击删除按钮
    $('#delete-btn-userPower').click(function(){
        var msg=confirm("确认要删除吗？")
        if(msg){
            let rows = getTableSelection("#power-table");
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
                url: '/user_power/delete',
                data: JSON.stringify({
                    idList: idList
                }),
                dataType: 'json',
                contentType: 'application/json;charset=utf-8'
            },false,'' ,function (res){
                if (res.code == 200) {
                    //getList();
                    $('#power-table').bootstrapTable('load', data);
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
    $('#table-toolbar').css({
        'opacity': 1
    })

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
            },{
                field: 'name',
                title: '姓名',
                align: 'left',
                sortable: true,
                width: 150
            }, {
                field: 'pwd',
                title: '密码',
                align: 'left',
                sortable: true,
                width: 150,
            }, {
                field: 'powerName',
                title: '权限',
                align: 'left',
                sortable: true,
                width: 150
            },{
                field: '',
                title: '操作',
                align: 'left',
                sortable: true,
                width: 150,
                formatter: function (value, row, index) {
                    $('#add-userInfoId-power').val(row.id);
                    return '<button onclick="javascript:showDetail(' +row.id +')" class="btn btn-primary">管理权限</button>'
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

function showDetail(id){
    $ajax({
        type: 'post',
        url: '/user_power/getList',
        data: {
            id: id
        }
    }, false,'' , function (res) {
        if (res.code == 200) {
            $('#power-modal').modal('show');
            $('#power-table').bootstrapTable('load', res.data).bootstrapTable({
                data: res.data,
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
                        field: '',
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
                    },{
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
                            if(value=="1"){
                                return '<input type="text" readonly="readonly" value="可操作" style="border: none" class="form-control"></input>'
                            }else if(value=="0"){
                                return '<input type="text" readonly="readonly" value="无权限" style="border: none" class="form-control"></input>'
                            }
                        }
                    }, {
                        field: 'deletes',
                        title: '删除',
                        align: 'left',
                        sortable: true,
                        width: 150,
                        formatter: function (value, row, index) {
                            if(value=="1"){
                                return '<input type="text" readonly="readonly" value="可操作" style="border: none" class="form-control"></input>'
                            }else if(value=="0"){
                                return '<input type="text" readonly="readonly" value="无权限" style="border: none" class="form-control"></input>'
                            }
                        }
                    }, {
                        field: 'updates',
                        title: '修改',
                        align: 'left',
                        sortable: true,
                        width: 150,
                        formatter: function (value, row, index) {
                            if(value=="1"){
                                return '<input type="text" readonly="readonly" value="可操作" style="border: none" class="form-control"></input>'
                            }else if(value=="0"){
                                return '<input type="text" readonly="readonly" value="无权限" style="border: none" class="form-control"></input>'
                            }
                        }
                    }
                    , {
                        field: 'selects',
                        title: '查询',
                        align: 'left',
                        sortable: true,
                        width: 150,
                        formatter: function (value, row, index) {
                            if(value=="1"){
                                return '<input type="text" readonly="readonly" value="可操作" style="border: none" class="form-control"></input>'
                            }else if(value=="0"){
                                return '<input type="text" readonly="readonly" value="无权限" style="border: none" class="form-control"></input>'
                            }
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
        console.log(res)
    })
}