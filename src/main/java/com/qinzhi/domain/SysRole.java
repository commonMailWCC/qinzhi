package com.qinzhi.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @className: SysRole
 * @description: 角色信息
 * @author: liwei
 * @date: 2017-03-26 下午2:14:48
 */
public class SysRole implements Serializable {
    private static final long serialVersionUID = -7059331243549997464L;
    /**
     * 主键
     */
    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色描述
     */
    private String roleDesc;
    /**
     * 创建时间
     */
    private Date createDate;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}