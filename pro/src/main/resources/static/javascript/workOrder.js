let tableDatas = {
    '#table1': [],
    '#table2': [],
    '#table3': [],
    '#table4': [],
    '#table5': [],
    '#table6': [],
    '#table7': [],
}

let tableIndex = 0;

/**
 * 点击提交按钮
 */
$(function () {
    $('#submit-btn').click(function () {
        var a=[];
        $.each(tableDatas,function (index,item){
            $.each(item,function (index1,item1){
                a.push(item1)
            })
        })
        $ajax({
            type: 'post',
            url: '/work_order_detail/add',
            data: JSON.stringify({
                workOrderDetailList:a,
            }),
            dataType: 'json',
            contentType: 'application/json;charset=utf-8'
        }, false, '', function (res) {
            if(res.code==200){
                alert("成功")
            }
        })
        console.log(tableDatas)
    })

    //获取用户点击的页面
    $('.nav-link').click(function(){
        console.log($(this).index);
    })

    //点击新增一行按钮
    // $('#add-btn').click(function (){
    //     $('#add-modal').modal('show');
    //
    //     $ajax({
    //         type: 'post',
    //         url: '/work_order_detail/getProductList',
    //     }, false, '', function (res) {
    //         if(res.code==200){
    //             setForm(res.data,'#add-form')
    //         }
    //     })
    // })

    //新增点击提交按钮
    $("#add-submit-btn").click(function (){
        if (checkForm("#add-form")){
            let params = formToJson('#add-form')
            let tableId = '#table'+(tableIndex+1)
            tableDatas[tableId].push(params)
            setTable(tableId)
            $('#add-modal').modal('hide');
            $(tableId).bootstrapTable('load',tableDatas[tableIndex])
        }
    })

    //点击关闭按钮
    $('#add-close-btn').click(function (){
        $('#add-modal').modal('hide');
    })




})
//点击新增一行
function showDetail(){
    $('#add-modal').modal('show');

}

// function getParams(){
//     let result = []
//     $('#workOrderTable tr').each(function(i,tr){
//         if(i == 0){
//             return true;
//         }
//         let item = {}
//         $(tr).children().each(function(j,td){
//             if(j == 0){
//                 return true;
//             }
//             let name = $(td).children().attr('name');
//             let val = $(td).children().val();
//             if (val!=""){
//                 item[name] = val;
//             }else{
//                 return false;
//             }
//         })
//         if (JSON.stringify(item) != "{}"){
//             result.push(item)
//         }else{
//             return false;
//         }
//     })
//     return result;
// }


function setTable(tableId) {
    $('#table-toolbar').css({
        'opacity': 1
    })

    if($(tableId).html() != ''){
        $(tableId).bootstrapTable('load',tableDatas[tableId])
    }

    $(tableId).bootstrapTable({
        data: tableDatas[tableId],
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
                field: 'workDate',
                title: '日期',
                align: 'left',
                sortable: false,
                width: 150,
            }, {
                field: 'orderInfoId',
                title: '订单',
                align: 'left',
                sortable: false,
                width: 150,
            }, {
                field: 'productInfoId',
                title: '产品',
                align: 'left',
                sortable: false,
                width: 150,
            },{
                field: 'workShop',
                title: '车间',
                align: 'left',
                sortable: false,
                width: 150,
            },{
                field: 'workLine',
                title: '产线',
                align: 'left',
                sortable: false,
                width: 150,
            },{
                field: 'workNum',
                title: '产品数量',
                align: 'left',
                sortable: false,
                width: 150,
            },
        ],
    })
}