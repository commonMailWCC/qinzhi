package com.qinzhi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import java.util.Date;

@Entity
@Table(name = "token")
public class TokenTable implements Serializable {

	private static final long serialVersionUID = -8973506635139428407L;

	// 注释
	@Id
	@Column(name = "id")
	private Long id;

	// 注释
	@Column(name = "user_id")
	private Long userId;

	// 注释
	@Column(name = "user_token")
	private String userToken;

	// 注释
	@Column(name = "create_time")
	private Date createTime;

	// 注释
	@Column(name = "update_time")
	private Date updateTime;

	// 注释
	@Column(name = "invalid_time")
	private Date invalidTime;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	public String getUserToken() {
		return userToken;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setInvalidTime(Date invalidTime) {
		this.invalidTime = invalidTime;
	}

	public Date getInvalidTime() {
		return invalidTime;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
