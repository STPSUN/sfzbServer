package com.idg.bfzb.server.message.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idg.bfzb.server.message.model.dto.ContMsgDetailEntity;
import com.idg.bfzb.server.message.model.dto.ContMsgDetailEntityId;

public interface ContMsgDetailRepository extends JpaRepository<ContMsgDetailEntity, ContMsgDetailEntityId>, ContMsgDetailRepositoryCust {

}
