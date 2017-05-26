	app.controller("StudentList",["$scope","$filter",function($scope,$filter) {
		$scope.uploadbtn = true;
		$scope.pageSize = 10; 
		$scope.defaultPage = getUrl('page') || 1;  
		$scope.baseImgurl = config.imagePath;
		if(getUrl('search')) {
			$scope.condition = getUrl('search');
		}

        window.onhashchange = function() {
            $scope.defaultPage = getUrl('page') || 1; 
        }
		
		$scope.getkey = function(e) {
			var keycode = window.event?e.keyCode:e.which;
            if(keycode == 13) {
				$scope.searchList();
            }
		}

		$scope.searchList = function() {
			if($scope.condition) {
				var nowURL = location.href.split('#');
				var newurl = nowURL[1] + "#&&search=" + $scope.condition;
				location.hash = newurl;
			} else {
				var nowURL = location.href.split('#');
				var newurl = nowURL[1];
				location.hash = newurl;
			}
		}
		
		$scope.getList = function() {
			$.AJAX({
				type: "GET",
                url:config.basePath + "student/list",
                data:{
                	phone:$scope.condition,
                	name:$scope.condition,
                	pageNo :$scope.defaultPage,
                	pageSize :$scope.pageSize
                },
                success:function(data) {
					$scope.datas = data.result.list;
					$scope.total = data.result.total;
					$scope.$apply();
					new Page({
						parent:$("#copot-page"),
						nowPage:$scope.defaultPage,
						pageSize:$scope.pageSize,
						totalCount:$scope.total,
						search:$scope.condition
					});                     
                }
            });  
		}
			
		$scope.getList();

		//删除
		$scope.del = function(data,index) {
			 var idcard = data.idcard;
			 Layer.confirm({width:300,height:160,title:"确认删除吗？",header:"删除"},function() {
					$.AJAX({
						method: "POST",
						url:config.basePath + "student/delete",
						data:{idcard:idcard},
						success:function(data) {
							if(data.code==0) {
								Layer.miss({width:250,height:90,title:data.msg,closeMask:true});
							}
							if(data.code==2) {
								Layer.alert({width:300,height:150,type:"error",title:data.msg});
							}
							$scope.getList();
						}
					}); 
			 })
		}

		//编辑
		$scope.edit = function(index) {
			$scope.getList();
			$scope.editData = $scope.datas[index];
			//清楚操作痕迹
			if($scope.editData.photo_url) {
				$("#updateImg").text('更换头像');
			} 
			if(!$scope.editData.photo_url) {
				$("#editImg").text('选择头像');
			}
			
			//班级列表
			 $.AJAX({
				url:config.basePath+"class/list",
				success:function(data) {
					$scope.classidDatas = data.result.list;
					$scope.$apply();
				}
			 });
			
		}
			
			//编辑保存
			$scope.update = function($event) {
				var photo_url = $scope.editData.photo_url;
				if($("#imgfile").val()) {
					getimgKey();
					photo_url = $scope.imgKey;
				}
				var applydate = $("#q_endDate").val();
				 var json = {
					idcard:$scope.editData.idcard,
					stunum: $scope.editData.stunum,
					name:$scope.editData.name,
					sex:$scope.editData.sex,
					nationality:$scope.editData.nationality,
					phone:$scope.editData.phone,
					address:$scope.editData.address,
					busitype:$scope.editData.busitype,
					traintype:$scope.editData.traintype,
					applydate:applydate,
					applyexam:$scope.editData.applyexam,
					photo_url:photo_url,
					classid:$scope.editData.classid,
					paystatus:$scope.editData.paystatus
				}

				$.AJAX({
					method: "POST",
                    url:config.basePath+"student/update",
                    data:json,
					async:false,
                    success:function(data) {
						if(data.code==0) {
							Layer.miss({width:250,height:90,title:data.msg,closeMask:true});
						}
                        if(data.code==2) {
							Layer.alert({width:300,height:150,type:"error",title:data.msg});
						}
						$scope.getList();
                    }
                }); 
				$("#mymodal").modal('hide');
				$(".modal-backdrop").remove();
				$("#editImg").text('选择图片');
				$("#updateImg").text("更改头像");
				$('#createImg').remove();
			}

			//新增
			$scope.showClass = function() {
				$scope.saveData={};
				$scope.myForm.$setPristine();
                $scope.saveData.idcard = '';
				$scope.saveData.completeTime = ''; 
				$scope.saveData.money = '';
				$scope.saveData.phone = '';
				
				$scope.saveData.sex = '1';
				$scope.saveData.busitype='0';
				$scope.saveData.traintype='C1';
				$scope.saveData.applytype='1';
				$scope.saveData.recordType='1'; 
				//$scope.saveData.classid = 1; 
				$scope.saveData.applyexam='1'; 
				$scope.saveData.trainmethod='1';
				$scope.saveData.nationality='1'; 
				
				//班级列表
				 $.AJAX({
					url:config.basePath+"class/list",
					//async:false,
					data:{pageSize :100},
					success:function(data) {
						$scope.classidDatas = data.result.list;
						$scope.$apply();
					}
				 });
			}
			$scope.savestus = function() {
				/*var province=$("#province").find("option:selected").text(); 
				var city=$("#city").find("option:selected").text(); 
				var areas=$("#area").find("option:selected").text(); 
				var address = province+''+city+''+areas; */
				//
				var photo_url = '';
				if($("#imgfile").val()) {
					getimgKey();
					photo_url = $scope.imgKey;
				}
				var applydate = $("#q_startDate").val();
				 var json = {
					//stunum: $scope.saveData.stunum,
					name:$scope.saveData.name,
					sex:$scope.saveData.sex,
					nationality:$scope.saveData.nationality,
					phone:$scope.saveData.phone,
					address:$scope.saveData.address,
					//cardtype:$scope.saveData.cardtype,
					idcard:$scope.saveData.idcard,
					busitype:$scope.saveData.busitype,
					traintype:$scope.saveData.traintype,
					perdritype:$scope.saveData.perdritype,
					photo_url:photo_url,
					applydate:applydate,
					trainmethod:$scope.saveData.trainmethod,
					applyexam:$scope.saveData.applyexam,
					recordType:$scope.saveData.recordType,
					completeTime:$scope.saveData.completeTime,
					classid:$scope.saveData.classid,
					money:$scope.saveData.money,
					paystatus:'1'
					//islogout:'2'
				}
				 $.AJAX({
                    url:config.basePath+"student/add",
					async:false,
                    data:json,
                    success:function(data) {
						if(data.code==0) {
							//通过电话检查此人是否有潜在客户转化而来
							$.AJAX({
								url:config.basePath+"marketing/list",
								data:{phone:$scope.saveData.phone,name:$scope.saveData.phone},
								success:function(data) {
									var market = data.result.list;
									if(market.length) {
										var date = applydate.replace(/-/g,'/'); 
										var applyTime = new Date(date).getTime();
										if(Number(applyTime)>=Number(market[0].ctime)   ) {
											$.AJAX({
												url:config.basePath+"marketing/update",
												data:{marketid:market[0].marketid,progress:'4'},
												async:false,
												success:function(data) {
												}
											}); 

										}

									}

								}
							}); 

							Layer.miss({width:250,height:90,title:data.msg,closeMask:true});
						}
                        if(data.code==2) {
							Layer.alert({width:300,height:150,type:"error",title:data.msg});
						}
						if(data.code==3) {
							Layer.alert({width:300,height:150,type:"error",title:data.msg});
						}
                    }
                }); 
				$("#addmodal").modal('hide');
				$(".modal-backdrop").remove();
				$("#addImg").text('选择图片');
				$('#createImg').remove();
				$scope.getList();
				//$scope.$apply();
			}

			//查看
			$scope.showData = {};
			$scope.showInfo = function(data) {
				//$("#showMymodal").modal("toggle");
				$scope.showData = data;
			}
			$scope.show = true;
			$scope.close = function() {
				$scope.show = false;
			}

			//定义下拉单
			$scope.traintypeDatas = [{id: 'A1',value: 'A1'},{id: 'A2',value: 'A2'},{id: 'A3',value: 'A3'},
									  {id: 'C1',value: 'C1'},{id: 'C2',value: 'C2'},{id: 'C3',value: 'C3'},{id: 'C4',value: 'C4'},{id: 'C5',value: 'C5'},
									  {id: 'B1',value: 'B1'},{id: 'B2',value: 'B2'},{id: 'D',value: 'D'},{id: 'E',value: 'E'},{id: 'F',value: 'F'},{id: 'M',value: 'M'},{id: 'N',value: 'N'},{id: 'P',value: 'P'}

									 ];
			$scope.sexDatas = [{id: "1",value: '男'},{id: "2",value: '女'}];
		    $scope.nationalityDatas = [{id: "1",value: '中国'},{id: "2",value: '其他国家'}];
		    $scope.cardtypeDatas = [{id: "1",value: '身份证'},{id:"2",value: '护照'},{id: "3",value: '军官证'},{id: "4",value: '其他'}];// 身份证、护照、军官证、其他
		    $scope.busitypeDatas = [{id: "0",value: '初领'},{id: "1",value: '增领'},{id: "2",value: '其他'}];
		    $scope.applyexamDatas = [{id: '1',value: '未缴费'},{id: '2',value: '已缴费'},{id: '3',value: '投递资料'},{id: '4',value: '车管受理'},{id: '5',value: '未分配教练'},{id: '6',value: '科目1约课'},
									{id: '7',value: '科目1结业'},{id: '8',value: '科目1报考预约'},{id: '9',value: '科目1考试通过'},{id: '10',value: '科目1重考预约'},{id: '11',value: '科目2约课'},{id: '12',value: '科目2结业'},
									{id: '13',value: '科目2报考预约'},{id: '14',value: '科目2考试通过'},{id: '15',value: '科目2重考预约'},{id: '16',value: '长训完成'},{id: '17',value: '科目3约课'},{id: '18',value: '科目3结业'},
									{id: '19',value: '科目3考试通过'},{id: '20',value: '科目3重考预约'},{id: '21',value: '科目4约课'},{id: '22',value: '结业'},{id: '23',value: '科目4报考预约'},{id: '24',value: '科目4考试通过'},
									{id: '25',value: '待领证'},{id: '26',value: '已领证'},{id: '27',value: '已注销'}];
									
									
			$scope.recordTypeDatas = [{id: "0",value: '未备案'},{id: "1",value: '已备案'}];
		    $scope.applytypeDatas = [{id: '1',value: '在线报名'},{id: '2',value: '现场报名'},{id: '3',value: '终端报名'},{id: '4',value: '其他报名方式'}]; //在线报名、现场报名、终端报名、其他报名方式
			$scope.trainmethodDatas = [{id: '1',value: '服务全包'},{id: '2',value: '先学后付'},{id: '3',value: '先付后学'}];//服务全包、先学后付、先付后学
			$scope.paystatusDatas = [{id: '1',value: '已支付'},{id: '2',value: '未支付'}];


	  //excel导入
	  $scope.uploadfile = function() {
			var html='<div id="createFileHtml" class="hidden"><form method="post" enctype="multipart/form-data" name="form1" id="f1"><input type="file" id="apkFile" name="file"/></form></div>';
			if(!$('#createFileHtml').length) {
				$('body').append(html);
			};
			$('#apkFile').click();
			$('#apkFile').change(function() {
				Layer.confirm({width:300,height:160,title:"确认上传吗？",header:"资料上传"},function() {
					var form = new FormData(document.getElementById("f1"));
						$.ajax({
							url:config.basePath+"student/uploadExcel",
							type:"post",
							data:form,
							xhrFields:{withCredentials: true},
							crossDomain: true,
							processData:false,	
							contentType:false,
							async: false,
							success:function(data) {
								if(data.code==200) {
									Layer.alert({width:300,height:150,type:"error",title:data.msg});
									return;
								}
								if(data.code==201) {
									Layer.alert({width:300,height:150,type:"error",title:data.msg});
									return;
								}
								$scope.filename = data.result.filename;
								$scope.datas = data.result.data;
								$scope.uploadbtn = false;
								//$scope.pagediv = false;
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
				
				$('.confirmRight').click(function() {
					$('#createFileHtml').remove();

				})
			})
		}
		
		//导入db
		$scope.importFile = function(type) {
		$.AJAX({
			url:config.basePath+"student/importDB",
			data:{filename:$scope.filename,type:type},
			success:function(data) {
				if(data.code==0) {
					Layer.miss({width:250,height:90,title:data.msg,closeMask:true});
				}
				if(data.code==2) {
					//Layer.miss({width:250,height:90,title:data.msg,closeMask:true});
					Layer.alert({width:300,height:150,type:"error",title:data.msg});
				}

				$scope.uploadbtn = true;
				$scope.getList();
				$scope.pagediv = true;
				$('#createFileHtml').remove();
				$scope.$apply();

			}
		});
		}
		
		//下载
		$scope.download = function() {
			var url = config.basePath+"student/download";
			window.open(url);	
		}
	  
	  
	  //上传图片
	  $scope.uploadImg = function(type) {
		  // http://obqfnhv9x.bkt.clouddn.com
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
	  
	  function getimgKey () {
		   $.AJAX({
				type: "POST",
				url:config.basePath+"qiniuToken",
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
	 
	  
	  //教练列表
	 $scope.coachList = function(page) {
		if(page) {
        	 var nowpage = page; 
        	}else{
        	 var nowpage = 1; 
        	}  
			var json = {pageNo:nowpage,pagesize:10};
		  $.AJAX({
			url:config.basePath+"student/coaStuList",
			data:json,
			success:function(data) {
				$scope.coachdatas = data.result.list;
				console.log($scope.coachdatas);
				$scope.total = data.result.total;
				new Page({
					type:2,
					parent:$("#copot-page2"),
					nowPage:nowpage,
					pageSize:10,
					totalCount:$scope.total,
					callback:$scope.coachList
				}); //分页请求完毕 
				$scope.$apply();
			}
		});
		 
	 }

	  //需分配教练的学员id
	$scope.getCoach = function(id,instid) {
		$scope.idcard = id;
		$scope.instid = instid;
		$scope.coachList();
	}
	  $scope.checkedCoach = ''; //教练id
	  //分配教练选择事件
	  $scope.clecked = function(id) {
		  for(var i in $scope.coachdatas) {
			  $scope.coachdatas[i].add = 0;
		  }
		  $scope.coachdatas[id].add = 1;
		  $scope.checkedCoach = $scope.coachdatas[id].coachId;
	  }
    $scope.fenpeiCoach = function() {
		
		if($scope.checkedCoach!='') {
			var json = {coachId:$scope.checkedCoach,idcard:$scope.idcard,instid:$scope.instid,applyexam:'6'};
			 $.AJAX({
				url:config.basePath+"student/fenpeiClassCoach",
				data:json,
				//async:false,
				success:function(data) {
					if(data.code==0) {
							Layer.miss({width:250,height:90,title:data.msg,closeMask:true});
						}
                        if(data.code==2) {
							//Layer.miss({width:250,height:90,title:data.msg,closeMask:true});
							Layer.alert({width:300,height:150,type:"error",title:data.msg});
						}
				}
			});
		}
		
		$scope.checkedCoach = '';
		$("#fenpeiCoach").modal('hide');
		$(".modal-backdrop").remove();
		$scope.getList();
		$scope.$apply();
	}
	
	//注销
	$scope.layout = function(data) {
		if(data.islogout=='1') return;
		 Layer.confirm({width:300,height:160,title:"确认注销吗？",header:"注销"},function() {
			 var json = {islogout:'1',idcard:data.idcard};
			 $.AJAX({
				url:config.basePath+"student/cancel",
				data:json,
				success:function(data) {
					if(data.code==0) {
						Layer.miss({width:250,height:90,title:data.msg,closeMask:true});
					}
					if(data.code==2) {
						Layer.alert({width:300,height:150,type:"error",title:data.msg});
					}
						
					$scope.getList();
					$scope.$apply();
				}
			});
		 })
	}
	
	//时间插件初始化
	var start = {
		elem: '#q_startDate',
		format: 'YYYY-MM-DD',
		istime: false,
		istoday: true,
		choose: function (datas) {
			end.min = datas; //开始日选好后，重置结束日的最小日期
			end.start = datas; //将结束日的初始值设定为开始日
		}
	};
    var end = {
        elem: '#q_endDate',
        format: 'YYYY-MM-DD',
        istime: false,
        istoday: false,
        choose: function (datas) {
            start.max = datas; //结束日选好后，重置开始日的最大日期
        }
    };
	$("#q_startDate").click(function() {
		$scope.showMsg = false;
		laydate(start);
	})
	
    $("#q_endDate").click(function() {
		laydate(end);
	})
	
	function CurentTime()
    { 
        var now = new Date();
        var year = now.getFullYear();       //年
        var month = now.getMonth() + 1;     //月
        var day = now.getDate();            //日
        var clock = year + "-";
        if(month < 10)
            clock += "0";
        clock += month + "-";
        if(day < 10)
            clock += "0";
        clock += day + " ";
        return(clock); 
    }
	$scope.applydate = CurentTime();	
	////

  //保存
  $scope.submitAddForm = function(isValid) {
	   var reg=/\d{4}-\d{2}-\d{2}/;
		if(!reg.test($("#q_startDate").val())) {     
			$scope.timeAddMsg = true;
			return;			
        }

	  $scope.savestus();
	  $scope.timeAddMsg = false;
	  if (!isValid) {
	  }
  };
  
    //编辑  timeEditMsg
  $scope.submitEditForm = function(isValid) {
	  $scope.update();
  };


}]); 


	
	
	 