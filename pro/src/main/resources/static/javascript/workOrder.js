let tableDatas = {
    '#table1': [],
    '#table2': [],
    '#table3': [],
    '#table4': [],
    '#table5': [],
    '#table6': [],
    '#table7': [],
}

let tableId = '';

let minDate;

let matters;

function getOrders() {
    let $s = $('#add-orderInfoId');
    $s.html('');
    let $o = $('<option value="">请选择订单</option>');
    $s.append($o)

    $ajax({
        type: 'post',
        url: '/order_info/post_list'
    }, false, '', function (res) {
        if (res.code == 200) {
            $.each(res.data, function (index, o) {
                $o = $('<option value="' + o.orderId + '">' + o.orderId + '</option>')
                $s.append($o)
            })
        } else {
            alert(res.msg);
            $s.html('');
        }
    })

    $s.change(function () {
        getProducts($(this).val());
    })
}

function getProducts(orderId) {
    $ajax({
        type: 'post',
        url: '/matter_order/select_list',
        data: JSON.stringify({
            orderId: orderId,
            code: ''
        }),
        contentType: 'application/json;charset=utf-8',
        dataType: 'json'
    }, false, '', function (res) {
        let $s = $('#add-productName');
        $s.html('');
        let $o = $('<option value="">请选择物料</option>');
        $s.append($o)

        if (res.code == 200) {
            matters = res.data;
            $.each(matters, function (index, o) {
                $o = $('<option value="' + o.id + '">' + o.code + '</option>')
                $s.append($o)
            })
        } else {
            alert(res.msg);
            $s.html('');
        }

        $s.change(function () {
            setProductNum(0, $(this).val());
        })
    })
}

function setProductNum(num, id) {
    let result = true;
    $.each(matters, function (index, matter) {
        if (matter.id == id) {
            result = matter.num - num >= 0;
            matter.num = result ? matter.num -= num : matter.num;
            $('#add-productNum').val(matter.num);
            return false;
        }
    })
    return result;
}

function getMinDate() {
    $('.row').css('display', 'none');
    $('#modal').modal('show');
    $('#weekFirstDate').attr('disabled', 'disabled');
    $ajax({
        type: 'post',
        url: '/work_order_detail/get_min'
    }, '', false, function (res) {
        if (res.code == 200) {
            minDate = res.data;
            $('#weekFirstDate').removeAttr('disabled');
            getOrders();
        } else {
            alert(res.msg)
        }
    })
}

$(function () {
    getMinDate();

    $('#submit-btn').click(function () {
        let weekFirstDate = $('#weekFirstDate').val();

        let check = function (date) {
            if (date == '') {
                return '请选择本周周一日期';
            } else {
                let myDate = new Date(date);
                if (minDate != undefined) {
                    let myMinDate = new Date(minDate)
                    if (myDate < myMinDate) {
                        let myFormatDate = formatDate(myMinDate, 'yyyy年MM月dd日');
                        return '请选择' + myFormatDate + '或者' + myFormatDate + '之后的周一';
                    }
                }
                if (myDate.getDay() != 1) {
                    return '该日期不是星期一';
                }
            }
            return '';
        }
        let result = check(weekFirstDate)
        if (result == '') {
            let weekArray = new Array("周日", "周一", "周二", "周三", "周四", "周五", "周六");
            let myDate = new Date(weekFirstDate);
            let linkDate;
            $('.nav-link').each(function (index, link) {
                linkDate = new Date(myDate.getTime() + (1000 * 60 * 60 * 24 * index));
                let date = formatDate(linkDate, 'yyyy-MM-dd');
                let week = weekArray[linkDate.getDay()];
                $(link).text(date + '/' + week);
            })

            $('#modal').modal('hide');
            $('.row').css('display', 'flex');
        } else {
            alert(result);
        }
    })

    //获取用户点击的页面
    $('.nav-link').click(function () {
        tableId = '#table' + ($(this).index() + 1)
        setTable();
    })

    //新增点击提交按钮
    $("#add-submit-btn").click(function () {
        if (checkForm("#add-form")) {
            let params = formToJson('#add-form')
            let productId = $('#add-productName').val();
            if (setProductNum(params.workNum, productId)) {
                let tableIndex = parseInt(tableId.replace('#table', '')) - 1;
                let workDate = $('.nav-link:eq(' + tableIndex + ')').text().split('/')[0];
                tableDatas[tableId].push({
                    id: getId(),
                    productName: $('#add-productName').find('option:selected').text(),
                    ...params,
                    workDate: workDate
                })
                setTable()
                $('#add-modal').modal('hide');
            } else {
                alert('生产数量不能大于产品数量')
            }
        }
    })

    //点击关闭按钮
    $('#add-close-btn').click(function () {
        $('#add-modal').modal('hide');
    })
})

