/**********************************************后台配置**************************************************************************/ 
window.config={
	loginUrl:"login.html",                                       //登陆页面
    registUrl:"regist.html",                                     //注册页面
	homeUrl: "index.html",                                       //登陆成功后的页面
	indexGoToSrc:'datas/data-statistics.html',                 //第一次登录成功后首页需要跳转的页面
	basePath:'http://192.168.1.99:9106/portal/',                //99服务器 接口
	layerPhoneWidth:$(window).width()<768?$(window).width()*0.9:660,    //弹出层手机访问时的宽度
	popLayerHideTime:1000,                                              //弹出框 消失层 的消失时间
	contentTimeShow:200,                                                //主模板display:block的时间
	ajaxtimeout:8000,                                                   //ajax 超时配置
	explTotal:1000,                                                     //导出数据最大条数 提示值  
	explPrompt:'导出最新的1000条数据!',                                 //导出数据提示
	msgTime:60,                                                         //发送短信间隔时间
};

/*------------------------ start jquery 相关  ------------------------*/
if(window.$){
	$.AJAX=function(json){
		var noError=true;
		$.ajax({
			type: json.type||"post",
			url: json.url,
			xhrFields: {
			    withCredentials: true
			},
			crossDomain: true,
			data: json.data||"",
			datatype:"json",
			async:json.async,
			success:function(data){
				if(!json.nohideloading){ win.hideLoading();};
				clearTimeout(timeout);
				error(data,json);
			},
			error:function(XMLHttpRequest){
				win.hideLoading();
				clearTimeout(timeout);
				if(noError){
					_error(XMLHttpRequest,json);
				};	
			}
		});
		if (json.async != false) {
			var timeout=setTimeout(function(){
			win.hideLoading();
			// 请求超时
			noError=false;
			Layer.alert({width:300,height:150,type:"error",title:"请求超时，请刷新重试!"});
		}, json.timeout||config.ajaxtimeout);
		}
	};
	$.FileAJAX=function(json){
		var noError=true;
		$.ajax({
			type: json.type||"post",
			url: json.url,
			xhrFields: {
			    withCredentials: true
			},
			crossDomain: true,
			data: json.data||"",
			datatype:"json", 
            cache: false, 
            contentType: false,  
            processData: false, 
			success:function(data){
				win.hideLoading();
				clearTimeout(time);
				error(data,json);
			},
			error:function(XMLHttpRequest){
				win.hideLoading();
				json.error();
				clearTimeout(time);
				if(noError){
					_error(XMLHttpRequest);
				};	
			}
		});
		var time=setTimeout(function(){
			win.hideLoading();
			// 请求超时
			noError=false;
			Layer.alert({width:300,height:150,type:"error",title:"请求超时，请刷新重试!"});
		}, json.timeout||config.ajaxtimeout);
	}
	//error 根据code码的函数
	function error(data,json){
		//判断code 并处理
		switch(parseInt(data.code)){
			case 0:
				json.success(data);
				break;
			case 1:
				if(window.location.href.indexOf(config.loginUrl) == -1){ 
					sessionStorage.setItem("url", window.location.href); //记录没有登陆前的访问页面
					win.location.href=startConfig.bathUrl+config.loginUrl;
				}else{
					Layer.alert({width:300,height:150,type:"error",title:"用户鉴权失败,请重新登录"});
				}
				break;
			case 601:
				json.error(data);
				break;	
			default:
				//直接弹出错误信息	
				Layer.alert({width:350,height:150,type:"error",title:data.msg});
		}
	}
	// _error函数
	function _error(XMLHttpRequest,json){
		win.hideLoading();
		if(json.code){
			json.error(JSON.parse(XMLHttpRequest.responseText));
		}else{
			switch(XMLHttpRequest.status){
				case 401:
					if(window.location.href.indexOf(config.loginUrl) == -1){ 
						sessionStorage.setItem("url", window.location.href); //记录没有登陆前的访问页面
						win.location.href=startConfig.bathUrl+config.loginUrl;
					}else{
						Layer.alert({width:300,height:150,type:"error",title:"用户鉴权失败,请重新登录"});
					}
					break;
				case 404:
					Layer.alert({width:300,height:150,type:"error",title:"接口地址不存在"});
					break;		
				case 500:case 502:
					Layer.alert({width:300,height:150,type:"error",title:"服务器内部错误"});
					break;		
				case 601:
					Layer.alert({width:300,height:150,type:"error",title:"手机号码已存在"});
					break;
				case 602:
					Layer.alert({width:300,height:150,type:"error",title:"车牌号或车辆行驶证号已存在"});
					break;
				case 603:
					Layer.alert({width:300,height:150,type:"error",title:"教练已绑定该车"});	
					break;		
				case 611:
					Layer.alert({width:300,height:150,type:"error",title:"数据更新失败"});
					break;
				case 612:
					Layer.alert({width:300,height:150,type:"error",title:"数据插入失败"});
					break;
				case 613:
					Layer.alert({width:300,height:150,type:"error",title:"数据查询失败"});
					break;
				case 614:
					Layer.alert({width:300,height:150,type:"error",title:"数据删除失败"});
					break;	
				case 620:
					Layer.alert({width:300,height:150,type:"error",title:"没有操作权限"});
					break;
				case 630:
					Layer.alert({width:300,height:150,type:"error",title:"Excel导出失败"});
					break;
				case 699:
					Layer.alert({width:300,height:150,type:"error",title:"请求参数有误"});
					break;
				case 701:
					Layer.alert({width:300,height:150,type:"error",title:"金额超出限制"});
					break;
				case 702:
					Layer.alert({width:300,height:150,type:"error",title:"该记录状态不对，不能进行操作"});
					break;
				case 703:
					Layer.alert({width:300,height:150,type:"error",title:"奖金有重复教练，请删除后再试"});
					break;
				case 704:
					Layer.alert({width:300,height:150,type:"error",title:"发放奖金失败"});
					break;
				default:
					Layer.alert({width:300,height:150,type:"error",title:"未知错误"});
			}
		}
	}
};
/*------------------------ end jquery 相关  ------------------------*/

