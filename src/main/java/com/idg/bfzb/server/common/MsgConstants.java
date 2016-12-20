package com.idg.bfzb.server.common;

/**
 * 类名称：MsgConstants
 * 类描述：消息常量类
 * 创建人：ouzhb
 * 创建日期：2016/12/08
 */
public class MsgConstants {
	//企业认证通过(推送)
	public final static String ENTERPRISE_AUTH_PASS = "恭喜您企业认证通过审核";
	//企业认证失败(推送)
	public final static String ENTERPRISE_AUTH_FAIL = "很遗憾，您企业认证未通过，您可以重新提交申请";
	//团队认证通过(推送)
	public final static String TEAM_AUTH_PASS = "恭喜您团队认证通过审核";
	//团队认证失败(推送)
	public final static String TEAM_AUTH_FAIL = "很遗憾，您团队认证未通过，您可以重新提交申请";
	//提现完成（后台已打款）(推送)
	public final static String TIXIAN_COMPLETE = "恭喜您申请提现￥${money}元已打款至您的${type}账号：${account}";
	//提现失败（后台打款失败）(推送)
	public final static String TIXIAN_FAIL = "您申请提现￥${money}元打款至${type}账号：${account}打款失败，该提现已返回您的平台账号";
	//待验收项目确认完成打款给接包方(推送)
	public final static String EMPLOYER_PAYMONEY_RECEIVER = "您<${projectName}>项目已由官方确认完成，项目奖金￥${tradeMoney}将打给工程师，您后续有任何问题可在“已完成”列表中查找到该项目可提起质保申诉。";
	//接包获得除质保金外项目奖金(推送)
	public final static String RECEIVER_GETMONEY_IMPREST = "您<${projectName}>项目已确认完成，您将获取该奖金的${imprestScale}%，剩余${marginScale}%将在${marginDay}天后打给你。";
	//接包方获得项目全款奖金(推送)
	public final static String RECEIVER_GETMONEY_FULL = "您<${projectName}>项目已确认完成，您将获取该奖金￥${money}。";
	//接包方获得质保金(推送)
	public final static String RECEIVER_GETMONEY_WARRANTY = "您<${projectName}>项目质保期已过，您将获取该质保奖金￥${marginMoney}。";
	//发包方获得项目拒收退款(推送)
	public final static String EMPLOYER_GETMONEY_RETURN = "您<${projectName}>项目拒收已由官方确认拒收，您将获取该项目退款￥${imprestMoney}。";
	//待确认项目剔除到失效(推送)
	public final static String PROJECT_TOCONFIRM_INVALID = "您的<${projectName}>由于您未确认，已过期。";
	//选标中项目剔除到失效(推送)
	public final static String PROJECT_SELECTING_INVALID = "您的<${projectName}>由于无人选标，已过期。";
	
	//项目审核通过(短信、推送)
	public final static String PROJECT_APPROVE_PASS = "尊敬的${realName}，您好！您的项目名为<${projectName}>已审核通过，请及时查看！";
	//项目审核拒绝(短信、推送)
	public final static String PROJECT_APPROVE_REFUSE = "尊敬的${realName}，您好！您的项目名为<${projectName}>审核被拒绝，拒绝理由：${reviewReason}！";
	//项目待确认(短信、推送)
	public final static String PROJECT_TO_CONFIRM = "尊敬的${realName}，您好！您的项目名为<${projectName}>需要您确认，请及时查看！";
	//管理员同意拒收-发给雇主(短信、推送)
	public final static String ADMIN_AGREEREFUSE_MSGTO_EMPLOYER = "尊敬的${realName}，您好！您的项目名为<${projectName}>已被管理员同意拒收，退款已退回到您的钱包中，请及时查看！";
	//管理员同意拒收-发给接包者(短信、推送)
	public final static String ADMIN_AGREEREFUSE_MSGTO_RECEIVER = "尊敬的${realName}，您好！您的项目名为<${projectName}>已被管理员同意雇主拒收，请及时查看！";
	//管理员驳回拒收-发给雇主(短信、推送)
	public final static String ADMIN_NOTAGREEREFUSE_MSGTO_EMPLOYER = "尊敬的${realName}，您好！您的项目名为<${projectName}>已被管理员驳回拒收，请及时查看！";
	//管理员驳回拒收-发给接包者(短信、推送)
	public final static String ADMIN_NOTAGREEREFUSE_MSGTO_RECEIVER = "尊敬的${realName}，您好！您的项目名为<${projectName}>已被管理员驳回雇主拒收，请及时查看！";
	//管理员同意终止质保-发给雇主(短信、推送)
	public final static String ADMIN_AGREEWARRANTY_MSGTO_EMPLOYER = "尊敬${realName}，您好，您申请的终止质保的申请已经受理，质保金已退回到您的钱包中，请注意查收，谢谢！";
	//管理员同意质保-发给接包方(短信、推送)
	public final static String ADMIN_AGREEWARRANTY_MSGTO_RECEIVER = "尊敬${realName}，很抱歉告诉您，在<${projectName}>由于雇主申请的终止质保的申请已经生效，质保金已退返 给雇主";
	//管理员驳回质保-发给雇主(短信、推送)
	public final static String ADMIN_NOTAGREEWARRANTY_MSGTO_EMPLOYER = "尊敬${realName}，您好，您申请的终止质保的申请被驳回，如有任何疑义，欢迎随时与我们！";
	//管理员驳回质保-发给接包方(短信、推送)
	public final static String ADMIN_NOTAGREEWARRANTY_MSGTO_RECEIVER = "尊敬${realName}，在<${projectName}>由于雇主申请的终止质保的申请被驳回，请您继续提供优质的服务给雇主，谢谢！";
	//线下充值（后台核对支付成功）(短信、推送)
	public final static String OFFLINE_RECHARGE_COMPLETE = "恭喜您充值的金额￥${money}元已到账，请您到个人中心查看，谢谢。";
	//线下充值（后台核对支付失败）(短信、推送)
	public final static String OFFLINE_RECHARGE_FAIL = "很遗憾，您充值的金额￥${money}元信息有误，请重新提交线下汇款信息，谢谢。";
	//邀标 (短信、推送)
	public final static String ADMIN_INVITE_PROJECT_MSGTO_RECEIVER = "尊敬${realName}，您好！现在<${projectName}>项目邀请您完成，请尽快到蝙蝠众包平台确认，过期不候！";
}
