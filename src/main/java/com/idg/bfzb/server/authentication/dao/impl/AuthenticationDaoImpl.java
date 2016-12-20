package com.idg.bfzb.server.authentication.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.idg.bfzb.server.authentication.dao.AuthenticationDao;
import com.idg.bfzb.server.authentication.model.EnterpriceAuthRequest;
import com.idg.bfzb.server.authentication.model.UserAuthRequest;
import com.idg.bfzb.server.authentication.model.UserEnterpriceVo;
import com.idg.bfzb.server.authentication.model.UserPersonalVo;
import com.idg.bfzb.server.authentication.model.dto.UserEnterpriseEntity;
import com.idg.bfzb.server.authentication.model.dto.UserPersonalEntity;
import com.idg.bfzb.server.utility.tools.ZhCN2Spell;

/**
 * 类名称：
 * 类描述：
 * 创建人：chen
 * 创建日期：2016/10/30
 */
@Repository
public class AuthenticationDaoImpl implements AuthenticationDao {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    
	@Override
	public UserPersonalEntity queryUserAuthenticationInfo(String userId) {
		StringBuilder sqlStmt = new StringBuilder();
        sqlStmt.append("SELECT idcard_code,balance,incoming,expenditure,alipay_code,bank_card_code,wechat_code,idcard_photo1_id,idcard_photo2_id,"
        		+ "idcard_photo3_id,is_team_user,is_enterprise_user,review_state,"
        		+ " (select attch.attch_url from t_attachment attch where attch.attch_id = t.idcard_photo1_id and attch.state=0 limit 1) front_image_url,"
        		+ " (select attch.attch_url from t_attachment attch where attch.attch_id = t.idcard_photo2_id and attch.state=0 limit 1) back_image_url,"
        		+ " (select attch.attch_url from t_attachment attch where attch.attch_id = t.idcard_photo3_id and attch.state=0 limit 1) hold_image_url"
        		+ " FROM t_user_personal t " +
                " WHERE user_id=:userId limit 1");

        UserPersonalVo user = null;
        Map<String,String> sqlParamters = new HashMap<String,String>();
        sqlParamters.put("userId",userId);
        try {
        	user = this.jdbcTemplate.queryForObject(sqlStmt.toString(),sqlParamters, BeanPropertyRowMapper.newInstance(UserPersonalVo.class));
		} catch (EmptyResultDataAccessException e) {
			System.out.println("企业认证数据为空");
			return null;
		}
        return user;
	}
	@Override
	public UserEnterpriseEntity queryUserEnterpriceAuthenticationInfo(
			String userId) {
		StringBuilder sqlStmt = new StringBuilder();
        sqlStmt.append("SELECT user_id,enterprise_id,enterprise_name,region_code,business_license,business_scope,corporate,"
        		+ "mobile,business_license_image,review_state,"
        		+ "  (select attch.attch_url from t_attachment attch where attch.attch_id = t.business_license_image and attch.state=0 limit 1) business_license_image_url " +
                "  FROM t_user_enterprise t "
                + " WHERE user_id=:userId limit 1");

        UserEnterpriceVo user = null;
        Map<String,String> sqlParamters = new HashMap<String,String>();
        sqlParamters.put("userId",userId);
        try {
        	user = this.jdbcTemplate.queryForObject(sqlStmt.toString(),sqlParamters, BeanPropertyRowMapper.newInstance(UserEnterpriceVo.class));
		} catch (EmptyResultDataAccessException e) {
			System.out.println("企业认证数据为空");
			return null;
		}
        return user;
	}
	@Override
	public int addPersonAuthentication(String userId, UserAuthRequest user) {
		StringBuilder sqlStmt = new StringBuilder();
        sqlStmt.append("insert into t_user_personal (user_id,idcard_code,idcard_photo1_id,idcard_photo2_id,idcard_photo3_id,review_state) "
        		+ " values (:user_id,:idcard_photo1_id,:idcard_photo2_id,:idcard_photo3_id,:idcard_code,'0')");

        int result = 0;
        Map<String,String> sqlParamters = new HashMap<String,String>();
        sqlParamters.put("user_id",userId);
        sqlParamters.put("idcard_code",user.getIdCardNumber());
        
        sqlParamters.put("idcard_photo1_id",user.getFrontBatchId());
        sqlParamters.put("idcard_photo2_id",user.getBackBatchId());
        sqlParamters.put("idcard_photo3_id",user.getHoldBatchId());
        //真实姓名拼音和缩写
        String realFullSpell = ZhCN2Spell.converterToSpell(user.getUserRealName());
        String realShortSpell = ZhCN2Spell.converterToFirstSpell(user.getUserRealName());
        StringBuilder sqlStmt2 = new StringBuilder();
        sqlStmt2.append("update uc_user_info set real_name = :userRealName,real_full_spell=:realFullSpell,real_short_spell=:realShortSpell"
        		+ " where user_id = :user_id");

        Map<String,String> sqlParamters2 = new HashMap<String,String>();
        sqlParamters2.put("user_id",userId);
        sqlParamters2.put("userRealName",user.getUserRealName());
        sqlParamters2.put("realFullSpell",realFullSpell);
        sqlParamters2.put("realShortSpell",realShortSpell);
        
        try {
        	result = this.jdbcTemplate.update(sqlStmt.toString(), sqlParamters);
        	this.jdbcTemplate.update(sqlStmt2.toString(), sqlParamters2);
		} catch (EmptyResultDataAccessException e) {
			System.out.println("企业认证数据为空");
			return 0;
		}
        return result;
	}
	@Override
	public int addEnterpriseAuthentication(String userId,
			EnterpriceAuthRequest enterprice) {
		StringBuilder sqlStmt = new StringBuilder();
		String enterUuid = UUID.randomUUID().toString();
        sqlStmt.append("insert into t_user_enterprise (user_id,enterprise_name,region_code,business_license,business_scope,corporate,mobile,review_state,enterprise_id,business_license_image) "
        		+ " values (:user_id,:enterprise_name,:region_code,:business_license,:business_scope,:corporate,:mobile,'0','"+enterUuid+"',:business_license_image)");

        int result = 0;
        Map<String,String> sqlParamters = new HashMap<String,String>();
        sqlParamters.put("user_id",userId);
        sqlParamters.put("enterprise_name",enterprice.getEnterpriseName());
        sqlParamters.put("region_code",enterprice.getEnterpriseCity());
        sqlParamters.put("business_license",enterprice.getEnterpriseNumber());
        sqlParamters.put("business_scope",enterprice.getBusinessScope());
        sqlParamters.put("corporate",enterprice.getBusinessEntity());
        sqlParamters.put("mobile",enterprice.getPhoneNumber());
        
        sqlParamters.put("business_license_image",enterprice.getBusinessLicenseImage());
        
        try {
        	result = this.jdbcTemplate.update(sqlStmt.toString(), sqlParamters);
		} catch (EmptyResultDataAccessException e) {
			System.out.println("企业认证数据为空");
			return 0;
		}
        return result;
	}
	@Override
	public int auditPersonAuthentication(String userId, String audituserId,
			String action) {
		
		int result = 0;
		StringBuilder sqlStmt = new StringBuilder();
        sqlStmt.append("update t_user_personal set review_state = :action where user_id = :user_id");

        Map<String,String> sqlParamters = new HashMap<String,String>();
        sqlParamters.put("user_id",userId);
        sqlParamters.put("action",action);
        try {
        	result = this.jdbcTemplate.update(sqlStmt.toString(), sqlParamters);
		} catch (EmptyResultDataAccessException e) {
			System.out.println("企业认证数据为空");
			return 0;
		}
        return result;
	}
	@Override
	public int auditEnterpriceAuthentication(String userId, String enterpriseId, String audituserId,
			String action,String reason) {
		int result = 0;
		StringBuilder sqlStmt = new StringBuilder();
        sqlStmt.append("update t_user_enterprise set review_state = :action,review_admin_id = :audituserId,reason = :reason where user_id = :userId");

        Map<String,String> sqlParamters = new HashMap<String,String>();
        sqlParamters.put("userId",userId);
        sqlParamters.put("action",action);
        sqlParamters.put("audituserId", audituserId);
        sqlParamters.put("enterpriseId", enterpriseId);
        sqlParamters.put("reason", reason);
        try {
        	result = this.jdbcTemplate.update(sqlStmt.toString(), sqlParamters);
        	//通过时将企业用户置为true
        	if(action.equals("1") && result > 0){
        		String enterUserSql = "update t_user_personal set is_enterprise_user = '1' where user_id = :userId";
        		Map<String,String> sqlParamters2 = new HashMap<String,String>();
        		sqlParamters2.put("userId",userId);
        		this.jdbcTemplate.update(enterUserSql, sqlParamters2);
        	}
		} catch (EmptyResultDataAccessException e) {
			System.out.println("企业认证数据为空");
			return 0;
		}
        return result;
	}
	@Override
	public int auditTeamAuthentication(String userId, String teamId, String audituserId,
			String action,String reason) {
		int result = 0;
		StringBuilder sqlStmt = new StringBuilder();
        sqlStmt.append("update t_user_team set review_state = :action,review_admin_id = :audituserId,reason = :reason where team_id = :team_id and team_leader_id = :userId");

        Map<String,String> sqlParamters = new HashMap<String,String>();
        sqlParamters.put("team_id",teamId);
        sqlParamters.put("userId",userId);
        sqlParamters.put("action",action);
        sqlParamters.put("audituserId",audituserId);
        sqlParamters.put("reason",reason);
        try {
        	result = this.jdbcTemplate.update(sqlStmt.toString(), sqlParamters);
        	//通过时将团队用户置为true
            if(action.equals("2") && result > 0){
                String enterUserSql = "update t_user_personal set is_team_user = '1' where user_id = :userId";
                Map<String,String> sqlParamters2 = new HashMap<String,String>();
                sqlParamters2.put("userId",userId);
                this.jdbcTemplate.update(enterUserSql, sqlParamters2);
            }
		} catch (EmptyResultDataAccessException e) {
			System.out.println("团队认证数据为空");
			return 0;
		}
        return result;
	}
	
