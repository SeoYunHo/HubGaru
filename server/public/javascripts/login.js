$(document).ready(function(){
    $("#login").click(function(){
        let id=$("#id").val();
        let password=$("#pwd").val();

        console.log(id, password);

        $.ajax({
            url:'/account/signin',
            data:{
                'id': id,
                'password': password
            },
            type: 'POST',
            statusCode: {
                500: function() {
                  alert('로그인 실패');
                },
                201: function() {
                    alert('로그인 성공');
                    location.href='/main';
                }
              }
        })
    })
});
