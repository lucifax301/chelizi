
/*自定义 $filter 公共过虐器*/
(function(window, angular, undefined) {

var app=angular.module('ngSelects', []);

/*-------------------------------factory 不分页AJAX分页 服务---------------------------------------*/

/*
调用方法 trem 是需要检测的项目
         check 坚持的key
         list 需要检测的ID
Selects.selects({datas:$s.datas,whichId:'id',trem:{
	check:"checkStatus",
	list:[0,3,4,5], //可选择的状态
}});
*/

/*全选与取消全选*/
app.factory('Selects', ['$rootScope', function($s){
	 return {
        selects: function(json) {
			/*全选与取消全选*/
			var num=json.num?json.num:'';
			var checkAll='checkAll'+num;
			var idList='idList'+num;
			$s[checkAll]=false;
			$s[idList]=[];
			var datas=json.datas;
			var whichId=json.whichId;
			$s.selectionAll=function($event){
				var checkbox = $event.target;
				var action = checkbox.checked?true:false;
				if(action){
					$s[idList]=[];
					$s[checkAll]=true;
					for(var i=0,len=datas.length;i<len;i++){
						if(json.trem){ //是否有筛选条件
							for(var j=0,lenj=json.trem['list'].length;j<lenj;j++){
								if(datas[i][json.trem['check']]==json.trem['list'][j]){
									$s[idList].push(datas[i][whichId]);
								}
							};
						}else{
							$s[idList].push(datas[i][whichId]);
						}
					}
				}else{
					$s[checkAll]=false;
					$s[idList]=[];
				}
			}
			/*checkbox操作选择*/
		    var updateSelected = function(action,id){
		        if(action == 'add' && $s[idList].indexOf(id) == -1){
		            $s[idList].push(id);
		        }
		        if(action == 'remove' && $s[idList].indexOf(id)!=-1){
		            var idx = $s[idList].indexOf(id);
		            $s[idList].splice(idx,1);
		            if($s[idList].length<=0){
		            	$s[checkAll]=false;
		            }
		        }
		    }
			$s.updateSelection = function($event, id){
		        var checkbox = $event.target;
		        var action = (checkbox.checked?'add':'remove');
		        updateSelected(action,id);
		    }
		    $s.isSelected = function(id){
		        return $s[idList].indexOf(id)>=0;
		    }
		   
		}
      };
}]);

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







