package com.idg.bfzb.server.controller.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idg.bfzb.server.controller.BaseController;
import com.idg.bfzb.server.tools.I18nProvider;
import com.idg.bfzb.server.usercenter.model.request.JpushRequest;
import com.idg.bfzb.server.usercenter.model.request.LoginRequest;
import com.idg.bfzb.server.usercenter.model.request.MobileRegisterRequest;
import com.idg.bfzb.server.usercenter.model.request.PlatformBindRequest;
import com.idg.bfzb.server.usercenter.model.request.PlatformLoginRequest;
import com.idg.bfzb.server.usercenter.model.request.PwdModifiedRequest;
import com.idg.bfzb.server.usercenter.model.request.RetrieveRequest;
import com.idg.bfzb.server.usercenter.model.request.UcUsersResumeRequest;
import com.idg.bfzb.server.usercenter.service.ShortMessageService;
import com.idg.bfzb.server.usercenter.service.TokenService;
import com.idg.bfzb.server.usercenter.service.UcCodeService;
import com.idg.bfzb.server.usercenter.service.UcJpushCodeService;
import com.idg.bfzb.server.usercenter.service.UserService;
import com.idg.bfzb.server.utility.tools.ConfigFileUtils;

/**
 * 类名称：UserCenterController
 * 类描述：用户中心controller
 * 创建人：jiangdong
 * 创建日期：2016/10/25
 */
