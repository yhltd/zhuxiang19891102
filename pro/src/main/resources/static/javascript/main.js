function formToJson(el) {
    let formData = $(el).serialize();
    let deCode = decodeURIComponent(formData);
    deCode = deCode.replace(/&/g, "\",\"").replace(/=/g, "\":\"").replace(/\+/g, " ").replace(/[\r\n]/g, "<br>");
    deCode = "{\"" + deCode + "\"}";
    return JSON.parse(deCode);
}

function $ajax(options,isLoading,success){
    $.ajax({
        ...options,
        beforeSend: function(){
            if(isLoading){

            }
        },
        success: res=> {
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