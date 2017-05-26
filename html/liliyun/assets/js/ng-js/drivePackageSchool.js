/*angular for 提现*/
var app=angular.module("app",["ngFilter","ngSelects"]);

/*main 控制器*/
app.controller("DrivePackageSchool",["$scope","$rootScope","$filter","Selects",function($s,$rootScope,$filter,Selects){

    /*-----------------------------------------查询训练场数据列表----------------------------------------------------*/
    $s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
    $s.pageSize=10;    //每页显示-显示条数
    $s.name="";    //高级查询

    // /*模拟数据*/
    // $s.data={
    // 	pages:10,
    // 	total:100,
    // 	pageSize:10,
    // 	pageNo:1,
    // 	dataList:[
    // 		{school_id:201,name:"深港",region:"广东-深圳",reverseLim:"1000",phoneNum:"13476225415",coachCount:25121,posDesc:"这里面是深圳港的介绍" },

    // 	]
    // };
    // //或得的数据列表
    // $s.datas=$s.data.dataList;

    /*全选与取消全选*/
    Selects.selects({datas:$s.datas,whichId:'ttid'});
    /*获取数据列表并展示*/
    $s.getDataList=function(){
        var json=getJson($s.defaultPage);
        $.AJAX({
            type:"get",
            url:json.url,
            data:json.data,
            success:function(data){
                var DATA=getListData(data);
                /*数据渲染页面*/
                $s.datas=DATA.dataList;
                /*全选与取消全选*/
                Selects.selects({datas:$s.datas,whichId:'ttid'});
                $s.$apply();
                //分页请求
                new Page({
                    parent:$("#copot-page"),
                    nowPage:$s.defaultPage,
                    pageSize:$s.pageSize,
                    totalCount:DATA.total,
                }); //分页请求完毕
            }
        });
    };
    $s.getDataList();
    /*参数配置函数*/
    function getJson(nowPage){
        var json={
            url:config.basePath+"school/queryPackage",
            data: {
                "pageNo": nowPage,
                "pageSize": parseInt($s.pageSize),
                "cityId":$s.cityId,
                "school_id":$s.school_id,
                "name": $s.name,
                "cstate": $s.cstate,
                "ostate": $s.ostate,
                "startTime": $s.startTime,
                "endTime": $s.endTime,
            }
        };
        return json;
    };

    /*hash值改变的时候加载数据列表*/
    window.onhashchange=function(){
        win.showLoading();
        $s.defaultPage=location.hash.substring(2) || 1;
        $s.getDataList();
    }

    /*--------------------------选择城市和驾校--------------------------------------*/
    /*加载城市列表*/
    queryCity({
        callback:function(data){
            $s.citys=data; $s.$apply();
        }
    });

    /*加载驾校列表*/
    $s.getSchools=function(){
        if($s.cityId){
            $s.checkHaveCity(); //判断是否选择城市
            $('#select-school li').last().addClass('active').siblings().removeClass("active");
            /*加载驾校列表*/
            $.AJAX({
                type:'get',
                url:config.basePath+"school/queryDriveSchool",
                data:{
                    cityId:$s.cityId,   //驾校id
                    pageNo:1,
                    pageSize:1000
                },
                success:function(data){
                    /*数据渲染页面*/
                    var DATA =JSON.parse(data.result.pageData);
                    $s.schools=DATA.dataList;
                    $s.$apply();
                }
            });
        }else{
            $('#select-school li').first().addClass('active').siblings().removeClass("active");
            $s.schoolNo=""; //清空驾校number
            $s.schools=""; //清空数据列表
            $s.school_id="";
        }
        $s.defaultPage=1; //默认第一页
        $s.getDataList();
    }
    /*判断是否选择城市*/
    $s.checkHaveCity=function(){
        $s.cityError=$s.cityId?false:true;
    }
    /*--------------------------选择城市和驾校--------------------------------------*/


    /*按所在学校筛选列表数据*/
    $s.getDataForSchool=function(){
        win.showLoading();
        $s.defaultPage=1; //默认第一页
        $s.school_id=$("#getDataForSchoolSel").val();
        $s.getDataList();
    }

    /*按审核状态筛选列表数据*/
    $s.getDataForCstate=function($event,type){
        $($event.target).addClass('active').siblings().removeClass("active");
        win.showLoading();
        $s.defaultPage=1; //默认第一页
        $s.cstate=type;
        $s.getDataList();
    }


    /*按开通招生状态筛选列表数据*/
    $s.getDataForOstate=function($event,type){
        $($event.target).addClass('active').siblings().removeClass("active");
        win.showLoading();
        $s.defaultPage=1; //默认第一页
        $s.ostate=type;
        $s.getDataList();
    }


    /*按分页条数筛选列表数据*/
    $s.getDataForPage=function(){
        win.showLoading();
        $s.defaultPage=1; //默认第一页
        $s.getDataList();
    }

    /*高级查询*/
    $s.getDataForSearch=function(){
        win.showLoading();
        $s.defaultPage=1; //默认第一页
        $s.getDataList();
    }

    /*按输入时间筛选数据列表*/
    $('#reservation').daterangepicker({format: 'YYYY/MM/DD'},
        function(start, end, label) {
            $s.startTime=$s.endTime="";
            $s.startTime = $filter('date')(start.toISOString(), 'yyyy-MM-dd');
            $s.endTime = $filter('date')(end.toISOString(), 'yyyy-MM-dd');
            win.showLoading();
            $s.defaultPage=1; //默认第一页
            $s.getDataList();
            $s.$apply();
        });

    /*按时间筛选列表数据*/
    $s.getDataForTime=function($event,type){
        $($event.target).addClass('active').siblings().removeClass("active");
        switch(type){
            case 'all':
                $s.startTime=$s.endTime="";
                break;
            case '0':
                $s.startTime = $filter('date')((new Date().getTime()-604800000), 'yyyy-MM-dd');;
                $s.endTime= $filter('date')(new Date().getTime(), 'yyyy-MM-dd');
                break;
            case '1':
                $s.startTime = $filter('date')((new Date().getTime()-1296000000), 'yyyy-MM-dd');;
                $s.endTime= $filter('date')(new Date().getTime(), 'yyyy-MM-dd');
                break;
        };
        $s.defaultPage=1; //默认第一页
        win.showLoading();
        $s.getDataList();
    }
    /** 审核通过 */
    $s.agreeCstate=function(type){
        /*检测是否选择班别*/
        if(!$rootScope.idList.length){
            Layer.alert({width:300,height:150,type:"msg",title:"请选择需要审核的班别！"});
            return false;
        };

            $.AJAX({
                url:config.basePath+"school/queryPackageById",
                type:"get",
                data:{
                    ttids:$rootScope.idList.toString(),
                },
                success:function(data){
                    var DATA=JSON.parse(data.result.pageData);
                    var boolea=true;
                    for(var i=0;i<DATA.length;i++){
                        if(DATA[i].cstate==2){  //cstate=2 已审核通过
                            boolea=false;
                            break;
                        }
                    }
                    if(boolea){
                        Layer.confirm({width:300,height:160,title:"您确认所选班别审核通过吗?",header:"审核通过"},function(){
                            $.AJAX({
                                url:config.basePath+"school/updatePackageState",
                                type:"get",
                                data:{
                                    ttids:$rootScope.idList.toString(),
                                    cstate:type
                                },
                                success:function(data){
                                    $rootScope.idList=[];
                                    Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                                    $s.getDataList();
                                }
                            });
                        });/*layer end*/
                    }else{
                        Layer.alert({width:300,height:150,type:"msg",title:"请勿重复操作"});
                    }
                }
            });

    }
    /** 审核不通过 */
    $s.refuseCstate=function(type){
        /*检测是否选择班别*/
        if(!$rootScope.idList.length){
            Layer.alert({width:300,height:150,type:"msg",title:"请选择需要审核的班别！"});
            return false;
        };
           $.AJAX({
                url:config.basePath+"school/queryPackageById",
                type:"get",
                data:{
                    ttids:$rootScope.idList.toString(),
                },
                success:function(data){
                    var DATA=JSON.parse(data.result.pageData);
                    var boolea=true;
                    var ctype=true;
                    for(var i=0;i<DATA.length;i++){
                        if(DATA[i].cstate==2){ //cstate=2 审核通过
                            boolea=false;
                            break;
                        }
                        if(DATA[i].cstate==3){  //cstate=3 审核未通过
                            ctype=false;
                            break;
                        }
                    }
                    if(!ctype){
                        Layer.alert({width:300,height:150,type:"msg",title:"请勿重复操作"});
                    }else if(boolea){
                        Layer.confirmNotByTextAlert({width:400,height:260,title:"您确认所选班别审核不通过吗?",header:"审核不过",botByText:'请填写不通过理由'},function(notByText){
                            $.AJAX({
                            url:config.basePath+"school/updatePackageState",
                            type:"get",
                            data:{
                                ttids:$rootScope.idList.toString(),
                                cstate:type,
                                refuse:notByText
                            },
                            success:function(data){
                                $rootScope.idList=[];
                                Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                                $s.getDataList();
                            }
                        });
                        });/*layer end*/
                    }else{
                        Layer.alert({width:300,height:150,type:"msg",title:"不能操作已审核通过的班别"});
                    }
                }
            });





    }
    /** 开通/停止招生 */
    $s.changeOstate=function(type){
        /*检测是否选择班别*/
        if(!$rootScope.idList.length){
            Layer.alert({width:300,height:150,type:"msg",title:"请选择需要审核的班别！"});
            return false;
        };
        /*获得选择的data数据*/
   //     var datas=getDataForKey({datas:$s.datas,id:'ttid',idList:$rootScope.idList});
        /*关闭订单*/
           $.AJAX({
                url:config.basePath+"school/queryPackageById",
                type:"get",
                data:{
                    ttids:$rootScope.idList.toString(),
                },
                success:function(data){
                    var DATA=JSON.parse(data.result.pageData);
                    var boolea=true;
                    var oType=true;
                    for(var i=0;i<DATA.length;i++){
                        if((DATA[i].ostate==1 && type=="1")  ||  (DATA[i].ostate==2 && type=="2")){
                            oType=false;
                            break;
                        }
                        if(DATA[i].cstate!=2){  //csate=2 审核通过  只有审核通过才能开通招生
                            boolea=false;
                            break;
                        }
                    }
                    if(!oType){
                        Layer.alert({width:300,height:150,type:"msg",title:"请勿重复操作"});
                    }else if(boolea || type=="1"){
                        Layer.confirm({width:300,height:160,title:"确认开通/停止招生吗？",header:"招生"},function(){
                            $.AJAX({
                            url:config.basePath+"school/updatePackageState",
                            type:"get",
                            data:{
                                ttids:$rootScope.idList.toString(),
                                ostate:type,
                            },
                            success:function(data){
                                $rootScope.idList=[];
                                Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                                $s.getDataList();
                            }
                        });
                        });/*layer end*/
                    }else{
                        Layer.alert({width:300,height:150,type:"msg",title:"未通过审核的不能开通招生"});
                    }
                }
            });

    }



}]);