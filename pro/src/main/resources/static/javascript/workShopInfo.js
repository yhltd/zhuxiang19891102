function getList() {
    $ajax({
        type: 'post',
        url: '/work_order_detail/getListByWorkShop',
    }, false,'' , function (res) {
        if (res.code == 200) {
            setTable(res.data);
        }
        console.log(res)
    })
}

$(function (){
    //获取集合
    getList();

    //点击按钮根据时间查询
    $("#select-btn").click(function (){
        var startDate=$("#startDate").val()+ ':00';
        var endDate=$("#startDate").val()+ ':00';
        if(startDate==':00'){
            alert("请选择时间再点击查询")
        }else if(endDate==':00'){
            alert("请选择时间再点击查询")
        }else{
            $ajax({
                type: 'post',
                url: '/work_order_detail/getWorkShopListDate',
                data: JSON.stringify({
                    startDate: startDate,
                    endDate:endDate
                }),
                dataType: 'json',
                contentType: 'application/json;charset=utf-8'
            },false, '' ,function (res){
                if (res.code == 200) {
                    $('#workShopTable').bootstrapTable('load', res.data);
                }
                console.log(res)
            })
        }
    })
})

function setTable(data) {
    $('#table-toolbar').css({
        'opacity': 1
    })

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
                align: 'left',
                width: 100,
                formatter: function (value, row, index) {
                    return index + 1;
                }
            },{
                field: 'workShop',
                title: '车间',
                align: 'left',
                sortable: true,
                width: 150
            }, {
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
    })
}