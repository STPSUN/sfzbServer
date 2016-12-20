package com.idg.bfzb.server.file.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.idg.bfzb.server.file.model.dto.AttachmentEntity;

public interface AttachmentRepository extends JpaRepository<AttachmentEntity, String> {
	
	@Query("select a from AttachmentEntity a where a.attchId = ?1")
	AttachmentEntity findAllByAttchId(String attchId);
}
