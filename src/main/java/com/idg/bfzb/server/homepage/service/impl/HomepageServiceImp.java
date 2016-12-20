package com.idg.bfzb.server.homepage.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.idg.bfzb.server.common.ErrorCode;
import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.common.model.ArrayResponse;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.content.dao.WebNewsManagerDao;
import com.idg.bfzb.server.content.model.WebNewsAdminRequest;
import com.idg.bfzb.server.content.model.WebNewsAdminResponse;
import com.idg.bfzb.server.homepage.dao.HomepageDao;
import com.idg.bfzb.server.homepage.model.dto.ContAdvertisementEntity;
import com.idg.bfzb.server.homepage.service.HomepageService;
import com.idg.bfzb.server.utility.cache.redis.ValueCacheService;
import com.idg.bfzb.server.utility.tools.ConfigFileUtils;
import com.idg.bfzb.server.utility.tools.DateUtil;
import com.idg.bfzb.server.utility.tools.StringUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class HomepageServiceImp implements HomepageService {
//    private final Logger logger = LoggerFactory.getLogger(HomepageServiceImp.class);

    @Autowired
    private ValueCacheService<String, String> valueCacheService;
    @Autowired
    private HomepageDao homePageDao;
    @Autowired
    private WebNewsManagerDao webNewsDao;
	
	@Override
	public Object getHomePageBanners(String adv_client_type, String adv_location) {
		
		APIResponse apiResponse = new APIResponse();
		//获取用户认证信息操作
		List<ContAdvertisementEntity> banners = homePageDao.getAdvertisementByAdvClientType(adv_client_type, adv_location);
		
		for (ContAdvertisementEntity contAdvertisementEntity : banners) {
			contAdvertisementEntity.setAdvImg(ConfigFileUtils.HEAD_ICON_URL+contAdvertisementEntity.getAdvImg());
		}
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("banners", banners);
		
		apiResponse.setMessage("获取首页banners成功！");
		apiResponse.setCode(APIResponse.SUCESS_MSG);
		apiResponse.setData(map);
		
		return apiResponse;
	}

	@Override
	public Object getWebNewsList(PageRequest pageable) {
		
		APIResponse apiResponse = new APIResponse();
		
		WebNewsAdminRequest projectRequest = new WebNewsAdminRequest();
		projectRequest.setWebNewsState("0");
		//获取用户认证信息操作
		PageInfo<WebNewsAdminResponse> webNews = webNewsDao.findWebNewsListByCond(projectRequest , pageable);
		for (WebNewsAdminResponse wb : webNews.getPageData()) {
			wb.setAdvImg(ConfigFileUtils.HEAD_ICON_URL+wb.getAdvImg());
			wb.setCreateDate(DateUtil.formatTimestampDateString(wb.getCreateTime(), "yyyy-MM-dd"));
		}
		apiResponse.setMessage("获取首页资讯成功！");
		apiResponse.setCode(APIResponse.SUCESS_MSG);
		
		ArrayResponse<WebNewsAdminResponse> data = new ArrayResponse<WebNewsAdminResponse>();
		data.setTotal((long) webNews.getTotalRows());
		data.setItems(webNews.getPageData());
		apiResponse.setData(data);

		return apiResponse;
	}

	@Override
	public Object getWebNewsDetail(String advId) {
		APIResponse apiResponse = new APIResponse();
		//参数空判断
		if(StringUtil.isNull(advId)){
			apiResponse.setErrorCode(ErrorCode.REQUIRE_ARGUMENT);
			return apiResponse;
		}
		WebNewsAdminResponse wb = webNewsDao.getWebNewsByAdvId(advId);
		if(wb != null){
			wb.setCreateDate(DateUtil.formatTimestampDateString(wb.getCreateTime(), "yyyy-MM-dd"));
		}
		apiResponse.setMessage("获取资讯详情成功！");
		apiResponse.setCode(APIResponse.SUCESS_MSG);
		apiResponse.setData(wb);
		
		return apiResponse;
	}

	@Override
	public Object updateReadCount(String advId) {
		APIResponse apiResponse = new APIResponse();
		//参数空判断
		if(StringUtil.isNull(advId)){
			apiResponse.setErrorCode(ErrorCode.REQUIRE_ARGUMENT);
			return apiResponse;
		}
		boolean ret = webNewsDao.updateReadCount(advId);
		if(!ret){
			apiResponse.setMessage("内部错误，修改阅读量失败！");
			apiResponse.setCode("1000");
		}else{
			
			apiResponse.setMessage("阅读量修改成功！");
			apiResponse.setCode(APIResponse.SUCESS_MSG);
		}
		
		return apiResponse;
	}

	@Override
	public Object getWebNewsRelevantList(PageRequest pageable, String keyWord, String advId) {
		APIResponse apiResponse = new APIResponse();
		
		WebNewsAdminRequest projectRequest = new WebNewsAdminRequest();
		projectRequest.setWebNewsState("0");
		
		if(!StringUtils.isEmpty(keyWord)){
			
			projectRequest.setWebNewsKeyWords(keyWord.split(","));
		}
		projectRequest.setWebNewsNotAdvId(advId);
		//获取用户认证信息操作
		PageInfo<WebNewsAdminResponse> webNews = webNewsDao.findWebNewsListByCond(projectRequest , pageable);
//		for (WebNewsAdminResponse wb : webNews.getPageData()) {
//			wb.setAdvImg(ConfigFileUtils.HEAD_ICON_URL+wb.getAdvImg());
//			wb.setCreateDate(DateUtil.formatTimestampDateString(wb.getCreateTime(), "yyyy-MM-dd"));
//		}
		apiResponse.setMessage("获取首页资讯成功！");
		apiResponse.setCode(APIResponse.SUCESS_MSG);
		
		ArrayResponse<WebNewsAdminResponse> data = new ArrayResponse<WebNewsAdminResponse>();
		data.setTotal((long) webNews.getTotalRows());
		data.setItems(webNews.getPageData());
		apiResponse.setData(data);

		return apiResponse;
	}

	@Override
	public Object getWebNewsqueryPreNest(String webNewsAdvId) {
		APIResponse apiResponse = new APIResponse();
		//参数空判断
		if(StringUtil.isNull(webNewsAdvId)){
			apiResponse.setErrorCode(ErrorCode.REQUIRE_ARGUMENT);
			return apiResponse;
		}
		WebNewsAdminResponse preWb = webNewsDao.getWebNewsqueryPre(webNewsAdvId);
//		if(preWb != null){
//			preWb.setCreateDate(DateUtil.formatTimestampDateString(preWb.getCreateTime(), "yyyy-MM-dd"));
//		}
		
		WebNewsAdminResponse nextWb = webNewsDao.getWebNewsqueryNext(webNewsAdvId);
//		if(nextWb != null){
//			nextWb.setCreateDate(DateUtil.formatTimestampDateString(nextWb.getCreateTime(), "yyyy-MM-dd"));
//		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("preWebNews", preWb);
		map.put("nextWebNews", nextWb);
		apiResponse.setMessage("获取资讯上一篇下一篇成功！");
		apiResponse.setCode(APIResponse.SUCESS_MSG);
		apiResponse.setData(map);
		
		return apiResponse;
	}

}
