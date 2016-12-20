package com.idg.bfzb.server.usercenter.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.idg.bfzb.server.common.Constants;
import com.idg.bfzb.server.common.dao.PageDao;
import com.idg.bfzb.server.common.model.AvailableEnum;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.usercenter.dao.UserInfoRepositoryCust;
import com.idg.bfzb.server.usercenter.model.dto.UcUserInfoEntity;
import com.idg.bfzb.server.usercenter.model.request.UserFinancialRequest;
import com.idg.bfzb.server.usercenter.model.request.UserManagerRequest;
import com.idg.bfzb.server.usercenter.model.response.UserFinancialResponse;
import com.idg.bfzb.server.usercenter.model.response.UserManagerResponse;
import com.idg.bfzb.server.usercenter.model.vo.RegionAllVo;
import com.idg.bfzb.server.utility.tools.StringUtil;

/**
 * 类名称：
 * 类描述：
 * 创建人：jiangdong
 * 创建日期：2016/10/26
 */
public class UserInfoRepositoryImpl implements UserInfoRepositoryCust{
    private Logger logger = LoggerFactory.getLogger(UserInfoRepositoryImpl.class);
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private PageDao pageDao;

    private final static String COLUMN = "user_id,org_id,realm,user_name,password,real_name,real_full_spell," +
            "real_short_spell,nick_name,nick_full_spell,nick_short_spell,sex,mobile,icon_attch_id,icon_url,icon_small_attch_id,icon_small_url,state,create_time,last_modified,weixin_id,qq_id ";

    /**
     * 用手机号码或者用户名查询用户
     * @param userName 用户名
     * @param mobile 手机号码
     * @return UcUserInfoEntity 用户实体对象
     */
    @Override
    public UcUserInfoEntity findByNameOrMobile(String userName, String mobile) {
        StringBuilder sqlStmt = new StringBuilder();
        sqlStmt.append("SELECT ").append(COLUMN).append(" FROM uc_user_info " +
                " WHERE (user_name=:userName or mobile=:mobile) AND state=:state");
        Map<String, Object> sqlParameters = new HashMap<>();
        sqlParameters.put("userName",userName);
        sqlParameters.put("mobile",mobile);
        sqlParameters.put("state", AvailableEnum.NORMAL.getValue());

        List<UcUserInfoEntity> results = this.jdbcTemplate.query(sqlStmt.toString(),sqlParameters,BeanPropertyRowMapper.newInstance(UcUserInfoEntity.class));
        if (results==null || results.size()<=0){
            return null;
        }

        if (results.size()>1){
            logger.warn(String.format("存在两个相同的用户名或手机号的用户:[user_name:%1$s,mobile%2$s]",userName,mobile));
        }
        return results.get(0);
    }
    
    /*
     * 查询地区信息
     */
    @Override
    public List<RegionAllVo> findAllRepository(String parentRegionId){
        StringBuilder sqlStmt = new StringBuilder();

        sqlStmt.append("SELECT t1.* FROM (")
            .append(" SELECT region_id,parent_code AS parent_region_id,region_code,region_name,full_spell,short_spell,'1' level")
            .append(" FROM uc_region WHERE parent_code = 1 AND state = 0 UNION")
            .append(" (SELECT region_id,parent_code AS parent_region_id,region_code,region_name,full_spell,short_spell,'2' level")
            .append(" FROM uc_region WHERE parent_code IN (SELECT region_id FROM uc_region WHERE parent_code = 1 AND state = 0)")
            .append(" AND state = 0) UNION (")
            .append(" SELECT region_id,parent_code AS parent_region_id,region_code,region_name,full_spell,short_spell,'3' level")
            .append(" FROM uc_region WHERE parent_code IN (")
            .append(" SELECT region_id FROM uc_region WHERE parent_code")
            .append(" IN (SELECT region_id FROM uc_region WHERE parent_code = 1 AND state = 0)")
            .append(" AND state = 0) AND state = 0 )) t1 ");
        if(!StringUtil.isNull(parentRegionId)){
            sqlStmt.append(" where t1.parent_region_id =").append(parentRegionId);
        }
        sqlStmt.append(" ORDER BY t1.level,t1.full_spell asc,t1.region_id");
        System.out.println(sqlStmt.toString());
        Map<String, Object> sqlParameters = new HashMap<>();

        List<RegionAllVo> results = this.jdbcTemplate.query(sqlStmt.toString(),sqlParameters,BeanPropertyRowMapper.newInstance(RegionAllVo.class));
        return results;
    }

