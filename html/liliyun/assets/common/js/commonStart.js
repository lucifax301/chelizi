/**********************************************后台配置**************************************************************************/ 
window.startConfig={
	bathUrl:"http://xc.res.com:8080/liliyun/",                         //99服务器 文件根目录
	edition:'2.1.0',                                                     //当前后台版本
};
/*------------------------ start 布局写入  ------------------------*/
window==window.top && document.write('\
	<div class="index-left" id="index-left">\
		<div class="logo">喱喱云</div>\
		<span class="iphone-nav-but ion-navicon" id="iphone-nav-but"></span>\
		<div class="clearfix"></div>\
		<ul class="admin-nav" id="admin-nav">\
			<!-- menu 列表占位 -->\
		</ul>\
	</div>\
	<div class="index-right">\
		<div class="top" id="top">\
			<div class="pull-left logo"><img src="'+startConfig.bathUrl+'assets/img/logo.png"><span>喱喱云 '+startConfig.edition+'</span></div>\
			<div class="pull-right top-right">\
				<div class="second-level-menu nav-slide">\
					<div><span class="ion-android-person"></span><span class="ion-arrow-down-b"></span></div>\
					<ul>\
						<li class="loginOut"><span class="ion-android-person"></span><span id="home-username"></span></li>\
						<li class="loginOut" onclick="logOut()"><span class="ion-locked"></span>退出登录</li>\
					</ul>\
				</div>\
			</div>\
			<div class="pull-right top-right notice-center" style="display: none">\
				<div class="second-level-menu nav-slide" id="noticeCenter">\
					<div><span class="ion-android-notifications"></span><span id="noticeNum"></span></div>\
					<ul>\
						<li class="notice-tit"><p class="z-pt10 z-font14">通知中心</p></li>\
						<li class="notice-attemp"><p class="z-pt10 z-font14">暂无最新消息</p></li>\
					</ul>\
				</div>\
			</div>\
		</div>\
		<div class="clearfix"></div>\
	</div>\
	<script src="'+startConfig.bathUrl+'assets/common/js/jquery-2.1.4.min.js"></script>\
	<script src="'+startConfig.bathUrl+'assets/common/js/common.js"></script>\
	<script src="'+startConfig.bathUrl+'assets/js/function.js"></script>\
	<script src="'+startConfig.bathUrl+'assets/js/index.js"></script>\
	<script src="'+startConfig.bathUrl+'assets/js/cms-jq.js"></script>\
	<script src="'+startConfig.bathUrl+'assets/common/js/PopLayer.js"></script>\
	<script src="'+startConfig.bathUrl+'assets/common/js/Page.js"></script>\
	<script src="'+startConfig.bathUrl+'assets/common/js/angular.min.js"></script>\
	<script src="'+startConfig.bathUrl+'assets/js/ng-js/angular-filter.js"></script>\
	<script src="'+startConfig.bathUrl+'assets/common/js/echarts.min.js"></script>\
');
/*------------------------ end 布局写入  ------------------------*/
