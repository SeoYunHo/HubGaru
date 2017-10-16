$(document).ready(function(){
    $("#login").click(function(){
        let id=$("#id").val();
        let password=$("#pwd").val();

        console.log(id, password);

        $.ajax({
            url:'/account/signin',
            data:{
                id: id,
                password: password
            },
            type: 'POST',
            success: function(){
                alert('야!! 성공했다!!')
            },
            error: function(xhr,status,error){
                console.log(status);
                console.log(error);
                alert('ㅠㅠ')
            }
        })
    })
});
