package com.idg.bfzb.server.content.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.content.dao.WebNewsManagerDao;
import com.idg.bfzb.server.content.model.WebNewsAdminRequest;
import com.idg.bfzb.server.content.model.WebNewsAdminResponse;
import com.idg.bfzb.server.content.model.dto.TContAdvertisementEntity;
import com.idg.bfzb.server.content.service.WebNewsMaganerService;
import com.idg.bfzb.server.utility.tools.ConfigFileUtils;

@Service
public class WebNewsManagerServiceImpl implements WebNewsMaganerService{

	@Autowired
	private WebNewsManagerDao webNewsDao;
	@Override
	public PageInfo<WebNewsAdminResponse> getWebNewsList(
			WebNewsAdminRequest projectRequest, PageRequest pageable) {
		PageInfo<WebNewsAdminResponse> pageInfo = this.webNewsDao.findWebNewsListByCond(projectRequest, pageable);
		
		if(pageInfo != null && pageInfo.getPageData() != null){
			List<WebNewsAdminResponse> list = pageInfo.getPageData();
			for (int i = 0; i < list.size(); i++) {
				
				if(!StringUtils.isEmpty(list.get(i).getAdvImg())){
					list.get(i).setAdvImg(ConfigFileUtils.HEAD_ICON_URL+list.get(i).getAdvImg());
				}
			}
		}
		return pageInfo;
	}
	@Override
	public APIResponse addWebNews(TContAdvertisementEntity adver) {
		APIResponse ret = new APIResponse();
		
		boolean result = webNewsDao.addWebNews(adver);
		if(result){
			ret.setSucess(true);
			ret.setMessage("网站新闻新增成功！");
			ret.setCode("0");
		}else {
			ret.setSucess(true);
			ret.setMessage("网站新闻新增失败！");
			ret.setCode("-1");
		}
		
		return ret;
	}
	@Override
	public APIResponse updateWebNews(TContAdvertisementEntity adver) {
		APIResponse ret = new APIResponse();
		
		boolean result = webNewsDao.updateWebNews(adver);
		if(result){
			ret.setSucess(true);
			ret.setMessage("网站新闻更新成功！");
			ret.setCode("0");
		}else {
			ret.setSucess(true);
			ret.setMessage("网站新闻更新失败！");
			ret.setCode("-1");
		}
		
		return ret;
	}
	@Override
	public APIResponse deleteWebNewsByAdvId(String advId) {
		APIResponse ret = new APIResponse();
		
		boolean result = webNewsDao.deleteWebNewsByAdvId(advId);
		if(result){
			ret.setSucess(true);
			ret.setMessage("网站新闻删除成功！");
			ret.setCode("0");
		}else {
			ret.setSucess(true);
			ret.setMessage("网站新闻删除失败！");
			ret.setCode("-1");
		}
		
		return ret;
	}
	@Override
	public APIResponse getWebNewsContentByAdvId(String advId) {
		APIResponse ret = new APIResponse();
		
		WebNewsAdminResponse webNews = webNewsDao.getWebNewsByAdvId(advId);
		ret.setSucess(true);
		ret.setMessage("");
		ret.setData(webNews.getAdvContent());
		ret.setCode("0");
		
		return ret;
	}

}
