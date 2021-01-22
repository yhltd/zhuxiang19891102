function getList(){
    $ajax({
        type: 'post',
        url: '/work_order_detail/getList',
    }, false,'' , function (res) {
        if (res.code == 200) {
            setTable(res.data);
        }
        console.log(res)
    })
}

$(function () {
    //获取数据
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
                    //$('#table').bootstrapTable('removeAll');
                    getList();
                }
                console.log(res)
            })
            // $('#workOrderDetailTable').bootstrapTable({
            //     remove:function (field,values){
            //         field:getTableSelection("#workOrderDetailTable");
            //         values:getTableSelection("#workOrderDetailTable");
            //     }
            // })
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
        var msg=confirm("确认要修改吗？")
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
    $('#table-toolbar').css({
        'opacity': 1
    })

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
                title: '周几',
                align: 'left',
                sortable: true,
                width: 150,
                formatter: function (value, row, index) {
                    let date = new Date(row.workDate);
                    let week = date.getDay();
                    let weekArray = ["星期日","星期一","星期二","星期三","星期四","星期五","星期六"]
                    return weekArray[week];
                }
            }, {
                field: 'workShop',
                title: '车间',
                align: 'left',
                sortable: true,
                width: 150
            }, {
                field: 'workLine',
                title: '产线',
                align: 'left',
                sortable: true,
                width: 150
            },
            {
                field: 'productName',
                title: '产品名称',
                align: 'left',
                sortable: true,
                width: 150
            },{
                field: 'workNum',
                title: '数量',
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


