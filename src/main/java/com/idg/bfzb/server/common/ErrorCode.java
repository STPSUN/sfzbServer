package com.idg.bfzb.server.common;

/**
 * 类名称：
 * 类描述：
 * 创建人：jiangdong
 * 创建日期：2016/10/26
 */
public enum  ErrorCode {
    SUCESSFUL("SUCESSFUL","请求成功"),

    SYSTEM_EXCEPTION("SYSTEM_EXCEPTION","系统异常"),
    REQUIRE_ARGUMENT("REQUIRE_ARGUMENT","缺少参数"),
    INVALID_ARGUMENT("INVALID_ARGUMENT","输入参数格式不正确"),
    INVALID_DATE_ARGUMENT("INVALID_DATE_ARGUMENT","日期参数格式不正确"),

    UC_SESSION_EXPIRE("session_expire","令牌过期"),
    UC_ERR_NOT_LOGIN("err_not_login","请重新登录"),
    UC_MOBILE_NOT_CORRECT("mobile_not_correct","手机号格式错误"),
    UC_MOBILE_ALREADY_REGISTER("mobile_already_register","手机号已被注册"),
    UC_MOBILE_NOT_EXIST("mobile_not_exist","手机号不存在"),
    UC_MOBILE_LOGIN_FAIL("mobile_login_fail","手机号不存在或密码错误"),
    UC_PIN_NOT_CORRECT("pin_not_correct","验证码错误"),
    UC_PIN_OVER_FREQUENCY("pin_over_frequency","短信验证码今天已达到发送次数上限"),
    UC_PIN_SEND_FAIL("pin_send_fail","短信验证码发送失败"),
    UC_PASSWORD_NOT_CORRECT("user_password_wrong","原密码错误"),
    UC_NEWOLD_PASSWORD_EQUAL("uc_newold_password_equal","新旧密码一样"),
    UC_USER_NOT_EXIST("user_not_exist","用户不存在."),
    UC_NICK_NAME_IS_EXIST("nick_name_is_exist","昵称已被使用."),
    UC_NICK_NAME_IS_NULL("nick_name_is_null","昵称不能为空."),
    UC_THIRD_LOGIN_WRONG("uc_third_login_wrong","第三方登录调用错误."),
    UC_JPUSHCODE_IS_EXIST("uc_jpushcode_is_exist","该极光ID已注册过."),
    UC_THIRD_LOGIN_IS_BIND("uc_third_login_is_bind","账号已绑定第三方登录"),

    UC_REAL_AUTH_REPEAT("real_auth_repeat","请勿重复提交实名认证信息"),
    USER_NOT_REAL("user_not_real","未进行实名认证"),
    TEAM_USER_NOT_REAL("team_user_not_real","未进行实名认证，无法加入团队"),
    TEAM_MAX_TEAM_NUM("team_max_team_num","加入的团队数量超过系统最大限制"),
    REQUIRE_TITLE_BLANK("require_title_blank","需求名称不能为空"),
    REQUIRE_DES_BLANK("require_des_blank","详细描述不能为空"),
    REQUIRE_DATE_WRONG("require_date_wrong","报名截止日期或交付截至日期错误"),
    REQUIRE_COMPANY_REALAUTH("require_company_realauth","未通过企业实名认证"),
    REGION_ID_IS_EMPTY("region_id_is_empty","传入regionId为空"),
    REGION_NOT_EXIST("region_not_exist","所选城市错误"),
    HAS_UNCOMPLETE_REQUIRE("has_uncomplete_require","存在未完成的团队项目"),
    REQUIRE_NOT_EXIST("require_not_exist","需求不存在"),
    REQUIRESTATUS_WRONG("requireStatus_wrong","需求状态异常"),
    PARAM_INPUT_WRONG("param_input_wrong","参数输入异常"),
    TEAM_MAX_MEMBER_NUM("team_max_member_num","成员数已满"),
    TENDER_MEMBER_INPUT_WRONG("tender_member_input_wrong","团队子任务数非法"),
    SESSION_NOTEXIST("session_notexist","会话不存在"),
    
    TEAMS_ID_NOT_EXIST("TEAMS_ID_NOT_EXIST","团队ID不存在"),
    TEAMS_NOT_UPDATE("TEAMS_NOT_UPDATE","不是团队创建者无法修改团队"),
    TEAMS_NAME_EXIST("TEAMS_NAME_EXIST","团队名称已存在"),
    TEAMS_USER_NOT_AUTHENTICATION("TEAMS_USER_NOT_AUTHENTICATION","个人未实名认证，不可申请团队"),
    TEAMS_NOT_SUBMIT("TEAMS_NOT_SUBMIT","团队认证不可重复提交"),
    
