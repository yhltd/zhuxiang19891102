/**
 * 点击提交按钮
 */
$(function () {
    $('#submit-btn').click(function () {
        let params = getParams();
        $ajax({
            type: 'post',
            url: '/work_order_detail/add',
            data: JSON.stringify({
                workOrderDetailList:params,
            }),
            dataType: 'json',
            contentType: 'application/json;charset=utf-8'
        }, false, '', function (res) {
            console.log(params)
        })
        console.log(params)
    })
})


function getParams(){
    let result = []
    $('#workOrderTable tr').each(function(i,tr){
        if(i == 0){
            return true;
        }
        let item = {}
        $(tr).children().each(function(j,td){
            if(j == 0){
                return true;
            }
            let name = $(td).children().attr('name');
            let val = $(td).children().val();
            if (val!=""){
                item[name] = val;
            }else{
                return false;
            }
        })
        if (JSON.stringify(item) != "{}"){
            result.push(item)
        }else{
            return false;
        }
    })
    return result;
}