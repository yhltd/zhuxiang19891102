function getList(){
    $ajax({
        type: 'post',
        url: '/matter_info/getList',
    }, false,'' , function (res) {
        if (res.code == 200) {
            setTable(res.data);
        }
        console.log(res)
    })
}

$(function (){
    //显示所有物料
    getList();
    //点击按钮根据项目名模糊查询物料
    $("#select-btn1").click(function () {
        var projectName = $("#projectName").val();
        if(projectName==""){
            alert("请输入项目名进行查询")
        }else{
            $ajax({
                type: 'post',
                url: '/matter_info/selectListByProjectName',
                data: {
                    projectName: projectName
                }

            },false, '' ,function (res){
                if (res.code == 200) {
                    $('#matterInfoTable').bootstrapTable('load', res.data);
                }
                console.log(res)
            })
        }
    })

    //点击新增按钮显示弹窗
    $("#add-btn").click(function (){
        $('#add-modal').modal('show');
        //setForm(rows[0].data, '#add-form')
    })

    //新增弹窗里点击提交按钮
    $("#add-submit-btn").click(function (){
        if (checkForm("add-form")){
            let addMatterInfo=formToJson("#add-form")
            $ajax({
                type: 'post',
                url: '/matter_info/add',
                data:JSON.stringify({
                    addMatterInfo:addMatterInfo
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

    //新增弹窗关闭按钮点击事件
    $('#add-close-btn').click(function () {
        $('#add-form')[0].reset();
        $('#add-modal').modal('hide');
    })

    //点击按钮显示修改弹窗事件
    $('#update-btn').click(function (){
        let rows = getTableSelection('#matterInfoTable')
        if (rows.length > 1 || rows.length == 0) {
            alert('请选择一条数据修改');
            return;
        }
        $('#update-modal').modal('show');
        setForm(rows[0].data, '#update-form');
    })

    //点击修改按钮提交事件
    $('#update-submit-btn').click(function (){
        var msg=confirm("确认要修改吗？")
        if(msg){
            if (checkForm('#update-form')) {
                let params = formToJson('#update-form');
                $ajax({
                    type: 'post',
                    url: '/matter_info/update',
                    data:{
                        matterInfoJson:JSON.stringify(params)
                    },
                    dataType: 'json',
                    contentType: 'application/json;charset=utf-8'
                },false, '', function (res){
                    alert(res.msg);
                    if (res.code == 200) {
                        $('#update-close-btn').click();
                        let rows = getTableSelection('#matterInfoTable');
                        $('#matterInfoTable').bootstrapTable('updateRow', {
                            index: rows[0].index,
                            row: res.data
                        })
                        $('#update-modal').modal('hide');
                    }
                })
            }
        }
    })

    //修改弹窗关闭按钮点击事件
    $('#update-close-btn').click(function () {
        $('#update-form')[0].reset();
        $('#update-modal').modal('hide');
    })

    //点击删除按钮事件
    $('#delete-btn').click(function(){
        var msg=confirm("确认要删除吗？")
        if(msg){
            let rows = getTableSelection("#matterInfoTable");
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
                url: '/matter_info/delete',
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
})

/**
 * 动态表格
 * @param data
 */
function setTable(data) {
    $('#table-toolbar').css({
        'opacity': 1
    })

    if ($('#matterInfoTable').html != '') {
        $('#matterInfoTable').bootstrapTable('load', data);
    }

    $('#matterInfoTable').bootstrapTable({
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
                field: 'code',
                title: '物料代码',
                align: 'left',
                sortable: true,
                width: 150
            }, {
                field: 'type',
                title: '类别',
                align: 'left',
                sortable: true,
                width: 150,
            }, {
                field: 'size',
                title: '长度(英尺)',
                align: 'left',
                sortable: true,
                width: 150
            }, {
                field: 'yieldStrength',
                title: '屈服强度',
                align: 'left',
                sortable: true,
                width: 150
            },
            {
                field: 'chartThickness',
                title: '图层厚度',
                align: 'left',
                sortable: true,
                width: 150
            },{
                field: 'color',
                title: '颜色',
                align: 'left',
                sortable: true,
                width: 150
            },{
                field: 'fittingsProportion',
                title: '配件比例',
                align: 'left',
                sortable: true,
                width: 150
            },{
                field: 'fittingsNum',
                title: '配件数量',
                align: 'left',
                sortable: true,
                width: 150
            },{
                field: 'supplier',
                title: '供应商',
                align: 'left',
                sortable: true,
                width: 150
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