/**********************************************检测登陆与跳转*********************************************************************/ 
/*记录当前页面 用于登陆之后跳转回来*/
;(function(){
	var winHref=window.location.href;
	if((winHref.indexOf(config.loginUrl)==-1)&&(winHref.indexOf(config.registUrl)==-1)){
		sessionStorage.setItem("school-url",window.location.href);
	};
})();

/*登陆页面 检测如果已经登陆就跳转到相应的页面*/
// ;(function(){
// 	if(window.location.href.indexOf(config.loginUrl)>-1){
// 		$.AJAX({
// 			url:config.basePath+"user/verify",
// 			success:function(data){
// 				/*用户已经登陆 跳转到登录主页*/
// 				window.location.href=config.homeUrl;
// 			}
// 		});
// 	};
// })();

/*------------------------ start 布局写入  ------------------------*/
window==window.top && document.write('<div id="loading"></div>');
/*------------------------ end 布局写入  ------------------------*/

/*------------------------ start 原生扩展  ------------------------*/
var win=window.top;
var UA=navigator.userAgent;
var isPC=UA.indexOf('Windows NT')>-1;
var isAndroid=UA.indexOf('Android')>-1;
var isIos=UA.indexOf('Mac OS X')>-1;
var isIphone=UA.indexOf('iPhone;')>-1;
var isIpad=UA.indexOf('iPad;')>-1;

var isIE7=UA.indexOf('MSIE 7.0;')>-1;
var isIE8=UA.indexOf('MSIE 8.0;')>-1;
var isIE9=UA.indexOf('MSIE 9.0;')>-1;
var isIE10=UA.indexOf('MSIE 10.0;')>-1;
var isIE11=UA.indexOf('Trident')>-1;
var isFirefox=UA.indexOf('Firefox')>-1;

var IsWeiXin=UA.indexOf('MicroMessenger')>-1;

var iFadeInterval=260;
var iSlideInterval=200;
var isLoading=false;
var baseURL='';
var $_GET={};

