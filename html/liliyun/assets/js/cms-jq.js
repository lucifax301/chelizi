/*JQUERY 的相关（比较散乱）代码放在里面*/
$(function(){

/*首页top栏目消息展开与缩放*/
$('#top').find('.nav-slide').click(function(event){
	event.stopPropagation();
	$('div.nav-slide').find('ul').slideUp();
	var slideObj=$(this).find("ul");
	if(slideObj.css("display")=="none"){
		slideObj.slideDown('fast');
	}else{
		slideObj.slideUp('fast');
	} 
})

/*--后台irfame操作父窗口响应相关--*/
$(window).click(function(){
	if($(win).width()<=768){
		$(win.document).find("#admin-nav").slideUp('fast');
	}
	$(win.document).find(".nav-slide ul").slideUp('fast');
});

/*更新搜索框提示文字*/
$("#search_type").change(function(){
    $("#search_placeholder").attr("placeholder","输入"+$(this).children("option:selected").text()+"查询")
})

/*图片预览*/
    $("body")
        .on("click",".previewImg",function(){
        var html='<div class="preview-wrap"><div class="preview-bg"></div><div class="preview-img" id="previewImg"></div><i class="preview-close">X</i></div>';
        $("body").append(html);
        $(this).clone(true).appendTo($("#previewImg"))
        $(".preview-wrap").show();
    })
        .on("click",".preview-close",function(){
        $(".preview-wrap").hide();
        $(".preview-wrap").remove();
    })






});












