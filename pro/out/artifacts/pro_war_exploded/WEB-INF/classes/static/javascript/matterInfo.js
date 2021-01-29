function getList() {
    $ajax({
        type: 'post',
        url: '/matter_info/getList',
    }, false, '', function (res) {
        if (res.code == 200) {
            setTable(res.data);
        }
        console.log(res)
    })
}

$(function () {
    //显示所有物料
    getList();
    //点击按钮根据项目名模糊查询物料
    $("#select-btn1").click(function () {
        var projectName = $("#projectName").val();
        if (projectName == "") {
            alert("请输入项目名进行查询")
        } else {
            $ajax({
                type: 'post',
                url: '/matter_info/selectListByProjectName',
                data: {
                    projectName: projectName
                }

            }, false, '', function (res) {
                if (res.code == 200) {
                    $('#matterInfoTable').bootstrapTable('load', res.data);
                }
                console.log(res)
            })
        }
    })

    //点击新增按钮显示弹窗
    $("#add-btn").click(function () {
        $('#add-modal').modal('show');
        //setForm(rows[0].data, '#add-form')
    })

    //新增弹窗里点击提交按钮
    $("#add-submit-btn").click(function () {
        if (checkForm("add-form")) {
            let addMatterInfo = formToJson("#add-form")
            $ajax({
                type: 'post',
                url: '/matter_info/add',
                data: {
                    matterInfoJson: JSON.stringify(addMatterInfo)
                },
                dataType: 'json',
                contentType: 'application/json;charset=utf-8'
            }, false, '', function (res) {
                alert(res.msg)
                if (res.code == 200) {
                    $('#add-modal').modal('hide');
                    getList();
                }
            })
        }
    })

    //新增弹窗关闭按钮点击事件
    $('#add-close-btn').click(function () {
        $('#add-form')[0].reset();
        $('#add-modal').modal('hide');
    })

    //点击按钮显示修改弹窗事件
    $('#update-btn').click(function () {
        let rows = getTableSelection('#matterInfoTable')
        if (rows.length > 1 || rows.length == 0) {
            alert('请选择一条数据修改');
            return;
        }
        $('#update-modal').modal('show');
        setForm(rows[0].data, '#update-form');
    })

    //点击修改按钮提交事件
    $('#update-matter-btn').click(function () {
        var msg = confirm("确认要修改吗？")
        if (msg) {
            if (checkForm('#update-form')) {
                let params = formToJson('#update-form');
                $ajax({
                    type: 'post',
                    url: '/matter_info/update',
                    data: {
                        matterInfoJson: JSON.stringify(params)
                    },
                    dataType: 'json',
                    contentType: 'application/json;charset=utf-8'
                }, false, '', function (res) {
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
    $('#close-matter-btn').click(function () {
        $('#update-form')[0].reset();
        $('#update-modal').modal('hide');
    })

    //点击删除按钮事件
    $('#delete-btn').click(function () {
        let rows = getTableSelection("#matterInfoTable");
        if (rows.length == 0) {
            alert('请至少选择一条数据删除')
            return;
        }
        $('#delete-modal').modal('show');
    })

    $('#delete-submit-btn').click(function(){
        let rows = getTableSelection("#matterInfoTable");

        let idList = [];
        $.each(rows, function (index, row) {
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
        }, false, '', function (res) {
            alert(res.msg);
            if (res.code == 200) {
                getList();
                $('#delete-close-btn').click();
            }
        })
    })

    $('#delete-close-btn').click(function(){
        $('#delete-modal').modal('hide');
    })
})

/**
 * 动态表格
 * @param data
 */
function setTable(data) {

    if ($('#matterInfoTable').html != '') {
        $('#matterInfoTable').bootstrapTable('load', data);
    }

    $('#matterInfoTable').bootstrapTable({
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
                field: '',
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
            }, {
                field: 'color',
                title: '颜色',
                align: 'left',
                sortable: true,
                width: 150
            }, {
                field: 'fittingsProportion',
                title: '配件比例',
                align: 'left',
                sortable: true,
                width: 150
            }, {
                field: 'fittingsNum',
                title: '配件数量',
                align: 'left',
                sortable: true,
                width: 150
            }, {
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
}