/*浏览器版本检测 如果是isIE7 isIE8 isIE9 则跳转到低版本浏览器提示页面*/
if(isIE7||isIE8||isIE9){
	window.location.href="ieTestingBrowser/ie-testing-browser.html"
}

// 深拷贝
function dClone(o){
	return JSON.parse(JSON.stringify(o));
}
// 创建svg元素
function createTag(tagName, attr){
	attr=attr || {};
	var ele=document.createElementNS('http://www.w3.org/2000/svg', tagName);
	for(var key in attr){
		ele.setAttribute(key, attr[key]);
	}
	return ele;
}
// 角度转弧度
function d2a(deg){
	return deg*Math.PI/180;
}
// 弧度转角度
function a2d(angle){
	return angule/Math.PI*180;
}
// 随机整数
function rand(m,n){
	return Math.round(Math.random()*(n-m)+m);
}
// 获取当前时间毫秒
function time(){
	return new Date().getTime();
}
// 随机颜色
function randColor(){
	var arr=[];
	for(var i=0; i<3; i++){
		arr.push(fillLen(rand(0,255).toString(16)));
	}
	return '#'+arr.join('');
}
/*
	'#oaf' => 'rgba(0,170,255,1)'
	'#00aaff' => 'rgba(0,170,255,1)'
*/
function color2rgba(color, opacity){
	opacity=opacity || 1;
	color=color.replace('#', '');
	color.length==3 && (color=color.replace(/\w/g, function(str){
		return str+str;
	}));
	var arr=[];
	color.replace(/\w{2}/g, function(str){
		arr.push(Number('0x'+str));
	});
	return 'rgba('+arr+','+opacity+')';
}
// 'rgba(0,170,255)' => '#00aaff'
function rgb2color(rgb){
	var arr=rgb.match(/\d+/g);
	arr.length=3;
	var str='#';
	arr.forEach(function(item){
		str+=fillLen(parseInt(item).toString(16));
	});
	return str;
}
/*
	字符串转化为数字
	'123' => 123
	'60%' => 0.6
	'123.55abc456' => 123.55
*/
function toNum(str){
	if(typeof str=='number'){
		return str;
	}else if(/^\d+%$/.test(str)){
		return str.replace('%', '')/100;
	}else{
		return parseFloat(str);
	}
}

function showLoading(){
	isLoading=true;
	$('#loading').stop().fadeIn(iFadeInterval);
};
function hideLoading(){
	isLoading=false;
	$('#loading').stop().fadeOut(iFadeInterval);
};

// 数字前面补零
function fillLen(n, len){
	n=''+n;
	len=len || 2;
	while(n.length<len){
		n='0'+n;
	}
	return n;
}
/*new Date().date('y-m-d h:i:s'); => 2015-11-02 17:11:55*/
Date.prototype.date=function(format){
	var 
	year=this.getFullYear(),
	month=fillLen(this.getMonth()+1),
	day=fillLen(this.getDate()),
	hour=fillLen(this.getHours()),
	minute=fillLen(this.getMinutes()),
	second=fillLen(this.getSeconds()),
	json={
		'y': year,
		'm': month,
		'd': day,
		'h': hour,
		'i': minute,
		's': second
	};
	return !format?year+'-'+month+'-'+day+' '+hour+':'+minute+':'+second:format.replace(/y|m|d|h|i|s/g, function(str){
		return json[str];
	});
}

