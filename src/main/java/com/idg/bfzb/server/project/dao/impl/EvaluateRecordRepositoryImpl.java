package com.idg.bfzb.server.project.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.project.dao.EvaluateRecordRepositoryCust;
import com.idg.bfzb.server.project.model.dto.EvaluateRecordEntity;
import com.idg.bfzb.server.project.model.vo.EvaluateListVo;
import com.idg.bfzb.server.project.model.vo.ProjectListVo;
import com.idg.bfzb.server.utility.tools.StringUtil;

public class EvaluateRecordRepositoryImpl implements EvaluateRecordRepositoryCust {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public double findAverageStar(String targetUserId) {
		StringBuilder sqlStmt = new StringBuilder();
		Map<String, Object> sqlParameters = new HashMap<String, Object>();
		
		sqlStmt.append(" select sum(star)/count(evaluate_id) cnt from t_evaluate_record where target_owner_id=:targetUserId ");
		sqlParameters.put("targetUserId", targetUserId);
        
        try {
        	Map<String, Object> map = this.jdbcTemplate.queryForMap(sqlStmt.toString(), sqlParameters);
        	Object cnt = map.get("cnt");
        	if(cnt!=null){
        		return Double.valueOf(String.valueOf(cnt));
        	}
        } catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	@Override
	public PageInfo<EvaluateListVo> findEvaluateListVoByCondition(Integer start, Integer size, EvaluateRecordEntity evaluateRecordEntity) {
		PageInfo<EvaluateListVo> pageInfo = new PageInfo<EvaluateListVo>();
		
        StringBuilder sqlStmt = new StringBuilder();
        
        sqlStmt.append(" select e.content,e.star,uc.user_id,uc.user_name,uc.nick_name,uc.icon_small_attch_id,uc.icon_small_url,e.create_time ")
        	   .append(" from t_evaluate_record e left join uc_user_info uc on e.target_owner_id=uc.user_id where 1=1 ");
        
        if(!StringUtil.isNull(evaluateRecordEntity.getTargetOwnerId())){
        	sqlStmt.append(" and e.target_owner_id='").append(evaluateRecordEntity.getTargetOwnerId()).append("'");
        }
        if(!StringUtil.isNull(evaluateRecordEntity.getTargetType())){
        	sqlStmt.append(" and e.target_type='").append(evaluateRecordEntity.getTargetType()).append("'");
        }

        try {
        	pageInfo.setTotalRows(jdbcTemplate.query(sqlStmt.toString(), BeanPropertyRowMapper.newInstance(ProjectListVo.class)).size());
        	
        	sqlStmt.append(" order by e.create_time desc limit ").append(start).append(",").append(size);
        	
        	pageInfo.setPageData(this.jdbcTemplate.query(sqlStmt.toString(), BeanPropertyRowMapper.newInstance(EvaluateListVo.class)));
        } catch (Exception e) {
        	pageInfo.setTotalRows(0);
			e.printStackTrace();
		}
        
        return pageInfo;
	}

	@Override
	public int countByCondition(EvaluateRecordEntity entity) {
		StringBuilder sqlStmt = new StringBuilder();
		Map<String, Object> sqlParameters = new HashMap<String, Object>();
		
		sqlStmt.append(" select count(evaluate_id) cnt from t_evaluate_record where 1=1 ");
		if(StringUtils.isNotBlank(entity.getTargetId())){
			sqlStmt.append(" and target_id=:targetId");
			sqlParameters.put("targetId", entity.getTargetId());
		}
		if(StringUtils.isNotBlank(entity.getTargetOwnerId())){
			sqlStmt.append(" and target_owner_id=:targetOwnerId");
			sqlParameters.put("targetOwnerId", entity.getTargetOwnerId());
		}
		if(StringUtils.isNotBlank(entity.getUserId())){
			sqlStmt.append(" and user_id=:userId");
			sqlParameters.put("userId", entity.getUserId());
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

}
