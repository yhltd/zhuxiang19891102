function getList(callback) {
    $ajax({
        type: 'post',
        url: '/work_order_detail/getListByWorkShop',
    }, false, '', function (res) {
        if (res.code == 200) {
            setTable(res.data);
            if(callback != undefined) callback();
        }
    })
}

$(function () {
    //获取集合
    getList();

    $('#refresh-btn').click(function(){
        getList(function(){
            alert('已刷新');
        })
    })


    //点击按钮根据时间查询
    $("#select-btn").click(function () {
        let startDate = $("#startDate").val();
        let endDate = $("#endDate").val() ;

        $ajax({
            type: 'post',
            url: '/work_order_detail/getWorkShopListDate',
            data: JSON.stringify({
                startDate: startDate,
                endDate: endDate
            }),
            dataType: 'json',
            contentType: 'application/json;charset=utf-8'
        }, false, '', function (res) {
            alert(res.msg);
            if (res.code == 200) {
                setTable(res.data);
            }
        })
    })
})

function setTable(data) {

    if ($('#workShopTable').html != '') {
        $('#workShopTable').bootstrapTable('load', data);
    }

    $('#workShopTable').bootstrapTable({
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
                field: 'workShop',
                title: '车间',
                align: 'left',
                sortable: true,
                width: 100
            }, {
                field: 'matterName',
                title: '物料名称',
                align: 'left',
                sortable: true,
                width: 100
            }, {
                field: 'workNum',
                title: '数量',
                align: 'left',
                sortable: true,
                width: 100
            }
        ],
    })
}