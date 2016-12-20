package com.idg.bfzb.server.project.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.idg.bfzb.server.common.Constants;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.project.dao.ProjectTenderRepositoryCust;
import com.idg.bfzb.server.project.model.dto.ProjectTenderEntity;
import com.idg.bfzb.server.project.model.response.ProjectStateTotalResponse;
import com.idg.bfzb.server.project.model.vo.ProjectListVo;
import com.idg.bfzb.server.project.model.vo.ProjectQryVo;
import com.idg.bfzb.server.project.model.vo.TenderUserDetailVo;
import com.idg.bfzb.server.utility.tools.StringUtil;

public class ProjectTenderRepositoryImpl implements ProjectTenderRepositoryCust {
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	private final static String COLUMN = "record_id,project_id,tender_user_id,offer,plan,description,complete_time,create_time,update_time,state,opinion ";

	@Override
	public PageInfo<ProjectListVo> findProjectTenderByPageAndCondition(
			int start, int size, ProjectQryVo projectQryVo) {
		PageInfo<ProjectListVo> pageInfo = new PageInfo<ProjectListVo>();
		
        StringBuilder sqlStmt = new StringBuilder();
        
        sqlStmt.append(" select p.project_id,p.project_name,p.project_type,p.tender_type,p.budget,p.category_ids,pt.offer trade_money,")
               .append(" p.is_margin, p.margin_day, p.margin_scale,p.region_province, p.region_city, p.region_area, p.longitude,p.latitude,")
        	   .append(" DATE_FORMAT(p.apply_deadline,'%Y-%m-%d') apply_deadline,DATE_FORMAT(p.submit_deadline,'%Y-%m-%d') submit_deadline,")
        	   .append(" p.sign_up_count,p.state project_state,pt.state tender_state,DATE_FORMAT(p.create_time,'%Y-%m-%d') create_time ")
        	   .append(" from t_project_tender pt left join t_project p on pt.project_id=p.project_id where p.tender_type='common_tender' ");
        
        if(!StringUtil.isNull(projectQryVo.getProjectName())){
        	sqlStmt.append(" and p.project_name like '%").append(projectQryVo.getProjectName()).append("%' ");
        }
        if(!StringUtil.isNull(projectQryVo.getCategoryId())){
        	sqlStmt.append(" and p.category_ids like '%").append(projectQryVo.getCategoryId()).append("%' ");
        }
        if(projectQryVo.getState()!=null){
        	sqlStmt.append(" and p.state=").append(projectQryVo.getState());
        }
        if(projectQryVo.getDeadline()!=null){
        	sqlStmt.append(" and datediff(p.apply_deadline,CURDATE())>=").append(projectQryVo.getDeadline());
        }
        if(projectQryVo.getMinBudget()!=null){
        	sqlStmt.append(" and p.budget>=").append(projectQryVo.getMinBudget());
        }
        if(projectQryVo.getMaxBudget()!=null){
        	sqlStmt.append(" and p.budget<=").append(projectQryVo.getMaxBudget());
        }
        if(!StringUtil.isNull(projectQryVo.getTenderUserId())){
        	sqlStmt.append(" and pt.tender_user_id='").append(projectQryVo.getTenderUserId()).append("'");
        }

        try {
        	pageInfo.setTotalRows(jdbcTemplate.query(sqlStmt.toString(), BeanPropertyRowMapper.newInstance(ProjectListVo.class)).size());
        	
        	sqlStmt.append(" order by p.create_time desc limit ").append(start).append(",").append(size);
        	
        	pageInfo.setPageData(this.jdbcTemplate.query(sqlStmt.toString(), BeanPropertyRowMapper.newInstance(ProjectListVo.class)));
        } catch (Exception e) {
        	pageInfo.setTotalRows(0);
			e.printStackTrace();
		}
        
        return pageInfo;
	}