    PROJECTS_NAME_TOO_LENGTH("PROJECTS_NAME_TOO_LENGTH","项目名称长度过长，不超过30个字符"),
    PROJECTS_HOUSENUM_TOO_LENGTH("PROJECTS_HOUSENUM_TOO_LENGTH","门牌号长度过长，不超过100个字符"),
    PROJECTS_DESCRIPTION_TOO_LENGTH("PROJECTS_DESCRIPTION_TOO_LENGTH","项目详细信息长度过长，不超过500个字符"),
    PROJECTS_CATEGORYID_NOT_EXIST("PROJECTS_CATEGORYID_NOT_EXIST","项目服务类别ID不存在"),
    PROJECTS_NAME_EXIST("PROJECTS_NAME_EXIST","项目名称已存在"),
    PROJECTS_NOT_EXIST("PROJECTS_NOT_EXIST","项目不存在"),
    PROJECTS_STATE_NOT_APPROVE_PASS("PROJECTS_STATE_NOT_APPROVE_PASS","项目状态不是审批通过状态"),
    PROJECTS_STATE_NOT_SELECTING("PROJECTS_STATE_NOT_SELECTING","项目状态不是选标中状态"),
    PROJECTS_STATE_NOT_SELECTED("PROJECTS_STATE_NOT_SELECTED","项目状态不是进行中状态"),
    PROJECTS_STATE_NOT_CHECK("PROJECTS_STATE_NOT_CHECK","项目状态不是待验收状态"),
    PROJECTS_STATE_NOT_OVER("PROJECTS_STATE_NOT_OVER","项目状态不是已完成状态"),
    PROJECTS_ALREADY_SIGNUP("PROJECTS_ALREADY_SIGNUP","项目已报名过"),
    PROJECTS_TENDER_USER_STATE_INVALID("PROJECTS_TENDER_USER_STATE_INVALID","用户投标状态不正确"),
    PROJECTS_NOT_SUPPORT_REVEAL("PROJECTS_NOT_SUPPORT_REVEAL","项目不支持兜底服务"),
    PROJECTS_TENDER_NOT_EXIST("PROJECTS_TENDER_NOT_EXIST","中标人不存在"),
    PROJECTS_REPORT_PHOTO_WRONG("PROJECTS_REPORT_PHOTO_WRONG","工作内容中照片错误"),
    PROJECTS_APPEAL_ATTACH_WRONG("PROJECTS_APPEAL_ATTACH_WRONG","申诉附件错误"),
    PROJECTS_NOT_USER("PROJECTS_NOT_USER","不是当前用户的项目"),
    PROJECTS_EVALUATE_TYPE_WRONG("PROJECTS_EVALUATE_TYPE_WRONG","评价目标类型错误"),
    PROJECTS_EMPLOYEE_IS_CHECK("PROJECTS_EMPLOYEE_IS_CHECK","项目已确认完成"),
    PROJECTS_SUBMITDEADLINE_LOW_APPLYDEADLINE("PROJECTS_SUBMITDEADLINE_LOW_APPLYDEADLINE","项目完成时间不能小于项目报名截止时间"),
    PROJECTS_NOT_PAY_REVEALMONEY("PROJECTS_NOT_PAY_REVEALMONEY","项目未购买兜底服务"),
    USER_ALREADY_EVALUATE("USER_ALREADY_EVALUATE","用户已评价"),
    
    MESSAGE_NOT_EXIST("MESSAGE_NOT_EXIST","消息不存在"),
    
    /**
     * 上传错误
     */
    FILE_IS_EMPTY("file_is_empty","请上传相关文件"),
    FILE_EXT_WRONG("file_ext_wrong","文件格式错误"),
    FILE_UPLOAD_FAIL("file_upload_fail","文件上传失败"),
    FILE_SIZE_OVER("file_size_over","文件过大"),
    
    /**
     * 资金错误
     */
    BALANCE_NOT_ENOUGH("balance_not_enough","余额不足"),
    WITHDRAW_APPLY_FAIL("withdraw_apply_fail","提现失败"),
    RECHARGE_APPLY_FAIL("recharge_apply_fail","充值失败"), 
    UNION_PAY_TN_FAIL("union_pay_tn_fail","银联支付TN号获取失败"),
    UNION_PAY_VALID_FAIL("union_pay_vaild_fail","银联支付验签失败"), 
    UNION_PAY_HTTP_FAIL("union_pay_http_fail","银联支付HTTP请求失败"),
    WECHAT_PAY_HTTP_FAIL("wechat_pay_http_fail","微信支付HTTP请求失败"),
    ALI_PAY_HTTP_FAIL("ali_pay_http_fail","阿里支付HTTP请求失败"),
    BANK_NAME_BLANK("bank_name_blank","汇款银行不能为空"),
    BANK_ACCOUNT_NAME_BLANK("bank_account_name_blank","转账户名不能为空"),
    BANK_ACCOUNT_NUNBER_BLANK("bank_account_number_blank","转账账号不能为空"),
    BANK_SERIAL_ID_BLANK("bank_serial_id_blank","交易流水号不能为空"),
    OUT_TRADE_NO_BLANK("out_trade_no_blank","商户订单号不能为空"),
    TRADE_NOT_EXIST("trade_no_blank","交易不存在")
    
    ;

    ErrorCode(String code,String msg){
        this.code = code;
        this.msg = msg;
    }
    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
