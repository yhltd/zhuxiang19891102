$(function(){
    $('table').bootstrapTable('post-body.bs.table',function(){
        alert('OK');
    })
})


function $ajax(options,isLoading,loadingEl,success){
    $.ajax({
        ...options,
        beforeSend: function(){
            if(isLoading){

            }
        },
        success: res=> {
            if(res.code == 401){
                alert(res.msg);
                return;
            }
            success(res);
        },
        error: err=>{
            if(err.status=='timeout'){
                alert('网络超时，请稍后再试。')
            }else{
                alert('网络错误，请稍后再试。')
            }
        },
        complete: res=>{
            if(isLoading){

            }
        }
    })
}

function loadingShow(el){

}

function loadingHide(el){

}

function formToJson(el) {
    let formData = $(el).serialize();
    let deCode = decodeURIComponent(formData);
    deCode = deCode.replace(/&/g, "\",\"").replace(/=/g, "\":\"").replace(/\+/g, " ").replace(/[\r\n]/g, "<br>");
    deCode = "{\"" + deCode + "\"}";
    return JSON.parse(deCode);
}

function setForm(params,el){
    for(let param in params){
        $(el + ' input').each(function(index,input){
            if($(input).attr('name')==param){
                $(input).val(params[param]);
                return false;
            }
        })
    }
}

function checkForm(el){
    let result = true
    $(el + ' input').each(function(index,input){
        if($(input).val() == '' || $(input).val() == 0){
            $(input).next().css('display','block')
            result = false
        }else{
            $(input).next().css('display','none')
        }
    })
    return result;
}

function formatDate(date, format) {
    if (!date) return;
    if (!format)
        format = "yyyy-MM-dd";
    switch (typeof date) {
        case "string":
            date = new Date(date.replace(/-/, "/"));
            break;
        case "number":
            date = new Date(date);
            break;
    }
    if (!date instanceof Date) return;
    var dict = {
        "yyyy" : date.getFullYear(),
        "M" : date.getMonth() + 1,
        "d" : date.getDate(),
        "H" : date.getHours(),
        "m" : date.getMinutes(),
        "s" : date.getSeconds(),
        "MM" : ("" + (date.getMonth() + 101)).substr(1),
        "dd" : ("" + (date.getDate() + 100)).substr(1),
        "HH" : ("" + (date.getHours() + 100)).substr(1),
        "mm" : ("" + (date.getMinutes() + 100)).substr(1),
        "ss" : ("" + (date.getSeconds() + 100)).substr(1)
    };
    return format.replace(/(yyyy|MM?|dd?|HH?|ss?|mm?)/g, function() {
        return dict[arguments[0]];
    });
}

//获取表格选择行
function getTableSelection(tableEl) {
    let result = [];
    let tableData = $(tableEl).bootstrapTable('getData');
    $(tableEl + ' tr').each(function (i, tr) {
        let index = $(tr).data('index');
        if (index != undefined) {
            if ($(tr).hasClass('selected')) {
                result.push({
                    index: index,
                    data: tableData[index]
                })
            }
        }
    })
    return result;
}

//选择表格行
function setTableSelection(tableEl, rowIndex, isSelect) {
    $(tableEl + ' tr').each(function (i, tr) {
        let index = $(tr).data('index');
        if (index == rowIndex) {
            if (isSelect) {
                $(tr).addClass('selected')
            } else {
                $(tr).removeClass('selected')
            }
        }
    })
}


$(function (){
    //点击修改密码显示弹窗
    $("#update-href").click(function (){
        $('#update-modal').modal('show');
    })

    //点击提交按钮
    $("#update-submit-btn").click(function (){
        let pwd=$('#update-pwd').val();
        if(pwd==""){
            alert("请输入新密码")
        }else{
            $ajax({
                type: 'post',
                url: '/user/updatePwd',
                data:{
                    pwd:pwd
                }
            },false, '', function (res){
                if (res.code==200){
                    alert("修改成功,请重新登录")
                    $('#add-modal').modal('hide');
                    window.location.href="html/main.html";
                }
            })
        }
    })



    //点击关闭按钮
    $("#update-close-btn").click(function (){
        $('#update-modal').modal('hide');
    })



})