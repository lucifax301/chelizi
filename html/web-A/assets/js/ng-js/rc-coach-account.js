
app.controller("RcCoachAccount",["$scope","$filter",function($scope,$filter){
	
	$scope.defaultPage= getUrl('page') || 1;  //默认请求页
    $scope.pageSize=10;    //每页显示-显示条数
    /*hash值改变的时候加载数据列表*/
    window.onhashchange=function(){
        $scope.defaultPage= getUrl('page') || 1;  //默认请求页
        $scope.getDataList();
    }

 
	$scope.getDataList = function(page) {
		if(page) {
			var nowpage = page;
		} else {
			var nowpage = 1;
		}
		$.AJAX({
			type : "POST",
			url : config.basePath + "groupAccount/list",
			data : {
				workType : $scope.workType,
                condition : $scope.condition,
				status:$scope.status,
				pageNo : nowpage,
				pageSize : $scope.pageSize,
				
			},
			success : function(data) {
				$scope.datas = data.result.list;
				console.log($scope.datass);
				$scope.total = data.result.total;
				new Page({
					parent : $("#copot-page"),
					nowPage : nowpage,
					pageSize : $scope.pageSize,
					totalCount : $scope.total,
					type:2,
					callback:$scope.getDataList
				}); 
				$scope.$apply();
				
			}
		});  
	}
			
	$scope.getDataList();
   
    $scope.authDel=function(id){
        Layer.confirm({width:300,height:160,title:"确认取消该教练授权？",header:"取消授权"},function(){
            $.AJAX({
                url:config.basePath+"xxx/xxxx",
                data: {coachId:id},
                success:function(data){
                    Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                    $scope.getDataList();
                }
            });
        });
    }

    /**************修改限额***********/
    //弹窗
	$scope.setLimitLoad = function(item){
		$scope.limitQuantity = '';
		$scope.editCoachid = item.coachid;
	}
    //提交
    $scope.setLimit = function(){
		if($scope.isLimit=='2'){
			if(!$scope.limitQuantity){
				Layer.alert({width:300,height:150,type:"error",title:'请输入金额!'});
			}
		}
		$.AJAX({
			type : "POST",
			url : config.basePath + "groupAccount/update",
			data : {
				coachid:$scope.editCoachid,
				isLimit:$scope.isLimit,
				limitQuantity:$scope.limitQuantity
			},
			success : function(data) {
				Layer.miss({width:250,height:90,title:'操作成功',closeMask:true});
				$("#editmodal").modal('hide');
				$(".modal-backdrop").remove();
				$scope.getDataList();
			}
		}); 

    }

    //弹窗隐藏时更新数据


    /**************取消授权***********/
    $scope.authDel=function(coachid){
        Layer.confirm({width:330,height:160,title:"确认取消此教练交易账号资格？",header:"取消授权"},function(){
           $.AJAX({
				type : "POST",
				url : config.basePath + "groupAccount/update",
				data : {
					coachid:coachid,
					auth: '2'
				},
				success : function(data) {
					Layer.miss({width:250,height:90,title:'操作成功',closeMask:true});
					$("#editmodal").modal('hide');
					$(".modal-backdrop").remove();
					$scope.getDataList();
				}
			}); 
        });
    }

}])





