function _error(e){switch(win.hideLoading(),e.status){case 401:-1==window.location.href.indexOf(config.loginUrl)?(sessionStorage.setItem("url",window.location.href),win.location.href=config.loginUrl):Layer.alert({width:300,height:150,type:"error",title:"用户鉴权失败,请重新登录"});break;case 404:Layer.alert({width:300,height:150,type:"error",title:"接口地址不存在"});break;case 500:case 502:Layer.alert({width:300,height:150,type:"error",title:"服务器内部错误"});break;case 601:Layer.alert({width:300,height:150,type:"error",title:"手机号码已存在"});break;case 602:Layer.alert({width:300,height:150,type:"error",title:"车牌号或车辆行驶证号已存在"});break;case 611:Layer.alert({width:300,height:150,type:"error",title:"数据更新失败"});break;case 612:Layer.alert({width:300,height:150,type:"error",title:"数据插入失败"});break;case 620:Layer.alert({width:300,height:150,type:"error",title:"没有操作权限"});break;default:Layer.alert({width:300,height:150,type:"error",title:"未知错误"})}}function log(e){console.log(e)}function dClone(e){return JSON.parse(JSON.stringify(e))}function createTag(e,t){t=t||{};var n=document.createElementNS("http://www.w3.org/2000/svg",e);for(var r in t)n.setAttribute(r,t[r]);return n}function d2a(e){return e*Math.PI/180}function a2d(e){return angule/Math.PI*180}function rand(e,t){return Math.round(Math.random()*(t-e)+e)}function time(){return(new Date).getTime()}function randColor(){for(var e=[],t=0;3>t;t++)e.push(fillLen(rand(0,255).toString(16)));return"#"+e.join("")}function color2rgba(e,t){t=t||1,e=e.replace("#",""),3==e.length&&(e=e.replace(/\w/g,function(e){return e+e}));var n=[];return e.replace(/\w{2}/g,function(e){n.push(Number("0x"+e))}),"rgba("+n+","+t+")"}function rgb2color(e){var t=e.match(/\d+/g);t.length=3;var n="#";return t.forEach(function(e){n+=fillLen(parseInt(e).toString(16))}),n}function toNum(e){return"number"==typeof e?e:/^\d+%$/.test(e)?e.replace("%","")/100:parseFloat(e)}function showLoading(){isLoading=!0,$("#loading").stop().fadeIn(iFadeInterval)}function hideLoading(){isLoading=!1,$("#loading").stop().fadeOut(iFadeInterval)}function fillLen(e,t){for(e=""+e,t=t||2;e.length<t;)e="0"+e;return e}function regCombination(e,t){var n="";switch(e){case"*":n=t?new RegExp("^[\\w\\W]{"+t[0]+","+t[1]+"}$"):new RegExp("^[\\w\\W]+$");break;case"number":n=t?new RegExp("^\\d{"+t[0]+","+t[1]+"}$"):new RegExp("^\\d+$");break;case"special":n=t?new RegExp("^[\\u4E00-\\u9FA5\\uf900-\\ufa2d\\w\\.\\s]{"+t[0]+","+t[1]+"}$"):new RegExp("^[\\u4E00-\\u9FA5\\uf900-\\ufa2d\\w\\.\\s]+$");break;case"chinese":n=new RegExp("^[\\u4E00-\\u9FA5\\uf900-\\ufa2d]{"+t[0]+","+t[1]+"}$");break;case"postcode":n=new RegExp("^[0-9]{6}$");break;case"phone":n=new RegExp("^13[0-9]{9}$|14[0-9]{9}|15[0-9]{9}$|18[0-9]{9}$");break;case"email":n=new RegExp("^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");break;case"url":n=new RegExp("^(\\w+:\\/\\/)?\\w+(\\.\\w+)+.*$")}return n}function getArrNum(e){for(var t=e.min||0,n=e.num,r=e.max,i=e.sort,o=[],a={};o.length<n;){var s=Math.round(Math.random()*r);e.isRepeat?s>t&&o.push(s):!a[s]&&s>t&&(o.push(s),a[s]=1)}return">"==i?(o.sort(function(e,t){return t-e}),o):"<"==i?(o.sort(function(e,t){return e-t}),o):o}function asterisk(e,t,n){for(var r="",i=0;n>i;i++)r+="*";return e.replace(e.substr(t,n),r)}function getCheckboxVal(e){for(var t=document.getElementsByName(e),n=[],r=0,i=t.length;i>r;r++)t[r].checked&&n.push(t[r].value);return n}function getQueryString(e){var t=new RegExp("(^|&)"+e+"=([^&]*)(&|$)","i"),n=window.location.search.substr(1).match(t);return null!=n?unescape(n[2]):null}function uniqueArr(e){for(var t={},n=[],r=0;r<e.length;r++)t[e[r]]||(n.push(e[r]),t[e[r]]=!0);return n}function addAttr(e,t){for(var n in t)e.setAttribute(n,t[n])}window.config={loginUrl:"login.html",homeUrl:"index.html",basePath:"http://192.168.1.21:80/",defaultSrcObj:$("#admin-nav li").eq(0).find("div"),layerPhoneWidth:$(window).width()<768?.9*$(window).width():660,popLayerHideTime:1e3,ajaxtimeout:1e3},window.$&&($.AJAX=function(e){var t=!0;$.ajax({type:e.type||"post",url:e.url,xhrFields:{withCredentials:!0},crossDomain:!0,data:e.data||"",datatype:"json",success:function(t){win.hideLoading(),clearTimeout(n),e.success(t)},error:function(e){win.hideLoading(),clearTimeout(n),t&&_error(e)}});var n=setTimeout(function(){win.hideLoading(),t=!1,console.log("请求超时，请刷新重试!"),Layer.alert({width:300,height:150,type:"error",title:"请求超时，请刷新重试!"})},e.timeout||config.ajaxtimeout)}),function(){var e=window.location.href;-1==e.indexOf(config.loginUrl)&&sessionStorage.setItem("url",window.location.href)}(),window==window.top&&document.write('<div id="loading"></div>');var win=window.top,UA=navigator.userAgent,isPC=UA.indexOf("Windows NT")>-1,isAndroid=UA.indexOf("Android")>-1,isIos=UA.indexOf("Mac OS X")>-1,isIphone=UA.indexOf("iPhone;")>-1,isIpad=UA.indexOf("iPad;")>-1,isIE7=UA.indexOf("MSIE 7.0;")>-1,isIE8=UA.indexOf("MSIE 8.0;")>-1,isIE9=UA.indexOf("MSIE 9.0;")>-1,isIE10=UA.indexOf("MSIE 10.0;")>-1,isIE11=UA.indexOf("Trident")>-1,isFirefox=UA.indexOf("Firefox")>-1,IsWeiXin=UA.indexOf("MicroMessenger")>-1,iFadeInterval=260,iSlideInterval=200,isLoading=!1,baseURL="",$_GET={};(isIE7||isIE8||isIE9)&&(window.location.href="ieTestingBrowser/ie-testing-browser.html"),Date.prototype.date=function(e){var t=this.getFullYear(),n=fillLen(this.getMonth()+1),r=fillLen(this.getDate()),i=fillLen(this.getHours()),o=fillLen(this.getMinutes()),a=fillLen(this.getSeconds()),s={y:t,m:n,d:r,h:i,i:o,s:a};return e?e.replace(/y|m|d|h|i|s/g,function(e){return s[e]}):t+"-"+n+"-"+r+" "+i+":"+o+":"+a},window.Highcharts&&Highcharts.setOptions({lang:{contextButtonTitle:"图表导出菜单",decimalPoint:".",downloadJPEG:"下载JPEG图片",downloadPDF:"下载PDF文件",downloadPNG:"下载PNG文件",downloadSVG:"下载SVG文件",drillUpText:"返回 {series.name}",loading:"加载中",months:["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"],noData:"没有数据",numericSymbols:["千","兆","G","T","P","E"],printChart:"打印图表",resetZoom:"恢复缩放",resetZoomTitle:"恢复图表",shortMonths:["Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"],thousandsSep:",",weekdays:["星期一","星期二","星期三","星期三","星期四","星期五","星期六","星期天"]},colors:["#97BBCD","#DCDCDC","#F7464A","#f7a35c"],yAxis:{title:{text:""},min:0},credits:!1,title:{style:{fontSize:"30px"},x:-20},xAxis:{categories:["Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"]}});