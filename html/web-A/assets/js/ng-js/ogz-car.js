
app.controller("OgzCar",["$scope","$filter",function($scope,$filter){
	
	$scope.toEditHref = function(id){
	   window.location.href = $scope.NURL+'/ogz/updateCar?carid='+id;
   }
   $scope.toAddHref = function(id){
	   window.location.href = $scope.NURL+'/ogz/addCar';
   }
	
    $scope.platecolorDatas = [{id: "1",value: '蓝色'},{id: "2",value: '黄色'},{id: "3",value: '黑色'}, {id: "4",value: '白色'}, 
                              {id: "5",value: '绿色'},{id: "9",value: '其他'}];
    $scope.skillstatusDatas = [{id: "0",value: '未使用'},{id: "1",value: '使用中'}, {id: "2",value: '维护中'}];
    $scope.checkstatusDatas = [{id: "0",value: '不合格'},{id: "1",value: '合格'}];
    $scope.secondstatusDatas = [{id: "0",value: '未完成'},{id: "1",value: '完成'}];
    $scope.statusDatas = [{id: "0",value: '未使用'},{id: "1",value: '使用中'}, {id: "2",value: '维护中'}];
    $scope.perdritypeDatas = perdritype;
    $scope.defaultPage = getUrl('page') || 1;
    $scope.pageSize = 10;   
    $scope.baseImgurl = config.imagePath;
    if(getUrl('search')){
        $scope.condition = getUrl('search');
    }

    window.onhashchange = function(){
        $scope.defaultPage= getUrl('page') || 1; 
    }

    //回车事件
    $scope.getkey = function(e){
        var keycode = window.event?e.keyCode:e.which;
        if(keycode==13){
            $scope.searchList();
        }
    }

    $scope.getDataList = function(data) {
        $.AJAX({
        	type : "POST",
            url : config.basePath + "coachCar/list",
            data : {
            	condition : $scope.condition,
            	pageNo : $scope.defaultPage,
            	pageSize : $scope.pageSize
        	},
            success : function(data){
                $scope.items = data.result.list
                $scope.total = data.result.total;
                $scope.$apply();
                new Page({
                    parent:$("#copot-page"),
                    nowPage:$scope.defaultPage,
                    pageSize:$scope.pageSize,
                    totalCount:$scope.total,
                });
            }
        });
    }

    $scope.getDataList();

	//搜索查询
    $scope.searchList = function() {
        if($scope.condition){
            var nowURL = location.href.split('#');
            var newurl = nowURL[1] + "#&&search=" + $scope.condition;
            location.hash = newurl;
        }else{
            var nowURL = location.href.split('#');
            var newurl = nowURL[1];
            location.hash= newurl;
        }
    }
    
  /*  //新增教练车
    $scope.addCoachCar = function() {
		var photo_url = '';
		if($("#imgfile").val()){
			getimgKey();
			$("#photo_url").val($scope.imgKey);
		}
        $.AJAX({
            url : config.basePath + "coachCar/addCoachCar",
            type : "POST",
			async:false,
            data :  $scope.addData,
            success:function(data){
                Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                $("#addCoachCar").modal('hide');
                $scope.getDataList();
            }
        });
		
		$("#addImg").text('选择图片');
		$('#createImg').remove();
    }

    $scope.resetForm = function() {
        $scope.addData = {};
		$("#addImg").text('选择图片');
        $scope.addData.platecolor = '1';
        $scope.addData.perdritype = 'C1';
        $scope.addData.skillstatus = '0';
        $scope.addData.checkstatus = '0';
        $scope.addData.secondstatus = '0';
        $scope.addData.status = '0';
        $scope.addForm.$setPristine();
    };
    
    //编辑数据赋值
    $scope.edit = function(data) {
        $scope.editData = data;
    }

    //编辑教练车
    $scope.editCoarchCar = function(carid) {
		$("#edit_photo_url").val($scope.editData.photo_url);
		if($("#imgfile").val()){
			getimgKey();
			$("#edit_photo_url").val($scope.imgKey);
		}
		
        $.AJAX({
            url : config.basePath + "coachCar/updateCoachCar?carid=" + carid,
            type : "POST",
            data :  $("#editForm").serialize(),
            success : function(data) {
                Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                $("#editCoachCar").modal('hide');
                $scope.getDataList();
            }
        });
		$("#editImg").text('选择图片');
		$("#updateImg").text("更改头像");
		$('#createImg').remove();
    } */

    //删除教练车
    $scope.delete = function(carid){
        Layer.confirm({width:300,height:160,title:"确认删除此教练车信息?",header:"删除教练车"},function(){
            $.AJAX({
                url : config.basePath + "coachCar/deleteCoachCar",
                type : "POST",
                data : { carid : carid },
                success:function(data){
                    Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                    $scope.getDataList();
                }
            });
        });
    }

    //分配教练列表
    $scope.coachList = function(carid) {
        $scope.alloCarid = carid;
        $("#coachList").modal("toggle");
        $scope.getList = function(page) {
            if(page) {
                var nowpage = page;
            } else {
                var nowpage = 1;
            }
            $.AJAX({
                method : "GET",
                url : config.basePath + "coach/list",
                data : {
					carid:carid,
                    employstatus:0,
                    pageNo:nowpage,
                    pagesize:10
                },
                success : function(data) {
                    $scope.datas = data.result.list;
                    $scope.total = data.result.total;
                    $scope.$apply();
                    new Page({
                        type:2,
                        parent:$("#copot-page2"),
                        nowPage:nowpage,
                        pageSize:10,
                        totalCount:$scope.total,
                        callback:$scope.getList
                    }); 
                }
            });
        }
        $scope.getList();
    }

    //分配教练
    $scope.alloCoachCar = function(coachid){
        Layer.confirm({width:300,height:160,title:"确认绑定此教练？",header:"绑定教练"},function(){
            $.AJAX({
                url:config.basePath + "coachCar/alloCoachCar",
                data:{
                    carid : $scope.alloCarid,
                    coachid : coachid
                },
                success:function(data){
                    $("#coachList").modal('hide');
                    Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                    $scope.editData = {};
                    $scope.getDataList();
                }
            });
        });
    }



    //查看已分配教练
    $scope.alloedCoachList = function(carid) {
        $scope.cancelCarid = carid;
        $("#alloCoachList").modal("toggle");
        $scope.getList = function(page) {
            if (page) {
                var nowpage = page;
            } else {
                var nowpage = 1;
            }
            $.AJAX({
                method : "GET",
                url : config.basePath + "coachCar/listCoachByCarid",
                data : {
                    pageNo:nowpage,
                    pagesize:10,
                    carid : carid
                },
                success : function(data){
                    $scope.datas = data.result.list;
                    $scope.total = data.result.total;
                    $scope.$apply();
                    new Page({
                        type:2,
                        parent:$("#copot-page3"),
                        nowPage:nowpage,
                        pageSize:10,
                        totalCount:$scope.total,
                        callback:$scope.getList
                    }); 
                }
            });
        }
        $scope.getList();
    }

    //取消分配教练
    $scope.cancelCoachCar = function(coachid){
        Layer.confirm({width:300,height:160,title:"确认取消绑定？",header:"取消绑定"},function(){
            $.AJAX({
                url:config.basePath + "coachCar/cancelCoachCar",
                data: {
                    carid : $scope.cancelCarid,
                    coachid : coachid
                },
                success:function(data){
                    $("#alloCoachList").modal('hide');
                    Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                    $scope.editData = {};
                    $scope.getDataList();
                }
            });
        });
    }

   /*	//上传图片
	$scope.uploadImg = function(type){
	 	var html='<div id="createImg" class="hidden"><form method="post" enctype="multipart/form-data" name="form1" id="f2">'+
				'<input type="hidden"  id="token" name="token" >'+
				'<input name="key" value="" id="key">'+
				'<input type="file" id="imgfile" name="file"/></form></div>';
		if(!$('#createImg').length){
			$('body').append(html);
		};
		$('#imgfile').click();
		$('#imgfile').change(function(){
			if($("#imgfile").val()){
				var filepath = $("#imgfile").val();
				var postfix = filepath.substring(filepath.lastIndexOf('.'),filepath.length)
				if(postfix != '.jpg' && postfix != '.jpeg' && postfix != '.png' && postfix != '.bmp' ){
					$('#createImg').remove();
					Layer.alert({width:300,height:150,type:"error",title:'格式不正确!'});
					return;
				}
				var path = $("#imgfile").val();
				var timestamp=new Date().getTime();
				$("#key").val(path.substring(path.lastIndexOf("\\")+1,path.lastIndexOf("."))+'_'+timestamp);
				if(type == '1'){
					$("#addImg").text( path.substring(path.lastIndexOf("\\")+1) );
				}
				if(type == '2'){
					$("#editImg").text( path.substring(path.lastIndexOf("\\")+1) );
					$("#updateImg").text( path.substring(path.lastIndexOf("\\")+1) );
				}
			}
		})		
	}
	
	function getimgKey(){
	   $.AJAX({
			type : "POST",
			url : config.basePath + "qiniuToken",
			async: false,
			success : function(data){
				$scope.imgToken = data.result;
			}
		});
		$("#token").val($scope.imgToken);
		var form = new FormData(document.getElementById("f2"));
		$.ajax({
			url:"http://up.qiniu.com",
			type:"post",
			data:form,
			processData:false,	
			contentType:false,
			async: false,
			success:function(data){
				$scope.imgKey = data.key;
			}
		})
	}


    var map; 
    var lushu;
    var point =  new BMap.Point(113.749728,22.800788); //默认地址
    var randomRoute = new Array(
        new BMap.Point(113.857633,22.596221),new BMap.Point(113.904777,22.594887),
        new BMap.Point(113.900752,22.533745),new BMap.Point(113.916419,22.549507),
        new BMap.Point(113.916347,22.528604),new BMap.Point(113.881636,22.497821), 
        new BMap.Point(113.952063,22.510108),new BMap.Point(114.058854,22.534813),
        new BMap.Point(114.036145,22.612503),new BMap.Point(114.051381,22.564720),
        new BMap.Point(114.089899,22.559113),new BMap.Point(114.059861,22.522929),
        new BMap.Point(114.018394,22.536115),new BMap.Point(114.034276,22.561482),
        new BMap.Point(113.998212,22.589915),new BMap.Point(113.963849,22.566555)
    ); 

    //显示地图信息
    $scope.showMap = function(licnum) {
        if (map == null || map == undefined) {
            map = new BMap.Map("map_canvas");
        } 
        map.enableScrollWheelZoom();   
        map.enableContinuousZoom();   
        map.centerAndZoom(point,12);    
        setTimeout(function() {
            var start = randomRoute[getRandomNum(0,15)];
            var end = randomRoute[getRandomNum(0,15)];      
            // 实例化一个驾车导航用来生成路线
            var driving = new BMap.DrivingRoute(map);
            driving.search(start, end);
            driving.setSearchCompleteCallback(function() {
                var pts = driving.getResults().getPlan(0).getRoute(0).getPath(); 
                //map.addOverlay(new BMap.Polyline(pts, {strokeColor: '#111'})); //显示路线
                map.setViewport(pts);
                lushu = new BMapLib.LuShu(map,pts,{
                    defaultContent : licnum,
                    autoView:true,//是否开启自动视野调整，如果开启那么路书在运动过程中会根据视野自动调整
                    icon  : new BMap.Icon('http://developer.baidu.com/map/jsdemo/img/car.png', new BMap.Size(52,26),{anchor : new BMap.Size(27, 13)}),
                    speed : 20,
                    enableRotation:true,//是否设置marker随着道路的走向进行旋转  
                    landmarkPois: []      
                });
                lushu.start();
            });      
        },1000); 
    } 

    $('#mappanel').on('hidden.bs.modal',function(e) {  
        lushu.stop();
        //map.reset();
        map.clearOverlays();
    }); */  

}]);