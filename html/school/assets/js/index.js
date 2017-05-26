//关闭加载层
//win.hideLoading();
/***************************************************后台首页*********************************************************************/

;(function(){

/*--------------------------------------index menu权限列表展示----------------------------------------------*/
	//var arr=[100100,100200,100300,100400,100500,100600,101900,102000,102100,101000,101400,101100,101200,101300,100600];
	//showMenu(arr);
	//menuList();
	/*获得menu 权限列表*/
	//$.AJAX({
	//	type:"GET",
	//	url:config.basePath+'resource/menu-list',
	//	nohideloading:true,
	//	success:function(data){
	//		/*展示菜单*/
	//		showMenu(data.result.pageData.split(','));//因测试数据被清空，暂时用静态权限判定数据
	//		//showMenu(arr);
	//		menuList();
	//	}
	//});

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
            showMenu([]);
            menuList();
            accessVerify();
        }

        //$.AJAX({
        //	type:"GET",
        //	url:config.basePath+'resource/menu-list',
        //	nohideloading:true,
        //	success:function(data){
        //		/*展示菜单*/
        //           console.log(data)
        //           showMenu(data.result.pageData.split(','));
        //           menuList();
        //	}
        //});
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


    /*渲染menu*/
	function showMenu(arr){
		var newMenu=getMenuData(arr);
        console.log(newMenu)
		var html='';
		for(var i=0,len=newMenu.length;i<len;i++){
			if(newMenu[i].title){
				html+='<li><div><span class="mainnavico"></span>'+newMenu[i].title.name+'<i class="s-i ion-ios-arrow-right"></i></div><ul class="second-menu s-ac-menu">';
				for(var j=0,lenj=newMenu[i].list.length;j<lenj;j++){
					if(newMenu[i].list[j].title){
						html+='<li><div>'+newMenu[i].list[j].title.name+'<i class="t-i ion-ios-arrow-right"></i></div><ul class="second-menu t-ac-menu">';
							for(var k=0;k<newMenu[i].list[j].list.length;k++){
								html+='<li><div src="'+newMenu[i].list[j].list[k].src+'">'+newMenu[i].list[j].list[k].name+'</div></li>';
							}
						html+='</li></ul>';
					}else{
						html+='<li><a href="'+startConfig.bathUrl+newMenu[i].list[j].src+'"><div src="'+newMenu[i].list[j].src+'">'+newMenu[i].list[j].name+'</div></a></li>';
					}		
				};		
				html+='</ul></li>';
			}else{
				html+='<li><a href="'+startConfig.bathUrl+newMenu[i].src+'"><div src="'+newMenu[i].src+'"><span class="mainnavico"></span>'+newMenu[i].name+'</div></a></li>';
			}
		}
		$('#admin-nav').html(html);
	}
	/*menu 权限筛选后操作 从新返回新数据*/
	function getMenuData(arr){
		var menuList=getMenu(arr);
		var menuPar=menuPer.par;
		var newArr=[];
		var parenName='';
		var parenName1='';
		var iNow=0;
		var iNow1=0;
		for(var i=0,len=menuList.length;i<len;i++){
            if(menuList[i].menuOrder!=-1) {/*menuOrder为-1，则菜单里排除不显示*/
                if (menuList[i].parenName) {
                    if (parenName == menuList[i].parenName) {
                        if (parenName1 == menuList[i].parenName) {
                            newArr[iNow - 1].list[iNow1].list.push(menuList[i])
                        } else {
                            newArr[iNow - 1].list.push(menuList[i]);
                            iNow1++;
                        }
                    } else {
                        var titleMe = menuPar[menuList[i].parenName].parentName;
                        if (titleMe) {
                            newArr[iNow - 1].list.push({
                                title: menuPar[menuList[i].parenName],
                                list: [menuList[i]]
                            })
                            iNow1++;
                            parenName1 = menuList[i].parenName;
                        } else {
                            newArr.push({
                                title: menuPar[menuList[i].parenName],
                                list: [menuList[i]]
                            });
                            iNow++;
                        }
                    }
                } else {
                    newArr.push(menuList[i]);
                    iNow++;
                }
                parenName = menuList[i].parenName;
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
			$('#content').css({width:$(window).width()+"px",height:($(window).height()-$("#top").height()-$("#index-left").height()-4)+"px"})
			$('#content').show();
			if($('#content').css("display")=="block"){clearInterval("contentLoad")}
		}, config.contentTimeShow);
	}else{
		/*算出右侧所展示的高度*/
		//setTimeout(function(){
		var contentLoad = setInterval(function(){
			$('#content').css({width:$(window).width()-220+"px",height:($(window).height()-$("#top").height()-2)+"px"});
			$('#content').show();
			if($('#content').css("display")=="block"){clearInterval("contentLoad")}
		}, config.contentTimeShow);
		/*PC端屏幕改变时 自适应*/
		window.onresize = function(){
			$('#content').css({width:$(window).width()-220+"px"});
		}
	}	

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
				/*iframe 赋值*/
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

/*--------------------------------------index AJAX-----------------------------------------------------------*/
	/*用户验证*/
	$.AJAX({
		url:config.basePath+"user/verify",
		nohideloading:true,
		success:function(data){
            sessionStorage.setItem("schoolUserName",data.result.userName);
			/*展示用户名*/
			$('#home-username').text(data.result.userName);
			/*展示驾校信息*/
			$('#school-name').text(data.result.schoolName);
		}
	});

	/*退出登陆*/
	window.logOut = function(){
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
			localStorage.setItem("school-btnList",data.result.pageData);
		}
	});


})();




