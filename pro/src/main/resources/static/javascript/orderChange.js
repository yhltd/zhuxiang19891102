$(function () {
    //查询按钮点击事件
    $('#select-btn').click(function () {
        let params = {
            code: $('#code-select').val()
        }

        $ajax({
            type: 'post',
            url: '/matter_order_change/select_list',
            data: JSON.stringify(params),
            contentType: 'application/json;charset=utf-8',
            dataType: 'json'
        }, false, '', function (res) {
            alert(res.msg);
            if (res.code == 200) {
                setTable(res.data);
            }
        })
    })

    // //修改按钮点击事件
    // $('#edit-btn').click(function(){
    //     let rows = getTableSelection('#table');
    //     if(rows.length != 1){
    //         alert('请选择一条数据修改')
    //         return;
    //     }
    //
    //     $('#edit-modal').modal({
    //         height: 400
    //     });
    //     $('#edit-modal').modal('show');
    //     setForm(rows[0].data,'#edit-form');
    // })
    //
    // //修改窗口提交按钮点击事件
    // $('#edit-form-submit-btn').click(function(){
    //     if(checkForm('#edit-form')){
    //         let params = formToJson('#edit-form');
    //
    //         $ajax({
    //             type: 'post',
    //             url: '/matter_project_change/update',
    //             data: {
    //                 matterProductChangeJson: JSON.stringify(params)
    //             }
    //         },false,'',function(res){
    //             alert(res.msg);
    //             if(res.code==200){
    //                 $('#edit-form-close-btn').click();
    //                 let rows = getTableSelection('#table');
    //                 $('#table').bootstrapTable('updateRow', {
    //                     index: rows[0].index,
    //                     row: res.data
    //                 })
    //             }
    //         })
    //     }
    // })
    //
    // //修改窗口关闭按钮
    // $('#edit-form-close-btn').click(function(){
    //     $('#edit-form')[0].reset();
    //     $('#edit-modal').modal('hide');
    // })
    //
    // //删除按钮点击事件
    // $('#delete-btn').click(function () {
    //     let rows = getTableSelection('#table');
    //     if (rows.length == 0) {
    //         alert("请至少选择一条数据删除")
    //         return;
    //     }
    //
    //     $('#delete-modal').modal('show');
    // })
    //
    // //确定删除点击事件
    // $('#delete-submit-btn').click(function(){
    //     let idList = [];
    //     let rows = getTableSelection('#table');
    //     $.each(rows, function (index, row) {
    //         idList.push(row.data.id)
    //     })
    //
    //     $ajax({
    //         type: 'post',
    //         url: '/matter_project_change/delete',
    //         data: JSON.stringify({
    //             idList: idList
    //         }),
    //         contentType: 'application/json;charset=utf-8',
    //         dataType: 'json'
    //     }, false, '', function (res) {
    //         alert(res.msg)
    //         if (res.code == 200) {
    //             $('#delete-close-btn').click();
    //             getList();
    //         }
    //     })
    // })
    //
    // //取消删除按钮点击事件
    // $('#delete-close-btn').click(function(){
    //     $('#delete-modal').modal('hide');
    // })

    //刷新按钮点击事件
    $('#refresh-btn').click(function () {
        $('#code-select').val('')
        getList(function(){
            alert('已刷新');
        });

    })



    getMatterInfo();
})

let matterList;

function getList(callback) {
    $ajax({
        type: 'post',
        url: '/matter_order_change/post_list'
    }, false, '', function (res) {
        if (res.code == 200) {
            setTable(res.data);
            if(callback != undefined)
                callback();
        }
    })
}

function getMatterInfo(){
    $ajax({
        type: 'post',
        url: '/matter_info/getList'
    }, false, '', function (res) {
        if (res.code == 200) {
            matterList = res.data;
            getList();
        }
    })
}

function setTable(data) {
    console.log(data)
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
                field: 'orderId',
                title: '订单号',
                align: 'left',
                sortable: true,
                width: 150
            }, {
                field: 'code',
                title: '物料编码',
                align: 'left',
                sortable: true,
                width: 100,
                formatter: function(value, row, index){
                    for(let i = 0;i<matterList.length;i++){
                        if(matterList[i].id == row.matterInfoId){
                            return matterList[i].code;
                        }
                    }
                    return '-';
                }
            },{
                field: 'materialDescription',
                title: '物料描述',
                align: 'left',
                sortable: true,
                width: 150,
                formatter: function(value, row, index){
                    for(let i = 0;i<matterList.length;i++){
                        if(matterList[i].id == row.matterInfoId){
                            return matterList[i].materialDescription;
                        }
                    }
                    return '-';
                }
            }, {
                field: 'oldNum',
                title: '修改前数量',
                align: 'left',
                sortable: true,
                width: 120
            }, {
                field: 'newNum',
                title: '修改后数量',
                align: 'left',
                sortable: true,
                width: 120
            }, {
                field: 'updateTime',
                title: '修改时间',
                align: 'left',
                sortable: true,
                width: 150,
                formatter: function (value, row, index) {
                    return formatDate(new Date(value), "yyyy-MM-dd HH:mm:ss")
                }
            }, {
                field: 'updateMan',
                title: '修改人',
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