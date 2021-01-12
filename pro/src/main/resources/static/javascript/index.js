$(function(){
    $("#submit-btn").click(function(){
        let params = formToJson('#login-form')
        $ajax({
            type: 'post',
            url: 'user/login',
            data: {
                name: params.name,
                pwd: params.pwd
            }
        },false,function(res){
            console.log(res)
            alert(res.message)
            if(res.code>0){
                window.location.href = "html/main.html";
            }
        })
    })
})