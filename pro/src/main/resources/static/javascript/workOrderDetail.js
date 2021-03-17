function getList(){
    $ajax({
        type: 'post',
        url: '/work_order_detail/getList',
    }, false,'' , function (res) {
        if (res.code == 200) {
            setTable(res.data);
        }
    })
}

$(function () {
    //获取数据getListByWorkOrder
    getList();

    //删除
    $('#delete-btn').click(function(){
        var msg=confirm("确认要删除吗？")
        if(msg){
            let rows = getTableSelection("#workOrderDetailTable");
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
                url: '/work_order_detail/delete',
                data: JSON.stringify({
                    idList: idList
                }),
                dataType: 'json',
                contentType: 'application/json;charset=utf-8'
            },false,'' ,function (res) {
                if (res.code == 200) {
                    getList();
                }
            })
        }
    })
})

$(function(){
    //点击修改按钮事件
    $('#update-btn').click(function () {
        let rows = getTableSelection('#workOrderDetailTable')
        if (rows.length > 1 || rows.length == 0) {
            alert('请选择一条数据修改');
            return;
        }
        $('#detail-modal').modal('show');
        setForm(rows[0].data, '#update-form');
    })

    //修改项目提交按钮点击事件
    $('#update-form-submit-btn').click(function () {
        var msg = confirm("确认要修改吗？")
        if(msg){
            if (checkForm('#update-form')) {
                let params = formToJson('#update-form');
                $ajax({
                    type: 'post',
                    url: '/work_order_detail/update',
                    data: {
                        workOrderDetailJson: JSON.stringify(params)
                    },
                    dataType: 'json',
                    contentType: 'application/json;charset=utf-8'
                }, false, '', function (res) {
                    alert(res.msg);
                    if (res.code == 200) {
                        $('#update-form-close-btn').click();
                        let rows = getTableSelection('#workOrderDetailTable');
                        $('#workOrderDetailTable').bootstrapTable('updateRow', {
                            index: rows[0].index,
                            row: res.data
                        })
                    }
                })
            }
        }
    })

    //修改项目关闭按钮点击事件
    $('#update-form-close-btn').click(function () {
        $('#update-form')[0].reset();
        $('#detail-modal').modal('hide');
    })


})




/**
 * 动态表格
 * @param data
 */
function setTable(data) {

    if ($('#workOrderDetailTable').html != '') {
        $('#workOrderDetailTable').bootstrapTable('load', data);
    }

    $('#workOrderDetailTable').bootstrapTable({
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
                field: 'workOrder',
                title: '派工单单号',
                align: 'left',
                sortable: true,
                width: 150
            }, {
                field: 'workDate',
                title: '日期',
                align: 'left',
                sortable: true,
                width: 150,
                formatter: function (value, row, index) {
                    let date = new Date(value);
                    let format = formatDate(date,"yyyy-MM-dd")
                    let weekArray = ["星期日","星期一","星期二","星期三","星期四","星期五","星期六"]
                    return format + '/' + weekArray[date.getDay()];
                }
            }, {
                field: 'workShop',
                title: '车间',
                align: 'left',
                sortable: true,
                width: 100
            }, {
                field: 'workLine',
                title: '产线',
                align: 'left',
                sortable: true,
                width: 100
            },
            {
                field: 'matterName',
                title: '物料编码',
                align: 'left',
                sortable: true,
                width: 100
            },{
                field: 'workNum',
                title: '数量',
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


