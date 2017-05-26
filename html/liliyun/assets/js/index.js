//关闭加载层
//win.hideLoading();
/***************************************************后台首页*********************************************************************/

;(function(){

/*-----------------------------------------index menu权限列表展示----------------------------------------------*/
	//var arr=[100100,101500,100200,100300,100400,100500,100600,100601,100602,100603,100700,100800,100900,101000,101600,101700,101300];
	//showMenu(arr);
	//menuList();
    var hasOrderAttempAcc = 0;//是否有查看订单调度的权限，默认无
    clearInterval(loadMenuData);
    var testI = 50;
    var loadMenuData = setInterval(function(){
        testI --;
        if(!testI){
            console.log("获取菜单列表失败");
            clearInterval(loadMenuData);
            window.location.href=startConfig.bathUrl+window.config.loginUrl;
        }
        //console.log(testI)
        if(window.menuPer){
            console.log(window.menuPer)
            clearInterval(loadMenuData);
            //如果权限值有订单调度值120109，则允许执行实时请求订单调度信息
            for( var i=0;i<window.menuPer.btn.length;i++){
                if(parseInt(window.menuPer.btn[i].id) ==120109){
                    hasOrderAttempAcc=1;
                }
            }
            showMenu([]);
            menuList();
            accessVerify();
        }

    },100)

    /*遍历按钮和链接，符合权限则显示*/
    function accessVerify(){
        /*获取当前角色权限ID值集合*/
        var acArrs = [];
        if(sessionStorage.acArrData){
            acArrs = sessionStorage.acArrData.split(",");//从缓存中拉取转化为数组
        }else{
            var acDatas = window.menuPer.btn;
            for(var i=0;i<acDatas.length;i++){
                acArrs.push(acDatas[i].id)
            }
            sessionStorage.acArrData = acArrs.toString();//数组转为字符串存入缓存
        }
        /*遍历需要验证权限的按钮，合则留，不合则去*/
        //$(".acVerify").each(function(){
        //    if($.inArray($(this).attr("ac-data"), acArrs)==-1){
        //        $(this).remove();
        //    }
        //})

    }


	function showMenu(arr){
		var newMenu=getMenuData(arr);
		var html='';
		for(var i=0,len=newMenu.length;i<len;i++){
			if(newMenu[i].title){
				html+='<li><div><span class="mainnavico"></span>'+newMenu[i].title.name+'<i class="s-i ion-ios-arrow-right"></i></div><ul class="second-menu s-ac-menu">';
				for(var j=0,lenj=newMenu[i].list.length;j<lenj;j++){
					if(newMenu[i].list[j].title){
						html+='<li><div>'+newMenu[i].list[j].title.name+'<i class="t-i ion-ios-arrow-right"></i></div><ul class="second-menu t-ac-menu">';
							for(var k=0;k<newMenu[i].list[j].list.length;k++){
								html+='<li><div src="'+newMenu[i].list[j].list[k].url+'">'+newMenu[i].list[j].list[k].name+'</div></li>';
							}
						html+='</li></ul>';
					}else{
						html+='<li><a href="'+startConfig.bathUrl+newMenu[i].list[j].url+'"><div src="'+newMenu[i].list[j].url+'">'+newMenu[i].list[j].name+'</div></a></li>';
					}		
				};		
				html+='</ul></li>';
			}else{
				html+='<li><a href="'+startConfig.bathUrl+newMenu[i].src+'"><div src="'+newMenu[i].src+'"><span class="'+newMenu[i].icon+'"></span>'+newMenu[i].name+'</div></a></li>';
			}
		}
		$('#admin-nav').html(html);
	}
	/*menu 权限筛选后操作 返回新的数据*/
	function getMenuData(arr){
		var menuList=getMenu(arr);
		var menuPar=menuPer.par;
		var newArr=[];
		var parenName='';
		var parenName1='';
		var iNow=0;
		var iNow1=0;
		for(var i=0,len=menuList.length;i<len;i++){
            if(menuList[i].menuOrder!=-1){/*menuOrder为-1，则菜单里排除不显示*/
                if(menuList[i].parenName){
                    if(parenName==menuList[i].parenName){
                        if(parenName1==menuList[i].parenName){
                            newArr[iNow-1].list[iNow1].list.push(menuList[i])
                        }else{
                            newArr[iNow-1].list.push(menuList[i]);
                            iNow1++;
                        }
                    }else{
                        var titleMe=menuPar[menuList[i].parenName].parentName;
                        if(titleMe){
                            newArr[iNow-1].list.push({
                                title:menuPar[menuList[i].parenName],
                                list:[menuList[i]]
                            })
                            iNow1++;
                            parenName1=menuList[i].parenName;
                        }else{
                            newArr.push({
                                title:menuPar[menuList[i].parenName],
                                list:[menuList[i]]
                            });
                            iNow++;
                        }
                    }
                }else{
                    newArr.push(menuList[i]);
                    iNow++;
                }
                parenName=menuList[i].parenName;
            }

		}
		return newArr;
	}
	
/*--------------------------------------index menu权限列表结束-----------------------------------------------*/

	/*mobile 导航相关操作*/
	if($(window).width()<=768){
		$('#iphone-nav-but').click(function(ev){
			ev.stopPropagation();
			$('#admin-nav').stop().slideToggle("fast");
		})
		/*算出右侧所展示的高度*/
		//setTimeout(function(){
		var contentLoad = setInterval(function(){
			$('#content').css({width:$(window).width()+"px",height:($(window).height()-$("#top").height()-$("#index-left").height()-4)+"px"});
			$('#content').show();
			if($('#content').css("display")=="block"){clearInterval("contentLoad")}
		}, config.contentTimeShow);
	}else{
		/*算出右侧所展示的高度*/
		var contentLoad = setInterval(function(){
		//setTimeout(function(){
			$('#content').css({width:$(window).width()-220+"px",height:($(window).height()-$("#top").height()-2)+"px"});
			$('#content').show();
			if($('#content').css("display")=="block"){clearInterval("contentLoad")}
		}, config.contentTimeShow);
		/*PC端屏幕改变时 自适应*/
		window.onresize = function(){
			$('#content').css({width:$(window).width()-220+"px"});
		}
	}		

	/*展示用户名*/
	$('#home-username').text(localStorage.getItem("lili-username"));

	/*首页menu 操作函数*/
	function menuList(){
		/*页面 加载|刷新 左侧导航active*/
		/*打开相应的右侧菜单*/
		openSrc();
		
		/*后台首页左侧导航active*/
		$('#admin-nav div').click(function(ev){
			ev.stopPropagation();
			var href=$(this).attr("src");
			if(href){
				if($(window).width()<=768){
					$('#admin-nav').slideUp("fast");
				}
				/*无子集*/
				/*左侧导航栏active*/
				openSrc();
			}else{
				/*有子集*/
				if($(this).parents("li").parents("li")[0]){
					$('#admin-nav ul.t-ac-menu').slideUp("fast");
					$('#admin-nav i.t-i').attr("class","ion-ios-arrow-right");
					if($(this).parent("li").find('ul').css("display")=="none"){
						$(this).parent("li").find("i").attr("class","t-i ion-ios-arrow-down");
						$(this).parent("li").find('ul').slideDown("fast");
					}else{
						$(this).parent("li").find("i").attr("class","t-i ion-ios-arrow-right");
						$(this).parent("li").find('ul').slideUp("fast");
					}
				}else{
					$('#admin-nav ul').slideUp("fast");
					$('#admin-nav i.s-i').attr("class","s-i ion-ios-arrow-right");
					if($(this).parent("li").find('ul.s-ac-menu').css("display")=="none"){
						$(this).parent("li").find("i.s-i").attr("class","s-i ion-ios-arrow-down");
						$(this).parent("li").find('ul.s-ac-menu').slideDown("fast");
					}else{
						$(this).parent("li").find("i.s-i").attr("class","s-i ion-ios-arrow-right");
						$(this).parent("li").find("i.t-i").attr("class","t-i ion-ios-arrow-right");
						$(this).parent("li").find('ul.s-ac-menu').slideUp("fast");
					}

				}
			}
		});	
	}

/*--------------------------------------index ajax-----------------------------------------------*/
	/*用户验证*/
	$.AJAX({
		url:config.basePath+"user/verify",
		nohideloading:true,
		success:function(data){
			localStorage.setItem("lili-username",data.result.userName);
		}
	});

	/*退出登陆*/
	window.logOut=function(){
		Layer.confirm({width:300,height:160,title:"确认退出登陆吗？",header:"退出登陆"},function(){
			loginOut(); //退出登录
		});
	};

	/*获取but 权限列表*/
	$.AJAX({
		type:"GET",
		url:config.basePath+'resource/btn-list',
		nohideloading:true,
		success:function(data){
			/*展示菜单*/
			localStorage.setItem("lili-btnList",data.result.pageData);
		}
	});

    /*实时请求订单调度*/
    window.getAttempOrderNum = function getAttempOrderNum(){
        $.ajax({
            type:"get",
            url:config.basePath+'order/schedule/notice',
            xhrFields: {withCredentials: true},
            async:true,
            crossDomain: true,
            datatype:"json",
            success:function(data){
                var orderNum=data.result.pageData;
                //当有新的调度订单时，更新消息提示
                if(orderNum>0){
                    $(".notice-center").css("display","block")
                    $("#noticeNum").text(orderNum)
                    $(".notice-attemp").remove();
                    $("#noticeCenter ul").append('<li class="notice-attemp"><p class="z-font14 z-pt10 z-mb0">自主预约订单</p><a class="z-font12 z-fontlight z-disblock z-mb10" href="'+window.startConfig.bathUrl+'order-attemper.html" >有 <span class="z-fontred">'+orderNum+'</span> 个自主预约的订单暂无教练信息，去给学员指派教练吧!</a></li>')
                }
            },
            error:function(){
                console.log("订单调度实时信息获取失败");
            }
        })
    }

    setTimeout(function(){
        if(hasOrderAttempAcc>0){
            window.getAttempOrderNum();
            setInterval(function(){window.getAttempOrderNum();},20000)
        }
    },2000)


})();




