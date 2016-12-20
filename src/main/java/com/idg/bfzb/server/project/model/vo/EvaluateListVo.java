package com.idg.bfzb.server.project.model.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import com.idg.bfzb.server.utility.tools.ConfigFileUtils;

/**
 * 类名称：EvaluateListVo
 * 类描述：评价vo
 * 创建人：ouzhb
 * 创建时间：2016/11/11
 */
public class EvaluateListVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String userId;
	
	private String userName;
	
	private String nickName;
	
	private String content;
	
	private Integer star;

	private String iconSmallAttchId;
	
	private String iconSmallUrl;
	
	private Timestamp createTime;

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getIconSmallAttchId() {
		return iconSmallAttchId;
	}

	public void setIconSmallAttchId(String iconSmallAttchId) {
		this.iconSmallAttchId = iconSmallAttchId;
	}

	public String getIconSmallUrl() {
		return iconSmallUrl;
	}

	public void setIconSmallUrl(String iconSmallUrl) {
		this.iconSmallUrl = ConfigFileUtils.HEAD_ICON_URL + iconSmallUrl;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}
