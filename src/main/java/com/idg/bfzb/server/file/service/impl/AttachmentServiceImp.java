package com.idg.bfzb.server.file.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.file.dao.AttachmentRepository;
import com.idg.bfzb.server.file.model.dto.AttachmentEntity;
import com.idg.bfzb.server.file.service.AttachmentService;

@Service
public class AttachmentServiceImp implements AttachmentService {
    private final Logger logger = LoggerFactory.getLogger(AttachmentServiceImp.class);

    @Autowired
    private AttachmentRepository attachmentRepository;

	@Override
	public APIResponse addAttachment(AttachmentEntity attachmentEntity) {
		APIResponse apiResponse = new APIResponse();
		attachmentRepository.save(attachmentEntity);
        apiResponse.setMessage(APIResponse.SUCESS_MSG);
        logger.info("上传成功！"+attachmentEntity.getAttchName());
        return apiResponse;
	}
    
}