/*根据参数生成常用的正则表达式
*string    type 生成的正则表达式类型
*array     numArr 生成正则的条件数组 例如:[6,16] 也可省略
*/
function regCombination(type,numArr){
	var reg="";
	switch(type){
		case "*":     //"*":"不能为空！"   
			if(numArr){
				reg=new RegExp("^[\\w\\W]{"+numArr[0]+","+numArr[1]+"}$"); 
			}else {
				reg=new RegExp("^[\\w\\W]+$"); 
			}  
			break;
		case "number":    //"number":"请填写数字！
			if(numArr){
				reg=new RegExp("^\\d{"+numArr[0]+","+numArr[1]+"}$");
			}else{
				reg=new RegExp("^\\d+$");
			}
			break;
		case "float":    //"number":"请填写小数！
			reg=new RegExp("^[0-9]{0,4}(.[0-9]{0,6})?$");
			break;
		case "special":  //"s":"不能输入特殊字符！"   
			if(numArr){
				reg=new RegExp("^[\\u4E00-\\u9FA5\\uf900-\\ufa2d\\w\\.\\s]{"+numArr[0]+","+numArr[1]+"}$");
			}else{
				reg=new RegExp("^[\\u4E00-\\u9FA5\\uf900-\\ufa2d\\w\\.\\s]+$");
			}
			break; 
		case "chinese":  //"z":"中文验证" 
			reg=new RegExp("^[\\u4E00-\\u9FA5\\uf900-\\ufa2d]{"+numArr[0]+","+numArr[1]+"}$");
			break;	
		case "postcode":    //"p":"邮政编码！
			reg=new RegExp("^[0-9]{6}$");
			break;	
		case "phone":    //"m":"写手机号码！"
			reg=new RegExp("^13[0-9]{9}$|14[0-9]{9}|15[0-9]{9}$|17[0-9]{9}$|18[0-9]{9}$");
			break;
		case "tell":    //"m":"写手机号码或座机号码！"
			reg=new RegExp("(^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$)|(^13[0-9]{9}$|14[0-9]{9}|15[0-9]{9}$|18[0-9]{9}$)");
			break;
		case "email":   //"e":"邮箱地址格式
			reg=new RegExp("^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
			break;
		case "id":   //"id":验证身份证
			reg=new RegExp("^\\d{17}[\\dXx]$");
			break;	
		case "url":   //"url":"网址"
			reg=new RegExp("^(\\w+:\\/\\/)?\\w+(\\.\\w+)+.*$");
			break;	
	}
	return reg;
}
/*获取随机数数组 参数类型json 
案例:   {
			min:1，           最小范围
			max:50,           最大范围
			num:10,           随机数个数
			sort:"<",         排序方式
			isRepeat:true     是否有重复数字
		} 
*array 返回类型为数组 
*/
function getArrNum(option){
    var min = option.min || 0; //随机数最小值  默认为0
    var num = option.num;//随机数个数
    var max = option.max;//随机数最大值
    var sort = option.sort;//是否排序  '>'：从大到小排序   '<'：从小到大排序  不传则不排序
    var arr = []; //[10,20]
    var json = {}; //{'10':1,'20':1}   10
    while( arr.length < num ){
        var iNum =  Math.round( Math.random()*max );
        if(option.isRepeat){
			if( iNum > min ){
	            arr.push( iNum );
	        }
        }else{
			if( !json[iNum] && iNum > min ){
	            arr.push( iNum );
	            json[iNum] = 1;
	        }
        }
    }
    if(sort=='>'){
        arr.sort( function(a,b){ return b - a; } );
        return arr;
    }else if(sort=='<'){
        arr.sort( function(a,b){ return a - b; } );
        return arr;
    }else{
        return arr;
    }
}
/*手机号码等字符中间用 * 号替换
obj 即需要替换的对象
start 开始位置
number 需要替换几位字符
*/
function asterisk(obj,start,number){
	var strxh="";
	for(var i=0;i<number;i++){
		strxh+='*';
	}	
	return obj.replace(obj.substr(start,number),strxh);
}
/*获取复选框值 返回值类型 array*/
function getCheckboxVal(name){
  var group=document.getElementsByName(name); 
  var chkValue =[];
  for(var i=0,len=group.length;i<len;i++){
  	if(group[i].checked){
  		chkValue.push(group[i].value); 
  	}
  }
  return chkValue;
}

/*获取url hash*/
function getQueryString(name) {
    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return unescape(r[2]);
    }
    return null;
}

/*获取URL 中文 hash*/
function getChineseQueryString(name) {
    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return decodeURI(r[2]);
    }
    return null;
}


/*数组去重*/
function uniqueArr(arr){
	var json={};
	var result=[];
	for(var i=0; i<arr.length; i++){
		if(!json[arr[i]]){
			result.push(arr[i]);
			json[arr[i]]=true;
		}
	}
	return result;
};