	@Override
	public int updatePersonBalance(String userId, Double balance) {
		int result = 0;
		StringBuilder sqlStmt = new StringBuilder();
        sqlStmt.append("update t_user_personal set balance = balance + :balance where user_id = :userId");

        Map<String,Object> sqlParamters = new HashMap<String,Object>();
        sqlParamters.put("userId",userId);
        sqlParamters.put("balance",balance);
        try {
        	result = this.jdbcTemplate.update(sqlStmt.toString(), sqlParamters);
		} catch (EmptyResultDataAccessException e) {
			System.out.println("个人信息为空");
			return 0;
		}
        return result;
	}
	@Override
	public int updatePersonAuthentication(String userId, UserAuthRequest user) {
		StringBuilder sqlStmt = new StringBuilder();
        sqlStmt.append("update t_user_personal set idcard_code=:idcard_code,idcard_photo1_id=:idcard_photo1_id,idcard_photo2_id=:idcard_photo2_id,idcard_photo3_id=:idcard_photo3_id,review_state='0'"
        		+ " where user_id= :user_id");

        int result = 0;
        Map<String,String> sqlParamters = new HashMap<String,String>();
        sqlParamters.put("user_id",userId);
        sqlParamters.put("idcard_code",user.getIdCardNumber());
        
        sqlParamters.put("idcard_photo1_id",user.getFrontBatchId());
        sqlParamters.put("idcard_photo2_id",user.getBackBatchId());
        sqlParamters.put("idcard_photo3_id",user.getHoldBatchId());
        //真实姓名拼音和缩写
        String realFullSpell = ZhCN2Spell.converterToSpell(user.getUserRealName());
        String realShortSpell = ZhCN2Spell.converterToFirstSpell(user.getUserRealName());
        StringBuilder sqlStmt2 = new StringBuilder();
        sqlStmt2.append("update uc_user_info set real_name = :userRealName,real_full_spell=:realFullSpell,real_short_spell=:realShortSpell"
        		+ " where user_id = :user_id");

        Map<String,String> sqlParamters2 = new HashMap<String,String>();
        sqlParamters2.put("user_id",userId);
        sqlParamters2.put("userRealName",user.getUserRealName());
        sqlParamters2.put("realFullSpell",realFullSpell);
        sqlParamters2.put("realShortSpell",realShortSpell);
        
        try {
        	result = this.jdbcTemplate.update(sqlStmt.toString(), sqlParamters);
        	this.jdbcTemplate.update(sqlStmt2.toString(), sqlParamters2);
		} catch (EmptyResultDataAccessException e) {
			System.out.println("企业认证数据为空");
			return 0;
		}
        return result;
	}
	
	@Override
	public boolean userIsAudith(String userId){
	    try {
	        StringBuilder sqlStmt = new StringBuilder();
	        sqlStmt.append("select user_id from t_user_personal where user_id= :userId and review_state = 2");
	        Map<String,Object> sqlParamters = new HashMap<String,Object>();
	        sqlParamters.put("userId",userId);
	        List<Map<String, Object>> row = this.jdbcTemplate.queryForList(sqlStmt.toString(), sqlParamters);
	        if(row != null && !row.isEmpty() && row.size() >0){
	            return true;
	        }else{
	            return false;
	        }
        } catch (Exception e) {
            return false;
        }
	}
	@Override
	public int deleteEnterpriceAuthentication(String enterpriseId) {
		StringBuilder sqlStmt = new StringBuilder();
		sqlStmt.append("delete from t_user_enterprise where enterprise_id=:enterpriseId");
		
		Map<String,Object> sqlParamters = new HashMap<String,Object>();
        sqlParamters.put("enterpriseId",enterpriseId);
		
        try {
        	return this.jdbcTemplate.update(sqlStmt.toString(), sqlParamters);
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
        return 0;
	}
}
