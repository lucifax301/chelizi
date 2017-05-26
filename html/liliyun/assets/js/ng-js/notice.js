/*angular for 通知*/
var app = angular.module("app", ["ngFilter", "ngSelects"]);

/*main 控制器*/
app.controller("Notice", ["$scope","$compile", "$rootScope", "$filter", "Selects", function ($s,$compile, $rootScope, $filter, Selects) {
    $s.defaultPage = location.hash.substring(2) || 1;  //默认请求页
    $s.pageSize = 10;    //每页显示-显示条数
    $s.type = "";  //公告类别（0-今日重点播报、1-优惠活动、2-订单消息 3-喱喱头条、4-我的消息）
    $s.userType = "10";
    $s.title = "";
    $s.isdel = "";
    $s.checkUrl=false;
    $s.uploadStae="success"; //判断图片上传成功是否
    $s.time = new Date("1999/01/01");
    $s.etime = new Date("2999/01/01");
   // $s.stime = new Date();
    $s.editType=getQueryString("editType");
    $s.editNoticeId=getQueryString("editNoticeId");
    $s.saveType=getQueryString("saveType");
    $s.view=getQueryString("view");
    $s.setType=getQueryString("setType")==null?10:getQueryString("setType");   //10 mcs学员端配置 9 cms教练端配置
    /*模拟数据*/
    $s.data = {
        pages: 10,
        total: 100,
        pageSize: 10,
        pageNo: 1,
        dataList: [
            {
                noticeId: 2,
                title: "通知",
                content: "通知内容",
                time: "2016-08-03 16:54:28",
                extra: "http://baidu.com",
                userId: 47707364
            },

        ]
    };
    //或得的数据列表
    $s.datas = $s.data.dataList;

    /*获取数据列表并展示*/
    $s.getDataList = function () {
        var json = getJson($s.defaultPage);
        $.AJAX({
            type: "post",
            url: json.url,
            data: json.data,
            success: function (data) {
                var DATA = getListData(data);
                $s.total = DATA.total;
                /*数据渲染页面*/
                $s.datas = DATA.dataList;
                /*全选与取消全选*/
                Selects.selects({datas: $s.datas, whichId: 'noticeId', num: ''});
                $s.$apply();
                //分页请求
                new Page({
                    parent: $("#copot-page"),
                    nowPage: $s.defaultPage,
                    pageSize: $s.pageSize,
                    totalCount: $s.total,
                }); //分页请求完毕
            }
        });
    };
    $s.getDataList();

    /*参数配置函数*/
    function getJson(nowPage) {
        var json = {
            url: config.basePath + "notice/getNotice",
            data: {
                "pageNo": nowPage,
                "pageSize": parseInt($s.pageSize),
                "type": $s.type,
                "userType":$s.setType,
                "isdel": $s.isdel,
                "title": $s.title,
                "time": $s.time,
                "etime": $s.etime
            }
        };
        /*增加搜索条件*/
        //	json.data[$s.searchType]=$s.search;
        return json;
    };

    /*hash值改变的时候加载数据列表*/
    window.onhashchange=function(){
    	win.showLoading();
    	$s.defaultPage=location.hash.substring(2) || 1;
    	$s.getDataList();
    	//$s.getCoachData("","");
    }

    /*按通知对象类别状态筛选列表数据*/
    $s.getUserTypeStatus = function ($event, userType) {
        $($event.target).addClass('active').siblings().removeClass("active");
        win.showLoading();
        location.hash="##1";
        location.search="setType="+userType;
        $s.defaultPage = 1; //默认第一页
        $s.rest();
        $s.userType = userType;
        $s.setType = userType;
        $s.getDataList();
    }

    /*按分页条数筛选列表数据*/
    $s.getDataForPage = function () {
        win.showLoading();
        $s.defaultPage = 1; //默认第一页
        $s.getDataList();
    }

    $s.searchData = function () {
        var title = $("#searchTitle").val();
        var isdel = $("#searchIsdel").val();
        var type = $("#type").val();
        var time = $("#reservation").val() == "" ? "1999/01/01" : $("#reservation").val();
        var etime = $("#reservation2").val() == "" ? "2999/01/01" : $("#reservation2").val();
        win.showLoading();
        $s.defaultPage = 1; //默认第一页
        $s.title = title;
        $s.type = type;
        $s.isdel = isdel;
        $s.time = new Date(time.replace(/-/g, "/"));  //兼容Safari浏览器
        $s.etime = new Date(etime.replace(/-/g, "/"));
        $s.getDataList();
    }
    $s.rest = function () {
        $("#searchTitle").val("");
        $("#searchIsdel").val("");
        $("#type").val("");
        $("#reservation").val("");
        $("#reservation2").val("");
    }


    /*------------------------------ 教练信息查询---------------------------------------*/
    /*获取数据列表并展示*/
    $s.getCoachDataList = function (name, phoneNum) {
        $.AJAX({
            type: "get",
            url: config.basePath + "coach/batch",
            data: {
                "pageNo": 1,
                "pageSize": 10,
                "name": name,
                "phoneNum": phoneNum
            },
            success: function (data) {
                var DATA = getListData(data);
                $s.total = DATA.total;
                /*数据渲染页面*/
                $s.coachDatas = DATA.dataList;
                /*全选与取消全选*/
                Selects.selects({datas: $s.coachDatas, whichId: 'coachId', num: ''});
                $s.$apply();
            }
        });
    };
    $s.coachSearch = function () {
        var coachName = $("#coachName").val();
        var coachPhone = $("#coachPhone").val();
        $s.getCoachDataList(coachName, coachPhone);
    }
    $s.coachRest = function () {
        $("#coachName").val("");
        $("#coachPhone").val("");
    }
    $s.coachSearchCancle = function () {
        $("div.edit-notice-searchCoach").fadeOut("fast");
    }

    $s.searchCoach = function () {
        $s.getCoachDataList("", "")
        $(".edit-notice-searchCoach").fadeIn("fast");
        $("#edit-notice-searchCoach").css("marginTop", parseInt(($(win).height() - $("#edit-notice").height() - 100) / 10) + "px");
    }
    $s.choseCoach = function () {
        if (!$rootScope.idList.length) {
            Layer.alert({width: 300, height: 150, type: "msg", title: "请选择教练！"});
            return false;
        }
        var userIdStrs="";
        var userName="";
        var datas=getDataForKey({datas:$s.coachDatas,id:'coachId',idList:$rootScope.idList});
        var name = getKeyArrFromData(datas,'name');
        for(var i=0;i<$rootScope.idList.length;i++){
            if(i==0){
                userIdStrs=$rootScope.idList[i];
            }else{
                userIdStrs+=","+$rootScope.idList[i];
            }
            $s.itemAdd($rootScope.idList[i],name[i])
        }
        for(var i=0;i<name.length;i++){
            var resultName=name[i];
            if(i==0){
                userName=resultName;
            }else{
                userName+=","+resultName;
            }
        }

        $s.editData.userIdStrs = userIdStrs;
        $s.editData.userName = userName;
        $("div.edit-notice-searchCoach").fadeOut("fast");
    }
    /*------------------------------ 教练信息查询--结束-------------------------------------*/

    /*------------------------------ 学员信息查询---------------------------------------*/
    /*获取数据列表并展示*/
    $s.getStudentDataList = function (name, phoneNum) {
        $.AJAX({
            type: "get",
            url: config.basePath + "student/lili-batch",
            data: {
                "pageNo": 1,
                "pageSize": 10,
                "name": name,
                "phoneNum": phoneNum
            },
            success: function (data) {
                var DATA = getListData(data);
                $s.total = DATA.total;
                /*数据渲染页面*/
                $s.studentDatas = DATA.dataList;
                /*全选与取消全选*/
                Selects.selects({datas: $s.studentDatas, whichId: 'studentId', num: ''});
                $s.$apply();

            }
        });
    };
    $s.studentSearch = function () {
        var studentName = $("#studentName").val();
        var studentPhone = $("#studentPhone").val();
        $s.getStudentDataList(studentName, studentPhone);
    }
    $s.studentRest = function () {
        $("#studentName").val("");
        $("#studentPhone").val("");
    }
    $s.studentSearchCancle = function () {
        $("div.edit-notice-searchStudent").fadeOut("fast");
    }

    $s.searchStudent = function () {
        $s.getStudentDataList("", "")
        $(".edit-notice-searchStudent").fadeIn("fast");
        $("#edit-notice-searchStudent").css("marginTop", parseInt(($(win).height() - $("#edit-notice").height() - 100) / 10) + "px");
    }
    $s.choseStudent = function () {
        if (!$rootScope.idList.length) {
            Layer.alert({width: 300, height: 150, type: "msg", title: "请选择学员！"});
            return false;
        }

        var userIdStrs="";
        var userName="";
        var datas=getDataForKey({datas:$s.studentDatas,id:'studentId',idList:$rootScope.idList});
        var name = getKeyArrFromData(datas,'name');
        for(var i=0;i<$rootScope.idList.length;i++){
            if(i==0){
                userIdStrs=$rootScope.idList[i];
            }else{
                userIdStrs+=","+$rootScope.idList[i];
            }
            $s.itemAdd($rootScope.idList[i],name[i]==null?"喱喱同学":name[i])
        }
        for(var i=0;i<name.length;i++){
            var resultName=name[i]==null?"喱喱同学":name[i];
            if(i==0){
                userName=resultName;
            }else{
                userName+=","+resultName;
            }
        }

        $s.editData.userIdStrs = userIdStrs;
        $s.editData.userName = userName;
        $("div.edit-notice-searchStudent").fadeOut("fast");
    }
    $s.itemAdd = function(userId,name){
        $("#userIds").append($compile($s.itemForm(userId,name))($s));
    }
    $s.itemForm=function(userId,name) {
        var html= '<span class="item" name="userIds" id="'+userId+'"><span>'+name+'</span><i ng-click="deleteObj(\''+userId+'\')" ></i></span>';
        return html;
    }
    /*------------------------------ 学员信息查询--结束-------------------------------------*/

    /*------------------------------ 驾校信息查询---------------------------------------*/
    /*获取数据列表并展示*/

    /*加载城市列表*/
    queryCity({
        callback: function (data) {
            $s.citys = data;
            $s.$apply();
        }
    });
    $s.getSchoolDataList = function (name, cityId) {
        $.AJAX({
            type: "get",
            url: config.basePath + "school/querySchool",
            data: {
                "pageNo": 1,
                "pageSize": 10,
                "cityId": cityId,
                "name": name
            },
            success: function (data) {
                var DATA = getListData(data);
                $s.total = DATA.total;
                /*数据渲染页面*/
                $s.schoolDatas = DATA.dataList;
                /*全选与取消全选*/
                Selects.selects({datas: $s.schoolDatas, whichId: 'schoolId', num: ''});
                $s.$apply();

            }
        });
    };
    $s.schoolSearch = function () {
        var schoolName = $("#schoolName").val();
        var cityId = $("#schoolCityId").val();
        $s.getSchoolDataList(schoolName, cityId);
    }
    $s.schoolRest = function () {
        $("#schoolName").val("");
        $("#schoolCityId").val("");
    }
    $s.schoolSearchCancle = function () {
        $("div.edit-notice-searchSchool").fadeOut("fast");
    }

    $s.searchSchool = function () {
        $s.getSchoolDataList("", "")
        $(".edit-notice-searchSchool").fadeIn("fast");
        $("#edit-notice-searchSchool").css("marginTop", parseInt(($(win).height() - $("#edit-notice").height() - 100) / 10) + "px");
    }
    $s.choseSchool = function () {
        if (!$rootScope.idList.length) {
            Layer.alert({width: 300, height: 150, type: "msg", title: "请选择一个驾校！"});
            return false;
        }

        var schoolId = "";
        var schoolName="";
        var datas=getDataForKey({datas:$s.schoolDatas,id:'schoolId',idList:$rootScope.idList});
        var schoolNames = getKeyArrFromData(datas,'name');
        for(var i=0;i<$rootScope.idList.length;i++){
            if(i==0){
                schoolId=$rootScope.idList[i];
            }else{
                schoolId+=","+$rootScope.idList[i];
            }
            $s.schoolAdd($rootScope.idList[i],schoolNames[i])
        }
        for(var i=0;i<schoolNames.length;i++){
            if(i==0){
                schoolName=schoolNames[i];
            }else{
                schoolName+=","+schoolNames[i];
            }
        }

        $s.editData.schoolId = schoolId;
        $s.editData.schoolName = schoolName;
        //$s.$apply();
        $("div.edit-notice-searchSchool").fadeOut("fast");
    }
    $s.schoolAdd = function(schoolId,name){
        $("#schoolIds").append($compile($s.schoolForm(schoolId,name))($s));
    }
    $s.schoolForm=function(schoolId,name) {
        var html='<span class="item" id="'+schoolId+'" name="schoolIds"><span>'+name+'</span><i ng-click="deleteObj(\''+schoolId+'\')" ></i></span>'
        return html;
    }
    /*------------------------------ 驾校信息查询--结束-------------------------------------*/
    $s.getCityId = function () {
        $s.editData.userId = $("#showCity").val();
    }

    /*-------------------------------通知 新增|修改 弹出层操作---------------------------------------*/
    /*include 加载完成后执行*/
    $s.noticeEditLoad = function () {
        /*点击按钮关闭弹出层*/

        $(".coachCloseAlert").click(function () {
            $(this).parents("div.edit-notice-searchCoach").fadeOut("fast");
        })
        $(".studentCloseAlert").click(function () {
            $(this).parents("div.edit-notice-searchStudent").fadeOut("fast");
        })
        $(".schoolCloseAlert").click(function () {
            $(this).parents("div.edit-notice-searchSchool").fadeOut("fast");
        })

    }

    /*时间输入控件*/
    $('#reservation').daterangepicker({
        'format': 'YYYY-MM-DD',
        'singleDatePicker': true,
        'autoclose': true,
        "timePicker": true,
        "timePicker24Hour": true,
        "timePickerSeconds": true,
        "autoApply": true,
    });
    $('#reservation2').daterangepicker({
        'format': 'YYYY-MM-DD',
        'singleDatePicker': true,
        'autoclose': true,
        "timePicker": true,
        "timePicker24Hour": true,
        "timePickerSeconds": true,
        "autoApply": true,
    });
    $('#reservation').val("");
    $('#reservation2').val("");
    /*点击 编辑|新增 通知信息*/

    /***********************************编辑、设施发布时读取编辑数据***************************************/
    if($s.editType=="edit"){
        $.AJAX({
            type: "get",
            url: config.basePath + "notice/getNoticeById",
            data: {noticeId:$s.editNoticeId},
            success: function (data) {
                var data = getListData(data);
                $s.editData=data;
                if(data.pic!=null && data.pic!=""){
                    $("#addImg").attr("src","http://o7d94lzvx.bkt.clouddn.com/"+data.pic);
                    $("#imgHide").attr("src","http://o7d94lzvx.bkt.clouddn.com/"+data.pic);
                }
                if(data.content!=null && data.content!="" && $("#contentValue").val()!=undefined){
                    UE.getEditor('myEditor').ready(function() {
                        UE.getEditor('myEditor').setContent(data.content);
                    });
                }

               //城市默认选中
                var cityIds=data.cityId;
                if(cityIds!=null && cityIds!=""){
                    var cityIdBox=document.getElementsByName("cityId");
                    for(var i=0;i<cityIdBox.length;i++){
                        if(cityIds.indexOf(cityIdBox[i].value)>=0){
                            cityIdBox[i].checked=true;
                        }else{
                            cityIdBox[i].checked=false;
                        }
                    }
                }
                //学员进度默认选中
                var applyexam=data.applyexam;
                if(applyexam!=null && applyexam!=""){
                    var applyexamBox=document.getElementsByName("applyexam");
                    for(var i=0;i<applyexamBox.length;i++){
                        if(applyexam.indexOf("|"+applyexamBox[i].value+"|")>=0){
                            applyexamBox[i].checked=true;
                        }else{
                            applyexamBox[i].checked=false;
                        }
                    }
                }
                //学员类型默认选中
                var utype=data.utype;
                if(utype!=null && utype!=""){
                    var utypeBox=document.getElementsByName("utype");
                    for(var i=0;i<utypeBox.length;i++){
                        if(utype.indexOf(utypeBox[i].value)>=0){
                            utypeBox[i].checked=true;
                        }else{
                            utypeBox[i].checked=false;
                        }
                    }
                }
                //学校显示
                var schoolId=data.schoolId;
                var schoolName=data.schoolName;
                if(schoolId!=null && schoolId!=""){
                    schoolId=schoolId.substring(1,schoolId.length-1);
                    var argId=schoolId.split(",");
                    var argName=schoolName.split(",");
                    for(var i=0;i<argId.length;i++){
                        $s.schoolAdd(argId[i],argName[i])
                    }
                }
                //学员、教练显示
                var userIdStrs=data.userIdStrs;
                var userName=data.userName;
                if(userIdStrs!=null && userIdStrs!=""){
                    userIdStrs=userIdStrs.substring(1,userIdStrs.length-1);
                    var argId=userIdStrs.split(",");
                    var argName=userName.split(",");
                    for(var i=0;i<argId.length;i++){
                        $s.itemAdd(argId[i],argName[i])
                    }
                }
                $s.$apply();
            }
        });
    }else{
        $s.editData = {};
    }
    /***********************************编辑、设施发布时读取编辑数据 结束***************************************/


    /**************************************参内容数配置函数**************************************************/
    function editNoticeJson(url) {
        var json = {
            title: $s.editData.title,
            digest: $s.editData.digest,
            content: $s.editData.content,
            type: $s.editData.type,
            pic: $s.editData.pic,
            userType: $s.editData.userType,
            isdel: $s.editData.isdel,
            utype: $s.editData.utype,
            applyexam: $s.editData.applyexam,
            cityId: $s.editData.cityId,
            schoolId: $s.editData.schoolId,
            schoolName: $s.editData.schoolName,
            userIdStrs: $s.editData.userIdStrs,
            userName: $s.editData.userName,
            sendMessage: $s.editData.sendMessage,
        };
        if ($s.editType == "edit") {
            angular.extend(json, {noticeId: $s.editData.noticeId});
        }
        return {
            url: config.basePath + url,
            data: json
        }
    }

    /***************************************内容设置保存*********************************************************************/
    $s.submitEditMsg = function ($event) {
        if($s.setType==9){
            $s.editData.userType=9;
        }else if($s.setType==10){
            $s.editData.userType=10;
        }
        $s.editData.isdel=2;
        $s.editData.content=UE.getEditor("myEditor").getContent();
        getimgKey();
	if($s.uploadStae=="failure"){
            Layer.alert({width: 300, height: 150, type: "msg", title: "页面超时,请刷新页面"});
            return false;
        }
        var json = $s.editType == "add" ? editNoticeJson("notice/add") : editNoticeJson("notice/update");


        if (!$s.editData.type) {
            Layer.alert({width: 300, height: 150, type: "msg", title: "请选择分类"});
            return false;
        }
        if (!$s.editData.pic) {
            Layer.alert({width: 300, height: 150, type: "msg", title: "请上传头图"});
            return false;
        }
        var width=$("#imgHide").width()
        var height=$("#imgHide").height();
        if($s.editData.type==3 && $s.setType==10 && (width>940 || width<938 || height>481 ||  height<479)){
            Layer.alert({width: 480, height: 150, type: "msg", title: "头图尺寸不符要求,请上传喱喱头图尺寸为：939*480"});
            return false;
        }
        if($s.editData.type!=3 && $s.setType==10 && (width>175 || width<173 || height>175 || height<173)){
            Layer.alert({width: 450, height: 150, type: "msg", title: "头图尺寸不符要求,请上传头图尺寸为：174*174"});
            return false;
        }
        if($s.setType==9 && (width>751 || width<749 || height>321 || height<319)){
            Layer.alert({width: 450, height: 150, type: "msg", title: "头图尺寸不符要求,请上传头图尺寸为：750*320"});
            return false;
        }

        if (!$s.editData.title || !regCombination('*').test($s.editData.title)) {
            Layer.alert({width: 300, height: 150, type: "msg", title: "请填写通知标题"});
            return false;
        }
        if (!$s.editData.digest) {
            Layer.alert({width: 300, height: 150, type: "msg", title: "请填写摘要"});
            return false;
        }
        if (!$s.editData.content) {
            Layer.alert({width: 300, height: 150, type: "msg", title: "请填写内容"});
            return false;
        }

        $.ajax({
            url: json.url,
            type:"POST",
            data: json.data,
            timeout:10000,
            success: function (data) {
                 var noticeId=$s.editNoticeId;
                 if($s.editType=="add"){
                     noticeId=getListData(data)
                 }
                 if($s.editData.type==3){
                     window.location.href="notice.html";
                 }else if($s.setType==10){
                     window.location.href="notice-add-student.html?editType=edit&setType=10&editNoticeId="+noticeId;
                 }else  if($s.setType==9){
                     window.location.href="notice-add-coach.html?editType=edit&setType=9&editNoticeId="+noticeId;
                 }

            }
        });
        /*AJAX end*/
    }

    /***************************************发布设置保存*********************************************************************/

    $s.submitEditPub = function ($event,type) {
        //城市复选框
        var cityIdBox=document.getElementsByName("cityId");
        var cityId;
        var checkedNum=0;
        for(var i=0;i<cityIdBox.length;i++){
            if(cityIdBox[i].checked){
               if(checkedNum==0){
                   cityId=cityIdBox[i].value;
               } else{
                   cityId+=","+cityIdBox[i].value;
               }
                checkedNum++;
            }
        }
        $s.editData.cityId=cityId;

        //用户类型复选框
        var utypeBox=document.getElementsByName("utype");
        var utype;
        checkedNum=0;
        for(var i=0;i<utypeBox.length;i++){
            if(utypeBox[i].checked){
                if( checkedNum==0){
                    utype=utypeBox[i].value;
                } else{
                    utype+=","+utypeBox[i].value;
                }
                checkedNum++;
            }

        }
        $s.editData.utype=utype;
        //用户类型复选框
        var applyexamBox=document.getElementsByName("applyexam");
        var applyexam;
        checkedNum=0;
        for(var i=0;i<applyexamBox.length;i++){
            if(applyexamBox[i].checked){
                if( checkedNum==0){
                    applyexam="|"+applyexamBox[i].value+"|";
                } else{
                    applyexam+=applyexamBox[i].value+"|";
                }
                checkedNum++;
            }

        }
        $s.editData.applyexam=applyexam;
    //    alert(applyexam)
        $s.editData.sendMessage=$('input:radio:checked').val();
        $s.editData.isdel=type

        $s.getStudentAndSchoolData();
        var json = editNoticeJson("notice/update");
        $.ajax({
            url: json.url,
            type:"POST",
            timeout:10000,
            data:json.data,
            success: function (data) {
                window.location.href="notice.html?setType="+$s.setType;
                $s.getDataList();
            }
        });
        /*AJAX end*/
    }
    /***************************************发布设置保存  结束*********************************************************************/


    //发布撤回
    $s.changeIsdel = function (noticeId, state,type) {
        //状态：0-已发布；1-已删除 2-草稿 3-已撤回
        if(state==1){
            Layer.confirm({width:300,height:160,title:"您确认删除该条记录?",header:"删除确认"},function() {
                $.AJAX({
                    type:"GET",
                    url: config.basePath + "notice/updateState",
                    data: {
                        noticeId: noticeId,
                        isdel: state,
                        type:type

                    },
                    success: function (data) {
                        /*关闭弹出层*/
                        Layer.miss({width: 250, height: 90, title: "删除成功", closeMask: true});
                        /*更新列表*/
                        $s.getDataList();
                    }
                });
            });
        }else{
            $.AJAX({
                type:"GET",
                url: config.basePath + "notice/updateState",
                data: {
                    noticeId: noticeId,
                    isdel: state,
                    type:type
                },
                success: function (data) {
                    /*关闭弹出层*/
                    Layer.miss({width: 250, height: 90, title: "操作成功", closeMask: true});
                    /*更新列表*/
                    $s.getDataList();
                }
            });
        }

        /*AJAX end*/
    }
    //获取选中学员、教练或驾校userId
    $s.getStudentAndSchoolData=function(){
        //获取选中驾校数据
        var schoolIds=document.getElementsByName("schoolIds");
        var schoolId;
        var schoolName;
        for(var i=0;i<schoolIds.length;i++){
            if(i==0){
                schoolId=","+schoolIds[i].id+",";
                schoolName=schoolIds[i].getElementsByTagName("span")[0].innerHTML;
            }else{
                if(schoolId.indexOf(","+schoolIds[i].id+",")>=0){
                    continue;
                }
                schoolId+=schoolIds[i].id+",";
                schoolName+=","+schoolIds[i].getElementsByTagName("span")[0].innerHTML;
            }
        }
        $s.editData.schoolId = schoolId;
        $s.editData.schoolName = schoolName;
       //获取选中学员、教练数据
        var userIds=document.getElementsByName("userIds");
        var userIdStrs;
        var userName;
        for(var i=0;i<userIds.length;i++){
            if(i==0){
                userIdStrs=","+userIds[i].id+",";

                userName=userIds[i].getElementsByTagName("span")[0].innerHTML;
            }else{
                if(userIdStrs.indexOf(","+userIds[i].id+",")>=0){
                    continue;
                }
                userIdStrs+=userIds[i].id+",";
                userName+=","+userIds[i].getElementsByTagName("span")[0].innerHTML;
            }
        }
        $s.editData.userIdStrs = userIdStrs;
        $s.editData.userName = userName;
    }
    //删除单个驾校
    $s.deleteObj=function(id){
        var obj=document.getElementById(id);
        obj.remove();
    }


    ///**********************************************上传图片*****************************************************************/
    /*获取七牛TOKEN并缓存*/
    if(!getCookie("qnToken")){
        $.ajax({
            type: "get",
            url:config.basePath+"resource/token",
            async: false,
            success:function(data){
                $s.imgToken = data.result.pageData;
                setCookie("qnToken",$s.imgToken,0.5)
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
            if($("#imgfile").val()){
                var filepath = $("#imgfile").val();
                var postfix = filepath.substring(filepath.lastIndexOf('.'),filepath.length)
                if(postfix !='.jpg' && postfix !='.png' ){
                    $('#createImg').remove();
                    Layer.alert({width:300,height:150,type:"error",title:'格式不正确！'});
                    return;
                }
                var path = $("#imgfile").val();
                var timestamp=new Date().getTime();
                $("#key").val("notice_"+timestamp);
                /*选择相册图片后，执行如下动作（还未上传）*/
                $("#addImg").attr("src", objUrl) ;
                $("#imgHide").attr("src", objUrl) ;
                $s.checkUrl=true;
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
                $s.editData.pic = data.key;
                $s.uploadStae="success";
                $s.$apply();
            },
            error:function(data){
                $s.uploadStae="failure";
            }
        })

    }

    $s.setShowLength=function(objId, maxlength, id){
        var length=$("#"+objId).val().length;
        var rem = maxlength - length;
        var wid = id;
        if (rem <= 5){
            document.getElementById(wid).innerHTML = "还可以输入" + rem + "字数";
        }else{
            document.getElementById(wid).innerHTML = "";
        }

    }
    /**********************************************上传图片  结束************************************************************/
}]);


