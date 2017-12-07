package com.qinzhi.domain;

import java.io.Serializable;

/**
 * @className: SysRoleAuthorityMap
 * @description: 角色和权限关系
 * @author: liwei
 * @date: 2017-03-26 下午2:15:10
 */
public class SysRoleAuthorityMap implements Serializable {
    private static final long serialVersionUID = 1466293182042672105L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 角色
     */
    private SysRole sysRole;

    /**
     * 权限
     */
    private SysAuthority sysAuthority;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SysRole getSysRole() {
        return sysRole;
    }

    public void setSysRole(SysRole sysRole) {
        this.sysRole = sysRole;
    }

    public SysAuthority getSysAuthority() {
        return sysAuthority;
    }

    public void setSysAuthority(SysAuthority sysAuthority) {
        this.sysAuthority = sysAuthority;
    }


}