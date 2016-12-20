package com.idg.bfzb.server.common;

public class Constants {
	/**
	 * 文件上传
	 */
	public final static String FILE_TYPE_IMG = "img";
	public final static String FILE_TYPE_TXT = "txt";
	public final static String FILE_TYPE_OTHER = "other";
	public final static String FILE_IMAGE_PATH = "/opt/bfzb/resource/img/";
	public final static String UEDITOR_FILE_PATH = "/opt/bfzb/resource/ueditor/";
	public final static String FILE_OTHER_PATH = "/opt/bfzb/resource/other/";
	public static int FILE_IMG_WIDTH = 128;
	public static int FILE_IMG_HEIGHT = 128;
	public static long FILE_MAX_SIZE = 20*1024*1024;
	/**
	 * 提现申请
	 */
	public static String REVIEW_WATTING = "watting";//审核状态 待审核 watting
	//交易平台类型 支付宝 alipay | 微信wechatpay | 银联 unionpay| 线下 offline
	public static String TRADE_PLAT_ALIPAY = "alipay";
	public static String TRADE_PLAT_WECHATPAY = "wechatpay";
	public static String TRADE_PLAT_UNIONPAY = "unionpay";
	public static String TRADE_PLAT_OFFLINE = "offline";
	//交易对象类型(业务类型) fullmoney全款 | imprestmoney预付款 | revealmoney兜底服务费  | remainmoney尾款  | returnmoney退款 | marginmoney质保金
	public static String TARGET_TYPE_FULLMONEY = "fullmoney";
	public static String TARGET_TYPE_IMPRESTMONEY = "imprestmoney";
	public static String TARGET_TYPE_REVEALMONEY = "revealmoney";
	public static String TARGET_TYPE_REMAINMONEY = "remainmoney";
	public static String TARGET_TYPE_RETURNMONEY = "returnmoney";
	public static String TARGET_TYPE_MARGINMONEY = "marginmoney";
	//交易操作类型( 充值recharge | 提现withdraw |  支付payment |  收入income
	public static String TRANS_TYPE_RECHARGE = "recharge";
	public static String TRANS_TYPE_WITHDRAW = "withdraw";
	public static String TRANS_TYPE_PAYMENT = "payment";
	public static String TRANS_TYPE_INCOME = "income";
	//订单类型
	public static String ORDER_TYPE_RECHARGE = "R";
	public static String ORDER_TYPE_WITHDRAW = "W";
	public static String ORDER_TYPE_PAYMENT = "P";
	public static String ORDER_TYPE_INCOME = "I";
	
	
	/**
	 * 充值审核状态
	 */
	public static short RECHARGE_AUDIT_STATE_PASS=2;//核对通过
	public static short RECHARGE_AUDIT_STATE_NO_PASS=1;//核对不通过
	
	//交易状态
	public static short TRADE_STATE_NOT_PAY = 0;
	public static short TRADE_STATE_PAID_SUCCESS = 1;
	public static short TRADE_STATE_PAID_FAIL = -1;
	
	//微信预支付请求错误
	public static String WECHAT_RETURN_CODE_FAIL="FAIL";
	
	//支付终端类型
	public static String TERMTYPE_PC="pc";
	public static String TERMTYPE_MOBILE="mobile";
	