/*给元素添加属性*/
function addAttr(obj,json){
	for(var attr in json){
		obj.setAttribute(attr, json[attr]);
	}
}

/*json 转换成get url传参方式*/
function jsonToGetUrl(json){
	var str="";
	for(var i in json){
		str+=i+'='+json[i]+'&';
	}
	return str.slice(0,-1);
}

/*拼json的key值*/
function jsonKeyStr(json){
	var str="";
	for(var i in json){
		str+=i+',';
	}
	return str.slice(0,-1);
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

/*生成随机字符串*/
function randomString(len) {
　　len = len || 32;
　　var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';    /****默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1****/
　　var maxPos = $chars.length;
　　var pwd = '';
　　for (i = 0; i < len; i++) {
　　　　pwd += $chars.charAt(Math.floor(Math.random() * maxPos));
　　}
　　return pwd;
}

/*下载app*/
function downLoadApp(json){
	var downLoadHref=document.getElementById(json.butId);
	/*苹果产品*/
	if(isIos||isIpad){
		downLoadHref.setAttribute("href",json.iosUrl);
	}
	/*安卓产品*/
	if(isAndroid || isPC){
		downLoadHref.setAttribute("href",json.androidUrl);
	}
	/*微信内置浏览器打开*/
	if(json.weiXinEle){
		if(IsWeiXin){
			document.getElementById(json.weiXinEle).style.display='block';
		}
	}
}

/*------------------------ end 原生扩展  ----------------------------------------*/

/*------------------------HightChart配置----------------------------------------*/
if(window.Highcharts){
	Highcharts.setOptions({
	    lang:{
	       contextButtonTitle:"图表导出菜单",
	       decimalPoint:".",
	       downloadJPEG:"下载JPEG图片",
	       downloadPDF:"下载PDF文件",
	       downloadPNG:"下载PNG文件",
	       downloadSVG:"下载SVG文件",
	       drillUpText:"返回 {series.name}",
	       loading:"加载中",
	       months:["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"],
	       noData:"没有数据",
	       numericSymbols: [ "千" , "兆" , "G" , "T" , "P" , "E"],
	       printChart:"打印图表",
	       resetZoom:"恢复缩放",
	       resetZoomTitle:"恢复图表",
	       shortMonths: [ "Jan" , "Feb" , "Mar" , "Apr" , "May" , "Jun" , "Jul" , "Aug" , "Sep" , "Oct" , "Nov" , "Dec"],
	       thousandsSep:",",
	       weekdays: ["星期一", "星期二", "星期三", "星期三", "星期四", "星期五", "星期六","星期天"]
	    },
	    colors: ['#97BBCD', '#DCDCDC', '#F7464A', '#f7a35c'],
	    yAxis: {
	    	title: {text: ''},
	    	min: 0
	    },
	    credits:false,
	    title: {
            style: { fontSize: '30px'},
            x: -20 //center
        },
        xAxis: {
            categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun','Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
        },
	});
};
/*-------------------------------------HightChart配置结束------------------------------------------------------*/

/*-------------------------------------时间格式化函数扩展开始---------------------------------------------------*/
// 对Date的扩展，将 Date 转化为指定格式的String 
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
// 例子： 
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
Date.prototype.format = function(fmt) 
{ //author: meizz 
  var o = { 
    "M+" : this.getMonth()+1,                 //月份 
    "d+" : this.getDate(),                    //日 
    "h+" : this.getHours(),                   //小时 
    "m+" : this.getMinutes(),                 //分 
    "s+" : this.getSeconds(),                 //秒 
    "q+" : Math.floor((this.getMonth()+3)/3), //季度 
    "S"  : this.getMilliseconds()             //毫秒 
  }; 
  if(/(y+)/.test(fmt)) 
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
  for(var k in o) 
    if(new RegExp("("+ k +")").test(fmt)) 
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length))); 
  return fmt; 
}

//字符串占位符
String.prototype.format=function()  
{  
  if(arguments.length==0) return this;  
  for(var s=this, i=1; i<=arguments.length; i++) 
    s=s.replace(new RegExp("\\{"+i+"\\}","g"), arguments[i-1]);  
  return s;  
};  



