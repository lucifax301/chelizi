/*angular for 提现*/
var app=angular.module("app",["ngFilter"]);

/*main 控制器*/
app.controller("drivePackageSchoolEdit",["$scope","$compile","$filter",function($s,$compile,$filter){
    $s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
    $s.ttid=getQueryString("ttid");
    $s.editData={};
    /*以下是将大块合并的数据细分并格式化后再显示在页面*/
    $s.priceDetailFormat="";
    $s.testConditionFormat = "";
    $s.describtionLocal = "";
    $s.describtionStrange = "";
    $s.describtionHK = "";
    $s.describtionArmy = "";
    $s.hoursRadio="";
    $s.checkUrl=false;

    /*加载城市数据列表*/

    /*--------------------------选择城市和驾校--------------------------------------*/
    /*加载城市列表*/
    $.ajax({
        async:false,
        type:"get",
        url:config.basePath+"school/queryCity",
        data:{
            "rlevel":2,
            "pid":"",
	    shield:0
        },
        success:function(data){
            /*数据渲染页面*/
            win.hideLoading();
            $s.citys=JSON.parse(data.result.pageData);
            $s.$apply();
        }
    });
    $.ajax({
        type: 'get',
        async:false,
        url: config.basePath + "school/queryDriveSchool",
        data: {
            pageNo: 1,
            pageSize: 1000,
            cityId:100100
        },
        success: function (data) {
            /*数据渲染页面*/
            var DATA = JSON.parse(data.result.pageData);
            $s.schools = DATA.dataList;
            $s.$apply();
        }
    });
    /*加载驾校列表*/
    $s.getSchools=function(){
        if($s.editData.cityId){
            $s.checkHaveCity(); //判断是否选择城市
            $('#select-school li').last().addClass('active').siblings().removeClass("active");
            /*加载驾校列表*/
            $.AJAX({
                type:'get',
                url:config.basePath+"school/queryDriveSchool",
                data:{
                    cityId:$s.editData.cityId,   //驾校id
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
            $s.schools=""; //清空数据列表
            $s.editData.school_id="";
        }
    }
    /*判断是否选择城市*/
    $s.checkHaveCity=function(){
        $s.cityError=$s.cityId?false:true;
    }
    /*--------------------------选择城市和驾校--------------------------------------*/
    /*加载当前报名包的详情数据*/
        if($s.ttid!=null){
            $.AJAX({
                type:"get",
                url:config.basePath+"school/queryPackage",
                data:{
                    ttid : $s.ttid
                },
                success:function(data){
                    var DATA=getListData(data);
                    var datas=DATA.dataList;
                    $s.editData=datas[0];
                    if(datas[0]!=null){
                        $s.editData.price=  $s.editData.price/100;
                        $s.editData.marketPrice=  $s.editData.marketPrice/100;
                        $s.editData.hoursPrice=  $s.editData.hoursPrice/100;
                        if(datas[0].procPic!=null){
                            $("#addImg").attr("src","http://o7d94lzvx.bkt.clouddn.com/"+datas[0].procPic);
                        }
                    }
                    //console.log($s.editData);
                    //费用详情/*按+分割，分割后的数组子项再按=分割，加标签格式化数据*/
                    var priceDetailArr = $s.editData.priceDetail==null?"":$s.editData.priceDetail.split("+");

                    $s.priceDetailArrData = [];//为编辑页面自增表单项模板提供数据
                    for(var i=0; i<priceDetailArr.length; i++){
                        var priceDetailItem = priceDetailArr[i].split("=");
                        $s.priceDetailArrData.push(priceDetailItem);
                        priceDetailArr[i] = '<p><span class="z-fl">'+ priceDetailItem[0] +'</span><span class="z-fr">'+ priceDetailItem[1] +'元</span></p>';
                        $s.priceDetailFormat += priceDetailArr[i];
                    }
                    //console.log($s.priceDetailArrData)

                    //报考条件/*原理同上*/
                    var testConditionArr = $s.editData.test_condition==null?"":$s.editData.test_condition.split("+");
                    $s.testConditionArrData = [];//为编辑页面自增表单项模板提供数据
                    for(var i=0; i<testConditionArr.length; i++){
                        $s.testConditionArrData.push(testConditionArr[i]);
                        $s.testConditionFormat += '<li>'+testConditionArr[i]+'</li>';
                    }
                    //报考资料/*用正则匹配出四种居民的资料段，再按同上的原理切割并格式化*/
                    var describtionArr1 = $s.editData.describtion==null?"":$s.editData.describtion.match(/本地居民\+(\S+)\+外地居民\+/);
                    var describtionLocalArr = describtionArr1==null?"":describtionArr1[1].split("+");
                    $s.describtionLocalArrData = [];//为编辑页面自增表单项模板提供数据
                    for(var i=0; i<describtionLocalArr.length; i++){
                        $s.describtionLocalArrData.push(describtionLocalArr[i]);
                        $s.describtionLocal += '<li>'+describtionLocalArr[i]+'</li>';
                    }
                    var describtionArr2 = $s.editData.describtion==null?"":$s.editData.describtion.match(/外地居民\+(\S+)\+港澳台\+/);
                    var describtionStrangeArr = describtionArr2==null?"":describtionArr2[1].split("+");
                    $s.describtionStrangeArrData = [];//为编辑页面自增表单项模板提供数据
                    for(var i=0; i<describtionStrangeArr.length; i++){
                        $s.describtionStrangeArrData.push(describtionStrangeArr[i]);
                        $s.describtionStrange += '<li>'+describtionStrangeArr[i]+'</li>';
                    }
                    var describtionArr3 = $s.editData.describtion==null?"":$s.editData.describtion.match(/港澳台\+(\S+)\+现役军人\+/);
                    var describtionHKArr = describtionArr3==null?"":describtionArr3[1].split("+");
                    $s.describtionHKArrData = [];//为编辑页面自增表单项模板提供数据
                    for(var i=0; i<describtionHKArr.length; i++){
                        $s.describtionHKArrData.push(describtionHKArr[i]);
                        $s.describtionHK += '<li>'+describtionHKArr[i]+'</li>';
                    }
                    var describtionArr4 = $s.editData.describtion==null?"":$s.editData.describtion.match(/现役军人\+(\S+)/);
                    var describtionArmyArr = describtionArr4==null?"":describtionArr4[1].split("+");
                    $s.describtionArmyArrData = [];//为编辑页面自增表单项模板提供数据
                    for(var i=0; i<describtionArmyArr.length; i++){
                        $s.describtionArmyArrData.push(describtionArmyArr[i]);
                        $s.describtionArmy += '<li>'+describtionArmyArr[i]+'</li>';
                    }
                    $s.$apply();
                    if(datas[0]!=null && datas[0].hours==0){
                        $("#hourText").val("");
                        $("#hourText").attr("readOnly",true);
                    }
                }
            });
        }






    /*-------------------编辑报名包-------------------*/
    /*以下是几项自增表单项的模板，1.根据后台数据自增条目 2.用户点击自增条目*/
    //费用详情

    //新增费用详情
    $s.autoFormItemPriceTPL = '<div class="item-auto-item form-group row"><div class="item-auto-label col-lg-2 col-md-2 col-sm-3">' +
        '<div class="z-ico-del" ng-click="zItemDel($event)">X</div>项目费用</div>' +
        '<div class="col-lg-5 col-md-7 col-sm-6">' +
        '<input type="text" class="form-control item-auto-text1" name="priceName" placeholder="请填写项目名称" value=""></div>' +
        '<div class="col-lg-3 col-md-3 col-sm-3"><div class="form-group item-auto-text2"><div class="input-group">' +
        '<input type="text" class="form-control" placeholder="项目金额" name="priceNum" value=""><span class="input-group-addon">元</span></div>' +
        '</div></div></div>';
    $s.zItemDel = function($event){
        $($event.target).parent(".item-auto-label").parent(".item-auto-item").slideUp("normal").remove();
    }

    $s.zItemAdd = function($event){
        $($event.target).parents(".row").before($compile($s.autoFormItemPriceTPL)($s));
    }
    //新增报考条件
    $s.test_conditionFormItem = '<div class="item-auto-item form-group row">' +
        '<div class="item-auto-label col-lg-2 col-md-2 col-sm-3">' +
        '<div class="z-ico-del" ng-click="zItemDel($event)">X</div>报名条件</div>' +
        '<div class="col-lg-8 col-md-10 col-sm-9">' +
        '<input type="text" name="test_condition" class="form-control item-auto-text1" placeholder="请填写报名条件" value=""></div></div>';

    $s.test_conditionAdd = function($event){
        $($event.target).parents(".row").before($compile($s.test_conditionFormItem)($s));
    }
    //新增本地报名资料
    $s.describtionLocalFormItem = '<div class="item-auto-item form-group row">' +
        '<div class="item-auto-label col-lg-2 col-md-2 col-sm-3">' +
        '<div class="z-ico-del" ng-click="zItemDel($event)">X</div>报名资料</div>' +
        '<div class="col-lg-8 col-md-10 col-sm-9">' +
        '<input type="text" name="describtionLocal" class="form-control item-auto-text1" placeholder="请填写报名资料" value=""></div></div>';

    $s.describtionLocalAdd = function($event){
        $($event.target).parents(".row").before($compile($s.describtionLocalFormItem)($s));
    }

    //新增军人本地报名资料
    $s.describtionArmyFormItem = '<div class="item-auto-item form-group row">' +
        '<div class="item-auto-label col-lg-2 col-md-2 col-sm-3">' +
        '<div class="z-ico-del" ng-click="zItemDel($event)">X</div>报名资料</div>' +
        '<div class="col-lg-8 col-md-10 col-sm-9">' +
        '<input type="text" name="describtionArmy" class="form-control item-auto-text1" placeholder="请填写报名资料" value=""></div></div>';

    $s.describtionArmyAdd = function($event){
        $($event.target).parents(".row").before($compile($s.describtionArmyFormItem)($s));
    }
    //新增外地报名资料
    $s.describtionStrangelFormItem = '<div class="item-auto-item form-group row">' +
        '<div class="item-auto-label col-lg-2 col-md-2 col-sm-3">' +
        '<div class="z-ico-del" ng-click="zItemDel($event)">X</div>报名资料</div>' +
        '<div class="col-lg-8 col-md-10 col-sm-9">' +
        '<input type="text" name="describtionStrange" class="form-control item-auto-text1" placeholder="请填写报名资料" value=""></div></div>';

    $s.describtionStrangeAdd = function($event){
        $($event.target).parents(".row").before($compile($s.describtionStrangelFormItem)($s));
    }
    //新增港澳台报名资料
    $s.describtionHKFormItem = '<div class="item-auto-item form-group row">' +
        '<div class="item-auto-label col-lg-2 col-md-2 col-sm-3">' +
        '<div class="z-ico-del" ng-click="zItemDel($event)">X</div>报名资料</div>' +
        '<div class="col-lg-8 col-md-10 col-sm-9">' +
        '<input type="text" name="describtionHK" class="form-control item-auto-text1" placeholder="请填写报名资料" value=""></div></div>';

    $s.describtionHKAdd = function($event){
        $($event.target).parents(".row").before($compile($s.describtionHKFormItem)($s));
    }

    /*表单块收缩*/
    $s.packItemToggle = function($event){
        var icoType = $($event.target).html();
        if(icoType == "-"){$($event.target).html("+").parent("h2.z-toggle-h2").next("div").slideUp();}
        else if(icoType == "+"){$($event.target).html("-").parent("h2.z-toggle-h2").next("div").slideDown();}
        else{alert("请注意ICO的+号和-号");}
    }

    //radio选中、取消
    $("#hoursRadio").click(function(){
        var radioCheck= $("#hoursRadio").val();
        if("0"==radioCheck){
            $("#hoursRadio").attr("checked",false);
            $("#hoursRadio").val("");
            $("#hourText").attr("readOnly",false);
        }else{
            $("#hoursRadio").val("0");
            $("#hourText").val("");
            $("#hourText").attr("readOnly",true);
        }
    });

    //移除已有图片，并显示上传控件
    $s.removeExistImg = function($event){
        $($event.target).parent(".form-exist-img-wrap").remove();
    }

    //提交表单
    $s.editSave=function($event){
        var json=$s.ttid?editPackageJson("school/updatePackage"):editPackageJson("school/addPackage")
        if(!$s.editData.name || !regCombination('*').test($s.editData.name)){
            Layer.alert({width:300,height:150,type:"msg",title:"请填写班级名称"});
            return false;
        }
        if(!$s.editData.cType){
            Layer.alert({width:300,height:150,type:"msg",title:"请填写证件类型"});
            return false;
        }
        if(!$s.editData.cityId){
            Layer.alert({width:300,height:150,type:"msg",title:"请选择城市"});
            return false;
        }

        if(!$s.editData.school_id){
            Layer.alert({width:300,height:150,type:"msg",title:"请选择驾校"});
            return false;
        }
        if(!$s.editData.price || !regCombination('number').test($s.editData.price)|| $s.editData.price==0){
            Layer.alert({width:300,height:150,type:"msg",title:"请输入正确的报名价格(正整数)"});
            return false;
        }
        if(!$s.editData.marketPrice || !regCombination('number').test($s.editData.marketPrice || $s.editData.marketPrice==0)){
            Layer.alert({width:300,height:150,type:"msg",title:"请输入正确的参考原价(正整数)"});
            return false;
        }
        if(!$s.editData.hours || !regCombination('number').test($s.editData.hours) || ($s.editData.hours==0 && $("#hoursRadio").val()!="0")){
            Layer.alert({width:300,height:150,type:"msg",title:"请输入正确的学时(正整数)"});
            return false;
        }
        if($s.editData.hours>0 && !regCombination('number').test($s.editData.hoursPrice)){
            Layer.alert({width:300,height:150,type:"msg",title:"请输入正确的课时价格(正整数)"});
            return false;
        }
        if($s.editData.hoursPrice && !regCombination('number').test($s.editData.hoursPrice)){
            Layer.alert({width:300,height:150,type:"msg",title:"请输入正确的课时价格(正整数)"});
            return false;
        }

        var priceName=document.getElementsByName("priceName");
        var priceNum=document.getElementsByName("priceNum");
        for(var i=0;i<priceNum.length;i++){
            if(priceName[i].value=="" && priceNum[i].value==""){
                continue;
            }
            if(!regCombination('number').test(priceNum[i].value)|| priceNum[i].value==0){
                Layer.alert({width:350,height:150,type:"msg",title:"请输入正确的费用详情金额(正整数)"});
                return false;
            }
        }
        Layer.confirm({width:300,height:160,title:"您确认提交当前编辑的内容?",header:"确认"},function(){
            $.AJAX({
                url:json.url,
                data:json.data,
                type:'get',
                success:function(data){
                    window.location.href="drive-school-package.html";
                }

            });/*AJAX end*/
        })
    }
    /*参数配置函数*/
    function editPackageJson(url){
        var priceName=document.getElementsByName("priceName");
        var priceNum=document.getElementsByName("priceNum");
        var testCondition=document.getElementsByName("test_condition");
        var describtionLocal=document.getElementsByName("describtionLocal");
        var describtionStrange=document.getElementsByName("describtionStrange");
        var describtionHK=document.getElementsByName("describtionHK");
        var describtionArmy=document.getElementsByName("describtionArmy");
        //价格详情
        var price_detail="";
        for(var i=0;i<priceName.length;i++){
            if(priceName[i].value=="" || priceNum[i].value==""){
                continue;
            }
            if(i==0){
                price_detail=priceName[i].value+"="+priceNum[i].value;
            }else{
                price_detail+="+"+priceName[i].value+"="+priceNum[i].value;
            }

        }
        $s.editData.priceDetail=price_detail
        //报名条件
        var condition="";
        for(var i=0;i<testCondition.length;i++){
            if(i==0){
                condition=testCondition[i].value;
            }else{
                condition+="+"+testCondition[i].value;
            }

        }
        $s.editData.test_condition=condition;
        //报名资料
        var describtion_local="";
        for(var i=0;i<describtionLocal.length;i++){
            describtion_local+="+"+describtionLocal[i].value;
        }
        var describtion_strange="";
        for(var i=0;i<describtionStrange.length;i++){
            describtion_strange+="+"+describtionStrange[i].value;
        }
        var describtion_HK="";
        for(var i=0;i<describtionHK.length;i++){
            describtion_HK+="+"+describtionHK[i].value;
        }
        var describtion_army="";
        for(var i=0;i<describtionArmy.length;i++){
            describtion_army+="+"+describtionArmy[i].value;
        }
        $s.editData.describtion="本地居民"+describtion_local+"+外地居民"+describtion_strange+"+港澳台"+describtion_HK+"+现役军人"+describtion_army
        if($("#hoursRadio").val()=="0"){
            $s.editData.hours="0";
        }
        getimgKey();//上传七牛图片
        var json={
            ttNo:$s.editData.ttid,
            name:$s.editData.name,
            cType:$s.editData.cType,
            school_id:$s.editData.school_id,
            cityId:parseInt($s.editData.cityId),
            marketPrice:$s.editData.marketPrice*100,
            price:$s.editData.price*100,
            hoursPrice:$s.editData.hoursPrice*100,
            priceDetail:$s.editData.priceDetail,
            test_condition:$s.editData.test_condition,
            procPic:$s.editData.procPic,
            describtion:$s.editData.describtion,
            feature:$s.editData.feature,
            transferStyle:$s.editData.transferStyle,
            trainStyle:$s.editData.trainStyle,
            carStyle:$s.editData.carStyle,
            hours:$s.editData.hours,
            remark:$s.editData.remark,
            //icon:$s.editData.icon,
            //title:$s.editData.title,
            //protocol:$s.editData.protocol,
            //mailAddress:$s.editData.mailAddress
        };

        if($s.editType=="edit"){
            angular.extend(json,{ttid:$s.editData.ttid});
        }
        return {
            url:config.basePath+url,
            data:json
        }
    }

    /*获取七牛TOKEN并缓存*/
    if(!getCookie("qnToken")){
        $.ajax({
            type: "get",
            url:config.basePath+"resource/token",
            async: false,
            success:function(data){
                $s.imgToken = data.result.pageData;
                setCookie("qnToken",$s.imgToken,1)
            }
        });
    }
    else{
        $s.imgToken = getCookie("qnToken");
    }
    //设置cookie
    function setCookie(cname, cvalue, exhours) {
        var d = new Date();
        d.setTime(d.getTime() + (exhours*60*60*1000));
        var expires = "expires="+d.toUTCString();
        document.cookie = cname + "=" + cvalue + "; " + expires;
    }
//获取cookie
    function getCookie(cname) {
        var name = cname + "=";
        var ca = document.cookie.split(';');
        for(var i=0; i<ca.length; i++) {
            var c = ca[i];
            while (c.charAt(0)==' ') c = c.substring(1);
            if (c.indexOf(name) != -1) return c.substring(name.length, c.length);
        }
        return "";
    }

    //上传图片
    $s.uploadImg = function(type){
        // http://obqfnhv9x.bkt.clouddn.com
        var html='<div id="createImg" class="hidden"><form method="post" enctype="multipart/form-data" name="form1" id="f2">'+
            '<input type="hidden"  id="token" name="token" value="'+$s.imgToken+'" >'+
            '<input name="key" value="" id="key">'+
            '<input type="file" id="imgfile" name="file"/></form></div>';
        if(!$('#createImg').length){
            $('body').append(html);
        };
        $('#imgfile').click();
        $('#imgfile').change(function(){
            /*实时显示选择的图片*/
            var objUrl = getObjectURL(this.files[0]) ;
            $s.checkUrl=true;
            if($("#imgfile").val()){
                var filepath = $("#imgfile").val();
                var postfix = filepath.substring(filepath.lastIndexOf('.'),filepath.length)
                if(postfix !='.jpg' && postfix !='.jpeg' && postfix !='.png' && postfix !='.bmp' ){
                    $('#createImg').remove();
                    Layer.alert({width:300,height:150,type:"error",title:'格式不正确！'});
                    return;
                }

                var path = $("#imgfile").val();
                var timestamp=new Date().getTime();
                $("#key").val("wxschool_"+path.substring(path.lastIndexOf("\\")+1,path.lastIndexOf("."))+"_"+timestamp);
                /*选择相册图片后，执行如下动作（还未上传）*/
                $("#addImg").attr("src", objUrl) ;
            }

        })
    }
    /*获取选择的图片路径*/
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
    /*上传图片*/
    function getimgKey (){
        $("#token").val($s.imgToken);
        if(document.getElementById("f2")==null || !$s.checkUrl){
            return;
        }
        var form = new FormData(document.getElementById("f2"));
        $.ajax({
        //    url:"http://up.qiniu.com",
            url:"https://up.qbox.me",
            type:"post",
            data:form,
            processData:false,
            contentType:false,
            async: false,
            success:function(data){
                /*上传相册图片时，刷新列表*/
                $("#uploadButton").addClass("z-locked")
                $s.editData.procPic = data.key;
            }
        })

    }


}]).filter(
    'to_trusted', ['$sce', function ($sce) {
        return function (text) {
            return $sce.trustAsHtml(text);
        }
    }]
)  ;


