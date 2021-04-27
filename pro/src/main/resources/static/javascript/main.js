// function alert(msg){
//     let $alerts = $('#alerts')
//     if($alerts.length == 0){
//         $alerts = $('<div id="alerts" class="alerts"></div>')
//         $('body').append($alerts)
//     }
//     let $alert = $('<div class="alert alert-primary alert-dismissible fade show">'+msg+'</div>')
//     $alerts.append($alert)
// }

function $ajax(options, isLoading, loadingEl, success) {
    $.ajax({
        timeout: 5000,
        ...options,
        beforeSend: function () {
            if (isLoading) {

            }
        },
        success: res => {
            if (res.code == 401) {
                alert(res.msg);
                return;
            }
            success(res);
        },
        error: err => {
            if(err.responseText == 'loseToken'){
                alert('身份验证已过期，请重新登录');
                window.location.href = '/';
                return;
            }

            if (err.status == 'timeout') {
                alert('网络超时，请稍后再试。')
            } else {
                alert('网络错误，请稍后再试。')
            }
        },
        complete: res => {
            if (isLoading) {

            }
        }
    })
}

function loadingShow(el) {

}

function loadingHide(el) {

}

function formToJson(el) {
    let formData = $(el).serialize();
    let deCode = decodeURIComponent(formData);
    deCode = deCode.replace(/&/g, "\",\"").replace(/=/g, "\":\"").replace(/\+/g, " ").replace(/[\r\n]/g, "<br>");
    deCode = "{\"" + deCode + "\"}";
    return JSON.parse(deCode);
}

function setForm(params, el) {
    for (let param in params) {
        $(el + ' input').each(function (index, input) {
            if ($(input).attr('name') == param) {
                $(input).val(params[param]);
                return false;
            }
        })
    }
}

function checkForm(el) {
    let result = true
    $(el + ' input').each(function(index,input){
        let isRequired = $(input).data('required');
        if(isRequired == "1"){
            return true;
        }
        if($(input).val() == '' || $(input).val() <= 0){
            $(input).next().css('display','block')
            result = false
        }else{
            $(input).next().css('display','none')
        }
    })
    $(el + ' select').each(function(index,select){
        if($(select).val() == '' || $(select).val() == undefined){
            $(select).next().css('display','block')
            result = false
        }else{
            $(select).next().css('display','none')
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
        "yyyy": date.getFullYear(),
        "M": date.getMonth() + 1,
        "d": date.getDate(),
        "H": date.getHours(),
        "m": date.getMinutes(),
        "s": date.getSeconds(),
        "MM": ("" + (date.getMonth() + 101)).substr(1),
        "dd": ("" + (date.getDate() + 100)).substr(1),
        "HH": ("" + (date.getHours() + 100)).substr(1),
        "mm": ("" + (date.getMinutes() + 100)).substr(1),
        "ss": ("" + (date.getSeconds() + 100)).substr(1)
    };
    return format.replace(/(yyyy|MM?|dd?|HH?|ss?|mm?)/g, function () {
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


$(function () {
    $('#out-a').click(function(){
        window.location.href = '/';
    })

    //点击修改密码显示弹窗
    $("#updPwd-a").click(function () {
        $('#update-modal').modal('show');
    })

    //点击提交按钮
    $("#update-submit-btn").click(function () {
        if(checkForm('#updPwdForm')) {
            let params = formToJson('#updPwdForm')
            if (params.newPwd != params.newPwdAgain) {
                alert('两次新密码不相同')
                return;
            }

            $ajax({
                type: 'post',
                url: '/user/updatePwd',
                data: JSON.stringify({
                    oldPwd: params.oldPwd,
                    newPwd: params.newPwd
                }),
                contentType: 'application/json;charset=utf-8',
                dataType: 'json'
            }, false, '', function (res) {
                if (res.code == 200) {
                    alert(res.msg)
                    $('#update-modal').modal('hide');
                    $('#updPwdForm')[0].reset();
                }
            })
        }
    })
    //点击关闭按钮
    $("#update-close-btn").click(function () {
        $('#update-modal').modal('hide');
    })
})