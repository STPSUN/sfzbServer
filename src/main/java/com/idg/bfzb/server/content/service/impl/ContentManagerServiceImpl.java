package com.idg.bfzb.server.content.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;
import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.idg.bfzb.server.ability.model.dto.TAbilityEntity;
import com.idg.bfzb.server.common.ErrorCode;
import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.configure.model.response.CategoryLinkAbilityResponse;
import com.idg.bfzb.server.content.dao.ContentManagerRespository;
import com.idg.bfzb.server.content.model.dto.TContAdvertisementEntity;
import com.idg.bfzb.server.content.model.request.ContentRequest;
import com.idg.bfzb.server.content.model.response.ContentResponse;
import com.idg.bfzb.server.content.service.ContentManagerService;
import com.idg.bfzb.server.utility.tools.StringUtil;

@Service
@SuppressWarnings("Duplicates")
public class ContentManagerServiceImpl implements ContentManagerService{
    private final Logger logger = LoggerFactory.getLogger(ContentManagerServiceImpl.class);
    
    @Autowired
    private ContentManagerRespository contentManagerRespository;
    
    public PageInfo queryAdBanners(ContentRequest contentRequest,Pageable pageable){
        PageInfo pageInfo = this.contentManagerRespository.queryAdBannersByCond(contentRequest, pageable);
        return pageInfo;
    }
    
    public APIResponse queryAdvSort(ContentRequest contentRequest){
        APIResponse apiResponse = new APIResponse();
        try {
            List<ContentResponse> contentResponseList = this.contentManagerRespository.queryAdvSort(contentRequest);
            apiResponse.setData(contentResponseList);
            apiResponse.setMessage(apiResponse.SUCESS_MSG);
        } catch (Exception e) {
            // TODO: handle exception
            apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
        }
        return apiResponse;
    }
    
    public APIResponse saveAdvertisement(ContentRequest contentRequest){
        APIResponse apiResponse = new APIResponse();
        try {
            String advId = UUID.randomUUID().toString();
            if(!StringUtil.isNull(contentRequest.getAdvId())){
                advId = contentRequest.getAdvId();
            }
            TContAdvertisementEntity tContAdvertisementEntity = new TContAdvertisementEntity();
            tContAdvertisementEntity.setAdvId(advId);
            tContAdvertisementEntity.setTitle(contentRequest.getTitle());
            tContAdvertisementEntity.setAdvContent(contentRequest.getAdvContent());
            tContAdvertisementEntity.setAdvImg(contentRequest.getAdvImg());
            tContAdvertisementEntity.setAdvClientType(contentRequest.getAdvClientType());
            tContAdvertisementEntity.setAdvLocation(contentRequest.getAdvLocation());
            tContAdvertisementEntity.setAdvLink(contentRequest.getAdvLink());
            tContAdvertisementEntity.setStartTime(contentRequest.getStartTime());
            tContAdvertisementEntity.setEndTime(contentRequest.getEndTime());
            tContAdvertisementEntity.setPlayArea(contentRequest.getPlayArea());
            tContAdvertisementEntity.setAdvSort(contentRequest.getAdvSort());
            tContAdvertisementEntity.setStatus((short) 0);
            tContAdvertisementEntity.setAdvType(contentRequest.getAdvType());
            tContAdvertisementEntity.setAdvLocationDetail(contentRequest.getAdvLocationDetail());
            tContAdvertisementEntity.setAdvUser(contentRequest.getAdvUser());
            tContAdvertisementEntity.setAdvUserMobile(contentRequest.getAdvUserMobile());
            if(contentRequest.getCreateTime() != null){
                tContAdvertisementEntity.setCreateTime(contentRequest.getCreateTime());
            }
            
            this.contentManagerRespository.save(tContAdvertisementEntity);
            apiResponse.setMessage(apiResponse.SUCESS_MSG);
        } catch (Exception e) {
            // TODO: handle exception
            apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
        }
        return apiResponse;
    }
    
    public APIResponse delAdvertisement(String advId,String updateUserId){
        APIResponse apiResponse = new APIResponse();
        try {
            if(advId == null || advId.isEmpty()){
                apiResponse.setErrorCode(ErrorCode.REQUIRE_ARGUMENT);
            }else{
                TContAdvertisementEntity tContAdvertisementEntity = this.contentManagerRespository.findOne(advId);
                tContAdvertisementEntity.setStatus((short) -1);
                tContAdvertisementEntity.setUpdateAdminId(updateUserId);
                tContAdvertisementEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                this.contentManagerRespository.save(tContAdvertisementEntity);
                apiResponse.setMessage(APIResponse.SUCESS_MSG);
            }
        } catch (Exception e) {
            // TODO: handle exception
            logger.error(e.getMessage());
            apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
        }
        return apiResponse;
    }
    
    public APIResponse changeAdvertisementType(String advId,String updateUserId,short status){
        APIResponse apiResponse = new APIResponse();
        try {
            if(advId == null || advId.isEmpty()){
                apiResponse.setErrorCode(ErrorCode.REQUIRE_ARGUMENT);
            }else{
                TContAdvertisementEntity tContAdvertisementEntity = this.contentManagerRespository.findOne(advId);
                tContAdvertisementEntity.setStatus(status);
                tContAdvertisementEntity.setUpdateAdminId(updateUserId);
                tContAdvertisementEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                this.contentManagerRespository.save(tContAdvertisementEntity);
                apiResponse.setMessage(APIResponse.SUCESS_MSG);
            }
        } catch (Exception e) {
            // TODO: handle exception
            logger.error(e.getMessage());
            apiResponse.setErrorCode(ErrorCode.SYSTEM_EXCEPTION);
        }
        return apiResponse;
    }
}
