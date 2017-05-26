
/****************************************************定义函数*******************************************************************/
/*输入验证提示*/
function errorMessage(str){
	$('#common-error').html('<span class="ion-close-circled"> '+str+'</span>');
}

/*顶部导航点击操作 localStorage的iframe src*/
function setIframelS(obj){
	openSrc(obj);
	var href=$(obj).attr("src");
	var iframeSrc=localStorage.getItem("lili-iframeSrc");
	if(iframeSrc){
		if(iframeSrc.indexOf(href)==-1){
			localStorage.setItem("lili-iframeSrc",href);
		}
	}else{
		localStorage.setItem("lili-iframeSrc",href);
	}
}

/*退出登录函数*/
function loginOut(){
	$.AJAX({
		type:"post",
		url:config.basePath+"login/logout",
		success:function(data){
			localStorage.setItem("lili-username","");
            sessionStorage.setItem("accessData","");
            setTimeout(function(){window.location.href=startConfig.bathUrl+config.loginUrl;},500)
		}
	});
}

/*查询城市列表(主要城市) - shield 如果不传，显示所有城市，如果传0，就显示4个主要城市，如果传1，就显示除4个城市外的所有城市*/
function queryCity(json){
	$.AJAX({
		type:"get",
		url:config.basePath+"school/queryCity",
		data:{
			rlevel:json.rlevel||2,  //区域等级:1代表省，2代表市，3代表区
			pid:json.pid,   //父节点id
			shield:0
		},
		success:function(data){
			json.callback(getListData(data));
		}
	});
	}


/*查询驾校列表*/
function querySchool(json){
	$.AJAX({
		type:"get",
		url:config.basePath+"school/query",
		data:{
			cityId:json.cityId,   //驾校id
		},
		success:function(data){
			json.callback(getListData(data));
		}
	});
}

/*查询报名资料列表*/
function infFect(json){
	$.AJAX({
		url:config.basePath+"student/pack-detail",
		type:'get',
		data:{
			applyttid:json.applyttid, //报名包id
		},
		success:function(data){
			json.callback(data.data.items);
		}
	});
}





/*动态添加iframe 并赋src值*/
function openSrc(){
	/*添加active样式*/
	var oNav=$('#admin-nav div');
    //var thistest = "app-client-plan.html";
    //console.log("汪微匹配DIVsrc："+thistest.match(/\b[\w_-]+/)[0])
    //console.log("测试匹配URL："+window.location.pathname.match(/(?=[^\/]+$).+(?=\.html)/))
	oNav.each(function(i){
		if(oNav.eq(i).attr("src")){
			if(window.location.href.indexOf(oNav.eq(i).attr("src").match(/\b[\w_-]+/)[0])>-1){ // 汪微写的规则,indexOf不准确
			//if(window.location.pathname.match(/(?=[^\/]+$).+(?=\.html)/) == oNav.eq(i).attr("src").match(/\b[\w_-]+/)[0]){//修复规则，必须相等才行
				/*添加active样式*/
				oNav.removeClass('active')
				oNav.eq(i).addClass("active").parents("li").find("div").eq(0).addClass("active");
				
				if(oNav.eq(i).parents("li").parents("li").parents("li")[0]){
					oNav.eq(i).parents("li").find("ul").slideDown("fast");
					oNav.eq(i).parents("li").find("i").attr("class","t-i ion-ios-arrow-down");
				}else{
					oNav.eq(i).parents("li").find("ul.s-ac-menu").slideDown("fast");
					oNav.eq(i).parents("li").find("i.s-i").attr("class","s-i ion-ios-arrow-down");
				}
			};
		}
	});	
}

/*冒泡弹出层*/
function topLongText(){
	// 定时器
	var time1=null;
	var time2=null;
	$('.layerBubble').mouseover(function(event) {
		clearTimeout(time1);
		clearTimeout(time2);
		var text=$(this).attr("data-bubble");
		var leftX=18;
		var topY=60;
		if(!$('#layer-bubble').length){
			$('body').append('<div id="layer-bubble" style="left:'+(event.pageX-leftX)+'px;top:'+(event.pageY-topY)+'px"><font>'+text+'</font><span></span></div>');
		}else{
			$('#layer-bubble').show();
			$('#layer-bubble font').text(text);
		};
		/*移入时*/
		$(this).mousemove(function(event) {
			var left=event.pageX;
			$('#layer-bubble').css({'left':(event.pageX-leftX)+'px','top':(event.pageY-topY)+'px'})
		});
		/*弹出层移入*/
		$('#layer-bubble').mouseover(function(event) {
			clearTimeout(time1);
			clearTimeout(time2);
		});
	});

	/*移除隐藏*/
	$('.layerBubble').mouseout(function(event) {
		clearTimeout(time1);
		clearTimeout(time2);
		time1=setTimeout(function(){
			$('#layer-bubble').hide();
		}, 500)
		/*弹出层移除*/
		$('#layer-bubble').mouseout(function(event){
			clearTimeout(time1);
			clearTimeout(time2);
			time2=setTimeout(function(){
				$('#layer-bubble').hide();
			}, 500)
		})
	});
}

