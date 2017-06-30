/**********************************************后台配置**************************************************************************/ 
window.startConfig={
	bathUrl:"http://39.108.11.184/school/",                           //99服务器 文件根目录
};
/*------------------------ start 布局写入  ------------------------*/
window==window.top && document.write('\
	<div class="index-left" id="index-left">\
		<div class="logo">考场预约系统</div>\
		<span class="iphone-nav-but ion-navicon" id="iphone-nav-but"></span>\
		<div class="clearfix"></div>\
		<ul class="admin-nav" id="admin-nav">\
			<!-- menu 列表占位 -->\
		</ul>\
	</div>\
	<div class="index-right">\
		<div class="top" id="top">\
			<div class="pull-left logo"><span>考场预约系统</span>-<span id="school-name"></span></div>\
			<div class="pull-right top-right">\
				<div class="second-level-menu nav-slide">\
					<div><span class="ion-android-person"></span><span class="ion-arrow-down-b"></span></div>\
					<ul>\
						<li class="loginOut"><span class="ion-android-person"></span><span id="home-username"></span></li>\
						<li class="loginOut" onclick="logOut()"><span class="ion-locked"></span>退出登录</li>\
					</ul>\
				</div>\
			</div>\
		</div>\
		<div class="clearfix"></div>\
	</div>\
	<script src="'+startConfig.bathUrl+'assets/common/js/jquery-1.11.3.min.js"></script>\
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