/*-------------------------------------时间格式化函数扩展结束----------------------------------------------------*/

/*------------------------------------后台权限配置文件 begin----------------------------------------------------*/ 
/*menu 权限表
  说明：menuPer.list 栏目顺序不能乱	
*/
/*如果是非登录页，从session中获取菜单、按钮及相关权限设置*/
if(window.location.href.indexOf("login.html")<0){
    console.log("缓存里有用户名："+localStorage.getItem("school-username"));
    //console.log("缓存里的菜单数据："+sessionStorage.accessData2);
    if(sessionStorage.accessData2&&(sessionStorage.accessData2!="")){
        console.log("直接从缓存拿菜单数据")
        window.menuPer = JSON.parse(sessionStorage.accessData2);
    }else{
        console.log("重新请求菜单数据")
        $.AJAX({
            type:"get",
            async:"false",
            url:config.basePath+"privilege/getUserMenu",
            success:function(data){
                window.menuPer = JSON.parse(data.data)
                var tempNavItemUser = "";
                var tempi="";
                for(var i=0;i<window.menuPer.list.length;i++){
                    //遍历查找用户管理的项，把它移到数组的最末位（菜单最后一项显示）
                    if(parseInt(window.menuPer.list[i].id)==150300){
                        tempNavItemUser = window.menuPer.list[i];
                        tempi = i;
                    }
                    //如果权限对象里包括排班管理，则为驾校专用，订单管理中去掉其它两个TAB项
                    //if(parseInt(window.menuPer.list[i].id)==2030000){
                    //    sessionStorage.setItem("isExamGround")
                    //}
                }
                window.menuPer.list.splice(tempi,1);
                window.menuPer.list.push(tempNavItemUser);
                sessionStorage.accessData2 = JSON.stringify(window.menuPer);
            }
        })
    }
}
//window.menuPer={
//	par:{
//		'yhgl':{name:'用户管理',icon:'ion-android-person'},
//		'zmgl':{name:'账目管理',icon:'ion-ios-list'},
//		'ykgl':{name:'约考管理',icon:'ion-ios-list'},
//	},
//	list:[
//		{id:100100,name:'教务报表',icon:'ion-stats-bars',src:'datas/data-statistics.html'},
//		{id:100200,name:'订单管理',icon:'ion-ios-paper',src:'order.html'},
//		{id:100200,name:'排班管理',icon:'ion-ios-paper',src:'arrange-exam-class.html'},
//		{id:100300,name:'学员管理',icon:'ion-university',src:'student.html'},
//		{id:100400,name:'教练管理',icon:'ion-ios-person',src:'coach.html'},
//		{id:100500,name:'车辆管理',icon:'ion-android-car',src:'car.html'},
//		{id:100600,name:'训练场管理',icon:'ion-ios-location',src:'field.html'},
//		{id:101900,name:'理论课管理',icon:'ion-ios-location',src:'theoretical-lessons.html'},
//		{id:102000,name:'长考看板',icon:'ion-ios-location',src:'long-exam.html'},
//		{id:102100,parenName:'ykgl',name:'导入约考情况',icon:'ion-ios-location',src:'import-book-exam.html'},
//		{id:102100,parenName:'ykgl',name:'导入考试成绩',icon:'ion-ios-location',src:'import-student-exam.html'},
//		{id:100600,parenName:'zmgl',name:'驾校钱包',src:'schools-account.html'},
//		{id:100900,name:'财务报表',icon:'ion-arrow-graph-up-right',src:'no-con/no-content-01.html'},
//		{id:101000,name:'考试管理',icon:'ion-document-text',src:'no-con/no-content-02.html'},
//		{id:101400,name:'班别管理',icon:'ion-android-list',src:'drive-school-package.html'},
//		{id:101300,name:'驾校管理',icon:'ion-model-s',src:'drive-wxschool.html'},
//		{id:101100,name:'营销管理',icon:'ion-android-people',src:'no-con/no-content-04.html'},
//		{id:101200,name:'客服管理',icon:'ion-android-contacts',src:'no-con/no-content-05.html'},
//		{id:100600,parenName:'yhgl',name:'修改密码',src:'change-password.html'},
//	]
//};

