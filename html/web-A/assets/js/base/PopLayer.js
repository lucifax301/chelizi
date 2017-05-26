
var Layer=(function(){
	return new PopLayer();
})(window);

//构造函数
function PopLayer(){
	this.setting={
		width:300,
		height:160,
		time:2000,
		header:"信息",
		type:"msg",
		title:"请填写提示信息！"
	}
}

//信息层
PopLayer.prototype.alert=function(json){
	var type=defAttr(json,this.setting,"type");
	var str="<div class='PopLayer PopLayer-alert PopLayer-alert-type alertMask'><div class='PopLayer-h1'>"+defAttr(json,this.setting,"header")+"<span class='closeButton alertClose'></span></div> <div class='PopLayer-body PopLayer-body-"+type+"'><span></span><b>"+defAttr(json,this.setting,"title")+"</b></div> <div class='PopLayer-bottom'> <div class='PopLayer-bottom-OK alertOK'>确定</div> </div> </div>";                   
	$('body').append(str);
	this.mask("alertMask");  //显示遮罩
	animate($('.PopLayer-alert-type'),defAttr(json,this.setting,"width"),defAttr(json,this.setting,"height"));
	closeLayer($('.alertClose'),'alertMask');  //点击关闭按钮关闭
	closeLayer($('.alertOK'),'alertMask');  //确定	
	if(json.closeMask){
		closeLayer($('.alertMask'),'alertMask');//点击遮罩关闭
	}
}

//自定义HTML页面层
PopLayer.prototype.alertMor=function(json){
	var msg="";
	var showClass="";
	if(json.type=='success'){
		msg="操作成功";
		showClass="button-show"
	}else{
		msg="操作失败";
		showClass="button-hide"
	}
	var str="<div class='PopLayer PopLayer-alert PopLayer-alertMor alertMorMask'><div class='PopLayer-h1'>"+defAttr(json,this.setting,"header")+"<span class='closeButton customHtmlClose'></span></div>\
	 <div class='PopLayer-HTML'>\
	 	<div class='top "+json.type+"'><span></span><p>"+msg+"</p></div>\
	 	<div class='center'>"+json.title+"</div>\
	 	<div class='bottom'><button onclick=closeNowLayerMor() class='btn btn-sm btn-default "+showClass+"'>取消</button>\
			<button  class='btn btn-sm btn-primary' onclick=submitAlert(this)>确认</button></div>\
	 </div></div>";                   
	$('body').append(str);
	$('.PopLayer-HTML').css({"height":(defAttr(json,this.setting,"height")-60)+"px"});
	this.mask("alertMorMask");  //显示遮罩
	animate($('.PopLayer-alertMor'),defAttr(json,this.setting,"width"),defAttr(json,this.setting,"height"));
	closeLayer($('.customHtmlClose'),'alertMorMask',json);  //点击关闭按钮关闭
	if(json.closeMask){
		closeLayer($('.alertMorMask'),'alertMorMask',json);//点击遮罩关闭
	}
	/*内部提交函数*/
	window.submitAlert=function(obj){
		$(obj).attr("disabled","disabled");
		if(json.callback){
			json.callback();
		}else{
			Layer.closeNowLayer('alertMorMask',json);
		}
	};
	window.closeNowLayerMor=function(sclass){
		if(json.close){json.close();};
		$('.alertMorMask').remove();
	};
}

//自定义确认（附加文字）
PopLayer.prototype.confirmTextAlert=function(json,fn){
	this.setting.width=600;
	this.setting.height=400;
	var str="<div class='PopLayer PopLayer-alert PopLayer-confirmTextAlert confirmTextAlertMask'><div class='PopLayer-h1'>"+defAttr(json,this.setting,"header")+"<span class='closeButton confirmTextAlertClose'></span></div> <div class='PopLayer-HTML'>\
	<div class='custom-msg-but'>\
        <div class='margin-bottom-15'>"+json.title+"</div>\
        <div><button type='button' onclick=Layer.closeNowLayer('confirmTextAlertMask'); class='btn btn-sm btn-default'>取消</button>\
        <button type='button' class='btn btn-sm btn-primary btn-submit'>确定</button></div></div></div></div>";                   
	$('body').append(str);
	$('.PopLayer-HTML').css({"height":(defAttr(json,this.setting,"height")-60)+"px"});
	this.mask("confirmTextAlertMask");  //显示遮罩
	animate($('.PopLayer-confirmTextAlert'),defAttr(json,this.setting,"width"),defAttr(json,this.setting,"height"));
	closeLayer($('.confirmTextAlertClose'),'confirmTextAlertMask');  //点击关闭按钮关闭
	deteButton($('.btn-submit'),'confirmTextAlertMask',fn); //确定
	if(json.closeMask){
		closeLayer($('.confirmTextAlertMask'),'confirmTextAlertMask');//点击遮罩关闭
	}
}

