app.controller("AddStudent",function($scope){
		
	$scope.traintype = perdritype;

	$.AJAX({
        type : "GET",
        url : config.basePath + "proxy/list",
        success : function(data) {
        	$scope.proxy = data.result.list;
        	$scope.$apply();
        }
    });
    $.AJAX({
        type : "GET",
        url : config.basePath + "scope/list",
        success : function(data) {
        	$scope.scopeArea = data.result.list;
        	$scope.$apply();
        }
    });
    $.AJAX({
        type : "GET",
        url : config.basePath + "class/list",
        success : function(data) {
        	$scope.class = data.result.list;
        	$scope.$apply();
        }
    });
    $.AJAX({
        type : "GET",
        url : config.basePath + "store/list",
        success : function(data) {
        	$scope.store = data.result.list;
        	$scope.$apply();
        }
    });

	var fields = {
		name: {
            validators: {
                notEmpty: {message: '学员姓名不能为空'}
            }
        },
    	phone: {
            validators: {
                notEmpty: {message: '学员手机号码不能为空'},
        		regexp: {
            		regexp: /^1[3|4|5|7|8]\d{9}$/,
            		message: '手机号码格式不正确'
       			}
            }
        },
        idcard: {
            validators: {
                notEmpty: {message: '用户名不能为空'},
                stringLength : {max: 30,message: '长度必须在30位内'},
                regexp: {
					regexp: /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/,
					message: '身份证号码不正确'
				}
            }
        },
        source : {
        	validators: {
        		notEmpty: {message: '请选择招生来源'}
        	}
        },
        classid : {
        	validators: {
        		notEmpty: {message: '请选择班别'}
        	}
        },
        scopeid : {
        	validators: {
        		notEmpty: {message: '请选择所属片区'}
        	}
        },
        proxyid : {
        	validators: {
        		notEmpty: {message: '请选择所属代理商'}
        	}
        },
        storeid : {
        	validators: {
        		notEmpty: {message: '请选择所属门店'}
        	}
        },
        applydate : {
        	validators: {
        		notEmpty: {message: '请选择报名日期'}
        	}
        }
    }

	$('#addStudentForm').bootstrapValidator({
	    fields: null ,
	    submitHandler: function() {
            //获取token
            $('#qiniutoken').val(getQiniuToken());
            //上传返回文件key
            $('#img').val(uploadFile());
	  		$.AJAX({
                type : "POST",
                url : config.basePath + "student/add",
                data : $("#addStudentForm").serialize(),
                success : function(data) {
                    window.location.href = "#/student/list";    
                }
            });
		}
	});

    $scope.uploadHeadImg = function() {
        createFileForm({
            callback : function(data) {   
                $("#headImage").attr("src",getObjectURL($("#expInputFile").get(0).files[0]));
            }
        });    
    }
})