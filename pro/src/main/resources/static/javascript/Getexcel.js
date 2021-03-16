//导出全部列
function getexcel(tableid,filename){
    $(tableid).tableExport({
        type: 'excel', //导出数据类型
        tableName: filename,
        exportDataType: "all",  //是否全部导出//basic', 'all' 全部, 'selected'选中.
        fileName: filename,//下载文件名称
        onCellHtmlData: function (cell, row, col, data){//处理导出内容,自定义某一行、某一列、某个单元格的内容
            console.info(data);
            return data;
        },
    });
}



//可以去除某一列
function getexcelnolast(tableid,filename,colnum){
    $(tableid).tableExport({
        tableName: filename,
        exportDataType: "all",  //是否全部导出//basic', 'all' 全部, 'selected'选中.
        ignoreColumn: [colnum],//忽略某一列的索引
        fileName: filename,//下载文件名称
        onCellHtmlData: function (cell, row, col, data){//处理导出内容,自定义某一行、某一列、某个单元格的内容
            console.info(data);
            return data;
        },
    });
}