/*menu 表单筛选函数*/
function getMenu(arr){
	var newArr=[];
	for(var i=0,len=menuPer.list.length;i<len;i++){
		//for(var j=0,lenj=arr.length;j<lenj;j++){
		//	if(parseInt(arr[j])==menuPer.list[i].id){
				newArr.push(menuPer.list[i]);
		//	}
		//}
	}
    console.log(newArr)
	return newArr;
}

/*button 增 删 改 查 权限表
  说明：此表仅做查询用	
*/
window.buttonPer={
	100100:'数据统计',
	100300:'学员',
	100301:'学员查询',100302:'新增学员',100303:'学员详情',100304:'编辑学员',100305:'个人账户',100306:'导入流水',
	100400:'教练',
	100401:'教练查询',100402:'新增教练',100403:'教练详情',100404:'编辑教练',
	100200:'订单',
	100201:'订单查询',100202:'关闭订单',100203:'订单详情',
	100500:'教练车',
	100501:'教练车查询',100502:'新增教练车',100503:'教练车详情',100504:'编辑教练车',
	100600:'训练场',
	100601:'训练场查询',100602:'新增训练场',100603:'编辑训练场',
	100700:'理论课管理',
}

/*注入button权限*/
sessionStorage.setItem("school-btnList",jsonKeyStr(window.buttonPer));


/*---------------------------------------置学员状态配置---------------------------------------------------*/
/*驾校学员状态筛选配置数据*/
window.studentStatesListForSchool=[
	{name:'报名相关',id:1,list:['5,0','5,1','6,0','6,1','101,0','6,101']},
	{name:'科一相关',id:2,list:['101,100','101,101','201,0','201,100','301,0','301,101','302,0','302,101','302,100']},
	{name:'科二相关',id:2,list:['401,0','401,101','402,0','402,101','402,100']},
	//{name:'科三相关',id:2,list:['501,0','501,101','502,0','502,101','502,100','601,0','601,101','602,0','602,101',]},
	{name:'科三相关',id:2,list:['501,0','501,101','601,0','601,101','602,0','602,101',]},
	{name:'科四相关',id:2,list:['701,0','701,101','701,100',]},
];

/*置学员状态权限驾校版数据总表*/
window.studentSchoolJurisConfig={ 
	'5,1':['6,100'], //未收资料->已交表
	'6,1':['6,100'],  //表已寄出->已交表
	'6,100':['7,1'],  //已交表->受理中
	'7,1':['7,100','7,101'],   //受理中->报名成功,报名失败
	'7,100': ['101,100'],  //报名成功->已约理论课
	'101,100':['7,100','101,101','201,0'], //已约理论课->报名成功,缺理论课,未模拟考试
	'101,101':['101,100'],  //缺理论课->已约理论课
	'301,0':['302,0'], //科一约考排队中->已约考科一
	'301,101':['302,0','302,101','201,100'], //科一约考取消中->已约考科一,科一不合格,模拟考试达标
	'302,0':['302,101','302,100'], //已约考科一->科一不合格,科一合格
	'401,101':['302,100','402,0','402,101'],//科二约考取消中->科一合格,已约考科二,科二不合格
	'401,0':['402,0'],//科二约考排队中->已约考科二
	'402,0':['402,101','402,100'],//已约考科二->科二不合格,科二合格
	'501,0':['502,0'],  //长训约考排队中->已约考长训
	'501,101':['402,100','502,0'], //长训约考取消中->科二合格,已约考长训
	'502,0':['502,101','502,100'],//已约考长训->长训不合格,长训合格
	'601,0':['602,0'], //科三约考排队中->已约考科三
	'601,101':['502,100','602,0','602,101'], //科三约考取消中->长训合格,已约考科三,科三不合格
	'602,0':['602,101','701,0'], //已约考科三->科三不合格,已约考科四
	'701,0':['701,101','701,100'], //已约考科四->科四不合格,已拿证 
};

/*------------------------------------------------后台权限配置文件 end---------------------------------------------------*/ 
