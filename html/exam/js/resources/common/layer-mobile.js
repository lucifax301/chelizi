;(function(window){
window.Popup=(function(){
    return new PopLayer();
})(window);

//构造函数
function PopLayer(){
    this.setting={
        time:2000,
        header:"提示",     //头部信息
        haveHeader:true,  //是否显示头部
        maskHide:false,   //是否点击遮罩隐藏
        closeBut:true,    //是否需要关闭按钮
        loadingImg:'loading-1',
        type:"msg",  
        style:'',
        title:"请填写提示信息！",
        yesHtml:'',
        yes:function(){},
        callback:function(){},
    };
}
/*extent json函数*/
PopLayer.prototype.extend=function(json1,json2){
    var newJson=json1;
    for(i in json1){
        for( j in json2){
            newJson[j]=json2[j];
        }
    }
    return newJson;
}
/*公共变量*/
PopLayer.prototype.varLiang=function(json){
	if(json){
        this.setting.style="";
        this.setting=this.extend(this.setting,json); //继承
   }else{
     	this.setting.style="";
   }
}
/*页面层*/
PopLayer.prototype.customHtml=function(json){
	this.varLiang(json);
    var str='<div class="popup">';
	str+=this.setting.maskHide?'<div class="mask" onclick="closeThisPopup(this)"></div>':'<div class="mask"></div>';
    str+='<div class="main" style="'+this.setting.style+'">';
    if(this.setting.haveHeader){
    	str+=this.setting.closeBut?'<div class="header">'+this.setting.header+'</div>':'<div class="header">'+this.setting.header+'<span onclick="closeThisPopup(this)"></span></div>';
    }
    str+='<div class="content html">'+this.setting.html+'</div>';
    if(this.setting.haveConfirm){
        str+='<div class="footer"><span class="no" onclick="closeThisPopup(this)">取消</span><span class="yes">确定</span></div>';
    }

    str+='</div></div>';
	         
    $('body').append(str);
    middle(this.setting); //居中
    $('span.yes').click(function(){
        json.callback();
    });
}
/*iframe层*/
PopLayer.prototype.iframe=function(json){
    win.showLoading();
	this.varLiang(json);
    var str='<div class="popup popup-iframe">';
	str+=this.setting.maskHide?'<div class="mask" onclick="closeThisPopup(this)"></div>':'<div class="mask"></div>';
    str+='<div class="main" style="'+this.setting.style+'">';
    if(this.setting.haveHeader){
    	str+=this.setting.closeBut?'<div class="header">'+this.setting.header+'</div>':'<div class="header">'+this.setting.header+'<span onclick="closeThisPopup(this)"></span></div>';
    }
    var isIos=navigator.userAgent.indexOf('Mac OS X')>-1;
    var isIpad=navigator.userAgent.indexOf('iPad;')>-1;
    if(isIos || isIpad){
       str+='<div class="content html contentios">'; 
    }else{
       str+='<div class="content html">'; 
    };
    str+='<iframe id="iframePage" src="'+json.src+'" width="100%" height="100%" frameborder="0"></iframe></div>';
    if(this.setting.yesHtml){str+='<div class="yesHtml" onclick="closeIframePopup(this)">'+this.setting.yesHtml+'</div>';}
    str+='</div></div>';
	         
    $('body').append(str);
    if(this.setting.yesHtml){
        var height=$('div.main').height()-20;
    }else{
        var height=$('div.main').height();     
    }
	if(this.setting.haveHeader){
		$('div.content').css({height:(height-80)+'px'});
	}else{
		$('div.content').css({height:(height-50)+'px'});
	}
    var _this=this;
    /*横竖屏切换时执行*/
    $(window).on('resize',function() {
        if(_this.setting.yesHtml){
            var height=$('div.main').height()-20;
        }else{
            var height=$('div.main').height();     
        }
        if(_this.setting.haveHeader){
            $('div.content').css({height:(height-80)+'px'});
        }else{
            $('div.content').css({height:(height-50)+'px'});
        }
    });
    setTimeout(function(){win.hideLoading();},1000);
    window.closeIframePopup=function(obj){
        $(obj).parents('div.popup').remove();
        if(json.callback){json.callback()}
    }
}

//信息层
PopLayer.prototype.alert=function(json){
    this.varLiang(json);
    var str='<div class="popup">';
	str+=this.setting.maskHide?'<div class="mask" onclick="closeThisPopup(this)"></div>':'<div class="mask"></div>';
    str+='<div class="main" style="'+this.setting.style+'">';
    if(this.setting.haveHeader){
    	str+=this.setting.closeBut?'<div class="header">'+this.setting.header+'</div>':'<div class="header">'+this.setting.header+'<span onclick="closeThisPopup(this)"></span></div>';
    }
    str+='<div class="content">'+this.setting.title+'</div>';
    str+='<div class="footer"><span class="yes" onclick="closeThisPopup(this)">确定</span></div>';
    str+='</div></div>';
	         
    $('body').append(str);
    middle(this.setting); //居中
}
//确认层
PopLayer.prototype.confirm=function(json){
	this.varLiang(json);
    var This=this;
    var str='<div class="popup">';
    	str+=this.setting.maskHide?'<div class="mask" onclick="closeThisPopup(this)"></div>':'<div class="mask"></div>';
		str+='<div class="main" style="'+this.setting.style+'">';
        if(this.setting.haveHeader){
        	str+=this.setting.closeBut?'<div class="header">'+this.setting.header+'</div>':'<div class="header">'+this.setting.header+'<span onclick="closeThisPopup(this)"></span></div>';
        }
        str+='<div class="content">'+this.setting.title+'</div>';
        str+='<div class="footer"><span class="no" onclick="closeThisPopup(this)">取消</span><span class="yes">确定</span></div>';
        str+='</div></div>';     
    $('body').append(str);
    $('span.yes').click(function(){
        dosomePopup(this,This.setting.yes);
    });
    middle(this.setting); //居中
}
/*2s消失*/
PopLayer.prototype.miss=function(json){
	this.varLiang(json);
    this.setting.haveHeader=true;
    var str='<div class="popup popup-hide">';
    	str+=this.setting.maskHide?'<div class="mask" onclick="closeThisPopup(this)"></div>':'<div class="mask"></div>';
		str+='<div class="main" style="'+this.setting.style+'">';
        str+='<div class="content">'+this.setting.title+'</div>';
        str+='</div></div>';     
    $('body').append(str);
    middle(this.setting); //居中
    setTimeout(function(){
    	$('div.popup-hide').remove();
    },this.setting.time);
}

//加载层
PopLayer.prototype.loading=function(json){
    this.varLiang(json);
    var str='<div class="popup popup-loading">';
    	str+=this.setting.maskHide?'<div class="mask" onclick="closeThisPopup(this)"></div>':'<div class="mask"></div>';
		str+='<div class="main" style="'+this.setting.style+'">';
        str+='<div class="content">'+this.setting.title+'</div>';
        str+='</div></div>';     
    $('body').append(str);
    middle(this.setting); //居中
}
/*关闭加载层*/
PopLayer.prototype.closeLoading=function(){
	$('div.popup-loading').remove();
}
/*关闭iframe层*/
PopLayer.prototype.closeIframe=function(){
	$(".popup-iframe", parent.document).remove();
}
/*确认回调函数*/
window.dosomePopup=function(obj,yes){
	closeThisPopup(obj);
	yes();
}
/*关闭遮罩*/
window.closeThisPopup=function(obj){
	$(obj).parents('div.popup').remove();
}
//居中函数
window.middle=function(json){
	var main=$('div.main');
    main.css({
    	'-moz-transform': 'translate(-50%,-50%)',
		'-webkit-transform': 'translate(-50%,-50%)',
		'transform': 'translate(-50%,-50%)'
    });
    if(!json.haveHeader){
        main.css('paddingTop','20px');
    };
}
})(window);





