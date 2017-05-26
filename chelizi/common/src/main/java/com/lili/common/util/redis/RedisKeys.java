package com.lili.common.util.redis;

public class RedisKeys {
	public class EXPIRE {
		/**
		 * 过期时间：天
		 */
		public static final int HOUR = 3600;
		/**
		 * 过期时间：天
		 */
		public static final int DAY = 3600 * 24;
		/**
		 * 过期时间：周
		 */
		public static final int WEEK = 3600 * 24 * 7;
		/**
		 * 过期时间：月
		 */
		public static final int MONTH = 3600 * 24 * 30;
	}
	
    public class REDISKEY {
    	
        /**
         * COACH_AUTHCODE+mobile----->authcode
         */
        public static final String COACH_AUTHCODE="user.c.authcode.";
        /**
         * COACH_TOKEN+id-------->token
         */
        public static final String COACH_TOKEN="user.c.token.";
        
        
        /**
         * STUDENT_AUTHCODE+mobile----->authcode
         */
        public static final String STUDENT_AUTHCODE="user.s.authcode.";
        /**
         * STUDENT_AUTHCODE+id----->token
         */
        public static final String STUDENT_TOKEN="user.s.token.";
        
        
        /**
         * ADMIN_AUTHCODE+mobile----->authcode
         */
        public static final String ADMIN_AUTHCODE="user.a.authcode.";
        /**
         * ADMIN_TOKEN+id----->token
         */
        public static final String ADMIN_TOKEN="user.a.token.";
        
        
        /**
         * SERVICE_AUTHCODE+mobile----->authcode
         */
        public static final String SERVICE_AUTHCODE="user.sv.authcode.";
        /**
         * SERVICE_TOKEN+id----->token
         */
        public static final String SERVICE_TOKEN="user.sv.token.";
        /**
         * SCHOOL_AUTHCODE+mobile----->authcode
         */
        public static final String SCHOOL_AUTHCODE="user.school.authcode.";
        /**
         * SCHOOL_TOKEN+id----->token
         */
        public static final String SCHOOL_TOKEN="user.school.token.";
        
        /**
         * PRE_PAY_ORDER+orderId ----->orderId
         */
        public static final String PRE_PAY_ORDER="use.prepay.order.";
        /**
         * PRE_PAY_ORDER+orderId ----->orderId
         */
        public static final String PRE_PAY_ORDER_SHORT="use.prepay.order.short.";
        
        /**
         * USER_CAR_LIST + coachId -----> Car
         */
        public static final String USER_CAR_LIST = "user.car.list.";

        /**
         * COACH_VO + coachId -----> Coach
         */
        public static final String COACH = "coach.dto.";
        /**
         * COACH_VO + coachId -----> CoachVo包含教练和教练车信息
         */
        public static final String COACH_VO = "coach.vo.";
        
        /**
         * CAR_INFO + carId ------> Car
         */
        public static final String CAR_INFO = "car.info.";
        
        /**
         * STUDENT_INFO + studentId ---->Student
         */
        public static final String STUDENT_INFO = "student.info.";

        /**
         * STUDENT_VIP_INFO + vipId ---->StudentVip
         */
        public static final String STUDENT_VIP_INFO = "student.vip.info.";
        
        /**
         * USER_ONLINE_STATE + coachId ----> isOnline(true,false)
         */
        public static final String USER_ONLINE_STATE = "user.online.state.";
        
        /**
         * CONFIGS_FOR_COACH ----> 针对教练的通用配置
         */
        public static final String CONFIGS_FOR_COACH = "configs.c";
        
        /**
         * CONFIGS_FOR_STUDENT ----> 针对学员的通用配置
         */
        public static final String CONFIGS_FOR_STUDENT = "configs.s";
        /**
         * CONFIGS_VERSION ----> 配置信息的版本
         */
        public static final String CONFIGS_VERSION = "configs.version";
        /**
         * COURSES ----> 课程信息
         */
        public static final String COURSES = "courses";
        /**
         * COURSES + id ----> 课程信息
         */
        public static final String COURSES_ITEM = "courses.";
        /**
         * COURSES_MAP ----> 课程信息
         */
        public static final String COURSES_MAP = "courses.map";
        /**
         * enroll.template  ---> 报名模板
         */
        public static final String ENROLL_TEMPLATE = "enroll.template";
        
