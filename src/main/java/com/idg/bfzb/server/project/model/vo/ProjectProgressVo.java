package com.idg.bfzb.server.project.model.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import com.idg.bfzb.server.utility.tools.ConfigFileUtils;

/**
 * 类名称：ProjectProgressVo
 * 类描述：项目进度
 * 创建人：ouzhb
 * 创建时间：2016/11/17
 */
public class ProjectProgressVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String workDesc;
	
	private Timestamp workTime;
	
	private String photos;
	
	private String photoUrlPre = ConfigFileUtils.HEAD_ICON_URL;

	public String getWorkDesc() {
		return workDesc;
	}

	public void setWorkDesc(String workDesc) {
		this.workDesc = workDesc;
	}

	public Timestamp getWorkTime() {
		return workTime;
	}

	public void setWorkTime(Timestamp workTime) {
		this.workTime = workTime;
	}

	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}

	public String getPhotoUrlPre() {
		return photoUrlPre;
	}

	public void setPhotoUrlPre(String photoUrlPre) {
		this.photoUrlPre = photoUrlPre;
	}

}
