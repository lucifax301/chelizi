
app.controller("updateSub",["$scope","$filter","$http",function($scope,$filter,$http){
	
	var href = location.href;
	var storeid = href.substring(href.indexOf("=")+1, href.length ); //主键
	$("#storeid").val(storeid);
	$scope.baseImgurl = config.imagePath;
	//基本信息
	$scope.getDataList=function(){
        $.AJAX({
            url : config.basePath + "store/getStore",
            data : {storeid : storeid},
            success : function(data){
                $scope.editData = data.result;
				console.log($scope.editData);
				$("#leaseStartDate").val($scope.editData.leaseStartDate);
				$("#leaseEndDate").val($scope.editData.leaseEndDate);
				$("#lng").val($scope.editData.lng);$("#lat").val($scope.editData.lat);
                $scope.$apply();
            }
        });
    };
    $scope.getDataList();
	
	
	//图片展示
	$scope.showImages = function(){
		$.AJAX({
			url:config.basePath+"trainarea/imageListById",
			data:{businessId:storeid},
			success:function(data){
				$scope.imageData = data.result;
				$scope.isImage =data.result.length;
				$scope.$apply(); 			
			}
		});
	}
	$scope.showImages();
	
	//删除照片
	$scope.resetImage = function($event){
		var ids = '';
		$("input[class='subcheck']:checked").each(function(index,e){
			ids = ids + ',' + $(e).val();
		})
		ids = ids.substring(1,ids.length);
		if(ids){
			Layer.confirm({width:300,height:160,title:"确认删除图片吗？",header:"删除"},function(){
				$.AJAX({
					method: "POST",
					url:config.basePath+"trainarea/deleteImage",
					data:{ids:ids},
					success:function(data){
						if(data.code==0){
							Layer.miss({width:250,height:90,title:data.msg,closeMask:true});
						}
						if(data.code==2){
							Layer.alert({width:300,height:150,type:"error",title:data.msg});
						}
						$scope.showImages();
						$scope.$apply();
					}
				});
			});
		}
	
	}
	
	//全选、取消全选的事件  
	$scope.selectAll = function(){  
		if ($("#SelectAll").prop("checked")) {  
			$(".subcheck").prop("checked", true);  
		} else {  
			$(".subcheck").prop("checked", false);  
		}  
	}  
	//子复选框的事件  
	$scope.setSelectAll = function(id){  
		//当没有选中某个子复选框时，SelectAll取消选中  
		if (!$(".subcheck").checked) {  
			$("#SelectAll").prop("checked", false);  
		}  
		var chsub = $(".subcheck").length; //获取subcheck的个数  
		var checkedsub = $("input[class='subcheck']:checked").length; //获取选中的subcheck的个数  
		if (checkedsub == chsub) {  
			$("#SelectAll").prop("checked", true);  
		}  
	}
	
	
	
	
	
	

	$scope.statusDatas = [{id: 1,value: '营业'},{id: 2,value: '停业'},{id: 3,value: '整改'}, {id: 4,value: '停业整顿'}, {id: 5,value: '歇业'}, {id: 6,value: '注销'}, {id: 7,value: '其他'}];
	//时间插件初始化
	var start = {
		elem: '#leaseStartDate',
		format: 'YYYY-MM-DD',
		istime: false,
		istoday: true,
		choose: function (datas) {
			datas = updateOne('+',datas);
			end.min = datas; //开始日选好后，重置结束日的最小日期
			end.start = datas ; //将结束日的初始值设定为开始日
		}
	};
    var end = {
        elem: '#leaseEndDate',
        format: 'YYYY-MM-DD',
        istime: false,
        istoday: false,
        choose: function (datas) {
			datas = updateOne('-',datas);
            start.max = datas; //结束日选好后，重置开始日的最大日期
        }
    };
	
	function updateOne(sign,datas){
		var mydate=new Date(Date.parse(datas.replace(/-/g,"/")));
		var milliseconds=mydate.getTime();
		if(sign==='+'){
			milliseconds=mydate.getTime()+1000*60*60*24; 
		}
		if(sign==='-'){
			milliseconds=mydate.getTime()-1000*60*60*24; 
		}
		var yesterday=new Date(); 
		yesterday.setTime(milliseconds); 

		var strYear=yesterday.getFullYear(); 
		var strDay=yesterday.getDate(); 
		var strMonth=yesterday.getMonth()+1; 
		if(strMonth<10) 
		{ 
		strMonth="0"+strMonth; 
		} 
		var strYesterday=strYear+"-"+strMonth+"-"+strDay;
		return strYesterday;
	}
	$("#leaseStartDate").click(function(){
		laydate(start);
	})
	
    $("#leaseEndDate").click(function(){
		laydate(end);
	})
	
	//上传图片
	  $scope.uploadImg = function(){
		  var num = 0;
		  $('#imgDiv').children('div').each(function(index,e){
			 index++;
			 num = index;
		  })
			var html= '<div class="col-md-3 col-sm-3 col-xs-12 form-group has-feedback" style="display:none;width:280px;">'+
						'<div style="float:left; padding:2px;" style="height:124px;width:174px;">'+
							'<div id="createImg_'+num+'" class="hidden"><form method="post" enctype="multipart/form-data" name="form1" id="form_'+num+'">'+
							'<input type="hidden"  id="token_'+num+'" name="token" >'+
							'<input name="key" value="" id="key_'+num+'">'+
							'<input type="file" id="imgfile_'+num+'" name="file"/></form></div>'+
							'<img style="width:174px;height:124px;" id="imgUrl_'+num+'"/>'+
						'</div>'+
						'<div style="float:left; padding:2px;">'+
							'<div class="item form-group"><p>&nbsp;</p>'+
							'<p><button type="button" class="btn btn-default btn-sm" style="background-color:#e7e7ea" onclick="javascript:$(this).parent().parent().parent().parent().remove();">&nbsp;&nbsp;删除&nbsp;&nbsp;</button></p>'+
							'<p><button type="button" class="btn btn-success btn-sm updateImg">&nbsp;&nbsp;更换&nbsp;&nbsp;</button></p></div>'+
						'</div>'+
					'</div>';

			$('#imgDiv').append(html);
			
			$('#imgfile_'+num).click();
			$('#imgfile_'+num).change(function(){
				if($('#imgfile_'+num).val()){
					var filepath = $('#imgfile_'+num).val();
					var postfix = filepath.substring(filepath.lastIndexOf('.'),filepath.length)
					if(postfix !='.jpg' && postfix !='.jpeg' && postfix !='.png' && postfix !='.bmp' ){
						Layer.alert({width:330,height:150,type:"error",title:'请选择jpg/jpeg/png/bmp格式！'});
						return;
					}
					
					var path = $('#imgfile_'+num).val();
					

					var objUrl = getObjectURL(this.files[0]) ;
					console.log("objUrl = "+objUrl) ;
					if (objUrl) {
						$('#imgUrl_'+num).attr("src", objUrl) ;
						$('#imgUrl_'+num).parent().parent().css('display','block');
					}
		
				function getObjectURL(file) {
					var url = null ; 
					if (window.createObjectURL!=undefined) { // basic
						url = window.createObjectURL(file) ;
					} else if (window.URL!=undefined) { // mozilla(firefox)
						url = window.URL.createObjectURL(file) ;
					} else if (window.webkitURL!=undefined) { // webkit or chrome
						url = window.webkitURL.createObjectURL(file) ;
					}
					return url ;
				}
				
					var timestamp=new Date().getTime();
					$("#key_"+num).val(path.substring(path.lastIndexOf("\\")+1,path.lastIndexOf("."))+'_'+timestamp);
					
				}
			})			
	  }
	  
	  // 上传七牛
	  var imgeArray = '';
	  function getimgKeys (){
		   $('#imgDiv').children('div').each(function(index,e){
				$.AJAX({
					type: "POST",
					url:config.basePath+"qiniuToken",
					async: false,
					success:function(data){
						$scope.imgToken = data.result;
					}
				});
				$("#token_"+index).val($scope.imgToken);
				var form = new FormData(document.getElementById("form_"+index));
				$.ajax({
					url:"http://up.qiniu.com",
					type:"post",
					data:form,
					processData:false,	
					contentType:false,
					async: false,
					success:function(data){
						$scope.imgKey = data.key;	
						imgeArray = imgeArray + ',' + $scope.imgKey;
					}
				})

		  })
		  imgeArray = imgeArray.substring(1,imgeArray.length);
		  $("#imgeArray").val(imgeArray);
	  }
	  
	  //更换图片
	  $("div[id='imgDiv']").on('click','.updateImg',function(){
		var inputFile =   $(this).parent().parent().parent().prev().find("input[type='file']");
		
		$(inputFile).click();
			$(inputFile).change(function(){
				if($(inputFile).val()){
					var filepath = $(inputFile).val();
					var postfix = filepath.substring(filepath.lastIndexOf('.'),filepath.length)
					if(postfix !='.jpg' && postfix !='.jpeg' && postfix !='.png' && postfix !='.bmp' ){
						Layer.alert({width:330,height:150,type:"error",title:'请选择jpg/jpeg/png/bmp格式！'});
						return;
					}
					
					var path = $(inputFile).val();
					var objUrl = getObjectURL(this.files[0]) ;
					console.log("objUrl = "+objUrl) ;
					if (objUrl) {
						$(inputFile).parent().parent().next().attr("src", objUrl) ;
						$(inputFile).parent().parent().parent().parent().css('display','block');
					}
		
				function getObjectURL(file) {
					var url = null ; 
					if (window.createObjectURL!=undefined) { // basic
						url = window.createObjectURL(file) ;
					} else if (window.URL!=undefined) { // mozilla(firefox)
						url = window.URL.createObjectURL(file) ;
					} else if (window.webkitURL!=undefined) { // webkit or chrome
						url = window.webkitURL.createObjectURL(file) ;
					}
					return url ;
				}
					var timestamp=new Date().getTime();
					$(inputFile).prev().val(path.substring(path.lastIndexOf("\\")+1,path.lastIndexOf("."))+'_'+timestamp);
					
				}
			})	

	  })
	

	//编辑保存
	$scope.editSub = function(){
		/*var leaseStartDate = $('#leaseStartDate').val();
		var leaseEndDate = $('#leaseEndDate').val();
		var lng = $('#lng').val();
		var lat = $('#lat').val();*/
		//上传照片
		getimgKeys();
		/*var json = {
			storeid : storeid,
			storearea:$scope.editData.storearea,
			storename : $scope.editData.storename,
			storeaddress : $scope.editData.storeaddress,
			status : $scope.editData.status,
			tel :  $scope.editData.tel,
			contacter:$scope.editData.contacter,
			operatingType:$scope.editData.operatingType,
			leaseEndDate:leaseEndDate,
			leaseStartDate:leaseStartDate,
			recordState:$scope.editData.recordState,
			grade:$scope.editData.grade,
			owner:$scope.editData.owner,
			mortgageMoney:$scope.editData.mortgageMoney,
			leaseMoney:$scope.editData.leaseMoney,
			ownerPhone:$scope.editData.ownerPhone,
			lng:lng,
			lat:lat,
			imgeArray:imgeArray
		};*/

		$.AJAX({
			method: "POST",
			url:config.basePath+"store/updateStore",
			//data:json,
			data:$("#editForm").serialize(),
			success:function(data){
				if(data.code==0){
					Layer.miss({width:250,height:90,title:data.msg,closeMask:true});
					window.location.href = $scope.NURL+'/ogz/sub';
				}
				if(data.code==3){
					Layer.alert({width:300,height:150,type:"error",title:data.msg});
				}
			}
        }); 

	} 

  //返回
  $scope.goback = function(){
	  window.location.href = $scope.NURL+'/ogz/sub';
  }
 
	
	/********地图部分asdf*********/
  	var map;var point;
	setTimeout(function() {
		map = new BMap.Map("map_canvas");
		point = new BMap.Point(114.064387,22.549316);
		map.centerAndZoom(point, 13);
		if($("#lng").val()){
			point = new BMap.Point($("#lng").val(),$("#lat").val());
			map.centerAndZoom(point, 16);
			var marker = new BMap.Marker(point);  // 创建标注
			map.addOverlay(marker);
			marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
		}
		map.setZoom(16);
		//map.enableScrollWheelZoom(true);
		map.addControl(new BMap.NavigationControl()); 
	},100)

	// 创建地址解析器实例
	var myGeo = new BMap.Geocoder();
	// 将地址解析结果显示在地图上
	var marker = null;
	$("#position").click(function(){
		map.removeOverlay(marker);
		myGeo.getPoint($scope.editData.storeaddress, function(point){
			if (point) {
				$("#lng").val(point.lng);
				$("#lat").val(point.lat);
				map.centerAndZoom(point, 16);
				var bootstrapValidator = $("#editForm").data('bootstrapValidator');  
				bootstrapValidator.updateStatus('lat', 'NOT_VALIDATED').validateField('lat');
				marker = new BMap.Marker(point);  // 创建标注
				map.addOverlay(marker);
				marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
				map.enableScrollWheelZoom(true);
			}else{
				Layer.alert({width:300,height:150,type:"error",title:'您输入的地址无法解析!！'});
				$('#lng').val('');$('#lat').val('');
			}
		}, "深圳市"); 
		
	})
	
	//表单验证
	$(function () {
		$('#editForm').bootstrapValidator({
			feedbackIcons: {
　　　　　　　　valid: 'glyphicon glyphicon-ok',
　　　　　　　　invalid: 'glyphicon glyphicon-remove',
　　　　　　　　validating: 'glyphicon glyphicon-refresh'
			},
			fields: {
				storename:{
					validators: {
						notEmpty: {
							message: '名称不能为空'
						},
						stringLength : {
							max: 120,
							message: '长度必须在120个字符内'
						}
						
					}
				},
				storeaddress:{
					validators: {
						notEmpty: {
							message: '名称不能为空'
						}
					}
				},
				contacter:{
					validators: {
						notEmpty: {
							message: '联系人不能为空'
						},
						stringLength : {
							max: 120,
							message: '长度必须在30个字符内'
						}
					}
				},
				storearea:{
					validators: {
						notEmpty: {
							message: '面积不能为空'
						},
						regexp: {
							regexp: /^[1-9]\d{0,7}$/,
							message: '输入不正确'
						}
					}
				},
				tel:{
					validators: {
						notEmpty: {
							message: '联系电话不能为空'
						},
						regexp: {
							regexp: /^1[3|4|5|7|8]\d{9}$/,
							message: '联系电话输入不正确'
						}
					}
				},
				leaseMoney:{
					validators: {
						regexp: {
							regexp: /^\d{1,15}(\.\d{1,2})?$/,
							message: '租金最多15位数,保留两位小数'
						}
					}
				},
				owner:{
					validators: {
						stringLength : {
							max: 120,
							message: '长度必须在120个字符内'
						}
					}
				},
				mortgageMoney:{
					validators: {
						regexp: {
							regexp: /^\d{1,15}(\.\d{1,2})?$/,
							message: '租金最多15位数,保留两位小数'
						}
					}
				},
				ownerPhone:{
					validators: {
						regexp: {
							regexp: /^1[3|4|5|7|8]\d{9}$/,
							message: '手机号码输入不正确'
						}
					}
				},
				lat:{
					validators: {
						notEmpty: {
							message: '坐标不能为空'
						}
					}
				}
			},
			submitHandler: function() {
				$scope.editSub();
			}
		});

	}); 
	
	

	// 城市级联 selects 为数组形式，请注意顺序 
	$('#element_id').cxSelect({
		url: 'assets/js/area/cityData.min.json',
		selects: ['province', 'city', 'area'],
		nodata: 'none'
	});
	
	$scope.loadAllScopeArea= function(){		
		$.AJAX({
			method: "get",
			url:config.basePath+"scope/getAll",
			success:function(data){
				$scope.scopeareas = data.result;
				angular.forEach($scope.scopeareas, function(data,index){
					if(data.scopeId == $scope.editData.scopeAreaId){
						$scope.scopeArea = $scope.scopeareas[index];
						$scope.getProxyByScopeId();
					}
				});
				$scope.$apply();
			}
        });
	};
	$scope.loadAllScopeArea();

	$scope.getProxyByScopeId= function(){	
		$.AJAX({
			method: "get",
			url:config.basePath+"proxy/getAllByScopeId",
			data:{scopeId:$scope.scopeArea.scopeId},
			success:function(data){
				$scope.proxydealers = data.result;
				angular.forEach($scope.proxydealers, function(data,index){
					if(data.proxyId == $scope.editData.proxyDealerId){
						$scope.proxyDealer = $scope.proxydealers[index];
						$scope.generateshopid();
					}
				});
				$scope.$apply();
			}
        });
	};

	$scope.generateshopid= function(){		
		$scope.storeNum = $scope.editData.storeNum;
		$scope.shopid =  $filter('idfilter')($scope.scopeArea.scopeId) + $filter('idfilter')($scope.proxyDealer.proxyId)
			+ $filter('idfilter')($scope.storeNum);
		$scope.$apply();
	};
}]);