	/**
	 * 团队管理
	 */
	//团队未审核状态
	public final static short TEAM_NOTCHECK_STATE = 0;
	//团队审批拒绝状态
	public final static short TEAM_CHECKREFUSE_STATE = 3;
	//是否团队用户 1 是  0 否
	public final static byte TEAM_USER_YES = 1;
	public final static byte TEAM_USER_NO = 0;
	/**
	 * 项目管理
	 */
	//项目状态:-1：已删除,0：已提交未审批,3：待客户确认,4:未中标,5：雇主选标中/接包者报名中/已发布（审批通过）,6：项目进行中/已中标,7：已完结,8：待验收,9：已失效（审批拒绝）
	public final static short PROJECT_STATE_DELETE = -1;
	public final static short PROJECT_STATE_NOT_APPROVE = 0;
	public final static short PROJECT_STATE_APPROVE_PASS = 3;
	public final static short PROJECT_STATE_NOT_SELECTED = 4;
	public final static short PROJECT_STATE_SELECTING = 5;
	public final static short PROJECT_STATE_SELECTED = 6;
	public final static short PROJECT_STATE_OVER = 7;
	public final static short PROJECT_STATE_CHECK = 8;
	public final static short PROJECT_STATE_INVALID = 9;
	//项目截止时间类别
	public final static String PROJECT_CLASSIFY_DEADLINE = "DEADLINE";
	//项目预算金额类别
	public final static String PROJECT_CLASSIFY_BUDGET = "BUDGET_AMOUNT";
	//项目协议页类别
	public final static String PROJECT_CLASSIFY_AGREEMENT = "AGREEMENT";
	//多个项目分类存储分隔符标记
	public final static String PROJECT_CATEGORY_SPLIT = "、";
	//工作内容照片分隔
	public final static String PROJECT_REPORTPHOTO_SPLIT = "、";
	//预算金额区间分隔符标记
	public final static String PROJECT_BUDGET_SPLIT = "-";
	//个人项目 individual_project | 团队项目 team_project 
	public final static String PROJECT_TYPE_INDIVIDUAL = "individual_project";
	public final static String PROJECT_TYPE_TEAM = "team_project";
	//招标方式：common_tender 普通招标 | 包招 contain_tender
	public final static String PROJECT_TENDER_COMMON = "common_tender";
	public final static String PROJECT_TENDER_CONTAIN = "contain_tender";
	//确认是否发布：ok发布，giveup不发布
	public final static String PROJECT_PUBLISH_OK = "ok";
	public final static String PROJECT_PUBLISH_GIVEUP = "giveup";
	//报名状态：0：不可报名，1：可报名，2：已报名
	public final static short PROJECT_SIGNUP_STATE_NO = 0; 
	public final static short PROJECT_SIGNUP_STATE_OK = 1; 
	public final static short PROJECT_SIGNUP_STATE_ALREADY = 2; 
	//投标状态0 发起投标；1被拒绝；2被采纳
	public final static short PROJECT_TENDER_STATE_LAUNCH = 0;
	public final static short PROJECT_TENDER_STATE_REFUSE = 1;
	public final static short PROJECT_TENDER_STATE_ADOPT = 2;
	//是否兜底0不要兜底；1要兜底
	public final static short PROJECT_REVEAL_OK = 1;
	public final static short PROJECT_REVEAL_NO = 0;
	//兜底服务申请状态：0已提交未审核；1审核通过；2审核拒绝
	public final static short PROJECT_REVEALAPPLY_SUBMIT = 0;
	public final static short PROJECT_REVEALAPPLY_PASS = 1;
	public final static short PROJECT_REVEALAPPLY_REFUSE = 2;
	//项目进度来源 ：bfzb 蝙蝠众包平台 | sdr 中锐SDR
	public final static String PROJECT_PROGRESS_SOURCE_BFZB = "bfzb";
	public final static String PROJECT_PROGRESS_SOURCE_SDR = "sdr";
	//项目雇主是否拒收：0否 | 1是
	public final static short PROJECT_REFUSE_EMPLOYEE_YES = 1;
	public final static short PROJECT_REFUSE_EMPLOYEE_NO = 0;
	//项目拒收接包方意见: 0 未处理 | 1 拒绝 | 2同意拒收
	public final static short PROJECT_REFUSE_RECEIVER_UNTREATED = 0;
	public final static short PROJECT_REFUSE_RECEIVER_REFUSE = 1;
	public final static short PROJECT_REFUSE_RECEIVER_AGREE = 2;
	//项目拒收客服处理结果：0 未处理 | 1 拒绝 | 2同意拒收
	public final static short PROJECT_REFUSE_ADMIN_UNTREATED = 0;
	public final static short PROJECT_REFUSE_ADMIN_REFUSE = 1;
	public final static short PROJECT_REFUSE_ADMIN_AGREE = 2;
	//评论目标类型：evaluate_employer评价雇主 | evaluate_receiver评价接包者
	public final static String PROJECT_EVALUATE_TARGET_TYPE_EMPLOYER = "evaluate_employer";
	public final static String PROJECT_EVALUATE_TARGET_TYPE_RECEIVER = "evaluate_receiver";
	//质保处理状态0 未处理 | 1 拒绝 | 2同意拒收
	public final static short PROJECT_WARRANTY_UNTREATED = 0;
	public final static short PROJECT_WARRANTY_REFUSE = 1;
	public final static short PROJECT_WARRANTY_AGREE = 2;
	//项目进度类型:work_progress工作进度
	public final static String PROJECT_PROGRESS_TYPE_WORK = "work_progress";
	//消息是否已读:0 未读;1已读
	public final static short MESSAGE__STATE_NOT_READ = 0;
	public final static short MESSAGE__STATE_IS_READ = 1;
	//消息发送渠道： 推送 push | 短信sms
	public final static String MESSAGE_CHANNEL_PUSH = "push";
	public final static String MESSAGE_CHANNEL_SMS = "sms";
	//项目是否评价过
	public final static short PROJECT_EVALUATE_YES = 1;
	public final static short PROJECT_EVALUATE_NO = 0;
	//项目是否质保
	public final static short PROJECT_IS_MARGIN_NO = 0;
	public final static short PROJECT_IS_MARGIN_YES = 1;
	//项目是否兜底
	public final static short PROJECT_IS_REVEAL_NO = 0;
	public final static short PROJECT_IS_REVEAL_YES = 1;
	//项目时间间隔天数	
	public final static int PROJECT_TIME_DAY_1 = 1;
	public final static int PROJECT_TIME_DAY_2 = 2;
	public final static int PROJECT_TIME_DAY_3 = 3;
	public final static int PROJECT_TIME_DAY_7 = 1;
	public final static int PROJECT_TIME_DAY_15 = 1;
	