//自定义拒绝（附加拒绝理由）
PopLayer.prototype.confirmNotByTextAlert=function(json,fn){
	this.setting.width=600;
	this.setting.height=400;
	var botByText=json.botByText||"填写理由";
	var str="<div class='PopLayer PopLayer-alert PopLayer-confirmNotByTextAlert confirmNotByTextAlertMask'><div class='PopLayer-h1'>"+defAttr(json,this.setting,"header")+"<span class='closeButton confirmNotByTextAlertClose'></span></div> <div class='PopLayer-HTML'>\
	<div class='custom-msg-but'>\
                    <div class='margin-bottom-15'>"+json.title+"</div>\
                    <textarea class='form-control' id='withdraw-NotBy' rows='3' placeholder='"+botByText+"'></textarea>\
                    <div><button type='button' onclick=Layer.closeNowLayer('confirmNotByTextAlertMask'); class='btn btn-sm btn-default'>取消</button>\
                    <button type='button' onclick='alertMs()' class='btn btn-sm btn-primary btn-notBy-submit'>确定</button></div>\
                </div></div></div>";                   
	$('body').append(str);
	$('.PopLayer-HTML').css({"height":(defAttr(json,this.setting,"height")-60)+"px"});
	this.mask("confirmNotByTextAlertMask");  //显示遮罩
	animate($('.PopLayer-confirmNotByTextAlert'),defAttr(json,this.setting,"width"),defAttr(json,this.setting,"height"));
	closeLayer($('.confirmNotByTextAlertClose'),'confirmNotByTextAlertMask');  //点击关闭按钮关闭
	window.alertMs=function (){
		var notByText=document.getElementById('withdraw-NotBy').value;
		if(json.errmsg){
			if(!/^[\w\W]+$/.test(notByText)){
				Layer.alert({width:300,height:150,type:"msg",title:json.errmsg});
				return false;
			}
		}
		$('.confirmNotByTextAlertMask').remove();
		fn(notByText);
	}
	if(json.closeMask){
		closeLayer($('.confirmNotByTextAlertMask'),'confirmNotByTextAlertMask');//点击遮罩关闭
	}
}

//确认层
PopLayer.prototype.confirm=function(json,fn){
	this.setting.title="你确认这样吗？";
	var str="<div class='PopLayer PopLayer-alert PopLayer-confirm confirmMask'><div class='PopLayer-h1'>"+defAttr(json,this.setting,"header")+"<span class='closeButton confirmClose'></span></div> <div class='PopLayer-body PopLayer-body-confirm'><span></span><b>"+defAttr(json,this.setting,"title")+"</b></div> <div class='PopLayer-bottom'> <div class='PopLayer-bottom-left confirmLeft'>确定</div> <div class='PopLayer-bottom-right confirmRight'>取消</div> </div> </div>";                   
	$('body').append(str);
	this.mask("confirmMask");  //显示遮罩
	animate($('.PopLayer-confirm'),defAttr(json,this.setting,"width"),defAttr(json,this.setting,"height"));
	closeLayer($('.confirmClose'),'confirmMask');  //点击关闭按钮关闭
	closeLayer($('.confirmRight'),'confirmMask');  //点击取消按钮关闭
	deteButton($('.confirmLeft'),'confirmMask',fn); //确定
	if(json.closeMask){
		closeLayer($('.confirmMask'),'confirmMask');//点击遮罩关闭
	}
}

//页面层
PopLayer.prototype.page=function(json){
	this.setting.width=600;
	this.setting.heighprot=400;
	var str="<div class='PopLayer PopLayer-alert PopLayer-page pageMask'><div class='PopLayer-h1'>"+defAttr(json,this.setting,"header")+"<span class='closeButton pageClose'></span></div> <div class='PopLayer-HTML'>"+json["pageHtml"]+"</div></div>";                   
	$('body').append(str);
	$('.PopLayer-HTML').css({"height":(defAttr(json,this.setting,"height")-60)+"px"});
	this.mask("pageMask");  //显示遮罩
	animate($('.PopLayer-page'),defAttr(json,this.setting,"width"),defAttr(json,this.setting,"height"));
	closeLayer($('.pageClose'),'pageMask');  //点击关闭按钮关闭
	if(json.closeMask){
		closeLayer($('.pageMask'),'pageMask');//点击遮罩关闭
	}
}

