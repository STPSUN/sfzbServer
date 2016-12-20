package com.idg.bfzb.server.usercenter.model.request;

/**
 * @author wangsheng
 * 
 * 用户信息修改请求对象
 *
 */
public class UcUsersResumeRequest {

	private String nickName;
	private String headIconAttchId;
	private String headIconSmallAttchId;
	private String sex;
	private String provinceCode;
	private String cityCode;
	private String areaCode;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getHeadIconAttchId() {
		return headIconAttchId;
	}

	public void setHeadIconAttchId(String headIconAttchId) {
		this.headIconAttchId = headIconAttchId;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getHeadIconSmallAttchId() {
		return headIconSmallAttchId;
	}

	public void setHeadIconSmallAttchId(String headIconSmallAttchId) {
		this.headIconSmallAttchId = headIconSmallAttchId;
	}

}
