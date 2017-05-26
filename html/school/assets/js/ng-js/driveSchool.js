/*angular for 提现*/
var app=angular.module("app",["ngFilter"]);

/*main 控制器*/
app.controller("driveSchool",["$scope","$compile","$filter",function($s,$compile,$filter,$scope){
    $s.index=0;
    $s.tableSwitch=function($event){
        $s.index=tabPageDetails($event);
    }

/*-----------------------------------------查询训练场数据列表----------------------------------------------------*/

    /*加载城市数据列表*/
    $.ajax({
        type:'get',
        async:false,
        url:config.basePath+"school/queryCity",
        data:{
            "rlevel":2,
            "pid":"",
            shield:0
        },
        success:function(data){
            /*数据渲染页面*/
            //console.log(data);
            $s.citys=JSON.parse(data.result.pageData);
            $s.$apply();
        }
    });

    $s.getSchoolData = function(){
        $.AJAX({
            type:"get",
            url:config.basePath+"school/queryDriveSchoolById",
            data:{},
            success:function(data){
                $s.data=getListData(data);
                $s.images=$s.data.image.split("+");
                $s.$apply();
            }
        });
    }
    $s.getSchoolData();



    /*------------------------------------------编辑 | 新增 驾校---------------------------------------------*/

    function getTime(name){
        return name.substring(name.lastIndexOf('_')+1,name.lastIndexOf('.'));
    }

    /*时间过滤器*/
    app.filter('itemTime', ["$filter", function ($filter) {
        return function (name) {
            if (name) {
                return name.substring(name.lastIndexOf('_')+1,name.lastIndexOf('.'));
            }
        };
    }]);

    /*参数配置函数*/
    function editSchoolJson(url){

        var json={
            name:$s.data.name,
            cityId:parseInt($s.data.cityId),
            logo:$s.data.logo,
            icon:$s.data.icon,
            image:$s.data.image,
            lge:$s.data.lge,
            lae:$s.data.lae,
            schoolInfo:$s.data.schoolInfo,
            regInfo:$s.data.regInfo,
            tell:$s.data.tell,
            address:$s.data.address,
            storeCount:$s.data.storeCount,
         //   coachCount:$s.data.coachCount,
         //   carCount:$s.data.carCount,
         //   trfieldCount:$s.data.trfieldCount,
        //    packageCount:$s.data.packageCount
        };

        return {
            url:config.basePath+url,
            data:json
        }
    }

    /*修改 | 新增 驾校信息*/
    $s.submitEditMsg=function($event){
        var json=editSchoolJson("school/updateWxSchool");
        if(!$s.data.name || !regCombination('*').test($s.data.name)){
            Layer.alert({width:300,height:150,type:"msg",title:"请填写驾校名称"});
            return false;
        }
        if(!$s.data.tell){
            Layer.alert({width:300,height:150,type:"msg",title:"请填写客服电话"});
            return false;
        }
        if(!regCombination('tell').test($s.data.tell)){
            Layer.alert({width:300,height:150,type:"msg",title:"请填写正确的电话"});
            return false;
        }
        if(!$s.data.cityId){
            Layer.alert({width:300,height:150,type:"msg",title:"请选择城市"});
            return false;
        }
        if(!$s.data.address){
            Layer.alert({width:300,height:150,type:"msg",title:"请填写总部地址"});
            return false;
        }

        if(!$s.data.storeCount || !regCombination('number').test($s.data.storeCount) ||$s.data.storeCount==0){
            Layer.alert({width:300,height:150,type:"msg",title:"请填写正确的门店数(正整数)"});
            return false;
        }
        if(!$s.data.lge || !regCombination('float').test($s.data.lge)||$s.data.lge==0  ){
            Layer.alert({width:300,height:150,type:"msg",title:"请输入有效的经度"});
            return false;
        }
        if(!$s.data.lae || !regCombination('float').test($s.data.lae) || $s.data.lae==0 ){
            Layer.alert({width:300,height:150,type:"msg",title:"请输入有效的纬度"});
            return false;
        }
        if(!$s.data.schoolInfo){
            Layer.alert({width:300,height:150,type:"msg",title:"请填写驾校简介"});
            return false;
        }
        if(!$s.data.regInfo){
            Layer.alert({width:300,height:150,type:"msg",title:"请填写报名须知"});
            return false;
        }

        Layer.confirm({width:300,height:160,title:"您确认提交当前编辑的内容?",header:"确认"},function(){
            $.AJAX({
                url:json.url,
                data:json.data,
                type:'post',
                success:function(data){
                    window.location.href="drive-school.html";
                }

            });/*AJAX end*/
        })

    }

    $s.cancle=function(){
        Layer.confirm({width:300,height:160,title:"您确认放弃当前编辑的内容?",header:"取消"},function(){
            window.location.href="drive-school.html";
        })
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
    else{$s.imgToken = getCookie("qnToken");}

    //新增费用详情
    $s.itemForm=function(name) {
       var html= '<span class="school-alarm-item">' +
        '<img src="http://o7d94lzvx.bkt.clouddn.com/'+name+'" width="200px" height="150px">' +
        '<div class="ng-binding alarm-action"><button  class="btn btn-xs btn-primary" ng-click="changIcon(\''+name+'\')">设为封面</button><button  class="btn btn-xs btn-primary z-ml10" ng-click="itemDel($event)">删除</button></div>' +
        '<input type="hidden" value="'+name+'" name="image"></span>';
        return html;
    }
    $s.itemDel = function($event,item){
        $($event.target).parent(".alarm-action").parent(".school-alarm-item").remove();
        getImageData();
    }

    $s.itemAdd = function(name){
        $("#fistImage").after($compile($s.itemForm(name))($s));
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
         //   console.log("objUrl = "+objUrl) ;

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
                $("#key").val("wxschool_"+path.substring(path.lastIndexOf("\\")+1,path.lastIndexOf("."))+'_'+timestamp);
                /*选择相册图片后，执行如下动作（还未上传）*/
                if(objUrl && type=='1'){
                    $("#addImg").attr("src", objUrl) ;
                    $("#uploadButton").removeClass("z-locked")
                }
                /*选择LOGO图片后，执行如下动作（还未上传）*/
                if(objUrl && type=='2'){
                    $("#addImgLog").attr("src", objUrl) ;
                    $("#uploadButtonLog").removeClass("z-locked")
                }
                /*确定上传到七牛云*/
                $s.uploadConfirm = function(){
                    getimgKey(type);
                }
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
    function getimgKey (type){
        $("#token").val($s.imgToken);
        var form = new FormData(document.getElementById("f2"));
        $.ajax({
           // url:"http://up.qiniu.com",
            url:"https://up.qbox.me",
            type:"post",
            data:form,
            processData:false,
            contentType:false,
            async: false,
            success:function(data){
                /*上传相册图片时，刷新列表*/
                if(type==1){
                 //   $s.getSchoolData();
                 //   $s.imgKey.push(data.key);
                    $("#uploadButton").addClass("z-locked")
                    $s.itemAdd(data.key);
                    getImageData();
                    $("#addImg").attr("src", "assets/img/image.png") ;
                }
                /*编辑LOGO时，提供缩略图预览*/
                else if(type==2){
                    $("#uploadButtonLog").addClass("z-locked")
                    $s.data.logo=data.key;
                }

            }
        })

    }

   $s.changIcon=function(icon){
       $s.data.icon=icon;
   }

    function getImageData(){
        var images=document.getElementsByName("image");
        var image="";
        for(var i=0;i<images.length;i++){
            if(i==0){
                image=images[i].value;
            }else{
                image+="+"+images[i].value;
            }
        }
        $s.data.image=image;
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





}]);