//新增点击事件
function addDetail() {
    if (tableId == '') {
        alert('请选择日期');
        return;
    }

    $('#add-productName').change();
    $('#add-modal').modal('show');
}

//删除点击事件
function deleteDetail() {
    let rows = getTableSelection(tableId);
    if (rows.length == 0) {
        alert('请至少选择一条数据删除')
        return;
    }

    $.each(rows, function (index, row) {
        let tableData = tableDatas[tableId];
        if ($.inArray(row.data, tableData) >= 0) {
            for (let i = 0; i < matters.length; i++) {
                if (matters[i].id == parseInt(row.data.matterId)) {
                    matters[i].num += parseInt(row.data.workNum);
                }
            }
            tableData.splice($.inArray(row.data, tableData), 1);
        }
    })
    setTable();
}

function getId() {
    let id = 1;
    let rows = $(tableId).bootstrapTable('getData');
    if (rows.length > 0 && rows[0].nodeType != 1) {
        $.each(rows, function (index, row) {
            if (row.id > id) {
                id = row.id;
            }
        })
    }
    return id;
}

//提交
function saveDetail() {
    let workOrderDetailList = [];
    for (let tableData in tableDatas) {
        $.each(tableDatas[tableData], function (index, table) {
            workOrderDetailList.push(table)
        })
    }

    if (workOrderDetailList.length == 0) {
        alert('输入派工单信息');
        return;
    }

    $ajax({
        type: 'post',
        url: '/work_order_detail/add',
        data: JSON.stringify({
            workOrderDetailList: workOrderDetailList
        }),
        contentType: 'application/json;charset=utf-8',
        dataType: 'json'
    }, false, '', function (res) {
        alert(res.msg)
        if (res.code == 200) {
            window.location.href = window.location.href
        }
    })
}

function setTable() {
    if ($(tableId).html() != '') {
        $(tableId).bootstrapTable('load', tableDatas[tableId])
    }

    $(tableId).bootstrapTable({
        data: tableDatas[tableId],
        sortStable: true,
        classes: 'table table-hover',
        idField: 'id',
        pagination: false,
        clickToSelect: true,
        locale: 'zh-CN',
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
                field: 'workDate',
                title: '日期',
                align: 'left',
                sortable: false,
                width: 100,
                formatter: function (value, row, index) {
                    return formatDate(new Date(value), 'yyyy-MM-dd');
                }
            }, {
                field: 'orderInfoId',
                title: '订单',
                align: 'left',
                sortable: false,
                width: 150,
            }, {
                field: 'productName',
                title: '物料',
                align: 'left',
                sortable: false,
                width: 100,
            }, {
                field: 'workShop',
                title: '车间',
                align: 'left',
                sortable: false,
                width: 100,
            }, {
                field: 'workLine',
                title: '产线',
                align: 'left',
                sortable: false,
                width: 100,
            }, {
                field: 'workNum',
                title: '生产数量',
                align: 'left',
                sortable: false,
                width: 100,
            },
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