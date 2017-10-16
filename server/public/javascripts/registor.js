$(document).ready(function(){
    $("#signup").click(function(){
        let name = $("#name").val();
        let email = $("#email").val();
        let id = $("#id").val();
        let password = $("#pwd").val();
        let checkPassword = $("#pwdCheck").val();
        let intro = $("#demo-message").val();

        console.log(name, email, id, password, checkPassword);

        $.ajax({
            url:'/account/signup',
            data:{
                id: id,
                password: password
            },
            type: 'POST',
            success: function(){
                alert('야!! 성공했다!!')
            },
            error: function(){
                alert('Shibal')
            } 
        })
    })
});