@Controller
@RequestMapping("/api/users")
public class UserCenterController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private ShortMessageService shortMessageService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UcCodeService ucCodeService;
    @Autowired
    private UcJpushCodeService jpushCodeService;

    private Logger logger = LoggerFactory.getLogger(UserCenterController.class);

    @RequestMapping(value = "/actions/login" , method = RequestMethod.POST)
    @ResponseBody
    public Object doLogin(@RequestBody LoginRequest loginRequest,HttpServletRequest servletRequest){
        logger.debug(String.format("请求URL:%1$s,请求内容:%2$s..",servletRequest.getRequestURI(), loginRequest));
        return userService.doLogin(loginRequest);
    }

    @RequestMapping(value = "/actions/register" , method = RequestMethod.POST)
    @ResponseBody
    public Object registerByMobile(@RequestBody MobileRegisterRequest registerRequest){
        return userService.registerByMobile(registerRequest);
    }

    /**
     * 获取我的基本信息 [GET] /users/resume
     * @param servletRequest
     */
    @RequestMapping(value = "/resume", method = RequestMethod.GET)
    @ResponseBody
    public Object getUserResume(HttpServletRequest servletRequest) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
        //String userId = "09a64517-7ae6-4108-a054-65acc6859f45";
        String userId = super.getCurrentUser();
        return this.userService.getUserBaseInfo(userId);
    }
    
    /**
     * 修改用户基本信息 [PUT] /users/resume
     * @param servletRequest
     */
    @RequestMapping(value = "/resume", method = RequestMethod.PUT)
    @ResponseBody
    public Object modifyUserResume(@RequestBody UcUsersResumeRequest ucUsersResumeRequest) {
        String userId = super.getCurrentUser();
        return this.userService.modifyUserBaseInfo(userId, ucUsersResumeRequest);
    }

    /**
     * 3.2.5 修改密码
     *
     * @param pwdModifiedRequest 修改密码请求模型
     * @param servletRequest http请求
     */
    @RequestMapping(value = "/password/actions/modify", method = RequestMethod.POST)
    @ResponseBody
    public Object modifyUserPassword(@RequestBody PwdModifiedRequest pwdModifiedRequest, HttpServletRequest servletRequest) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
        String userId = super.getCurrentUser();

        return this.userService.modifyUserPassword(userId, pwdModifiedRequest);
    }

    /**
     * 退出登录 [GET] /api/users/actions/loginout
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/actions/loginout", method = RequestMethod.GET)
    @ResponseBody
    public Object getUserLoginout(HttpServletRequest servletRequest) {

        if (logger.isDebugEnabled()) {
            logger.debug(I18nProvider.getMessage("common.debug.controller",
                    servletRequest.getRequestURI(), ""));
        }
        String userId = super.getCurrentUser();
        return  this.userService.loginOut(userId);

    }

    /**
     * 3.2.10 找回密码
     *
     * @param retrieveRequest 找回密码请求模型
     * @param servletResponse http响应
     */
    @RequestMapping(value = "/password/actions/retrieve", method = RequestMethod.POST)
    @ResponseBody
    public Object retrievePassword(@RequestBody RetrieveRequest retrieveRequest,
                                 HttpServletResponse servletResponse, HttpServletRequest servletRequest) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.请求内容:%2$s..",servletRequest.getRequestURI(),retrieveRequest));
        }

        if (StringUtils.isEmpty(retrieveRequest.getAuthCode())) {
            /*验证码不存在 */
            return super.httpArgumentRequire("auth_code", logger);
        }
        if (StringUtils.isEmpty(retrieveRequest.getNewPassword())) {
            /*加密后的密码不存在 */
            return  super.httpArgumentRequire("new_password", logger);
        }

        return this.userService.retrievePassword( retrieveRequest);
    }

    /**
     * 3.2.10 短信验证码发送
     */
    @RequestMapping(value = "/sms/{mobile}/actions/send", method = RequestMethod.GET)
    @ResponseBody
    public Object sendVerificationCode(@PathVariable("mobile") String mobile, HttpServletRequest servletRequest) {
        return shortMessageService.sendVerificationCode(mobile);
    }
    
    /**
     * 3.2.10 输入验证码验证
     */
    @RequestMapping(value = "/sms/{mobile}/actions/{code}/check", method = RequestMethod.GET)
    @ResponseBody
    public Object sendVerificationCode(@PathVariable("mobile") String mobile, @PathVariable("code") String code, HttpServletResponse servletResponse, HttpServletRequest servletRequest) {
        return shortMessageService.checkVerificationCode(mobile,code);
    }
    
    /**
     * 查询城市列表
     * @param servletResponse
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/regions", method = RequestMethod.GET)
    @ResponseBody
    public Object queryCityMsg(HttpServletResponse servletResponse, HttpServletRequest servletRequest){
        String parentRegionId = servletRequest.getParameter("parentRegionId");
        return userService.queryCityMsg(ConfigFileUtils.regionAllVoList,parentRegionId);
    }

    /**
     * 选择角色 [POST] /api/users/roles/{role_name}/actions/switch
     * @param roleName 角色名称
     * @param servletRequest http请求
     * @return 角色切换结果
     */
    @RequestMapping(value = "/roles/{role_name}/actions/switch", method = RequestMethod.POST)
    @ResponseBody
    public Object switchRole(@PathVariable("role_name")String roleName, HttpServletRequest servletRequest){
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.请求内容:%2$s..",servletRequest.getRequestURI(),roleName));
        }

        String userId = super.getCurrentUser();
        return this.userService.switchRole(userId,roleName);
    }
    
    /**
     * 第三方应用登录 [POST] /api/users/platform/actions/login
     * @param platformLoginRequest
     * @return
     */
    @RequestMapping(value = "/platform/actions/login", method = RequestMethod.POST)
    @ResponseBody
    public Object loginPlatform(@RequestBody PlatformLoginRequest platformLoginRequest){

        return this.userService.loginPlatform(platformLoginRequest);
    }
    
    /**
     * 登录绑定第三方应用 [POST] /api/users/platform/actions/bind
     * @param platformBindRequest
     * @return
     */
    @RequestMapping(value = "/platform/actions/bind", method = RequestMethod.POST)
    @ResponseBody
    public Object bindPlatform(@RequestBody PlatformBindRequest platformBindRequest){

        return this.userService.bindPlatform(platformBindRequest);
    }
    
    /**
     * 用户极光ID注册 [GET] /api/users/jpush/actions/add
     * @param jpushCode	极光ID
     * @return
     */
    @RequestMapping(value = "/jpush/actions/bind", method = RequestMethod.POST)
    @ResponseBody
    public Object addJpushCode(@RequestBody JpushRequest jpushRequest){
    	
        return jpushCodeService.addJpushCode(super.getCurrentUser(), jpushRequest);
    }
}
