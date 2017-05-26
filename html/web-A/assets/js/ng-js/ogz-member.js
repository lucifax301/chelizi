app.controller("OgzMember",["$scope","$filter",function($scope,$filter) {
	$scope.sexDatas = [{id: "1",value: '男'},{id: "2",value: '女'}];
	$scope.postDatas = [{id: "1",value: '安全员'},{id: "2",value: '考核员'}];
	$scope.recordTypeDatas = [{id: '1',value: '已备案'},{id: '0',value: '未备案'}];
    $scope.pageSize = 10;   
	$scope.uploadbtn = true;
	$scope.baseImgurl = config.imagePath;
	
	$scope.defaultPage = getUrl('page') || 1;  

	if(getUrl('search')) {
		$scope.condition = getUrl('search');
	}

   	window.onhashchange = function() {
        $scope.defaultPage= getUrl('page') || 1; 
    }
	
	//回车事件
	$scope.getkey = function(e) {
		var keycode = window.event?e.keyCode:e.which;
        if(keycode==13) {
			$scope.searchList();
        }
	}
		
	//列表
	$scope.getList = function() {
		var json = {name:$scope.condition,mobile:$scope.condition,pageNo :$scope.defaultPage,pageSize : $scope.pageSize};
		$.AJAX({
			url : config.basePath+"personnel/list",
			type : 'POST',
			data : json,
			success:function(data) {
				$scope.datas = data.result.list;
				$scope.total = data.result.total;
				$scope.$apply();
				new Page({
					parent:$("#copot-page"),
					nowPage:$scope.defaultPage,
					pageSize: $scope.pageSize,
					totalCount:$scope.total
				}); 
				initValidate();
			}
        }); 
	}
	
	$scope.getList();

	//查询
	$scope.searchList = function() {
		if($scope.condition) {
			var nowURL = location.href.split('#');
			var newurl = nowURL[1] + "#&&search=" + $scope.condition;
			location.hash= newurl;
		}else{
			var nowURL = location.href.split('#');
			var newurl = nowURL[1];
			location.hash= newurl;
		}
	}

	//删除
    $scope.delete = function(data,index) {
		Layer.confirm({width:300,height:160,title:"确认删除吗？",header:"删除人员"},function() {
			$.AJAX({
				url:config.basePath+"personnel/delete",
				data:{idcard:data.idcard},
				success:function(data) {
					if(data.code==0) {
						Layer.miss({width:250,height:90,title:data.msg,closeMask:true});
					}
					if(data.code==2) {
						Layer.miss({width:250,height:90,title:data.msg,closeMask:true});
					}
					$scope.getList();
				}
			})
		})
	}
    
	
	//新增保存
	$scope.resetForm = function() {
		$scope.saveData = {};
		$scope.saveData.sex = '1';
		$scope.saveData.post = '1';
		$scope.saveData.recordType = '1';
		resetForm("addMemberForm"); 
	}
	
	$scope.saveEmp = function() {
		if (formValid("addMemberForm")) {
			var photo_url = '';
			if($("#imgfile").val()) {
				getimgKey();
				photo_url = $scope.imgKey;
			}
			$.AJAX({
				type : "POST",
				url : config.basePath + "personnel/add",
				data : $("#addMemberForm").serialize(),
				success : function(data) {
					if(data.code==0) {
						Layer.miss({width:250,height:90,title:data.msg,closeMask:true});
					} else {
						Layer.alert({width:300,height:150,type:"error",title:data.msg});
						return false;
					}
				}
	        }); 
			$("#addModal").modal('hide');
			$(".modal-backdrop").remove();
			$("#addImg").text('选择图片');
			$('#createImg').remove();
			$scope.getList();
		}
	}
	
	//编辑
	$scope.edit = function(index) {
		$scope.editData = $scope.datas[index];
		resetForm("editMemberForm"); 
    }
	
	$scope.updateEmp = function() {
		if (formValid("editMemberForm")) {
	 		var photo_url = $scope.editData.photo_url;
			if($("#imgfile").val()) {
				getimgKey();
				photo_url = $scope.imgKey;
			}
			$.AJAX({
				url : config.basePath + "personnel/update",
				async: false,
				data : $("#editMemberForm").serialize(),
				success:function(data) {
					if(data.code==0) {
						Layer.miss({width:250,height:90,title:data.msg,closeMask:true});
					}
					if(data.code==2) {
						Layer.alert({width:300,height:150,type:"error",title:data.msg});
						return;
					}
					
				}
	        }); 
			$("#editModal").modal('hide');
			$(".modal-backdrop").remove();
			$("#editImg").text('选择图片');
			$("#updateImg").text("更改头像");
			$('#createImg').remove();
			$scope.getList();
			
		 }
	}

	//excel导入
	$scope.uploadfile = function() {
			var html='<div id="createFileHtml" class="hidden"><form method="post" enctype="multipart/form-data" name="form1" id="f1"><input type="file" id="apkFile" name="file"/></form></div>';
			if(!$('#createFileHtml').length) {
				$('body').append(html);
			};
			$('#apkFile').click();
			$('#apkFile').change(function() {
				Layer.confirm({width:300,height:160,title:"确认上传吗？",header:"删除"},function() {
				var form = new FormData(document.getElementById("f1"));
				$.ajax({
					url: config.basePath  + "personnel/uploadExcel",
					type:"post",
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
						}); //分页请求完毕
						$scope.$apply();
					}
				})
				})
			})
		}
		
		//导入db
		$scope.importFile = function(type) {
			$.AJAX({
				url:config.basePath+"personnel/importDB",
				data:{filename:$scope.filename,type:type},
				success:function(data) {
					if(data.code==0) {
						Layer.miss({width:250,height:90,title:data.msg,closeMask:true});
					}
					if(data.code==2) {
						Layer.alert({width:300,height:150,type:"error",title:data.msg});
					}
					$scope.uploadbtn = true;
					$scope.getList();
				}
			});
		}

	   	//上传图片
	  	$scope.uploadImg = function(type) {
		  var html='<div id="createImg" class="hidden"><form method="post" enctype="multipart/form-data" name="form1" id="f2">'+
					'<input type="hidden"  id="token" name="token" >'+
					'<input name="key" value="" id="key">'+
					'<input type="file" id="imgfile" name="file"/></form></div>';
			if(!$('#createImg').length) {
				$('body').append(html);
			};
			$('#imgfile').click();
			$('#imgfile').change(function() {
				if($("#imgfile").val()) {
					var filepath = $("#imgfile").val();
					var postfix = filepath.substring(filepath.lastIndexOf('.'),filepath.length)
					if(postfix !='.jpg' && postfix !='.jpeg' && postfix !='.png' && postfix !='.bmp' ) {
						$('#createImg').remove();
						Layer.alert({width:300,height:150,type:"error",title:'格式不正确！'});
						return;
					}
					var path = $("#imgfile").val();
					var timestamp=new Date().getTime();
					$("#key").val(path.substring(path.lastIndexOf("\\")+1,path.lastIndexOf("."))+'_'+timestamp);
					if(type=='1') {
						$("#addImg").text( path.substring(path.lastIndexOf("\\")+1) );
					}
					if(type=='2') {
						$("#editImg").text( path.substring(path.lastIndexOf("\\")+1) );
						$("#updateImg").text( path.substring(path.lastIndexOf("\\")+1) );
					}
				}
			})			
	  }
	  
  	function getimgKey() {
	   $.AJAX({
			type: "POST",
			url : config.basePath + "qiniuToken",
			async: false,
			success:function(data) {
				$scope.imgToken = data.result;
			}
		});
		$("#token").val($scope.imgToken);
		var form = new FormData(document.getElementById("f2"));
		$.ajax({
			url:"http://up.qiniu.com",
			type:"post",
			data:form,
			//xhrFields:{withCredentials: true},
			//crossDomain: true,
			processData:false,	
			contentType:false,
			async: false,
			success:function(data) {
				$scope.imgKey = data.key;
			}
		})
	}

	var fields =  {
        	name : {
                validators : {
                    notEmpty : {message : '用户名不能为空'},
                	stringLength : {max : 30, message : '长度必须在30位内'}
                }
            },
            fstdrilicdate: {
                validators: {notEmpty: {message : '证件初领时间不能为空'}}
            },
            drilicence: {
                validators: {
                    notEmpty: {message: '驾驶证号不能为空'},
                    regexp: {regexp: /^[0-9a-zA-Z]{12,18}$/,message: '驾驶证号格式不正确'}
                }
            },
            mobile: {
                validators: {
                    notEmpty: {message: '手机号码不能为空'},
					regexp: {regexp: /^1[3|4|5|7|8]\d{9}$/,message: '手机号码格式不正确'}
                }
            },
            idcard: {
                validators: {
                    notEmpty: {message: '身份证号码不能为空'},
						regexp: {
                		regexp: /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/,
                		message: '身份证号码格式不正确'
           			}
                }
            },
            hiredate: {
                validators: {
                    notEmpty: {message: '入职时间不能为空'}
                }
            },
        }

		var initValidate = function() {
			$('#addMemberForm').bootstrapValidator({
		        fields:fields,
		        submitHandler: function() {
		          	$scope.saveEmp();
		        }
			});

			$('#editMemberForm').bootstrapValidator({
	        	fields: fields,
	        	submitHandler: function() {
	          		$scope.updateEmp();
	        	}
			});
		}

}]);



	
			
			
	





