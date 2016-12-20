package com.idg.bfzb.server.configure.dao;

import org.springframework.data.domain.Pageable;

import com.idg.bfzb.server.common.model.PageInfo;

public interface RegionRespositoryCust {
	public PageInfo findRegionAll(Pageable pageable);
}
