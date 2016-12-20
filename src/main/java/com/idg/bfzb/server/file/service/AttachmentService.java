package com.idg.bfzb.server.file.service;

import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.file.model.dto.AttachmentEntity;


/**
 * 上传文件
 * @author weibeifeng
 *
 */
public interface AttachmentService {
    APIResponse addAttachment(AttachmentEntity attachmentEntity);
}