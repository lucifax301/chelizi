!function(e,t,i){var n=t.module("ngSelects",[]);n.factory("Selects",["$rootScope",function(e){return{selects:function(t){var i=t.num?t.num:"",n="checkAll"+i,o="idList"+i;e[n]=!1,e[o]=[];var c=t.datas,g=t.whichId;e.selectionAll=function(i){var a=i.target,r=!!a.checked;if(r){e[o]=[],e[n]=!0;for(var l=0,m=c.length;m>l;l++)if(t.trem)for(var s=0,f=t.trem.list.length;f>s;s++)c[l][t.trem.check]==t.trem.list[s]&&e[o].push(c[l][g]);else e[o].push(c[l][g])}else e[n]=!1,e[o]=[]};var a=function(t,i){if("add"==t&&-1==e[o].indexOf(i)&&e[o].push(i),"remove"==t&&-1!=e[o].indexOf(i)){var c=e[o].indexOf(i);e[o].splice(c,1),e[o].length<=0&&(e[n]=!1)}};e.updateSelection=function(e,t){var i=e.target,n=i.checked?"add":"remove";a(n,t)},e.isSelected=function(t){return e[o].indexOf(t)>=0}}}}]),n.factory("getMsgTime",["$rootScope",function(e){return{getNowTime:function(){e.isClick=!(localStorage.getItem("nowMsgTime")<config.msgTime),e.getMsgText="获取验证码",e.runTime=function(t){if(localStorage.getItem("nowTime"))var i=setInterval(function(){var n=config.msgTime-Math.floor(((new Date).getTime()-localStorage.getItem("nowTime"))/1e3);e.nowMsgTime=n,localStorage.setItem("nowMsgTime",n),e.$apply(),0>=n&&(clearInterval(i),e.isClick=!0,localStorage.setItem("nowMsgTime",config.msgTime),e.nowMsgTime="",e.getMsgText="重新获取",t&&t(),e.$apply())},1e3)},localStorage.getItem("nowMsgTime")<config.msgTime&&e.runTime()},shotMessage:function(t){e.getMsgText="重新获取",localStorage.setItem("nowMsgTime",config.msgTime),localStorage.setItem("nowTime",(new Date).getTime()),e.isClick=!1,e.runTime(t)}}}])}(window,window.angular);