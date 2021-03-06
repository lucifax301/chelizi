﻿/************************************后台配置**********************************/
window.config={
    loginUrl:"#/login",                                       //登陆页面
    homeUrl: "#/dashboard",                                       //登陆成功后的页面
    baseUrl:"http://xc.res.com:8080/examGround-h5/",						//网站URL--开发
    basePath:'http://192.168.1.99:9106/portal/',                //99服务器 接口--开发
    //baseUrl:"http://"+window.location.host+"/wx/",						//网站URL--线上
    //basePath:"http://"+window.location.host+"/portal/",                // 接口--线上
    layerPhoneWidth:$(window).width()<768?$(window).width()*0.9:660,    //弹出层手机访问时的宽度
    popLayerHideTime:1000,                                              //弹出框 消失层 的消失时间
    contentTimeShow:200,                                                //主模板display:block的时间
    ajaxtimeout:8000,                                                   //ajax 超时配置
    explTotal:1000,                                                     //导出数据最大条数 提示值
    msgTime:60,                                                         //发送短信间隔时间
};


/*-----------缩放比--------------*/
//缩放比
var phoneWidth = parseInt(window.screen.width);
var phoneScale = phoneWidth/750;
var userAgent = navigator.userAgent;
var index = userAgent.indexOf("Android");
if(index >= 0){
var androidVersion = parseFloat(userAgent.slice(index+8));
if(androidVersion>2.3){
    $("head").eq(0).append("<meta name='viewport' content='width=750, initial-scale = "+phoneScale+", minimum-scale = "+phoneScale+", maximum-scale = "+phoneScale+",user-scalable=no, target-densitydpi=device-dpi'>");
	}else{
		$("head").eq(0).append("<meta name='viewport' content='width=750, target-densitydpi=device-dpi'>");
	}

}else{
	$("head").eq(0).append("<meta name='viewport' content='width=750, initial-scale = "+phoneScale+", minimum-scale = "+phoneScale+", maximum-scale = "+phoneScale+",user-scalable=no, target-densitydpi=device-dpi'>");
}

/*------------------------  start 布局写入  ------------------------*/

//window==window.top && document.write("<div id='loading'></div>") && document.write("<script src='/assets/common/js/zepto.min.js'></script>") && document.write("<script src='/assets/common/js/angular.min.js'></script>") && document.write("<script src='/assets/js/ng-js/angular-filter.js'></script>") && document.write("<script src='/assets/common/js/layer-mobile.js'></script>") && document.write("<script src='/assets/common/js/md5.js'></script>") && document.write("<script src='/assets/js/function.js'></script>");
/*------------------------ end 布局写入  ------------------------*/

/*------------------------ start jquery 相关  ------------------------*/
/*数据交互ajax*/
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
                Popup.alert({type:'error',style:'width:80%',title:'请求超时，请刷新重试!'});
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
            Popup.alert({type:'error',style:'width:80%',title:'请求超时，请刷新重试!'});
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
                    win.location.href=startConfig.baseUrl+config.loginUrl;
                }else{
                    Popup.alert({type:'error',style:'width:80%',title:'鉴权失败,请重新登录'});
                }
                break;
            case 601:
                json.error(data);
                break;
            default:
                //直接弹出错误信息
                Popup.alert({type:'error',style:'width:80%',title:data.msg});
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
                        win.location.href=window.config.baseUrl+window.config.loginUrl;
                    }else{
                        Popup.alert({type:'error',style:'width:80%',title:"鉴权失败,请重新登录"});
                    }
                    break;
                case 404:
                    Popup.alert({type:'error',style:'width:80%',title:"接口地址不存在"});
                    break;
                case 500:case 502:
                    Popup.alert({type:'error',style:'width:80%',title:"服务器内部错误"});
                    break;
                case 611:
                    Popup.alert({type:'error',style:'width:80%',title:"数据更新失败"});
                    break;
                case 612:
                    Popup.alert({type:'error',style:'width:80%',title:"数据插入失败"});
                    break;
                default:
                    Popup.alert({type:'error',style:'width:80%',title:"未知错误"});
            }
        }
    }
}

/*------------------------ end jquery 相关  ------------------------*/

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


/*------------------------ start 原生扩展  ------------------------*/

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

/*js trim函数*/
String.prototype.trim=function(){
　 return this.replace(/(^\s*)|(\s*$)/g, "");
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
	$('#loading').fadeIn(iFadeInterval);
};
function hideLoading(){
	isLoading=false;
	$('#loading').fadeOut(iFadeInterval);
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

/*获取url hash 解决中文乱码问题 */
function getChineseQueryString(name) {
	var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
	var r = window.location.search.substr(1).match(reg);
	if (r != null) {
		return decodeURI(r[2]);
	}
	return null;
}
//路由转发后获取url
function getUrlString(id){
	var nowurl = window.location.href.split('&&');
	for(var i in nowurl){
		if(nowurl[i].indexOf(id+'=')>-1){
			var fenge = nowurl[i].split('=');
			return decodeURI(fenge[1]);
		}
	}
}
/*数字去重*/
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
	for(var i in window.buttonPer){
		str+=i+',';
	}
	return str.slice(0,-1);
}

/*生成随机字符串*/
function randomString(len) {
　　len = len || 32;
	/****默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1****/
　　var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678'; 
　　var maxPos = $chars.length;
　　var pwd = '';
　　for (i = 0; i < len; i++) {
　　　　pwd += $chars.charAt(Math.floor(Math.random() * maxPos));
　　}
　　return pwd;
}
/*下载app*/
function downLoadApp(json){
	var downLoadUrl=json.downLoadUrl||config.download_student_address;
	var downLoadHref=document.getElementById(json.butId);
	/*非微信产品*/
	downLoadHref.setAttribute("href",downLoadUrl);
}
/*检测是不是数组里面的值*/
function checkIn(arr,value){
	var result=false
	for(var i=0,len=arr.length;i<len;i++){
		if(arr[i]==value){
			result=true;
		}
	}
	return result;
}
/*检测某值在数组中是否存在*/
function isInArray(arr,value){
	var result=false;
	for(var i=0,len=arr.length;i<len;i++){
		if(value.indexOf(arr[i]) != -1){
			result=true;
		};
	};
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
/*根据某个key值得到数据集合*/
function getKeyArrFromData(datas,key){
	var newArr=[];
	for(var i=0,len=datas.length;i<len;i++){
		newArr.push(datas[i][key]);
	}
	return newArr;
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
/*extent json函数*/
function extend(json1,json2){
	var newJson=json1;
	for(i in json1){
		for( j in json2){
			newJson[j]=json2[j];
		}
	}
	return newJson;
}
//解析URL获取参数   如 getUrl（'page'）
function getUrl(id){
	var nowurl = window.location.href.split('&');
	for(var i in nowurl){
		if(nowurl[i].indexOf(id+'=')>-1){
			var fenge = nowurl[i].split('=');
			return decodeURI(fenge[1]);
		}
	}
}


/*------------------------ end 原生扩展  ------------------------*/