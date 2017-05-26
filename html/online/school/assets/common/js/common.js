function error(e,t){switch(parseInt(e.code)){case 0:t.success(e);break;case 1:-1==window.location.href.indexOf(config.loginUrl)?(sessionStorage.setItem("url",window.location.href),win.location.href=startConfig.bathUrl+config.loginUrl):Layer.alert({width:300,height:150,type:"error",title:"用户鉴权失败,请重新登录"});break;case 601:t.error(e);break;default:Layer.alert({width:350,height:150,type:"error",title:e.msg})}}function _error(e,t){if(win.hideLoading(),t.code)t.error(JSON.parse(e.responseText));else switch(e.status){case 401:-1==window.location.href.indexOf(config.loginUrl)?(sessionStorage.setItem("url",window.location.href),win.location.href=startConfig.bathUrl+config.loginUrl):Layer.alert({width:300,height:150,type:"error",title:"用户鉴权失败,请重新登录"});break;case 404:Layer.alert({width:300,height:150,type:"error",title:"接口地址不存在"});break;case 500:case 502:Layer.alert({width:300,height:150,type:"error",title:"服务器内部错误"});break;case 601:Layer.alert({width:300,height:150,type:"error",title:"手机号码已存在"});break;case 602:Layer.alert({width:300,height:150,type:"error",title:"车牌号或车辆行驶证号已存在"});break;case 603:Layer.alert({width:300,height:150,type:"error",title:"教练已绑定该车"});break;case 611:Layer.alert({width:300,height:150,type:"error",title:"数据更新失败"});break;case 612:Layer.alert({width:300,height:150,type:"error",title:"数据插入失败"});break;case 613:Layer.alert({width:300,height:150,type:"error",title:"数据查询失败"});break;case 614:Layer.alert({width:300,height:150,type:"error",title:"数据删除失败"});break;case 620:Layer.alert({width:300,height:150,type:"error",title:"没有操作权限"});break;case 630:Layer.alert({width:300,height:150,type:"error",title:"Excel导出失败"});break;case 699:Layer.alert({width:300,height:150,type:"error",title:"请求参数有误"});break;case 701:Layer.alert({width:300,height:150,type:"error",title:"金额超出限制"});break;case 702:Layer.alert({width:300,height:150,type:"error",title:"该记录状态不对，不能进行操作"});break;case 703:Layer.alert({width:300,height:150,type:"error",title:"奖金有重复教练，请删除后再试"});break;case 704:Layer.alert({width:300,height:150,type:"error",title:"发放奖金失败"});break;default:Layer.alert({width:300,height:150,type:"error",title:"未知错误"})}}function dClone(e){return JSON.parse(JSON.stringify(e))}function createTag(e,t){t=t||{};var n=document.createElementNS("http://www.w3.org/2000/svg",e);for(var r in t)n.setAttribute(r,t[r]);return n}function d2a(e){return e*Math.PI/180}function a2d(e){return angule/Math.PI*180}function rand(e,t){return Math.round(Math.random()*(t-e)+e)}function time(){return(new Date).getTime()}function randColor(){for(var e=[],t=0;3>t;t++)e.push(fillLen(rand(0,255).toString(16)));return"#"+e.join("")}function color2rgba(e,t){t=t||1,e=e.replace("#",""),3==e.length&&(e=e.replace(/\w/g,function(e){return e+e}));var n=[];return e.replace(/\w{2}/g,function(e){n.push(Number("0x"+e))}),"rgba("+n+","+t+")"}function rgb2color(e){var t=e.match(/\d+/g);t.length=3;var n="#";return t.forEach(function(e){n+=fillLen(parseInt(e).toString(16))}),n}function toNum(e){return"number"==typeof e?e:/^\d+%$/.test(e)?e.replace("%","")/100:parseFloat(e)}function showLoading(){isLoading=!0,$("#loading").stop().fadeIn(iFadeInterval)}function hideLoading(){isLoading=!1,$("#loading").stop().fadeOut(iFadeInterval)}function fillLen(e,t){for(e=""+e,t=t||2;e.length<t;)e="0"+e;return e}function regCombination(e,t){var n="";switch(e){case"*":n=t?new RegExp("^[\\w\\W]{"+t[0]+","+t[1]+"}$"):new RegExp("^[\\w\\W]+$");break;case"number":n=t?new RegExp("^\\d{"+t[0]+","+t[1]+"}$"):new RegExp("^\\d+$");break;case"special":n=t?new RegExp("^[\\u4E00-\\u9FA5\\uf900-\\ufa2d\\w\\.\\s]{"+t[0]+","+t[1]+"}$"):new RegExp("^[\\u4E00-\\u9FA5\\uf900-\\ufa2d\\w\\.\\s]+$");break;case"chinese":n=new RegExp("^[\\u4E00-\\u9FA5\\uf900-\\ufa2d]{"+t[0]+","+t[1]+"}$");break;case"postcode":n=new RegExp("^[0-9]{6}$");break;case"phone":n=new RegExp("^13[0-9]{9}$|14[0-9]{9}|15[0-9]{9}$|17[0-9]{9}$|18[0-9]{9}$");break;case"email":n=new RegExp("^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");break;case"id":n=new RegExp("^\\d{17}[\\dXx]$");break;case"url":n=new RegExp("^(\\w+:\\/\\/)?\\w+(\\.\\w+)+.*$")}return n}function getArrNum(e){for(var t=e.min||0,n=e.num,r=e.max,i=e.sort,o=[],a={};o.length<n;){var s=Math.round(Math.random()*r);e.isRepeat?s>t&&o.push(s):!a[s]&&s>t&&(o.push(s),a[s]=1)}return">"==i?(o.sort(function(e,t){return t-e}),o):"<"==i?(o.sort(function(e,t){return e-t}),o):o}function asterisk(e,t,n){for(var r="",i=0;n>i;i++)r+="*";return e.replace(e.substr(t,n),r)}function getCheckboxVal(e){for(var t=document.getElementsByName(e),n=[],r=0,i=t.length;i>r;r++)t[r].checked&&n.push(t[r].value);return n}function getQueryString(e){var t=new RegExp("(^|&)"+e+"=([^&]*)(&|$)","i"),n=window.location.search.substr(1).match(t);return null!=n?unescape(n[2]):null}function uniqueArr(e){for(var t={},n=[],r=0;r<e.length;r++)t[e[r]]||(n.push(e[r]),t[e[r]]=!0);return n}function addAttr(e,t){for(var n in t)e.setAttribute(n,t[n])}function jsonToGetUrl(e){var t="";for(var n in e)t+=n+"="+e[n]+"&";return t.slice(0,-1)}function jsonKeyStr(e){var t="";for(var n in e)t+=n+",";return t.slice(0,-1)}function deleteJson(e,t){var n={};for(var r in e)r!=t&&(n[r]=e[r]);return n}function randomString(e){e=e||32;var t="ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678",n=t.length,r="";for(i=0;i<e;i++)r+=t.charAt(Math.floor(Math.random()*n));return r}function downLoadApp(e){var t=document.getElementById(e.butId);(isIos||isIpad)&&t.setAttribute("href",e.iosUrl),(isAndroid||isPC)&&t.setAttribute("href",e.androidUrl),e.weiXinEle&&IsWeiXin&&(document.getElementById(e.weiXinEle).style.display="block")}function getMenu(e){for(var t=[],n=0,r=menuPer.list.length;r>n;n++)for(var i=0,o=e.length;o>i;i++)parseInt(e[i])==menuPer.list[n].id&&t.push(menuPer.list[n]);return t}window.config={loginUrl:"login.html",homeUrl:"index.html",indexGoToSrc:"datas/data-statistics.html",basePath:"http://"+window.location.host+"/portal/",layerPhoneWidth:$(window).width()<768?.9*$(window).width():660,popLayerHideTime:1e3,contentTimeShow:200,ajaxtimeout:8e3,explTotal:1e3,explPrompt:"导出最新的1000条数据!",msgTime:60},window.$&&($.AJAX=function(e){var t=!0;if($.ajax({type:e.type||"post",url:e.url,xhrFields:{withCredentials:!0},crossDomain:!0,data:e.data||"",datatype:"json",async:e.async,success:function(t){e.nohideloading||win.hideLoading(),clearTimeout(n),error(t,e)},error:function(r){win.hideLoading(),clearTimeout(n),t&&_error(r,e)}}),0!=e.async)var n=setTimeout(function(){win.hideLoading(),t=!1,Layer.alert({width:300,height:150,type:"error",title:"请求超时，请刷新重试!"})},e.timeout||config.ajaxtimeout)},$.FileAJAX=function(e){var t=!0;$.ajax({type:e.type||"post",url:e.url,xhrFields:{withCredentials:!0},crossDomain:!0,data:e.data||"",datatype:"json",cache:!1,contentType:!1,processData:!1,success:function(t){win.hideLoading(),clearTimeout(n),error(t,e)},error:function(r){win.hideLoading(),e.error(),clearTimeout(n),t&&_error(r)}});var n=setTimeout(function(){win.hideLoading(),t=!1,Layer.alert({width:300,height:150,type:"error",title:"请求超时，请刷新重试!"})},e.timeout||config.ajaxtimeout)}),function(){var e=window.location.href;-1==e.indexOf(config.loginUrl)&&sessionStorage.setItem("school-url",window.location.href)}(),window==window.top&&document.write('<div id="loading"></div>');var win=window.top,UA=navigator.userAgent,isPC=UA.indexOf("Windows NT")>-1,isAndroid=UA.indexOf("Android")>-1,isIos=UA.indexOf("Mac OS X")>-1,isIphone=UA.indexOf("iPhone;")>-1,isIpad=UA.indexOf("iPad;")>-1,isIE7=UA.indexOf("MSIE 7.0;")>-1,isIE8=UA.indexOf("MSIE 8.0;")>-1,isIE9=UA.indexOf("MSIE 9.0;")>-1,isIE10=UA.indexOf("MSIE 10.0;")>-1,isIE11=UA.indexOf("Trident")>-1,isFirefox=UA.indexOf("Firefox")>-1,IsWeiXin=UA.indexOf("MicroMessenger")>-1,iFadeInterval=260,iSlideInterval=200,isLoading=!1,baseURL="",$_GET={};(isIE7||isIE8||isIE9)&&(window.location.href="ieTestingBrowser/ie-testing-browser.html"),Date.prototype.date=function(e){var t=this.getFullYear(),n=fillLen(this.getMonth()+1),r=fillLen(this.getDate()),i=fillLen(this.getHours()),o=fillLen(this.getMinutes()),a=fillLen(this.getSeconds()),s={y:t,m:n,d:r,h:i,i:o,s:a};return e?e.replace(/y|m|d|h|i|s/g,function(e){return s[e]}):t+"-"+n+"-"+r+" "+i+":"+o+":"+a},window.Highcharts&&Highcharts.setOptions({lang:{contextButtonTitle:"图表导出菜单",decimalPoint:".",downloadJPEG:"下载JPEG图片",downloadPDF:"下载PDF文件",downloadPNG:"下载PNG文件",downloadSVG:"下载SVG文件",drillUpText:"返回 {series.name}",loading:"加载中",months:["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"],noData:"没有数据",numericSymbols:["千","兆","G","T","P","E"],printChart:"打印图表",resetZoom:"恢复缩放",resetZoomTitle:"恢复图表",shortMonths:["Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"],thousandsSep:",",weekdays:["星期一","星期二","星期三","星期三","星期四","星期五","星期六","星期天"]},colors:["#97BBCD","#DCDCDC","#F7464A","#f7a35c"],yAxis:{title:{text:""},min:0},credits:!1,title:{style:{fontSize:"30px"},x:-20},xAxis:{categories:["Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"]}}),Date.prototype.format=function(e){var t={"M+":this.getMonth()+1,"d+":this.getDate(),"h+":this.getHours(),"m+":this.getMinutes(),"s+":this.getSeconds(),"q+":Math.floor((this.getMonth()+3)/3),S:this.getMilliseconds()};/(y+)/.test(e)&&(e=e.replace(RegExp.$1,(this.getFullYear()+"").substr(4-RegExp.$1.length)));for(var n in t)new RegExp("("+n+")").test(e)&&(e=e.replace(RegExp.$1,1==RegExp.$1.length?t[n]:("00"+t[n]).substr((""+t[n]).length)));return e},String.prototype.format=function(){if(0==arguments.length)return this;for(var e=this,t=1;t<=arguments.length;t++)e=e.replace(new RegExp("\\{"+t+"\\}","g"),arguments[t-1]);return e},window.menuPer={par:{yhgl:{name:"用户管理",icon:"ion-android-person"},ykgl:{name:"约考管理",icon:"ion-ios-list"}},list:[{id:100100,name:"教务报表",icon:"ion-stats-bars",src:"datas/data-statistics.html"},{id:100200,name:"订单管理",icon:"ion-ios-paper",src:"order.html"},{id:100300,name:"学员管理",icon:"ion-university",src:"student.html"},{id:100400,name:"教练管理",icon:"ion-ios-person",src:"coach.html"},{id:100500,name:"车辆管理",icon:"ion-android-car",src:"car.html"},{id:100600,name:"训练场管理",icon:"ion-ios-location",src:"field.html"},{id:101900,name:"理论课管理",icon:"ion-ios-location",src:"theoretical-lessons.html"},{id:102e3,name:"长考看板",icon:"ion-ios-location",src:"long-exam.html"},{id:102100,parenName:"ykgl",name:"导入约考情况",icon:"ion-ios-location",src:"import-book-exam.html"},{id:102100,parenName:"ykgl",name:"导入考试成绩",icon:"ion-ios-location",src:"import-student-exam.html"},{id:100900,name:"财务报表",icon:"ion-arrow-graph-up-right",src:"no-con/no-content-01.html"},{id:101e3,name:"考试管理",icon:"ion-document-text",src:"no-con/no-content-02.html"},{id:101400,name:"班别管理",icon:"ion-android-list",src:"no-con/no-content-03.html"},{id:101100,name:"营销管理",icon:"ion-android-people",src:"no-con/no-content-04.html"},{id:101200,name:"客服管理",icon:"ion-android-contacts",src:"no-con/no-content-05.html"},{id:101300,name:"驾校管理",icon:"ion-model-s",src:"no-con/no-content-06.html"},{id:100600,parenName:"yhgl",name:"修改密码",src:"change-password.html"}]},window.buttonPer={100100:"数据统计",100300:"学员",100301:"学员查询",100302:"新增学员",100303:"学员详情",100304:"编辑学员",100305:"个人账户",100306:"导入流水",100400:"教练",100401:"教练查询",100402:"新增教练",100403:"教练详情",100404:"编辑教练",100200:"订单",100201:"订单查询",100202:"关闭订单",100203:"订单详情",100500:"教练车",100501:"教练车查询",100502:"新增教练车",100503:"教练车详情",100504:"编辑教练车",100600:"训练场",100601:"训练场查询",100602:"新增训练场",100603:"编辑训练场",100700:"理论课管理"},sessionStorage.setItem("school-btnList",jsonKeyStr(window.buttonPer)),window.studentStatesListForSchool=[{name:"报名相关",id:1,list:["5,0","5,1","6,0","6,1","6,100","6,101"]},{name:"科一相关",id:2,list:["101,100","101,101","201,0","201,100","301,0","301,101","302,0","302,101","302,100"]},{name:"科二相关",id:2,list:["401,0","401,101","402,0","402,101","402,100"]},{name:"科三相关",id:2,list:["501,0","501,101","502,0","502,101","502,100","601,0","601,101","602,0","602,101"]},{name:"科四相关",id:2,list:["701,0","701,101","701,100"]}],window.studentSchoolJurisConfig={"5,1":["6,100"],"6,1":["6,100"],"6,100":["7,1"],"7,1":["7,100","7,101"],"7,100":["101,100"],"101,100":["7,100","101,101","201,0"],"101,101":["101,100"],"301,0":["302,0"],"301,101":["302,0","302,101","201,100"],"302,0":["302,101","302,100"],"401,101":["302,100","402,0","402,101"],"401,0":["402,0"],"402,0":["402,101","402,100"],"501,0":["502,0"],"501,101":["402,100","502,0"],"502,0":["502,101","502,100"],"601,0":["602,0"],"601,101":["502,100","602,0","602,101"],"602,0":["602,101","701,0"],"701,0":["701,101","701,100"]};