        public static final String ENROLL_SCHOOL_TEMPLATE = "enroll.template.school";
        
        public static final String SCHOOL_CLASS_INFO = "school.class.info.";
        /**
         * enroll.template + ttid  ---> 单个报名模板
         */
        public static final String ENROLL_TEMPLATE_ONE = "enroll.template.";
        /**
         * enroll.mail + ttid  ---> 单个邮寄材料
         */
        public static final String ENROLL_MAIL_ONE = "enroll.mail.";
        /**
         * enroll.exam.place  ---> 考试地点
         */
        public static final String ENROLL_EXAM_PLACE = "enroll.exam.place";
        /**
         * 用户报名流程模板
         */
        public static final String ENROLL_PROGRESS_TEMPLATE = "enroll.progress.template";
        /**
         * 用户报名流程某个步骤--->enroll.progress.template.ttid-stepid
         */
        public static final String ENROLL_PROGRESS_TEMPLATE_ONE = "enroll.progress.template.";
        
        /**
         * 理论课信息
         */
        public static final String ENROLL_THEORY = "enroll.theory.";
        /**
         * 长训课信息
         */
        public static final String ENROLL_LONGTRAIN = "enroll.longtrain.";
        /**
         * 考试须知
         */
        public static final String ENROLL_NOTICE = "enroll.notice.";
        /**
         * 导入表信息
         */
        public static final String ENROLL_TABLE = "enroll.table.";
              
        
        
        /**
         * user.check.pw + userId ---> userType 用户钱包密码验证
         */
        public static final String USER_CHECK_PW = "user.check.pw";
        
        /**
         * user.s.deposit.+type+userId----->deposit 提现
         */
        public static final String DEPOST_APPLY="user.d.deposit.";
        
        /**
         * 分布式锁  e.g.	lock.enroll.id
         */
        public static final String LOCK_PRE ="lock.";
        
        /**
         * 标签：超级用户（用于标示此用户是否进行超级登录，即如为未注册用户，则自动注册并登录）
         */
        public static final String TAG_SUPER_STUDENT ="tag.s.";
        
        /**
         * 锁住优惠券，避免同个优惠券被同步的订单消耗
         */
		public static final String LOCK_COUPON = "lock.coupon.";
		
		/**
		 * ip地址信息
		 */
		public static final String IP_ADDRESS = "ip.address.";
		
		/**
		 * 教练预约排班价格
		 */
		public static final String CITY_PRICE = "city.price";
		
		public static final String TEYUE_COACH_CLASS = "teyue.coach.class.";
		
		public static final String COACH_CLASS_LIST= "coach.class.list.";
		
		public static final String ENROLL_STUDENT_INFO= "enroll.student.info.";
		
		
		
		/**
		 * 文件上传数据
		 */
		public static final String UPLOAD = "upload.file.";
		
		public static final String CONFIG_FILE = "config.file.";
		
		/**
		 * wechat.school.count ------------ 驾校点评，驾校总数
		 */
		public static final String WECHAT_SCHOOL_COUNT = "wechat.school.count";
		
		/**
		 * wechat.school.list.size_ + pageSize ------------ 驾校点评，驾校列表
		 */
		public static final String WECHAT_SCHOOL_LIST = "wechat.school.list.size_";
		
		
		/**
		 * wechat.school.list.all------------ 驾校点评，所有驾校
		 */
		public static final String WECHAT_SCHOOL_ALL = "wechat.schoolAll";
		
		public static final String SEARCH_SCHOOL_HAS_CLASS = "search.school.class.";
		
		public static final String SEARCH_SCHOOL_NOT_CLASS = "search.school.no.class.";
		
		/**
		 * wechat.school + schoolId ------------ 驾校点评，单个驾校信息
		 */
		public static final String WECHAT_SCHOOL = "wechat.school.";
		