/*接口同一返回数据列表函数*/
function getListData(data){
	return JSON.parse(data.result.pageData);
}

/*星级评价函数*/
function scoreHtml(num,id){
	var typeTex="";
	switch(num){
		case 0:
			typeTex='<i class="ion-ios-star-outline"></i><i class="ion-ios-star-outline"></i><i class="ion-ios-star-outline"></i><i class="ion-ios-star-outline"></i><i class="ion-ios-star-outline"></i>';
			break;
		case 1:
			typeTex='<i class="ion-ios-star"></i><i class="ion-ios-star-outline"></i><i class="ion-ios-star-outline"></i><i class="ion-ios-star-outline"></i><i class="ion-ios-star-outline"></i>';
			break;
		case 2:
			typeTex='<i class="ion-ios-star"></i><i class="ion-ios-star"></i><i class="ion-ios-star-outline"></i><i class="ion-ios-star-outline"></i><i class="ion-ios-star-outline"></i>';
			break;
		case 3:
			typeTex='<i class="ion-ios-star"></i><i class="ion-ios-star"></i><i class="ion-ios-star"></i><i class="ion-ios-star-outline"></i><i class="ion-ios-star-outline"></i>';
			break;
		case 4:
			typeTex='<i class="ion-ios-star"></i><i class="ion-ios-star"></i><i class="ion-ios-star"></i><i class="ion-ios-star"></i><i class="ion-ios-star-outline"></i>';
			break;
		case 5:
			typeTex='<i class="ion-ios-star"></i><i class="ion-ios-star"></i><i class="ion-ios-star"></i><i class="ion-ios-star"></i><i class="ion-ios-star"></i>';
			break;				

	}
	//$("#pingji").html(typeTex);
	$(id).html(typeTex);
}

/*各页面详情页tab切换函数*/
function tabPageDetails($event){
	if($event.target){
		var index=$($event.target).index();
		$($event.target).addClass('active').siblings().removeClass("active");
		$("#order-table").children().eq(index).show().siblings().hide();
	}else{
		var index=$event;
		$(".table-li").children().eq(index).addClass('active').siblings().removeClass("active");
		$("#order-table").children().eq(index).show().siblings().hide();
	}	
	return index;
}

/*数据统计出来json函数*/
function getDataFromJson(titleArr,strArr,arr){
	var newArr=[];
	for(var j=0,lenj=titleArr.length;j<lenj;j++){
		if(!newArr[j]){
			newArr.push([titleArr[j]])
		}else{
			newArr[j].push(titleArr[j]);
		}
		
		for(var i=0,len=arr.length;i<len;i++){
			newArr[j].push(arr[i][strArr[j]]);
		}
	}
	return newArr;
}

/*firame 上传文件函数*/
function createFileForm(json){
	var html='<div id="createFileHtml"><form enctype="multipart/form-data" target="fileIframeName" method="post" action="'+json.url+'" class="hidden">\
		<input type="file" name="filename" id="expInputFile">\
		</form><iframe class="hidden" name="fileIframeName" ></iframe></div>';
	if(!$('#createFileHtml').length){
		$('body').append(html);
	};	
	$('#expInputFile').click();
	$('#expInputFile').change(function(){
		json.callback();
	});
}

/*FormData 上传文件函数*/
function cerateFileFormData(json){
	var filename=json.filename?json.filename:'filename'
	var html='<div id="createFileHtml" class="hidden">\
				<form enctype="multipart/form-data" id="uploadForm">\
					<input type="file" name="'+filename+'" id="expInputFile"></div>\
				</form>';
	if(!$('#createFileHtml').length){
		$('body').append(html);
	}
	$('#expInputFile').click();	
	$('#expInputFile').change(function(){
		win.showLoading();
	    var formData = new FormData($( "#uploadForm" )[0]); 
	    if(json.data){
		    for(var i in json.data){
		    	formData.append(i, json.data[i]);
		    };
	    };
	    $.FileAJAX({
	    	url:json.url,
	    	data:formData,
	    	success:function(data){
	    		$('#createFileHtml').remove();
	    		json.callback(data);
	    	},
	    	error:function(){
	    		$('#createFileHtml').remove();
	    	}
	    });
	});
}

/*提现 匹配是否确认*/
function isFinaConfirm(json){
	var result=false;
	var arr=[];
	for(var i=0,len=json.datas.length;i<len;i++){
		for(var j=0,lenj=json.idList.length;j<lenj;j++){
			if(json.datas[i][json.id]==json.idList[j]){
				arr.push(json.datas[i]);
			}
		}
	}
	for(var i=0,len=arr.length;i<len;i++){
		if(checkIn(json.val,arr[i][json.check],result)){
			result=true;	
		}
	}
	return result;
}

