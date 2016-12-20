package com.idg.bfzb.server.usercenter.dao;

import org.springframework.data.domain.Pageable;

import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.usercenter.model.request.EvaluateManagerRequest;
import com.idg.bfzb.server.usercenter.model.request.MessageManagerRequest;

public interface EvaluateManagerRepositoryCust {
    /**
     * 
     * .查询评价信息
     * 
     * @param evaluateManagerRequest
     * @param pageable
     * @return
     */
    PageInfo queryEvaluateByCond(EvaluateManagerRequest evaluateManagerRequest,Pageable pageable);
}
