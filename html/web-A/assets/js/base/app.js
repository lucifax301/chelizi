var app = angular.module('app', ['ngRoute','ngAnimate',"ngFilter"]);
	app.controller("myCtrl",function($scope) {
	
	$scope.NURL = config.webPath + '#';

	//获取权限和用户信息
	if (sessionStorage.getItem("menu") == null) {
		$.AJAX({
	        type : "GET",
	        async : "false",
	        url : config.basePath + "privilege/getUserMenu",
	        success : function(data) {
	        	if (data.result.menu == "" || data.result.menu == null) {
	        		Layer.alert({width:300,height:150,type:"error",title: "请管理员配置权限!"});
                    setTimeout(function() {
                    	$scope.clearSession();
                    },5000);
	        	} else {
		        	sessionStorage.setItem("menu",JSON.stringify(data.result.menu));
		        	sessionStorage.setItem("userInfo",JSON.stringify(data.result.userInfo));
		        	sessionStorage.setItem("btn",JSON.stringify(data.result.btn));
		        	sessionStorage.setItem("index",JSON.stringify(data.result.index));
		        	$scope.leftMenuData = data.result.menu;
		        	$scope.userInfo = data.result.userInfo;
		        	$scope.index =  data.result.index;
		        	window.btn = data.result.btn;
		        	$scope.$apply();
	        	}
	        }
	    });
	} else {
		$scope.leftMenuData = JSON.parse(sessionStorage.getItem("menu"));
		$scope.userInfo = JSON.parse(sessionStorage.getItem("userInfo"));
		$scope.index = JSON.parse(sessionStorage.getItem("index"));
		window.btn = JSON.parse(sessionStorage.getItem("btn"));
	}


// 一级菜单就是功能页面时配置方法：
// 先配置一个一级菜单 pid=0 type=1 level=1 url配置上
// 在配置一个二级菜单 pid=一级菜单的id type=1 level=2 不配置url
// 查看列表页 type=2 level=3

	//左侧菜单初始化
  	$scope.menuInit = function() {
	  	$scope.thisurl = window.location.href;
	  	$('#leftMenu li').find('a').each(function(){
	  		$scope.ahref = $(this).attr('href');
			if($scope.thisurl.indexOf($scope.ahref) == 0){
				$('#leftMenu').find('li').removeClass('active');
				$(this).parent('li').addClass('active');
				if($(this).parents('ul').attr('id') != 'leftMenu'){
					$('#leftMenu li').removeClass('active');
					$(this).parents('ul').slideDown();
					$(this).parents('li').addClass('active');
				}else{
					$(this).parent('li').find('ul').slideDown();
				}
			}
	  	})
	  	$('#leftMenu li').each(function(){
			if(!$(this).hasClass('active')){
				if($(this).find('ul').css('display') == 'block'){
					$(this).find('ul').slideUp();
				}
			}
		})
  	}

    //左侧菜单点击执行
	$scope.leftMenuClick = function(){
		$(".modal-backdrop").css("display","none");
		var lock = true;
		$("#leftMenu").find('li').click(function(){
	 		if($(this).parents('ul').attr('id') == 'leftMenu'){
	 			if(lock == false){
	 				lock = true;
	 				return;
	 			}
	 			$("#leftMenu").find('li').removeClass('active');
	 			$(this).addClass('active'); 
	 			if($(this).find('ul').css('display') == 'block' ){
	 				return
	 			}else{
	 				$("#leftMenu").find('ul').each(function(){
		 				if($(this).css('display') == 'block'){
		 					$(this).slideUp();
		 				}
		 			})
	 				$(this).find('ul').slideDown();
	 			}
	 		}else{
	 			$(this).parent('ul').find('li').removeClass('active');
	 			$(this).addClass('active'); 
	 			lock = false;
	 		}
	 	})
	}

	$scope.indexPage = function(index) {
    	if (index == 0) {
			if (getCurrentUrl() == "/") {
				if ($scope.leftMenuData[0] != null && $scope.leftMenuData[0].child != null) {
					window.location.href = "#/" + $scope.leftMenuData[0].child[0].url
				} else {
					window.location.href = "#/" + $scope.leftMenuData[0].url;
				}
			}
		} else {
			if (getCurrentUrl() == "/") {
				window.location.href = "#/index";
			}
		}
    }

	setTimeout(function(){
		$scope.indexPage($scope.index);
	  	$scope.leftMenuClick();
	 	$scope.menuInit();
	 },500)

  	window.onpopstate = function(event) {
    	$scope.menuInit();
	};

	//注销用户
	$scope.logout = function() {
		Layer.confirm({width:300,height:160,title:"确认退出系统？",header:"确认"},
			function(){
				$scope.clearSession();
		});
	}

	//注销用户
	$scope.clearSession = function() {
		$.AJAX({
			type: "GET",
			url : config.basePath + "open/logout",     
			success : function(data) {
				sessionStorage.clear();
				localStorage.clear();
				window.location.href = "web-index.html";
			}
		});
	}

	//处理返回页面时候的遮罩层
	$scope.keydown = function(e) {
        if ((event.keyCode == 8 && event.srcElement.tagName != "INPUT" 
        	&& event.srcElement.type != "text" 
        	&& event.srcElement.type != "textarea")) {        	
      		event.returnValue=false; 
        }
    }

	$scope.resetForm = function() {
		$scope.password = "";
		$scope.password1 = "";
		resetForm("passwordForm");
	}

	$('#passwordForm').bootstrapValidator({
		submitHandler: function() {
			if (formValid("passwordForm")) {
				$.AJAX({
					type : "POST",
					data : {
						password : $scope.password
					},
					url : config.basePath + "user/updatePassword",     
					success : function(data) {
						Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});         
						$("#passwordModal").modal('hide');
					}
				});
			}
		},
        fields: {
       		password: {
                validators: {
                    notEmpty: { message : '用户密码不能为空'},
                    identical: { field : 'password1', message : '用户密码和确认密码不一致'},
                    stringLength : { min : 6, max : 30, message : '长度必须在6-30位内'}
                }
            },
            password1: {
                validators: {
                    notEmpty: { message: '确认密码不能为空'},
                    identical: { field: 'password', message: '用户密码和确认密码不一致'},
                    stringLength : { min : 6, max : 30, message : '长度必须在6-30位内'}
                }
            }
        }
	})
});