/*检测是不是数组里面的值*/
function checkIn(arr,value,result){
	var result=false
	for(var i=0,len=arr.length;i<len;i++){
		if(arr[i]==value){
			result=true;
		}
	}
	return result;
}

/*删除json 中含有的某个key值*/
function deleteJson(json,key){
	var newJson={};
	for(var i in json){
		if(i!=key){
			newJson[i]=json[i];
		}
	}
	return newJson;
}

/*检测json 中是否有某个key值*/
function haveKeyInJson(json,key){
	var haveKey=false;
	for(var i in json){
		if(i==key){
			haveKey=true;
		}
	}
	return haveKey;
}

/*遍历json 清除active样式*/
function clearAllActive(objElement){
	for(var i in objElement){
		objElement[i].parent().children().eq(0).addClass('active').siblings().removeClass('active');
	}
}

/*处理置学员状态的返回值*/
function studentStateRes(data){
	var json={
		dataList:[],
		canChange:0,
		notChange:0,
	};
	for(var i=0,len=data.length;i<len;i++){
		json.dataList.push({
			'applyexamAfter':data[i].applyexam+','+data[i].applystate,
			'applystateAfter':data[i].reqApplyexam+','+data[i].reqApplystate,
			'studentId':data[i].studentId,
			'canReset':data[i].canReset,
			'studentName':data[i].studentName,
		});
		data[i].canReset?json.notChange++:json.canChange++;
	}

	return json;
}

/*根据某值获得data数据*/
function getDataForKey(json){
	var result=false;
	var arr=[];
	for(var i=0,len=json.datas.length;i<len;i++){
		for(var j=0,lenj=json.idList.length;j<lenj;j++){
			if(json.datas[i][json.id]==json.idList[j]){
				arr.push(json.datas[i]);
			}
		}
	}
	return arr;
}

/*获得某个key值得数据集合*/
function getKeyArrFromData(datas,key){
	var newArr=[];
	for(var i=0,len=datas.length;i<len;i++){
		newArr.push(datas[i][key]);
	}
	return newArr;
}

/*根据状态筛选出可置状态权限*/
function getDataForStudentConfig(json){
	var haveKey=false;
	var studentState='';
	var studConfig=[];
	for(var i=0,len=json.datas.length;i<len;i++){
		if(json.check.indexOf(',')>0){
			var check=json.check.split(',');
			studentState=json.datas[i][check[0]]+','+json.datas[i][check[1]];
		}else{
			studentState=json.datas[i]['check'];
		}
		/*检测权限*/
		var haveKey=haveKeyInJson(json.checkData,studentState);
		if(haveKey){
			for(var j in json.checkData){
				if(studentState==j){
					if(studConfig.length){
						studConfig=commonArrData(studConfig,json.checkData[j]);
					}else{
						studConfig=json.checkData[j];
					}
				}
			}
		}else{
			studConfig=[];
			json.callback(studConfig);
			return false;
		}
		
	}
	json.callback(studConfig);
}

/*保留两个数组相同的值*/
function commonArrData(arr1,arr2){
	var newArr=[];
	for(var i=0,len=arr1.length;i<len;i++){
		for(var j=0,lenj=arr2.length;j<lenj;j++){
			if(arr2[j]==arr1[i]){
				newArr.push(arr2[j]);
			}
		}
	}
	return newArr;
}



/*数据导出iframe窗口
getSearchs:get json参数
total:分页总数据量，导出不能超过总数据量
url:导出api地址
*/
function dataExportForIframe(json){
	var urlstr=jsonToGetUrl(json.getSearchs);
	if(json.total>config.explTotal){
		Layer.confirm({width:300,height:160,title:config.explPrompt,header:"导出"},function(){
			if($('#exproiframe').length){
				$('#exproiframe').attr('src',config.basePath+json.url+'?'+urlstr);
			}else{
				$('body').append('<iframe id="exproiframe" class="hide" src="'+config.basePath+json.url+'?'+urlstr+'"></iframe>')
			}
		});
	}else{
		if($('#exproiframe').length){
			$('#exproiframe').attr('src',config.basePath+json.url+'?'+urlstr);
		}else{
			$('body').append('<iframe id="exproiframe" class="hide" src="'+config.basePath+json.url+'?'+urlstr+'"></iframe>')
		}
	};
	setTimeout(function(){
		$('#exproiframe').remove();
	},5000);
}

//设置cookie
function setCookie(cname, cvalue, exhours) {
    var d = new Date();
    d.setTime(d.getTime() + (exhours*60*60*1000));
    var expires = "expires="+d.toUTCString();
    document.cookie = cname + "=" + cvalue + "; " + expires;
}
//获取cookie
function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) != -1) return c.substring(name.length, c.length);
    }
    return "";
}