	/**
	 * 字符串长度限制
	 */
	//长度不超过30
	public final static int STRING_LENGTH_30 = 30;
	//长度不超过100
	public final static int STRING_LENGTH_100 = 100;
	//长度不超过500
	public final static int STRING_LENGTH_500 = 500;
	
	public final static short UC_DEFAULT_STATE = 0;
	
	/**
	 * 后台管理
	 */
	//用户管理
	public final static String USER_AUTH_TYPE_NORMAL = "normal";// normal普通用户    team 团队用户
	public final static String USER_AUTH_TYPE_ENTERPRISE = "enterprise";//enterprise企业用户
	public final static String USER_AUTH_TYPE_TEAM = "team";//team 团队用户
	
	//平台账户
	public final static String PLAT_USER_ID = "34c6c844-766f-4c57-8c1b-2ca3e2a50d7d";
	public final static String PLAT_USER_NAME = "admin";
	
	/**
	 * 验证码失效时间：5分钟
	 */
	public final static int UC_AUTHCODE_INVALID_MINUTE = 5;
	/**
	 * 验证码当天最多发送次数
	 */
	public final static int UC_AUTHCODE_SEND_TIME = 6;
	/**
	 * 第三方登录强制覆盖原有绑定
	 */
	public final static short UC_THIRDLOGIN_IS_FORCE_BIND_YES = 1;
	
	// 用户角色必填;employer:雇主 worker:接包方
	public final static String UC_USER_ROLE_EMPLOYER = "employer";
	public final static String UC_USER_ROLE_WORKER = "worker";
}
