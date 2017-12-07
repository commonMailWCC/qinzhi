package com.qinzhi.domain;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.Serializable;

/**
 * @className: 用户
 * @description: 操作员信息
 * @author: liwei
 * @date: 2017-03-27 下午1:57:09
 */
public class SysOperator implements Serializable {
	private static final long serialVersionUID = -7059331243549997464L;

	/**
	 * 主键
	 */
	private Long operatorId;

	/**
	 * 电子邮箱
	 */
	private String operatorEmail;

	/**
	 * 登陆名称
	 */
	private String operatorLoginName;

	/**
	 * 企业名称
	 */
	private String operatorName;

	/**
	 * 登陆密码
	 */
	private String operatorPassword;

	/**
	 * 用户类型[1-管理员; 0:商户]
	 */
	private Integer operatorState;

	private String salt;

	/**
	 * 销售商品
	 */
	private String saleGoods;

	/**
	 * 企业联系人
	 */
	private String operatorContact;

	/**
	 * qq
	 */
	private String operatorQq;

	/**
	 * 商户等级
	 */
	private String operatorLevel;

	private Long levelId;
	private String operatorProvince;
	private String operatorCity;
	private String operatorRegion;
	private String operatorAddress;

	private String gender;

	private Integer rows;
	private Integer page;
	private Integer start;

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public Integer getOperatorState() {
		return operatorState;
	}

	public void setOperatorState(Integer operatorState) {
		this.operatorState = operatorState;
	}

	public String getOperatorEmail() {
		return operatorEmail;
	}

	public void setOperatorEmail(String operatorEmail) {
		this.operatorEmail = operatorEmail;
	}

	public String getOperatorLoginName() {
		return operatorLoginName;
	}

	public void setOperatorLoginName(String operatorLoginName) {
		this.operatorLoginName = operatorLoginName == null ? "" : operatorLoginName.trim();
	}

	public String getOperatorPassword() {
		return operatorPassword;
	}

	public void setOperatorPassword(String operatorPassword) {
		this.operatorPassword = operatorPassword == null ? "" : operatorPassword.trim();
	}

	/**
	 * 增加一个密码检查接口
	 *
	 * @return
	 */
	public boolean checkPassword(String operatorPassword) {
		return DigestUtils.md5Hex(operatorPassword.trim()).equals(this.operatorPassword);
	}

	public String getSaleGoods() {
		return saleGoods;
	}

	public void setSaleGoods(String saleGoods) {
		this.saleGoods = saleGoods;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getOperatorContact() {
		return operatorContact;
	}

	public void setOperatorContact(String operatorContact) {
		this.operatorContact = operatorContact;
	}

	public String getOperatorQq() {
		return operatorQq;
	}

	public void setOperatorQq(String operatorQq) {
		this.operatorQq = operatorQq;
	}

	public String getOperatorLevel() {
		return operatorLevel;
	}

	public void setOperatorLevel(String operatorLevel) {
		this.operatorLevel = operatorLevel;
	}

	public String getOperatorProvince() {
		return operatorProvince;
	}

	public void setOperatorProvince(String operatorProvince) {
		this.operatorProvince = operatorProvince;
	}

	public String getOperatorCity() {
		return operatorCity;
	}

	public void setOperatorCity(String operatorCity) {
		this.operatorCity = operatorCity;
	}

	public String getOperatorRegion() {
		return operatorRegion;
	}

	public void setOperatorRegion(String operatorRegion) {
		this.operatorRegion = operatorRegion;
	}

	public String getOperatorAddress() {
		return operatorAddress;
	}

	public void setOperatorAddress(String operatorAddress) {
		this.operatorAddress = operatorAddress;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
		setStart((page - 1) * rows);
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Long getLevelId() {
		return levelId;
	}

	public void setLevelId(Long levelId) {
		this.levelId = levelId;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

}