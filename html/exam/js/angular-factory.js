
/*自定义 $filter 公共过虐器*/
(function(window, angular, undefined) {

var app=angular.module('ngSelects', []);


/*------------------------------------短信定时器---------------------------------------*/
/*
 暴露的接口：
 	n:参数n为短信版本号，可以是任何字符串
    getMsgTime.getNowTime(n);     页面加载后短信时间没有跑完时继续执行 
    getMsgTime.shotMessage(n,function(){
		//一个回调函数时间走完后执行
    });    定时器开始执行
*/
app.factory('getMsgTime', ['$rootScope', function($s){
    return {
        getNowTime:function(num){
        	console.log(localStorage.getItem("nowMsgTime"+num));
           $s.isClick=localStorage.getItem("nowMsgTime"+num)<config.msgTime?false:true;
           $s.getMsgText="获取验证码";
           //运行函数
           $s.runTime=function(num,fn){
                var timer=setInterval(function(){
	                var nowTime=config.msgTime-Math.floor((new Date().getTime()-localStorage.getItem("nowTime"+num))/1000);
	                $s.nowMsgTime=nowTime;
	                localStorage.setItem("nowMsgTime"+num,nowTime);
	                $s.$apply();
	                 //时间走完时执行
	                if(nowTime<=0){
	                    clearInterval(timer);
	                    $s.isClick=true; 
	                    localStorage.setItem("nowMsgTime"+num,config.msgTime);
	                    $s.nowMsgTime="";
	                    //$s.getMsgText="重新获取";
	                    if(fn){fn()};
	                    $s.$apply();  
	                }
	            },1000);
           };
           if(localStorage.getItem("nowMsgTime"+num)<config.msgTime){
                $s.runTime(num);
           };
        },
        shotMessage:function(num,fn){
        	//$s.getMsgText="重新获取";
        	//设置初始总时间
            localStorage.setItem("nowMsgTime"+num,config.msgTime);
            //设置当然前时间
            localStorage.setItem("nowTime"+num,new Date().getTime()); 
            $s.isClick=false;
            $s.runTime(num,fn);
        }
    }
}]);


})(window, window.angular);







