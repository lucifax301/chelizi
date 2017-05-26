
app.controller("Visit",["$scope","$filter","$http",function($scope,$filter,$http){
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
		
		$scope.baseInfoDiv = true;$scope.visitRecordsDiv = false;$scope.addVisitBut = false;
		$scope.baseInfo = function(){
			$scope.baseInfoDiv = true;$scope.visitRecordsDiv = false;$scope.addVisitBut = false;
			$("#baseInfo").css("color",'#333'); 
			$("#visitRecords").css("color",'#999999');	
		}
		$scope.visitRecords = function(){
			$scope.baseInfoDiv = false;$scope.visitRecordsDiv = true;$scope.addVisitBut = true;
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
		var marketid = href.substring(href.indexOf("=")+1, href.length );
		
		$scope.sourceDatas = [{id:'1',value:'门店'},{id:'2',value:'微信'},{id:'3',value:'网页'},{id:'4',value:'教练二维码'},{id:'5',value:'大客户推荐'},{id:'6',value:'其他'}];
		$scope.progressDatas = [{id:'1',value:'未跟踪'},{id:'2',value:'跟踪中'},{id:'3',value:'跟踪结束'},{id:'4',value:'已转化'}];
		var progress = '';var source = '';
		$scope.progress = function($event){
			$($event.target).addClass('btn-radio').siblings('button').removeClass("btn-radio");
			progress = $($event.target).val();
		}
		$scope.source = function($event){
			$($event.target).addClass('btn-radio').siblings('button').removeClass("btn-radio");
			source = $($event.target).val();
		}
		
		
		//基本信息
		$.AJAX({
			url:config.basePath+"marketing/getMarketingById",
			data:{marketid:marketid},
			success:function(data){
				$scope.datas = data.result;
				$scope.ctime = $scope.datas.ctime;$scope.mtime = $scope.datas.mtime;
				
				var ctime = $scope.ctime;
				var d = new Date(ctime);    //根据时间戳生成的时间对象
				$scope.ctime = (d.getFullYear()) + "-" + (d.getMonth() + 1) + "-" +(d.getDate()) + " " + (d.getHours()) + ":" + (d.getMinutes()) + ":" + (d.getSeconds());
				var mtime = $scope.mtime;
				d = new Date(mtime);    //根据时间戳生成的时间对象
				$scope.mtime = (d.getFullYear()) + "-" + (d.getMonth() + 1) + "-" +(d.getDate()) + " " + (d.getHours()) + ":" + (d.getMinutes()) + ":" + (d.getSeconds());
				$scope.$apply();            
			}
        });
		//回访记录 $scope.getList();
		$scope.getList = function(){
			$.AJAX({
				url:config.basePath+"marketing/getVisitList",
				data:{marketid:marketid,pageNo :$scope.defaultPage,pageSize : 10},
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
		$scope.getList();
		
		//编辑客户基本信息
		$scope.edit = function(){
			$.AJAX({
				url:config.basePath+"marketing/getMarketingById",
				data:{marketid:marketid},
				async:false,
				success:function(data){
					$scope.editData = data.result;
					//单选按钮选中
					$(".editSource").each(function(i,e){
						$(e).removeClass('btn-radio');
						if($(e).val()== $scope.editData.source  ){
							$(e).addClass('btn-radio');
						}
					})
					$(".editProgress").each(function(i,e){
						$(e).removeClass('btn-radio');
						if($(e).val()== $scope.editData.progress ){
							$(e).addClass('btn-radio');
						}
					})
				}
			}); 
			
		}
		//编辑保存
		$scope.update = function(){
			//没有更改的情况下，保留已有值
			if(!source){
				source = $scope.editData.source;
			}
			if(!progress){
				progress = $scope.editData.progress;
			}
			 var json = {
				 marketid:$scope.editData.marketid,
				name:$scope.editData.name,
				address:$scope.editData.address,
				traintype:$scope.editData.traintype,
				source:source,
				remarks:$scope.editData.remarks,
				trackePersonnel:$scope.editData.trackePersonnel,
				progress:progress
			}
			$.AJAX({
				url:config.basePath+"marketing/update",
				data:json,
				async:false,
				success:function(data){
					if(data.code==0){
						Layer.miss({width:250,height:90,title:data.msg,closeMask:true});
					}
					if(data.code==2){
						Layer.alert({width:300,height:150,type:"error",title:data.msg});
					}
				}
			}); 
			$("#mymodal").modal('hide');
			$(".modal-backdrop").remove();
			$scope.getList();
			$scope.$apply();
		}

		//编辑  timeEditMsg
	  $scope.submitEditForm = function(isValid) {
		  $scope.update();
	  };
	  
	  //新增回访
	  $scope.addVisit = function(){
		  $.AJAX({
				url:config.basePath+"marketing/getMarketingById",
				data:{marketid:marketid},
				async:false,
				success:function(data){
					$scope.editData = data.result;
					$scope.visitName = $scope.editData.name;
					$scope.visitPhone = $scope.editData.phone;
					$scope.visitNumber = $scope.editData.number
				}
			});
			$scope.visitForm.$setPristine();
			$scope.content = ''; 
			$scope.visitPersonnel = '';
		  
	  }
	  //搜索人员
	/*   $scope.realname = '';
	  $scope.userList = function(){
		   $.AJAX({
				url:config.basePath+"user/list",
				async:false,
				success:function(data){
					$scope.userDatas = data.result.list;
				}
			});
	  }
	  
	  $scope.clecked = function(index){
		  $scope.realname = $scope.userDatas[index].realname;
	  }
	  $scope.sureUser = function(){
		  $scope.visitPersonnel = $scope.realname; //当前用户
		  $scope.visitPersonnel = $scope.realname;//新增潜在用户
		 
		  $("#userListDiv").modal('hide');
		  $(".modal-backdrop").remove();
	  }  */

		  $scope.saveVisit = function(){
		   var json = {
				marketid:marketid,
				content:$scope.content,
				visitPersonnel:$scope.visitPersonnel,
				operatorPersonnel:$scope.userInfo.realname
			}
			 $.AJAX({
				url:config.basePath+"marketing/addVisit",
				async:false,
				data:json,
				success:function(data){
					if(data.code==0){
						Layer.miss({width:250,height:90,title:data.msg,closeMask:true});
					}
					if(data.code==2){
						Layer.alert({width:300,height:150,type:"error",title:data.msg});
					}
					if(data.code==3){
						Layer.alert({width:300,height:150,type:"error",title:data.msg});
					}
				}
			}); 
			//更新回访次数
			$.AJAX({
				url:config.basePath+"marketing/update",
				async:false,
				data:{marketid:marketid,number:$scope.visitNumber + 1},
				success:function(data){
					if(data.code==2){
						Layer.alert({width:300,height:150,type:"error",title:data.msg});
					}
				}
			}); 
			$("#addVisit").modal('hide');
			$(".modal-backdrop").remove();
			$scope.getList();
			$scope.$apply();
		  }
		   $scope.submitAddVisitForm = function(isValid) {
			  $scope.saveVisit();
		  };
	  
	  
		
	//定义下拉单
	$scope.traintypeDatas = [{id: 'A1',value: 'A1'},{id: 'A2',value: 'A2'},{id: 'A3',value: 'A3'},
							  {id: 'C1',value: 'C1'},{id: 'C2',value: 'C2'},{id: 'C3',value: 'C3'},{id: 'C4',value: 'C4'},{id: 'C5',value: 'C5'},
							  {id: 'B1',value: 'B1'},{id: 'B2',value: 'B2'},{id: 'D',value: 'D'},{id: 'E',value: 'E'},{id: 'F',value: 'F'},{id: 'M',value: 'M'},{id: 'N',value: 'N'},{id: 'P',value: 'P'}

							 ];
							 

}]);




