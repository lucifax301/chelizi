app.controller("UserIndex",["$scope","$filter",function($scope,$filter) {		
	$scope.defaultPage = getUrl('page') || 1;  
    $scope.pageSize = 10; 
	$scope.condition = null;
    $scope.uploadbtn = true;
    $scope.role = null;

    //翻页获取数据
	window.onhashchange = function() {
        $scope.defaultPage = getUrl('page') || 1; 
    }
	
	//回车事件
	$scope.getkey = function(e) {
		var keycode = window.event?e.keyCode:e.which;
        if(keycode == 13) {
			$scope.searchList();
        }
	}
	
	if(getUrl('search')) {
		$scope.condition = getUrl('search');
	}
	
	//条件查询
	$scope.searchList = function() {
		if($scope.condition) {
			var nowURL = location.href.split('#');
			var newurl = nowURL[1] + "#&&search=" + $scope.condition;
			location.hash = newurl;
		} else {
			var nowURL = location.href.split('#');
			var newurl = nowURL[1];
			location.hash= newurl;
		}
	}
	
	//获取数据列表
	$scope.getDataList = function() {
		$.AJAX({
			type : "POST",
			url : config.basePath + "user/list",
			data : {
				pageNo : $scope.defaultPage,
            	pageSize : $scope.pageSize,
				condition : $scope.condition
			},
			success : function(data) {
				$scope.datas = data.result.list;
				$scope.total = data.result.total;
				$scope.$apply();
				new Page({
					parent : $("#copot-page"),
					nowPage : $scope.defaultPage,
					pageSize : $scope.pageSize,
					totalCount : $scope.total
				}); 
				initValidate();
			}
		});  
	}
		
	$scope.getDataList();

	//增加用户
	$scope.addUser = function() {
		if (formValid("addUserForm")) {
			$.AJAX({
				type : "POST",
				url : config.basePath + "user/add",
				data : $("#addUserForm").serialize(),
				success : function(data) {
					Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});         
					$scope.getDataList();
					$("#addModal").modal('hide');
				}
			});  
		}
	}
	
	//编辑用户
	$scope.editUser = function() {
		if (formValid("editUserForm")) {
			$.AJAX({
				type : "POST",
				url : config.basePath + "user/update",
				data : $("#editUserForm").serialize(),
				success : function(data) {
					Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});         
					$scope.getDataList();
					$("#editModal").modal('hide');
				}
			});  
		}
	}

	//编辑用户赋值
	$scope.showEdit = function(item) {
		$scope.editData = angular.copy(item);
		resetForm("editUserForm"); 
	}
	
	//编辑用户状态
	$scope.status = function(item) {
		if (item.issuper == 1) {
			Layer.alert({width:350,height:150,type:"error",title:"根账号不能停用!"});
			return;
		}
		var title = item.status == 0 ? "停用" : "启用";
		var status = item.status == 0 ? "1" : "0";
		Layer.confirm({width:300,height:160,title:"确认" + title + "该用户?",header:"确认"},function() {
			$.AJAX({
				url : config.basePath + "user/status",
				data : {
					id : item.id,
					status : status
				},
				success : function(data) {
					Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});         
					$scope.defaultPage = 1;             
					$scope.getDataList();
				}
			});
		});
	}

	var fields = {
    	username: {
            validators: {
                notEmpty: {message: '登录手机号码不能为空'},
        		regexp: {
            		regexp: /^1[3|4|5|7|8]\d{9}$/,
            		message: '手机号码格式不正确'
       			}
            }
        },
        realname: {
            validators: {
                notEmpty: {message: '用户名不能为空'},
                stringLength : {max: 30,message: '长度必须在30位内'}
            }
        },
        password: {
            validators: {
                notEmpty: {message: '用户密码不能为空'},
                identical: {field: 'password1',message: '用户密码和确认密码不一致'},
                stringLength : {min:6,max: 20,message: '长度必须在6-20位之间'}
            }
        },
        password1: {
            validators: {
                notEmpty: { message: '确认密码不能为空'},
                identical: { field: 'password', message: '用户密码和确认密码不一致'},
                stringLength : {min:6,max: 20,message: '长度必须在6-20位之间'}
        	}
    	}
    }

    var initValidate = function() {
    	$('#addUserForm').bootstrapValidator({
	        fields: fields ,
	        submitHandler: function() {
	      		$scope.addUser();
	    	}
		});
		$('#editUserForm').bootstrapValidator({
	        fields: fields,
	        submitHandler: function() {
	      		$scope.editUser();
	    	},
		});
    }

	//重置表单
	$scope.resetForm = function() {
		$scope.addData = {};
		resetForm("addUserForm"); 
	}

	//excel导入
  	$scope.uploadfile = function() {
		var html='<div id="createFileHtml" class="hidden"><form method="post" enctype="multipart/form-data" name="form1" id="f1"><input type="file" id="apkFile" name="file"/></form></div>';
		if(!$('#createFileHtml').length) {
			$('body').append(html);
		};
		$('#apkFile').click();
		$('#apkFile').change(function() {
			Layer.confirm({width:300,height:160,title:"确认上传吗?",header:"确认"},function() {
				var form = new FormData(document.getElementById("f1"));
					$.ajax({
						url:config.basePath + "user/upload",
						type:"POST",
						data:form,
						xhrFields:{withCredentials: true},
						crossDomain: true,
						processData:false,	
						contentType:false,
						async: false,
						success:function(data) {
							$scope.filename = data.result.filename;
							$scope.datas = data.result.data;
							$scope.uploadbtn = false;
							new Page({
								parent:$("#copot-page"),
								nowPage:$scope.defaultPage,
								pageSize:data.result.data.length,
								totalCount:data.result.data.length
							});
							$scope.$apply();									
						}
					})
			})
		})
	}

	//确认导入数据
	$scope.confirmImport = function() {
		Layer.confirm({width:300,height:160,title:"确认导入列表中的数据?",header:"导入确认"},function() {
			$.AJAX({
				url : config.basePath + "user/import?type=1",
				data : {filename:$scope.filename},
				success : function(data) {
					Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});     
					$scope.defaultPage = 1;      
					$scope.uploadbtn = true;            
					$scope.getDataList();
				}
			});
		});
	}

	//取消导入数据
	$scope.cancelImport = function() {
		Layer.confirm({width:300,height:160,title:"确认导入列表中的数据？",header:"导入确认"},function() {
			$.AJAX({
				url : config.basePath + "user/import?type=2",
				data : {filename:$scope.filename},
				success : function(data) {
					Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});     
					$scope.defaultPage = 1;   
					$scope.uploadbtn = true;               
					$scope.getDataList();
				}
			});
		});
	}

	//下载模板
    $scope.download = function() {
        var url = config.basePath + "user/download";
        window.open(url);
    }

}])