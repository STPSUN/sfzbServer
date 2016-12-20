package com.idg.bfzb.server.configure.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idg.bfzb.server.configure.model.dto.TContMsgConfigureEntity;

public interface MsgConfigureRespository extends JpaRepository<TContMsgConfigureEntity,Long>,MsgConfigureRespositoryCust{

}
