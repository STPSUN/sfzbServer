package com.idg.bfzb.server.adminproject.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.idg.bfzb.server.adminproject.dao.AdminProjectDao;
import com.idg.bfzb.server.adminproject.dao.RejectionAdminDao;
import com.idg.bfzb.server.adminproject.model.ProjectAdminResponse;
import com.idg.bfzb.server.adminproject.model.RejectionAdminRequest;
import com.idg.bfzb.server.adminproject.model.RejectionAdminResponse;
import com.idg.bfzb.server.adminproject.service.RejectionAdminService;
import com.idg.bfzb.server.common.Constants;
import com.idg.bfzb.server.common.MsgConstants;
import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.common.tools.MessageCommon;
import com.idg.bfzb.server.common.tools.ProjectCommon;
import com.idg.bfzb.server.file.model.dto.AttachmentEntity;
import com.idg.bfzb.server.utility.tools.ConfigFileUtils;

@Service
public class RejectionAdminServiceImpl implements RejectionAdminService{

	@Autowired
	private RejectionAdminDao rejectionAdminDao;
	@Autowired
	private AdminProjectDao projectDao;
	@Autowired
	private ProjectCommon projectCommnon;
	@Autowired
	private MessageCommon messageCommon;
	@Override
	public PageInfo<RejectionAdminResponse> getRejectionList(
			RejectionAdminRequest projectRequest, PageRequest pageable) {
		PageInfo<RejectionAdminResponse> pageInfo = this.rejectionAdminDao.findProjectRejectionListByCond(projectRequest, pageable);
		return pageInfo;
	}
	@Override
	public APIResponse auditPassRejection(String projectId, String reason) {
		APIResponse ret = new APIResponse();
		
		boolean result = rejectionAdminDao.auditPassRejection(projectId,reason);
		if(result){
			
			ProjectAdminResponse project = projectDao.findProjectById(projectId);
			Float imprestScale = 0f;
			if(project.getIsReveal() != null && project.getIsReveal().equals(Constants.PROJECT_REVEAL_OK)){
				imprestScale = project.getRevealScale();
			}
			//钱退回原账户
			projectCommnon.rejectionReturnMoney(project.getTradeMoney(), imprestScale , projectId, project.getProjectName(), project.getEmployerId());
			//订单状态改为已失效
			projectDao.updateProjectState(projectId, Constants.PROJECT_STATE_INVALID + "");
			ret.setSucess(true);
			ret.setMessage("拒收记录通过操作成功！");
			ret.setCode("0");
			try{
				/**
				 * 雇主
				 */
				//发送短信
				messageCommon.sendMessage(Constants.MESSAGE_CHANNEL_SMS, 
						MsgConstants.ADMIN_AGREEREFUSE_MSGTO_EMPLOYER.replace("${projectName}", project.getProjectName())
						.replace("${realName}", project.getRealName()), project.getEmployerId());
				//发送推送
				messageCommon.sendMessage(Constants.MESSAGE_CHANNEL_PUSH, 
						MsgConstants.ADMIN_AGREEREFUSE_MSGTO_EMPLOYER.replace("${projectName}", project.getProjectName())
						.replace("${realName}", project.getRealName()), project.getEmployerId());
				/**
				 * 接包者
				 */
				//发送短信
				messageCommon.sendMessage(Constants.MESSAGE_CHANNEL_SMS, 
						MsgConstants.ADMIN_AGREEREFUSE_MSGTO_RECEIVER.replace("${projectName}", project.getProjectName())
						.replace("${realName}", project.getTenderRealName()), project.getTenderUserId());
				//发送推送
				messageCommon.sendMessage(Constants.MESSAGE_CHANNEL_PUSH, 
						MsgConstants.ADMIN_AGREEREFUSE_MSGTO_RECEIVER.replace("${projectName}", project.getProjectName())
						.replace("${realName}", project.getTenderRealName()), project.getTenderUserId());
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}else {
			ret.setSucess(true);
			ret.setMessage("拒收记录通过操作失败！");
			ret.setCode("-1");
		}
		
		return ret;
	}
	@Override
	public APIResponse auditNotPassRejection(String projectId, String reason) {
		
		APIResponse ret = new APIResponse();
		boolean result = rejectionAdminDao.auditNotPassRejection(projectId,reason);
		if(result){
			//订单状态改为待验收
			projectDao.updateProjectState(projectId, Constants.PROJECT_STATE_CHECK + "");
			
			ret.setSucess(true);
			ret.setMessage("拒收记录驳回操作成功！");
			ret.setCode("0");
			
			try{
				
				ProjectAdminResponse project = projectDao.findProjectById(projectId);
				/**
				 * 雇主
				 */
				//发送短信
				messageCommon.sendMessage(Constants.MESSAGE_CHANNEL_SMS, 
						MsgConstants.ADMIN_NOTAGREEREFUSE_MSGTO_EMPLOYER.replace("${projectName}", project.getProjectName())
						.replace("${realName}", project.getRealName()), project.getEmployerId());
				//发送推送
				messageCommon.sendMessage(Constants.MESSAGE_CHANNEL_PUSH, 
						MsgConstants.ADMIN_NOTAGREEREFUSE_MSGTO_EMPLOYER.replace("${projectName}", project.getProjectName())
						.replace("${realName}", project.getRealName()), project.getEmployerId());
				/**
				 * 接包者
				 */
				//发送短信
				messageCommon.sendMessage(Constants.MESSAGE_CHANNEL_SMS, 
						MsgConstants.ADMIN_NOTAGREEREFUSE_MSGTO_RECEIVER.replace("${projectName}", project.getProjectName())
						.replace("${realName}", project.getTenderRealName()), project.getTenderUserId());
				//发送推送
				messageCommon.sendMessage(Constants.MESSAGE_CHANNEL_PUSH, 
						MsgConstants.ADMIN_NOTAGREEREFUSE_MSGTO_RECEIVER.replace("${projectName}", project.getProjectName())
						.replace("${realName}", project.getTenderRealName()), project.getTenderUserId());
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}else {
			ret.setSucess(true);
			ret.setMessage("拒收记录驳回操作失败！");
			ret.setCode("-1");
		}
		
		return ret;
	}
	@Override
	public APIResponse getAttachsPath(String[] attachIds) {
		APIResponse ret = new APIResponse();
		
		List<AttachmentEntity> attachs = rejectionAdminDao.getAttachsPath(attachIds);
		
		for (AttachmentEntity attachmentEntity : attachs) {
			attachmentEntity.setAttchUrl(ConfigFileUtils.HEAD_ICON_URL+attachmentEntity.getAttchUrl());
		}
		ret.setSucess(true);
		ret.setData(attachs);
		ret.setMessage("查询附件地址成功！");
		ret.setCode("0");
		
		return ret;
	}

}