		/**
		 * wechat.trfield.list + schoolId ------------ 驾校点评，某个驾校的所有训练场列表
		 * wechat.trfield.list + schoolId_pageSize ------------ 驾校点评，某个驾校的部分训练场列表
		 */
		public static final String WECHAT_TRFIELD_LIST = "wechat.trfield.list.";
		
		/**
		 * wechat.trfield.count + schoolId ------------ 驾校点评，某个驾校的所有训练场列表
		 */
		public static final String WECHAT_TRFIELD_COUNT = "wechat.trfield.count.";
		
		
		/**
		 * wechat.enroll.package.list. + schoolId ------------ 驾校点评，某个驾校的所有有效报名包数据
		 */
		public static final String WECHAT_ENROLL_PACKAGE_LIST = "wechat.enroll.package.list.";
		
		/**
		 * wechat.enroll.package. + packageID ------------ 驾校点评，某个报名包数据
		 */
		public static final String WECHAT_ENROLL_PACKAGE = "wechat.enroll.package.";
		
		
		
		/**
		 * cms.statistics.total + schoolId ----------------- cms首页，统计数据
		 */
		public static final String CMS_STATISTICS_TOTAL = "cms.statistics.total.";
		
		
		/**
		 * exam.place.+ id 考场信息
		 */
		public static final String EXAM_PLACE = "exam.place.";
		/**
		 * 通过驾校id获取对应考场信息
		 */
		public static final String EXAM_PLACE_SCHOOL = "exam.place.school.";
		/**
		 * 获取约考场城市
		 */
		public static final String EXAM_PLACE_CITY = "exam.place.city";
		/**
		 * 考场按日期查询排班情况  考场id+日期 <br>排班新增、关闭，考场预约、考场取消需要更新缓存
		 */
		public static final String EXAM_PLACE_DAY = "exam.place.day.";
		/**
		 * 考场排班 + 排班id
		 */
		public static final String EXAM_PLACE_CLASS = "exam.place.class.";
		/**
		 * 考场排班预约记录  考场id+教练id+车型	预约排班，取消预约，关闭预约排班需要更新这个缓存
		 */
		public static final String EXAM_PLACE_CLASS_BOOK = "exam.place.class.book.";
		/**
		 * 考场预约订单
		 */
		public static final String EXAM_PLACE_ORDER = "exam.place.order.";
		/**
		 * 考场人员白名单记录	在添加修改白名单记录后需要更新这个缓存
		 */
		public static final String EXAM_PLACE_WHITE = "exam.place.white.";
		/**
		 * 订单验证码
		 */
		public static final String EXAM_PLACE_CODE = "exam.place.code.";
		
		public static final String CITY_NAME_HASHMAP = "city.name.hashMap";
		
		public static final String COURSE_NAME_HASHMAP = "course.name.hashMap";
		
		/**
		 * 消息通知
		 */
		public static final String NOTICE = "notice.info.";
		
		public static final String NOTICE_TOP= "notice.top.list";
		
		public static final String LILI_COACH_TOKEN="lii.coach.token";
		
		public static final String LILI_COACH_TEMPLATE="lii.coach.template";
		
		public static final String COACH_CLASS_CCID_CACHE="coach.class.ccid.";
		
		public static final String LILI_COACH_STUDENT="lili.coach.student.";
		
		public static final String BBS_STUDENT_LIST="bbs.student.list";
		
		public static final String BBS_STUDENT_PRAISE_LIST="bbs.student.praise.list.";
		
		public static final String BBS_ID="bbs.id";
		
		public static final String BBS_SUM="bbs.sum";
		
		public static final String BBS_STUDENT_TOPIC_LIST="bbs.student.topic.list";
		
		public static final String BBS_FILTER_WORD_LIST="bbs.filter.word.list";
		
		public static final String BBS_STUDENT_BLACK_LIST="bbs.student.black.list";
		
		public static final String BBS_COACH_LIST="bbs.coach.list";
		
		public static final String BBS_COACH_BLACK_LIST="bbs.coach.black.list";
		
		public static final String BBS_COACH_TOPIC_LIST="bbs.coach.topic.list";
    }
}
