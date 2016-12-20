package com.idg.bfzb.server.adminproject.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.idg.bfzb.server.adminproject.dao.WarrantyAdminDao;
import com.idg.bfzb.server.adminproject.model.WarrantyAdminRequest;
import com.idg.bfzb.server.adminproject.model.WarrantyAdminResponse;
import com.idg.bfzb.server.adminproject.service.WarrantyAdminService;
import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.common.model.PageInfo;

@Service
public class WarrantyAdminServiceImpl implements WarrantyAdminService{

	@Autowired
	private WarrantyAdminDao warrantyAdminDao;
	
	@Override
	public PageInfo<WarrantyAdminResponse> getWarrantyList(
			WarrantyAdminRequest projectRequest, PageRequest pageable) {
		PageInfo<WarrantyAdminResponse> pageInfo = this.warrantyAdminDao.findProjectWarrantyListByCond(projectRequest, pageable);
		return pageInfo;
	}
	@Override
	public APIResponse auditPassWarranty(String projectId, String reason,String userId) {
		APIResponse ret = new APIResponse();
		
		boolean result = warrantyAdminDao.auditPassWarranty(projectId,reason,userId);
		if(result){
			ret.setSucess(true);
			ret.setMessage("质保记录通过操作成功！");
			ret.setCode("0");
		}else {
			ret.setSucess(true);
			ret.setMessage("质保记录通过操作失败！");
			ret.setCode("-1");
		}
		
		return ret;
	}
	@Override
	public APIResponse auditNotPassWarranty(String projectId, String reason,String userId) {
		APIResponse ret = new APIResponse();
		
		boolean result = warrantyAdminDao.auditNotPassWarranty(projectId,reason,userId);
		if(result){
			ret.setSucess(true);
			ret.setMessage("质保记录驳回操作成功！");
			ret.setCode("0");
		}else {
			ret.setSucess(true);
			ret.setMessage("质保记录驳回操作失败！");
			ret.setCode("-1");
		}
		
		return ret;
	}

}
