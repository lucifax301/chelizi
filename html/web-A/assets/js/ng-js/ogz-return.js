
app.controller("ReturnList",["$scope","$filter","$http",function($scope,$filter,$http){
		
		//搜索人员列表
		var vm = $scope.vm = {};	
		 vm.statusOpt = {  
			allowClear : true  
		} 
		$.AJAX({
			url:config.basePath+"user/list",
			async:false,
			success:function(data){
				$scope.gzUsers = data.result.list;
			}
		});
		
		$scope.statsuDatas = [{id:0,value:'未处理'},{id:1,value:'处理中'},{id:2,value:'已处理'},{id:3,value:'拒绝'}];
		$scope.baseInfoDiv = true;$scope.visitRecordsDiv = false;   
		$scope.lili = function(){
			$scope.baseInfoDiv = true;$scope.visitRecordsDiv = false;   
			$("#baseInfo").css("color",'#333'); 
			$("#visitRecords").css("color",'#999999');	
		}
		$scope.school = function(){
			$scope.baseInfoDiv = false;$scope.visitRecordsDiv = true; 
			$("#visitRecords").css("color",'#333');
			$("#baseInfo").css("color",'#999999'); 	
			
		}
		
		$scope.defaultPage= getUrl('page') || 1;  //默认请求页	
        $scope.pageSize=10;    //每页显示-显示条数

		 /*hash值改变的时候加载数据列表*/
        window.onhashchange=function(){
            // win .showLoading();
            $scope.defaultPage= getUrl('page') || 1;  //默认请求页	
        }
		
		var href = location.href;
		var complainid = href.substring(href.indexOf("=")+1, href.length );
		//基本信息
		$scope.getBaseDataList=function(){
			$.AJAX({
				url:config.basePath+"complain/getComplainById",
				data:{complainid:complainid},
				success:function(data){
					$scope.datas = data.result;
					$scope.$apply();

				}
			});
		}
		$scope.getDataList=function(){
			//回访记录
			$.AJAX({
				url:config.basePath+"complain/returnList",
				data:{complainid:complainid,pageNo :$scope.defaultPage,pageSize : 10},
				success:function(data){
					$scope.visitDatas = data.result.list;
					console.log($scope.visitDatas);
					$scope.total = data.result.total;
					new Page({
						parent:$("#copot-page"),
						nowPage:$scope.defaultPage,
						pageSize:10,
						totalCount:$scope.total,
						search:$scope.condition
					}); //分页请求完毕
					$scope.$apply();
				}
			});
		}

	$scope.getDataList();
	$scope.getBaseDataList();


	/*$scope.manageUser=function(data){
		$("#manageUser").modal("toggle");
	} */

	//投诉编辑弹框
	$scope.complainEdit=function(){
		$("#editComplain").modal("toggle");
		$scope.editData = $scope.datas;
	}
	 $scope.submitEditMsg = function() {
        $.AJAX({
            url : config.basePath + "complain/updateComplain",
            type : "POST",
            data : {
                complainid : $scope.editData.complainid,
                complainuser : $scope.editData.complainuser,
                complainmobile : $scope.editData.complainmobile,
                summary : $scope.editData.summary,
                content :  $scope.editData.content,
                remark : $scope.editData.remark
            },
            success : function(data){
                Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                $('#editComplain').modal('hide');
                $scope.editData = {};
                $scope.getDataList();
            }
        });
    }
	
	 //投诉编辑提交
    $scope.submitEditForm = function(isValid) {
        $scope.submitEditMsg();
    };
	
	// ‘处理’弹框
	$scope.manageUser=function(){
		$("#manageUser").modal("toggle");
		$scope.namaData = $scope.datas;
		$scope.manaform.$setPristine();
	}
	
	//处理投诉
    $scope.submitManaMsg = function() {
        $.AJAX({
            url : config.basePath + "complain/updateComplain",
            type : "POST",
            data : {
                complainid : complainid,
                manageuser : $scope.namaData.manageuser,
                managetype : $scope.namaData.managetype,
                status : $scope.namaData.status,
                manageremark : $scope.namaData.manageremark
            },
            success : function(data){
                Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                $('#manageUser').modal('hide');
                $scope.namaData = {};
                $scope.getDataList();
            }
        });
    }
	
	//处理投诉提交
    $scope.submitManaForm = function(isValid) {
        $scope.submitManaMsg();
    };
	
	/*$scope.userList = function(){
		$.AJAX({
			url:config.basePath+"user/list",
			async:false,
			data:{realname:$scope.namaData.manageuser},
			success:function(data){
				$scope.userDatas = data.result.list;
			}
		});
	}

	$scope.clecked = function(index){
		$scope.realname = $scope.userDatas[index].realname;
	}
	$scope.sureUser = function(){
		$scope.visitPersonnel = $scope.realname; //回访
		try{
			$scope.namaData.manageuser = $scope.realname;//新增潜在用户
		}
		catch(e){ }

		$("#userListDiv").modal('hide');
		$(".modal-backdrop").remove();
	} */


}]);




