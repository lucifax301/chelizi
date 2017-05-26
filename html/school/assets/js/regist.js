//关闭加载层
win.hideLoading();
/***************************************************用户登陆*********************************************************************/
$(function(){
    $("#registForm").Validform({
        tiptype:3,
        showAllError:true,
        btnSubmit:"#admin-submit",
        callback:function(){
            $.AJAX({
                url:config.basePath+"school/apply",
                type:"post",
                data:getFormJson($("#registForm")),
                success:function(data){
                    $(".login").fadeOut().next(".apply-success").fadeIn();
                }
            })
            return false;
        }
    })

    //$("#admin-submit").click(function(){
    //    rForm.config({ajaxurl:config.basePath+"school/apply",ajaxpost:{
    //        url:config.basePath+"school/apply",
    //        type:"post",
    //        data:getFormJson($("#registForm")),
    //        success:function(data){
    //            console.log(data);
    //            alert("提交成功")
    //        }
    //    }})
    //})
})



function getFormJson(form) {
    var o = {};
    var a = $(form).serializeArray();
    $.each(a, function () {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
}


/*城市下拉选择*/
$("#chooseCity").click(function(e){
    //阻止冒泡
    e = e || window.event;
    if(e.stopPropagation) {e.stopPropagation();}
    else {e.cancelBubble = true;}
    $(this).next(".regist-city").css("display","block");
})
$(".regist-city li").click(function(){
    $("#cityId").attr("value",$(this).attr("data-cityid"));
    $("#city").attr("value",$(this).text());
    $("#chooseCity span").text($(this).text()).removeClass("z-fontlight");
})
$(document).click(function(){
    $(".regist-city").css("display","none");
    if($("#cityId").val()){$("#chooseCity").siblings(".Validform_checktip").removeClass("Validform_wrong").empty()}
})

//$("#commentForm").validate();
//提交表单
//$("#admin-submit").click(function(){
//    $("#registForm").Validform({tiptype:2,callback:function(){
//        alert("可以提交了")
//    }});
//})





/*展示用户名*/
$('#username').val(localStorage.getItem("school-username"));