//自定义HTML页面层
PopLayer.prototype.customHtml=function(json){
	this.setting.width=600;
	this.setting.height=400;
	var str="<div class='PopLayer PopLayer-alert PopLayer-customHtml customHtmlMask'><div class='PopLayer-h1'>"+defAttr(json,this.setting,"header")+"<span class='closeButton customHtmlClose'></span></div> <div class='PopLayer-HTML'>"+json["html"]+"</div></div>";                   
	$('body').append(str);
	$('.PopLayer-HTML').css({"height":(defAttr(json,this.setting,"height")-60)+"px"});
	this.mask("customHtmlMask");  //显示遮罩
	animate($('.PopLayer-customHtml'),defAttr(json,this.setting,"width"),defAttr(json,this.setting,"height"));
	closeLayer($('.customHtmlClose'),'customHtmlMask');  //点击关闭按钮关闭
	if(json.closeMask){
		closeLayer($('.customHtmlMask'),'customHtmlMask');//点击遮罩关闭
	}
}
//iframe层
PopLayer.prototype.iframe=function(json){
	this.setting.width=600;
	this.setting.height=400;
	var str="<div class='PopLayer PopLayer-alert PopLayer-iframe iframeMask'><div class='PopLayer-h1'>"+defAttr(json,this.setting,"header")+"<span class='closeButton iframeClose'></span></div> <div class='PopLayer-HTML'><iframe src='"+json["href"]+"' width='100%' height='97%' frameborder='0'></iframe></div></div>";                   
	$('body').append(str);
	$('.PopLayer-HTML').css({"height":(defAttr(json,this.setting,"height")-60)+"px"});
	this.mask("iframeMask");  //显示遮罩
	animate($('.PopLayer-iframe'),defAttr(json,this.setting,"width"),defAttr(json,this.setting,"height"));
	closeLayer($('.iframeClose'),'iframeMask');  //点击关闭按钮关闭
	if(json.closeMask){
		closeLayer($('.iframeMask'),'iframeMask');//点击遮罩关闭
	}
}

//提示层
PopLayer.prototype.miss=function(json){
	this.setting.height=90;
	var str="<div class='PopLayer PopLayer-alert PopLayer-miss missMask'><div class='PopLayer-body PopLayer-body-miss'><span></span><b>"+defAttr(json,this.setting,"title")+"</b></div> </div>";                   
	$('body').append(str);
	this.mask("missMask");  //显示遮罩
	animate($('.PopLayer-miss'),defAttr(json,this.setting,"width"),defAttr(json,this.setting,"height"));
	closeLayer($('.missClose'),'missMask');  //点击关闭按钮关闭
	if(json.closeMask){
		closeLayer($('.missMask'),'missMask');//点击遮罩关闭
	}
	setTimeout(function(){
		$('.missMask').remove();
	},defAttr(json,this.setting,"time"))
}

//加载层
PopLayer.prototype.loading=function(json){
	this.setting.height=110; 
	this.setting.title="正在加载中，请稍后...";
	var src="loading-1";
	if(json){
		switch(json["srcType"]){
			case 1:
				src="loading-1";
				break;
			case 2:
				src="loading-2";
				break;
			case 3:
				src="loading-3";
				break;
			case 4:
				src="loading-4";
				break;			
		}	
	}
	
	var str="<div class='PopLayer PopLayer-alert PopLayer-loading loadingMask'><div class='PopLayer-body PopLayer-body-loading'><img src='/assets/img/"+src+".gif'><b>"+defAttr(json,this.setting,"title")+"</b></div> </div>";
	$('body').append(str);
	this.mask("loadingMask");  //显示遮罩
	$('.loadingMask').css({"cursor":"wait"})
	animate($('.PopLayer-loading'),defAttr(json,this.setting,"width"),defAttr(json,this.setting,"height"));
}

//创建遮罩
PopLayer.prototype.mask=function(Class){
    var str="<div class='PopLayer PopLayer-mask "+Class+"'></div>";
	$('body').append(str);
}

//关闭加载层
PopLayer.prototype.closeLoading=function(){
	$('.loadingMask').remove();
}

//弹出层默认大小处理函数
function defAttr(json,setting,attr){
	return json?json[attr]||setting[attr]:setting[attr];	
}

//关闭层
PopLayer.prototype.closeNowLayer=function(sClass,json){
	if(json&&json['close']){json.close();}
	$('.'+sClass).remove();
}

//关闭所有层
PopLayer.prototype.closeAllLayer=function(){
	$('.PopLayer').remove();
}

//关闭遮罩
function closeLayer(but,Class,json){
	but.click(function(){
		if(json&&json['close']){json.close();}
		$('.'+Class).remove();
   });
}

//确定点击事件
function deteButton(button,Class,fn){
	button.click(function(){
		$('.'+Class).remove();
		fn();
	});
}

//弹出层动态效果
function animate(obj,wid,hei){
	obj.animate({
		width:wid+'px',
		height:hei+'px',
		marginLeft:(-parseInt(wid))/2+"px",
		marginTop:(-parseInt(hei))/2+"px",
		opacity:1
	},"fast")
}



/*success
Layer.alertMor({width:350,height:210,type:"msg",type:"success",title:"这里面是相关的提示",callback:function(){
	关闭弹出层
	Layer.closeNowLayer('alertMorMask');
},close:function(){
	
}});

//error
Layer.alertMor({width:350,height:210,type:"msg",type:"error",title:"这里面是相关的提示",callback:function(){
	关闭弹出层
	Layer.closeNowLayer('alertMorMask');
}});

//拒绝附加理由
Layer.confirmNotByTextAlert({
    header:"财务拒绝",
    width:400,
    height:260,
    botByText:'请填写拒绝理由',
    title:"您已选择财务拒绝",
    errmsg:'请填写拒绝的理由！',   （选填）
},function(notByText){
    
});

//成功+一段提示
 Layer.confirmTextAlert({
	 header:"通过",
	 width:350,
	 height:180,
	 title:"您已选择<b class='error'>6</b>条认证申请，是否全部确认吊销?",
},function(){
            
});


*/



