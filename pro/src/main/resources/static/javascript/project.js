$(function () {
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

            $ajax({
                type: 'post',
                url: '/project_info/update',
                data: {
                    projectInfoJson: JSON.stringify(params)
                }
            }, false, '', function (res) {
                alert(res.msg);
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

    //修改项目关闭按钮点击事件
    $('#edit-form-close-btn').click(function () {
        $('#edit-form')[0].reset();
        $('#edit-modal').modal('hide');
    })

    //新增按钮点击事件
    $('#add-btn').click(function () {
        $('#add-form')[0].reset();
        $('#add-modal').modal('show');
    })

    //新增项目提交按钮点击事件
    $('#add-form-submit-btn').click(function () {
        if (checkForm('#add-form')) {
            $ajax({
                type: 'post',
                url: '/matter_info/getList'
            }, false, '', function (res) {
                $('#add-modal').modal('hide');
                $('#matter-modal').modal('show');
                setMatterTable(res.data);
            })
        }
    })

    //新增项目关闭按钮点击事件
    $('#add-form-close-btn').click(function () {
        $('#add-modal').modal('hide');
    })

    //选择物料确定按钮点击事件
    $('#matter-submit-btn').click(function () {
        let matterProductList = getMatterProductList('#table-matter')
        let projectInfo = formToJson('#add-form');

        $ajax({
            type: 'post',
            url: '/project_info/add',
            data: JSON.stringify({
                projectInfo: projectInfo,
                matterProjectList: matterProductList
            }),
            contentType: "application/json;charset=utf-8",
            dataType: 'json'
        }, false, '', function (res) {
            alert(res.msg);
            if (res.code == 200) {
                $('#matter-modal').modal('hide');
                $('#table').bootstrapTable('append', [res.data])
            }
        })
    })

    //选择物料返回按钮点击事件
    $('#matter-close-btn').click(function () {
        $('#matter-modal').modal('hide');
        $('#add-modal').modal('show');
    })

    //删除按钮点击事件
    $('#delete-btn').click(function () {
        let rows = getTableSelection('#table')
        if (rows.length == 0) {
            alert('请选择一条数据删除');
            return;
        }
        $('#delete-modal').modal('show');
    })

    //确定删除按钮点击事件
    $('#delete-submit-btn').click(function () {
        let rows = getTableSelection('#table')
        let idList = [];
        $.each(rows, function (index, value) {
            idList.push(value.data.id)
        })

        $ajax({
            type: 'post',
            url: '/project_info/delete',
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

    $('#delete-close-btn').click(function () {
        $('#delete-modal').modal('hide');
    })

    //刷新按钮点击事件
    $('#refresh-btn').click(function () {
        $('#projectName-select').val('')
        $('#startDate-select').val('')
        $('#endDate-select').val('')
        getList(function () {
            alert('已刷新')
        })
    })

    //查询按钮点击事件
    $('#select-btn').click(function () {
        let selectPrams = {
            projectName: $('#projectName-select').val(),
            startDate: $('#startDate-select').val(),
            endDate: $('#endDate-select').val()
        }

        $ajax({
            type: 'post',
            url: '/project_info/selectList',
            data: JSON.stringify(selectPrams),
            contentType: "application/json;charset=utf-8",
            dataType: 'json'
        }, false, '', function (res) {
            alert(res.msg);
            if (res.code == 200) {
                setTable(res.data);
            }
        })
    })

    //查看物料确定修改按钮
    $('#upd-matter-submit-btn').click(function () {
        let params = {
            newList: getMatterProductList('#show-table-matter'),
            projectId: parseInt($('#showMatterProjectId').val())
        }
        console.log(params)

        $ajax({
            type: 'post',
            url: '/matter_project/updateAndAdd',
            data: JSON.stringify(params),
            contentType: "application/json;charset=utf-8",
            dataType: 'json'
        }, false, '', function (res) {
            alert(res.msg)
            if (res.code == 200) {
                $('#show-matter-modal').modal('hide');
            }
        })
    })

    //查看物料关闭按钮
    $('#upd-matter-close-btn').click(function () {
        $('#show-matter-modal').modal('hide')
    })

    //获取表格数据
    getList()

    //查看物料点击新增物料按钮
    $('#add-matter-submit-btn').click(function () {
         let projectId=$('#showMatterProjectId').val();
        $ajax({
            type: 'post',
            url: '/matter_info/getNotMatter',
            data: JSON.stringify({
                projectId: projectId
            }),
            contentType: 'application/json;charset=utf-8',
            dataType: 'json'
        }, false, '', function (res) {
            $('#show-matter-modal').modal('show');
            setShowMatterTable(res.data)
        })

    })
})

function selectMatter() {
    $ajax({
        type: 'post',
        url: '/matter_info/getList'
    }, false, '', function (res) {
        $('#show-matter-modal').modal('show');
        setShowMatterTable(res.data)
    })
}

function selectMatterByProjectId(projectId) {
    $('#showMatterProjectId').val(projectId);
    $ajax({
        type: 'post',
        url: '/matter_info/selectListByProjectId',
        data: JSON.stringify({
            projectId: projectId
        }),
        contentType: 'application/json;charset=utf-8',
        dataType: 'json'
    }, false, '', function (res) {
        $('#show-matter-modal').modal('show');
        setShowMatterTable(res.data)
    })
}

function setShowMatterTable(data) {
    console.log(data)
    if ($('#show-table-matter').html() != '') {
        $('#show-table-matter').bootstrapTable('load', data);
        return;
    }
    $('#show-table-matter').bootstrapTable({

        data: data,
        sortStable: true,
        classes: 'table table-hover',
        idField: 'id',
        pagination: true,
        search: true,
        searchAlign: 'left',
        clickToSelect: true,
        locale: 'zh-CN',
        columns: [
            // {
            //     field: 'id',
            //     title: '序号',
            //     align: 'center',
            //     width: 50,
            //     formatter: function (value, row, index) {
            //         return index + 1;
            //     }
            // }
             {
                field: 'matterNum',
                title: '更改数量',
                align: 'left',
                sortable: true,
                width: 120,
                formatter: function (value, row, index) {
                    return '<input type="number" class="form-control" value="' + value + '"/>'
                }
            },{
                field: 'matterNum',
                title: '数量',
                align: 'left',
                sortable: true,
                width: 150,
                visible:true,
            },
            {
                field: 'id',
                title: '序号',
                align: 'center',
                width: 50,
                formatter: function (value, row, index) {
                    return index + 1;
                }
            }
            ,{
                field: 'code',
                title: '物料代码',
                align: 'left',
                sortable: true,
                width: 150
            },  {
                field: 'type',
                title: '类别',
                align: 'left',
                sortable: true,
                width: 100,
            }, {
                field: 'size',
                title: '长度(英尺)',
                align: 'left',
                sortable: true,
                width: 100
            },{
                field: 'meter',
                title: '长度(米)',
                align: 'left',
                sortable: true,
                width: 100
            }, {
                field: 'yieldStrength',
                title: '屈服强度',
                align: 'left',
                sortable: true,
                width: 100
            },
            {
                field: 'chartThickness',
                title: '图层厚度',
                align: 'left',
                sortable: true,
                width: 100
            },{
                field: 'thickness',
                title: '料厚',
                align: 'left',
                sortable: true,
                width: 100
            }, {
                field: 'color',
                title: '颜色',
                align: 'left',
                sortable: true,
                width: 100
            }, {
                field: 'supplier',
                title: '供应商',
                align: 'left',
                sortable: true,
                width: 120
            }, {
                field: 'materialDescription',
                title: '物料描述',
                align: 'left',
                sortable: true,
                width: 120
            }, {
                field: 'weight',
                title: '产品单重',
                align: 'left',
                sortable: true,
                width: 120
            }, {
                field: 'numberPackages',
                title: '单位包装个数',
                align: 'left',
                sortable: true,
                width: 120
            }, {
                field: 'traySize',
                title: '托盘尺寸',
                align: 'left',
                sortable: true,
                width: 120
            },{
                field: 'createTime',
                title: '录入时间',
                align: 'left',
                sortable: true,
                width: 150
            }

            // },{
            //     field: 'matterNum',
            //     title: '所需数量',
            //     align: 'left',
            //     sortable: true,
            //     width: 120,
            //     formatter: function (value, row, index) {
            //         return '<input type="number" class="form-control" value="' + value + '"/>'
            //     }
            // }
        ]
    })
}

function setMatterTable(data) {
    if ($('#table-matter').html() != '') {
        $('#table-matter').bootstrapTable('load', data);
        return;
    }
    $('#table-matter').bootstrapTable({
        data: data,
        sortStable: true,
        classes: 'table table-hover',
        idField: 'id',
        pagination: false,
        search: true,
        searchAlign: 'left',
        clickToSelect: false,
        locale: 'zh-CN',
        columns: [
            {
                field: 'matterNum',
                title: '所需数量',
                align: 'left',
                sortable: true,
                width: 120,
                formatter: function (value, row, index) {
                    return '<input type="number" class="form-control"/>'
                }
            },
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
            },  {
                field: 'type',
                title: '类别',
                align: 'left',
                sortable: true,
                width: 100,
            }, {
                field: 'size',
                title: '长度(英尺)',
                align: 'left',
                sortable: true,
                width: 100
            },{
                field: 'meter',
                title: '长度(米)',
                align: 'left',
                sortable: true,
                width: 100
            }, {
                field: 'yieldStrength',
                title: '屈服强度',
                align: 'left',
                sortable: true,
                width: 100
            },
            {
                field: 'chartThickness',
                title: '图层厚度',
                align: 'left',
                sortable: true,
                width: 100
            },{
                field: 'thickness',
                title: '料厚',
                align: 'left',
                sortable: true,
                width: 100
            }, {
                field: 'color',
                title: '颜色',
                align: 'left',
                sortable: true,
                width: 100
            }, {
                field: 'supplier',
                title: '供应商',
                align: 'left',
                sortable: true,
                width: 120
            }, {
                field: 'materialDescription',
                title: '物料描述',
                align: 'left',
                sortable: true,
                width: 120
            }, {
                field: 'createTime',
                title: '录入时间',
                align: 'left',
                sortable: true,
                width: 150
            }
            // ,{
            //     field: 'matterNum',
            //     title: '所需数量',
            //     align: 'left',
            //     sortable: true,
            //     width: 120,
            //     formatter: function (value, row, index) {
            //         return '<input type="number" class="form-control"/>'
            //     }
            // }
        ]
    })
}

function getList(callback) {
    $ajax({
        type: 'post',
        url: '/project_info/getList',
    }, false, '', function (res) {
        if (res.code == 200) {
            setTable(res.data);
            if (callback != undefined) {
                callback();
            }
        }
    })
}

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
                field: '',
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
                field: 'projectAddress',
                title: '项目地址',
                align: 'left',
                sortable: true,
                width: 150
            }, {
                field: 'customerName',
                title: '客户名称',
                align: 'left',
                sortable: true,
                width: 150
            },{
                field: 'postcode',
                title: '邮政编码',
                align: 'left',
                sortable: true,
                width: 150
            }, {
                field: 'createTime',
                title: '创建时间',
                align: 'left',
                sortable: true,
                width: 150,
                formatter: function (value, row, index) {
                    return formatDate(new Date(value), "yyyy-MM-dd HH:mm:ss");
                }
            }, {
                field: 'outNum',
                title: '出库数量',
                align: 'left',
                sortable: true,
                width: 150,
            }, {
                field: 'id',
                title: '操作',
                align: 'left',
                width: 100,
                formatter: function (value, row, index) {
                    return '<button onclick="javascript:selectMatterByProjectId(' + value + ')" type="button" class="btn btn-primary">' +
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

function getMatterProductList(el) {
    let matterProjectList = [];
    $(el + ' tr').each(function (index, tr) {
        let dataIndex = $(tr).data('index');
        if (dataIndex == undefined) return true;

        let id = $(el).bootstrapTable('getData')[dataIndex].id;
        let num = $(tr).children().first().children().val();
        if (num == '' || num <= 0) return true;

        matterProjectList.push({
            id: 0,
            projectInfoId: 0,
            matterInfoId: parseInt(id),
            matterNum: parseInt(num),
        })
    })
    return matterProjectList;
}