package com.idg.bfzb.server.controller.api;

import javax.servlet.http.HttpServletRequest;

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
import com.idg.bfzb.server.team.model.request.TeamModifyRequest;
import com.idg.bfzb.server.team.model.request.TeamSetRequest;
import com.idg.bfzb.server.team.service.TeamService;

/**
 * 类名称：TeamController
 * 类描述：团队管理controller
 * 创建人：ouzhb
 * 创建时间：2016/10/30
 */
@Controller
@RequestMapping("/api/teams")
public class TeamController extends BaseController{
	
	private final Logger logger = LoggerFactory.getLogger(TeamController.class);
	
	@Autowired
	private TeamService teamService;
	/**
	 * 创建团队（申请团队认证） [POST] /api/teams/actions/add
	 * @param teamSetRequest
	 * @param servletRequest
	 * @return
	 */
	@RequestMapping(value = "/actions/add" , method = RequestMethod.POST)
    @ResponseBody
    public Object setTeam(@RequestBody TeamSetRequest teamSetRequest, HttpServletRequest servletRequest){
		if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
		
		String userId = super.getCurrentUser();
		
        return teamService.setTeam(userId, teamSetRequest);
    }
	@RequestMapping(value = "/actions/update", produces = "application/json;charset=UTF-8", method = RequestMethod.PUT)
    @ResponseBody
    public Object modifyTeam(@RequestBody TeamModifyRequest teamModifyRequest, HttpServletRequest servletRequest){
		if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
		
		String userId = super.getCurrentUser();
		
        return teamService.modifyTeam(userId, teamModifyRequest);
    }
	/**
	 * 团队认证详情查询 [GET] /api/teams/actions/detail
	 * @param teamId
	 * @param servletRequest
	 * @return
	 */
	@RequestMapping(value = "/actions/{teamId}/detail" , method = RequestMethod.GET)
    @ResponseBody
    public Object detail(@PathVariable("teamId") String teamId, HttpServletRequest servletRequest){
		if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
		
		//String userId = super.getCurrentUser();
		
        return teamService.detail(teamId);
    }
}