app.config(function($routeProvider) {
  $routeProvider
    .when('/index', {
       templateUrl: 'tpls/index.html',
       controller: 'HomeCenter'
    })
    .when('/', {
       templateUrl: 'tpls/blank.html'
    })
	.when('/student/list', {
      templateUrl: 'tpls/student/student-list.html',
      controller: 'StudentList'
    })
    .when('/student/add', {
      templateUrl: 'tpls/student/add-student.html',
      controller: 'AddStudent'
    })
	.when('/ogz/member', {
      templateUrl: 'tpls/ogz/ogz-member.html',
      controller: 'OgzMember'
    })
	.when('/coach/index', {
	  templateUrl: 'tpls/coach/coach-index.html',
	  controller: 'CoachIndex'
	})
	.when('/coach/time', {
	  templateUrl: 'tpls/coach/coach-time.html',
	  controller: 'CoachTime'
	})
	.when('/coach/addCoach', {
	  templateUrl: 'tpls/coach/add-coach.html',
	  controller: 'addCoach'
	})
	.when('/coach/updateCoach', {
	  templateUrl: 'tpls/coach/update-coach.html',
	  controller: 'updateCoach'
	})
	.when('/coach/showStuOfCoach', {
	  templateUrl: 'tpls/coach/show-stuOfCoach.html',
	  controller: 'showStuOfCoach'
	}) 
	.when('/coach/distributionStu', {
	  templateUrl: 'tpls/coach/coach-distributionStu.html',
	  controller: 'distributionStu'
	})
	
	.when('/ogz/sub', {
	  templateUrl: 'tpls/ogz/ogz-sub.html',
	  controller: 'OgzSub'
	})
	.when('/add/sub', {
	  templateUrl: 'tpls/ogz/add-sub.html',
	  controller: 'addSub'
	})
	.when('/update/sub', {
	  templateUrl: 'tpls/ogz/update-sub.html',
	  controller: 'updateSub'
	})
	.when('/ogz/car', {
	  templateUrl: 'tpls/ogz/ogz-car.html',
	  controller: 'OgzCar'
	})
	.when('/ogz/addCar', {
	  templateUrl: 'tpls/ogz/add-car.html',
	  controller: 'addCar'
	})
	.when('/ogz/updateCar', {
	  templateUrl: 'tpls/ogz/update-car.html',
	  controller: 'updateCar'
	})
	.when('/classManage/class', {
	  templateUrl: 'tpls/classManage/classManage-class.html',
	  controller: 'ClassManage'
	})
	.when('/ogz/trainarea', {
	templateUrl: 'tpls/ogz/ogz-train-area.html',
	controller: 'Ogztrainarea'
	})
	.when('/add/trainarea', {
	templateUrl: 'tpls/ogz/add-trainarea.html',
	controller: 'addTrainarea'
	})
	.when('/update/trainarea', {
	templateUrl: 'tpls/ogz/update-trainarea.html',
	controller: 'updateTrainarea'
	})
	.when('/user/list', {
	templateUrl: 'tpls/user/user-list.html',
	controller: 'UserIndex'
	}) 
	.when('/register/list', {
	templateUrl: 'tpls/user/register-list.html',
	controller: 'RegisterIndex'
	})
	.when('/contact', {
	templateUrl: 'tpls/page-contact.html',
	controller: 'contactController'
	})
    .when('/ogz/complain', {
	  templateUrl: 'tpls/ogz/ogz-complain.html',
	  controller: 'ComplainList'
    })
    .when('/ogz/return', {
	  templateUrl: 'tpls/ogz/ogz-return.html',
	  controller: 'ReturnList'
    })
	.when('/ogz/marketing', {
	templateUrl: 'tpls/ogz/ogz-marketing.html',
	controller: 'Marketing'
	})
	.when('/ogz/visit', {
	templateUrl: 'tpls/ogz/ogz-visit.html',
	controller: 'Visit'
	})
	.when('/exam/theory-exam', {
	templateUrl: 'tpls/exam/theory-exam.html',
	controller: 'TheoryExam'
	})
	.when('/exam/long-exam', {
	templateUrl: 'tpls/exam/long-exam.html',
	controller: 'LongExam'
	})
	.when('/user/permission', {
	templateUrl: 'tpls/user/user-permission.html',
	controller: 'UserPermission'
	})
	.when('/user/permission-add', {
	templateUrl: 'tpls/user/user-permission-add.html',
	controller: 'RoleAdd'
	})
	.when('/user/permission-edit', {
	templateUrl: 'tpls/user/user-permission-edit.html',
	controller: 'RoleEdit'
	})
	.when('/user/permission-users', {
	templateUrl: 'tpls/user/user-permission-users.html',
	controller: 'UserPermissionUsers'
	})
	.when('/exam/book-exam', {
	templateUrl: 'tpls/exam/book-exam.html',
	controller: 'BookExam'
	})
	.when('/exam/book-exam-op', {
	templateUrl: 'tpls/exam/book-exam-op.html',
	controller: 'BookExamOp'
	})
	.when('/exam/book-exam-detail', {
	templateUrl: 'tpls/exam/book-exam-detail.html',
	controller: 'BookExamDetail'
	})	
	.when('/exam/exam-result', {
	templateUrl: 'tpls/exam/exam-result.html',
	controller: 'ExamResult'
	})
	.when('/exam/exam-result-op', {
	templateUrl: 'tpls/exam/exam-result-op.html',
	controller: 'ExamResultOp'
	})
	.when('/exam/exam-result-detail', {
	templateUrl: 'tpls/exam/exam-result-detail.html',
	controller: 'ExamResultDetail'
	})
	.when('/rc-school/account', {
      templateUrl: 'tpls/rc-school/rc-school-account.html',
      controller: 'RcSchoolAccount'
    })
    .when('/rc-school/account-add', {
      templateUrl: 'tpls/rc-school/rc-account-add.html',
      controller: 'RcAccountAdd'
    })
    .when('/rc-school/coach', {
      templateUrl: 'tpls/rc-school/rc-coach-account.html',
      controller: 'RcCoachAccount'
    })
    .when('/rc-school/consumption', {
      templateUrl: 'tpls/rc-school/rc-consumption.html',
      controller: 'RcConsumption'
    })
    .when('/rc-school/recharge', {
      templateUrl: 'tpls/rc-school/rc-recharge.html',
      controller: 'RcRecharge'
    })
    .when('/scope/list', {
      templateUrl: 'tpls/base/scope-list.html',
      controller: 'ScopeAreaList'
  	})
  	.when('/proxy/list',{
      templateUrl: 'tpls/base/proxy-list.html',
      controller: 'ProxyDealerList'
	});
});