	@Override
	public PageInfo<TenderUserDetailVo> findTenderUserByCondition(int start, int size, ProjectTenderEntity projectTenderEntity) {
		PageInfo<TenderUserDetailVo> pageInfo = new PageInfo<TenderUserDetailVo>();
		
        StringBuilder sqlStmt = new StringBuilder();
        Map<String, Object> sqlParameters = new HashMap<String, Object>();
        
        sqlStmt.append(" select pt.description, pt.offer, pt.tender_user_id, uc.user_name, uc.icon_small_attch_id, uc.icon_small_url,uc.nick_name ")
        	   .append(" from t_project_tender pt left join uc_user_info uc on pt.tender_user_id=uc.user_id where 1=1 ");
        
        if(projectTenderEntity.getState()!=null){
        	sqlStmt.append(" and pt.state=:state");
        	sqlParameters.put("state", projectTenderEntity.getState());
        }
        if(!StringUtil.isNull(projectTenderEntity.getTenderUserId())){
        	sqlStmt.append(" and pt.tender_user_id=:tenderUserId");
        	sqlParameters.put("tenderUserId", projectTenderEntity.getTenderUserId());
        }
        if(!StringUtil.isNull(projectTenderEntity.getProjectId())){
        	sqlStmt.append(" and pt.project_id=:projectId");
        	sqlParameters.put("projectId", projectTenderEntity.getProjectId());
        }
        if(!StringUtil.isNull(projectTenderEntity.getProjectId())){
        	sqlStmt.append(" and pt.project_id=:projectId");
        	sqlParameters.put("projectId", projectTenderEntity.getProjectId());
        }
        try {
        	pageInfo.setTotalRows(jdbcTemplate.query(sqlStmt.toString(), sqlParameters, BeanPropertyRowMapper.newInstance(ProjectListVo.class)).size());
        	
        	sqlStmt.append(" limit ").append(start).append(",").append(size);
        	
        	pageInfo.setPageData(this.jdbcTemplate.query(sqlStmt.toString(), sqlParameters, BeanPropertyRowMapper.newInstance(TenderUserDetailVo.class)));
        } catch (Exception e) {
        	pageInfo.setTotalRows(0);
			e.printStackTrace();
		}
        
        return pageInfo;
	}

	@Override
	public int countByCondition(ProjectTenderEntity projectTenderEntity) {
		StringBuilder sqlStmt = new StringBuilder();
		Map<String, Object> sqlParameters = new HashMap<String, Object>();
		
		sqlStmt.append(" select count(pt.record_id) cnt from t_project_tender pt,t_project p where pt.project_id=p.project_id ");
		
		if(projectTenderEntity.getState()!=null){
        	sqlStmt.append(" and pt.state=:state");
        	sqlStmt.append(" and p.state=:state");
        	sqlParameters.put("state", projectTenderEntity.getState());
        	sqlParameters.put("state", Constants.PROJECT_STATE_OVER);
        }
        if(!StringUtil.isNull(projectTenderEntity.getTenderUserId())){
        	sqlStmt.append(" and pt.tender_user_id=:tenderUserId");
        	sqlParameters.put("tenderUserId", projectTenderEntity.getTenderUserId());
        }
        if(!StringUtil.isNull(projectTenderEntity.getProjectId())){
        	sqlStmt.append(" and pt.project_id=:projectId");
        	sqlParameters.put("projectId", projectTenderEntity.getProjectId());
        }
        
        try {
        	Map<String, Object> map = this.jdbcTemplate.queryForMap(sqlStmt.toString(), sqlParameters);
        	Object cnt = map.get("cnt");
        	if(cnt!=null){
        		return Integer.valueOf(String.valueOf(cnt));
        	}
        } catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	@Override
	public List<ProjectTenderEntity> findProjectTenderByProjectId(String projectId) {
		List<ProjectTenderEntity> list = null;
		
		StringBuilder sqlStmt = new StringBuilder();
		
		sqlStmt.append("select ").append(COLUMN).append(" from t_project_tender where project_id=:projectId");
		
		Map<String, Object> sqlParameters = new HashMap<String, Object>();
		sqlParameters.put("projectId", projectId);
		
		try{
			list = this.jdbcTemplate.query(sqlStmt.toString(), sqlParameters, BeanPropertyRowMapper.newInstance(ProjectTenderEntity.class));
		} catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public List<ProjectStateTotalResponse> countStateByTenderUserId(
			String tenderUserId) {
		List<ProjectStateTotalResponse> list = null;
		
		StringBuilder sqlStmt = new StringBuilder();
		sqlStmt.append(" select count(pt.project_id) total, p.state ")
			   .append(" from t_project_tender pt left join t_project p on pt.project_id=p.project_id ")
			   .append("  where p.tender_type=:tenderType and pt.tender_user_id=:tenderUserId group by p.state");
		
		Map<String, Object> sqlParameters = new HashMap<String, Object>();
		sqlParameters.put("tenderType", Constants.PROJECT_TENDER_COMMON);
		sqlParameters.put("tenderUserId", tenderUserId);
		
		try{
			list = this.jdbcTemplate.query(sqlStmt.toString(), sqlParameters, BeanPropertyRowMapper.newInstance(ProjectStateTotalResponse.class));
		} catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}
}
