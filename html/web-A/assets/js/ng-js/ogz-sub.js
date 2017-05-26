
app.controller("OgzSub",["$scope","$filter","$compile",function($scope,$filter,$compile){
	
	$scope.toEditHref = function(id){
		window.location.href = $scope.NURL+'/update/sub?id='+id;
	}
	
    $scope.statusDatas = [{id: "1",value: '建设筹备中'},{id: "2",value: '开业招生中'},{id: "3",value: '正常教学中'}, {id: "4",value: '停业整顿中'}];
    $scope.defaultPage = getUrl('page') || 1;  
    
    if (getUrl('search')) {
        $scope.condition = getUrl('search');
    }
    $scope.pageSize=10;    

    window.onhashchange=function(){
        $scope.defaultPage= getUrl('page') || 1; 
    }
    
    $scope.getDataList=function(){
        $.AJAX({
            url : config.basePath + "store/list",
            data : {
                condition : $scope.condition,
                pageNo : $scope.defaultPage,
                pageSize : $scope.pageSize
            },
            success : function(data){
                $scope.datas = data.result.list
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
    };
    
    $scope.getDataList();

    $scope.searchList = function() {
        if ($scope.condition) {
            var nowURL = location.href.split('#');
            var newurl = nowURL[1] + "#&&search=" + $scope.condition;
            location.hash= newurl;
        } else {
            var nowURL = location.href.split('#');
            var newurl = nowURL[1];
            location.hash= newurl;
        }
    }
    
	$scope.storeDelete = function(storeid) {
        Layer.confirm({width:300,height:160,title:"确认删除此门店信息？",header:"删除门店"},function(){
            $.AJAX({
                url:config.basePath+"store/deleteStore",
                data: {storeid:storeid},
                success:function(data){
                    Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                    $scope.getDataList();
                }
            });
        });
    }
	
	/*
    $scope.resetForm = function () {
        $scope.saveData = {};
        $scope.saveData.status='1';
        $scope.myForm.$setPristine();
    };


    $scope.submitAdd = function() {
        $.AJAX({
            url : config.basePath + "store/addStore",
            type : "POST",
            data : {        
                storename : $scope.saveData.storename,
                storeaddress : $scope.saveData.storeaddress,
                status : $scope.saveData.status,
                tel :  $scope.saveData.tel
            },
            success:function(data){
                Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                $('#addStore').modal('hide');
                $scope.saveData = {};
                $scope.saveData.status='1';

                $scope.getDataList();
            }
        });
    }

    $scope.submitEdit = function() {
        $.AJAX({
            url : config.basePath + "store/updateStore",
            type : "POST",
            data : {
                storeid : $scope.editData.storeid,
                storename : $scope.editData.storename,
                storeaddress : $scope.editData.storeaddress,
                status : $scope.editData.status,
                tel :  $scope.editData.tel
            },
            success : function(data){
                Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                $('#editStore').modal('hide');
                $scope.editData = {};
                $scope.getDataList();
            }
        });
    }

    $scope.storeEdit = function(data) {
        $scope.editData = data;
    }

  

    $scope.showMap = function(item) {
        $scope.id = item.storeid;
        var point;
        $scope.lng = 113.947967;
        $scope.lat = 22.556676;
        setTimeout(function() {
            if (item.lng == null || item.lng == "") {
                point = new BMap.Point(113.947967,22.556676); //默认地址
            } else {
                point = new BMap.Point(item.lng,item.lat);
            }
            $("#position").html(point.lng + "," + point.lat);
            var map = new BMap.Map("map_canvas");
            var marker = new BMap.Marker(point);       
            map.addOverlay(marker);                    
            marker.setAnimation(BMAP_ANIMATION_BOUNCE);
            map.addEventListener("click",function(e) {
                marker.setPosition(e.point);
                $("#position").html(e.point.lng + "," + e.point.lat);
                $scope.lng = e.point.lng;
                $scope.lat = e.point.lat;
            });
            map.enableScrollWheelZoom();   
            map.enableContinuousZoom();   
            map.centerAndZoom(point,16);
        },300);
    }

    $scope.savePosition = function() {
        $.AJAX({
            type : "POST",
            url:config.basePath + "store/updateStore",
            data:{
                storeid : $scope.id,
                lng : $scope.lng,
                lat : $scope.lat
            },
            success : function(data) {
                Layer.miss({width:250,height:90,title:data.msg,closeMask:true});
                $scope.getDataList();
            }
        });
    } */

}]);
