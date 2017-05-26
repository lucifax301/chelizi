app.controller("Marketing",["$scope","$filter","$http",function($scope,$filter,$http){
		//人员列表
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
	    
   
		$scope.uploadbtn = true;//上传按钮
		$scope.progressMsg = false;//进度非空信息
		
		$scope.defaultPage= getUrl('page') || 1;  //默认请求页	
		if(getUrl('search')){
			$scope.condition = getUrl('search');//查询当前url是否存在参数search，如有赋值搜索框
		}
        $scope.pageSize=10;    //每页显示-显示条数

		 /*hash值改变的时候加载数据列表*/
        window.onhashchange=function(){
            $scope.defaultPage= getUrl('page') || 1;  //默认请求页	
        }
		
		//回车事件
		$scope.getkey = function(e){
			var keycode = window.event?e.keyCode:e.which;
            if(keycode==13){
				$scope.searchList();
            }
		}
		
			//潜在客户列表
			$scope.getList = function(){
				var json = {phone:$scope.condition,name:$scope.condition,pageNo :$scope.defaultPage,pageSize : 10};
				 $.AJAX({
                    url:config.basePath+"marketing/list",
                    data:json,
                    success:function(data){
						$scope.datas = data.result.list;
						$scope.total = data.result.total;
						$scope.$apply();
						new Page({
							parent:$("#copot-page"),
							nowPage:$scope.defaultPage,
							pageSize:10,
							totalCount:$scope.total,
							search:$scope.condition
						}); //分页请求完毕                     
                    }
                });  
			}
			
			 $scope.getList();
			 //查询
			 $scope.searchList = function(){
				if($scope.condition){
					var nowURL = location.href.split('#');
					var newurl = nowURL[1] + "#&&search=" + $scope.condition;
					location.hash= newurl;
				}else{
					var nowURL = location.href.split('#');
					var newurl = nowURL[1];
					location.hash= newurl;
				}
			}
			
			var progress = '';var source = '';$scope.flag = false;
			$scope.progress = function($event){
				$($event.target).addClass('btn-radio').siblings('button').removeClass("btn-radio");
				progress = $($event.target).val();
				$scope.flag = true;$scope.progressMsg = false;
			}
			$scope.source = function($event){
				$($event.target).addClass('btn-radio').siblings('button').removeClass("btn-radio");
				source = $($event.target).val();
				
			}
			//新增
			$scope.sourceDatas = [{id:'1',value:'门店'},{id:'2',value:'微信'},{id:'3',value:'网页'},{id:'4',value:'教练二维码'},{id:'5',value:'大客户推荐'},{id:'6',value:'其他'}];
			$scope.progressDatas = [{id:'1',value:'未跟踪'},{id:'2',value:'跟踪中'},{id:'3',value:'跟踪结束'},{id:'4',value:'已转化'}];
			//新增弹框
			$scope.addPotential = function(){
				$scope.saveData={};
				$scope.myForm.$setPristine();
				$scope.saveData.phone = ''; 
				$scope.saveData.traintype = 'C1';
				$scope.progressMsg = false;$scope.flag = false;
				//清理痕迹
				$(".addSource").each(function(i,e){
					$(e).removeClass('btn-radio');	
				})
				$(".addProgress").each(function(i,e){
					$(e).removeClass('btn-radio');	
				})
				
				
			}
			$scope.savestus = function(){
				if(!$scope.flag){
					$scope.progressMsg = true;
					return;
				}
				 var json = {
					name:$scope.saveData.name,
					phone:$scope.saveData.phone,
					address:$scope.saveData.address,
					traintype:$scope.saveData.traintype,
					source:source,
					remarks:$scope.saveData.remarks,
					trackePersonnel:$scope.saveData.trackePersonnel,
					progress:progress
				}
				 $.AJAX({
                    url:config.basePath+"marketing/add",
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
				$("#addmodal").modal('hide');
				$(".modal-backdrop").remove();
				$scope.getList();
				//$scope.$apply();
			}
			
		  //保存
		  $scope.submitAddForm = function(isValid) {
			  $scope.savestus();
		  };
			
			//编辑弹框
			$scope.edit = function(index){
				$scope.getList();
				$scope.editData = $scope.datas[index];
				
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
			
			//编辑保存
			$scope.update = function($event){
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
		  
		  //删除
		$scope.del=function(data,index){
			 var idcard = data.idcard;
			 Layer.confirm({width:300,height:160,title:"确认删除吗？",header:"删除"},function(){
					$.AJAX({
						method: "POST",
						url:config.basePath+"marketing/delete",
						data:{idcard:idcard},
						success:function(data){
							if(data.code==0){
								Layer.miss({width:250,height:90,title:data.msg,closeMask:true});
							}
							if(data.code==2){
								Layer.alert({width:300,height:150,type:"error",title:data.msg});
							}
							$scope.getList();
							$scope.$apply();
						}
					}); 
			 })
		}
			
		  //新增回访
		/*  $scope.realname = '';
		  $scope.userList = function(){
			  var realnameSearch = '';
			  if($scope.saveData.trackePersonnel){
				  realnameSearch = $scope.saveData.trackePersonnel;
			  }
			   $.AJAX({
					url:config.basePath+"user/list",
					data:{realname: realnameSearch},
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
			  $scope.visitPersonnel = $scope.realname; //回访
			  try{
				  $scope.saveData.trackePersonnel = $scope.realname;//新增潜在用户
			  }
			  catch(e){ }
			   try{
				 $scope.editData.trackePersonnel = $scope.realname;//编辑潜在用户
			  }
			  catch(e){ }
			  $("#userListDiv").modal('hide');
			  $(".modal-backdrop").remove();
		  } */
		  
		  $scope.addVisit = function(item){
			$scope.visitForm.$setPristine();
			$scope.content = ''; 
			$scope.visitPersonnel = '';
			  
			  $scope.visitData = item;
			  $scope.visitName = item.name;
			  $scope.visitPhone = item.phone;
			  //
			  $scope.visitNumber = item.number
		  }	
		  $scope.saveVisit = function(){
		   var json = {
				marketid:$scope.visitData.marketid,
				content:$scope.content,
				visitPersonnel:$scope.visitPersonnel,
				operatorPersonnel:$scope.userInfo.realname //当前操作人员
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
			$.AJAX({
				url:config.basePath+"marketing/update",
				async:false,
				data:{marketid:$scope.visitData.marketid,number:$scope.visitNumber + 1},
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

			//查看
			$scope.showData = {};
			$scope.showInfo = function(data){
				$scope.showData = data;
			}
			$scope.show = true;
			$scope.close = function(){
				$scope.show = false;
			}

			//定义下拉单
			$scope.traintypeDatas = [{id: 'A1',value: 'A1'},{id: 'A2',value: 'A2'},{id: 'A3',value: 'A3'},
									  {id: 'C1',value: 'C1'},{id: 'C2',value: 'C2'},{id: 'C3',value: 'C3'},{id: 'C4',value: 'C4'},{id: 'C5',value: 'C5'},
									  {id: 'B1',value: 'B1'},{id: 'B2',value: 'B2'},{id: 'D',value: 'D'},{id: 'E',value: 'E'},{id: 'F',value: 'F'},{id: 'M',value: 'M'},{id: 'N',value: 'N'},{id: 'P',value: 'P'}

									 ];
			


	  //excel导入
	  $scope.uploadfile = function(){
			var html='<div id="createFileHtml" class="hidden"><form method="post" enctype="multipart/form-data" name="form1" id="f1"><input type="file" id="apkFile" name="file"/></form></div>';
			if(!$('#createFileHtml').length){
				$('body').append(html);
			};
			$('#apkFile').click();
			$('#apkFile').change(function(){
				Layer.confirm({width:300,height:160,title:"确认上传吗？",header:"资料上传"},function(){
					var form = new FormData(document.getElementById("f1"));
						$.ajax({
							url:config.basePath+"marketing/uploadExcel",
							type:"post",
							data:form,
							xhrFields:{withCredentials: true},
							crossDomain: true,
							processData:false,	
							contentType:false,
							async: false,
							success:function(data){
								if(data.code==200){
									Layer.alert({width:300,height:150,type:"error",title:data.msg});
									return;
								}
								if(data.code==201){
									Layer.alert({width:300,height:150,type:"error",title:data.msg});
									return;
								}
								$scope.filename = data.result.filename;
								$scope.datas = data.result.data;
								$scope.uploadbtn = false;
								new Page({
									parent:$("#copot-page"),
									nowPage:$scope.defaultPage,
									pageSize:data.result.data.length,
									totalCount:data.result.data.length
								}); //分页请求完毕
							}
						})
						$('#createFileHtml').remove();
						$scope.$apply();
				})
				
				$('.confirmRight').click(function(){
					$('#createFileHtml').remove();

				})
			})

		}
		
		//导入db
		$scope.importFile = function(type){
		$.AJAX({
			url:config.basePath+"marketing/importDB",
			data:{filename:$scope.filename,type:type},
			success:function(data){
				if(data.code==0){
					Layer.miss({width:250,height:90,title:data.msg,closeMask:true});
				}
				if(data.code==2){
					Layer.alert({width:300,height:150,type:"error",title:data.msg});
				}

				$scope.uploadbtn = true;
				$scope.getList();
				$('#createFileHtml').remove();
				$scope.$apply();

			}
		});
		}
		
		//下载
		$scope.download = function(){
			var url = config.basePath+"marketing/download";
			window.open(url);	
		}
		


}]);




