package com.idg.bfzb.server.configure.dao;

import org.springframework.data.domain.Pageable;

import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.configure.model.request.MsgConfigureRequest;

public interface MsgConfigureRespositoryCust {
	public PageInfo findMsgConfigureByCond(MsgConfigureRequest msgConfigureRequest,Pageable pageable);
}
