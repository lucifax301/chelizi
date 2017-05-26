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
    /*查询城市列表*/
        //$.AJAX({
        //    type:"get",
        //    url:config.basePath+"school/queryCity",
        //    data:{
        //        "rlevel":2,
        //        "pid":"",
        //    },
        //    success:function(data){
        //        $s.citys=JSON.parse(data.result.pageData);
        //        $s.$apply();
        //    }
        //});
    /*--------------------------选择城市和驾校--------------------------------------*/


    /*按所在学校筛选列表数据*/
    //$s.getDataForSchool=function(){
    //    win.showLoading();
    //    $s.defaultPage=1; //默认第一页
    //    $s.school_id=$("#getDataForSchoolSel").val();
    //    $s.getDataList();
    //}

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
    //$s.agreeCstate=function(type){
    //    /*检测是否选择班别*/
    //    if(!$rootScope.idList.length){
    //        Layer.alert({width:300,height:150,type:"msg",title:"请选择需要审核的班别！"});
    //        return false;
    //    };
    //    Layer.confirm({width:300,height:160,title:"您确认所选班别审核通过吗?",header:"审核通过"},function(){
    //        $.AJAX({
    //            url:config.basePath+"school/updatePackageState",
    //            type:"get",
    //            data:{
    //                ttids:$rootScope.idList.toString(),
    //                cstate:type
    //            },
    //            success:function(data){
    //                $rootScope.idList=[];
    //                Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
    //                $s.getDataList();
    //            }
    //        });
    //    });/*layer end*/
    //}
    /** 审核不通过 */
    //$s.refuseCstate=function(type){
    //    /*检测是否选择班别*/
    //    if(!$rootScope.idList.length){
    //        Layer.alert({width:300,height:150,type:"msg",title:"请选择需要审核的班别！"});
    //        return false;
    //    };
    //    Layer.confirmNotByTextAlert({width:400,height:260,title:"您确认所选班别审核不通过吗?",header:"审核不过",botByText:'请填写不通过理由'},function(notByText){
    //        $.AJAX({
    //            url:config.basePath+"school/updatePackageState",
    //            type:"get",
    //            data:{
    //                ttids:$rootScope.idList.toString(),
    //                cstate:type,
    //                remark:notByText
    //            },
    //            success:function(data){
    //                $rootScope.idList=[];
    //                Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
    //                $s.getDataList();
    //            }
    //        });
    //    });/*layer end*/
    //}
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

    /*------------------------------------------编辑 | 新增 班级---------------------------------------------*/

    /*include 加载完成后执行*/
    //$s.dirveSchoolEditLoad=function(){
    //    /*点击按钮关闭弹出层*/
    //    $(".closeAlert").click(function(){
    //        $(this).parents("div.school-alert").fadeOut("fast");
    //    })
    //}
    //
    ///*点击 编辑|新增 训练场信息*/
    //$s.editData={};
    //$s.editType="add";
    //$s.packageEdit=function(type,data){
    //    $(".school-alert").fadeIn("fast");
    //    /*弹出层垂直居中*/
    //    $("#edit-content").css("marginTop",parseInt(($(win).height()- $("#edit-content").height()-100)/2)+"px");
    //    if(type=="edit"){
    //        /*修改*/
    //        $s.editData = data;
    //        $s.editType="edit";
    //    }else{
    //        /*新增*/
    //        $s.editData={};
    //        $s.editType="add";
    //    }
    //}
    //
    ///*参数配置函数*/
    //function editPackageJson(url){
    //    var json={
    //        ttNo:$s.editData.ttid,
    //        name:$s.editData.name,
    //        school_id:$s.editData.school_id,
    //        cityId:parseInt($s.editData.cityId),
    //        marketPrice:$s.editData.marketPrice,
    //        price:$s.editData.price,
    //        icon:$s.editData.icon,
    //        title:$s.editData.title,
    //        protocol:$s.editData.protocol,
    //        priceDetail:$s.editData.priceDetail,
    //        test_condition:$s.editData.test_condition,
    //        procPic:$s.editData.procPic,
    //        describtion:$s.editData.describtion,
    //        feature:$s.editData.feature,
    //        transferStyle:$s.editData.transferStyle,
    //        trainStyle:$s.editData.trainStyle,
    //        carStyle:$s.editData.carStyle,
    //        hourse:$s.editData.hourse,
    //        remark:$s.editData.remark,
    //        mailAddress:$s.editData.mailAddress
    //    };
    //
    //    if($s.editType=="edit"){
    //        angular.extend(json,{ttid:$s.editData.ttid});
    //    }
    //    return {
    //        url:config.basePath+url,
    //        data:json
    //    }
    //}
    //
    ///*修改 | 新增 班级信息*/
    //$s.submitEditMsg=function($event){
    //    $s.checkSchoolSameName = 0;
    //    var json=$s.editType=="add"?editPackageJson("school/addPackage"):editPackageJson("school/updatePackage");
    //    if(!$s.editData.name || !regCombination('*').test($s.editData.name)){
    //        Layer.alert({width:300,height:150,type:"msg",title:"请填写班级名称"});
    //        return false;
    //    }
    //    if(!$s.editData.cityId){
    //        Layer.alert({width:300,height:150,type:"msg",title:"请选择城市"});
    //        return false;
    //    }
    //
    //
    //    if(!$s.editData.icon){
    //        Layer.alert({width:300,height:150,type:"msg",title:"请输入图标"});
    //        return false;
    //    }
    //    if(!$s.editData.marketPrice || !regCombination('number').test($s.editData.marketPrice)){
    //        Layer.alert({width:300,height:150,type:"msg",title:"请输入正确的市场价(正整数)"});
    //        return false;
    //    }
    //    if(!$s.editData.price || !regCombination('number').test($s.editData.price)){
    //        Layer.alert({width:300,height:150,type:"msg",title:"请输入正确的报名总价(正整数)"});
    //        return false;
    //    }
    //    if(!$s.editData.protocol){
    //        Layer.alert({width:300,height:150,type:"msg",title:"请输入报名协议"});
    //        return false;
    //    }
    //    if(!$s.editData.title){
    //        Layer.alert({width:300,height:150,type:"msg",title:"请输入摘要"});
    //        return false;
    //    }
    //
    // //   checkDataList({name:$s.editData.name},"school/queryPackage");
    //
    //    if($s.checkSchoolSameName > 0){
    //        Layer.alert({width:300,height:150,type:"msg",title:"该班级已存在"});
    //        return false;
    //    }
    //
    //    $.AJAX({
    //        url:json.url,
    //        data:json.data,
    //        type:'get',
    //        success:function(data){
    //            /*关闭弹出层*/
    //            $($event.target).parents("div.school-alert").fadeOut("fast");
    //            /*更新列表*/
    //            $s.getDataList();
    //        }
    //    });/*AJAX end*/
    //
    //    /*检查重名*/
    //    function checkDataList(checkData,queryUrl){
    //        //判断班级重复
    //
    //        $.AJAX({
    //            url:config.basePath+queryUrl,
    //            type:'get',
    //            async:false,
    //            data:checkData,
    //            timeout:1000000,
    //            success:function(data){
    //                var DATA = JSON.parse(data.result.pageData).dataList;
    //                //var dataNum = ;
    //                //console.log("查出同名的班级数量："+dataNum+",更新方式："+$s.editType)
    //                //新增时直接检查是否存在重名项
    //                if(DATA.length>0 && $s.editType=="add"){
    //                    $s.checkSchoolSameName = 1;
    //                }
    //                //编辑时检查现有重名项的ID与当前编辑项ID是否一致，如不一致，则不通过
    //                if(DATA.length>1 && $s.editType=="edit"){
    //                    alert("编辑，多项同名直接阻止")
    //                    //console.log("编辑时的数据"+DATA+",查出来的数据条目："+DATA.length);
    //                    $s.checkSchoolSameName = 1;
    //                }
    //                if(DATA.length=1 && $s.editType=="edit" && DATA[0].school_id && DATA[0].school_id != $s.editData.school_id){
    //                    //console.log(DATA);
    //                    //console.log("编辑，仅一项同名且ID不一样，查到的已有ID为："+ DATA[0].school_id+",当前编辑的ID为："+$s.editData.school_id)
    //                    //console.log("编辑时的数据"+DATA+",查出来的数据条目："+DATA.length);
    //                    $s.checkSchoolSameName = 1;
    //                }
    //                //console.log(DATA+"DATA的长度为"+DATA.length+",dataNum为："+DATA.length);
    //
    //                //$s.$apply();
    //            }
    //        })
    //    }
    //
    //}



}]);