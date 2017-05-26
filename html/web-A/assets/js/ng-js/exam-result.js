﻿app.controller("ExamResult",["$scope","$filter",function($scope,$filter){		
        $scope.pageSize = 10; 
		$scope.condition = null;
        $scope.flag = 1;
        $scope.subjectName = "";
 		$scope.result = "";
		
		if (!$filter('isAccessShow')('101121')) {
			window.location.href = "#/exam/exam-result-op";
		} 
		
		$scope.showPage = function() {
			window.location.href = "#/exam/exam-result-op";
		}

		//获取数据列表
		$scope.getDataList = function(page) {
			if(page) {
                var nowpage = page;
            } else {
                var nowpage = 1;
            }
			$.AJAX({
				type : "POST",
				url : config.basePath + "examResult/list",
				data : {
					pageNo : nowpage,
                	pageSize : $scope.pageSize,
                	startTime : $("#e_startTime").val(),
					endTime : $("#e_endTime").val(),
					condition : $scope.condition,
                	subjectName : $scope.subjectName,
                	result : $scope.result
				},
				success : function(data) {
					$scope.datas = data.result.list;
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

		//excel导入
	  	$scope.uploadfile = function(){
			var html='<div id="createFileHtml" class="hidden"><form method="post" enctype="multipart/form-data" name="form1" id="f1"><input type="file" id="apkFile" name="file"/></form></div>';
			if(!$('#createFileHtml').length) {
				$('body').append(html);
			};
			$('#apkFile').click();
			$('#apkFile').change(function(){
				Layer.confirm({width:300,height:160,title:"确认上传吗?",header:"确认"},function() {
					var form = new FormData(document.getElementById("f1"));
					$.ajax({
						url:config.basePath + "examResult/upload",
						type:"POST",
						data:form,
						xhrFields:{withCredentials: true},
						crossDomain: true,
						processData:false,	
						contentType:false,
						async: false,
						success:function(data) {
							$("#createFileHtml").remove();		
							if (data.code == 200) {
								 Layer.alert({width:300,height:150,type:"error",title:data.msg});
								 return;
							}
							$scope.flag = 2;
							$scope.filename = data.result.filename;
							$scope.name = data.result.name;
							$scope.importDatas = data.result.data;
							$scope.count = data.result.count;
							$scope.validCount = data.result.validCount;
							$scope.title = data.result.title;
							new Page({
								parent:$("#copot-page"),
								nowPage:1,
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
					url : config.basePath + "examResult/import?type=1",
					data : {
						filename : $scope.filename,
						name : $scope.name,
						title : $scope.title
					},
					success : function(data){
						$scope.flag = 1;
						$scope.getDataList();
						Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});    
					}
				});
			});
		}

		//取消导入数据
		$scope.cancelImport = function() {
			Layer.confirm({width:300,height:160,title:"确认取消导入数据?",header:"取消确认"},function() {
				$.AJAX({
					url : config.basePath + "examResult/import?type=2",
					data : {filename:$scope.filename},
					success : function(data){
						$scope.flag = 1;
						$scope.getDataList();
						$scope.$apply();
					}
				});
			});
		}

		//下载模板
	    $scope.download = function() {
	        var url = config.basePath + "examResult/download";
	        window.open(url);
	    }
}])