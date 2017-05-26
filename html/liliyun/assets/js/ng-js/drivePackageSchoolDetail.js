/*angular for 提现*/
var app=angular.module("app",["ngFilter"]);

/*main 控制器*/
app.controller("driveSchoolPackageDetail",["$scope","$filter",function($s,$filter){
    $s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
    $s.pageSize=10;    //每页显示-显示条数
    $s.searchType="name";
    $s.name = "";
    $s.phoneNum = "";
    $s.ttid=getQueryString("ttid");
    $s.schoolId=getQueryString("schoolId");
    /*以下是将大块合并的数据细分并格式化后再显示在页面*/
    $s.priceDetailFormat="";
    $s.testConditionFormat = "";
    $s.describtionLocal = "";
    $s.describtionStrange = "";
    $s.describtionHK = "";
    $s.describtionArmy = "";
    $s.remarkFormat = "";


    /*获取数据列表并展示*/
    $s.getSignUpPackDetailsData=function(){
        $.AJAX({
            type:"get",
            url:config.basePath+"school/queryPackage",
            data:{
                ttid : $s.ttid
            },
            success:function(data){
                var DATA=getListData(data);
                var datas=DATA.dataList;
                $s.data=datas[0];
                console.log($s.data);
                //费用详情/*按+分割，分割后的数组子项再按=分割，加标签格式化数据*/
                var priceDetailArr = $s.data.priceDetail==null?"":$s.data.priceDetail.split("+");
                for(var i=0; i<priceDetailArr.length; i++){
                    var priceDetailItem = priceDetailArr[i].split("=");
                    var  priceNum=priceDetailItem[1]==undefined?"":priceDetailItem[1]+"元";
                    priceDetailArr[i] = '<p><span class="z-fl">'+ priceDetailItem[0] +'</span><span class="z-fr">'+ priceNum +'</span></p>';
                    $s.priceDetailFormat += priceDetailArr[i];
                }
                //报考条件/*原理同上*/
                var testConditionArr = $s.data.test_condition==null?"":$s.data.test_condition.split("+");
                for(var i=0; i<testConditionArr.length; i++){
                    $s.testConditionFormat += '<li>'+testConditionArr[i]+'</li>';
                }
                //报考资料/*用正则匹配出四种居民的资料段，再按同上的原理切割并格式化*/
                var describtionArr1 =  $s.data.describtion==null?"":$s.data.describtion.match(/本地居民\+(\S+)\+外地居民\+/);
                var describtionLocalArr = describtionArr1==null?"":describtionArr1[1].split("+");
                for(var i=0; i<describtionLocalArr.length; i++){
                    $s.describtionLocal += '<li>'+describtionLocalArr[i]+'</li>';
                }
                var describtionArr2 = $s.data.describtion==null?"":$s.data.describtion.match(/外地居民\+(\S+)\+港澳台\+/);
                var describtionStrangeArr = describtionArr2==null?"":describtionArr2[1].split("+");
                for(var i=0; i<describtionStrangeArr.length; i++){
                    $s.describtionStrange += '<li>'+describtionStrangeArr[i]+'</li>';
                }
                var describtionArr3 = $s.data.describtion==null?"":$s.data.describtion.match(/港澳台\+(\S+)\+现役军人\+/);
                var describtionHKArr = describtionArr3==null?"":describtionArr3[1].split("+");
                for(var i=0; i<describtionHKArr.length; i++){
                    $s.describtionHK += '<li>'+describtionHKArr[i]+'</li>';
                }
                var describtionArr4 = $s.data.describtion==null?"":$s.data.describtion.match(/现役军人\+(\S+)/);
                var describtionArmyArr = describtionArr4==null?"":describtionArr4[1].split("+");
                for(var i=0; i<describtionArmyArr.length; i++){
                    $s.describtionArmy += '<li>'+describtionArmyArr[i]+'</li>';
                }

                //售后保障
                //var tmpserviceArr = $s.data.remark.split("+");
                //for(var i=0; i<tmpserviceArr.length; i++){
                //    $s.remarkFormat += '<p>'+tmpserviceArr[i]+'</p>';
                //}



                $s.$apply();

                console.log($s.data);
                //console.log($s.data.note)
            }
        });
    };
    $s.getSignUpPackDetailsData();


    /*获取班别成员*/
    $s.getStudentData = function(){
        var json=getJson($s.defaultPage);
        $.AJAX({
            type:"get",
            data:json.data,
            url:json.url,
            success:function(data){
                var DATA=getListData(data);
                $s.total=DATA.total;
                $s.stuDatas=DATA.dataList;
                $s.$apply();
                new Page({
                    parent:$("#copot-page"),
                    nowPage:$s.defaultPage,
                    pageSize:$s.pageSize,
                    totalCount:DATA.total,
                }); //分页请求完毕
            }
        })
    }
    $s.getStudentData();

    function getJson(nowPage){
        var json={
            url:config.basePath+"student/havePackageStudent",
            data: {
                "pageNo": nowPage,
                "pageSize": parseInt($s.pageSize),
                "packageId":$s.ttid,
                "schoolId":""
            }
        };
        /*增加搜索条件*/
        json.data[$s.searchType]=$s.search;
        return json;
    };

    /*高级查询*/
    $s.getDataForSearch=function(){
        win.showLoading();
        $s.defaultPage=1; //默认第一页
        $s.getStudentData();
    }

    /*hash值改变的时候加载数据列表*/
    window.onhashchange=function(){
        win.showLoading();
        $s.defaultPage=location.hash.substring(2) || 1;
        $s.getStudentData();
    }

    /*按分页条数筛选列表数据*/
    $s.getDataForPage=function(){
        win.showLoading();
        $s.defaultPage=1; //默认第一页
        $s.getStudentData();
    }

    /*将学员从班别中删除*/
    $s.delStu = function(stuInfo){
        Layer.confirm({width:300,height:160,title:"您确认挂起所选订单吗？",header:"挂起订单"},function(){
            $.AJAX({
                url:config.basePath+"school/deletePackageStudent",
                data:{studentId:stuInfo.studentId},
                success:function(data){
                    Layer.miss({width:250,height:90,title:"删除成功",closeMask:true});
                    $s.getStudentData();
                }
            })
        });
    }


}]).filter(
    'to_trusted', ['$sce', function ($sce) {
        return function (text) {
            return $sce.trustAsHtml(text);
        }
    }]
)  ;