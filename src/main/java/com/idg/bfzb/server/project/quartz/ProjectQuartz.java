package com.idg.bfzb.server.project.quartz;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.idg.bfzb.server.common.Constants;
import com.idg.bfzb.server.common.MsgConstants;
import com.idg.bfzb.server.common.tools.MessageCommon;
import com.idg.bfzb.server.common.tools.ProjectCommon;
import com.idg.bfzb.server.project.dao.ProjectRepository;
import com.idg.bfzb.server.project.model.dto.ProjectEntity;

/**
 * 类名称：ProjectQuartz
 * 类描述：项目管理定时器
 * 创建人：ouzhb
 * 创建时间：2016/12/3
 */
public class ProjectQuartz {
	@Autowired
	private ProjectRepository projectRepository;
	@Resource
	private ProjectCommon projectCommon;
	@Resource
	private MessageCommon messageCommon;
	/**
	 * 自动结算待验收中项目
	 */
	@Transactional
	public void autoPayInCheckProject(){
		try{
			List<ProjectEntity> list = projectRepository.findProjectByStateAndTimer(Constants.PROJECT_STATE_CHECK, Constants.PROJECT_TIME_DAY_7);
			for(ProjectEntity project:list){
				Double tradeMoney = project.getTradeMoney();
				Short isMargin = project.getIsMargin();
				Float marginScale = project.getMarginScale();
				Float imprestScale = project.getImprestScale();
				String tenderUserId = project.getTenderUserId();
				String projectId = project.getProjectId();
				
				Double remainMoney = tradeMoney*(1-imprestScale);//尾款
				if(remainMoney>0){
					boolean userPayToPlatform = projectCommon.userPayToPlatform(project.getEmployerId(), remainMoney, projectId, Constants.TARGET_TYPE_REMAINMONEY);
					if(!userPayToPlatform)	continue;
					//发送消息
					String msgStr = MsgConstants.EMPLOYER_PAYMONEY_RECEIVER.replace("${projectName}", project.getProjectName())
									.replace("${tradeMoney}", String.valueOf(tradeMoney));
					messageCommon.sendMessage(Constants.MESSAGE_CHANNEL_PUSH, msgStr, project.getEmployerId());
				}
				Double toMoney = tradeMoney;
				boolean platformPayToUser = false;
				String msgStr = "";
				//是否支持质保
				if(Constants.PROJECT_IS_MARGIN_YES==isMargin){
					toMoney-=toMoney*marginScale;//尾款中扣除质保金
					platformPayToUser = projectCommon.platformPayToUser(tenderUserId, toMoney, projectId, Constants.TARGET_TYPE_IMPRESTMONEY);
					if(platformPayToUser){
						msgStr = MsgConstants.RECEIVER_GETMONEY_IMPREST.replace("${projectName}", project.getProjectName())
								.replace("${imprestScale}", String.valueOf((1-marginScale)*100))
								.replace("${marginScale}", String.valueOf(marginScale*100))
								.replace("${marginDay}", String.valueOf(project.getMarginDay()));
					}
				}else{
					platformPayToUser = projectCommon.platformPayToUser(tenderUserId, toMoney, projectId, Constants.TARGET_TYPE_FULLMONEY);
					if(platformPayToUser){
						msgStr = MsgConstants.RECEIVER_GETMONEY_FULL.replace("${projectName}", project.getProjectName())
								.replace("${money}", String.valueOf(toMoney));
					}
				}
				//插入消息表
				if(platformPayToUser){
					messageCommon.sendMessage(Constants.MESSAGE_CHANNEL_PUSH, msgStr, tenderUserId);
				}
				//修改项目状态为已完成
				projectRepository.updateProjectStateByProjectId(projectId, Constants.PROJECT_STATE_OVER);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	/**
	 * 自动结算质保
	 */
	@Transactional
	public void autoPayWarranty(){
		try{
			List<ProjectEntity> list = projectRepository.findProjectWarrantyByAdminReviewState(Constants.PROJECT_WARRANTY_UNTREATED);
			for(ProjectEntity project:list){
				Double tradeMoney = project.getTradeMoney();
				Float marginScale = project.getMarginScale();
				String tenderUserId = project.getTenderUserId();
				String projectId = project.getProjectId();
				
				Double marginMoney = tradeMoney*marginScale;//质保金
				boolean platformPayToUser = projectCommon.platformPayToUser(tenderUserId, marginMoney, projectId, Constants.TARGET_TYPE_REMAINMONEY);
				if(platformPayToUser){
					String msgStr = MsgConstants.RECEIVER_GETMONEY_WARRANTY.replace("${projectName}", project.getProjectName())
									.replace("${marginMoney}", String.valueOf(marginMoney));
					messageCommon.sendMessage(Constants.MESSAGE_CHANNEL_PUSH, msgStr, tenderUserId);
					projectRepository.updateProjectWarranStateByProjectId(projectId, Constants.PROJECT_WARRANTY_AGREE);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	/**
	 * 自动结算未处理的拒收
	 */
	@Transactional
	public void autoPayRejection(){
		try{
			List<ProjectEntity> list = projectRepository.findProjectRejectionByAdminReviewState(Constants.PROJECT_REFUSE_ADMIN_UNTREATED, Constants.PROJECT_TIME_DAY_15);
			for(ProjectEntity project:list){
				projectCommon.rejectionReturnMoney(project.getTradeMoney(), project.getImprestScale(), 
						project.getProjectId(), project.getProjectName(), project.getEmployerId());
				//messageCommon.sendMessage(Constants.MESSAGE_CHANNEL_PUSH, MsgConstants.ADMIN_AGREEREFUSE_MSGTO_EMPLOYER, toUserId);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	/**
	 * 处理已延期项目
	 */
	public void doDelayProject(){
		try{
			//处理进行中已延期的项目
			List<ProjectEntity> list = projectRepository.findDelaySelectedProject(Constants.PROJECT_TIME_DAY_3);
			for(ProjectEntity project:list){
				String projectId = project.getProjectId();
				Double tradeMoney = project.getTradeMoney();
				Float imprestScale = project.getImprestScale();
				//退款给发包方
				projectCommon.platformPayToUser(project.getEmployerId(), tradeMoney*imprestScale, projectId, Constants.TARGET_TYPE_RETURNMONEY);
				projectRepository.updateProjectStateByProjectId(projectId, Constants.PROJECT_STATE_INVALID);
			}
			//处理待确认过期的招标项目
			List<ProjectEntity> toConfirmCommonProjects = projectRepository.findDelayToConfirmCommonProject();
			for(ProjectEntity project:toConfirmCommonProjects){
				projectRepository.updateProjectStateByProjectId(project.getProjectId(), Constants.PROJECT_STATE_INVALID);
				messageCommon.sendMessage(Constants.MESSAGE_CHANNEL_PUSH, MsgConstants.PROJECT_TOCONFIRM_INVALID.replace("${projectName}", project.getProjectName()), project.getEmployerId());
			}
			//处理待确认过期的包招项目
			List<ProjectEntity> toConfirmContainProjects = projectRepository.findDelayToConfirmContainProject(Constants.PROJECT_TIME_DAY_1);
			for(ProjectEntity project:toConfirmContainProjects){
				projectRepository.updateProjectStateByProjectId(project.getProjectId(), Constants.PROJECT_STATE_INVALID);
				messageCommon.sendMessage(Constants.MESSAGE_CHANNEL_PUSH, MsgConstants.PROJECT_TOCONFIRM_INVALID.replace("${projectName}", project.getProjectName()), project.getEmployerId());
			}
			//处理选标中过期的项目
			List<ProjectEntity> selectingProjects = projectRepository.findDelaySelectingProject(Constants.PROJECT_TIME_DAY_2);
			for(ProjectEntity project:selectingProjects){
				projectRepository.updateProjectStateByProjectId(project.getProjectId(), Constants.PROJECT_STATE_INVALID);
				messageCommon.sendMessage(Constants.MESSAGE_CHANNEL_PUSH, MsgConstants.PROJECT_SELECTING_INVALID.replace("${projectName}", project.getProjectName()), project.getEmployerId());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
