//var app=angular.module("app",["ngSelects"]);
app.controller("RcAccountAdd",["$scope","$filter",function($scope,$filter){
    $scope.defaultPage= getUrl('page') || 1;  //默认请求页
    $scope.pageSize=10;    //每页显示-显示条数
    /*hash值改变的时候加载数据列表*/
    window.onhashchange=function(){
        $scope.defaultPage= getUrl('page') || 1;  //默认请求页
        $scope.getDataList();
    }

    //回车事件
    $scope.getkey = function(e){
        var keycode = window.event?e.keyCode:e.which;
        if(keycode==13){
            $scope.searchList();
        }
    }
 
	$scope.getDataList = function(page) {
		if(page) {
			var nowpage = page;
		} else {
			var nowpage = 1;
		}
		$.AJAX({
			type : "POST",
			url : config.basePath + "groupAccount/notAccountCoachList",
			data : {
				workType : $scope.workType,
                condition : $scope.condition,
				pageNo : nowpage,
				pageSize : $scope.pageSize,
				
			},
			success : function(data) {
				$scope.datas = data.result.list;
				$scope.total = data.result.total;
				$("#coachSum").text(data.result.list[0].coachSum );
				new Page({
					parent : $("#copot-page"),
					nowPage : nowpage,
					pageSize : $scope.pageSize,
					totalCount : $scope.total,
					type:2,
					callback:$scope.getDataList
				}); 
				$scope.$apply();
				$(".subcheck").prop("checked", true);
			}
		});  
	}
			
	$scope.getDataList();

    
    //查询
    /*$scope.searchList = function(){
        if($scope.condition && typeof($scope.workType)=='undefined' ){
            var nowURL = location.href.split('#');
            var newurl = nowURL[1] + "#&&search=" + $scope.condition+",";
            location.hash= newurl;
        }
		
		if($scope.condition && $scope.workType){
            var nowURL = location.href.split('#');
            var newurl = nowURL[1] + "#&&search=" + $scope.condition+','+$scope.workType;
            location.hash= newurl;
        }
		
		if(typeof($scope.condition)=="undefined"  && $scope.workType){
            var nowURL = location.href.split('#');
            var newurl = nowURL[1] + "#&&search=" +','+$scope.workType;
            location.hash= newurl;
        }
		if(){
			
		}
		else{
            var nowURL = location.href.split('#');
            var newurl = nowURL[1];
            location.hash= newurl;
        }

	
    } */
	



    //全选、取消全选的事件
	$("#SelectAll").prop("checked", true);
    $scope.selectAll = function(){
        if ($("#SelectAll").prop("checked")) {
            $(".subcheck").prop("checked", true);
        } else {
            $(".subcheck").prop("checked", false);
        }
    }
    
    //子复选框的事件
    $scope.setSelectAll = function(id){
        //当没有选中某个子复选框时，SelectAll取消选中
        if (!$(".subcheck").checked) {
            $("#SelectAll").prop("checked", false);
        }
        var chsub = $(".subcheck").length; //获取subcheck的个数
        var checkedsub = $("input[class='subcheck']:checked").length; //获取选中的subcheck的个数
		$("#remove").text(chsub-checkedsub);
        if (checkedsub == chsub) {
            $("#SelectAll").prop("checked", true);
        }
    }


	$scope.submitForm = function(){
		var coachids = '';
		$("input[class='subcheck']:checked").each(function(i,e){
			coachids = coachids+","+$(this).parent().next().text();
		});
		coachids = coachids.substring(1) ;
		var limitQuantity = '';
		var isLimit = '1';
		
		if( $scope.setData.limitQuantity=='2'){
			limitQuantity = $scope.limitQuantity;
			isLimit = '2';
			if(!limitQuantity){
				Layer.alert({width:300,height:150,type:"error",title:'请输入金额!'});
				return;
			}
		}
			
		$.AJAX({
			type : "POST",
			url : config.basePath + "groupAccount/add",
			data : {
				limitQuantity:limitQuantity,
				coachids:coachids,
				status:'0',
				isLimit:isLimit,
				workType:$scope.workType
			},
			success : function(data) {
				if(data.code==0){
					Layer.miss({width:250,height:90,title:data.msg,closeMask:true});
					window.location.href = $scope.NURL+'/rc-school/coach';
				}
				$scope.$apply();
			}
		}); 
		

	}


	$scope.goback = function(){
		window.location.href = $scope.NURL+'/rc-school/coach';
	}




}])


	