	@Override
	public boolean isExistNickName(String userId,String nickName) {
		StringBuilder sqlStmt = new StringBuilder();
		sqlStmt.append("SELECT ").append(COLUMN).append(" FROM uc_user_info " +
                " WHERE nick_name=:nickName AND user_id!=:userId AND state=:state");
		Map<String, Object> sqlParameters = new HashMap<>();
        sqlParameters.put("nickName",nickName);
        sqlParameters.put("userId",userId);
        sqlParameters.put("state", AvailableEnum.NORMAL.getValue());
		List<UcUserInfoEntity> list = this.jdbcTemplate.query(sqlStmt.toString(),sqlParameters,BeanPropertyRowMapper.newInstance(UcUserInfoEntity.class));
		if(list!=null && list.size()>0){
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageInfo<UserManagerResponse> findUserListByCond(
			UserManagerRequest userManagerRequest, Pageable pageable) {
		StringBuilder sqlStmt = new StringBuilder();
		Map<String, Object> sqlParameters = new HashMap<>();
		String userAuthType = userManagerRequest.getUserAuthType();
		pageable.getPageNumber();
		pageable.getPageSize();
		if(userAuthType.equals(Constants.USER_AUTH_TYPE_NORMAL)){//普通用户
			Map<String, Object> sqlMap = this.getNormalUserSqlAndParam(userManagerRequest);
			sqlStmt = (StringBuilder)sqlMap.get("sqlStmt");
	        sqlParameters = (Map<String, Object>)sqlMap.get("sqlParameters");
		}else if(userAuthType.equals(Constants.USER_AUTH_TYPE_ENTERPRISE)){//企业用户
			Map<String, Object> sqlMap = this.getEntUserSqlAndParam(userManagerRequest);
			sqlStmt = (StringBuilder)sqlMap.get("sqlStmt");
	        sqlParameters = (Map<String, Object>)sqlMap.get("sqlParameters");
		}else if(userAuthType.equals(Constants.USER_AUTH_TYPE_TEAM)){//团队用户
		    Map<String, Object> sqlMap = this.getTeamUserSqlAndParam(userManagerRequest);
            sqlStmt = (StringBuilder)sqlMap.get("sqlStmt");
            sqlParameters = (Map<String, Object>)sqlMap.get("sqlParameters");
		}
		
		PageInfo<UserManagerResponse> pageInfo = new PageInfo<UserManagerResponse>();
		try {
			pageInfo = pageDao.findPageByCond(sqlStmt.toString(),sqlParameters,UserManagerResponse.class,pageable);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return pageInfo;
	}
	
	@SuppressWarnings("unchecked")
    @Override
    public PageInfo<UserManagerResponse> findUserAuthenticationListByCond(
            UserManagerRequest userManagerRequest, Pageable pageable) {
        StringBuilder sqlStmt = new StringBuilder();
        Map<String, Object> sqlParameters = new HashMap<>();
        String userAuthType = userManagerRequest.getUserAuthType();
        pageable.getPageNumber();
        pageable.getPageSize();
        if(userAuthType.equals(Constants.USER_AUTH_TYPE_NORMAL)){//普通用户
            Map<String, Object> sqlMap = this.getNormalUserSqlAndParamByUpdateTime(userManagerRequest);
            sqlStmt = (StringBuilder)sqlMap.get("sqlStmt");
            sqlParameters = (Map<String, Object>)sqlMap.get("sqlParameters");
        }else if(userAuthType.equals(Constants.USER_AUTH_TYPE_ENTERPRISE)){//企业用户
            Map<String, Object> sqlMap = this.getEntUserSqlAndParamByUpdateTime(userManagerRequest);
            sqlStmt = (StringBuilder)sqlMap.get("sqlStmt");
            sqlParameters = (Map<String, Object>)sqlMap.get("sqlParameters");
        }else if(userAuthType.equals(Constants.USER_AUTH_TYPE_TEAM)){//团队用户
            Map<String, Object> sqlMap = this.getTeamUserSqlAndParamByUpdateTime(userManagerRequest);
            sqlStmt = (StringBuilder)sqlMap.get("sqlStmt");
            sqlParameters = (Map<String, Object>)sqlMap.get("sqlParameters");
        }
        
        PageInfo<UserManagerResponse> pageInfo = new PageInfo<UserManagerResponse>();
        try {
            pageInfo = pageDao.findPageByCond(sqlStmt.toString(),sqlParameters,UserManagerResponse.class,pageable);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return pageInfo;
    }

	@SuppressWarnings("unchecked")
	@Override
	public UserManagerResponse findOneNormalUser(UserManagerRequest userManagerRequest) {
		UserManagerResponse userManagerResponse = new UserManagerResponse();
		Map<String, Object> sqlMap = this.getNormalUserSqlAndParam(userManagerRequest);
		StringBuilder sqlStmt = (StringBuilder)sqlMap.get("sqlStmt");
        Map<String, Object> sqlParameters = (Map<String, Object>)sqlMap.get("sqlParameters");
        try {
        	userManagerResponse = this.jdbcTemplate.queryForObject(sqlStmt.toString(),sqlParameters,
        			BeanPropertyRowMapper.newInstance(UserManagerResponse.class));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
        return userManagerResponse;
	}
	
	@SuppressWarnings("unchecked")
    @Override
    public PageInfo<UserFinancialResponse> getFinancialDetail(UserFinancialRequest userFinancialRequest,Pageable pageable) {
	    PageInfo<UserFinancialResponse> pageInfo = new PageInfo<UserFinancialResponse>();
        Map<String, Object> sqlMap = this.getUserFinancialSqlAndParam(userFinancialRequest);
        StringBuilder sqlStmt = (StringBuilder)sqlMap.get("sqlStmt");
        Map<String, Object> sqlParameters = (Map<String, Object>)sqlMap.get("sqlParameters");
        try {
            pageInfo = pageDao.findPageByCond(sqlStmt.toString(),sqlParameters,UserFinancialResponse.class,pageable);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return pageInfo;
    }
	
	@SuppressWarnings("unchecked")
    @Override
    public UserFinancialResponse queryBalance(UserFinancialRequest userManagerRequest){
	    UserFinancialResponse userFinancialResponse = new UserFinancialResponse();
        Map<String, Object> sqlMap = this.getBalanceSqlAndParam(userManagerRequest);
        StringBuilder sqlStmt = (StringBuilder)sqlMap.get("sqlStmt");
        Map<String, Object> sqlParameters = (Map<String, Object>)sqlMap.get("sqlParameters");
        try {
            userFinancialResponse = this.jdbcTemplate.queryForObject(sqlStmt.toString(),sqlParameters,
                    BeanPropertyRowMapper.newInstance(UserFinancialResponse.class));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return userFinancialResponse;
	}
	
	@SuppressWarnings("unchecked")
    @Override
    public List<UserFinancialResponse> queryImprest(UserFinancialRequest userManagerRequest){
        List<UserFinancialResponse> userFinancialResponseList = new ArrayList<UserFinancialResponse>();
        Map<String, Object> sqlMap = this.getImprestSqlAndParam(userManagerRequest);
        StringBuilder sqlStmt = (StringBuilder)sqlMap.get("sqlStmt");
        Map<String, Object> sqlParameters = (Map<String, Object>)sqlMap.get("sqlParameters");
        try {
        	userFinancialResponseList = this.jdbcTemplate.query(sqlStmt.toString(),sqlParameters,
        			BeanPropertyRowMapper.newInstance(UserFinancialResponse.class));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return userFinancialResponseList;
    }
	
	/**
	 * 生成普通用户查询sql
	 * @param userManagerRequest
	 * @return
	 */
	public Map<String,Object> getNormalUserSqlAndParam(UserManagerRequest userManagerRequest){
		Map<String,Object> map = new HashMap<String,Object>();
		StringBuilder sqlStmt = new StringBuilder();
		Map<String, Object> sqlParameters = new HashMap<>();
		sqlStmt.append(" SELECT")
		.append(" uc.user_id,")
		.append(" uc.org_id,")
		.append(" uc.realm,")
		.append(" uc.user_name,")
		.append(" uc.password,")
		.append(" uc.real_name,")
		.append(" uc.real_full_spell,")
		.append(" uc.real_short_spell,")
		.append(" uc.nick_name,")
		.append(" uc.nick_full_spell,")
		.append(" uc.nick_short_spell,")
		.append(" uc.sex,")
		.append(" uc.mobile,")
		.append(" uc.icon_attch_id,")
		.append(" uc.icon_url,")
		.append(" uc.icon_small_attch_id,")
		.append(" uc.icon_small_url,")
		.append(" uc.state,")
		.append(" uc.create_time,")
		.append(" uc.last_modified,")
		.append(" personal.idcard_code,")
		.append(" IFNULL(personal.balance, 0) balance,")
		.append(" IFNULL(personal.incoming, 0) incoming,")
		.append(" IFNULL(personal.expenditure, 0) expenditure,")
		.append(" personal.alipay_code,")
		.append(" personal.bank_card_code,")
		.append(" personal.wechat_code,")
		.append(" personal.idcard_photo1_id,")
		.append(" (select attch.attch_url from t_attachment attch where attch.attch_id = personal.idcard_photo1_id and attch.state=0 limit 1) idcard_photo1_url,")
		.append(" personal.idcard_photo2_id,")
		.append(" (select attch.attch_url from t_attachment attch where attch.attch_id = personal.idcard_photo2_id and attch.state=0 limit 1) idcard_photo2_url,")
		.append(" personal.idcard_photo3_id,")
		.append(" (select attch.attch_url from t_attachment attch where attch.attch_id = personal.idcard_photo3_id and attch.state=0 limit 1) idcard_photo3_url,")
		.append(" personal.last_province_code,")
		.append(" (select reg.region_name FROM uc_region reg WHERE reg.region_code=personal.last_province_code and reg.state=0 limit 1)  last_province_name,")
		.append(" personal.last_city_code,")
		.append(" (select reg.region_name FROM uc_region reg WHERE reg.region_code=personal.last_city_code and reg.state=0 limit 1)  last_city_name,")
		.append(" personal.last_area_code,")
		.append(" (select reg.region_name FROM uc_region reg WHERE reg.region_code=personal.last_area_code and reg.state=0 limit 1)  last_area_name,")
		.append(" personal.last_role,")
		.append(" personal.is_team_user,")
		.append(" personal.is_enterprise_user,")
		.append(" personal.update_time,")
		.append(" personal.review_state,")
		.append(" personal.review_admin_id,")
		.append(" (SELECT group_concat(ability_name) from t_ability WHERE ability_id IN (")
		.append(" SELECT ability FROM t_user_ability_assoc WHERE state = 1 and user_id =uc.user_id)) abilitys_name,")
		.append(" personal.review_time")
		.append(" FROM")
		.append(" uc_user_info uc")
		.append(" LEFT JOIN t_user_personal personal ON uc.user_id = personal.user_id")
		.append(" WHERE 1=1");
		//查询条件
		if(userManagerRequest.getUserId()!=null){
			sqlStmt.append(" and uc.user_id = :userId");
			sqlParameters.put("userId", userManagerRequest.getUserId());
		}
		if(userManagerRequest.getReviewState()!=null){
			sqlStmt.append(" and personal.review_state = :reviewState");
			sqlParameters.put("reviewState", userManagerRequest.getReviewState());
		}
		if(!StringUtil.isNull(userManagerRequest.getUserName())){
			sqlStmt.append(" and uc.user_name LIKE :userName");
			sqlParameters.put("userName", "%"+userManagerRequest.getUserName()+"%");
		}
		if(!StringUtil.isNull(userManagerRequest.getQryStartTime())){
			sqlStmt.append(" and uc.create_time >= :qryStartTime");
			sqlParameters.put("qryStartTime", userManagerRequest.getQryStartTime());
		}
		if(!StringUtil.isNull(userManagerRequest.getQryEndTime())){
			sqlStmt.append(" and uc.create_time <= ADDDATE(:qryEndTime, INTERVAL 1 DAY)");
			sqlParameters.put("qryEndTime", userManagerRequest.getQryEndTime());
		}
		if(!StringUtil.isNull(userManagerRequest.getNickName())){
            sqlStmt.append(" and uc.nick_name LIKE :nickName");
            sqlParameters.put("nickName", "%"+userManagerRequest.getNickName()+"%");
        }
		if(!StringUtil.isNull(userManagerRequest.getRealName())){
            sqlStmt.append(" and uc.real_name = :realName");
            sqlParameters.put("realName", userManagerRequest.getRealName());
        }
		if(!StringUtil.isNull(userManagerRequest.getIdcardCode())){
		    sqlStmt.append(" and personal.idcard_code = :idcardCode");
            sqlParameters.put("idcardCode", userManagerRequest.getIdcardCode());
		}

		sqlStmt.append(" order by uc.create_time desc");
		map.put("sqlStmt", sqlStmt);
		map.put("sqlParameters", sqlParameters);
		return map;
	}
	
	/**
     * 生成普通用户查询sql
     * @param userManagerRequest
     * @return
     */
    public Map<String,Object> getNormalUserSqlAndParamByUpdateTime(UserManagerRequest userManagerRequest){
        Map<String,Object> map = new HashMap<String,Object>();
        StringBuilder sqlStmt = new StringBuilder();
        Map<String, Object> sqlParameters = new HashMap<>();
        sqlStmt.append(" SELECT")
        .append(" uc.user_id,")
        .append(" uc.org_id,")
        .append(" uc.realm,")
        .append(" uc.user_name,")
        .append(" uc.password,")
        .append(" uc.real_name,")
        .append(" uc.real_full_spell,")
        .append(" uc.real_short_spell,")
        .append(" uc.nick_name,")
        .append(" uc.nick_full_spell,")
        .append(" uc.nick_short_spell,")
        .append(" uc.sex,")
        .append(" uc.mobile,")
        .append(" uc.icon_attch_id,")
        .append(" uc.icon_url,")
        .append(" uc.icon_small_attch_id,")
        .append(" uc.icon_small_url,")
        .append(" uc.state,")
        .append(" uc.create_time,")
        .append(" uc.last_modified,")
        .append(" personal.idcard_code,")
        .append(" IFNULL(personal.balance, 0) balance,")
        .append(" IFNULL(personal.incoming, 0) incoming,")
        .append(" IFNULL(personal.expenditure, 0) expenditure,")
        .append(" personal.alipay_code,")
        .append(" personal.bank_card_code,")
        .append(" personal.wechat_code,")
        .append(" personal.idcard_photo1_id,")
        .append(" (select attch.attch_url from t_attachment attch where attch.attch_id = personal.idcard_photo1_id and attch.state=0 limit 1) idcard_photo1_url,")
        .append(" personal.idcard_photo2_id,")
        .append(" (select attch.attch_url from t_attachment attch where attch.attch_id = personal.idcard_photo2_id and attch.state=0 limit 1) idcard_photo2_url,")
        .append(" personal.idcard_photo3_id,")
        .append(" (select attch.attch_url from t_attachment attch where attch.attch_id = personal.idcard_photo3_id and attch.state=0 limit 1) idcard_photo3_url,")
        .append(" personal.last_province_code,")
        .append(" (select reg.region_name FROM uc_region reg WHERE reg.region_code=personal.last_province_code and reg.state=0 limit 1)  last_province_name,")
        .append(" personal.last_city_code,")
        .append(" (select reg.region_name FROM uc_region reg WHERE reg.region_code=personal.last_city_code and reg.state=0 limit 1)  last_city_name,")
        .append(" personal.last_area_code,")
        .append(" (select reg.region_name FROM uc_region reg WHERE reg.region_code=personal.last_area_code and reg.state=0 limit 1)  last_area_name,")
        .append(" personal.last_role,")
        .append(" personal.is_team_user,")
        .append(" personal.is_enterprise_user,")
        .append(" personal.update_time,")
        .append(" personal.review_state,")
        .append(" personal.review_admin_id,")
        .append(" personal.review_time")
        .append(" FROM")
        .append(" uc_user_info uc")
        .append(" LEFT JOIN t_user_personal personal ON uc.user_id = personal.user_id")
        .append(" WHERE 1=1");
        //查询条件
        if(userManagerRequest.getUserId()!=null){
            sqlStmt.append(" and uc.user_id = :userId");
            sqlParameters.put("userId", userManagerRequest.getUserId());
        }
        if(userManagerRequest.getReviewState()!=null){
            sqlStmt.append(" and personal.review_state = :reviewState");
            sqlParameters.put("reviewState", userManagerRequest.getReviewState());
        }
        if(!StringUtil.isNull(userManagerRequest.getUserName())){
            sqlStmt.append(" and uc.user_name LIKE :userName");
            sqlParameters.put("userName", "%"+userManagerRequest.getUserName()+"%");
        }
        if(!StringUtil.isNull(userManagerRequest.getQryStartTime())){
            sqlStmt.append(" and uc.create_time >= :qryStartTime");
            sqlParameters.put("qryStartTime", userManagerRequest.getQryStartTime());
        }
        if(!StringUtil.isNull(userManagerRequest.getQryEndTime())){
            sqlStmt.append(" and uc.create_time <= ADDDATE(:qryEndTime, INTERVAL 1 DAY)");
            sqlParameters.put("qryEndTime", userManagerRequest.getQryEndTime());
        }
        if(!StringUtil.isNull(userManagerRequest.getNickName())){
            sqlStmt.append(" and uc.nick_name LIKE :nickName");
            sqlParameters.put("nickName", "%"+userManagerRequest.getNickName()+"%");
        }
        if(!StringUtil.isNull(userManagerRequest.getRealName())){
            sqlStmt.append(" and uc.real_name LIKE :realName");
            sqlParameters.put("realName", "%"+userManagerRequest.getRealName()+"%");
        }
        if(!StringUtil.isNull(userManagerRequest.getIdcardCode())){
            sqlStmt.append(" and personal.idcard_code LIKE :idcardCode");
            sqlParameters.put("idcardCode", "%"+userManagerRequest.getIdcardCode()+"%");
        }

        sqlStmt.append(" order by personal.update_time desc");
        map.put("sqlStmt", sqlStmt);
        map.put("sqlParameters", sqlParameters);
        return map;
    }
	
	/**
	 * 生成企业用户查询sql
	 * @param userManagerRequest
	 * @return
	 */
	public Map<String,Object> getEntUserSqlAndParam(UserManagerRequest userManagerRequest){
		Map<String,Object> map = new HashMap<String,Object>();
		StringBuilder sqlStmt = new StringBuilder();
		Map<String, Object> sqlParameters = new HashMap<>();
		sqlStmt.append(" SELECT")
		.append(" uc.user_id,")
		.append(" uc.org_id,")
		.append(" uc.realm,")
		.append(" uc.user_name,")
		.append(" uc.password,")
		.append(" uc.real_name,")
		.append(" uc.real_full_spell,")
		.append(" uc.real_short_spell,")
		.append(" uc.nick_name,")
		.append(" uc.nick_full_spell,")
		.append(" uc.nick_short_spell,")
		.append(" uc.sex,")
		.append(" uc.mobile,")
		.append(" uc.icon_attch_id,")
		.append(" uc.icon_url,")
		.append(" uc.icon_small_attch_id,")
		.append(" uc.icon_small_url,")
		.append(" uc.state,")
		.append(" uc.create_time,")
		.append(" uc.last_modified,")
		.append(" per.last_role,")
		.append(" ent.enterprise_id,")
		.append(" ent.enterprise_name,")
		.append(" ent.enterprise_name,")
		.append(" (SELECT ur.region_name FROM uc_region ur WHERE ur.region_code = ent.region_code) region_name,")
		.append(" ent.business_license,")
		.append(" ent.business_scope,")
		.append(" ent.corporate,")
		.append(" (SELECT attch_url FROM t_attachment WHERE attch_id = ent.business_license_image) business_license_image,")
		.append(" ent.review_state,")
		.append(" ent.review_admin_id,")
		.append(" ent.create_time ent_create_time,")
		.append(" ent.update_time")
		.append(" FROM")
		.append(" uc_user_info uc")
		.append(" LEFT JOIN t_user_personal per ON uc.state = 0 AND uc.user_id = per.user_id")
		.append(" LEFT JOIN t_user_enterprise ent ON uc.state = 0 AND uc.user_id = ent.user_id")
		.append(" WHERE 1=1 AND ent.enterprise_id IS NOT NULL");
		//查询条件
		if(userManagerRequest.getUserId()!=null){
			sqlStmt.append(" and uc.user_id = :userId");
			sqlParameters.put("userId", userManagerRequest.getUserId());
		}
		if(userManagerRequest.getReviewState()!=null){
		    if("3".equals(userManagerRequest.getReviewState().toString())){
		        sqlStmt.append(" and (ent.review_state is null)");
		    }else{
		        sqlStmt.append(" and ent.review_state = :reviewState");
	            sqlParameters.put("reviewState", userManagerRequest.getReviewState());
		    }
		}
		if(!StringUtil.isNull(userManagerRequest.getUserName())){
			sqlStmt.append(" and uc.user_name LIKE :userName");
			sqlParameters.put("userName", "%"+userManagerRequest.getUserName()+"%");
		}
		if(!StringUtil.isNull(userManagerRequest.getQryStartTime())){
			sqlStmt.append(" and uc.create_time >= :qryStartTime");
			sqlParameters.put("qryStartTime", userManagerRequest.getQryStartTime());
		}
		if(!StringUtil.isNull(userManagerRequest.getQryEndTime())){
			sqlStmt.append(" and uc.create_time <= ADDDATE(:qryEndTime, INTERVAL 1 DAY)");
			sqlParameters.put("qryEndTime", userManagerRequest.getQryEndTime());
		}
		sqlStmt.append(" order by ent.create_time desc");
		map.put("sqlStmt", sqlStmt);
		map.put("sqlParameters", sqlParameters);
		return map;
	}
	
	/**
     * 生成企业用户查询sql根据提交申请时间排序
     * @param userManagerRequest
     * @return
     */
    public Map<String,Object> getEntUserSqlAndParamByUpdateTime(UserManagerRequest userManagerRequest){
        Map<String,Object> map = new HashMap<String,Object>();
        StringBuilder sqlStmt = new StringBuilder();
        Map<String, Object> sqlParameters = new HashMap<>();
        sqlStmt.append(" SELECT")
        .append(" uc.user_id,")
        .append(" uc.org_id,")
        .append(" uc.realm,")
        .append(" uc.user_name,")
        .append(" uc.password,")
        .append(" uc.real_name,")
        .append(" uc.real_full_spell,")
        .append(" uc.real_short_spell,")
        .append(" uc.nick_name,")
        .append(" uc.nick_full_spell,")
        .append(" uc.nick_short_spell,")
        .append(" uc.sex,")
        .append(" uc.mobile,")
        .append(" uc.icon_attch_id,")
        .append(" uc.icon_url,")
        .append(" uc.icon_small_attch_id,")
        .append(" uc.icon_small_url,")
        .append(" uc.state,")
        .append(" uc.create_time,")
        .append(" uc.last_modified,")
        .append(" ent.enterprise_id,")
        .append(" ent.enterprise_name,")
        .append(" ent.region_code,")
        .append(" ent.business_license,")
        .append(" ent.business_scope,")
        .append(" ent.corporate,")
        .append(" (SELECT attch_url FROM t_attachment WHERE attch_id = ent.business_license_image) business_license_image,")
        .append(" ent.review_state,")
        .append(" ent.review_admin_id,")
        .append(" ent.create_time ent_create_time,")
        .append(" ent.update_time")
        .append(" FROM")
        .append(" uc_user_info uc")
        .append(" LEFT JOIN t_user_enterprise ent ON uc.state = 0 and uc.user_id = ent.user_id")
        .append(" WHERE 1=1");
        //查询条件
        if(userManagerRequest.getUserId()!=null){
            sqlStmt.append(" and uc.user_id = :userId");
            sqlParameters.put("userId", userManagerRequest.getUserId());
        }
        if(userManagerRequest.getReviewState()!=null){
            sqlStmt.append(" and ent.review_state = :reviewState");
            sqlParameters.put("reviewState", userManagerRequest.getReviewState());
        }
        if(!StringUtil.isNull(userManagerRequest.getUserName())){
            sqlStmt.append(" and uc.user_name LIKE :userName");
            sqlParameters.put("userName", "%"+userManagerRequest.getUserName()+"%");
        }
        if(!StringUtil.isNull(userManagerRequest.getQryStartTime())){
            sqlStmt.append(" and uc.create_time >= :qryStartTime");
            sqlParameters.put("qryStartTime", userManagerRequest.getQryStartTime());
        }
        if(!StringUtil.isNull(userManagerRequest.getQryEndTime())){
            sqlStmt.append(" and uc.create_time <= ADDDATE(:qryEndTime, INTERVAL 1 DAY)");
            sqlParameters.put("qryEndTime", userManagerRequest.getQryEndTime());
        }
        if(!StringUtil.isNull(userManagerRequest.getEnterpriseName())){
            sqlStmt.append(" and ent.enterprise_name LIKE :enterpriseName");
            sqlParameters.put("enterpriseName", "%"+userManagerRequest.getEnterpriseName()+"%");
        }
        if(!StringUtil.isNull(userManagerRequest.getBusinessLicense())){
            sqlStmt.append(" and ent.business_license LIKE :businessLicense");
            sqlParameters.put("businessLicense", "%"+userManagerRequest.getBusinessLicense()+"%");
        }
        sqlStmt.append(" order by ent.update_time desc");
        System.out.println("企业用户信息查询："+sqlStmt);
        map.put("sqlStmt", sqlStmt);
        map.put("sqlParameters", sqlParameters);
        return map;
    }
    
    /**
     * 生成团队用户查询sql根据申请通过时间排序
     * @param userManagerRequest
     * @return
     */
    public Map<String,Object> getTeamUserSqlAndParam(UserManagerRequest userManagerRequest){
        Map<String,Object> map = new HashMap<String,Object>();
        StringBuilder sqlStmt = new StringBuilder();
        Map<String, Object> sqlParameters = new HashMap<>();
        sqlStmt.append(" SELECT")
        .append(" uc.user_id,")
        .append(" uc.org_id,")
        .append(" uc.realm,")
        .append(" uc.user_name,")
        .append(" uc.password,")
        .append(" uc.real_name,")
        .append(" uc.real_full_spell,")
        .append(" uc.real_short_spell,")
        .append(" uc.nick_name,")
        .append(" uc.nick_full_spell,")
        .append(" uc.nick_short_spell,")
        .append(" uc.sex,")
        .append(" uc.mobile,")
        .append(" uc.icon_attch_id,")
        .append(" uc.icon_url,")
        .append(" uc.icon_small_attch_id,")
        .append(" uc.icon_small_url,")
        .append(" uc.state,")
        .append(" uc.create_time,")
        .append(" uc.last_modified,")
        .append(" tut.contacts_idcard_code,")
        .append(" tut.contacts_mobile,")
        .append(" tut.contacts_real_name,")
        .append(" tut.experience,")
        .append(" (SELECT ur.region_name FROM uc_region ur WHERE ur.region_code = tut.region_code) region_name,")
        .append(" tut.review_admin_id,")
        .append(" tut.review_state,")
        .append(" tut.review_time,")
        .append(" tut.service_content,")
        .append(" tut.team_id,")
        .append(" tut.team_name,")
        .append(" tut.team_nick_name,")
        .append(" tut.team_skills,")
        .append(" tut.update_time,")
        .append(" tup.idcard_code,")
        .append(" (SELECT GROUP_CONCAT(uui.real_name) FROM t_team_member ttm LEFT JOIN uc_user_info uui ON ttm.user_id = uui.user_id WHERE ttm.team_id = tut.team_id) team_member_name")
        .append(" FROM")
        .append(" (select * from uc_user_info uui where uui.state = 0 ");
        //查询条件
        if(userManagerRequest.getUserId()!=null){
            sqlStmt.append(" and uui.user_id = :userId");
            sqlParameters.put("userId", userManagerRequest.getUserId());
        }
        if(!StringUtil.isNull(userManagerRequest.getUserName())){
            sqlStmt.append(" and uui.user_name LIKE :userName");
            sqlParameters.put("userName", "%"+userManagerRequest.getUserName()+"%");
        }
     sqlStmt.append(" ) uc")
        .append(" LEFT JOIN t_user_team tut ON tut.team_leader_id = uc.user_id")
        .append(" LEFT JOIN t_user_personal tup ON uc.user_id = tup.user_id")
        .append(" WHERE 1=1 and tut.team_id is not null AND tup.user_id IS NOT NULL ");
        //查询条件
        if(userManagerRequest.getReviewState()!=null){
            if("4".equals(userManagerRequest.getReviewState())){
                sqlStmt.append(" and tut.review_state is null");
            }else{
                sqlStmt.append(" and tut.review_state = :reviewState");
                sqlParameters.put("reviewState", userManagerRequest.getReviewState());
            }
        }
        if(!StringUtil.isNull(userManagerRequest.getTeamNickName())){
            sqlStmt.append(" and tut.team_nick_name LIKE :teamNickName");
            sqlParameters.put("teamNickName", "%"+userManagerRequest.getTeamNickName()+"%");
        }
        if(!StringUtil.isNull(userManagerRequest.getQryStartTime())){
            sqlStmt.append(" and tut.review_time >= :qryStartTime");
            sqlParameters.put("qryStartTime", userManagerRequest.getQryStartTime());
        }
        if(!StringUtil.isNull(userManagerRequest.getQryEndTime())){
            sqlStmt.append(" and tut.review_time <= ADDDATE(:qryEndTime, INTERVAL 1 DAY)");
            sqlParameters.put("qryEndTime", userManagerRequest.getQryEndTime());
        }
        if(!StringUtil.isNull(userManagerRequest.getTeamName())){
            sqlStmt.append(" and tut.team_name LIKE :teamName");
            sqlParameters.put("teamName", "%"+userManagerRequest.getTeamName()+"%");
        }
        sqlStmt.append(" order by tut.review_time desc");
        System.out.println("团队用户信息查询："+sqlStmt);
        map.put("sqlStmt", sqlStmt);
        map.put("sqlParameters", sqlParameters);
        return map;
    }
    
    /**
     * 生成团队用户查询sql根据提交申请时间排序
     * @param userManagerRequest
     * @return
     */
    public Map<String,Object> getTeamUserSqlAndParamByUpdateTime(UserManagerRequest userManagerRequest){
        Map<String,Object> map = new HashMap<String,Object>();
        StringBuilder sqlStmt = new StringBuilder();
        Map<String, Object> sqlParameters = new HashMap<>();
        sqlStmt.append(" SELECT")
        .append(" uc.user_id,")
        .append(" uc.org_id,")
        .append(" uc.realm,")
        .append(" uc.user_name,")
        .append(" uc.password,")
        .append(" uc.real_name,")
        .append(" uc.real_full_spell,")
        .append(" uc.real_short_spell,")
        .append(" uc.nick_name,")
        .append(" uc.nick_full_spell,")
        .append(" uc.nick_short_spell,")
        .append(" uc.sex,")
        .append(" uc.mobile,")
        .append(" uc.icon_attch_id,")
        .append(" uc.icon_url,")
        .append(" uc.icon_small_attch_id,")
        .append(" uc.icon_small_url,")
        .append(" uc.state,")
        .append(" uc.create_time,")
        .append(" uc.last_modified,")
        .append(" tut.contacts_idcard_code,")
        .append(" tut.contacts_mobile,")
        .append(" tut.contacts_real_name,")
        .append(" tut.experience,")
        .append(" (SELECT ur.region_name FROM uc_region ur WHERE ur.region_code = tut.region_code) region_name,")
        .append(" tut.review_admin_id,")
        .append(" tut.review_state,")
        .append(" tut.review_time,")
        .append(" tut.service_content,")
        .append(" tut.team_id,")
        .append(" tut.team_name,")
        .append(" tut.team_nick_name,")
        .append(" tut.team_skills,")
        .append(" tut.update_time")
        .append(" FROM")
        .append(" uc_user_info uc")
        .append(" LEFT JOIN t_user_team tut ON uc.state = 0 AND tut.team_leader_id = uc.user_id")
        .append(" WHERE 1=1");
        //查询条件
        if(userManagerRequest.getUserId()!=null){
            sqlStmt.append(" and uc.user_id = :userId");
            sqlParameters.put("userId", userManagerRequest.getUserId());
        }
        if(userManagerRequest.getReviewState()!=null){
            sqlStmt.append(" and tut.review_state = :reviewState");
            sqlParameters.put("reviewState", userManagerRequest.getReviewState());
        }
        if(!StringUtil.isNull(userManagerRequest.getUserName())){
            sqlStmt.append(" and uc.user_name LIKE :userName");
            sqlParameters.put("userName", "%"+userManagerRequest.getUserName()+"%");
        }
        if(!StringUtil.isNull(userManagerRequest.getTeamNickName())){
            sqlStmt.append(" and tut.team_nick_name LIKE :teamNickName");
            sqlParameters.put("teamNickName", "%"+userManagerRequest.getTeamNickName()+"%");
        }
        if(!StringUtil.isNull(userManagerRequest.getQryStartTime())){
            sqlStmt.append(" and uc.create_time >= :qryStartTime");
            sqlParameters.put("qryStartTime", userManagerRequest.getQryStartTime());
        }
        if(!StringUtil.isNull(userManagerRequest.getQryEndTime())){
            sqlStmt.append(" and uc.create_time <= ADDDATE(:qryEndTime, INTERVAL 1 DAY)");
            sqlParameters.put("qryEndTime", userManagerRequest.getQryEndTime());
        }
        if(!StringUtil.isNull(userManagerRequest.getTeamName())){
            sqlStmt.append(" and tut.team_name LIKE :teamName");
            sqlParameters.put("teamName", "%"+userManagerRequest.getTeamName()+"%");
        }
        sqlStmt.append(" order by tut.update_time desc");
        System.out.println("团队用户信息查询："+sqlStmt);
        map.put("sqlStmt", sqlStmt);
        map.put("sqlParameters", sqlParameters);
        return map;
    }
    
    /**
     * 查询用户资金信息
     * @param userFinancialRequest
     * @return
     */
    public Map<String,Object> getUserFinancialSqlAndParam(UserFinancialRequest userFinancialRequest){
        Map<String,Object> map = new HashMap<String,Object>();
        StringBuilder sqlStmt = new StringBuilder();
        Map<String, Object> sqlParameters = new HashMap<>();
        sqlStmt.append("select")
            .append(" record_id,")
            .append(" pay_user_id,")
            .append(" pay_user_name,")
            .append(" income_user_id,")
            .append(" income_user_name,")
            .append(" target_type,")
            .append(" target_id,")
            .append(" money,")
            .append(" trans_type,")
            .append(" trade_state,")
            .append(" trade_platform,")
            .append(" trade_no,")
            .append(" trade_extend,")
            .append(" trade_time")
            .append(" from t_finance_trade_detail")
            .append(" WHERE 1=1 ");
        if(!StringUtil.isNull(userFinancialRequest.getUserId())){
            sqlStmt.append(" and (pay_user_id = :userId or income_user_id = :userId)");
            sqlParameters.put("userId", userFinancialRequest.getUserId());
        }
        sqlStmt.append(" ORDER BY trade_time DESC");
        map.put("sqlStmt", sqlStmt);
        map.put("sqlParameters", sqlParameters);
        return map;
    }
    
    /**
     * 生成账户余额查询
     * @param userManagerRequest
     * @return
     */
    public Map<String,Object> getBalanceSqlAndParam(UserFinancialRequest userFinancialRequest){
        Map<String,Object> map = new HashMap<String,Object>();
        StringBuilder sqlStmt = new StringBuilder();
        Map<String, Object> sqlParameters = new HashMap<>();
        sqlStmt.append("select")
            .append(" user_id,")
            .append(" balance,")
            .append(" incoming,")
            .append(" expenditure")
            .append(" from t_user_personal")
            .append(" WHERE 1=1 ");
        if(!StringUtil.isNull(userFinancialRequest.getUserId())){
            sqlStmt.append(" and user_id = :userId ");
            sqlParameters.put("userId", userFinancialRequest.getUserId());
        }
        map.put("sqlStmt", sqlStmt);
        map.put("sqlParameters", sqlParameters);
        return map;
    }
    
    /**
     * 生成账户余额查询
     * @param userManagerRequest
     * @return
     */
    public Map<String,Object> getImprestSqlAndParam(UserFinancialRequest userFinancialRequest){
        Map<String,Object> map = new HashMap<String,Object>();
        StringBuilder sqlStmt = new StringBuilder();
        Map<String, Object> sqlParameters = new HashMap<>();
        sqlStmt.append("select")
            .append(" project_id,")
            .append(" employer_id,")
            .append(" budget,")
            .append(" trade_money,")
            .append(" imprest_scale,")
            .append(" margin_scale,")
            .append(" reveal_scale,")
            .append(" is_reveal,")
            .append(" is_margin")
            .append(" from t_project")
            .append(" WHERE (state = 6 or state = 8)");
        if(!StringUtil.isNull(userFinancialRequest.getUserId())){
            sqlStmt.append(" and employer_id = :userId ");
            sqlParameters.put("userId", userFinancialRequest.getUserId());
        }
        map.put("sqlStmt", sqlStmt);
        map.put("sqlParameters", sqlParameters);
        return map;
    }

	@Override
	public UcUserInfoEntity findByWeixinIdOrQQId(String weixinId, String qqId) {
		StringBuilder sqlStmt = new StringBuilder();
		Map<String, Object> sqlParameters = new HashMap<>();
		
        sqlStmt.append("SELECT ").append(COLUMN).append(" FROM uc_user_info " +
                " WHERE state=:state");
        sqlParameters.put("state", AvailableEnum.NORMAL.getValue());
        if(weixinId!=null){
        	sqlStmt.append(" and weixin_id=:weixinId");
        	sqlParameters.put("weixinId", weixinId);
        }
        if(qqId!=null){
        	sqlStmt.append(" and qq_id=:qqId");
        	sqlParameters.put("qqId", qqId);
        }

        List<UcUserInfoEntity> results = this.jdbcTemplate.query(sqlStmt.toString(),sqlParameters,BeanPropertyRowMapper.newInstance(UcUserInfoEntity.class));
        if (results==null || results.size()<=0){
            return null;
        }

        if (results.size()>1){
            logger.warn(String.format("存在两个相同微信id或qqid的用户:[weixin_id:%1$s,qq_id%2$s]",weixinId, qqId));
        }
        return results.get(0);
	}
}
